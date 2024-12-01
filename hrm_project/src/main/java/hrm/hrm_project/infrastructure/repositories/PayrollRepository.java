package hrm.hrm_project.infrastructure.repositories;

import hrm.hrm_project.domain.entities.Payroll;
import hrm.hrm_project.infrastructure.data.Db;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class PayrollRepository {
    private static Connection conn = Db.getConnection();

    // Save payroll record
    public boolean savePayroll(Payroll payroll) {
        String sql = "INSERT INTO Payroll (employee_id, base_salary, hours_worked, overtime_hours, bonus, tax, total_pay, pay_date) " +
                "VALUES (?, ?, ?, ?, ?, ?, ?, ?)";
        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, payroll.getEmployeeId());
            stmt.setDouble(2, payroll.getBaseSalary());
            stmt.setDouble(3, payroll.getHoursWorked());
            stmt.setDouble(4, payroll.getOvertimeHours());
            stmt.setDouble(5, payroll.getBonus());
            stmt.setDouble(6, payroll.getTax());
            stmt.setDouble(7, payroll.getTotalPay());
            stmt.setString(8, payroll.getPayDate().toString());
            stmt.executeUpdate();
            return true;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    // Fetch all payroll records
    public List<Payroll> getAllPayrolls() {
        List<Payroll> payrolls = new ArrayList<>();
        String sql = "SELECT * FROM Payroll";
        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                Payroll payroll = new Payroll(
                        rs.getInt("employee_id"),
                        rs.getDouble("base_salary"),
                        rs.getDouble("hours_worked"),
                        rs.getDouble("overtime_hours"),
                        rs.getDouble("bonus"),
                        rs.getDouble("tax")
                );
                payroll.setPayrollId(rs.getInt("payroll_id"));
                payrolls.add(payroll);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return payrolls;
    }
}
