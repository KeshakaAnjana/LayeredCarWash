package com.ijse.gdse.carwash.controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

import java.io.IOException;

public class ForgetPasswordController {

    public Label lblusername;
    public TextField usernametxt;
    public Label lbluserId;
    public TextField userIdtxt;
    public Label lblnewPassword;
    public TextField newPasswordtxt;
    public Button btnsave;
    public Button btnback;

    private static final String CORRECT_USER_ID = "u001";
    private static final String CORRECT_USERNAME = "keshaka";

    private static String storedUserId;
    private static String storedUsername;
    private static String storedPassword;

    public void username(ActionEvent actionEvent) {
        String username = usernametxt.getText();

        if (username.isEmpty()) {
            new Alert(Alert.AlertType.WARNING, "Username cannot be empty").show();
        } else {
            System.out.println("Username entered: " + username);
        }
    }

    public void savebtn(ActionEvent actionEvent) throws IOException {
        String username = usernametxt.getText();
        String userId = userIdtxt.getText();
        String newPassword = newPasswordtxt.getText();

        if (username.isEmpty() || userId.isEmpty() || newPassword.isEmpty()) {
            new Alert(Alert.AlertType.ERROR, "Please fill all fields.").show();
            return;
        }

        if (userId.equals(CORRECT_USER_ID) && username.equals(CORRECT_USERNAME)) {
            storedUserId = userId;
            storedUsername = username;
            storedPassword = newPassword;

            new Alert(Alert.AlertType.INFORMATION, "Password updated successfully! You can now log in.").show();

            FXMLLoader loader = new FXMLLoader(getClass().getResource("/view/MainLayout.fxml"));
            AnchorPane root = loader.load();

            Scene scene = new Scene(root);

            Stage stage = (Stage) btnsave.getScene().getWindow();

            stage.setScene(scene);
            stage.show();

            usernametxt.clear();
            userIdtxt.clear();
            newPasswordtxt.clear();
        } else {
            new Alert(Alert.AlertType.ERROR, "Invalid User ID or Username").show();
        }
    }

    public void backbtn(ActionEvent actionEvent) throws IOException {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/view/UserView.fxml"));
            AnchorPane root = loader.load();

            Scene scene = new Scene(root);

            Stage stage = (Stage) btnback.getScene().getWindow();

            stage.setScene(scene);
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
            new Alert(Alert.AlertType.ERROR, "Error loading the login screen.").show();
        }
    }
}
