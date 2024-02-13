package com.example.lab1.controller;

import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@AllArgsConstructor
@RequestMapping("/hotel")
public class HotelController {

    private final HotelService hotelService;

    @GetMapping()
    public ResponseEntity<List<Hotel>> getHotels(@RequestParam int dataBegin,
                                           @RequestParam int dataEnd,
                                           @RequestParam String city) {

        return hotelService.findAll();
    }
}
