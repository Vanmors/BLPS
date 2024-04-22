package com.example.lab1.service;

import com.example.lab1.dto.HotelNumberDTO;
import com.example.lab1.entity.HotelNumber;
import com.example.lab1.repository.HotelNumberRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;

@Service
@AllArgsConstructor
public class HotelNumberService {
    private final HotelNumberRepository repository;

    //параметры: date_begin, date_end, city
    //SELECT * from hotel_number AS h JOIN reservation AS r ON h.id = r.hotel_id WHERE h.city = $city AND
    // (r.date_begin > $date_begin OR r.date_end < $date_end)

    public List<HotelNumber> getAvailableRooms(String city, LocalDate dateBegin, LocalDate dateEnd) {
        return repository.findAvailableRooms(city, dateBegin, dateEnd);
    }

    public HotelNumber create(HotelNumberDTO hotelNumberDTO) {
        HotelNumber hotel = HotelNumber.builder()
                .hotelName(hotelNumberDTO.getHotelName())
                .city(hotelNumberDTO.getCity())
                .square(hotelNumberDTO.getSquare())
                .rooms(hotelNumberDTO.getRooms())
                .build();
        return repository.save(hotel);
    }
}
