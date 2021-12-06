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
/**
 * AUTHS-USC stands for AuthorizationServer-UserServiceClass
 * SM stands for ServiceMethod
 */
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

    /**
     * AUTHS-USC-1 (SM_11)
     */
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
    /**
     * AUTHS-USC-2 (SM_12)
     */
    public ResetPasswordResponse resetPassword(ResetPasswordRequest resetPasswordRequest) {
    	User user = userDetailRepository.findUserByEmail(resetPasswordRequest.getEmail());
    	if (user == null){
    		throw new IllegalArgumentException("user not exist");
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
    /**
     * AUTHS-USC-3 (SM_13)
     */
    public User save(User user){
        return userDetailRepository.save(user);
    }
    /**
     * AUTHS-USC-4 (SM_14)
     */
    public User findByUsername(String username){
        return userDetailRepository.findUserByUsername(username);
    }
    /**
     * AUTHS-USC-5 (SM_15)
     */
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
    /**
     * AUTHS-USC-6 (SM_16)
     */
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

}
