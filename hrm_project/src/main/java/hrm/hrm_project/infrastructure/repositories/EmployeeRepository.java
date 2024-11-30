package hrm.hrm_project.infrastructure.repositories;

import java.security.NoSuchAlgorithmException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.logging.Logger;

import hrm.hrm_project.domain.entities.Employee;
import hrm.hrm_project.domain.interfaces.IEmployee;
import hrm.hrm_project.infrastructure.data.Db;
import hrm.hrm_project.utils.PasswordUtil;

// Repository for Employee class
public class EmployeeRepository implements IEmployee {
    private static Connection conn = Db.getConnection();
    private static final Logger logger = Db.getLogger();

    // Insert a new employee
    public Db.DbResult insertEmployee(Employee emp) throws SQLException {
        if (conn.isClosed()) {
            conn = Db.getConnection();
        }

        String sql = "INSERT INTO Employees (first_name, last_name, email, phone_number, hire_date, salary)" +
                "VALUES (?, ?, ?, ?, ?, ?, ?)";
        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, emp.getFirstName());
            stmt.setString(2, emp.getLastName());
            stmt.setString(3, emp.getEmail());
            stmt.setString(4, emp.getPhone());
            stmt.setString(5, emp.getHireDate());
            stmt.setDouble(6, emp.getSalary());

            stmt.executeUpdate();

            logger.info("Inserted new employee: " + emp.getFirstName() + " " + emp.getLastName());

            return new Db.DbResult(true, "Employee added successfully");
        } catch (SQLException ex) {
            logger.warning("Failed to insert employee: " + ex.getMessage());
            return new Db.DbResult(false, ex.getMessage());
        } finally {
            Db.closeConnection();
        }
    }

    // Get an employee by id
    public Employee getEmployeeById(int id) {
        String sql = "SELECT first_name, last_name, email, phone_number, " +
                "salary FROM Employees WHERE id = ?";
        try (PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, id);

            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
                return new Employee(
                        rs.getString("first_name"),
                        rs.getString("last_name"),
                        rs.getString("email"),
                        rs.getString("phone_number"),
                        rs.getDouble("salary")
                );
            }
        } catch (SQLException ex) {
            logger.warning("Error fetching employee: " + ex.getMessage());
        }
        return null; // Employee not found
    }

    // Update an existing employee
    public Db.DbResult updateEmployee(Employee emp) throws SQLException {
        String sql = "UPDATE Employees SET first_name = ?, last_name = ?, email = ?, " +
                "phone_number = ?, salary = ?  WHERE username = ?";

        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, emp.getFirstName());
            stmt.setString(2, emp.getLastName());
            stmt.setString(3, emp.getEmail());
            stmt.setString(4, emp.getPhone());
            stmt.setDouble(5, emp.getSalary());

            stmt.executeUpdate();

            logger.info("Updated employee: " + emp.getId() + " " + emp.getFirstName());

            return new Db.DbResult(true, "Employee updated successfully");
        } catch (SQLException ex) {
            logger.warning("Error updating employee: " + ex.getMessage());
            return new Db.DbResult(false, ex.getMessage());
        } finally {
            Db.closeConnection();
        }
    }

    // Delete an employee
    public Db.DbResult deleteEmployee(int id) throws SQLException {
        String sql = "DELETE FROM Employees WHERE id = ?";

        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, id);
            stmt.executeUpdate();

            logger.info("Deleted employee: " + id);

            return new Db.DbResult(true, "Employee deleted successfully");
        } catch (SQLException ex) {
            logger.warning("Error deleting employee: " + ex.getMessage());
            return new Db.DbResult(false, ex.getMessage());
        } finally {
            Db.closeConnection();
        }
    }

    // Login to validate username and password
    public boolean login(String username, String password) {
        String sql = "SELECT password FROM Employees WHERE username = ?";
        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, username);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                String storedPasswordHash = rs.getString("password");
                // Verify the provided password using PasswordUtil
                return PasswordUtil.verifyPassword(password, storedPasswordHash);
            }
        } catch (SQLException | NoSuchAlgorithmException ex) {
            logger.warning("Login failed: " + ex.getMessage());
        }
        return false; // Login failed
    }
}