package com.bookend.accountservice.service;

import com.bookend.accountservice.model.Account;

public interface AccountService {
    Account getByID(String id);
    Account getByUsername(String username);
    Account getByEmail(String email);
}
