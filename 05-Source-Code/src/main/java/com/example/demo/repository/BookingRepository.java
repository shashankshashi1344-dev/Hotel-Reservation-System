
package com.hotel.hotelreservation.repository;

import com.hotel.hotelreservation.Booking;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface BookingRepository extends JpaRepository<Booking, Integer> {

    List<Booking> findAllByOrderByBookingIdDesc();

    List<Booking> findByBookingStatusOrderByBookingIdDesc(String bookingStatus);

    boolean existsByRoomId(Integer roomId);

    @Query(value = """
        SELECT
            b.booking_id AS bookingId,
            g.full_name AS guestName,
            r.room_number AS roomNumber,
            b.check_in AS checkIn,
            b.check_out AS checkOut,
            b.number_of_guests AS numberOfGuests,
            b.total_amount AS totalAmount,
            b.booking_status AS bookingStatus,
            b.stay_status AS stayStatus
        FROM bookings b
        JOIN guests g ON b.guest_id = g.guest_id
        JOIN rooms r ON b.room_id = r.room_id
        ORDER BY b.booking_id DESC
        """, nativeQuery = true)
    List<Object[]> findAdminBookingDetails();

    @Query(value = """
        SELECT
            b.booking_id AS bookingId,
            g.full_name AS guestName,
            r.room_number AS roomNumber,
            b.check_in AS checkIn,
            b.check_out AS checkOut,
            b.number_of_guests AS numberOfGuests,
            b.total_amount AS totalAmount,
            b.booking_status AS bookingStatus,
            b.stay_status AS stayStatus
        FROM bookings b
        JOIN guests g ON b.guest_id = g.guest_id
        JOIN rooms r ON b.room_id = r.room_id
        WHERE b.booking_status = :status
        ORDER BY b.booking_id DESC
        """, nativeQuery = true)
    List<Object[]> findAdminBookingDetailsByStatus(
            @Param("status") String status);
}
