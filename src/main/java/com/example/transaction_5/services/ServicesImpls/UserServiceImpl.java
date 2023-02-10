package com.example.transaction_5.services.ServicesImpls;

import com.example.transaction_5.entities.Users;
import com.example.transaction_5.models.Registration;
import com.example.transaction_5.models.ResponseDto;
import com.example.transaction_5.repositories.UserRepository;
import com.example.transaction_5.services.UserService;
import com.example.transaction_5.utilities.Utils;
import javax.transaction.Transactional;

import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Optional;
import java.util.UUID;

import static com.example.transaction_5.utilities.Constants.USERNAME_NOT_FOUND;

@Service
public class UserServiceImpl implements UserService, UserDetailsService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    public UserServiceImpl(UserRepository userRepository, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public UserDetails loadUserByUsername(String phone) throws UsernameNotFoundException {

        Users users = userRepository.findUserByPhone(phone).orElseThrow(
                ()-> new UsernameNotFoundException(String.format(USERNAME_NOT_FOUND,phone)));


        Collection<SimpleGrantedAuthority> authorities = new ArrayList<>();
        authorities.add(new SimpleGrantedAuthority("USER"));

        return new User(users.getUsername(), users.getPassword(),authorities);
    }

    @Override
    @Transactional
    public ResponseDto<?> save(Registration registration) {

        //checks  if phone number is valid
        if (Utils.isPhoneValid(registration.getPhone())){
            // if phone number valid then validates phone number if it's ok then it is saved
            if (Utils.isPasswordValid(registration.getPassword())){
                Users users = new Users();
              //  users.setPassword(passwordEncoder.encode(registration.getPassword()));
                users.setPassword(registration.getPassword());
                users.setPhone(registration.getPhone());
                userRepository.save(users);
                return ResponseDto.builder()
                        .isError(false)
                        .message("created")
                        .data(users)
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
            Optional<Users> user = userRepository.findUserByPhone(registration.getPhone());
            //checks user is there or not if there then checks is password is correct
            // if success then permission is kinda granted
            if (user.isPresent()){
               //  String password = passwordEncoder.encode(registration.getPassword());
                 String password =registration.getPassword();
                if (user.get().getPassword().equals(password)){
                    return ResponseDto.builder()
                            .data(user.get())
                            .message("Successfully logg     ed in")
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
        Optional<Users> user = userRepository.findById(id);
        user.ifPresentOrElse(u -> u.setStatus("DELETED"), ()-> new RuntimeException("User not found"));
    }


}
