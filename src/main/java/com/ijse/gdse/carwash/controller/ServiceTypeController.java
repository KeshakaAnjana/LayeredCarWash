package com.ijse.gdse.carwash.controller;

import com.ijse.gdse.carwash.bo.BOFactory;
import com.ijse.gdse.carwash.bo.custom.ServiceTypeBO;
import com.ijse.gdse.carwash.dao.DAOFactory;
import com.ijse.gdse.carwash.dao.custom.ServiceTypeDAO;
import com.ijse.gdse.carwash.dto.ServiceTypeDTO;
import com.ijse.gdse.carwash.dto.tm.ServiceTypeTM;
import com.ijse.gdse.carwash.dao.custom.impl.ServiceTypeDAOImpl;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Optional;
import java.util.ResourceBundle;

import java.net.URL;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;

public class ServiceTypeController implements Initializable {
    @FXML
    private Button btnBack;

    @FXML
    private Button btnDeleteServiceType;

    @FXML
    private Button btnReset;

    @FXML
    private Button btnSaveServiceType;

    @FXML
    private Button btnUpdateServiceType;

    @FXML
    private TableColumn<ServiceTypeTM, String> colPrice;

    @FXML
    private TableColumn<ServiceTypeTM, String> colServiceTypeId;

    @FXML
    private TableColumn<ServiceTypeTM, String> colServiceTypeName;

    @FXML
    private Label lblServiceId;

    @FXML
    private TableView<ServiceTypeTM> tblServiceType;

    @FXML
    private TextField txtPrice;

    @FXML
    private TextField txtServiceType;

    ServiceTypeBO serviceTypeBO = (ServiceTypeBO) BOFactory.getInstance().getBO(BOFactory.BOType.SERVICETYPE);

    public void initialize(URL url, ResourceBundle resourceBundle) {

        colServiceTypeId.setCellValueFactory(new PropertyValueFactory<>("ServiceId"));
        colServiceTypeName.setCellValueFactory(new PropertyValueFactory<>("ServiceTypeName"));
        colPrice.setCellValueFactory(new PropertyValueFactory<>("Price"));

        try {
            refreshPage();
        } catch (Exception e) {
            e.printStackTrace();
            new Alert(Alert.AlertType.ERROR, "Fail to load service id").show();
        }
    }

    private void refreshPage() throws SQLException, ClassNotFoundException {

        loadNextServiceId();
        loadTableData();

        btnSaveServiceType.setDisable(false);
        btnUpdateServiceType.setDisable(true);
        btnDeleteServiceType.setDisable(true);

        txtServiceType.setText("");
        txtPrice.setText("");

    }

    private void loadTableData() throws SQLException, ClassNotFoundException {
        ArrayList<ServiceTypeDTO> serviceTypeDTOS = serviceTypeBO.getAll();

        ObservableList<ServiceTypeTM> serviceTypeTMS = FXCollections.observableArrayList();

        for (ServiceTypeDTO serviceTypeDTO : serviceTypeDTOS) {
            ServiceTypeTM serviceTypeTM = new ServiceTypeTM(
                    serviceTypeDTO.getServiceId(),
                    serviceTypeDTO.getServiceTypeName(),
                    serviceTypeDTO.getPrice()
            );
            serviceTypeTMS.add(serviceTypeTM);
        }

        tblServiceType.setItems(serviceTypeTMS);
    }

    public void loadNextServiceId() throws SQLException, ClassNotFoundException {
        String nextServiceId = serviceTypeBO.getNext();
        lblServiceId.setText(nextServiceId);
    }

    public void btnResetOnAction(ActionEvent actionEvent) throws SQLException, ClassNotFoundException {
        refreshPage();
    }

    public void btnDeleteServiceTypeOnAction(ActionEvent actionEvent) throws SQLException, ClassNotFoundException {
        String ServiceId = lblServiceId.getText();

        Alert alert = new Alert(Alert.AlertType.CONFIRMATION, "Are you sure?", ButtonType.YES, ButtonType.NO);
        Optional<ButtonType> optionalButtonType = alert.showAndWait();

        if (optionalButtonType.isPresent() && optionalButtonType.get() == ButtonType.YES) {

            boolean isDeleted = serviceTypeBO.delete(ServiceId);
            if (isDeleted) {
                refreshPage();
                new Alert(Alert.AlertType.INFORMATION, " deleted...!").show();
            } else {
                new Alert(Alert.AlertType.ERROR, "Fail to delete...!").show();
            }
        }
    }

