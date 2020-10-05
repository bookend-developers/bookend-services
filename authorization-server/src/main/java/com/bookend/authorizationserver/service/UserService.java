package com.bookend.authorizationserver.service;

import com.bookend.authorizationserver.model.Token;
import com.bookend.authorizationserver.model.User;
import com.bookend.authorizationserver.payload.SignUpRequest;
import com.bookend.authorizationserver.repository.TokenRepository;
import com.bookend.authorizationserver.repository.UserDetailRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class UserService {

    @Autowired
    UserDetailRepository userDetailRepository;
    @Autowired
    private PasswordEncoder passwordEncoder;
    @Autowired
    private TokenRepository tokenRepository;

    public void addUser(SignUpRequest signUpRequest){
        User user = new User();
        user.setEmail(signUpRequest.getEmail());
        user.setPassword(passwordEncoder.encode(signUpRequest.getPassword()));
        user.setUsername(signUpRequest.getUsername());
        user.setEnabled(false);
        user.setAccountNonExpired(true);
        user.setCredentialsNonExpired(true);
        user.setAccountNonLocked(true);
        Token token = new Token(user);
        userDetailRepository.save(user);
        tokenRepository.save(token);
    }

    public void confirm(String token){
        Token confirmationToken = tokenRepository.findByConfirmationToken(token);
        User user = confirmationToken.getUser();
        user.setEnabled(true);
        tokenRepository.delete(confirmationToken);
    }
}
