package hrm.hrm_project.presentation.controllers;

import hrm.hrm_project.domain.entities.Payroll;
import hrm.hrm_project.infrastructure.repositories.PayrollRepository;
import javafx.fxml.FXML;
import javafx.scene.control.*;

public class PayrollController {

    @FXML
    private TextField employeeIdField, baseSalaryField, hoursWorkedField, overtimeHoursField, bonusField, taxField;

    @FXML
    private Label totalPayLabel;

    private final PayrollRepository payrollRepo = new PayrollRepository();

    @FXML
    private void calculatePayroll() {
        try {
            // Retrieve user inputs
            int employeeId = Integer.parseInt(employeeIdField.getText());
            double baseSalary = Double.parseDouble(baseSalaryField.getText());
            double hoursWorked = Double.parseDouble(hoursWorkedField.getText());
            double overtimeHours = Double.parseDouble(overtimeHoursField.getText());
            double bonus = Double.parseDouble(bonusField.getText());
            double tax = Double.parseDouble(taxField.getText());

            // Create Payroll object and calculate
            Payroll payroll = new Payroll(employeeId, baseSalary, hoursWorked, overtimeHours, bonus, tax);

            // Save to the database
            if (payrollRepo.savePayroll(payroll)) {
                totalPayLabel.setText("Total Pay: $" + payroll.getTotalPay());
            } else {
                totalPayLabel.setText("Failed to save payroll!");
            }
        } catch (NumberFormatException e) {
            totalPayLabel.setText("Invalid input! Please enter valid numbers.");
        }
    }
}