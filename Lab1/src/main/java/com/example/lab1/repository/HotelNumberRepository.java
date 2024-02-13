package com.example.lab1.repository;

import com.example.lab1.entity.HotelNumber;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;

@Repository
public interface HotelNumberRepository extends JpaRepository<HotelNumber, Long> {
    @Query("SELECT h FROM HotelNumber h " +
            "JOIN Reservation r ON h.id = r.hotelId " +
            "WHERE h.city = :city AND (r.dateBegin > :dateBegin OR r.dateEnd < :dateEnd)")
    List<HotelNumber> findAvailableRooms(@Param("city") String city,
                                         @Param("dateBegin") LocalDate dateBegin,
                                         @Param("dateEnd") LocalDate dateEnd);
}