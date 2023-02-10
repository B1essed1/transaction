package com.example.transaction_5.services.ServicesImpls;

import com.example.transaction_5.entities.Card;
import com.example.transaction_5.entities.Users;
import com.example.transaction_5.models.CardDto;
import com.example.transaction_5.models.CardInfo;
import com.example.transaction_5.models.CardsDetails;
import com.example.transaction_5.models.ResponseDto;
import com.example.transaction_5.repositories.CardRepository;
import com.example.transaction_5.repositories.UserRepository;
import com.example.transaction_5.services.CardService;
import com.example.transaction_5.utilities.Utils;
import javax.transaction.Transactional;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class CardServiceImpl implements CardService {

    private final CardRepository cardRepository;
    private final UserRepository userRepository;

    public CardServiceImpl(CardRepository cardRepository, UserRepository userRepository) {
        this.cardRepository = cardRepository;
        this.userRepository = userRepository;
    }

    @Transactional
    @Override
    public ResponseDto<?> addCard(CardDto dto) {
          Card card = new Card();

        card.setBalance(dto.getBalance());
        card.setName(dto.getName());

        // checking card is number for bad case scenarios this  validation is written
        // usually this validation is handled in client side
        if (Utils.isNumeric(dto.getCarNumber())) {
            card.setCardNumber(dto.getCarNumber());
            if (Utils.isVisa(dto.getCarNumber())) {
                card.setType("VISA");
                card.setCurrency("USD");
            } else if (Utils.isHumo(dto.getCarNumber())){
                card.setCurrency("UZS");
            } else {
                return ResponseDto.builder()
                        .isError(true)
                        .message("Invalid card type!!! HUMO and VISA only")
                        .build();
            }
        } else {
            return ResponseDto.builder()
                    .isError(true)
                    .message("Invalid type!!! Numbers only")
                    .build();
        }

        Optional<Users> user = userRepository.findById(dto.getUser_id());
        card.setUsers(user.get());

        cardRepository.save(card);

        return ResponseDto.builder()
                .isError(false)
                .message("created")
                .build();

    }

    @Override
    public ResponseDto<?> getMyCards(String id) {

        List<CardsDetails> cards = cardRepository.getUsersCard(id);
        if (cards.isEmpty()){
            return ResponseDto.builder()
                    .isError(true)
                    .message("You do not have any card")
                    .build();
        }

        for (CardsDetails card:cards ) {
            card.setBalance(Math.round(card.getBalance()/100d));
        }

        return ResponseDto.builder()
                .isError(false)
                .data(cards)
                .build();
    }

    @Override
    public ResponseDto<?> getCardInfo(String cardNumber) {
        Optional<CardInfo> cardInfo = cardRepository.getCardInfo(cardNumber);
            if (cardInfo.isPresent()){
            return ResponseDto.builder()
                    .data(cardInfo.get())
                    .message("success")
                    .isError(false)
                    .build();
        }
          return ResponseDto.builder()
                .data(cardInfo.get())
                .message("this card is not exist")
                .isError(true)
                .build();
    }
}
