package com.example.transaction_5.services.ServicesImpls;

import com.example.transaction_5.entities.User;
import com.example.transaction_5.models.Registration;
import com.example.transaction_5.models.ResponseDto;
import com.example.transaction_5.repositories.UserRepository;
import com.example.transaction_5.services.UserService;
import com.example.transaction_5.utilities.Utils;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.UUID;

@Service
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;

    public UserServiceImpl(UserRepository userRepository) {
        this.userRepository = userRepository;
    }


    @Override
    @Transactional
    public ResponseDto<?> save(Registration registration) {

        //checks  if phone number is valid
        if (Utils.isPhoneValid(registration.getPhone())){
            // if phone number valid then validates phone number if it's ok then it is saved
            if (Utils.isPasswordValid(registration.getPassword())){
                User user = new User();
                user.setPassword(registration.getPassword());
                user.setPhone(registration.getPhone());
                userRepository.save(user);
                return ResponseDto.builder()
                        .isError(false)
                        .message("created")
                        .build();
            } else {
                return ResponseDto.builder()
                        .message("should at least 8 characters\n" +
                                ", at least one letter\n" +
                                ", at least one digit\n" +
                                "and at least one special character (@, $, !, %, *, #, ?, &)")
                        .isError(true)
                        .build();
            }

        }
        return ResponseDto.builder()
                .isError(true)
                .message("phone number is not valid! please check it is the same with this pattern {+998901234567}")
                .build();

    }

    @Override
    public ResponseDto<?> login(Registration registration) {

        //checks if the phone number valid
        if (Utils.isPhoneValid(registration.getPhone())){
            Optional<User> user = userRepository.findUserByPhone(registration.getPhone());
            //checks user is there or not if there then checks is password is correct
            // if success then permission is kinda granted
            if (user.isPresent()){
                if (user.get().getPassword().equals(registration.getPassword())){
                    return ResponseDto.builder()
                            .message("Successfully logged in")
                            .isError(false).build();
                } else {
                    return ResponseDto.builder()
                            .isError(true)
                            .message("Incorrect password! Please try again")
                            .build();
                }
            }  else {
                return ResponseDto.builder()
                        .isError(true)
                        .message("User not found")
                        .build();
            }
        }

        return ResponseDto.builder()
                .isError(true)
                .message("phone number is not valid! please check it is the same with this pattern {+998901234567}")
                .build();
    }

    @Override
    @Transactional
    public void delete(String id) {
        UUID uuid = UUID.fromString(id);
        Optional<User> user = userRepository.findById(uuid);
        user.ifPresentOrElse(u -> u.setStatus("DELETED"), ()-> new RuntimeException("User not found"));
    }
}
