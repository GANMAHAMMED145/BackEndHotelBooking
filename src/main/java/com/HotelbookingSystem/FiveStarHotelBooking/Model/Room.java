package com.HotelbookingSystem.FiveStarHotelBooking.Model;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import org.apache.commons.lang3.RandomStringUtils;

import java.math.BigDecimal;
import java.sql.Blob;
import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@Setter
@AllArgsConstructor
public class Room {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(name="room type")
    private String roomtype;
    @Column(name="room price")
    private BigDecimal roomprice;
    private boolean isBooked = false;
    @Lob
    private Blob photo;

    @OneToMany(fetch= FetchType.LAZY,cascade = CascadeType.ALL, mappedBy = "room")
    private List<BookedRoom> bookings ;


    public BigDecimal getRoomprice() {
        return roomprice;
    }

    public void setRoomprice(BigDecimal roomprice) {
        this.roomprice = roomprice;
    }

    public String getRoomtype() {
        return roomtype;
    }

    public void setRoomtype(String roomtype) {
        this.roomtype = roomtype;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Blob getPhoto() {
        return photo;
    }

    public void setPhoto(Blob photo) {
        this.photo = photo;
    }

    public boolean isBooked() {
        return isBooked;
    }

    public void setBooked(boolean booked) {
        isBooked = booked;
    }

    public Room (){
        this.bookings = new ArrayList<>();
    }

    //Adding a room to Bookings when its only Booked
    public void addBooking(BookedRoom booking){
        if(bookings == null){
            bookings = new ArrayList<>();
        }
        bookings.add(booking);
        booking.setRoom(this);
        isBooked = true;
        String bookingcode = RandomStringUtils.randomNumeric(10);
        booking.setBookingConfirmationCode(bookingcode);
    }
}
