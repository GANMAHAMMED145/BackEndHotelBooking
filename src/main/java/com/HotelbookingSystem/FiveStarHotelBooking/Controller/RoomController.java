package com.HotelbookingSystem.FiveStarHotelBooking.Controller;
import com.HotelbookingSystem.FiveStarHotelBooking.Exception.PhotoRetrievevalException;
import com.HotelbookingSystem.FiveStarHotelBooking.Exception.ResourceNotFoundException;
import com.HotelbookingSystem.FiveStarHotelBooking.Model.BookedRoom;
import com.HotelbookingSystem.FiveStarHotelBooking.Model.BookedRoomResponse;
import com.HotelbookingSystem.FiveStarHotelBooking.Model.Room;
import com.HotelbookingSystem.FiveStarHotelBooking.Model.RoomResponse;
import com.HotelbookingSystem.FiveStarHotelBooking.Service.BookedRoomService;
import com.HotelbookingSystem.FiveStarHotelBooking.Service.RoomService;
import lombok.SneakyThrows;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.sql.rowset.serial.SerialBlob;
import java.io.IOException;
import java.math.BigDecimal;
import java.sql.Blob;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Base64;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/Rooms")
public class RoomController {

    private final RoomService roomService;

    private final BookedRoomService bookedRoomService;

    public RoomController(RoomService roomService, BookedRoomService bookedRoomService) {
        this.roomService = roomService;
        this.bookedRoomService = bookedRoomService;
    }

//Adding a new Room to database
    @PostMapping(value = "/add/new-room", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<RoomResponse> addNewRoom(
           @RequestParam("photo") MultipartFile photo,
           @RequestParam("roomType")  String roomType,
           @RequestParam("roomPrice") BigDecimal roomPrice) throws SQLException, IOException {
        Room savedRoom = roomService.addNewRoom(photo, roomType, roomPrice);
        RoomResponse response = new RoomResponse(savedRoom.getRoomprice(), savedRoom.getRoomtype(),savedRoom.getId());
        return ResponseEntity.ok(response);
    }

    // fetching room types
    @GetMapping("/room-types")
    public List<String> getRoomTypes (){
        return roomService.getAllRoomTypes();
    }

    // fetching all rooms
    @GetMapping("/all-rooms")
     public ResponseEntity<List<RoomResponse>> getAllRooms() throws SQLException {
        List<Room> rooms = roomService.getAllRooms();
        List<RoomResponse> roomResponses =  new ArrayList<>();
           for(Room room: rooms){
            byte[] photoBytes = roomService.getRoomPhotoByRoomId(room.getId());
            if(!(photoBytes == null) && photoBytes.length > 0){
                String  base64Photo  =Base64.getEncoder().encodeToString(photoBytes);
                RoomResponse roomResponse   = getRoomResponse(room);
                if (roomResponse != null) {
                    roomResponse.setPhoto(base64Photo);
                }
                roomResponses.add(roomResponse);
            }
        }
           return ResponseEntity.ok(roomResponses);
     }
//Get Room response
    private RoomResponse getRoomResponse(Room room) throws SQLException {
        List<BookedRoom> bookedRoomsList = getAllBookedRoomsByRoomId(room.getId());
//        List<BookedRoomResponse> bookedRoomDetails = bookedRoomsList
//               .stream()
//               .map(bookedRoom -> new BookedRoomResponse(bookedRoom.getBookingConfirmationCode(),
//                       bookedRoom.getBookingid(),
//                       bookedRoom.getCheckOutDate(),
//                       bookedRoom.getCheckOutDate())).toList();
        byte[] photobytes = null;
        Blob photoblob    = room.getPhoto();
        if(!(photoblob == null)){
            try{
                photobytes = photoblob.getBytes(1, (int) photoblob.length());
            }
            catch (SQLException e) {
                throw new PhotoRetrievevalException("Error while retrying photo");
            }
        }
        return new RoomResponse(
                room.getId(),
                room.isBooked(),
                photobytes,
                room.getRoomprice(),
                room.getRoomtype()
                );
    }
 //getting room by roomId
     private List<BookedRoom> getAllBookedRoomsByRoomId(Long roomid){

         return  bookedRoomService.getAllBookedRoomsByRoomId(roomid);
     }

//Deleting a room by RoomId

     @DeleteMapping("/delete/room/{roomId}")
     public ResponseEntity<Void> deleteRoomByRoomId(@PathVariable ("roomId") long roomId){
        roomService.deleteRoomById(roomId);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
     }

     //Updating a room by RoomId
     @PutMapping("/update/{roomId}")
     public ResponseEntity<RoomResponse> updateRoom(
             @PathVariable Long roomId,
             @RequestParam(required = false) String roomType,
             @RequestParam(required = false) BigDecimal roomPrice,
             @RequestParam(required = false) MultipartFile photo) throws SQLException, IOException {

         byte[] photoBytes = (photo != null && !photo.isEmpty())
                 ? photo.getBytes()
                 : roomService.getRoomPhotoByRoomId(roomId);

         // Ensure photoBytes is not null before creating a Blob
         Blob photoBlob = (photoBytes != null && photoBytes.length > 0) ? new SerialBlob(photoBytes) : null;

         Room updatedRoom = roomService.updateRoom(roomId, roomType, roomPrice, photoBytes);

         // If Blob storage is required, update it
         if (photoBlob != null) {
             updatedRoom.setPhoto(photoBlob);
         }

         RoomResponse roomResponse = getRoomResponse(updatedRoom);
         return ResponseEntity.ok(roomResponse);
     }


    @GetMapping("/room/{roomId}")
    public ResponseEntity<Optional<RoomResponse>> getRoomById(@PathVariable Long roomId) {
        Optional<Room> theRoom = roomService.getRoomByRoomId(roomId);

        return theRoom.map(room -> {
            try {
                return ResponseEntity.ok(Optional.of(getRoomResponse(room)));
            } catch (SQLException e) {
                throw new RuntimeException("Error retrieving room response", e);
            }
        }).orElseThrow(() -> new ResourceNotFoundException("Room not found"));
    }

}
