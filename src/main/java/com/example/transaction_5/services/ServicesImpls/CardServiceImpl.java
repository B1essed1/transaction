package com.example.transaction_5.services.ServicesImpls;

import com.example.transaction_5.entities.Card;
import com.example.transaction_5.entities.User;
import com.example.transaction_5.models.CardDto;
import com.example.transaction_5.models.ResponseDto;
import com.example.transaction_5.repositories.CardRepository;
import com.example.transaction_5.repositories.UserRepository;
import com.example.transaction_5.services.CardService;
import com.example.transaction_5.utilities.Utils;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

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
            card.setCard_number(dto.getCarNumber());
            if (Utils.isVisa(dto.getCarNumber())) {
                card.setType("VISA");
                card.setCurrency("USD");
            } else {
                card.setCurrency("UZS");
            }
        } else {
            return ResponseDto.builder()
                    .isError(true)
                    .message("Invalid type!!! Numbers only")
                    .build();
        }

        Optional<User> user = userRepository.findById(UUID.fromString(dto.getUser_id()));
        card.setUser(user.get());

        cardRepository.save(card);

        return ResponseDto.builder()
                .isError(false)
                .message("success")
                .build();
    }

    @Override
    public ResponseDto<?> getMyCards(String id) {

        List<Card> cards = cardRepository.getUsersCard(id);
        if (cards.isEmpty()){
            return ResponseDto.builder()
                    .isError(true)
                    .message("You do not have any card")
                    .build();
        }

        return ResponseDto.builder()
                .isError(false)
                .data(cards)
                .build();
    }

    @Override
    public ResponseDto<?> getCardInfo(String cardNumber) {
        return null;
    }
}
