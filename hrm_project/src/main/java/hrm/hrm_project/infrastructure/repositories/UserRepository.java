package hrm.hrm_project.infrastructure.repositories;

import hrm.hrm_project.domain.entities.Employee;
import hrm.hrm_project.domain.entities.User;
import hrm.hrm_project.domain.interfaces.IUser;
import hrm.hrm_project.infrastructure.data.Db;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.logging.Logger;

public class UserRepository implements IUser {
    private static Connection conn = Db.getConnection();
    private static final Logger logger = Db.getLogger();

    public Db.DbResult insertUser(User user) throws SQLException {
        if (conn.isClosed()) {
            conn = Db.getConnection();
        }

        String sql = "INSERT INTO users (username, password, role) " +
                "VALUES (?, ?, ?);";

        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, user.username);
            stmt.setString(2, user.password);
            stmt.setString(3, user.role);

            stmt.executeUpdate();

            logger.info("Inserted new user");

            return new Db.DbResult(true, "Successfully inserted user");
        } catch (SQLException ex) {
            logger.warning("Failed to insert user " + ex.getMessage());
            return new Db.DbResult(false, ex.getMessage());
        }
        finally {
            Db.closeConnection();
        }
    }

    public User getUserById(int id) {
        String sql = "SELECT username, password, role FROM users WHERE id = ?";

        try (PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, id);

            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
                return new User(
                        rs.getString("username"),
                        rs.getString("password"),
                        rs.getString("role")
                );
            }
        } catch (SQLException ex) {
            logger.info("Error fetching user: " + ex.getMessage());
            return null;
        }
        finally {
            Db.closeConnection();
        }
        return null;
    }

    public Db.DbResult updateUser(User user) throws SQLException {
        String sql = "UPDATE users SET username = ?, password = ?, role = ? WHERE id = ?";

        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, user.getUsername());
            stmt.setString(2, user.getPassword());
            stmt.setString(3, user.getRole());
            stmt.setInt(4, user.getId());

            stmt.executeUpdate();

            logger.info("Updated user: " + user.getId() + " " + user.getUsername());

            return new Db.DbResult(true, "User updated successfully");
        } catch (SQLException ex) {
            logger.info("Error updating user: " + ex.getMessage());
            return new Db.DbResult(false, ex.getMessage());
        } finally {
            Db.closeConnection();
        }
    }

    public Db.DbResult deleteUser(int id) throws SQLException {
        String sql = "DELETE FROM users WHERE id = ?";

        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, id);

            stmt.executeUpdate();

            logger.info("Deleted user: " + id);

            return new Db.DbResult(true, "User deleted successfully");
        } catch (SQLException ex) {
            logger.info("Error deleting User: " + ex.getMessage());
            return new Db.DbResult(false, ex.getMessage());
        } finally {
            Db.closeConnection();
        }
    }
}
