package com.ijse.gdse.carwash.controller;

import com.ijse.gdse.carwash.bo.BOFactory;
import com.ijse.gdse.carwash.bo.custom.CustomerBO;
import com.ijse.gdse.carwash.bo.custom.VehicleBO;
import com.ijse.gdse.carwash.dao.DAOFactory;
import com.ijse.gdse.carwash.dao.custom.CustomerDAO;
import com.ijse.gdse.carwash.dao.custom.VehicleDAO;
import com.ijse.gdse.carwash.dto.CustomerDTO;
import com.ijse.gdse.carwash.dto.tm.VehicleTM;
import com.ijse.gdse.carwash.dao.custom.impl.CustomerDAOImpl;
import com.ijse.gdse.carwash.dao.custom.impl.VehicleDAOImpl;
import com.ijse.gdse.carwash.entity.Customer;
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
import com.ijse.gdse.carwash.dto.VehicleDTO;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;


import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Optional;
import java.util.ResourceBundle;

public class VehicleController implements Initializable {

    public Button btnDeleteVehicle;
    @FXML
    private Button Backbtn;

    @FXML
    private Button btnSaveVehicle;

    @FXML
    private Button btnUpdateVehicle;

    @FXML
    private ComboBox<String> cmbCustomerId;

    @FXML
    private TableColumn<VehicleTM, String> colCustomerId;

    @FXML
    private TableColumn<VehicleTM, String> colLicancePlate;

    @FXML
    private TableColumn<VehicleTM, String> colModel;

    @FXML
    private TableColumn<VehicleTM, String> colVehicleId;

    @FXML
    private Label customername;

    @FXML
    private Label lblVehicleId;

    @FXML
    private TableView<VehicleTM> tblVehicle;

    @FXML
    private TextField txtLicanceplate;

    @FXML
    private TextField txtModel;


    VehicleBO vehicleBO = (VehicleBO) BOFactory.getInstance().getBO(BOFactory.BOType.VEHICLE);
    CustomerBO customerBO = (CustomerBO) BOFactory.getInstance().getBO(BOFactory.BOType.CUSTOMER);

    public void initialize(URL url, ResourceBundle resourceBundle) {

        colVehicleId.setCellValueFactory(new PropertyValueFactory<>("VehicleId"));
        colLicancePlate.setCellValueFactory(new PropertyValueFactory<>("LicencePlate"));
        colModel.setCellValueFactory(new PropertyValueFactory<>("Model"));
        colCustomerId.setCellValueFactory(new PropertyValueFactory<>("CustomerId"));

        try {
            refreshPage();
        } catch (Exception e) {
            e.printStackTrace();
            new Alert(Alert.AlertType.ERROR, "Fail to load vehicle id").show();
        }
    }

    private void refreshPage() throws SQLException, ClassNotFoundException {

        loadCustomerId();
        loadNextVehicleId();
        loadTableData();

        btnSaveVehicle.setDisable(false);
        btnUpdateVehicle.setDisable(true);
        btnDeleteVehicle.setDisable(true);

        txtLicanceplate.setText("");
        txtModel.setText("");
        customername.setText("");
        cmbCustomerId.getSelectionModel().clearSelection();

    }

    private void loadTableData() throws SQLException, ClassNotFoundException {
        ArrayList<VehicleDTO> vehicleDTOS = vehicleBO.getAll();

        ObservableList<VehicleTM> vehicleTMS = FXCollections.observableArrayList();

        for (VehicleDTO vehicleDTO : vehicleDTOS) {
            VehicleTM vehicleTM = new VehicleTM(
                    vehicleDTO.getVehicleId(),
                    vehicleDTO.getLicencePlate(),
                    vehicleDTO.getModel(),
                    vehicleDTO.getCustomerId()
            );
            vehicleTMS.add(vehicleTM);
        }

        tblVehicle.setItems(vehicleTMS);
    }

    public void loadNextVehicleId() throws SQLException, ClassNotFoundException {
        String nextVehicleId = vehicleBO.getNext();
        lblVehicleId.setText(nextVehicleId);
    }
    private void loadCustomerId() throws SQLException, ClassNotFoundException {
        ArrayList<String> customerId = CustomerDAOImpl.getAllCustomerIds();
        ObservableList<String> observableList = FXCollections.observableArrayList();
        observableList.addAll(customerId);
        cmbCustomerId.setItems(observableList);
    }

    public void clickcmbCustomerId(ActionEvent actionEvent) throws SQLException, ClassNotFoundException {
        String selectedCustomerId = cmbCustomerId.getSelectionModel().getSelectedItem();
        Customer customerDTO = customerBO.findById(selectedCustomerId);

        if (customerDTO != null) {

            customername.setText(customerDTO.getName());
        }
    }

