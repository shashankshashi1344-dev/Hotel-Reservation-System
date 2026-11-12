
package com.hotel.hotelreservation;

import com.hotel.hotelreservation.repository.RoomRepository;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.time.LocalDate;
import java.util.List;

@Controller
public class RoomController {

    private final RoomRepository roomRepository;

    public RoomController(RoomRepository roomRepository) {
        this.roomRepository = roomRepository;
    }

    @GetMapping("/rooms")
    public String searchRooms(
            @RequestParam(required = false) String checkIn,
            @RequestParam(required = false) String checkOut,
            @RequestParam(defaultValue = "1") Integer guests,
            Model model) {

        if (checkIn == null || checkOut == null
                || checkIn.isBlank() || checkOut.isBlank()) {
            model.addAttribute("error",
                    "Please select check-in and check-out dates.");
            return "rooms";
        }

        try {
            LocalDate arrival = LocalDate.parse(checkIn);
            LocalDate departure = LocalDate.parse(checkOut);
            LocalDate today = LocalDate.now();

            if (arrival.isBefore(today)
                    || !departure.isAfter(arrival)
                    || guests == null
                    || guests < 1
                    || guests > 20) {
                model.addAttribute("error",
                        "Please enter valid dates and guest details.");
                return "rooms";
            }

            List<Room> rooms = roomRepository.findAvailableRooms(
                    "Available", guests, arrival, departure);

            model.addAttribute("rooms", rooms);
            model.addAttribute("checkIn", checkIn);
            model.addAttribute("checkOut", checkOut);
            model.addAttribute("guests", guests);

        } catch (Exception e) {
            model.addAttribute("error",
                    "Unable to search rooms. Please check your dates and try again.");
            return "rooms";
        }

        return "rooms";
    }
}
