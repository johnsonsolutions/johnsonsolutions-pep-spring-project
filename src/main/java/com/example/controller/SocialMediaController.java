package com.example.controller;

import java.util.List;

import javax.websocket.server.PathParam;

import org.aspectj.apache.bcel.generic.RET;
import org.springframework.data.repository.query.Param;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
//import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.*;

import com.example.entity.Account;
import com.example.entity.Message;

import com.example.service.MessageService;

import net.bytebuddy.asm.Advice.Return;

import com.example.service.AccountService;

/**
 * TODO: You will need to write your own endpoints and handlers for your controller using Spring. The endpoints you will need can be
 * found in readme.md as well as the test cases. You be required to use the @GET/POST/PUT/DELETE/etc Mapping annotations
 * where applicable as well as the @ResponseBody and @PathVariable annotations. You should
 * refer to prior mini-project labs and lecture materials for guidance on how a controller may be built.
 */

@RestController
@RequestMapping("")
public class SocialMediaController {

    MessageService messageService;
    AccountService accountService;


    public SocialMediaController(MessageService messageService, AccountService accountService){
        this.messageService = messageService;
        this.accountService = accountService;
    }

    @PostMapping("messages")
    @ResponseStatus(HttpStatus.OK)
    public @ResponseBody ResponseEntity<Message> createMessage(@RequestBody Message message){
        Message message2 = messageService.save(message);

        if(message2 != null){ return new ResponseEntity<Message>(message2, HttpStatus.OK); }
        
        return new ResponseEntity<Message>(HttpStatus.BAD_REQUEST);
    }
    @DeleteMapping("messages/{messageId}")
    @ResponseStatus(HttpStatus.OK)
    public @ResponseBody ResponseEntity<Integer> deleteMessage(@PathVariable int messageId){
        int rows = messageService.DeleteMessageByMessageId(messageId);
        return rows > 0 ? new ResponseEntity<Integer>(rows, HttpStatus.OK) : new ResponseEntity<Integer>(HttpStatus.OK);
    }
    @GetMapping("accounts/{accountId}/messages")
    @ResponseStatus(HttpStatus.OK)
    public @ResponseBody ResponseEntity<List<Message>> getAllUserMessages(@PathVariable int accountId){
        
        List<Message> messages = messageService.findAllByPostedBy(accountId);
        return new ResponseEntity<List<Message>>(messages, HttpStatus.OK);
    }
    @GetMapping("messages")
    @ResponseStatus(HttpStatus.OK)
    public @ResponseBody List<Message> getAllMessages(){
        return messageService.findAllMessages();
    }
    @GetMapping("messages/{messageId}")
    @ResponseStatus(HttpStatus.OK)
    public @ResponseBody ResponseEntity<Message> getMessageById(@PathVariable int messageId){
        try{
            Message message = messageService.getById(messageId);
            System.out.println("It's " + message);
            if (message != null) {
                return new ResponseEntity<Message>(message, HttpStatus.OK);
            }
        }
        catch(Exception ex){
            return new ResponseEntity<Message>(HttpStatus.OK);
        }
        return new ResponseEntity<Message>(HttpStatus.OK);
    }
    @PatchMapping("messages/{messageId}")
    @ResponseStatus(HttpStatus.OK)
    public @ResponseBody ResponseEntity<Integer> updateMessage(@PathVariable int messageId, @RequestBody Message message){
        int rows = messageService.UpdateMessage(messageId, message);
        if(rows == 1){
            return new ResponseEntity<Integer>(rows, HttpStatus.OK);
        }
        return new ResponseEntity<Integer>(HttpStatus.BAD_REQUEST);
    }
    @PostMapping("login")
    @ResponseStatus(HttpStatus.OK)
    //R.E. when changing status only
    public @ResponseBody ResponseEntity<Account> login(@RequestBody Account account){
        try{
            Account account2 = accountService.login(account);
            return new ResponseEntity<Account>(account2, HttpStatus.OK);
        }
        catch(Exception ex){
            return new ResponseEntity<Account>(HttpStatus.UNAUTHORIZED);
        }
    }
    @PostMapping("register")
    @ResponseStatus(HttpStatus.OK)
    public @ResponseBody ResponseEntity<Account> register(@RequestBody Account account){
        Account savedAccount = accountService.save(account);

        try{
            if(savedAccount != null){ return new ResponseEntity<Account>(savedAccount, HttpStatus.OK); }
        }
        catch(Exception exception){
            return new ResponseEntity<Account>(HttpStatus.BAD_REQUEST);
        }
        Account lAccount = accountService.login(savedAccount);
        if(lAccount != null){ return new ResponseEntity<Account>(HttpStatus.CONFLICT); }
        return new ResponseEntity<Account>(HttpStatus.BAD_REQUEST);
    }

}