    public void btnUpdateServiceTypeOnAction(ActionEvent actionEvent) throws SQLException, ClassNotFoundException {
        String ServiceId = lblServiceId.getText();
        String ServiceTypeName = txtServiceType.getText();
        String Price = txtPrice.getText();

        txtServiceType.setStyle(txtServiceType.getStyle() + ";-fx-border-color: #045ae6;");
        txtPrice.setStyle(txtPrice.getStyle() + ";-fx-border-color: #045ae6;");

        String ServiceTypeNamePattern = "^[A-Za-z ]+$";
        String PricePattern = "^(\\d+(\\.\\d{1,2})?)$";

        boolean isValidServiceTypeName = ServiceTypeName.matches(ServiceTypeNamePattern);
        boolean isValidPrice = Price.matches(PricePattern);

        if (!isValidServiceTypeName) {
            System.out.println(txtServiceType.getStyle());
            txtServiceType.setStyle(txtServiceType.getStyle() + ";-fx-border-color: red;");
            System.out.println("Invalid .............");
        }

        if (!isValidPrice) {
            txtPrice.setStyle(txtPrice.getStyle() + ";-fx-border-color: red;");
        }


        if (isValidServiceTypeName && isValidPrice) {
            ServiceTypeDTO serviceTypeDTO = new ServiceTypeDTO(
                    ServiceId,
                    ServiceTypeName,
                    Price
            );

            boolean isUpdate = serviceTypeBO.update(serviceTypeDTO);
            if (isUpdate) {
                refreshPage();
                new Alert(Alert.AlertType.INFORMATION, "vehicle update...!").show();
            } else {
                new Alert(Alert.AlertType.ERROR, "Fail to update...!").show();
            }
        }
    }

    public void btnSaveServiceTypeOnAction(ActionEvent actionEvent) throws SQLException, ClassNotFoundException {
        String ServiceId = lblServiceId.getText();
        String ServiceTypeName = txtServiceType.getText();
        String Price = txtPrice.getText();

        txtServiceType.setStyle(txtServiceType.getStyle() + ";-fx-border-color: #045ae6;");
        txtPrice.setStyle(txtPrice.getStyle() + ";-fx-border-color: #045ae6;");

        String ServiceTypeNamePattern = "^[A-Za-z ]+$";
        String PricePattern = "^(\\d+(\\.\\d{1,2})?)$";

        boolean isValidServiceTypeName = ServiceTypeName.matches(ServiceTypeNamePattern);
        boolean isValidPrice = Price.matches(PricePattern);

        if (!isValidServiceTypeName) {
            System.out.println(txtServiceType.getStyle());
            txtServiceType.setStyle(txtServiceType.getStyle() + ";-fx-border-color: red;");
            System.out.println("Invalid .............");
        }

        if (!isValidPrice) {
            txtPrice.setStyle(txtPrice.getStyle() + ";-fx-border-color: red;");
        }


        if (isValidServiceTypeName && isValidPrice) {
            ServiceTypeDTO serviceTypeDTO = new ServiceTypeDTO(
                    ServiceId,
                    ServiceTypeName,
                    Price
            );

            boolean isSaved = serviceTypeBO.save(serviceTypeDTO);
            if (isSaved) {
                refreshPage();
                new Alert(Alert.AlertType.INFORMATION, "vehicle saved...!").show();
            } else {
                new Alert(Alert.AlertType.ERROR, "Fail to save vehicle...!").show();
            }
        }
    }

    public void onClickTable(MouseEvent mouseEvent) {
        ServiceTypeTM serviceTypeTM = tblServiceType.getSelectionModel().getSelectedItem();
        if (serviceTypeTM != null) {
            lblServiceId.setText(serviceTypeTM.getServiceId());
            txtServiceType.setText(serviceTypeTM.getServiceTypeName());
            txtPrice.setText(serviceTypeTM.getPrice());

            btnSaveServiceType.setDisable(true);
            btnUpdateServiceType.setDisable(false);
            btnDeleteServiceType.setDisable(false);
        }
    }

    public void clickBackbtn(ActionEvent actionEvent) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/view/MainLayout.fxml"));
            AnchorPane root = loader.load();

            Scene scene = new Scene(root);

            Stage stage = (Stage) btnBack.getScene().getWindow();

            stage.setScene(scene);
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
            new Alert(Alert.AlertType.ERROR, "Error loading the login screen.").show();
        }
    }
}


