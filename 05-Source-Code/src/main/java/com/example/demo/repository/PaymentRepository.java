
package com.hotel.hotelreservation.repository;

import com.hotel.hotelreservation.Payment;
import com.hotel.hotelreservation.repository.PaymentRepository;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface PaymentRepository extends JpaRepository<Payment, Integer> {

    List<Payment> findByBookingIdOrderByPaymentIdDesc(Integer bookingId);
}
