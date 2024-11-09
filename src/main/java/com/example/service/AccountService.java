package com.example.service;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Example;
import org.springframework.stereotype.Service;

import com.example.entity.Account;
import com.example.repository.AccountRepository;
import com.example.repository.MessageRepository;


@Service
public class AccountService {
    private AccountRepository accountRepository;
    private MessageRepository messageRepository;

    @Autowired
    public AccountService(MessageRepository messageRepository, AccountRepository accountRepository){
        this.accountRepository = accountRepository;
        this.messageRepository = messageRepository;
    }

    public Account login(Account account){
        if(validateAccount(account.getUsername(), account.getPassword())){
            return accountRepository.findByUsernameAndPassword(account.getUsername(), account.getPassword()).orElseThrow();
        }
        return null;
    }
    
    public Account save(Account account){
        if (validateAccount(account.getUsername(), account.getPassword())) {

            Optional<Account> existingAccount = accountRepository.findByUsernameAndPassword(
                account.getUsername(), account.getPassword()
            );
            
            if (existingAccount.isPresent()) {
                return null;
            }
        }
    
        return accountRepository.save(account);
    }
    
    public Optional<Account> findByUsernameAndPassword(String username, String password){
        return accountRepository.findByUsernameAndPassword(username, password);
    }

    public Account findByAccountId(int accountId){
        Account account = accountRepository.findByAccountId(accountId);
        if (account != null) {
            return account;
        }
        return null;
    }

    //Utility
    boolean validateAccount(String username, String password){
        boolean[] passes = new boolean[3];

        passes[0] = username != null;
        passes[1] = username != "";
        
        passes[2] = password.length() >= 4;

        for(boolean pass : passes){
            if(!pass){ return false; }
        }
        for(boolean pass : passes){
            if(!pass){ return false; }
        }
        
        return true;
    }
}
