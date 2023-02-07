package com.example.transaction_5.repositories;

import com.example.transaction_5.entities.Card;
import com.example.transaction_5.models.CardInfo;
import com.example.transaction_5.models.CardsDetails;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface CardRepository extends JpaRepository<Card,Long > {

    @Query("select new com.example.transaction_5.models.CardsDetails(c.id,c.balance,c.name,c.cardNumber,c.type,c.currency) " +
            "from Card c where c.users.id = :id")
    List<CardsDetails> getUsersCard(@Param("id") String id);

    @Query("select new  com.example.transaction_5.models.CardInfo(u.phone,c.cardNumber,c.type,c.id,u.id)" +
            " from Card c join Users u on u.id = c.users.id where c.cardNumber=:card")
    Optional<CardInfo> getCardInfo(@Param("card") String card);

}
