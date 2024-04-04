package dao;

import database.DbConnection;
import model.RoomType;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class RoomTypeDao {

    private Connection connection;

    public RoomTypeDao() throws ClassNotFoundException, SQLException {
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

    // Retrieve all room types
    public List<RoomType> getAllRoomTypes() {
        List<RoomType> roomTypes = new ArrayList<>();
        String sql = "SELECT * FROM room_types";

        try (PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
            ResultSet resultSet = preparedStatement.executeQuery();

            while (resultSet.next()) {
                RoomType roomType = new RoomType();
                roomType.setId(resultSet.getLong("id"));
                roomType.setLabel(resultSet.getString("label"));
                roomType.setDescription(resultSet.getString("description"));
                roomType.setNumberOfPersons(resultSet.getInt("nbr_persone"));

                roomTypes.add(roomType);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return roomTypes;
    }

    // Retrieve available room types had method 3iyat 3liha f RoomSearchServlet
    public List<RoomType> getAvailableRoomTypes(String checkInDate, String checkOutDate)
            throws SQLException {
        List<RoomType> availableRoomTypes = new ArrayList<>();
        String sql = "SELECT rt.id, rt.label, rt.description, rt.nbr_persone " +
                "FROM room_types rt " +
                "WHERE NOT EXISTS (" +
                "    SELECT 1 FROM reservations r " +
                "    WHERE r.room_id = rt.id " +
                "      AND (r.check_in_date BETWEEN ? AND ? " +
                "           OR r.check_out_date BETWEEN ? AND ?)" +
                ")";

        try (PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
            preparedStatement.setString(1, checkInDate);
            preparedStatement.setString(2, checkOutDate);
            preparedStatement.setString(3, checkInDate);
            preparedStatement.setString(4, checkOutDate);

            ResultSet resultSet = preparedStatement.executeQuery();

            while (resultSet.next()) {
                RoomType roomType = new RoomType();
                roomType.setId(resultSet.getLong("id"));
                roomType.setLabel(resultSet.getString("label"));
                roomType.setDescription(resultSet.getString("description"));
                roomType.setNumberOfPersons(resultSet.getInt("nbr_persone"));

                availableRoomTypes.add(roomType);
            }
        }

        return availableRoomTypes;
    }

    // Retrieve a room type by ID
    public RoomType getRoomTypeById(long roomTypeId) {
        RoomType roomType = null;
        String sql = "SELECT * FROM room_types WHERE id = ?";

        try (PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
            preparedStatement.setLong(1, roomTypeId);

            ResultSet resultSet = preparedStatement.executeQuery();

            if (resultSet.next()) {
                roomType = new RoomType();
                roomType.setId(resultSet.getLong("id"));
                roomType.setLabel(resultSet.getString("label"));
                roomType.setDescription(resultSet.getString("description"));
                roomType.setNumberOfPersons(resultSet.getInt("nbr_persone"));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return roomType;
    }
}

