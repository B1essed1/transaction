package com.example.transaction_5.repositories;

import com.example.transaction_5.entities.Users;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface UserRepository extends JpaRepository<Users, String> {
    Optional<Users> findUserByPhone(String phone);
}
