
package com.hotel.hotelreservation;

import com.hotel.hotelreservation.repository.RoomRepository;
import com.hotel.hotelreservation.repository.BookingRepository;
import com.hotel.hotelreservation.repository.PaymentRepository;
import com.hotel.hotelreservation.repository.GuestRepository;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class DashboardController {

    private final RoomRepository roomRepository;
    private final BookingRepository bookingRepository;
    private final PaymentRepository paymentRepository;
    private final GuestRepository guestRepository;

    public DashboardController(
            RoomRepository roomRepository,
            BookingRepository bookingRepository,
            PaymentRepository paymentRepository,
            GuestRepository guestRepository) {
        this.roomRepository = roomRepository;
        this.bookingRepository = bookingRepository;
        this.paymentRepository = paymentRepository;
        this.guestRepository = guestRepository;
    }

    @GetMapping("/dashboard")
    public String showDashboard(Model model) {
        var rooms = roomRepository.findAll();
        var bookings = bookingRepository.findAll();
        var payments = paymentRepository.findAll();
        var guests = guestRepository.findAll();

        long availableRooms = rooms.stream()
                .filter(room -> "Available".equalsIgnoreCase(room.getRoomStatus()))
                .count();

        long confirmedBookings = bookings.stream()
                .filter(booking -> "Confirmed".equalsIgnoreCase(booking.getBookingStatus()))
                .count();

        double simulatedPayments = payments.stream()
                .filter(payment -> "Successful".equalsIgnoreCase(payment.getPaymentStatus()))
                .mapToDouble(payment -> payment.getAmount())
                .sum();

        model.addAttribute("totalRooms", rooms.size());
        model.addAttribute("availableRooms", availableRooms);
        model.addAttribute("totalBookings", bookings.size());
        model.addAttribute("confirmedBookings", confirmedBookings);
        model.addAttribute("totalPayments", simulatedPayments);
        model.addAttribute("totalGuests", guests.size());

        return "dashboard";
    }
}
