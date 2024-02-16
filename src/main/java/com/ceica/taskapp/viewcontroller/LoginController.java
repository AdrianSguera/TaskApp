package com.ceica.taskapp.viewcontroller;

import com.ceica.taskapp.TaskApp;
import com.ceica.taskapp.controller.AppController;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.io.IOException;

public class LoginController {

    @FXML
    private TextField txtUsername;

    @FXML
    private PasswordField txtPassword;
    @FXML
    private Label lblMessage;

    public void onLoginClick() {
        AppController appController = new AppController();

        if (appController.login(txtUsername.getText(), txtPassword.getText())) {
            String view;
            Scene scene;
            String title;
            try {
                if (appController.isAdmin(appController.getUserLogged())) {
                    view="admin-view.fxml";
                    title = "Admin menu";
                } else {
                    view="user-view.fxml";
                    title = "User menu";
                }
                FXMLLoader fxmlLoader = new FXMLLoader(TaskApp.class.getResource(view));
                Parent root = fxmlLoader.load();
                ViewController viewController = fxmlLoader.getController();
                viewController.setAppController(appController);

                scene = new Scene(root, 600, 400);
                Stage currentStage = (Stage) lblMessage.getScene().getWindow();
                currentStage.close();
                Stage stage = new Stage();
                stage.setTitle(title);
                stage.setScene(scene);
                stage.show();

            } catch (Exception e) {
                e.printStackTrace();
            }
        } else {
            System.out.println("Incorrect user or password");
        }
    }
}
