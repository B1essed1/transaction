package com.example.transaction_5.controller;

import com.example.transaction_5.entities.Users;
import com.example.transaction_5.models.Registration;
import com.example.transaction_5.models.ResponseDto;
import com.example.transaction_5.security.JwtTokenCreator;
import com.example.transaction_5.services.UserService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/auth/")
public class UserController {

    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @PostMapping("registration")
    public ResponseEntity<?> registration(@RequestBody Registration registration) {

        ResponseDto response = userService.save(registration);
        if (response.getIsError()) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response.getMessage());
        }
        return ResponseEntity.status(HttpStatus.CREATED).body(JwtTokenCreator.createJwtToken((Users) response.getData()));
    }

    @PostMapping("login")
    public ResponseEntity<?> login(@RequestBody Registration registration) {
        ResponseDto response = userService.login(registration);

        if (response.getIsError()) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response.getMessage());
        }

        return ResponseEntity.ok(JwtTokenCreator.createJwtToken((Users) response.getData()));
    }

    @DeleteMapping("delete")
    ResponseEntity<?> delete(@RequestParam("id") String id ){
        userService.delete(id);
       return ResponseEntity.ok().body("deleted");
    }

}
