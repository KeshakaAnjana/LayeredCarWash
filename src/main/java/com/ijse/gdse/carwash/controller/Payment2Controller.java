package com.ijse.gdse.carwash.controller;

import com.ijse.gdse.carwash.bo.BOFactory;
import com.ijse.gdse.carwash.bo.custom.Payment2BO;
import com.ijse.gdse.carwash.bo.custom.SupplierBO;
import com.ijse.gdse.carwash.dao.DAOFactory;
import com.ijse.gdse.carwash.dao.custom.Payment2DAO;
import com.ijse.gdse.carwash.dao.custom.SupplierDAO;
import com.ijse.gdse.carwash.dto.Payment2DTO;
import com.ijse.gdse.carwash.dto.SupplierDTO;
import com.ijse.gdse.carwash.dto.tm.Payment2TM;
import com.ijse.gdse.carwash.dao.custom.impl.Payment2DAOImpl;
import com.ijse.gdse.carwash.dao.custom.impl.SupplierDAOImpl;
import com.ijse.gdse.carwash.entity.Supplier;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;

import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Optional;
import java.util.ResourceBundle;

public class Payment2Controller implements Initializable {
    @FXML
    private TextField txtSupplierId;

    @FXML
    private Label SupplierPDatelbl;

    @FXML
    private Button btnBack;

    @FXML
    private Button btnDeletePayment2;

    @FXML
    private Button btnReset;

    @FXML
    private Button btnSavePayment2;

    @FXML
    private Button btnUpdatePayment2;

    @FXML
    private ComboBox<String> cmbProductName;

    @FXML
    private TableColumn<Payment2TM, String> colAmount;

    @FXML
    private TableColumn<Payment2TM, String> colDate;

    @FXML
    private TableColumn<Payment2TM, String> colQty;

    @FXML
    private TableColumn<Payment2TM, String> colSupplierId;

    @FXML
    private TableColumn<Payment2TM, String> colSupplierPaymentId;

    @FXML
    private TableColumn<Payment2TM, String> colUnitPrice;

    @FXML
    private Label lblpayment2;

    @FXML
    private AnchorPane supplierAnchorPane;

    @FXML
    private TableView<Payment2TM> tblSupplierPayment;

    @FXML
    private TextField txtQty;

    @FXML
    private TextField txtUnitPrice;

    Payment2BO payment2BO = (Payment2BO) BOFactory.getInstance().getBO(BOFactory.BOType.PAYMENT2);
    SupplierBO supplierBO = (SupplierBO) BOFactory.getInstance().getBO(BOFactory.BOType.SUPPLIER);

    public void initialize(URL url, ResourceBundle resourceBundle) {
        colSupplierPaymentId.setCellValueFactory(new PropertyValueFactory<>("SupplierPaymentId"));
        colDate.setCellValueFactory(new PropertyValueFactory<>("Date"));
        colQty.setCellValueFactory(new PropertyValueFactory<>("Qty"));
        colUnitPrice.setCellValueFactory(new PropertyValueFactory<>("UnitPrice"));
        colAmount.setCellValueFactory(new PropertyValueFactory<>("Amount"));
        colSupplierId.setCellValueFactory(new PropertyValueFactory<>("SupplierId"));

        try {
            refreshPage();
        } catch (Exception e) {
            e.printStackTrace();
            new Alert(Alert.AlertType.ERROR, "Fail to load booking id" + e.getMessage()).show();
        }
    }

    private void refreshPage() throws Exception {
        loadSupplierId();
        loadNextPayment2Id();
        loadTableData();

        btnSavePayment2.setDisable(false);
        btnUpdatePayment2.setDisable(true);
        btnDeletePayment2.setDisable(true);

        SupplierPDatelbl.setText(LocalDate.now().toString());
        txtSupplierId.setText("");
        cmbProductName.getSelectionModel().clearSelection();
        txtQty.setText("");
        txtUnitPrice.setText("");
    }

    private void loadNextPayment2Id() throws SQLException, ClassNotFoundException {
        String nextPayment2 = payment2BO.getNext();
        lblpayment2.setText(nextPayment2);
    }

    private void loadSupplierId() throws SQLException, ClassNotFoundException {
        ArrayList<String> SupplierId = SupplierDAOImpl.getAllSupplierStatus();
        ObservableList<String> observableList = FXCollections.observableArrayList();
        observableList.addAll(SupplierId);
        cmbProductName.setItems(observableList);
    }

    private void loadTableData() throws SQLException, ClassNotFoundException {

        ArrayList<Payment2DTO> payment2DTOS = payment2BO.getAll();
        ObservableList<Payment2TM> payment2TMS = FXCollections.observableArrayList();

        for (Payment2DTO payment2DTO : payment2DTOS) {

           double Amount = Double.parseDouble(payment2DTO.getUnitPrice()) * Double.parseDouble(payment2DTO.getQty());

            Payment2TM payment2TM = new Payment2TM(
                    payment2DTO.getSupplierPaymentId(),
                    payment2DTO.getDate(),
                    payment2DTO.getSupplierId(),
                    payment2DTO.getQty(),
                    payment2DTO.getUnitPrice(),
                    String.valueOf(Amount)



            );
            payment2TMS.add(payment2TM);
        }
        tblSupplierPayment.setItems(payment2TMS);
    }

