package com.example.transaction_5.security;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.example.transaction_5.entities.Users;
import lombok.extern.slf4j.Slf4j;

import org.springframework.stereotype.Component;

import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.stream.Collectors;


import static com.example.transaction_5.utilities.Constants.*;


@Slf4j
@Component
public class JwtTokenCreator {



    public  static Map<String,String> createJwtToken(Users users){
        Map<String , String> tokens = new HashMap<>();
        Algorithm algorithm = Algorithm.HMAC256(SECURITY_KEY);

        String access_token = JWT.create()
                .withSubject(users.getPhone())
                .withExpiresAt(new Date(System.currentTimeMillis() + X_DAY))
                .withIssuer(String.valueOf(users.getPassword()))
                .withClaim("roles", Collections.singletonList("USER"))
                .sign(algorithm);



        tokens.put("access_token", access_token);
        return tokens;
    }

}
