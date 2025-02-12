package com.ijse.gdse.carwash.controller;

import com.ijse.gdse.carwash.bo.BOFactory;
import com.ijse.gdse.carwash.bo.custom.SupplierBO;
import com.ijse.gdse.carwash.dao.DAOFactory;
import com.ijse.gdse.carwash.dao.custom.SupplierDAO;
import com.ijse.gdse.carwash.db.DBConnection;
import com.ijse.gdse.carwash.dto.SupplierDTO;
import com.ijse.gdse.carwash.dto.tm.SupplierTM;
import com.ijse.gdse.carwash.dao.custom.impl.SupplierDAOImpl;
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
import net.sf.jasperreports.engine.*;
import net.sf.jasperreports.view.JasperViewer;

import java.io.IOException;
import java.net.URL;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Optional;
import java.util.ResourceBundle;

public class SupplierController implements Initializable {
    @FXML
    private AnchorPane AnchorPane;

    @FXML
    private Button SupplierPaymentbtn;

    @FXML
    private Button btnBack;

    @FXML
    private Button btnDeleteSupplier;

    @FXML
    private Button btnReset;

    @FXML
    private Button btnSaveSupplier;

    @FXML
    private Button btnUpdateSupplier;

    @FXML
    private TableColumn<SupplierTM, String> colContactNo;

    @FXML
    private TableColumn<SupplierTM, String> colName;

    @FXML
    private TableColumn<SupplierTM, String> colStatus;

    @FXML
    private TableColumn<SupplierTM, String> colSupplierId;

    @FXML
    private TableColumn<SupplierTM, String> colUnitPrice;

    @FXML
    private TableColumn<SupplierTM, String> colQty;

    @FXML
    private Label lblSupplierId;

    @FXML
    private TableView<SupplierTM> tblSupplier;

    @FXML
    private TextField txtContactNo;

    @FXML
    private TextField txtName;

    @FXML
    private TextField txtQty;

    @FXML
    private TextField txtStatus;

    @FXML
    private TextField txtUnitPrice;

    SupplierBO supplierBO = (SupplierBO) BOFactory.getInstance().getBO(BOFactory.BOType.SUPPLIER);

    public void initialize(URL url, ResourceBundle resourceBundle) {

        colSupplierId.setCellValueFactory(new PropertyValueFactory<>("SupplierId"));
        colName.setCellValueFactory(new PropertyValueFactory<>("SupplierName"));
        colStatus.setCellValueFactory(new PropertyValueFactory<>("Status"));
        colContactNo.setCellValueFactory(new PropertyValueFactory<>("ContactNo"));
        colUnitPrice.setCellValueFactory(new PropertyValueFactory<>("UnitPrice"));
        colQty.setCellValueFactory(new PropertyValueFactory<>("Qty"));

        try {
            refreshPage();
        } catch (Exception e) {
            e.printStackTrace();
            new Alert(Alert.AlertType.ERROR, "Fail to load supplier id").show();
        }
    }

    private void refreshPage() throws SQLException, ClassNotFoundException {

        loadNextSupplierId();
        loadTableData();

        btnSaveSupplier.setDisable(false);
        btnUpdateSupplier.setDisable(true);
        btnDeleteSupplier.setDisable(true);

        txtName.setText("");
        txtStatus.setText("");
        txtContactNo.setText("");
        txtUnitPrice.setText("");
        txtQty.setText("");

    }

    private void loadTableData() throws SQLException, ClassNotFoundException {
        ArrayList<SupplierDTO> supplierDTOS = supplierBO.getAll();

        ObservableList<SupplierTM> supplierTMS = FXCollections.observableArrayList();

        for (SupplierDTO supplierDTO : supplierDTOS) {
            SupplierTM supplierTM = new SupplierTM(
                    supplierDTO.getSupplierId(),
                    supplierDTO.getSupplierName(),
                    supplierDTO.getStatus(),
                    supplierDTO.getContactNo(),
                    supplierDTO.getUnitPrice(),
                    supplierDTO.getQty()
            );
            supplierTMS.add(supplierTM);
        }

        tblSupplier.setItems(supplierTMS);
    }

    public void loadNextSupplierId() throws SQLException, ClassNotFoundException {
        String nextSupplierId = supplierBO.getNext();
        lblSupplierId.setText(nextSupplierId);
    }

