module com.ceica.taskapp {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.sql;


    opens com.ceica.viewcontroller to javafx.fxml;
    exports com.ceica.taskapp;
}