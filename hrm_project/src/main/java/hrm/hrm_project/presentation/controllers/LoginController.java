package hrm.hrm_project.presentation.controllers;

import hrm.hrm_project.infrastructure.repositories.EmployeeRepository;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;

public class LoginController {
    private final EmployeeRepository employeeRepository = new EmployeeRepository();

    @FXML private TextField usernameField;
    @FXML private PasswordField passwordField;

    @FXML
    private void handleLogin() {
        try {
            String username = usernameField.getText().trim();
            String password = passwordField.getText().trim();

            if (username.isEmpty() || password.isEmpty()) {
                throw new Exception("Username and password cannot be empty");
            }

            boolean isAuthenticated = employeeRepository.login(username, password);

            if (isAuthenticated) {
                showAlert("Login successful!", Alert.AlertType.INFORMATION);
                navigateToHome();
            } else {
                throw new Exception("Invalid username or password");
            }
        } catch (Exception ex) {
            showAlert(ex.getMessage(), Alert.AlertType.ERROR);
        }
    }

    private void navigateToHome() {
        try {
            PageController.navigateTo("home.fxml");
        } catch (Exception ex) {
            showAlert("Failed to load the home screen: " + ex.getMessage(), Alert.AlertType.ERROR);
        }
    }

    private void showAlert(String message, Alert.AlertType type) {
        Alert alert = new Alert(type);
        alert.setContentText(message);
        alert.show();
    }
}
