package com.example.dto;

import lombok.Data;

@Data
public class CustomerDTO {

    private String surname;
    private String name;
    private String email;
    private String phoneNumber;
    private String nation;

    private String numberOfCard;
    private Long reservationId; // hotelId -> pay_amount
}
