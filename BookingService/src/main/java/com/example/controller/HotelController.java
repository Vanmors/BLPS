package com.example.controller;

import com.example.dto.HotelNumberDTO;
import com.example.entity.HotelNumber;
import com.example.service.HotelNumberService;
import jakarta.inject.Named;
import lombok.AllArgsConstructor;
import org.camunda.bpm.engine.delegate.DelegateExecution;
import org.camunda.bpm.engine.delegate.JavaDelegate;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.time.Instant;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@RestController
@AllArgsConstructor
@RequestMapping("/hotel")
@Named
public class HotelController implements JavaDelegate {


    private final HotelNumberService hotelService;

    //http://localhost:8081/hotel?dataBegin=2024-03-12&dataEnd=2024-03-15&city=Paris
    @GetMapping
    public ResponseEntity<List<HotelNumber>> getHotels(@RequestParam("dataBegin") LocalDate dataBegin,
                                                       @RequestParam("dataEnd") LocalDate dataEnd,
                                                       @RequestParam("city") String city) {
        var hotels = hotelService.getAvailableRooms(city, dataBegin, dataEnd);
        if (hotels == null || hotels.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }
        return new ResponseEntity<>(hotelService.getAvailableRooms(city, dataBegin, dataEnd), HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<HotelNumber> createHotel(@RequestBody HotelNumberDTO hotelNumberDTO) {
        return new ResponseEntity<>(hotelService.create(hotelNumberDTO), HttpStatus.OK);
    }

    @Override
    public void execute(DelegateExecution delegateExecution) throws Exception {
        String city = (String) delegateExecution.getVariable("city");
        LocalDate dataBegin = convertToLocalDate((Date) delegateExecution.getVariable("dataBegin"));
        LocalDate dataEnd =  convertToLocalDate((Date) delegateExecution.getVariable("dataEnd"));

        var hotels = hotelService.getAvailableRooms(city, dataBegin, dataEnd);
        if (hotels == null || hotels.isEmpty()) {
            //delegateExecution.setVariable("freeHotels", "Отелей нету");
            delegateExecution.setVariable("freeHotels", new ArrayList<HotelNumber>());
            return;
        }
        delegateExecution.setVariable("freeHotels", hotels);
    }

    public static LocalDate convertToLocalDate(Date date) {
        Instant instant = date.toInstant();

        return instant.atZone(ZoneId.systemDefault()).toLocalDate();
    }
}
