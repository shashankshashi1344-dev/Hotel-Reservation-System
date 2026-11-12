
package com.hotel.hotelreservation;

import com.hotel.hotelreservation.repository.RoomRepository;
import org.springframework.stereotype.Controller;
import com.hotel.hotelreservation.repository.BookingRepository;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
public class RoomManagementController {

    private final RoomRepository roomRepository;
    private final BookingRepository bookingRepository;

    public RoomManagementController(
        RoomRepository roomRepository,
        BookingRepository bookingRepository) {
    this.roomRepository = roomRepository;
    this.bookingRepository = bookingRepository;
}

    @GetMapping("/admin/rooms")
    public String showRooms(Model model) {
        model.addAttribute("rooms", roomRepository.findAll());
        return "room-management";
    }

    @GetMapping("/admin/rooms/new")
    public String showAddRoomForm(Model model) {
        model.addAttribute("room", new Room());
        return "add-room";
    }

    @PostMapping("/admin/rooms/save")
    public String saveRoom(
            @ModelAttribute("room") Room room,
            RedirectAttributes redirectAttributes) {

        room.setRoomStatus("Available");
        roomRepository.save(room);

        redirectAttributes.addFlashAttribute(
                "successMessage", "Room added successfully!");
        return "redirect:/admin/rooms";
    }

    @GetMapping("/admin/rooms/edit/{id}")
    public String showEditRoomForm(
            @PathVariable Integer id,
            Model model,
            RedirectAttributes redirectAttributes) {

        var room = roomRepository.findById(id);

        if (room.isEmpty()) {
            redirectAttributes.addFlashAttribute(
                    "errorMessage", "Room not found.");
            return "redirect:/admin/rooms";
        }

        model.addAttribute("room", room.get());
        return "edit-room";
    }

    @PostMapping("/admin/rooms/update/{id}")
    public String updateRoom(
            @PathVariable Integer id,
            @ModelAttribute("room") Room updatedRoom,
            RedirectAttributes redirectAttributes) {

        var existingRoom = roomRepository.findById(id);

        if (existingRoom.isEmpty()) {
            redirectAttributes.addFlashAttribute(
                    "errorMessage", "Room not found.");
            return "redirect:/admin/rooms";
        }

        Room room = existingRoom.get();
        room.setRoomNumber(updatedRoom.getRoomNumber());
        room.setRoomType(updatedRoom.getRoomType());
        room.setPricePerNight(updatedRoom.getPricePerNight());
        room.setCapacity(updatedRoom.getCapacity());
        room.setFacilities(updatedRoom.getFacilities());
        room.setRoomStatus(updatedRoom.getRoomStatus());

        roomRepository.save(room);

        redirectAttributes.addFlashAttribute(
                "successMessage", "Room updated successfully!");
        return "redirect:/admin/rooms";
    }

    
    @PostMapping("/admin/rooms/delete")
    public String deleteRoom(
            @RequestParam Integer roomId,
            RedirectAttributes redirectAttributes) {

        if (!roomRepository.existsById(roomId)) {
            redirectAttributes.addFlashAttribute(
                    "errorMessage", "Room not found.");
            return "redirect:/admin/rooms";
        }

        if (bookingRepository.existsByRoomId(roomId)) {
            redirectAttributes.addFlashAttribute(
                    "errorMessage",
                    "This room cannot be deleted because booking records exist.");
            return "redirect:/admin/rooms";
        }

        roomRepository.deleteById(roomId);

        redirectAttributes.addFlashAttribute(
                "successMessage", "Room deleted successfully!");

        return "redirect:/admin/rooms";
    }

}
