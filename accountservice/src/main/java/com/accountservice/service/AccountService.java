package com.accountservice.service;
import com.accountservice.model.Account;

import java.util.List;

public interface AccountService {

    Account getById(Long id);


    Account saveOrUpdate(Account advert);

    void delete(Long id);
}
