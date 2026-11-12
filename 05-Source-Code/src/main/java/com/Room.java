
package com.hotel.hotelreservation;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name = "rooms")
public class Room {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "room_id")
    private Integer roomId;

    @Column(name = "room_number")
    private String roomNumber;

    @Column(name = "room_type")
    private String roomType;

    @Column(name = "price_per_night")
    private Double pricePerNight;

    @Column(name = "capacity")
    private Integer capacity;

    @Column(name = "facilities")
    private String facilities;

    @Column(name = "room_status")
    private String roomStatus;

    public Room() {
    }

    public Integer getRoomId() {
        return roomId;
    }

    public String getRoomNumber() {
        return roomNumber;
    }

    public String getRoomType() {
        return roomType;
    }

    public Double getPricePerNight() {
        return pricePerNight;
    }

    public Integer getCapacity() {
        return capacity;
    }

    public String getFacilities() {
        return facilities;
    }

    public String getRoomStatus() {
        return roomStatus;
    }
    
    public void setRoomNumber(String roomNumber) {
        this.roomNumber = roomNumber;
    }

    public void setRoomType(String roomType) {
        this.roomType = roomType;
    }

    public void setPricePerNight(Double pricePerNight) {
        this.pricePerNight = pricePerNight;
    }

    public void setCapacity(Integer capacity) {
        this.capacity = capacity;
    }

    public void setFacilities(String facilities) {
        this.facilities = facilities;
    }

    public void setRoomStatus(String roomStatus) {
        this.roomStatus = roomStatus;
    }

}
