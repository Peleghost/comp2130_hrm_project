package hrm.hrm_project.presentation.controllers;

import java.util.ArrayList;
import java.util.List;

import hrm.hrm_project.domain.entities.Employee;
import hrm.hrm_project.infrastructure.data.Db;
import hrm.hrm_project.infrastructure.repositories.EmployeeRepository;
import hrm.hrm_project.utils.CommonUtil.ValidationResult;
import static hrm.hrm_project.utils.CommonUtil.alertMsg;
import static hrm.hrm_project.utils.CommonUtil.maxTextFieldChars;
import static hrm.hrm_project.utils.CommonUtil.setTextFieldErrStyle;
import static hrm.hrm_project.utils.CommonUtil.validateEmptyFields;
import static hrm.hrm_project.utils.EmployeeUtil.formatPhoneNum;
import static hrm.hrm_project.utils.EmployeeUtil.isValidEmail;
import static hrm.hrm_project.utils.EmployeeUtil.isValidPhone;
import static hrm.hrm_project.utils.EmployeeUtil.roleSelected;
import static hrm.hrm_project.utils.EmployeeUtil.validateName;
import static hrm.hrm_project.utils.EmployeeUtil.validateNewUserPassword;
import hrm.hrm_project.utils.PasswordUtil;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;

public class EmployeeController extends PageController {
    // For database operations
    private final EmployeeRepository empRepo = new EmployeeRepository();
    private String selectedRole;
    private final List<TextField> requiredFields = new ArrayList<>();

    @FXML private TextField firstNameField;
    @FXML private TextField lastNameField;
    @FXML private TextField emailField;
    @FXML private TextField phoneField;
    @FXML private TextField usernameField;
    @FXML private ComboBox<String> roleCombo;
    @FXML private TextField passwordField;
    @FXML private TextField confirmPasswordField;

    // Clear all fields in the form
    private void clearFields() {
        firstNameField.clear();
        lastNameField.clear();
        emailField.clear();
        phoneField.clear();
        usernameField.clear();
        roleCombo.setValue(null);
        passwordField.clear();
        confirmPasswordField.clear();
    }

    @FXML
    private void initialize() {
        // Set up role ComboBox
        roleCombo.setOnAction(event -> selectedRole = roleCombo.getValue());

        // Set maximum character limits for text fields
        maxTextFieldChars(firstNameField, 30);
        maxTextFieldChars(lastNameField, 30);
        maxTextFieldChars(emailField, 40);
        maxTextFieldChars(phoneField, 10);
        maxTextFieldChars(usernameField, 20);
        maxTextFieldChars(passwordField, 20);
        maxTextFieldChars(confirmPasswordField, 20);

        // Add all required fields for empty validation
        requiredFields.add(firstNameField);
        requiredFields.add(lastNameField);
        requiredFields.add(emailField);
        requiredFields.add(phoneField);
        requiredFields.add(usernameField);
        requiredFields.add(passwordField);
        requiredFields.add(confirmPasswordField);
    }

    @FXML
    private void handleSubmit() {
        try {
            // Collect field values
            String firstName = firstNameField.getText().trim();
            String lastName = lastNameField.getText().trim();
            String email = emailField.getText().trim();
            String phone = phoneField.getText().trim();
            String username = usernameField.getText().trim();
            String password = passwordField.getText().trim();
            String confirmPassword = confirmPasswordField.getText().trim();

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

            // Validate role selection
            if (!roleSelected(selectedRole)) {
                roleCombo.setStyle("-fx-background-color: red;");
                throw new Exception("Please select a role");
            }

            // Validate password
            ValidationResult passwordResult = validateNewUserPassword(password, confirmPassword);
            if (!passwordResult.isValid) {
                setTextFieldErrStyle(passwordField);
                setTextFieldErrStyle(confirmPasswordField);
                throw new Exception(passwordResult.errorMsg);
            }

            // Format phone number: (111) 222-3333
            String formattedPhone = formatPhoneNum(phone);

            // Hash the password
            String hashedPassword = PasswordUtil.hashPassword(password);

            // Create a new Employee object
            Employee employee = new Employee(
                username, hashedPassword, firstName, lastName, email, formattedPhone, selectedRole
            );

            // Save employee to the database
            Db.DbResult dbResult = empRepo.insertEmployee(employee);

            if (!dbResult.success) {
                throw new Exception(dbResult.message);
            }

            // Success message and clear fields
            alertMsg(dbResult.message, Alert.AlertType.INFORMATION);
            clearFields();
            roleCombo.setStyle("");
        } catch (Exception ex) {
            // Show warning for any exception
            alertMsg(ex.getMessage(), Alert.AlertType.WARNING);
        }
    }
}
