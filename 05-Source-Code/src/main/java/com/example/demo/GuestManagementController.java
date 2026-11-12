
package com.hotel.hotelreservation;

import com.hotel.hotelreservation.repository.GuestRepository;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class GuestManagementController {

    private final GuestRepository guestRepository;

    public GuestManagementController(GuestRepository guestRepository) {
        this.guestRepository = guestRepository;
    }

    @GetMapping("/admin/guests")
    public String viewGuests(Model model) {
        model.addAttribute("guests", guestRepository.findAll());
        return "guest-management";
    }
}
