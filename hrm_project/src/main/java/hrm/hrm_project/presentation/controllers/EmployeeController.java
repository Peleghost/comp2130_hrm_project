package hrm.hrm_project.presentation.controllers;

import hrm.hrm_project.domain.entities.Employee;
import hrm.hrm_project.domain.interfaces.IEmployee;
import hrm.hrm_project.infrastructure.data.Db;
import hrm.hrm_project.infrastructure.repositories.EmployeeRepository;
import hrm.hrm_project.utils.PasswordUtil;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;

import java.util.ArrayList;
import java.util.List;

import static hrm.hrm_project.utils.CommonUtil.*;
import static hrm.hrm_project.utils.EmployeeUtil.*;

public class EmployeeController extends PageController {
    // For DB operations
    private final IEmployee empRepo = new EmployeeRepository();
    //
//    public ComboBox<String> roleCombo;
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

    private void clearFields() {
        firstNameField.setText(null);
        lastNameField.setText(null);
        emailField.setText(null);
        phoneField.setText(null);
        usernameField.setText(null);
        roleCombo.valueProperty().setValue(null);
        passwordField.setText(null);
        confirmPasswordField.setText(null);
    }

    @FXML
    private void initialize() {
        roleCombo.setOnAction(event -> {
            selectedRole = roleCombo.getValue();
        });

        // Setting max input for text fields
        maxTextFieldChars(firstNameField, 30);
        maxTextFieldChars(lastNameField, 30);
        maxTextFieldChars(emailField, 40);
        maxTextFieldChars(phoneField, 10);
        maxTextFieldChars(usernameField, 20);
        maxTextFieldChars(passwordField, 20);
        maxTextFieldChars(confirmPasswordField, 20);

        // To check for any empty fields
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
            String fName = firstNameField.getText().trim();
            String lName = lastNameField.getText().trim();
            String email = emailField.getText().trim();
            String phoneNum = phoneField.getText().trim();
            String password = passwordField.getText().trim();
            String confirmPass = confirmPasswordField.getText().trim();
            String username = usernameField.getText().trim();

            // Fields' validation
            if(validateEmptyFields(requiredFields)) {
                throw new Exception("Fields cannot be empty");
            }

            ValidationResult fNameResult = validateName(fName);
            ValidationResult lNameResult = validateName(lName);

            if (!fNameResult.isValid) {
                throw new Exception(fNameResult.errorMsg);
            } else if (!lNameResult.isValid) {
                throw new Exception(lNameResult.errorMsg);
            }

            if (!isValidEmail(email)) {
                setTextFieldErrStyle(emailField);
                throw new Exception("Invalid email format");
            }

            if (!isValidPhone(phoneNum)) {
                setTextFieldErrStyle(phoneField);
                throw new Exception("Invalid phone number, make sure to input only 10 digits");
            }

            if (!roleSelected(selectedRole)) {
                roleCombo.setStyle("-fx-background-color: red;");
                throw new Exception("Must select a User Role");
            }

            ValidationResult passResult = validateNewUserPassword(password, confirmPass);
            if (!passResult.isValid) {
                setTextFieldErrStyle(passwordField);
                setTextFieldErrStyle(confirmPasswordField);

                throw new Exception(passResult.errorMsg);
            }

            // Format phone number - (111) 222-3333
            String formattedPhone = formatPhoneNum(phoneField.getText().trim());

            // Password hash
            String hashedPass = PasswordUtil.hashPassword(passwordField.getText().trim());

            // All good
            Employee employee = new Employee(username, hashedPass, fName, lName,
                                        email, formattedPhone, selectedRole);

            Db.DbResult dbResult = empRepo.insertEmployee(employee);

            if (!dbResult.success) {
                throw new Exception(dbResult.message);
            } else {
                alertMsg(dbResult.message, Alert.AlertType.INFORMATION);
                clearFields();
                roleCombo.setStyle("");
            }
        }
        catch (Exception ex) {
            alertMsg(ex.getMessage(), Alert.AlertType.WARNING);
        }
    }

}
