//
// COMP 2130 Java group project
//
// Human Resources Management System
//
// ---------- Members ----------
// Fellipe C.T.C - 101497831
// Ayesha Akbar --
// Claire Lee ---- 100882058
// Suthan Suresh -
//

package hrm.hrm_project;

import java.io.IOException;
import java.net.URL;

import hrm.hrm_project.domain.entities.AppConstants;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class Main extends Application {
    @Override
    public void start(Stage stage) throws IOException {
        URL url = getClass().getResource(AppConstants.LOGIN_FXML);
        Parent root = FXMLLoader.load(url);
        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.setResizable(false);
        stage.show();
    }

    public static void main(String[] args) {
        launch();
    }
}