    public void btnResetOnAction(ActionEvent actionEvent) throws SQLException, ClassNotFoundException {
        refreshPage();
    }

    public void btnDeleteSupplierOnAction(ActionEvent actionEvent) throws SQLException, ClassNotFoundException {
        String SupplierId = lblSupplierId.getText();

        Alert alert = new Alert(Alert.AlertType.CONFIRMATION, "Are you sure?", ButtonType.YES, ButtonType.NO);
        Optional<ButtonType> optionalButtonType = alert.showAndWait();

        if (optionalButtonType.isPresent() && optionalButtonType.get() == ButtonType.YES) {

            boolean isDeleted = supplierBO.delete(SupplierId);
            if (isDeleted) {
                refreshPage();
                new Alert(Alert.AlertType.INFORMATION, " deleted...!").show();
            } else {
                new Alert(Alert.AlertType.ERROR, "Fail to delete...!").show();
            }
        }
    }

    public void btnUpdateSupplierOnAction(ActionEvent actionEvent) throws SQLException, ClassNotFoundException {
        String SupplierId = lblSupplierId.getText();
        String SupplierName = txtName.getText();
        String Status = txtStatus.getText();
        String ContactNo = txtContactNo.getText();
        String UnitPrice = txtUnitPrice.getText();
        String Qty = txtQty.getText();

        txtName.setStyle(txtName.getStyle() + ";-fx-border-color: #045ae6;");
        txtStatus.setStyle(txtStatus.getStyle() + ";-fx-border-color: #045ae6;");
        txtContactNo.setStyle(txtContactNo.getStyle() + ";-fx-border-color: #045ae6;");
        txtUnitPrice.setStyle(txtUnitPrice.getStyle() + ";-fx-border-color: #045ae6;");
        txtQty.setStyle(txtQty.getStyle() + ";-fx-border-color: #045ae6;");

        String NamePattern = "^[A-Za-z ]+$";
        String StatusPattern = "^[A-Za-z ]+$";
        String ContactNoPattern = "^07\\d{8}$";
        String UnitPricePattern = "^(\\d+(\\.\\d{1,2})?)$";
        String QtyPattern = "^(\\d+)$";

        boolean isValidName = SupplierName.matches(NamePattern);
        boolean isValidStatus = Status.matches(StatusPattern);
        boolean isValidContactNo = ContactNo.matches(ContactNoPattern);
        boolean isUnitPrice = UnitPrice.matches(UnitPricePattern);
        boolean isValidQty = Qty.matches(QtyPattern);

        if (!isValidName) {
            System.out.println(txtName.getStyle());
            txtName.setStyle(txtName.getStyle() + ";-fx-border-color: red;");
            System.out.println("Invalid .............");
        }
        if (!isValidStatus) {
            txtStatus.setStyle(txtStatus.getStyle() + ";-fx-border-color: red;");
        }
        if (!isValidContactNo) {
            txtContactNo.setStyle(txtContactNo.getStyle() + ";-fx-border-color: red;");
        }
        if (!isUnitPrice) {
            txtUnitPrice.setStyle(txtUnitPrice.getStyle() + ";-fx-border-color: red;");
        }
        if (!isValidQty) {
            txtQty.setStyle(txtQty.getStyle() + ";-fx-border-color: red;");
        }

        if (isValidName && isValidStatus && isValidContactNo && isUnitPrice && isValidQty) {
            SupplierDTO supplierDTO = new SupplierDTO(
                    SupplierId,
                    SupplierName,
                    Status,
                    ContactNo,
                    UnitPrice,
                    Qty
            );

            boolean isUpdate = supplierBO.update(supplierDTO);
            if (isUpdate) {
                refreshPage();
                new Alert(Alert.AlertType.INFORMATION, "supplier update...!").show();
            } else {
                new Alert(Alert.AlertType.ERROR, "Fail to update supplier...!").show();
            }
        }
    }

