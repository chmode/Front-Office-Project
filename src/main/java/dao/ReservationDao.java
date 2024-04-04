package dao;

import database.DbConnection;
import model.Reservation;
import model.Room;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class ReservationDao {

    private Connection connection;

    public ReservationDao() throws ClassNotFoundException, SQLException {
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
//new function return rooms trus
    /*public List<Room> getAvailableRooms(String checkInDate, String checkOutDate) {
        List<Room> availableRooms = new ArrayList<>();
        String sql = "SELECT * FROM rooms r WHERE r.id NOT IN " +
                "(SELECT room_id FROM reservations " +
                "WHERE (? BETWEEN check_in_date AND check_out_date) OR (? BETWEEN check_in_date AND check_out_date))";

        try (PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
            preparedStatement.setString(1, checkInDate);
            preparedStatement.setString(2, checkOutDate);

            ResultSet resultSet = preparedStatement.executeQuery();

            while (resultSet.next()) {
                RoomDao roomDao = new RoomDao();
                Room room = roomDao.getRoomById(resultSet.getLong("id"));
                availableRooms.add(room);
            }
        } catch (SQLException | ClassNotFoundException e) {
            e.printStackTrace();
        }

        return availableRooms;
    }
*/
    public void saveReservation(long clientId, Reservation reservation) {
        String sql = "INSERT INTO reservations (client_id, check_in_date, check_out_date, room_id) VALUES (?, ?, ?, ?)";

        try (PreparedStatement preparedStatement = connection.prepareStatement(sql, PreparedStatement.RETURN_GENERATED_KEYS)) {
            preparedStatement.setLong(1, clientId);
            preparedStatement.setString(2, reservation.getCheckInDate());
            preparedStatement.setString(3, reservation.getCheckOutDate());
            preparedStatement.setLong(4, reservation.getRoom().getId()); // Assuming Room has an 'id' field

            preparedStatement.executeUpdate();

            // Retrieve the auto-generated key (reservation ID)
            ResultSet generatedKeys = preparedStatement.getGeneratedKeys();
            if (generatedKeys.next()) {
                long reservationId = generatedKeys.getLong(1);
                // Update the status of the reserved room to false
                updateRoomStatus(reservation.getRoom().getId(), false);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private void updateRoomStatus(long roomId, boolean status) {
        String updateSql = "UPDATE rooms SET status = ? WHERE id = ?";

        try (PreparedStatement updateStatement = connection.prepareStatement(updateSql)) {
            updateStatement.setBoolean(1, status);
            updateStatement.setLong(2, roomId);

            updateStatement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
/*
    public List<Reservation> getAllReservations() {
        List<Reservation> reservations = new ArrayList<>();
        String sql = "SELECT * FROM reservations";

        try (PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
            ResultSet resultSet = preparedStatement.executeQuery();

            while (resultSet.next()) {
                Reservation reservation = new Reservation();
                reservation.setId(resultSet.getLong("id"));
                reservation.setCheckInDate(resultSet.getString("check_in_date"));
                reservation.setCheckOutDate(resultSet.getString("check_out_date"));
                // Assuming you have a RoomDao to retrieve Room details
                RoomDao roomDao = new RoomDao();
                reservation.setRoom(roomDao.getRoomById(resultSet.getLong("room_id")));
                reservations.add(reservation);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        }

        return reservations;
    }*/
}
