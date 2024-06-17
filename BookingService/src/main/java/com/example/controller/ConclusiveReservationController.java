package com.example.controller;

import com.example.dto.CustomerDTO;
import com.example.dto.ReservationDTO;
import com.example.entity.Customer;
import com.example.entity.Reservation;
import com.example.service.CustomerService;
import com.example.service.ReservationService;
import lombok.AllArgsConstructor;
import org.camunda.bpm.engine.delegate.BpmnError;
import org.camunda.bpm.engine.delegate.DelegateExecution;
import org.camunda.bpm.engine.delegate.JavaDelegate;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDate;
import java.util.Date;

@RestController
@AllArgsConstructor
@RequestMapping("/reservation")
public class ConclusiveReservationController implements JavaDelegate {
    private final ReservationService reservationService;
    private final CustomerService customerService;

    @PostMapping("confirmation")
    @Transactional
    public ResponseEntity<Reservation> completeToChooseHotelRoom(@RequestBody CustomerDTO customerDTO) {
        var customer = customerService.create(customerDTO);
        var ans = reservationService.createCompleted(customerDTO.getReservationId(), customer.getId(), customer.getNumberOfCard());
        return new ResponseEntity<>(ans, HttpStatus.OK);
    }

    @Override
    @Transactional
    public void execute(DelegateExecution delegateExecution){
        /*
        surname
        name
        email
        phoneNumber
        nation
        numberOfCard
         */
        Long reservationId = (Long) delegateExecution.getVariable("reservationId");
        String name = (String) delegateExecution.getVariable("name");
        String surname = (String) delegateExecution.getVariable("surname");
        String email = (String) delegateExecution.getVariable("email");
        String phoneNumber = (String) delegateExecution.getVariable("phoneNumber");
        String nation = (String) delegateExecution.getVariable("nation");
        String numberOfCard = (String) delegateExecution.getVariable("numberOfCard");
        CustomerDTO customerDTO = CustomerDTO.builder().
                surname(surname).
                name(name).
                email(email).
                phoneNumber(phoneNumber).
                nation(nation).
                numberOfCard(numberOfCard).
                reservationId(reservationId).
                build();
        var customer = customerService.create(customerDTO);
        reservationService.createCompletedForCamunda(customerDTO.getReservationId(), customer.getId(), customer.getNumberOfCard());

    }
}
