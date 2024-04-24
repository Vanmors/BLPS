package com.email.service.emailservice.service;

import com.email.service.emailservice.EmailMessage;
import com.email.service.emailservice.repository.EmailRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class EmailService {

    @Autowired
    private EmailRepository repository;

    public EmailMessage create(EmailMessage emailMessage) {
        return repository.save(emailMessage);
    }

    @Scheduled(fixedRate = 5000)
    public void getEmails() {
        List<EmailMessage> emails = repository.findAll();
        for (EmailMessage email: emails) {
            System.out.println(email);
        }
    }
}
