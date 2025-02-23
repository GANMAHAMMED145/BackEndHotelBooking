package com.HotelbookingSystem.FiveStarHotelBooking.Repoistory;
import com.HotelbookingSystem.FiveStarHotelBooking.Model.Room;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface RoomRepoistory extends JpaRepository<Room, Long> {

    @Query("SELECT DISTINCT r.roomtype FROM Room r")
    List<String> findDistnctRoomTypes();
}
