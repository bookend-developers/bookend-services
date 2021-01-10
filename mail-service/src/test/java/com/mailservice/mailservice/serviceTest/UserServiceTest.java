package com.mailservice.mailservice.serviceTest;


import com.mailservice.mailservice.model.User;
import com.mailservice.mailservice.repository.UserRepository;
import com.mailservice.mailservice.service.UserService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;

@ExtendWith(MockitoExtension.class)

public class UserServiceTest {

    @Mock
    private UserRepository userRepository;

    @InjectMocks
    private UserService userService;

    @Test
    void shouldSaveUser(){
        User user = new User();
        user.setId((long)1);
        user.setEmail("testdeneme@blabla.com");
        given(userRepository.save(any(User.class))).willReturn(user);
        final User expected = userService.save(user.getId(),user.getEmail());
        assertThat(expected).isNotNull();
        assertEquals(expected,user);
    }
    @Test
    void shouldNotSaveWhenIdOrUsernameNull(){
        User user = new User();
        user.setId(null);
        user.setEmail("testdeneme@blabla.com");
        User user2 = new User();
        user2.setId((long)1);
        user2.setEmail(null);
        assertThrows(IllegalArgumentException.class,()->{
            userService.save(user.getId(),user.getEmail());
        });
        assertThrows(IllegalArgumentException.class,()->{
            userService.save(user2.getId(),user2.getEmail());
        });
        verify(userRepository, never()).save(any(User.class));
    }
}
