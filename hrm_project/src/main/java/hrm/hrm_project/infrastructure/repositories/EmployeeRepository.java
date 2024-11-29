package hrm.hrm_project.infrastructure.repositories;

import hrm.hrm_project.domain.entities.Employee;
import hrm.hrm_project.domain.interfaces.IEmployee;
import hrm.hrm_project.infrastructure.data.Db;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.logging.Logger;

// Repository for Employee class
// To house database related methods
public class EmployeeRepository implements IEmployee {
    private static Connection conn = Db.getConnection();
    private static final Logger logger = Db.getLogger();

    // Example of repository pattern
    public Db.DbResult insertEmployee(Employee emp) throws SQLException {
        if (conn.isClosed()) {
            conn = Db.getConnection();
        }

        // Prepare parameters
        String sql = "INSERT INTO Employees (username, password, first_name" +
                ", last_name, email, phone, role)" +
                        "VALUES (?, ?, ?, ?, ?, ?, ?)";

        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, emp.getUsername());
            stmt.setString(2, emp.getPassword());
            stmt.setString(3, emp.getFirstName());
            stmt.setString(4, emp.getLastName());
            stmt.setString(5, emp.getEmail());
            stmt.setString(6, emp.getPhone());
            stmt.setString(7, emp.getRole());

            stmt.executeUpdate();

            // Remember to close DB connection
            Db.closeConnection();

            // Log info to terminal
            logger.info("Inserted new employee");
        } catch (SQLException ex) {
            // Close connection if failed
            Db.closeConnection();
            logger.info(ex.toString());
            return new Db.DbResult(false, ex.getMessage());
        }

        return new Db.DbResult(true, "Success");
    }
}
