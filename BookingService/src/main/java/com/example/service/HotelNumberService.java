package com.example.service;

import com.example.dto.HotelNumberDTO;
import com.example.entity.HotelNumber;
import com.example.repository.HotelNumberRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

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
                .payment(hotelNumberDTO.getPayment())
                .build();
        return repository.save(hotel);
    }

    public Optional<HotelNumber> findById(Long id){
        return repository.findById(id);
    }
}
