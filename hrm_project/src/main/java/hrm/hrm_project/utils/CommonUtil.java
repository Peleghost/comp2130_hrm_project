package hrm.hrm_project.utils;

import javafx.scene.control.Alert;
import javafx.scene.control.TextField;

import java.util.List;

// Common utility class to house common utility methods
// that can be used throughout the application
public final class CommonUtil {

    // Private constructor to prevent instantiation
    private CommonUtil() {
        throw new AssertionError("This class cannot be instantiated");
    }

    // Validation result class to use for general validations
    public static class ValidationResult {
        public boolean isValid;
        public String errorMsg;

        public ValidationResult(boolean isValid, String errorMsg) {
            this.isValid = isValid;
            this.errorMsg = errorMsg;
        }
    }

    // Methods
    // Set how many characters are allowed in a text field
    public static void maxTextFieldChars(TextField field, int maxLength) {
        field.textProperty().addListener(
                (observable, oldValue, newValue) -> {
                    if (newValue.length() > maxLength) {
                        field.setText(oldValue);
                    }
                }
        );
    }

    // Set the error outline for a text field
    public static void setTextFieldErrStyle(TextField field) {
        field.setStyle("-fx-border-color: red;");
    }

    public static boolean validateEmptyFields(List<TextField> fields) {
        int count = 0;
        for (TextField field : fields) {
            field.setStyle("");
            if (field.getText().trim().isEmpty()) {
                setTextFieldErrStyle(field);
                count++;
            }
        }

        return count != 0;
    }

    // Alert msg helper
    public static void alertMsg(String msg, Alert.AlertType alertType) {
        Alert alert = new Alert(alertType);
        alert.setContentText(msg);
        alert.show();
    }
}
