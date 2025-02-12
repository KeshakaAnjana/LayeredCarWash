package com.ijse.gdse.carwash.controller;

import com.ijse.gdse.carwash.bo.BOFactory;
import com.ijse.gdse.carwash.bo.custom.CustomerBO;
import com.ijse.gdse.carwash.dao.DAOFactory;
import com.ijse.gdse.carwash.dao.custom.CustomerDAO;
import com.ijse.gdse.carwash.db.DBConnection;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.input.MouseEvent;
import com.ijse.gdse.carwash.dto.CustomerDTO;
import com.ijse.gdse.carwash.dto.tm.CustomerTM;
import com.ijse.gdse.carwash.dao.custom.impl.CustomerDAOImpl;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.Window;
import net.sf.jasperreports.engine.*;
import net.sf.jasperreports.view.JasperViewer;

import java.io.IOException;
import java.net.URL;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Optional;
import java.util.ResourceBundle;

public class CustomerController implements Initializable {

    public Button btnBack;
    @FXML
    private TextField txtuserId,txtName, txtEmail, txtPhone;
    @FXML
    private Button btnDelete, btnSave, btnUpdate;
    @FXML
    private Label lblCustomerId;
    @FXML
    private TableColumn<CustomerTM, String> colCustomerId, colEmail, colName, colPhone, colUserId;
    @FXML
    private TableView<CustomerTM> tblCustomer;

    CustomerBO customerBO = (CustomerBO) BOFactory.getInstance().getBO(BOFactory.BOType.CUSTOMER);
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        colCustomerId.setCellValueFactory(new PropertyValueFactory<>("customerId"));
        colName.setCellValueFactory(new PropertyValueFactory<>("Name"));
        colEmail.setCellValueFactory(new PropertyValueFactory<>("Email"));
        colPhone.setCellValueFactory(new PropertyValueFactory<>("Phone"));
        colUserId.setCellValueFactory(new PropertyValueFactory<>("UserId"));

