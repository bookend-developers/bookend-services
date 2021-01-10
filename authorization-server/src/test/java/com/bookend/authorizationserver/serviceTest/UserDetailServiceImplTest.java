package com.bookend.authorizationserver.serviceTest;

import com.bookend.authorizationserver.model.User;
import com.bookend.authorizationserver.repository.UserDetailRepository;
import com.bookend.authorizationserver.service.UserDetailServiceImpl;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.server.ResponseStatusException;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.BDDMockito.given;

@ExtendWith(MockitoExtension.class)

public class UserDetailServiceImplTest {
    @Mock
    private UserDetailRepository userDetailRepository;
    @InjectMocks
    private UserDetailServiceImpl userDetailService;

    @Test
    void shouldNotLoadUserByUsernameIfUserNotExist(){

        given(userDetailRepository.findByUsername("username")).willReturn(Optional.empty());
        assertThrows(UsernameNotFoundException.class,()->{
            userDetailService.loadUserByUsername("username");
        });
    }
    @Test
    void shouldLoadUserByUsername(){
        User user = new User();
        user.setAccountNonLocked(true);
        user.setEnabled(true);
        user.setAccountNonExpired(true);
        user.setCredentialsNonExpired(true);
        given(userDetailRepository.findByUsername("username")).willReturn(Optional.of(user));
        UserDetails userDetails = userDetailService.loadUserByUsername("username");
        assertThat(userDetails).isNotNull();
    }
}
