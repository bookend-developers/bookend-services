package com.mailservice.mailservice.serviceTest;

import com.mailservice.mailservice.repository.UserRepository;
import com.mailservice.mailservice.service.EmailMailSender;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
public class EMailMailSenderServiceTest {
    @Mock
    private UserRepository userRepository;
    @InjectMocks
    private EmailMailSender emailMailSender;

}
