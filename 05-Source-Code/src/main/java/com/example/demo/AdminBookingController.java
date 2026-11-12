
package com.hotel.hotelreservation;

import com.hotel.hotelreservation.repository.BookingRepository;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class AdminBookingController {

    private final BookingRepository bookingRepository;

    public AdminBookingController(BookingRepository bookingRepository) {
        this.bookingRepository = bookingRepository;
    }

    @GetMapping("/admin/bookings")
    public String showBookings(
            @RequestParam(required = false) String status,
            Model model) {

        if (status == null || status.isBlank()
                || status.equalsIgnoreCase("All")) {

            model.addAttribute(
                    "bookings",
                    bookingRepository.findAdminBookingDetails());

            model.addAttribute("selectedStatus", "All");

        } else {

            model.addAttribute(
                    "bookings",
                    bookingRepository.findAdminBookingDetailsByStatus(status));

            model.addAttribute("selectedStatus", status);
        }

        return "admin-bookings";
    }
}
