package com.HotelbookingSystem.FiveStarHotelBooking.Model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class BookedRoomResponse {
    private Long bookingid;
    private LocalDate checkInDate;
    private LocalDate checkOutDate;
    private String guestFullName;
    private String guestEmail;
    private int numofAdults;
    private int numofChildren;
    private int totalNumofguests;
    private String bookingConfirmationCode;
    private Room room;

    public BookedRoomResponse(String bookingConfirmationCode, Long bookingid, LocalDate checkInDate, LocalDate checkOutDate) {
        this.bookingConfirmationCode = bookingConfirmationCode;
        this.bookingid = bookingid;
        this.checkInDate = checkInDate;
        this.checkOutDate = checkOutDate;
    }


}
