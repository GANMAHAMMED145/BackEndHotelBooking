package com.HotelbookingSystem.FiveStarHotelBooking.Service;

import com.HotelbookingSystem.FiveStarHotelBooking.Model.BookedRoom;

import java.util.List;

public interface BookedRoomService {

   List<BookedRoom> getAllBookedRoomsByRoomId(Long roomid);


}
