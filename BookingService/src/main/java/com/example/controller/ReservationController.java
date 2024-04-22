package com.example.lab1.controller;

import com.example.lab1.dto.CustomerDTO;
import com.example.lab1.dto.ReservationDTO;
import com.example.lab1.entity.Customer;
import com.example.lab1.entity.Reservation;
import com.example.lab1.service.CustomerService;
import com.example.lab1.service.ReservationService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@AllArgsConstructor
@RequestMapping("/reservation")
public class ReservationController {
    private final ReservationService reservationService;
    private final CustomerService customerService;

    @PostMapping("beginning")
    public ResponseEntity<Reservation> startToChooseHotelRoom(@RequestBody ReservationDTO reservationDTO) {
        var reservation = reservationService.createTemporary(reservationDTO);
        reservationService.setTimer(reservation.getId());
        return new ResponseEntity<>(reservation, HttpStatus.OK);
    }

    @PostMapping("confirmation")
    @Transactional
    public ResponseEntity<Reservation> completeToChooseHotelRoom(@RequestBody CustomerDTO customerDTO) {
        var customer = customerService.create(customerDTO);
        var ans = reservationService.createCompleted(customerDTO.getReservationId(), customer.getId());
        return new ResponseEntity<>(ans, HttpStatus.OK);
    }
}
