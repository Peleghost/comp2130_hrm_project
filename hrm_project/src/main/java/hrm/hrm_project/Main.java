//
// COMP 2130 Java group project
//
// Human Resources Management System
//
// ---------- Members ----------
// Fellipe C.T.C - 101497831
// Ayesha Akbar --
// Claire Lee ----
// Suthan Suresh -
//

package hrm.hrm_project;

import hrm.hrm_project.domain.entities.Employee;
import hrm.hrm_project.domain.interfaces.IEmployee;
import hrm.hrm_project.infrastructure.repositories.EmployeeRepository;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;
import java.sql.SQLException;

public class Main extends Application {
    @Override
    public void start(Stage stage) throws IOException, SQLException {
        FXMLLoader fxmlLoader = new FXMLLoader(Main.class.getResource("hello-view.fxml"));
        Scene scene = new Scene(fxmlLoader.load(), 320, 240);
        stage.setTitle("HRM System");
        stage.setScene(scene);
        stage.show();

        // EMPLOYEE TEST
        IEmployee empRepo = new EmployeeRepository();

        Employee emp1 = new Employee("test", "pass", "john",
                "doe", "email@example.com", "(123)123 1234");

        empRepo.insertEmployee(emp1);
    }

    public static void main(String[] args) {
        launch();
    }
}