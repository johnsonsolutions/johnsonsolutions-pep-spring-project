package com.example.service;

import java.util.*;

import javax.transaction.Transactional;
import org.springframework.transaction.annotation.Propagation;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.repository.AccountRepository;
import com.example.repository.MessageRepository;

import com.example.entity.Message;
import com.example.entity.Account;


@Service
public class MessageService {
    private MessageRepository messageRepository;
    private AccountRepository accountRepository;

    @Autowired
    public MessageService(MessageRepository messageRepository, AccountRepository accountRepository){
        this.messageRepository = messageRepository;
        this.accountRepository = accountRepository;
    }

    public Message save(Message message){
        if(accountRepository.existsById(message.getPostedBy())){
            if(validateMessage(message.getMessageText())){
                return messageRepository.save(message);
            }
        }
        return null;
    }

    public List<Message> findAllMessages(){ return (List<Message>) messageRepository.findAll(); }

    public List<Message> findAllByPostedBy(int messageId){      
        List<Message> messages = (List<Message>) messageRepository.findAllByPostedBy(messageId);
        return messages;
    }

    public Optional<Message> findById(int messageId){ 
        Optional<Message> message = (Optional<Message>) messageRepository.findById(messageId);
        if(message.isPresent()){ return message; }
        return null;
     }

    public int UpdateMessage(int messageId, Message message){
        if(messageRepository.existsById(messageId) && (validateMessage(message.getMessageText()))){
            Message foundMessage = (Message) messageRepository.findById(messageId).orElseThrow();
            foundMessage.setMessageText(message.getMessageText());
            messageRepository.save(foundMessage);
            return 1;
        }
        else{ System.out.println("update fail"); }
        return 0;
    }
    public int DeleteMessageByMessageId(int id){ 
        boolean valid = messageRepository.existsById(id);
        if(valid){
            messageRepository.deleteById(id);
            return 1;
        }
        return 0;

     }

    
    //Utility
    boolean validateMessage(String message, int postedBy){
        boolean[] passes = new boolean[4];

        passes[0] = message.length() < 255;
        passes[1] = message != null;
        passes[2] = message != "";
        passes[3] = accountRepository.findByAccountId(postedBy) != null;

        for(boolean pass : passes){
            if(!pass){ return false; }
        }
        
        return true;
    }
    boolean validateMessage(String message){
        boolean[] passes = new boolean[3];

        passes[0] = message.length() < 255;
        passes[1] = message != null;
        passes[2] = message != "";

        for(boolean pass : passes){
            if(!pass){ return false; }
        }
        
        return true;
    }

}
