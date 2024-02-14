package com.example.lab1.controller;

import com.example.lab1.dto.ReservationDTO;
import com.example.lab1.entity.HotelNumber;
import com.example.lab1.entity.Reservation;
import com.example.lab1.service.HotelNumberService;
import com.example.lab1.service.ReservationService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;

@RestController
@AllArgsConstructor
@RequestMapping("/hotel")
public class HotelController {

    private final HotelNumberService hotelService;
    private final ReservationService reservationService;

    @GetMapping()
    public ResponseEntity<List<HotelNumber>> getHotels(@RequestParam LocalDate dataBegin,
                                                       @RequestParam LocalDate dataEnd,
                                                       @RequestParam String city) {
        var hotels = hotelService.getAvailableRooms(city, dataBegin, dataEnd);
        if (hotels == null || hotels.size() == 0) {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }
        return new ResponseEntity<>(hotelService.getAvailableRooms(city, dataBegin, dataEnd), HttpStatus.OK);
    }

    @PostMapping()
    public ResponseEntity<Reservation> chooseHotelRoom(@RequestBody ReservationDTO reservationDTO) {
        return new ResponseEntity<>(reservationService.create(reservationDTO), HttpStatus.OK);
    }
}
