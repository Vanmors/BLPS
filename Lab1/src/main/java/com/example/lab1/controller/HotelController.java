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
import java.util.Timer;
import java.util.TimerTask;

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
    public ResponseEntity<Reservation> startToChooseHotelRoom(@RequestBody ReservationDTO reservationDTO) {
        var reservation = reservationService.createTemporary(reservationDTO);
        reservationService.setTimer(reservation.getId());
        return new ResponseEntity<>(reservation, HttpStatus.OK);
    }

//    @PostMapping()
//    public ResponseEntity<Reservation> completeToChooseHotelRoom(@RequestBody ReservationDTO reservationDTO) {
//        //todo переделать.
//        return new ResponseEntity<>(reservationService.createCompleted(reservationDTO), HttpStatus.OK)
//    }
}
