package com.example.lab1.controller;

import com.example.lab1.entity.HotelNumber;
import com.example.lab1.service.HotelNumberService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDate;
import java.util.List;

@RestController
@AllArgsConstructor
@RequestMapping("/hotel")
public class HotelController {

    private final HotelNumberService hotelService;

    @GetMapping()
    public ResponseEntity<List<HotelNumber>> getHotels(@RequestParam LocalDate dataBegin,
                                                       @RequestParam LocalDate dataEnd,
                                                       @RequestParam String city) {

        return new ResponseEntity<>(hotelService.getAvailableRooms(city, dataBegin, dataEnd), HttpStatus.OK);
    }
}