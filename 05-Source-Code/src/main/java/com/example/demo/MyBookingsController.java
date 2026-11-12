
package com.hotel.hotelreservation;

import com.hotel.hotelreservation.repository.BookingRepository;
import com.hotel.hotelreservation.repository.PaymentRepository;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class MyBookingsController {

    private final BookingRepository bookingRepository;
    private final PaymentRepository paymentRepository;

    public MyBookingsController(
            BookingRepository bookingRepository,
            PaymentRepository paymentRepository) {
        this.bookingRepository = bookingRepository;
        this.paymentRepository = paymentRepository;
    }

    @GetMapping("/my-bookings")
    public String showMyBookings(Model model) {

        var bookings =
                bookingRepository.findAllByOrderByBookingIdDesc();

        model.addAttribute("bookings", bookings);

        model.addAttribute("paymentRepository", paymentRepository);

        return "my-bookings";
    }
}
