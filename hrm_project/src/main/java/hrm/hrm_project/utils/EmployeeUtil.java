package hrm.hrm_project.utils;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import hrm.hrm_project.utils.CommonUtil.ValidationResult;

public final class EmployeeUtil {
    // Private constructor to prevent instantiation
    private EmployeeUtil() {
        throw new AssertionError("This class cannot be instantiated");
    }

    public static ValidationResult validateName(String name) {
        Pattern pattern = Pattern.compile("[^A-z0-9]", Pattern.CASE_INSENSITIVE);
        Matcher m1 = pattern.matcher(name);

        boolean b1 = m1.find();

        if (b1) {
            return new ValidationResult(false, "Name cannot contain special characters");
        }

        if (name.length() > 30) {
            return new ValidationResult(false, "Name cannot be greater than 30 characters");
        }

        return new ValidationResult(true, "Success");
    }

    public static boolean isValidEmail(String email) {
        Pattern pattern = Pattern.compile("^(.+)@(.+)$");
        Matcher m = pattern.matcher(email);

        return m.matches();
    }

    public static boolean isValidPhone(String phoneNum) {
        return phoneNum.matches("\\d{0,10}") && phoneNum.length() >= 10;
    }

    public static String formatPhoneNum(String phoneNum) {
        return String.format("(%s) %s-%s",
                phoneNum.substring(0, 3),
                phoneNum.substring(3, 6),
                phoneNum.substring(6, 10));
    }

    public static boolean roleSelected(String selectedRole) {
        return selectedRole != null && !selectedRole.isEmpty();
    }

    public static ValidationResult validateNewUserPassword(String password, String confirmPassword) {
        if (password.length() < 9) {
            return new ValidationResult(false, "Password must contain at least 9 characters");
        }

        if (!confirmPassword.equals(password)) {
            return new ValidationResult(false, "Passwords don't match");
        }

        return new ValidationResult(true, "Success");
    }
}
