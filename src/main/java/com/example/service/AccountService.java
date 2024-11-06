package com.example.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.entity.Account;
import com.example.repository.AccountRepository;
import com.example.repository.MessageRepository;


@Service
public class AccountService {
    private AccountRepository accountRepository;
    private MessageRepository messageRepository;

    @Autowired
    public AccountService(){
        this.accountRepository = accountRepository;
        this.messageRepository = messageRepository;
    }

    public Account UserLogin(String username, String password){
        return accountRepository.findByAccountAndPassword(username, password).orElseThrow();
    }
    public Account UserRegistration(Account account){
        return accountRepository.save(account);
    }
    
}
