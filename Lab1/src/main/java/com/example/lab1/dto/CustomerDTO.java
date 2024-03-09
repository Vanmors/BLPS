package com.example.lab1.dto;

import lombok.Data;

@Data
public class CustomerDTO {

    private String surname;
    private String name;
    private String email;
    private String phoneNumber;
    private String nation;

    private Long numberOfCard;
}
