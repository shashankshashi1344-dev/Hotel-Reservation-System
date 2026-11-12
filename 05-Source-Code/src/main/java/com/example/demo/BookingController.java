
package com.hotel.hotelreservation;

import com.hotel.hotelreservation.repository.RoomRepository;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.time.LocalDate;

@Controller
public class BookingController {

    private final RoomRepository roomRepository;

    public BookingController(RoomRepository roomRepository) {
        this.roomRepository = roomRepository;
    }

    @GetMapping("/booking/new")
    public String showBookingForm(
            @RequestParam Integer roomId,
            @RequestParam String checkIn,
            @RequestParam String checkOut,
            @RequestParam Integer guests,
            Model model) {

        var room = roomRepository.findById(roomId);

        if (room.isEmpty()) {
            model.addAttribute("error", "Room not found.");
            return "booking";
        }

        LocalDate arrival = LocalDate.parse(checkIn);
        LocalDate departure = LocalDate.parse(checkOut);

        if (arrival.isBefore(LocalDate.now())
                || !departure.isAfter(arrival)
                || guests < 1
                || guests > room.get().getCapacity()) {
            model.addAttribute("error", "Invalid booking details.");
            return "booking";
        }

        model.addAttribute("room", room.get());
        model.addAttribute("checkIn", checkIn);
        model.addAttribute("checkOut", checkOut);
        model.addAttribute("guests", guests);

        return "booking";
    }
}
