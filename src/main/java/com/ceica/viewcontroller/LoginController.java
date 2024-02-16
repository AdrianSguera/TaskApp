package com.ceica.viewcontroller;

import com.ceica.controller.AppController;
import com.ceica.modelos.User;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
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
        FXMLLoader fxmlLoader;
        if (appController.login(txtUsername.getText(), txtPassword.getText())) {
            fxmlLoader = new FXMLLoader();
            Scene scene;
            String title;
            try {
                if (appController.isAdmin(appController.getUserLogged())) {
                    fxmlLoader.setLocation(getClass().getResource("/com/ceica/taskapp/admin-view.fxml"));
                    title = "Admin menu";
                } else {
                    fxmlLoader.setLocation(getClass().getResource("/com/ceica/taskapp/user-view.fxml"));
                    title = "User menu";
                }

                scene = new Scene(fxmlLoader.load(), 600, 400);
                Stage currentStage = (Stage) lblMessage.getScene().getWindow();
                currentStage.close();
                Stage stage = new Stage();
                stage.setTitle(title);
                stage.setScene(scene);
                stage.show();

            } catch (IOException ignored) {
            }
        } else {
            System.out.println("Incorrect user or password");
        }
    }
}
