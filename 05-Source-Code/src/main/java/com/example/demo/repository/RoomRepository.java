
package com.hotel.hotelreservation.repository;

import com.hotel.hotelreservation.Room;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDate;
import java.util.List;

public interface RoomRepository extends JpaRepository<Room, Integer> {

    @Query("""
        SELECT r FROM Room r
        WHERE LOWER(r.roomStatus) = LOWER(:roomStatus)
          AND r.capacity >= :guests
          AND NOT EXISTS (
              SELECT b FROM Booking b
              WHERE b.roomId = r.roomId
                AND b.bookingStatus = 'Confirmed'
                AND b.checkIn < :checkOut
                AND b.checkOut > :checkIn
          )
        """)
    List<Room> findAvailableRooms(
        @Param("roomStatus") String roomStatus,
        @Param("guests") Integer guests,
        @Param("checkIn") LocalDate checkIn,
        @Param("checkOut") LocalDate checkOut
    );
}
