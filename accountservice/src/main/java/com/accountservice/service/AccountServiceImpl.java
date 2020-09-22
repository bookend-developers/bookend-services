package com.accountservice.service;

import com.accountservice.model.Account;
import com.accountservice.repository.AccountRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AccountServiceImpl implements AccountService {
    private AccountRepository accountRepository ;
    @Autowired
    public void setAccountRepository(AccountRepository accountRepository) {
        this.accountRepository = accountRepository;
    }



    @Override
    public Account getById(Long id) {
        return accountRepository.findAccountById(id);
    }

    @Override
    public Account saveOrUpdate(Account account) {
        return accountRepository.save(account);
    }


    @Override
    public void delete(Long id) {

    }
}
