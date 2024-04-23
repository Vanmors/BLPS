package com.example.controller;


import com.example.config.RabbitConfig;
import com.example.entity.EmailMessage;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import javax.jms.JMSConnectionFactory;

@RestController
public class EmailController {

    @Autowired
    RabbitTemplate rabbitTemplate;


    @PostMapping("/publish")
    public String publishMessage(@RequestBody EmailMessage email) {
        System.out.println(email);
//        email.setEmail("helooo");
        rabbitTemplate.convertAndSend(RabbitConfig.EXCHANGE, RabbitConfig.ROUTING_KEY, email);
        return "Message published";
    }
}
