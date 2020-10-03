package com.bookend.accountservice.repository;

import com.bookend.accountservice.model.Account;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AccountRepository extends MongoRepository<Account, String> {
    Account getAccountById(String id);
    Account getAccountByEmail(String email);
    Account getAccountByUsername(String username);
}
