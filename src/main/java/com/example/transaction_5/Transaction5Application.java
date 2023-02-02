package com.example.transaction_5;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.Bean;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.filter.ForwardedHeaderFilter;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

@EnableJpaRepositories("com.example.transaction_5.repositories")
@SpringBootApplication
@EnableSwagger2
@EntityScan("com.example.transaction_5.entities")
public class Transaction5Application {

    public static void main(String[] args) {
        SpringApplication.run(Transaction5Application.class, args);
    }
    @Bean
    public PasswordEncoder passwordEncoder(){
        return new BCryptPasswordEncoder();
    }

    @Bean
    ForwardedHeaderFilter forwardedHeaderFilter() {
        return new ForwardedHeaderFilter();
    }
}
