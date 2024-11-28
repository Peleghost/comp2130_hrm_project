package hrm.hrm_project.presentation.controllers;


import hrm.hrm_project.domain.entities.AppConstants;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;


public class PageController {

    private Stage stage;
    private Scene scene;
    private Parent root;


    public void switchToHome(ActionEvent event) throws IOException {
        URL url = getClass().getResource(AppConstants.HOME_FXML);
        Parent root = FXMLLoader.load(url);
        stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }

    public void switchToEmployee(ActionEvent event) throws IOException {
        URL url = getClass().getResource(AppConstants.EMPLOYEE_FXML);
        Parent root = FXMLLoader.load(url);
        stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }

    public void switchToPayroll(ActionEvent event) throws IOException {
        URL url = getClass().getResource(AppConstants.PAYROLL_FXML);
        Parent root = FXMLLoader.load(url);
        stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }

    public void switchToReporting(ActionEvent event) throws IOException {
        URL url = getClass().getResource(AppConstants.REPORTING_FXML);
        Parent root = FXMLLoader.load(url);
        stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }

    public void switchToLogout(ActionEvent event) throws IOException {
        URL url = getClass().getResource(AppConstants.LOGIN_FXML);
        Parent root = FXMLLoader.load(url);
        stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }
}


