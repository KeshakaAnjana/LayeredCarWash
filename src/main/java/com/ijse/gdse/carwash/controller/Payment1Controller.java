package com.ijse.gdse.carwash.controller;

import com.ijse.gdse.carwash.bo.BOFactory;
import com.ijse.gdse.carwash.bo.custom.BookingBO;
import com.ijse.gdse.carwash.bo.custom.Payment1BO;
import com.ijse.gdse.carwash.bo.custom.ServiceTypeBO;
import com.ijse.gdse.carwash.dao.DAOFactory;
import com.ijse.gdse.carwash.dao.custom.BookingDAO;
import com.ijse.gdse.carwash.dao.custom.Payment1DAO;
import com.ijse.gdse.carwash.dao.custom.ServiceTypeDAO;
import com.ijse.gdse.carwash.dto.Payment1DTO;
import com.ijse.gdse.carwash.dto.tm.Payment1TM;
import com.ijse.gdse.carwash.dao.custom.impl.BookingDAOImpl;
import com.ijse.gdse.carwash.dao.custom.impl.Payment1DAOImpl;
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
import java.net.URL;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Optional;
import java.util.ResourceBundle;

public class Payment1Controller implements Initializable {

    @FXML
    private Button btnBack;

    @FXML
    private Button btnDeletePayment;

    @FXML
    private Button btnReset;

    @FXML
    private Button btnSavePayment;

    @FXML
    private Button btnUpdatePayment;

    @FXML
    private ComboBox<String> cmbBookingId;

    @FXML
    private TableColumn<Payment1TM, String> colAmount;

    @FXML
    private TableColumn<Payment1TM, String> colBookingId;

    @FXML
    private TableColumn<Payment1TM, String> colCustomerPaymentId;

    @FXML
    private TableColumn<Payment1TM, String> colPaymentmethod;

    @FXML
    private Label lblpayment1;

    @FXML
    private TableView<Payment1TM> tblCustomerPayment;

    @FXML
    private TextField txtAmount;

    @FXML
    private TextField txtpaymentmethod;

    Payment1BO payment1BO = (Payment1BO) BOFactory.getInstance().getBO(BOFactory.BOType.PAYMENT1);
    ServiceTypeBO serviceTypeBO = (ServiceTypeBO) BOFactory.getInstance().getBO(BOFactory.BOType.SERVICETYPE);
    BookingBO bookingBO = (BookingBO) BOFactory.getInstance().getBO(BOFactory.BOType.BOOKING);

    public void initialize(URL url, ResourceBundle resourceBundle){
        colCustomerPaymentId.setCellValueFactory(new PropertyValueFactory<>("CustomerPaymentId"));
        colAmount.setCellValueFactory(new PropertyValueFactory<>("Amount"));
        colPaymentmethod.setCellValueFactory(new PropertyValueFactory<>("PaymentMethod"));
        colBookingId.setCellValueFactory(new PropertyValueFactory<>("BookingId"));

        try {
            refreshPage();
        } catch (Exception e) {
            e.printStackTrace();
            new Alert(Alert.AlertType.ERROR, "Fail to load customer Payment id").show();
        }
    }
    public void refreshPage() throws Exception {
        loadBookingId();
        loadNextPayment1Id();
        loadTableData();

        btnDeletePayment.setDisable(true);
        btnUpdatePayment.setDisable(true);
        btnSavePayment.setDisable(false);

        txtAmount.setText("");
        txtpaymentmethod.setText("");
        cmbBookingId.getSelectionModel().clearSelection();
    }

    private void loadTableData() throws SQLException, ClassNotFoundException {
        ArrayList<Payment1DTO> payment1DTOS = payment1BO.getAll();
        ObservableList<Payment1TM> payment1TMS = FXCollections.observableArrayList();

        for (Payment1DTO payment1DTO : payment1DTOS) {
            Payment1TM payment1TM = new Payment1TM(
                    payment1DTO.getCustomerPaymentId(),
                    payment1DTO.getAmount(),
                    payment1DTO.getPaymentMethod(),
                    payment1DTO.getBookingId()
            );
            payment1TMS.add(payment1TM);
        }
        tblCustomerPayment.setItems(payment1TMS);
    }

    public void loadNextPayment1Id() throws SQLException, ClassNotFoundException {
        String nextPaymentId = payment1BO.getNext();
        lblpayment1.setText(nextPaymentId);
    }

    private void loadBookingId() throws SQLException, ClassNotFoundException {
        ArrayList<String> BookingId = BookingDAOImpl.getAllBookingIds();
        ObservableList<String> observableList = FXCollections.observableArrayList();
        observableList.addAll(BookingId);
        cmbBookingId.setItems(observableList);
    }
        @FXML
    void btnDeletePaymentOnAction(ActionEvent event) throws Exception {
            String CustomerPaymentId = lblpayment1.getText();

            Alert alert = new Alert(Alert.AlertType.CONFIRMATION, "Are you sure?", ButtonType.YES, ButtonType.NO);
            Optional<ButtonType> optionalButtonType = alert.showAndWait();

            if (optionalButtonType.isPresent() && optionalButtonType.get() == ButtonType.YES) {

                boolean isDeleted = payment1BO.delete(CustomerPaymentId);
                if (isDeleted) {
                    refreshPage();
                    new Alert(Alert.AlertType.INFORMATION, " deleted...!").show();
                } else {
                    new Alert(Alert.AlertType.ERROR, "Fail to delete...!").show();
                }
            }
    }

