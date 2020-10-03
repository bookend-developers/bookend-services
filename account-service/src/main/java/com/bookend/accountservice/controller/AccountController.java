package com.bookend.accountservice.controller;

import com.bookend.accountservice.model.Account;
import com.bookend.accountservice.service.AccountService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.oauth2.provider.OAuth2Authentication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.LinkedHashMap;
import java.util.Map;

@RestController
public class AccountController {
    private AccountService accountService;
    @Autowired
    public void setAccountService(AccountService accountService) {
        this.accountService = accountService;
    }
    @GetMapping("/account/view")
    public Account viewProfile(OAuth2Authentication auth){


        return accountService.getByUsername(auth.getName());
    }
}
