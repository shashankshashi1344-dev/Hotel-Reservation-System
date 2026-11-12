
package com.hotel.hotelreservation;

import com.hotel.hotelreservation.repository.BookingRepository;
import com.hotel.hotelreservation.Payment;
import com.hotel.hotelreservation.repository.PaymentRepository;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.transaction.annotation.Transactional;
import java.time.LocalDateTime;
import java.util.UUID;

@Controller
public class PaymentProcessingController {

    private final BookingRepository bookingRepository;
    private final PaymentRepository paymentRepository;

    public PaymentProcessingController(
            BookingRepository bookingRepository,
            PaymentRepository paymentRepository) {
        this.bookingRepository = bookingRepository;
        this.paymentRepository = paymentRepository;
    }

    @PostMapping("/payment/process")
    @Transactional
    public String processPayment(
            @RequestParam Integer bookingId,
            @RequestParam String paymentMethod,
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

        if (!paymentMethod.equals("UPI")
                && !paymentMethod.equals("Card")
                && !paymentMethod.equals("Net Banking")
                && !paymentMethod.equals("Cash")) {
            model.addAttribute("booking", booking);
            model.addAttribute("error", "Please select a valid payment method.");
            return "payment";
        }
        
        if (!paymentRepository
                .findByBookingIdOrderByPaymentIdDesc(bookingId)
                .isEmpty()) {

            model.addAttribute("booking", booking);
            model.addAttribute(
                "error",
                "A payment record already exists for this booking."
            );

            return "payment";
        }

        Payment payment = new Payment();
        payment.setBookingId(booking.getBookingId());
        payment.setAmount(booking.getTotalAmount());
        payment.setPaymentMethod(paymentMethod);
        payment.setPaymentStatus("Successful");
        payment.setTransactionId("DEMO-" + UUID.randomUUID());
        payment.setPaymentDate(LocalDateTime.now());

        Payment savedPayment = paymentRepository.save(payment);

        model.addAttribute("payment", savedPayment);
        model.addAttribute("booking", booking);

        return "payment-success";
    }
}
