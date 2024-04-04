package dao;

import database.DbConnection;
import model.Room;
import model.RoomType;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class RoomDao {

    private Connection connection;

    public RoomDao() throws ClassNotFoundException, SQLException {
        connection = DbConnection.getConnection();
    }

    public void close() {
        // Close the connection when the DAO is no longer needed
        try {
            if (connection != null && !connection.isClosed()) {
                connection.close();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    // Retrieve all rooms
    public List<Room> getAllRooms() {
        List<Room> rooms = new ArrayList<>();
        String sql = "SELECT * FROM rooms";

        try (PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
            ResultSet resultSet = preparedStatement.executeQuery();

            while (resultSet.next()) {
                Room room = new Room();
                room.setId(resultSet.getLong("id"));
                room.setMatricule(resultSet.getInt("matricule"));
                room.setStatus(resultSet.getBoolean("status"));
                // Assuming Room entity has a Type field with corresponding getters and setters
                room.setRoomType(new RoomTypeDao().getRoomTypeById(resultSet.getLong("type_id")));

                rooms.add(room);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        }

        return rooms;
    }

    // Retrieve available rooms within a date range
    public Room getAvailableRoom(long roomTypeId, String checkInDate, String checkOutDate) {
        String sql = "SELECT * FROM rooms r " +
                "WHERE r.status = true AND r.type_id = ? AND NOT EXISTS (" +
                "    SELECT 1 FROM reservations res " +
                "    WHERE r.id = res.room_id " +
                "    AND ((res.check_in_date BETWEEN ? AND ?) OR (res.check_out_date BETWEEN ? AND ?))" +
                ") LIMIT 1";

        System.out.println(roomTypeId);
        System.out.println(checkInDate);
        System.out.println(checkOutDate);


        try (PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
            preparedStatement.setLong(1, roomTypeId);
            preparedStatement.setString(2, checkInDate);
            preparedStatement.setString(3, checkOutDate);
            preparedStatement.setString(4, checkInDate);
            preparedStatement.setString(5, checkOutDate);

            ResultSet resultSet = preparedStatement.executeQuery();

            if (resultSet.next()) {
                Room room = new Room();
                room.setId(resultSet.getLong("id"));
                room.setMatricule(resultSet.getInt("matricule"));
                room.setStatus(resultSet.getBoolean("status"));
                room.setRoomType(new RoomTypeDao().getRoomTypeById(resultSet.getLong("type_id")));

                return room;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        }

        return null; // Return null if no available room is found
    }

    public void updateRoomStatusBasedOnDate() {
        String sql = "UPDATE rooms " +
                "SET status = true " +
                "WHERE EXISTS ( " +
                "    SELECT 1 " +
                "    FROM reservations " +
                "    WHERE reservations.room_id = rooms.id " +
                "      AND reservations.check_out_date < CURDATE() " +
                ")";

        try (PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
