package com.bookend.authorservice.serviceTest;

import com.bookend.authorservice.model.Author;
import com.bookend.authorservice.repository.AuthorRepository;
import com.bookend.authorservice.service.AuthorService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;


public class BookServiceTest {
    @Mock
    private AuthorRepository authorRepository;
    @InjectMocks
    private AuthorService authorService;

}
