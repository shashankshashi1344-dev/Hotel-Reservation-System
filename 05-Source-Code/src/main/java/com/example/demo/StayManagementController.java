
package com.hotel.hotelreservation;

import com.hotel.hotelreservation.repository.BookingRepository;
import com.hotel.hotelreservation.repository.RoomRepository;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
public class StayManagementController {

    private final BookingRepository bookingRepository;
    private final RoomRepository roomRepository;

    public StayManagementController(
            BookingRepository bookingRepository,
            RoomRepository roomRepository) {
        this.bookingRepository = bookingRepository;
        this.roomRepository = roomRepository;
    }

    @PostMapping("/admin/stay/check-in")
    public String checkIn(
            @RequestParam Integer bookingId,
            RedirectAttributes redirectAttributes) {

        var bookingOptional = bookingRepository.findById(bookingId);

        if (bookingOptional.isEmpty()) {
            redirectAttributes.addFlashAttribute(
                    "errorMessage", "Booking not found.");
            return "redirect:/admin/bookings";
        }

        Booking booking = bookingOptional.get();

        if ("Cancelled".equalsIgnoreCase(booking.getBookingStatus())) {
            redirectAttributes.addFlashAttribute(
                    "errorMessage", "Cancelled bookings cannot be checked in.");
            return "redirect:/admin/bookings";
        }

        if ("Checked In".equals(booking.getStayStatus())) {
            redirectAttributes.addFlashAttribute(
                    "errorMessage", "This guest has already checked in.");
            return "redirect:/admin/bookings";
        }

        var roomOptional = roomRepository.findById(booking.getRoomId());

        if (roomOptional.isEmpty()) {
            redirectAttributes.addFlashAttribute(
                    "errorMessage", "Room not found.");
            return "redirect:/admin/bookings";
        }

        Room room = roomOptional.get();

        if (!"Available".equalsIgnoreCase(room.getRoomStatus())) {
            redirectAttributes.addFlashAttribute(
                    "errorMessage",
                    "This room is not available for check-in.");
            return "redirect:/admin/bookings";
        }

        booking.setStayStatus("Checked In");
        room.setRoomStatus("Occupied");

        bookingRepository.save(booking);
        roomRepository.save(room);

        redirectAttributes.addFlashAttribute(
                "successMessage", "Guest checked in successfully.");

        return "redirect:/admin/bookings";
    }

    @PostMapping("/admin/stay/check-out")
    public String checkOut(
            @RequestParam Integer bookingId,
            RedirectAttributes redirectAttributes) {

        var bookingOptional = bookingRepository.findById(bookingId);

        if (bookingOptional.isEmpty()) {
            redirectAttributes.addFlashAttribute(
                    "errorMessage", "Booking not found.");
            return "redirect:/admin/bookings";
        }

        Booking booking = bookingOptional.get();

        if (!"Checked In".equals(booking.getStayStatus())) {
            redirectAttributes.addFlashAttribute(
                    "errorMessage",
                    "Only checked-in guests can be checked out.");
            return "redirect:/admin/bookings";
        }

        var roomOptional = roomRepository.findById(booking.getRoomId());

        if (roomOptional.isEmpty()) {
            redirectAttributes.addFlashAttribute(
                    "errorMessage", "Room not found.");
            return "redirect:/admin/bookings";
        }

        Room room = roomOptional.get();

        booking.setStayStatus("Checked Out");

        if ("Occupied".equalsIgnoreCase(room.getRoomStatus())) {
            room.setRoomStatus("Available");
        }

        bookingRepository.save(booking);
        roomRepository.save(room);

        redirectAttributes.addFlashAttribute(
                "successMessage", "Guest checked out successfully.");

        return "redirect:/admin/bookings";
    }
}
