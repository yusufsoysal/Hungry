package com.yusufsoysal.slack.hungry.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.stereotype.Service;

import java.util.Locale;

@Service
public class MessageService {


    @Autowired
    private MessageSource messageSource;

    public String getMessage(String messageId){
        return getMessage(messageId, null);
    }

    public String getMessage(String messageId, Object ... args){
        return messageSource.getMessage(messageId, args, Locale.getDefault());
    }

}
