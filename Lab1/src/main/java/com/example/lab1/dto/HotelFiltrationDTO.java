package com.example.lab1.dto;

import lombok.Data;

import java.time.LocalDate;

@Data
public class HotelFiltrationDTO {
    private LocalDate dateBegin;
    private LocalDate dateEnd;
    private String city;
}