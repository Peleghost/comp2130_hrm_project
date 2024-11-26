module hrm.hrm_project {
    requires javafx.controls;
    requires javafx.fxml;
    requires javafx.web;

    requires org.controlsfx.controls;
    requires com.dlsc.formsfx;
    requires net.synedra.validatorfx;
    requires org.kordamp.bootstrapfx.core;
    //requires eu.hansolo.tilesfx;
    requires java.sql;

    opens hrm.hrm_project to javafx.fxml;
    exports hrm.hrm_project;
    exports hrm.hrm_project.presentation.controllers;
    opens hrm.hrm_project.presentation.controllers to javafx.fxml;
}