
package com.hotel.hotelreservation;

import com.hotel.hotelreservation.repository.BookingRepository;
import com.hotel.hotelreservation.repository.PaymentRepository;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class PaymentController {

    private final BookingRepository bookingRepository;
    private final PaymentRepository paymentRepository;

    public PaymentController(
        BookingRepository bookingRepository,
        PaymentRepository paymentRepository) {
    this.bookingRepository = bookingRepository;
    this.paymentRepository = paymentRepository;
    }

    @GetMapping("/payment")
    public String showPaymentPage(
            @RequestParam Integer bookingId,
            Model model) {

        Booking booking = bookingRepository.findById(bookingId)
                .orElse(null);

        if (booking == null) {
            model.addAttribute("error", "Booking not found.");
            return "payment";
        }

        if ("Cancelled".equalsIgnoreCase(booking.getBookingStatus())) {
            model.addAttribute("error",
                    "Payment cannot be made for a cancelled booking.");
            return "payment";
        }

        model.addAttribute("booking", booking);
        model.addAttribute("canPay",
                paymentRepository
                        .findByBookingIdOrderByPaymentIdDesc(bookingId)
                        .isEmpty());
        return "payment";
    }
}
