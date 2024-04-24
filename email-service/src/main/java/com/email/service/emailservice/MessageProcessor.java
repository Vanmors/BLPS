package com.email.service.emailservice;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class MessageProcessor {

    @Autowired
    private EmailListener emailListener;

    public void processMessages() {
        EmailMessage emailMessage = new EmailMessage( );
        emailMessage.setEmail("email.com");
        emailListener.listener(emailMessage);
        System.out.println("Email handler works");
    }
}