    @FXML
    void btnResetOnAction(ActionEvent event) throws Exception {
        refreshPage();
    }

    @FXML
    void btnSavePaymentOnAction(ActionEvent event) throws Exception {
        String CustomerPaymentId = lblpayment1.getText();
        String Amount = txtAmount.getText();
        String PaymentMethod = txtpaymentmethod.getText();
        String BookingId = cmbBookingId.getValue();

        txtAmount.setStyle(txtAmount.getStyle() + ";-fx-border-color: #045ae6;");
        txtpaymentmethod.setStyle(txtpaymentmethod.getStyle() + ";-fx-border-color: #045ae6;");
        cmbBookingId.setStyle(cmbBookingId.getStyle() + ";-fx-border-color: #045ae6;");

        String amountPattern = "^(\\d+(\\.\\d{1,2})?)$";
        String paymentMethodPattern = "^[A-Za-z ]+$";

        boolean isValidAmount = Amount.matches(amountPattern);
        boolean isValidPaymentMethod = PaymentMethod.matches(paymentMethodPattern);

        if (isValidAmount && isValidPaymentMethod) {
            Payment1DTO payment1DTO = new Payment1DTO(
                    CustomerPaymentId,
                    Amount,
                    PaymentMethod,
                    BookingId
            );
            boolean isSaved = payment1BO.save(payment1DTO);
            if (isSaved) {
                refreshPage();
                new Alert(Alert.AlertType.INFORMATION, "Saved...!").show();
            } else {
                new Alert(Alert.AlertType.ERROR, "Failed to save...!").show();
            }
        }else{
            if (!isValidAmount) {
                txtAmount.setStyle("-fx-border-color: red;");
            }
            if (!isValidPaymentMethod) {
                txtpaymentmethod.setStyle("-fx-border-color: red;");
            }
        }
    }

    @FXML
    void btnUpdatePaymentOnAction(ActionEvent event) throws Exception {
        String CustomerPaymentId = lblpayment1.getText();
        String Amount = txtAmount.getText();
        String PaymentMethod = txtpaymentmethod.getText();
        String BookingId = cmbBookingId.getSelectionModel().getSelectedItem();

        txtAmount.setStyle(txtAmount.getStyle() + ";-fx-border-color: #045ae6;");
        txtpaymentmethod.setStyle(txtpaymentmethod.getStyle() + ";-fx-border-color: #045ae6;");
        cmbBookingId.setStyle(cmbBookingId.getStyle() + ";-fx-border-color: #045ae6;");

        String amountPattern = "^(\\d+(\\.\\d{1,2})?)$";
        String paymentMethodPattern = "^[A-Za-z ]+$";

        boolean isValidAmount = Amount.matches(amountPattern);
        boolean isValidPaymentMethod = PaymentMethod.matches(paymentMethodPattern);

        if (!isValidAmount) {
            System.out.println(txtAmount.getStyle());
            txtAmount.setStyle(txtAmount.getStyle() + ";-fx-border-color: red;");
            System.out.println("Invalid Amount");
        }
        if (!isValidPaymentMethod) {
            txtpaymentmethod.setStyle(txtpaymentmethod.getStyle() + ";-fx-border-color: red;");
        }

        if (isValidAmount && isValidPaymentMethod) {
            Payment1DTO payment1DTO = new Payment1DTO(
                    CustomerPaymentId,
                    Amount,
                    PaymentMethod,
                    BookingId
            );
            boolean isUpdated = payment1BO.update(payment1DTO);
            if (isUpdated) {
                refreshPage();
                new Alert(Alert.AlertType.INFORMATION, "Updated...!").show();
            } else {
                new Alert(Alert.AlertType.ERROR, "Failed to update...!").show();
            }
        }
    }

    @FXML
    void clickBackbtn(ActionEvent event) {
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


    @FXML
    void clickcmbBookingId(ActionEvent event) throws SQLException, ClassNotFoundException {
        String selectedBookingId = cmbBookingId.getSelectionModel().getSelectedItem();

        // Ensure that the selectedBookingId is not null or empty
        if (selectedBookingId != null && !selectedBookingId.trim().isEmpty()) {
            System.out.println("Selected Booking ID: " + selectedBookingId);

            // Fetch service_id using the selected Booking ID
            String serviceId = bookingBO.servicePriceFindByServiceId(selectedBookingId);

            // If serviceId is not null, fetch the price and set it in the text field
            if (serviceId != null) {
                String price = serviceTypeBO.priceFindFbServiceId(serviceId);
                if (price != null) {
                    txtAmount.setText(price);  // Set the price in the text field
                } else {
                    txtAmount.setText("Price not found");
                }
            } else {
                txtAmount.setText("Service not found");
            }
        } else {
            txtAmount.setText("");
        }
    }

    @FXML
    void generateAllPaymentReportOnAction(ActionEvent event) {

    }

    @FXML
    void onClickTable(MouseEvent event) {
        Payment1TM payment1TM = tblCustomerPayment.getSelectionModel().getSelectedItem();
        if(payment1TM != null) {
            lblpayment1.setText(payment1TM.getCustomerPaymentId());
            txtAmount.setText(payment1TM.getAmount());
            txtpaymentmethod.setText(payment1TM.getPaymentMethod());
            cmbBookingId.getSelectionModel().select(payment1TM.getBookingId());

            btnSavePayment.setDisable(true);
            btnUpdatePayment.setDisable(false);
            btnDeletePayment.setDisable(false);
        }
    }

}
