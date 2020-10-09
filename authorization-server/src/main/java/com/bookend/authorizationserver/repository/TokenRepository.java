package com.bookend.authorizationserver.repository;

import com.bookend.authorizationserver.model.Token;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TokenRepository extends JpaRepository<Token,Long> {

    Token findByConfirmationToken(String token);
}
