
package com.hotel.hotelreservation;

import com.hotel.hotelreservation.repository.BookingRepository;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
public class BookingCancellationController {

    private final BookingRepository bookingRepository;

    public BookingCancellationController(
            BookingRepository bookingRepository) {
        this.bookingRepository = bookingRepository;
    }

    @PostMapping("/booking/cancel")
    public String cancelBooking(
            @RequestParam Integer bookingId,
            RedirectAttributes redirectAttributes) {

        Booking booking = bookingRepository.findById(bookingId)
                .orElse(null);

        if (booking == null) {
            redirectAttributes.addFlashAttribute(
                    "message", "Booking not found.");
            return "redirect:/my-bookings";
        }

        if ("Cancelled".equalsIgnoreCase(booking.getBookingStatus())) {
            redirectAttributes.addFlashAttribute(
                    "message", "This booking is already cancelled.");
            return "redirect:/my-bookings";
        }

        booking.setBookingStatus("Cancelled");
        bookingRepository.save(booking);

        redirectAttributes.addFlashAttribute(
                "message", "Booking cancelled successfully.");

        return "redirect:/my-bookings";
    }
}
