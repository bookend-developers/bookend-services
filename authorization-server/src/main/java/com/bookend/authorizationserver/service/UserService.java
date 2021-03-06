package com.bookend.authorizationserver.service;

import com.bookend.authorizationserver.kafka.MessageProducer;
import com.bookend.authorizationserver.model.Token;
import com.bookend.authorizationserver.model.User;
import com.bookend.authorizationserver.payload.*;
import com.bookend.authorizationserver.repository.RoleRepository;
import com.bookend.authorizationserver.repository.TokenRepository;
import com.bookend.authorizationserver.repository.UserDetailRepository;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import com.bookend.authorizationserver.model.Role;

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
    @Autowired
    private MessageProducer messageProducer;
    @Autowired
    private RoleRepository roleRepository;


    public SignUpResponse addUser(SignUpRequest signUpRequest){
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
        messageProducer.sendConfirmationMailRequest(new MailRequest(user.getEmail(),"Confirmation Mail",
                "Kayıt olduğunuz için teşekkürler.\nKullanıcı adınız: " + user.getUsername() + "\n Aktivasyon linki: " +
                        "" + "localhost:9191/api/oauth/confirm/"  + token.getConfirmationToken() +
                        "\n\n" +
                        "Thank you for registering.\nYour username: "+ user.getUsername()+ "\n Activation link: " +
                        "" + "localhost:9191/api/oauth/confirm/"+ token.getConfirmationToken()));
        return new SignUpResponse("user registered. need confirmation",token.getConfirmationToken());
    }
    
    public ResetPasswordResponse resetPassword(ResetPasswordRequest resetPasswordRequest) {
    	User user = userDetailRepository.findUserByEmail(resetPasswordRequest.getEmail());
    	if (user == null){
    		return null;
    	}
    	Token token = new Token(user);
    	tokenRepository.save(token);
    	messageProducer.sendResetPasswordMailRequest(new MailRequest(user.getEmail(),"Reset Password Confirmation Mail",
        "Şifrenizi yenilemek için aktivasyon linkine tıklayınız.\n Aktivasyon linki: " +
        "" + "localhost:9191/api/oauth/confirmPassword/"  + token.getConfirmationToken() +
        "\n\n" +
        "Click on the activation link to reset your password..\nActivation link: " +
        "" + "localhost:9191/api/oauth/confirmPassword/"+ token.getConfirmationToken()));
    	
    	return new ResetPasswordResponse("need confirmation to reset passsword",token.getConfirmationToken());
    }

    public User save(User user){
        return userDetailRepository.save(user);
    }
    public User findByUsername(String username){
        return userDetailRepository.findUserByUsername(username);
    }
    public ConfirmResponse confirm(String token){

        Token confirmationToken = tokenRepository.findByConfirmationToken(token);
        if(confirmationToken==null){
            return new ConfirmResponse("not valid token",null);
        }
        User user = confirmationToken.getUser();
        user.setEnabled(true);
        Role role = roleRepository.findByName("ROLE_USER");
        List<Role> roles = new ArrayList<>();
        roles.add(role);
        user.setRoles(roles);
        userDetailRepository.save(user);
        tokenRepository.delete(confirmationToken);
        messageProducer.sendUserInformation(new KafkaUserRegistered(user.getId(),user.getUsername(),user.getEmail()));
        return  new ConfirmResponse("confirmed successfully",user.getUsername());
    }
    
    public ConfirmResponse confirmPassword(String token,NewPasswordRequest newPasswordRequest){

        Token confirmationToken = tokenRepository.findByConfirmationToken(token);
        if(confirmationToken==null){
            return new ConfirmResponse("not valid token",null);
        }
        User user = confirmationToken.getUser();
        user.setEnabled(true);
        user.setPassword(passwordEncoder.encode(newPasswordRequest.getPassword()));
        userDetailRepository.save(user);
        tokenRepository.delete(confirmationToken);
        //messageProducer.sendUserInformation(new KafkaUserRegistered(user.getId(),user.getUsername(),user.getEmail()));
        return  new ConfirmResponse("password reset confirmed successfully",user.getUsername());
    }
    
    
    /*
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
    }*/
}
