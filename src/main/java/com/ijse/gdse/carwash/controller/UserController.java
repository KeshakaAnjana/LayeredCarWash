package com.ijse.gdse.carwash.controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.*;
import javafx.scene.layout.AnchorPane;

import java.io.IOException;

public class UserController {
    public Label usernamelable;
    public Label passwordlable;
    public Label userIdlable;
    public TextField usernametext;
    public PasswordField PasswordTxt;
    public TextField userIdtext;
    public Button loginbutton;
    public AnchorPane loginLayout;
    public Button Forgetpassword;

    String UserId = "u001";
    String Username = "keshaka";
    @FXML
    String Password = "123456";

    public void loginBtn(javafx.event.ActionEvent actionEvent) throws IOException {

        if ( userIdtext.getText().equals(UserId) && usernametext.getText().equals(Username) && PasswordTxt.getText().equals(Password)) {
        AnchorPane load = FXMLLoader.load(getClass().getResource("/view/MainLayout.fxml"));
        loginLayout.getChildren().clear();
        loginLayout.getChildren().add(load);
    }else{
            Alert alert = new Alert(Alert.AlertType.ERROR,"Invalid userId or username or password");
            alert.show();
        }
    }

    public void Forgetpassword(ActionEvent actionEvent) throws IOException {

            AnchorPane load = FXMLLoader.load(getClass().getResource("/view/ForgetPassword.fxml"));
            loginLayout.getChildren().clear();
            loginLayout.getChildren().add(load);
    }
}
