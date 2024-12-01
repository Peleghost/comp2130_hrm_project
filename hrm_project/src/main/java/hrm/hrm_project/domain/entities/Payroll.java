package hrm.hrm_project.domain.entities;

import java.time.LocalDate;

public class Payroll {
    private int payrollId;
    private int employeeId;
    private double baseSalary;
    private double hoursWorked;
    private double overtimeHours;
    private double bonus;
    private double tax;
    private double totalPay;
    private LocalDate payDate;

    // Constructor
    public Payroll(int employeeId, double baseSalary, double hoursWorked, double overtimeHours, double bonus, double tax) {
        this.employeeId = employeeId;
        this.baseSalary = baseSalary;
        this.hoursWorked = hoursWorked;
        this.overtimeHours = overtimeHours;
        this.bonus = bonus;
        this.tax = tax;
        calculateTotalPay();
        this.payDate = LocalDate.now();
    }

    // Calculate total pay
    private void calculateTotalPay() {
        double overtimePay = overtimeHours * (baseSalary / 160) * 1.5; // Overtime: 1.5x hourly rate
        this.totalPay = (baseSalary + overtimePay + bonus) - tax;
    }

    // Getters and Setters
    public int getPayrollId() {
        return payrollId;
    }

    public void setPayrollId(int payrollId) {
        this.payrollId = payrollId;
    }

    public int getEmployeeId() {
        return employeeId;
    }

    public void setEmployeeId(int employeeId) {
        this.employeeId = employeeId;
    }

    public double getBaseSalary() {
        return baseSalary;
    }

    public void setBaseSalary(double baseSalary) {
        this.baseSalary = baseSalary;
    }

    public double getHoursWorked() {
        return hoursWorked;
    }

    public void setHoursWorked(double hoursWorked) {
        this.hoursWorked = hoursWorked;
    }

    public double getOvertimeHours() {
        return overtimeHours;
    }

    public void setOvertimeHours(double overtimeHours) {
        this.overtimeHours = overtimeHours;
    }

    public double getBonus() {
        return bonus;
    }

    public void setBonus(double bonus) {
        this.bonus = bonus;
    }

    public double getTax() {
        return tax;
    }

    public void setTax(double tax) {
        this.tax = tax;
    }

    public double getTotalPay() {
        return totalPay;
    }

    public LocalDate getPayDate() {
        return payDate;
    }

    public void setPayDate(LocalDate payDate) {
        this.payDate = payDate;
    }

    @Override
    public String toString() {
        return "Payroll{" +
                "payrollId=" + payrollId +
                ", employeeId=" + employeeId +
                ", baseSalary=" + baseSalary +
                ", hoursWorked=" + hoursWorked +
                ", overtimeHours=" + overtimeHours +
                ", bonus=" + bonus +
                ", tax=" + tax +
                ", totalPay=" + totalPay +
                ", payDate=" + payDate +
                '}';
    }
}
