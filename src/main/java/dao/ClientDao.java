package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import dao.UserDao;
import org.mindrot.jbcrypt.BCrypt;

import database.DbConnection;
import model.User;
import model.Client;



public class ClientDao extends UserDao {

    Connection connection;

    public ClientDao() throws ClassNotFoundException, SQLException {
        connection = DbConnection.getConnection();
    }


    public int createClient(User user) throws SQLException {
        int generatedUserId = createUser(user); // Reuse the existing create method to insert the user

        if (generatedUserId != -1) {
            try (PreparedStatement prs = connection.prepareStatement(
                    "INSERT INTO clients(user_id) VALUES(?)",
                    PreparedStatement.RETURN_GENERATED_KEYS)) {
                prs.setInt(1, generatedUserId);

                int affectedRows = prs.executeUpdate();

                if (affectedRows <= 0) {
                    // Rollback the user creation if the client creation fails
                    delete(generatedUserId);
                    generatedUserId = -1;
                }
            } catch (SQLException e) {
                e.printStackTrace();
                // Rollback the user creation if the client creation fails
                delete(generatedUserId);
                generatedUserId = -1;
            }
        }

        return generatedUserId;
    }

    // Add a new method to delete a user by ID
    public void delete(int userId) throws SQLException {
        try (PreparedStatement prs = connection.prepareStatement("DELETE FROM users WHERE id = ?")) {
            prs.setInt(1, userId);
            prs.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
//new one
    public long getClientIdByEmail(String email) throws SQLException {
        long clientId = -1; // Default value for an invalid client ID
        String sql = "SELECT id FROM clients WHERE user_id IN (SELECT id FROM users WHERE email = ?)";

        try (PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
            preparedStatement.setString(1, email);

            ResultSet resultSet = preparedStatement.executeQuery();

            if (resultSet.next()) {
                clientId = resultSet.getLong("id");
            }
        }

        return clientId;
    }

    // Other methods for client-related operations

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

}
