package com.email.service.emailservice;

import com.email.service.emailservice.configuration.RabbitConfig;
import com.email.service.emailservice.service.EmailService;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;


@Component
public class EmailListener {


    @Autowired
    private EmailService service;
    @RabbitListener(queues = RabbitConfig.QUEUE)
    public void listener(EmailMessage email) {
        System.out.println(email);
        service.create(email);

    }
}