    public void clickcmbSupplierId(ActionEvent actionEvent) throws SQLException, ClassNotFoundException {
        String selectedStatus = cmbProductName.getSelectionModel().getSelectedItem();
        Supplier supplierDTO = supplierBO.findByName(selectedStatus);

        if (selectedStatus != null && supplierDTO != null) {
            String supplierId = supplierBO.SupplierIdFindByStatus(selectedStatus);
            txtSupplierId.setText(supplierId);
            txtQty.setText(supplierDTO.getQty());
            txtUnitPrice.setText(supplierDTO.getUnitPrice());

        }
    }

    public void btnResetOnAction(ActionEvent actionEvent) throws Exception {
        refreshPage();
    }

    public void btnDeletePayment2OnAction(ActionEvent actionEvent) throws Exception {
        String Payment2Id = lblpayment2.getText();

        Alert alert = new Alert(Alert.AlertType.CONFIRMATION,"Are you sure?",ButtonType.YES, ButtonType.NO);
        Optional<ButtonType> optionalButtonType = alert.showAndWait();

        if(optionalButtonType.isPresent() && optionalButtonType.get() == ButtonType.YES){
            boolean isDeleted = payment2BO.delete(Payment2Id);
            if (isDeleted) {
                refreshPage();
                new Alert(Alert.AlertType.INFORMATION, "batch deleted...!").show();
            } else {
                new Alert(Alert.AlertType.ERROR, "Fail to delete ...!").show();
            }
        }
    }

    public void btnUpdatePayment2OnAction(ActionEvent actionEvent) throws Exception {
        String SupplierPaymentId = lblpayment2.getText();
        String Date = SupplierPDatelbl.getText();
        String Amount = txtSupplierId.getText();
        String SupplierId = txtSupplierId.getText();
        String Qty = txtQty.getText();
        String UnitPrice = txtUnitPrice.getText();

        Payment2DTO payment2DTO = new Payment2DTO(
                SupplierPaymentId,
                Date,
                SupplierId,
                Qty,
                UnitPrice
        );
        boolean isUpdate = payment2BO.update(payment2DTO);
        if(isUpdate) {
            refreshPage();
            new Alert(Alert.AlertType.INFORMATION, " update successfully").show();
        }else{
            new Alert(Alert.AlertType.ERROR, "Fail to update....").show();
        }
    }

    public void btnSavePayment2OnAction(ActionEvent actionEvent) throws Exception {
        String SupplierPaymentId = lblpayment2.getText();
        String Date = SupplierPDatelbl.getText();
        String SupplierId = txtSupplierId.getText();
        String Qty = txtQty.getText();
        String UnitPrice = txtUnitPrice.getText();


        Payment2DTO payment2DTO = new Payment2DTO(
                SupplierPaymentId,
                Date,
                SupplierId,
                Qty,
                UnitPrice
        );
        boolean isSave = payment2BO.save(payment2DTO);
        if(isSave) {
            refreshPage();
            new Alert(Alert.AlertType.INFORMATION, " save successfully").show();
        }else{
            new Alert(Alert.AlertType.ERROR, "Fail to save....").show();
        }
    }

    public void generateAllSupplierPaymentReportOnAction(ActionEvent actionEvent) {
    }

    public void onClickTable(MouseEvent mouseEvent) {
        Payment2TM payment2TM = tblSupplierPayment.getSelectionModel().getSelectedItem();
        if (payment2TM != null) {
            lblpayment2.setText(payment2TM.getSupplierPaymentId());
            txtUnitPrice.setText(payment2TM.getUnitPrice());
            txtSupplierId.setText(payment2TM.getSupplierId());
            txtQty.setText(payment2TM.getQty());
            try {
                String SupplierId = supplierBO.SupplierIdFindByStatus(txtSupplierId.getText());
                cmbProductName.setValue(SupplierId);

            } catch (SQLException | ClassNotFoundException e) {
                Alert alert = new Alert(Alert.AlertType.ERROR,"can not load...");
                alert.show();
            }

            btnSavePayment2.setDisable(true);
            btnUpdatePayment2.setDisable(false);
            btnDeletePayment2.setDisable(false);
        }
    }

    public void clickBackbtn(ActionEvent actionEvent) {
        try {
            AnchorPane load = FXMLLoader.load(getClass().getResource("/view/SupplierView.fxml"));
            supplierAnchorPane.getChildren().clear();
            supplierAnchorPane.getChildren().add(load);
        } catch (IOException e) {
            e.printStackTrace();
            new Alert(Alert.AlertType.ERROR, "Error loading the product...").show();
        }
    }
}
