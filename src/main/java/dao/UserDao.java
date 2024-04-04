package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import org.mindrot.jbcrypt.BCrypt;

import database.DbConnection;
import model.User;

public class UserDao {
    Connection connection;

    public UserDao() throws ClassNotFoundException, SQLException {
        connection = DbConnection.getConnection();
    }

    public int createUser(User user) throws SQLException {
        int generatedUserId = -1; // Default value indicating failure

        try (PreparedStatement prs = connection.prepareStatement(
                "INSERT INTO users(name,email,phone,role,password) VALUES(?,?,?,?,?)",
                PreparedStatement.RETURN_GENERATED_KEYS)) {
            prs.setString(1, user.getName());
            prs.setString(2, user.getEmail());
            prs.setString(3, user.getPhone());
            prs.setString(4, user.getRole());

            String hashedPassword = BCrypt.hashpw(user.getPassword(), BCrypt.gensalt());
            prs.setString(5, hashedPassword);

            int affectedRows = prs.executeUpdate();

            if (affectedRows > 0) {
                // Retrieve the generated keys (in this case, the user ID)
                try (ResultSet generatedKeys = prs.getGeneratedKeys()) {
                    if (generatedKeys.next()) {
                        generatedUserId = generatedKeys.getInt(1);
                    }
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return generatedUserId;
    }

    public List<User> getAllUsers() {
        List<User> users = new ArrayList<>();
        try (java.sql.Statement statement = connection.createStatement();
             ResultSet results = statement.executeQuery("SELECT * FROM users")) {

            while (results.next()) {
                User user = new User();
                user.setId(results.getInt("id"));
                user.setEmail(results.getString("email"));
                user.setName(results.getString("name"));
                user.setPassword(results.getString("password"));
                users.add(user);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return users;
    }

    public void deleteUser(int id) {
        try (PreparedStatement prs = connection.prepareStatement("DELETE FROM users WHERE id=?")) {
            prs.setInt(1, id);
            prs.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public User getUserById(int id) {
        try (PreparedStatement prs = connection.prepareStatement("SELECT * FROM users WHERE id=?")) {
            prs.setInt(1, id);
            try (ResultSet results = prs.executeQuery()) {
                if (results.next()) {
                    User user = new User();
                    user.setId(results.getInt("id"));
                    user.setEmail(results.getString("email"));
                    user.setName(results.getString("name"));
                    user.setPassword(results.getString("password"));
                    return user;
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    public void updateUser(User user) {
        try (PreparedStatement prs = connection
                .prepareStatement("UPDATE users SET name=?, email=?, password=? WHERE id=?")) {
            prs.setString(1, user.getName());
            prs.setString(2, user.getEmail());
            prs.setString(3, user.getPassword());
            prs.setInt(4, user.getId());
            prs.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    /**
     * this function sent auery to database for count email into database
     * then resultSet.next() retrun true if the result has one ligne (row)exist
     * resultSet.getInt(1) return the value of the first column in the least row
     * */
    public boolean isEmailExists(String email) throws SQLException {
        boolean emailExists = false;

        try (PreparedStatement prs = connection.prepareStatement("SELECT COUNT(*) FROM users WHERE email = ?")) {
            prs.setString(1, email);

            try (ResultSet resultSet = prs.executeQuery()) {
                if (resultSet.next()) {
                    int count = resultSet.getInt(1);
                    emailExists = count > 0;
                }
            }
        }

        return emailExists;
    }

    public String getHashPassword(String email) throws SQLException {
        String hashedPassword = null;

        try (PreparedStatement prs = connection.prepareStatement("SELECT password FROM users WHERE email = ?")) {
            prs.setString(1, email);

            try (ResultSet resultSet = prs.executeQuery()) {
                if (resultSet.next()) {
                    hashedPassword = resultSet.getString("password");
                }
            }
        }

        return hashedPassword;
    }

    public User getUserByEmail(String email) {
        User user = null;

        try (PreparedStatement prs = connection.prepareStatement("SELECT * FROM users WHERE email = ?")) {
            prs.setString(1, email);

            try (ResultSet resultSet = prs.executeQuery()) {
                if (resultSet.next()) {
                    user = new User();
                    user.setId(resultSet.getInt("id"));
                    user.setName(resultSet.getString("name"));
                    user.setEmail(resultSet.getString("email"));
                    user.setPhone(resultSet.getString("phone"));
                    user.setRole(resultSet.getString("role"));
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return user;
    }


}
