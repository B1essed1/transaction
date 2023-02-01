package com.example.transaction_5.services;

import com.example.transaction_5.models.Registration;
import com.example.transaction_5.models.ResponseDto;

public interface UserService {
    ResponseDto<?> save(Registration registration);

    ResponseDto<?> login(Registration registration);

    void delete (String id);
}
