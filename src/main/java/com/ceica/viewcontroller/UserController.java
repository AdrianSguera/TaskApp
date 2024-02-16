package com.ceica.viewcontroller;

import com.ceica.controller.AppController;
import javafx.fxml.FXML;
import javafx.scene.control.*;

public class UserController {

    @FXML
    private TextField txtTitle;

    @FXML
    private TextArea txtDescription;

    @FXML
    private DatePicker deadline;

    @FXML
    private Label lblMessage;

    @FXML
    private Menu userLogged;

    @FXML
    public void initialize(){
        AppController appController = new AppController();
        //userLogged.setText(appController.getUserLogged().getUsername());
    }

    @FXML
    public void addNew(){
        AppController appController = new AppController();
        if (appController.newTask(txtTitle.getText(),txtDescription.getText(),deadline.getValue().atStartOfDay())){
            clearFields();
            lblMessage.setText("Task added successfully");
        } else
            lblMessage.setText("Error creating task");
    }

    private void clearFields() {
        txtTitle.clear();
        txtDescription.clear();
        deadline.setValue(null);
    }
}
