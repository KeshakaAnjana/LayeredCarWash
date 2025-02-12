package com.ijse.gdse.carwash.controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class MainLayoutController implements Initializable {

    public Button btnLogout;
    @FXML
    private AnchorPane content;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        //navigateTo("/view/CustomerView.fxml");
    }

    public void clickCustomer(ActionEvent actionEvent) {
        {
            navigateTo("/view/CustomerView.fxml");
        }
    }
    public void clickVehicle(ActionEvent actionEvent) {
        {
            navigateTo("/view/VehicleView.fxml");
        }
    }
    public void clickBooking(ActionEvent actionEvent) {
        {
            navigateTo("/view/BookingView.fxml");
        }
    }
    public void clickCustomerPayment(ActionEvent actionEvent) {
        {
            navigateTo("/view/Payment1View.fxml");
        }
    }
    public void clickServiceType(ActionEvent actionEvent) {
        {
            navigateTo("/view/ServiceTypeView.fxml");
        }
    }
    public void clickEmployee(ActionEvent actionEvent) {
        {
            navigateTo("/view/EmployeeView.fxml");
        }
    }
    public void clickProducts(ActionEvent actionEvent) {
        {
            navigateTo("/view/ProductView.fxml");
        }
    }

    public void clickSuppliers(ActionEvent actionEvent) {
        {
            navigateTo("/view/SupplierView.fxml");
        }
    }
    private void navigateTo(String fxmlPath) {
        try {
            content.getChildren().clear();
            AnchorPane load = FXMLLoader.load(getClass().getResource(fxmlPath));

            load.prefWidthProperty().bind(content.widthProperty());
            load.prefHeightProperty().bind(content.heightProperty());

            content.getChildren().add(load);
        } catch (IOException e) {
            e.printStackTrace();
            new Alert(Alert.AlertType.ERROR, "Fail to load page!").show();
        }
    }

    public void clickLogout(ActionEvent actionEvent) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/view/UserView.fxml"));
            AnchorPane root = loader.load();

            Scene scene = new Scene(root);

            Stage stage = (Stage) btnLogout.getScene().getWindow();

            stage.setScene(scene);
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
            new Alert(Alert.AlertType.ERROR, "Error loading the login screen.").show();
        }
    }
}
