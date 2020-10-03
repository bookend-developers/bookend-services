package com.bookend.accountservice.service;

import com.bookend.accountservice.model.Account;
import com.bookend.accountservice.repository.AccountRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class AccountServiceImpl implements AccountService{
    private AccountRepository accountRepository;
    @Autowired
    public void setAccountRepository(AccountRepository accountRepository) {
        this.accountRepository = accountRepository;
    }

    @Override
    public Account getByID(String id) {
        return accountRepository.getAccountById(id);
    }

    @Override
    public Account getByUsername(String username) {
        return accountRepository.getAccountByUsername(username);
    }

    @Override
    public Account getByEmail(String email) {
        return accountRepository.getAccountByEmail(email);
    }
}
