package com.example.transaction_5.security;

import com.example.transaction_5.repositories.UserRepository;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import static org.springframework.security.config.http.SessionCreationPolicy.STATELESS;

@EnableWebSecurity
@Configuration
public class SecurityConfiguration extends WebSecurityConfigurerAdapter {

    private final UserDetailsService userDetailsService;
    private final PasswordEncoder bCryptPasswordEncoder;
    private final UserRepository userRepository;



    private static final String[] PUBLIC_URLS = {
            "/v2/api-docs",
            "/swagger-resources/**",
            "/swagger-ui/**",
            "/swagger-ui.html",
            "/webjars/**",
            "/api/auth/**",
    };

    public SecurityConfiguration(UserDetailsService userDetailsService, PasswordEncoder bCryptPasswordEncoder, UserRepository userRepository) {
        this.userDetailsService = userDetailsService;
        this.bCryptPasswordEncoder = bCryptPasswordEncoder;
        this.userRepository = userRepository;
    }


    @Override
    public void configure(WebSecurity web){
        web.ignoring().antMatchers(PUBLIC_URLS);
    }

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(userDetailsService).passwordEncoder(bCryptPasswordEncoder);
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.csrf().disable()
                .cors().and().authorizeRequests()
                .antMatchers(PUBLIC_URLS).permitAll();
        http.authorizeRequests().antMatchers("/api/**").hasAnyAuthority("USER");
        http.sessionManagement().sessionCreationPolicy(STATELESS);
        http.addFilter(new CustomAuthenticationFilter(authenticationManagerBean(),userRepository));
        http.addFilterBefore(new CustomAuthorizationFilter(), UsernamePasswordAuthenticationFilter.class);
    }

    @Bean
    @Override
    public AuthenticationManager authenticationManagerBean() throws Exception{
        return super.authenticationManagerBean();
    }
}
