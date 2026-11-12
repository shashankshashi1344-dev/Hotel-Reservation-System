
package com.hotel.hotelreservation.repository;

import com.hotel.hotelreservation.Guest;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.Optional;

public interface GuestRepository extends JpaRepository<Guest, Integer> {

    Optional<Guest> findByEmail(String email);
}
