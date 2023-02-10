package com.example.transaction_5.services.ServicesImpls;

import com.example.transaction_5.entities.Card;
import com.example.transaction_5.entities.Rate;
import com.example.transaction_5.entities.Transactions;
import com.example.transaction_5.models.ResponseDto;
import com.example.transaction_5.models.TransactionDetails;
import com.example.transaction_5.repositories.CardRepository;
import com.example.transaction_5.repositories.RateRepository;
import com.example.transaction_5.repositories.TransactionRepository;
import com.example.transaction_5.services.TransactionService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Transactional;


@Service
public class TransactionServiceImpl implements TransactionService {

    private final TransactionRepository transactionRepository;
    private final CardRepository cardRepository;
    private final RateRepository rateRepository;

    public TransactionServiceImpl(TransactionRepository transactionRepository, CardRepository cardRepository, RateRepository rateRepository) {

        this.transactionRepository = transactionRepository;
        this.cardRepository = cardRepository;
        this.rateRepository = rateRepository;
    }

    @Transactional(isolation = Isolation.SERIALIZABLE)
    public ResponseDto<?> requestTransaction(TransactionDetails transactionDetails) {

        if (transactionRepository.
                findTransactionsByStatusAndSenderCardId("NEW",transactionDetails.getSenderCardId()).isEmpty()) {
            Card sendersCard = cardRepository.getById(transactionDetails.getSenderCardId());
            Card receiverCard = cardRepository.getById(transactionDetails.getReceiverCardId());

            Transactions transactions = new Transactions();
            transactions.setStatus("NEW");
            transactions.setTime(System.currentTimeMillis());
            transactions.setReceiverCardId(receiverCard.getId());
            transactions.setSenderCardId(sendersCard.getId());

            // Check if sender has enough balance
            if (sendersCard.getBalance() < transactionDetails.getAmount()) {
                return ResponseDto.builder()
                        .isError(true)
                        .message("There is not enough money in your card")
                        .build();
            }

            // Deduct amount from sender's card balance
            transactions.setSenderAmount(transactionDetails.getAmount());

            // Convert amount if currency type is different
            if (sendersCard.getType() != receiverCard.getType()) {
                Rate rate = rateRepository.findRateByFromCurrency(sendersCard.getCurrency());

                // UZS to other currency
                if (rate.getFromCurrency().equals("UZS")) {
                    if (transactionDetails.getAmount() < rate.getRate()) {
                        return ResponseDto.builder()
                                .isError(true)
                                .message("you must transfer money more than " + rate.getRate())
                                .build();
                    }
                    long receiverAmount =  transactionDetails.getAmount()% rate.getRate();
                    transactions.setReceiverAmount(receiverAmount);
                    transactions.setSenderAmount(receiverAmount*rate.getRate());
                }
                // Other currency to UZS
                else {
                    transactions.setReceiverAmount(transactionDetails.getAmount() * rate.getRate());
                }
            }
            // Same currency, no conversion needed
            else {
                transactions.setReceiverAmount(transactionDetails.getAmount());
            }


            transactionRepository.save(transactions);
            return ResponseDto.builder()
                    .isError(false)
                    .data(transactions.getId())
                    .message("Success")
                    .build();
        }
        return ResponseDto.builder()
                .isError(true)
                .message("Transaction cannot be proceeded because you have a unfinished transactions")
                .build();
    }

    @Override
    @Transactional
    public ResponseDto<?> confirm(String id) {
        Transactions transaction = transactionRepository.getById(id);

        try {
            Card sendersCard = cardRepository.getById(transaction.getSenderCardId());
            Card receiverCard = cardRepository.getById(transaction.getReceiverCardId());
            if (sendersCard.getBalance()>transaction.getSenderAmount()) {
                sendersCard.setBalance(sendersCard.getBalance() - transaction.getSenderAmount());
                receiverCard.setBalance(receiverCard.getBalance() + transaction.getReceiverAmount());
                transaction.setStatus("SUCCESS");
                cardRepository.save(sendersCard);
                cardRepository.save(receiverCard);
                transactionRepository.save(transaction);
            } else {
                transaction.setStatus("ERROR");
                transactionRepository.save(transaction);
            }
        } catch (Exception e) {
            transaction.setStatus("ERROR");
            transactionRepository.save(transaction);

            return ResponseDto.builder()
                    .isError(true)
                    .message(e.getMessage())
                    .build();
        }

        return ResponseDto.builder()
                .isError(false)
                .message("Successfully transferred")
                .build();
    }
}