    public void btnUpdateVehicleOnAction(ActionEvent actionEvent) throws SQLException, ClassNotFoundException {
        String VehicleId = lblVehicleId.getText();
        String LicensePlate = txtLicanceplate.getText();
        String Model = txtModel.getText();
        String CustomerId = cmbCustomerId.getValue();

        txtLicanceplate.setStyle(txtLicanceplate.getStyle() + ";-fx-border-color: #045ae6;");
        txtModel.setStyle(txtModel.getStyle() + ";-fx-border-color: #045ae6;");
        cmbCustomerId.setStyle(cmbCustomerId.getStyle() + ";-fx-border-color: #045ae6;");

        String LicensePlatePattern = "^[A-Z]{2,3}\\d{4}$";
        String modelPattern = "^[A-Za-z ]+$";
        //String CustomerIdPattern = "^c\\d{3}$";

        boolean isValidLicensePlate = LicensePlate.matches(LicensePlatePattern);
        boolean isValidmodel = Model.matches(modelPattern);
       // boolean isValidCustomerId = CustomerId.matches(CustomerIdPattern);

        if (!isValidLicensePlate) {
            System.out.println(txtLicanceplate.getStyle());
            txtLicanceplate.setStyle(txtLicanceplate.getStyle() + ";-fx-border-color: red;");
            System.out.println("Invalid .............");
        }

        if (!isValidmodel) {
            txtModel.setStyle(txtModel.getStyle() + ";-fx-border-color: red;");
        }

//        if (!isValidCustomerId) {
//            cmbCustomerId.setStyle(cmbCustomerId.getStyle() + ";-fx-border-color: red;");
//        }

        if (isValidLicensePlate && isValidmodel) {
            VehicleDTO vehicleDTO = new VehicleDTO(
                    VehicleId,
                    LicensePlate,
                    Model,
                    CustomerId
            );

            boolean isUpdate = vehicleBO.update(vehicleDTO);
            if (isUpdate) {
                refreshPage();
                new Alert(Alert.AlertType.INFORMATION, " update...!").show();
            } else {
                new Alert(Alert.AlertType.ERROR, "Fail to update ...!").show();
            }
        }
    }

    public void btnSaveVehicleOnAction(ActionEvent actionEvent) throws SQLException, ClassNotFoundException {
        String VehicleId = lblVehicleId.getText();
        String LicensePlate = txtLicanceplate.getText();
        String Model = txtModel.getText();
        String CustomerId = cmbCustomerId.getValue();

        txtLicanceplate.setStyle(txtLicanceplate.getStyle() + ";-fx-border-color: #045ae6;");
        txtModel.setStyle(txtModel.getStyle() + ";-fx-border-color: #045ae6;");
        cmbCustomerId.setStyle(cmbCustomerId.getStyle() + ";-fx-border-color: #045ae6;");

        String LicensePlatePattern = "^[A-Z]{2,3}\\d{4}$";
        String modelPattern = "^[A-Za-z ]+$";
        //String CustomerIdPattern = "^c\\d{3}$";

        boolean isValidLicensePlate = LicensePlate.matches(LicensePlatePattern);
        boolean isValidmodel = Model.matches(modelPattern);
        //boolean isValidCustomerId = CustomerId.matches(CustomerIdPattern);

        if (!isValidLicensePlate) {
            System.out.println(txtLicanceplate.getStyle());
            txtLicanceplate.setStyle(txtLicanceplate.getStyle() + ";-fx-border-color: red;");
            System.out.println("Invalid .............");
        }

        if (!isValidmodel) {
            txtModel.setStyle(txtModel.getStyle() + ";-fx-border-color: red;");
        }


        if (isValidLicensePlate && isValidmodel) {
            VehicleDTO vehicleDTO = new VehicleDTO(
                    VehicleId,
                    LicensePlate,
                    Model,
                    CustomerId
            );

            boolean isSaved = vehicleBO.save(vehicleDTO);
            if (isSaved) {
                refreshPage();
                new Alert(Alert.AlertType.INFORMATION, "vehicle saved...!").show();
            } else {
                new Alert(Alert.AlertType.ERROR, "Fail to save vehicle...!").show();
            }
        }
    }

    public void btnDeleteVehicleOnAction(ActionEvent actionEvent) throws SQLException, ClassNotFoundException {
        String VehicleId = lblVehicleId.getText();

        Alert alert = new Alert(Alert.AlertType.CONFIRMATION, "Are you sure?", ButtonType.YES, ButtonType.NO);
        Optional<ButtonType> optionalButtonType = alert.showAndWait();

        if (optionalButtonType.isPresent() && optionalButtonType.get() == ButtonType.YES) {

            boolean isDeleted = vehicleBO.delete(VehicleId);
            if (isDeleted) {
                refreshPage();
                new Alert(Alert.AlertType.INFORMATION, " deleted...!").show();
            } else {
                new Alert(Alert.AlertType.ERROR, "Fail to delete...!").show();
            }
        }
    }

    public void onclickTable(MouseEvent mouseEvent) {
        VehicleTM vehicleTM = tblVehicle.getSelectionModel().getSelectedItem();
        if (vehicleTM != null) {
            lblVehicleId.setText(vehicleTM.getVehicleId());
            txtLicanceplate.setText(vehicleTM.getLicencePlate());
            txtModel.setText(vehicleTM.getModel());
            cmbCustomerId.setValue(vehicleTM.getCustomerId());

            btnSaveVehicle.setDisable(true);
            btnUpdateVehicle.setDisable(false);
            btnDeleteVehicle.setDisable(false);
        }
    }

    public void btnResetOnAction(ActionEvent actionEvent) throws SQLException, ClassNotFoundException {
        refreshPage();
    }

    public void clickBack(ActionEvent actionEvent) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/view/MainLayout.fxml"));
            AnchorPane root = loader.load();

            Scene scene = new Scene(root);

            Stage stage = (Stage) Backbtn.getScene().getWindow();

            stage.setScene(scene);
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
            new Alert(Alert.AlertType.ERROR, "Error loading the login screen.").show();
        }
    }
}


