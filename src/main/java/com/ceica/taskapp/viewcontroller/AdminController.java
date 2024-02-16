package com.ceica.taskapp.viewcontroller;

import com.ceica.taskapp.TaskApp;
import com.ceica.taskapp.modelos.Rol;
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
import javafx.scene.input.MouseButton;
import javafx.stage.Stage;
import javafx.util.StringConverter;

import java.io.IOException;
import java.time.LocalDateTime;
import java.util.Comparator;

public class AdminController extends ViewController {
    @FXML
    private TextField txtPassword, txtUsername;

    @FXML
    private Button btnSave, btnAdd, btnCancel, btnDelete;

    @FXML
    private ComboBox<Rol> comboBoxRol;

    @FXML
    private Menu userLogged;

    @FXML
    private TableView<User> tableViewUsers;

    @FXML
    private TableView<Task> tableViewTasks;

    @FXML
    private TableColumn<User, Integer> clmId;

    @FXML
    private TableColumn<User, String> clmUsername, clmRol;

    @FXML
    private TableColumn<Task, Integer> clmTaskId;

    @FXML
    private TableColumn<Task, String> clmTitle, clmDescription, clmUser;

    @FXML
    private TableColumn<Task, Boolean> clmStatus;

    @FXML
    private TableColumn<Task, LocalDateTime> clmCreationTime, clmDeadline;

    private ObservableList<Task> taskList = FXCollections.observableArrayList();

    private ObservableList<User> userList = FXCollections.observableArrayList();

    @FXML
    public void initialize() {
        clmId.setCellValueFactory(cellData -> new SimpleObjectProperty<>(cellData.getValue().getId()));
        clmUsername.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getUsername()));
        clmRol.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getRol().getDescription()));
        clmTaskId.setCellValueFactory(cellData -> new SimpleObjectProperty<>(cellData.getValue().getId()));
        clmTitle.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getTitle()));
        clmDescription.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getDescription()));
        clmStatus.setCellValueFactory(cellData -> new SimpleBooleanProperty(cellData.getValue().getStatus()));
        clmDeadline.setCellValueFactory(cellData ->
                new SimpleObjectProperty<>(cellData.getValue().getDeadline()));
        clmCreationTime.setCellValueFactory(cellData ->
                new SimpleObjectProperty<>(cellData.getValue().getCreation_time()));
        clmUser.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getUser().getUsername()));
        comboBoxRol.setConverter(new StringConverter<Rol>() {
            @Override
            public String toString(Rol rol) {

                return rol.getDescription();
            }

            @Override
            public Rol fromString(String s) {
                return null;
            }
        });

        tableViewUsers.setOnMouseClicked(event -> {
            if (event.getButton().equals(MouseButton.PRIMARY) && event.getClickCount() == 2) {
                User selectedUser = tableViewUsers.getSelectionModel().getSelectedItem();
                if (selectedUser != null) {
                    // Aquí puedes hacer lo que necesites con la fila seleccionada
                    txtUsername.setText(selectedUser.getUsername());
                    txtPassword.setText(selectedUser.getPassword());
                    comboBoxRol.getSelectionModel().select(selectedUser.getRol());
                    btnAdd.setVisible(false);
                    btnAdd.setDisable(true);
                    btnSave.setVisible(true);
                    btnSave.setDisable(false);
                    btnCancel.setVisible(true);
                    btnCancel.setDisable(false);
                    btnDelete.setVisible(true);
                    btnDelete.setDisable(false);
                }
            }
        });
    }

    @FXML
    public void addToTable() {
        appController.newUser(txtUsername.getText(), txtPassword.getText(), comboBoxRol.getSelectionModel().getSelectedItem().getId());
        txtUsername.clear();
        txtPassword.clear();
        comboBoxRol.getSelectionModel().clearSelection();
        refreshAndSortTable(tableViewUsers);
    }

    @FXML
    public void saveToTable() {
        User selectedUser = tableViewUsers.getSelectionModel().getSelectedItem();
        appController.changeUsernameUser(selectedUser.getId(), txtUsername.getText());
        appController.changePasswordUser(selectedUser.getUsername(), txtPassword.getText());
        appController.changeRolUser(selectedUser.getId(), comboBoxRol.getSelectionModel().getSelectedItem().getId());
        refreshAndSortTable(tableViewUsers);
        onCancel();
    }

    @FXML
    public void onCancel() {
        btnAdd.setVisible(true);
        btnAdd.setDisable(false);
        btnSave.setVisible(false);
        btnSave.setDisable(true);
        btnDelete.setVisible(false);
        btnDelete.setDisable(true);
        btnCancel.setVisible(false);
        btnCancel.setDisable(true);
        txtUsername.clear();
        txtPassword.clear();
        comboBoxRol.setValue(null);
    }

    @FXML
    public void onDelete() {
        User selectedUser = tableViewUsers.getSelectionModel().getSelectedItem();
        appController.deleteUserById(selectedUser.getId());
        refreshAndSortTable(tableViewUsers);
        onCancel();
    }

    @FXML
    public void refreshAndSortTable(TableView<?> tableView) {
        if (tableView == tableViewTasks) {
            taskList = FXCollections.observableList(appController.getTasks());
            taskList.sort(Comparator.comparing(Task::getId));
            tableViewTasks.setItems(taskList);
        } else {
            userList = FXCollections.observableList(appController.getUsers());
            userList.sort(Comparator.comparing(User::getId));
            tableViewUsers.setItems(userList);
        }
    }

    public void closeSession(){
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(TaskApp.class.getResource("login-view.fxml"));
            Parent root = fxmlLoader.load();
            Scene scene = new Scene(root, 600, 400);
            Stage currentStage = (Stage) txtPassword.getScene().getWindow();
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
        comboBoxRol.getItems().addAll(FXCollections.observableList(appController.getRoles()));
        refreshAndSortTable(tableViewUsers);
        refreshAndSortTable(tableViewTasks);
    }
}
