package com.HotelbookingSystem.FiveStarHotelBooking.Model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import java.time.LocalDate;
@Entity
@Table(name="BookedRoom")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class BookedRoom {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long bookingid;
    @Column(name="check_In")
    private LocalDate checkInDate;
    @Column(name="check_Out")
    private LocalDate checkOutDate;
    @Column(name="guest_FullName")
    private String guestFullName;
    @Column(name="guest_Email")
     private String guestEmail;
    @Column(name="adults")
     private int numofAdults;
    @Column(name="children")
     private int numofChildren;
    @Column(name="total_guest")
     private int totalNumofguests;
    @Column(name="confirmation_Code")
    private String bookingConfirmationCode;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name="room.id", referencedColumnName = "id")
     private Room room;
     public void CalculateTotalNumofGuests(){
        this.totalNumofguests = this.numofAdults + this.numofChildren;
    }

    public void setNumofAdults(int numofAdults) {
        this.numofAdults = numofAdults;
        CalculateTotalNumofGuests();
    }

    public void setNumofChildren(int numofChildren) {
        this.numofChildren = numofChildren;
        CalculateTotalNumofGuests();
    }

    public void setRoom(Room room) {
        this.room = room;
    }

    public void setBookingConfirmationCode(String bookingConfirmationCode) {
        this.bookingConfirmationCode = bookingConfirmationCode;
    }
}
