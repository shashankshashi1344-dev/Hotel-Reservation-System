
package com.hotel.hotelreservation;

import com.hotel.hotelreservation.repository.BookingRepository;
import com.hotel.hotelreservation.repository.GuestRepository;
import com.hotel.hotelreservation.repository.RoomRepository;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.time.LocalDate;
import java.time.temporal.ChronoUnit;

@Controller
public class BookingConfirmationController {

    private final BookingRepository bookingRepository;
    private final GuestRepository guestRepository;
    private final RoomRepository roomRepository;

    public BookingConfirmationController(
            BookingRepository bookingRepository,
            GuestRepository guestRepository,
            RoomRepository roomRepository) {
        this.bookingRepository = bookingRepository;
        this.guestRepository = guestRepository;
        this.roomRepository = roomRepository;
    }

    @PostMapping("/booking/confirm")
    public String confirmBooking(
            @RequestParam Integer roomId,
            @RequestParam String checkIn,
            @RequestParam String checkOut,
            @RequestParam Integer guests,
            @RequestParam String fullName,
            @RequestParam String email,
            @RequestParam String phone,
            @RequestParam String address,
            Model model,
            RedirectAttributes redirectAttributes) {

        var roomOptional = roomRepository.findById(roomId);

        if (roomOptional.isEmpty()) {
            redirectAttributes.addFlashAttribute(
                    "error", "Room not found.");
            return "redirect:/";
        }

        Room room = roomOptional.get();

        LocalDate arrival;
        LocalDate departure;

        try {
            arrival = LocalDate.parse(checkIn);
            departure = LocalDate.parse(checkOut);
        } catch (Exception e) {
            return showError(model, room, checkIn, checkOut,
                    guests, "Please enter valid booking dates.");
        }

        if (arrival.isBefore(LocalDate.now())
                || !departure.isAfter(arrival)) {
            return showError(model, room, checkIn, checkOut,
                    guests, "Please select valid check-in and check-out dates.");
        }

        if (guests == null || guests < 1
                || guests > room.getCapacity()) {
            return showError(model, room, checkIn, checkOut,
                    guests, "Invalid number of guests for this room.");
        }

        if (fullName == null || fullName.isBlank()
                || email == null || email.isBlank()
                || phone == null || phone.isBlank()
                || address == null || address.isBlank()) {
            return showError(model, room, checkIn, checkOut,
                    guests, "Please complete all guest details.");
        }

        if (!"Available".equalsIgnoreCase(room.getRoomStatus())) {
            return showError(model, room, checkIn, checkOut,
                    guests, "This room is not currently available.");
        }

        boolean alreadyBooked = bookingRepository.findAll().stream()
                .filter(b -> b.getRoomId().equals(roomId))
                .filter(b -> "Confirmed".equalsIgnoreCase(
                        b.getBookingStatus()))
                .anyMatch(b ->
                        arrival.isBefore(b.getCheckOut())
                        && departure.isAfter(b.getCheckIn()));

        if (alreadyBooked) {
            return showError(model, room, checkIn, checkOut,
                    guests, "This room is already booked for those dates.");
        }

        Guest guest = guestRepository.findByEmail(email.trim())
                .orElse(null);

        if (guest == null) {
            guest = new Guest();
            guest.setFullName(fullName.trim());
            guest.setEmail(email.trim());
            guest.setPhone(phone.trim());
            guest.setAddress(address.trim());
            guest = guestRepository.save(guest);
        } else {
            guest.setFullName(fullName.trim());
            guest.setPhone(phone.trim());
            guest.setAddress(address.trim());
            guest = guestRepository.save(guest);
        }

        long nights = ChronoUnit.DAYS.between(arrival, departure);
        double totalAmount = nights * room.getPricePerNight();

        Booking booking = new Booking();
        booking.setGuestId(guest.getGuestId());
        booking.setRoomId(room.getRoomId());
        booking.setCheckIn(arrival);
        booking.setCheckOut(departure);
        booking.setNumberOfGuests(guests);
        booking.setTotalAmount(totalAmount);
        booking.setBookingStatus("Confirmed");
        booking.setStayStatus("Not Checked In");

        booking = bookingRepository.save(booking);

        model.addAttribute("booking", booking);
        model.addAttribute("guest", guest);
        model.addAttribute("room", room);

        return "confirmation";
    }

    private String showError(
            Model model,
            Room room,
            String checkIn,
            String checkOut,
            Integer guests,
            String message) {

        model.addAttribute("room", room);
        model.addAttribute("checkIn", checkIn);
        model.addAttribute("checkOut", checkOut);
        model.addAttribute("guests", guests);
        model.addAttribute("error", message);

        return "booking";
    }
}

