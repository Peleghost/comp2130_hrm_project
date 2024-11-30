package hrm.hrm_project.presentation.controllers;

import java.util.ArrayList;
import java.util.List;

import hrm.hrm_project.domain.entities.Employee;
import hrm.hrm_project.domain.interfaces.IEmployee;
import hrm.hrm_project.infrastructure.data.Db;
import hrm.hrm_project.infrastructure.repositories.EmployeeRepository;
import hrm.hrm_project.utils.CommonUtil.ValidationResult;
import static hrm.hrm_project.utils.CommonUtil.*;
import static hrm.hrm_project.utils.EmployeeUtil.*;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.TextField;

public class EmployeeController extends PageController {
    // For database operations
    // The whole point of having an interface is to use it in such a way
    // *****No need to change this
    private final IEmployee empRepo = new EmployeeRepository();
    private final List<TextField> requiredFields = new ArrayList<>();

    @FXML private TextField firstNameField;
    @FXML private TextField lastNameField;
    @FXML private TextField emailField;
    @FXML private TextField phoneField;

    // Clear all fields in the form
    private void clearFields() {
        firstNameField.clear();
        lastNameField.clear();
        emailField.clear();
        phoneField.clear();
    }

    @FXML
    private void initialize() {
        // Set maximum character limits for text fields
        maxTextFieldChars(firstNameField, 30);
        maxTextFieldChars(lastNameField, 30);
        maxTextFieldChars(emailField, 40);
        maxTextFieldChars(phoneField, 10);

        // Add all required fields for empty validation
        requiredFields.add(firstNameField);
        requiredFields.add(lastNameField);
        requiredFields.add(emailField);
        requiredFields.add(phoneField);
    }

    @FXML
    private void handleSubmit() {
        try {
            // Collect field values
            String firstName = firstNameField.getText().trim();
            String lastName = lastNameField.getText().trim();
            String email = emailField.getText().trim();
            String phone = phoneField.getText().trim();

            // Validate fields
            if (validateEmptyFields(requiredFields)) {
                throw new Exception("All fields are required");
            }

            // Validate names
            ValidationResult firstNameResult = validateName(firstName);
            ValidationResult lastNameResult = validateName(lastName);
            if (!firstNameResult.isValid) throw new Exception(firstNameResult.errorMsg);
            if (!lastNameResult.isValid) throw new Exception(lastNameResult.errorMsg);

            // Validate email
            if (!isValidEmail(email)) {
                setTextFieldErrStyle(emailField);
                throw new Exception("Invalid email format");
            }

            // Validate phone
            if (!isValidPhone(phone)) {
                setTextFieldErrStyle(phoneField);
                throw new Exception("Phone number must contain exactly 10 digits");
            }

            // Format phone number: (111) 222-3333
            String formattedPhone = formatPhoneNum(phone);

            // Create a new Employee object
//            Employee employee = new Employee(
//                firstName, lastName, email, formattedPhone,
//                    hireDate, salary
//            );

            // Save employee to the database
//            Db.DbResult dbResult = empRepo.insertEmployee(employee);

//            if (!dbResult.success) {
//                throw new Exception(dbResult.message);
//            }

            // Success message and clear fields
//            alertMsg(dbResult.message, Alert.AlertType.INFORMATION);
//            clearFields();
//            roleCombo.setStyle("");
        } catch (Exception ex) {
            // Show warning for any exception
            alertMsg(ex.getMessage(), Alert.AlertType.WARNING);
        }
    }
}
