package com.ceica.taskapp.viewcontroller;

import com.ceica.taskapp.TaskApp;
import com.ceica.taskapp.controller.AppController;
import com.ceica.taskapp.modelos.Task;
import com.ceica.taskapp.modelos.User;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.stage.Stage;

import java.io.IOException;
import java.time.LocalDateTime;
import java.util.Comparator;

public class UserController extends ViewController {

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
    private TableView<Task> tableView;

    @FXML
    private TableColumn<Task, Integer> clmId;

    @FXML
    private TableColumn<Task, String> clmTitle, clmDescription;

    @FXML
    private TableColumn<Task, LocalDateTime> clmCreationTime, clmDeadline;

    @FXML
    private TableColumn<Task, Boolean> clmStatus;

    private ObservableList<Task> taskList = FXCollections.observableArrayList();

    @FXML public void initialize(){
        clmId.setCellValueFactory(cellData -> new SimpleObjectProperty<>(cellData.getValue().getId()));
        clmTitle.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getTitle()));
        clmDescription.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getDescription()));
        clmStatus.setCellValueFactory(cellData -> new SimpleBooleanProperty(cellData.getValue().getStatus()));
        clmDeadline.setCellValueFactory(cellData ->
                new SimpleObjectProperty<>(cellData.getValue().getDeadline()));
        clmCreationTime.setCellValueFactory(cellData ->
                new SimpleObjectProperty<>(cellData.getValue().getCreation_time()));
    }

    @FXML
    public void addNew(){
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

    public void closeSession(){
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(TaskApp.class.getResource("login-view.fxml"));
            Parent root = fxmlLoader.load();
            Scene scene = new Scene(root, 600, 400);
            Stage currentStage = (Stage) lblMessage.getScene().getWindow();
            currentStage.close();
            Stage stage = new Stage();
            stage.setTitle("Login");
            stage.setScene(scene);
            stage.show();
        } catch (IOException ignored) {
        }
    }

    @Override
    public void cargaInicial() {
        userLogged.setText(appController.getUserLogged().getUsername());
        taskList = FXCollections.observableList(appController.getTasksByUser());
        taskList.sort(Comparator.comparing(Task::getId));
        tableView.setItems(taskList);
    }
}
