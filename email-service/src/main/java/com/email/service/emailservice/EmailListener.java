package com.email.service.emailservice;

import com.email.service.emailservice.configuration.RabbitConfig;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.mail.MailException;
import org.springframework.stereotype.Component;

import java.util.Arrays;

@Component
public class EmailListener {


    @Autowired
    DefaultEmailService emailService;
//    @Scheduled(fixedRate = 5000)
    @RabbitListener(queues = RabbitConfig.QUEUE)
//    @JmsListener(destination = RabbitConfig.QUEUE)
    public ResponseEntity<String> listener(EmailMessage email) {
        try {
            emailService.sendSimpleEmail(email.getEmail(), "Welcome", "This is a welcome email for your!!");
        } catch (MailException mailException) {
            System.out.println(Arrays.toString(mailException.getStackTrace()));
            return new ResponseEntity<>("Unable to send email", HttpStatus.INTERNAL_SERVER_ERROR);
        }

        System.out.println(email);
        return new ResponseEntity<>("Mail sended", HttpStatus.OK);
    }
}
