package com.HotelbookingSystem.FiveStarHotelBooking.Model;


import java.math.BigDecimal;
import java.util.Base64;
import java.util.List;

public class RoomResponse {
    private Long id;
    private String roomtype;
    private BigDecimal roomprice;
    private boolean isBooked = false;
    private String photo;
    private List<BookedRoomResponse> bookingResponse ;

    public RoomResponse(BigDecimal roomprice, String roomtype, Long id) {
        this.roomprice = roomprice;
        this.roomtype = roomtype;
        this.id= id;
    }

    public RoomResponse( Long id, boolean isBooked, byte[] photoBytes, BigDecimal roomprice, String roomtype) {
//        this.bookingResponse = bookingResponse;

        //List<BookedRoomResponse> bookingResponse,

        this.id = id;
        this.isBooked = isBooked;
        this.photo = !((photoBytes) ==null)? Base64.getEncoder().encodeToString(photoBytes) : null;
        this.roomprice = roomprice;
        this.roomtype = roomtype;
    }

    public List<BookedRoomResponse> getBookingResponse() {
        return bookingResponse;
    }

    public void setBookingResponse(List<BookedRoomResponse> bookingResponse) {
        this.bookingResponse = bookingResponse;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public boolean isBooked() {
        return isBooked;
    }

    public void setBooked(boolean booked) {
        isBooked = booked;
    }

    public String getPhoto() {
        return photo;
    }

    public void setPhoto(String photo) {
        this.photo = photo;
    }

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
}
