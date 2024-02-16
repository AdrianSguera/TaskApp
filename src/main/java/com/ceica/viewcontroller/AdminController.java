package com.ceica.viewcontroller;

import com.ceica.controller.AppController;
import com.ceica.modelos.Rol;
import com.ceica.modelos.Task;
import com.ceica.modelos.User;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.input.MouseButton;
import javafx.util.StringConverter;

import java.time.LocalDateTime;
import java.util.Comparator;

public class AdminController {
    @FXML
    private TextField txtPassword, txtUsername;

    @FXML
    private Button btnSave, btnAdd, btnCancel, btnDelete;

    @FXML
    private ComboBox<Rol> comboBoxRol;

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
        AppController appController = new AppController();
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
        comboBoxRol.getItems().addAll(FXCollections.observableList(appController.getRoles()));
        refreshAndSortTable(tableViewUsers);
        refreshAndSortTable(tableViewTasks);
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
        AppController appController = new AppController();
        appController.newUser(txtUsername.getText(), txtPassword.getText(), comboBoxRol.getSelectionModel().getSelectedItem().getId());
        txtUsername.clear();
        txtPassword.clear();
        comboBoxRol.getSelectionModel().clearSelection();
        refreshAndSortTable(tableViewUsers);
    }

    @FXML
    public void saveToTable() {
        AppController appController = new AppController();
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
        AppController appController = new AppController();
        User selectedUser = tableViewUsers.getSelectionModel().getSelectedItem();
        appController.deleteUserById(selectedUser.getId());
        refreshAndSortTable(tableViewUsers);
        onCancel();
    }

    @FXML
    public void refreshAndSortTable(TableView<?> tableView) {
        AppController appController = new AppController();
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
}
