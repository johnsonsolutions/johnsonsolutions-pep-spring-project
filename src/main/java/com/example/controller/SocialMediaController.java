package com.example.controller;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
//import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.*;

import com.example.entity.Account;
import com.example.entity.Message;

/**
 * TODO: You will need to write your own endpoints and handlers for your controller using Spring. The endpoints you will need can be
 * found in readme.md as well as the test cases. You be required to use the @GET/POST/PUT/DELETE/etc Mapping annotations
 * where applicable as well as the @ResponseBody and @PathVariable annotations. You should
 * refer to prior mini-project labs and lecture materials for guidance on how a controller may be built.
 */
@RestController
@RequestMapping("")
public class SocialMediaController {

    @PutMapping("messages")
    @ResponseStatus(HttpStatus.OK)
    public @ResponseBody String createMessage(@RequestBody Message updatedMessage){
        return updatedMessage.getMessageText();
    }
    @DeleteMapping("messages/{messageId}")
    @ResponseStatus(HttpStatus.OK)
    public @ResponseBody int deleteMessage(@PathVariable String messageId){
        return 0;
    }
    @GetMapping("messages/{userId}")
    @ResponseStatus(HttpStatus.OK)
    public @ResponseBody List<Message> getAllUserMessages(@PathVariable String userId){
        return null;    //was supposed to be get; needs validation, should return rows affected
    }
    @GetMapping("messages")
    @ResponseStatus(HttpStatus.OK)
    public @ResponseBody List<Message> getAllMessages(@RequestBody List<Message> messages){
        return messages;
    }
    @GetMapping("messages/{messageId}")
    @ResponseStatus(HttpStatus.OK)
    public @ResponseBody Message getMessageById(@PathVariable String messageId, @RequestBody Message message){
        return message;
    }
    @PatchMapping("messages/{messageId}")
    @ResponseStatus(HttpStatus.OK)
    public @ResponseBody int updateMessage(@PathVariable Message message){
        return 0;    // needs validation, should return rows affected
    }
    @GetMapping("login")
    @ResponseStatus(HttpStatus.OK)
    public @ResponseBody Account login(@RequestBody Account account){
        return account;
    }
    @PostMapping("register")
    @ResponseStatus(HttpStatus.OK)
    public @ResponseBody Account register(@RequestBody Account account){
        return account;
    }

    /*
    @ExceptionHandler({RuntimeException.class, CustomException.class})
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public @ResponseBody String handleNotFound(RuntimeException ex){
        return ex.getMessage();
    }

    @ExceptionHandler(MissingServletRequestParameterException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public @ResponseBody String handleMissingParams(MissingServletRequestParameterException ex) {
        return ex.getParameterName() + " is missing in the query parameters and is required.";
    }

    */
}
