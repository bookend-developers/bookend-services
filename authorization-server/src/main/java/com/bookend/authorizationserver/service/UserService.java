package com.bookend.authorizationserver.service;

import com.bookend.authorizationserver.model.Token;
import com.bookend.authorizationserver.model.User;
import com.bookend.authorizationserver.payload.SignUpRequest;
import com.bookend.authorizationserver.repository.TokenRepository;
import com.bookend.authorizationserver.repository.UserDetailRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.*;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.server.ResponseStatusException;

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
        if(userDetailRepository.existsByEmail(signUpRequest.getEmail())){
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST,"Entered email is already in use please enter another email.");
        }
        user.setEmail(signUpRequest.getEmail());
        user.setPassword(passwordEncoder.encode(signUpRequest.getPassword()));
        if(userDetailRepository.existsByUsername(signUpRequest.getUsername())){
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST,"Entered username is already in use please enter another one.");
        }
        user.setUsername(signUpRequest.getUsername());
        user.setEnabled(false);
        user.setAccountNonExpired(true);
        user.setCredentialsNonExpired(true);
        user.setAccountNonLocked(true);
        Token token = new Token(user);
        userDetailRepository.save(user);
        tokenRepository.save(token);
        addToMailService(String.valueOf(user.getId()),user.getEmail());
        sendMail(user.getEmail(),token.getConfirmationToken());
    }
    public User save(User user){
        return userDetailRepository.save(user);
    }
    public User findByUsername(String username){
        return userDetailRepository.findUserByUsername(username);
    }
    public void confirm(String token){
        Token confirmationToken = tokenRepository.findByConfirmationToken(token);
        User user = confirmationToken.getUser();
        user.setEnabled(true);
        tokenRepository.delete(confirmationToken);
    }
    public void addToMailService(String id, String email){
        RestTemplate restTemplate = new RestTemplate();

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);

        MultiValueMap<String, String> map= new LinkedMultiValueMap<String, String>();
        map.add("id", id);
        map.add("email", email);

        HttpEntity<MultiValueMap<String, String>> request = new HttpEntity<MultiValueMap<String, String>>(map, headers);

        ResponseEntity<String> response = restTemplate.postForEntity( "http://localhost:8090/save-user", request , String.class );
    }

    public void sendMail(String email, String token){
        RestTemplate restTemplate = new RestTemplate();

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);

        MultiValueMap<String, String> map= new LinkedMultiValueMap<String, String>();
        map.add("email", email);
        map.add("token", token);

        HttpEntity<MultiValueMap<String, String>> request = new HttpEntity<MultiValueMap<String, String>>(map, headers);

        ResponseEntity<String> response = restTemplate.postForEntity( "http://localhost:8090/confirmation-mail", request , String.class );
    }
}
