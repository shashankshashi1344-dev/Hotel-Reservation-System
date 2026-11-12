
package com.hotel.hotelreservation;

import com.hotel.hotelreservation.repository.BookingRepository;
import com.hotel.hotelreservation.repository.GuestRepository;
import com.hotel.hotelreservation.repository.RoomRepository;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@Controller
public class BookingDetailsController {

    private final BookingRepository bookingRepository;
    private final GuestRepository guestRepository;
    private final RoomRepository roomRepository;

    public BookingDetailsController(
            BookingRepository bookingRepository,
            GuestRepository guestRepository,
            RoomRepository roomRepository) {
        this.bookingRepository = bookingRepository;
        this.guestRepository = guestRepository;
        this.roomRepository = roomRepository;
    }

    @GetMapping("/admin/bookings/details/{id}")
    public String showBookingDetails(
            @PathVariable Integer id,
            Model model) {

        Booking booking = bookingRepository.findById(id)
                .orElse(null);

        if (booking == null) {
            return "redirect:/admin/bookings";
        }

        Guest guest = guestRepository.findById(booking.getGuestId())
                .orElse(null);

        Room room = roomRepository.findById(booking.getRoomId())
                .orElse(null);

        model.addAttribute("booking", booking);
        model.addAttribute("guest", guest);
        model.addAttribute("room", room);

        return "booking-details";
    }
}
