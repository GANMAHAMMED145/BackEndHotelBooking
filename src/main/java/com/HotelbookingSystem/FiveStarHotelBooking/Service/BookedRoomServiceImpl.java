package com.HotelbookingSystem.FiveStarHotelBooking.Service;

import com.HotelbookingSystem.FiveStarHotelBooking.Model.BookedRoom;
import org.springframework.stereotype.Service;

import java.util.List;
@Service
public class BookedRoomServiceImpl  implements  BookedRoomService{
    @Override
    public List<BookedRoom> getAllBookedRoomsByRoomId(Long roomid) {
        return null;
    }
}
