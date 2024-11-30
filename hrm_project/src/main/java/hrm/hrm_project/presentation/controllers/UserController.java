package hrm.hrm_project.presentation.controllers;

import hrm.hrm_project.domain.entities.User;
import hrm.hrm_project.domain.interfaces.IUser;
import hrm.hrm_project.infrastructure.repositories.UserRepository;
import hrm.hrm_project.utils.CommonUtil;
import hrm.hrm_project.utils.PasswordUtil;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.ComboBox;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;

import java.util.ArrayList;
import java.util.List;

import static hrm.hrm_project.utils.CommonUtil.*;
import static hrm.hrm_project.utils.EmployeeUtil.roleSelected;
import static hrm.hrm_project.utils.EmployeeUtil.validateNewUserPassword;

public class UserController extends PageController {
    private final IUser userRepo = new UserRepository();
    private List<TextField> requiredFields = new ArrayList<>();
    private String selectedRole;

    @FXML private TextField usernameField;
    @FXML private ComboBox<String> roleCombo;
    @FXML private PasswordField passwordField;
    @FXML private PasswordField confirmPasswordField;

    private void clearFields() {
        usernameField.clear();
        roleCombo.setValue(null);
        passwordField.clear();
        confirmPasswordField.clear();
    }

    @FXML
    private void initialize() {
        // Set up role ComboBox
        roleCombo.setOnAction(event -> selectedRole = roleCombo.getValue());

        maxTextFieldChars(usernameField, 20);
        maxTextFieldChars(passwordField, 20);
        maxTextFieldChars(confirmPasswordField, 20);

        requiredFields.add(usernameField);
        requiredFields.add(passwordField);
        requiredFields.add(confirmPasswordField);
    }

    @FXML
    private void handleSubmit() {
        try {
            String username = usernameField.getText().trim();
            String password = passwordField.getText().trim();
            String confirmPassword = confirmPasswordField.getText().trim();

            // Validate fields
            if (validateEmptyFields(requiredFields)) {
                throw new Exception("All fields are required");
            }

            // Validate role selection
            if (!roleSelected(selectedRole)) {
                roleCombo.setStyle("-fx-background-color: red;");
                throw new Exception("Please select a role");
            }

            // Validate password
            CommonUtil.ValidationResult passwordResult = validateNewUserPassword(password, confirmPassword);
            if (!passwordResult.isValid) {
                setTextFieldErrStyle(passwordField);
                setTextFieldErrStyle(confirmPasswordField);
                throw new Exception(passwordResult.errorMsg);
            }

            // Hash the password
            String hashedPassword = PasswordUtil.hashPassword(password);

            User user = new User(username, hashedPassword, selectedRole);


        } catch (Exception ex) {
            alertMsg(ex.getMessage(), Alert.AlertType.WARNING);
        }
    }
}
