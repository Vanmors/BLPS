package com.example.repository;

import com.example.entity.HotelNumber;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;

@Repository
public interface HotelNumberRepository extends JpaRepository<HotelNumber, Long> {
    @Query("SELECT h FROM HotelNumber h " +
            "LEFT JOIN Reservation r ON h.id = r.hotelId " +
            "WHERE h.city = :city AND ((r.dateBegin > :dateBegin OR r.dateEnd < :dateEnd) OR (r.dateBegin is null AND r.dateEnd is null))")
    List<HotelNumber> findAvailableRooms(@Param("city") String city,
                                         @Param("dateBegin") LocalDate dateBegin,
                                         @Param("dateEnd") LocalDate dateEnd);
}