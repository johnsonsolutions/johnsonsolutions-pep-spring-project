package com.example.service;

import java.util.*;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.repository.MessageRepository;
import com.example.entity.Message;

@Service
public class MessageService {
    private MessageRepository messageRepository;

    @Autowired
    public MessageService(){
        this.messageRepository = messageRepository;
    }

    public void CreateMessage(Message message){ messageRepository.save(message); }

    public List<Message> RetrieveAllMessages(){ return (List<Message>) messageRepository.findAll(); }

    public List<Message> RetrieveAllMessagesForUser(String id){         
        return (List<Message>) messageRepository.findByUserId(id).orElseThrow();
    }

    public Message RetrieveMessageByMessageId(String id){ return (Message) messageRepository.findById(id).orElseThrow(); }

    public void UpdateMessage(String id, String message){ 
        if(messageRepository.existsById(id) && (message.length() <= 255)){
            Message foundMessage = (Message) messageRepository.findById(id).orElseThrow();
            foundMessage.setMessageText(message);
            messageRepository.save(foundMessage);
        }
        else{ System.out.println("update fail"); }
    }
    
    public void DeleteMessageByMessageId(String id){ messageRepository.deleteById(id); }

    

}