    public void btnSaveSupplierOnAction(ActionEvent actionEvent) throws SQLException, ClassNotFoundException {
        String SupplierId = lblSupplierId.getText();
        String SupplierName = txtName.getText();
        String Status = txtStatus.getText();
        String ContactNo = txtContactNo.getText();
        String UnitPrice = txtUnitPrice.getText();
        String Qty = txtQty.getText();

        txtName.setStyle(txtName.getStyle() + ";-fx-border-color: #045ae6;");
        txtStatus.setStyle(txtStatus.getStyle() + ";-fx-border-color: #045ae6;");
        txtContactNo.setStyle(txtContactNo.getStyle() + ";-fx-border-color: #045ae6;");
        txtUnitPrice.setStyle(txtUnitPrice.getStyle() + ";-fx-border-color: #045ae6;");
        txtQty.setStyle(txtQty.getStyle() + ";-fx-border-color: #045ae6;");

        String NamePattern = "^[A-Za-z ]+$";
        String StatusPattern = "^[A-Za-z ]+$";
        String ContactNoPattern = "^07\\d{8}$";
        String UnitPricePattern = "^(\\d+(\\.\\d{1,2})?)$";
        String QtyPattern = "^(\\d+)$";

        boolean isValidName = SupplierName.matches(NamePattern);
        boolean isValidStatus = Status.matches(StatusPattern);
        boolean isValidContactNo = ContactNo.matches(ContactNoPattern);
        boolean isUnitPrice = UnitPrice.matches(UnitPricePattern);
        boolean isValidQty = Qty.matches(QtyPattern);

        if (!isValidName) {
            System.out.println(txtName.getStyle());
            txtName.setStyle(txtName.getStyle() + ";-fx-border-color: red;");
            System.out.println("Invalid .............");
        }
        if (!isValidStatus) {
            txtStatus.setStyle(txtStatus.getStyle() + ";-fx-border-color: red;");
        }
        if (!isValidContactNo) {
            txtContactNo.setStyle(txtContactNo.getStyle() + ";-fx-border-color: red;");
        }
        if (!isUnitPrice) {
            txtUnitPrice.setStyle(txtUnitPrice.getStyle() + ";-fx-border-color: red;");
        }
        if (!isValidQty) {
            txtQty.setStyle(txtQty.getStyle() + ";-fx-border-color: red;");
        }

        if (isValidName && isValidStatus && isValidContactNo && isUnitPrice && isValidQty) {
            SupplierDTO supplierDTO = new SupplierDTO(
                    SupplierId,
                    SupplierName,
                    Status,
                    ContactNo,
                    UnitPrice,
                    Qty
            );

            boolean isSaved = supplierBO.save(supplierDTO);
            if (isSaved) {
                refreshPage();
                new Alert(Alert.AlertType.INFORMATION, "supplier saved...!").show();
            } else {
                new Alert(Alert.AlertType.ERROR, "Fail to save supplier...!").show();
            }
        }
    }

    public void generateAllSupplierReportOnAction(ActionEvent actionEvent) {
        try {
            JasperReport jasperReport = JasperCompileManager.compileReport(
                    getClass()
                            .getResourceAsStream("/report/supplier_report.jrxml"
                            ));

            Connection connection = DBConnection.getInstance().getConnection();

            JasperPrint jasperPrint = JasperFillManager.fillReport(
                    jasperReport,
                    null,
                    connection
            );

            JasperViewer.viewReport(jasperPrint, false);
        } catch (JRException e) {
            new Alert(Alert.AlertType.ERROR, "Fail to generate report...!").show();
        } catch (SQLException e) {
            new Alert(Alert.AlertType.ERROR, "DB error...!").show();
        }
    }


    public void onClicktable(MouseEvent mouseEvent) {
        SupplierTM supplierTM = tblSupplier.getSelectionModel().getSelectedItem();
        if (supplierTM != null) {
            lblSupplierId.setText(supplierTM.getSupplierId());
            txtName.setText(supplierTM.getSupplierName());
            txtStatus.setText(supplierTM.getStatus());
            txtContactNo.setText(supplierTM.getContactNo());
            txtUnitPrice.setText(supplierTM.getUnitPrice());
            txtQty.setText(supplierTM.getQty());

            btnSaveSupplier.setDisable(true);
            btnUpdateSupplier.setDisable(false);
            btnDeleteSupplier.setDisable(false);
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

    public void clickSupplierPayment(ActionEvent actionEvent) {
        try {
            AnchorPane load = FXMLLoader.load(getClass().getResource("/view/Payment2View.fxml"));
            AnchorPane.getChildren().clear();
            AnchorPane.getChildren().add(load);
        } catch (IOException e) {
            e.printStackTrace();
            new Alert(Alert.AlertType.ERROR, "Error loading the Supplier Payment...").show();
        }
    }
}
