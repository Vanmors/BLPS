package com.email.service.emailservice;

import com.email.service.emailservice.configuration.RabbitConfig;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

@Component
public class EmailListener {

    @RabbitListener(queues = RabbitConfig.QUEUE)
    public void listener(EmailMessage email) {
        System.out.println(email);
    }
}