        try {
            refreshPage();
        } catch (Exception e) {
            e.printStackTrace();
            new Alert(Alert.AlertType.ERROR, "Failed to load customer ID").show();
        }
    }

    private void refreshPage() throws SQLException, ClassNotFoundException {
        loadNextCustomerId();
        loadTableData();

        btnSave.setDisable(false);
        btnUpdate.setDisable(true);
        btnDelete.setDisable(true);

        clearFormFields();
    }

    private void loadNextCustomerId() throws SQLException, ClassNotFoundException {
        String nextCustomerId = customerBO.getNext();
        lblCustomerId.setText(nextCustomerId);
    }

    private void loadTableData() throws SQLException, ClassNotFoundException {
        ArrayList<CustomerDTO> customerDTOS = customerBO.getAll();
        ObservableList<CustomerTM> customerTMS = FXCollections.observableArrayList();

        for (CustomerDTO customerDTO : customerDTOS) {
            CustomerTM customerTM = new CustomerTM(
                    customerDTO.getCustomerId(),
                    customerDTO.getName(),
                    customerDTO.getEmail(),
                    customerDTO.getPhone(),
                    customerDTO.getUserId()
            );
            customerTMS.add(customerTM);
        }
        tblCustomer.setItems(customerTMS);
    }

    private void clearFormFields() {
        txtName.clear();
        txtEmail.clear();
        txtPhone.clear();
        txtuserId.clear();
    }

    @FXML
    public void btnSaveOnAction(ActionEvent actionEvent) throws SQLException, ClassNotFoundException {
        String customerId = lblCustomerId.getText();
        String name = txtName.getText();
        String email = txtEmail.getText();
        String phone = txtPhone.getText();
        String userId = txtuserId.getText();

        txtName.setStyle(txtName.getStyle() + ";-fx-border-color:  #045ae6;");
        txtEmail.setStyle(txtEmail.getStyle() + ";-fx-border-color:  #045ae6;");
        txtPhone.setStyle(txtPhone.getStyle() + ";-fx-border-color:  #045ae6;");
        txtuserId.setStyle(txtuserId.getStyle() + ";-fx-border-color:  #045ae6;");

        String namePattern = "^[A-Za-z ]+$";
        String emailPattern = "^[a-zA-Z0-9._%+-]+@gmail\\.com$";
        String phonePattern = "^07\\d{8}$";
        String userIdPattern =  "^u\\d{3}$";

        boolean isValidName = name.matches(namePattern);
        boolean isValidEmail = email.matches(emailPattern);
        boolean isValidPhone = phone.matches(phonePattern);
        boolean isValidUserId = userId.matches(userIdPattern);

        if (!isValidName) {
            System.out.println(txtName.getStyle());
            txtName.setStyle(txtName.getStyle() + ";-fx-border-color: red;");
            System.out.println("Invalid name.............");

        }

        if (!isValidEmail) {
            txtEmail.setStyle(txtEmail.getStyle() + ";-fx-border-color: red;");
        }

        if (!isValidPhone) {
            txtPhone.setStyle(txtPhone.getStyle() + ";-fx-border-color: red;");
        }

        if (!isValidUserId) {
            txtuserId.setStyle(txtuserId.getStyle() + ";-fx-border-color: red;");
        }


        if (isValidName  && isValidEmail && isValidPhone && isValidUserId) {
            CustomerDTO customerDTO = new CustomerDTO(
                    customerId,
                    name,
                    email,
                    phone,
                    userId
            );

            boolean isSaved = customerBO.save(customerDTO);
            if (isSaved) {
                refreshPage();
                new Alert(Alert.AlertType.INFORMATION, "Customer saved...!").show();
            } else {
                new Alert(Alert.AlertType.ERROR, "Fail to save customer...!").show();
            }
        }
    }

        @FXML
    void onClickTable(MouseEvent event) {
        CustomerTM selectedCustomer = tblCustomer.getSelectionModel().getSelectedItem();
        if (selectedCustomer != null) {
            lblCustomerId.setText(selectedCustomer.getCustomerId());
            txtName.setText(selectedCustomer.getName());
            txtEmail.setText(selectedCustomer.getEmail());
            txtPhone.setText(selectedCustomer.getPhone());
            txtuserId.setText(selectedCustomer.getUserId());

            btnSave.setDisable(true);
            btnUpdate.setDisable(false);
            btnDelete.setDisable(false);
        }
    }

    @FXML
    void btnDeleteOnAction(ActionEvent event) throws SQLException, ClassNotFoundException {
        String customerId = lblCustomerId.getText();
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION, "Are you sure?", ButtonType.YES, ButtonType.NO);
        Optional<ButtonType> result = alert.showAndWait();

        if (result.isPresent() && result.get() == ButtonType.YES) {
            boolean isDeleted = customerBO.delete(customerId);
            if (isDeleted) {
                refreshPage();
                new Alert(Alert.AlertType.INFORMATION, "Customer deleted!").show();
            } else {
                new Alert(Alert.AlertType.ERROR, "Failed to delete customer!").show();
            }
        }
    }

    @FXML
    void btnUpdateOnAction(ActionEvent event) throws SQLException, ClassNotFoundException {
        String customerId = lblCustomerId.getText();
        String name = txtName.getText();
        String email = txtEmail.getText();
        String phone = txtPhone.getText();
        String userId = txtuserId.getText();

        txtName.setStyle(txtName.getStyle() + ";-fx-border-color: #045ae6;");
        txtEmail.setStyle(txtEmail.getStyle() + ";-fx-border-color:  #045ae6;");
        txtPhone.setStyle(txtPhone.getStyle() + ";-fx-border-color:  #045ae6;");
        txtuserId.setStyle(txtuserId.getStyle() + ";-fx-border-color: #045ae6;");

        String namePattern = "^[A-Za-z ]+$";
        String emailPattern ="^[a-zA-Z0-9._%+-]+@gmail\\.com$";
        String phonePattern = "^07\\d{8}$";
        String userIdPattern =  "^u\\d{3}$";

        boolean isValidName = name.matches(namePattern);
        boolean isValidEmail = email.matches(emailPattern);
        boolean isValidPhone = phone.matches(phonePattern);
        boolean isValidUserId = userId.matches(userIdPattern);

        if (!isValidName) {
            System.out.println(txtName.getStyle());
            txtName.setStyle(txtName.getStyle() + ";-fx-border-color: red;");
            System.out.println("Invalid name.............");

        }

        if (!isValidEmail) {
            txtEmail.setStyle(txtEmail.getStyle() + ";-fx-border-color: red;");
        }

        if (!isValidPhone) {
            txtPhone.setStyle(txtPhone.getStyle() + ";-fx-border-color: red;");
        }

        if (!isValidUserId) {
            txtuserId.setStyle(txtuserId.getStyle() + ";-fx-border-color: red;");
        }


        if (isValidName  && isValidEmail && isValidPhone && isValidUserId) {
            CustomerDTO customerDTO = new CustomerDTO(
                    customerId,
                    name,
                    email,
                    phone,
                    userId
            );

            boolean isUpdate = customerBO.update(customerDTO);
            if (isUpdate) {
                refreshPage();
                new Alert(Alert.AlertType.INFORMATION, "Customer update...!").show();
            } else {
                new Alert(Alert.AlertType.ERROR, "Fail to update customer...!").show();
            }
        }
    }
    @FXML
    void resetOnAction(ActionEvent event) throws SQLException, ClassNotFoundException {
        refreshPage();
    }
    public void generateAllCustomerReportOnAction(ActionEvent actionEvent) {
        try {
            JasperReport jasperReport = JasperCompileManager.compileReport(
                    getClass()
                            .getResourceAsStream("/report/customer_report.jrxml"
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
//           e.printStackTrace();
        } catch (SQLException e) {
            new Alert(Alert.AlertType.ERROR, "DB error...!").show();
        }
    }

    public void openSendMailModel(ActionEvent actionEvent) {
        CustomerTM selectedItem = tblCustomer.getSelectionModel().getSelectedItem();
        if (selectedItem == null) {
            new Alert(Alert.AlertType.WARNING, "Please select customer..!");
            return;
        }

        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/view/NotificationView.fxml"));
            Parent load = loader.load();

            NotificationController notificationController = loader.getController();

            String email = selectedItem.getEmail();
            notificationController.setCustomerEmail(email);

            Stage stage = new Stage();
            stage.setScene(new Scene(load));
            stage.setTitle("Send email");
            stage.getIcons().add(new Image(getClass().getResourceAsStream("/images/mail_icon.png")));

            stage.initModality(Modality.APPLICATION_MODAL);

            Window underWindow = btnUpdate.getScene().getWindow();
            stage.initOwner(underWindow);

            stage.showAndWait();
        } catch (IOException e) {
            new Alert(Alert.AlertType.ERROR, "Fail to load ui..!");
            e.printStackTrace();
        }
    }

    public void clickBack(ActionEvent actionEvent) {
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








