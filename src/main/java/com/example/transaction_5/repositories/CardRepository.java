package com.example.transaction_5.repositories;

import com.example.transaction_5.entities.Card;
import com.example.transaction_5.models.CardInfo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface CardRepository extends JpaRepository<Card,Long > {

    @Query("select Card from Card c where c.user.id = :id")
    List<Card> getUsersCard(@Param("id") String id);

    Optional<CardInfo> getCardInfo();

}
