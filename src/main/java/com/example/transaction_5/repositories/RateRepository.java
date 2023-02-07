package com.example.transaction_5.repositories;

import com.example.transaction_5.entities.Rate;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RateRepository extends JpaRepository<Rate, Long> {

    Rate findRateByFromCurrency(String fromCurrency);
}
