package com.bookend.authorizationserver.serviceTest;

import com.bookend.authorizationserver.kafka.MessageProducer;
import com.bookend.authorizationserver.model.Token;
import com.bookend.authorizationserver.model.User;
import com.bookend.authorizationserver.payload.*;
import com.bookend.authorizationserver.repository.RoleRepository;
import com.bookend.authorizationserver.repository.TokenRepository;
import com.bookend.authorizationserver.repository.UserDetailRepository;
import com.bookend.authorizationserver.service.UserService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.server.ResponseStatusException;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.verify;

@ExtendWith(MockitoExtension.class)


public class UserServiceTest {

    @Mock
    private UserDetailRepository userDetailRepository;
    @Mock
    private PasswordEncoder passwordEncoder;
    @Mock
    private TokenRepository tokenRepository;
    @Mock
    private MessageProducer messageProducer;
    @Mock
    private RoleRepository roleRepository;
    @InjectMocks
    private UserService userService;

    @Test
    void shouldNotAddUserIfEmailExistInAnotherUser(){
        SignUpRequest signUpRequest = new SignUpRequest("testusername","pass","test@blabla.com");
        given(userDetailRepository.existsByEmail(signUpRequest.getEmail())).willReturn(true);
        assertThrows(ResponseStatusException.class,()->{
            userService.addUser(signUpRequest);
        });
    }
    @Test
    void shouldNotAddUserIfUsernameExistInAnotherUser(){
        SignUpRequest signUpRequest = new SignUpRequest("testusername","pass","test@blabla.com");
        given(userDetailRepository.existsByEmail(signUpRequest.getEmail())).willReturn(false);
        given(userDetailRepository.existsByUsername(signUpRequest.getUsername())).willReturn(true);
        assertThrows(ResponseStatusException.class,()->{
            userService.addUser(signUpRequest);
        });
    }
    @Test
    void shouldAddUser(){
        SignUpRequest signUpRequest = new SignUpRequest("testusername","pass","test@blabla.com");
        User user = new User();
        user.setEmail(signUpRequest.getEmail());
        Token token = new Token();
        given(userDetailRepository.existsByEmail(signUpRequest.getEmail())).willReturn(false);
        given(userDetailRepository.existsByUsername(signUpRequest.getUsername())).willReturn(false);
        given(userDetailRepository.save(any(User.class))).willReturn(user);
        given(tokenRepository.save(any(Token.class))).willReturn(token);

        SignUpResponse signUpResponse =  userService.addUser(signUpRequest);
        assertThat(signUpResponse).isNotNull();
        verify(tokenRepository).save(any(Token.class));
        verify(userDetailRepository).save(any(User.class));

    }
    @Test
    void shouldNotResetPasswordIfUserNotExistWithGivenEmail(){
        ResetPasswordRequest resetPasswordRequest = new ResetPasswordRequest("test@blabla.com");
        given(userDetailRepository.findUserByEmail(resetPasswordRequest.getEmail())).willReturn(null);
        assertThrows(IllegalArgumentException.class,()->{
            userService.resetPassword(resetPasswordRequest);
        });
    }
    @Test
    void shouldResetPassword(){
        ResetPasswordRequest resetPasswordRequest = new ResetPasswordRequest("test@blabla.com");
        User user = new User();
        user.setEmail(resetPasswordRequest.getEmail());
        Token token = new Token();
        given(userDetailRepository.findUserByEmail(resetPasswordRequest.getEmail())).willReturn(user);
        given(tokenRepository.save(any(Token.class))).willReturn(token);
        ResetPasswordResponse resetPasswordResponse = userService.resetPassword(resetPasswordRequest);
        assertThat(resetPasswordResponse).isNotNull();
        verify(tokenRepository).save(any(Token.class));
    }
    @Test
    void shouldNotConfirmIfTokenNotValid(){
        given(tokenRepository.findByConfirmationToken(any(String.class))).willReturn(null);
        ConfirmResponse confirmResponse = userService.confirm("blabla");
        assertThat(confirmResponse).isNotNull();
    }
    @Test
    void shouldConfirm(){
        User user =  new User();
        Token token = new Token(user);
        given(tokenRepository.findByConfirmationToken(any(String.class))).willReturn(token);
        given(userDetailRepository.save(any(User.class))).willReturn(user);
        ConfirmResponse confirmResponse = userService.confirm("blabla");
        assertThat(confirmResponse).isNotNull();
        verify(userDetailRepository).save(any(User.class));

    }

    @Test
    void shouldNotConfirmPasswordIfTokenNotValid(){
        given(tokenRepository.findByConfirmationToken(any(String.class))).willReturn(null);
        ConfirmResponse confirmResponse = userService.confirmPassword("blabla",new NewPasswordRequest("pass"));
        assertThat(confirmResponse).isNotNull();
    }
    @Test
    void shouldConfirmPassword(){
        User user =  new User();
        Token token = new Token(user);
        given(tokenRepository.findByConfirmationToken(any(String.class))).willReturn(token);
        given(userDetailRepository.save(any(User.class))).willReturn(user);
        ConfirmResponse confirmResponse = userService.confirmPassword("blabla",new NewPasswordRequest("pass"));
        assertThat(confirmResponse).isNotNull();
        verify(userDetailRepository).save(any(User.class));

    }
    @Test
    void shouldReturnNullWhenFindByUserNameIfNotExist(){
        User user = new User();
        user.setUsername("ekrem");
        given(userDetailRepository.findUserByUsername("ekrem")).willReturn(null);
        User expected = userService.findByUsername("ekrem");
        assertThat(expected).isNull();
    }
    @Test
    void shouldFindByUserName(){
        User user = new User();
        user.setUsername("ekrem");
        given(userDetailRepository.findUserByUsername("ekrem")).willReturn(user);
        User expected = userService.findByUsername("ekrem");
        assertEquals(expected,user);
        assertThat(expected).isNotNull();
    }
    @Test
    void shouldSaveUser(){
        User user = new User();
        user.setUsername("ekrem");
        given(userDetailRepository.save(user)).willReturn(user);
        User expected = userService.save(user);
        assertThat(expected).isNotNull();
        verify(userDetailRepository).save(any(User.class));
    }
}
