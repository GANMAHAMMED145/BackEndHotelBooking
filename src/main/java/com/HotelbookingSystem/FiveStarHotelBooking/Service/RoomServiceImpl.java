package com.HotelbookingSystem.FiveStarHotelBooking.Service;
import com.HotelbookingSystem.FiveStarHotelBooking.Exception.InternalServerException;
import com.HotelbookingSystem.FiveStarHotelBooking.Exception.ResourceNotFoundException;
import com.HotelbookingSystem.FiveStarHotelBooking.Model.Room;
import com.HotelbookingSystem.FiveStarHotelBooking.Repoistory.RoomRepoistory;
import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import javax.sql.rowset.serial.SerialBlob;
import java.io.IOException;
import java.math.BigDecimal;
import java.sql.Blob;
import java.sql.SQLException;
import java.util.List;
import java.util.Optional;

@Service
    public class RoomServiceImpl implements RoomService {

    private final RoomRepoistory roomRepoistory;

    public RoomServiceImpl(RoomRepoistory roomRepoistory) {
        this.roomRepoistory = roomRepoistory;
    }

    //Adding a New Room to Database
    @Override
    public Room addNewRoom(MultipartFile photo, String roomType, BigDecimal roomPrice) throws IOException, SQLException {
        Room room = new Room();
        room.setRoomtype(roomType);
        room.setRoomprice(roomPrice);
        if (!photo.isEmpty()) {
            byte[] bytes = photo.getBytes();
            Blob photoBlob = new SerialBlob(bytes);
            room.setPhoto(photoBlob);
        }
        return roomRepoistory.save(room);
    }

    // Get All Room types
    @Override
    public List<String> getAllRoomTypes() {
        return roomRepoistory.findDistnctRoomTypes();
    }

    @Override
    public List<Room> getAllRooms() {
        return roomRepoistory.findAll();
    }

    @Override
    public byte[] getRoomPhotoByRoomId(Long roomid) throws SQLException {
        Optional<Room> roomfetchedbyid = roomRepoistory.findById(roomid);
        if (roomfetchedbyid.isEmpty()) {
            throw new ResourceNotFoundException("Room was not found with id " + roomid);
        }
        Blob photoblob = roomfetchedbyid.get().getPhoto();
        if (!(photoblob == null)) {
            return photoblob.getBytes(1, (int) photoblob.length());
        }
        return null;
    }

    @Transactional
    public void deleteRoomById(long roomId) {
        Room room = roomRepoistory.findById(roomId)
                .orElseThrow(() -> new EntityNotFoundException("Room not found with ID: " + roomId));

        roomRepoistory.delete(room);
    }
    @Override
    public Room updateRoom(Long roomId, String roomType, BigDecimal roomPrice, byte[] photoBytes) {
        Room room = roomRepoistory.findById(roomId).orElseThrow(() -> new ResourceNotFoundException("Room Not found"));
        if (!(roomType == null)) {
            room.setRoomtype(roomType);
        }
        if (!(roomPrice == null)) {
            room.setRoomprice(roomPrice);
        }
        if (!(photoBytes == null) && photoBytes.length > 0) {

            try {
                room.setPhoto(new SerialBlob(photoBytes));
            } catch (SQLException ex) {
                throw new InternalServerException("Error  updating room");
            }

        }

        return roomRepoistory.save(room);
    }

    @Override
    public Optional<Room> getRoomByRoomId(Long roomId) {
        return Optional.ofNullable(roomRepoistory.findById(roomId)
                .orElseThrow(() -> new ResourceNotFoundException("Room not found with ID: " + roomId)));
    }

}
