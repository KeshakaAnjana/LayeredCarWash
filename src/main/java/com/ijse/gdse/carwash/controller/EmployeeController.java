package com.ijse.gdse.carwash.controller;

import com.ijse.gdse.carwash.bo.BOFactory;
import com.ijse.gdse.carwash.bo.custom.EmployeeBO;
import com.ijse.gdse.carwash.bo.custom.ServiceTypeBO;
import com.ijse.gdse.carwash.dao.DAOFactory;
import com.ijse.gdse.carwash.dao.custom.EmployeeDAO;
import com.ijse.gdse.carwash.dao.custom.ServiceTypeDAO;
import com.ijse.gdse.carwash.db.DBConnection;
import com.ijse.gdse.carwash.dto.EmployeeDTO;
import com.ijse.gdse.carwash.dto.ServiceTypeDTO;
import com.ijse.gdse.carwash.dto.tm.EmployeeTM;
import com.ijse.gdse.carwash.dao.custom.impl.EmployeeDAOImpl;
import com.ijse.gdse.carwash.dao.custom.impl.ServiceTypeDAOImpl;
import com.ijse.gdse.carwash.entity.ServiceType;
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

public class EmployeeController implements Initializable {
    @FXML
    private Label JobId;

    @FXML
    private Button btnBack;

    @FXML
    private Button btnDeleteEmployee;

    @FXML
    private Button btnReset;

    @FXML
    private Button btnSaveEmployee;

    @FXML
    private Button btnUpdateEmployee;

    @FXML
    private ComboBox<String> cmbJob;

    @FXML
    private TableColumn<EmployeeTM, String> colEmployeeId;

    @FXML
    private TableColumn<EmployeeTM, String> colJob;

    @FXML
    private TableColumn<EmployeeTM, String> colName;

    @FXML
    private TableColumn<EmployeeTM, String> colSalary;

    @FXML
    private Label lblEmployeeId;

    @FXML
    private TableView<EmployeeTM> tblEmployee;

    @FXML
    private TextField txtName;

    @FXML
    private TextField txtSalary;

    ServiceTypeBO serviceTypeBO = (ServiceTypeBO) BOFactory.getInstance().getBO(BOFactory.BOType.SERVICETYPE);
    EmployeeBO employeeBO = (EmployeeBO) BOFactory.getInstance().getBO(BOFactory.BOType.EMPLOYEE);

    public void initialize(URL url, ResourceBundle resourceBundle) {
        colEmployeeId.setCellValueFactory(new PropertyValueFactory<>("EmployeeId"));
        colName.setCellValueFactory(new PropertyValueFactory<>("EmployeeName"));
        colSalary.setCellValueFactory(new PropertyValueFactory<>("Salary"));
        colJob.setCellValueFactory(new PropertyValueFactory<>("Job"));

        try {
            refreshPage();
        } catch (Exception e) {
            e.printStackTrace();
            new Alert(Alert.AlertType.ERROR, "Fail to load booking id" + e.getMessage()).show();
        }
    }

    private void refreshPage() throws SQLException, ClassNotFoundException {
        loadServiceTypeName();
        loadNextEmployeeId();
        loadTableData();

        btnSaveEmployee.setDisable(false);
        btnUpdateEmployee.setDisable(true);
        btnDeleteEmployee.setDisable(true);

        txtName.setText("");
        txtSalary.setText("");
        txtSalary.setText("");
        JobId.setText("");
        cmbJob.getSelectionModel().clearSelection();

    }

    private void loadNextEmployeeId() throws SQLException, ClassNotFoundException {
        String nextEmployeeId = employeeBO.getNext();
        lblEmployeeId.setText(nextEmployeeId);
    }

    private void loadServiceTypeName() throws SQLException, ClassNotFoundException {
        ArrayList<String> ServiceTypeName = ServiceTypeDAOImpl.getAllserviceNames();
        ObservableList<String> observableList = FXCollections.observableArrayList();
        observableList.addAll(ServiceTypeName);
        cmbJob.setItems(observableList);
    }

    private void loadTableData() throws SQLException, ClassNotFoundException {
        ArrayList<EmployeeDTO> employeeDTOS = employeeBO.getAll();
        ObservableList<EmployeeTM> employeeTMS = FXCollections.observableArrayList();

        for (EmployeeDTO employeeDTO : employeeDTOS) {
            EmployeeTM employeeTM = new EmployeeTM(
                    employeeDTO.getEmployeeId(),
                    employeeDTO.getEmployeeName(),
                    employeeDTO.getSalary(),
                    employeeDTO.getJob()
            );
            employeeTMS.add(employeeTM);
        }
        tblEmployee.setItems(employeeTMS);
    }

    @FXML
    void btnDeleteEmployeeOnAction(ActionEvent event) throws SQLException, ClassNotFoundException {
        String EmployeeId = lblEmployeeId.getText();

        Alert alert = new Alert(Alert.AlertType.CONFIRMATION,"Are you sure?", ButtonType.YES, ButtonType.NO);
        Optional<ButtonType> optionalButtonType = alert.showAndWait();

        if (optionalButtonType.isPresent() && optionalButtonType.get() == ButtonType.YES) {

            boolean isDeleted = employeeBO.delete(EmployeeId);
            if (isDeleted) {
                refreshPage();
                new Alert(Alert.AlertType.INFORMATION, " deleted...!").show();
            } else {
                new Alert(Alert.AlertType.ERROR, "Fail to delete...!").show();
            }
        }
    }

    @FXML
    void btnResetOnAction(ActionEvent event) throws SQLException, ClassNotFoundException {
        refreshPage();
    }

    @FXML
    void btnSaveEmployeeOnAction(ActionEvent event) throws SQLException, ClassNotFoundException {
        String EmployeeId = lblEmployeeId.getText();
        String Name = txtName.getText();
        String Salary = txtSalary.getText();
        String Job = cmbJob.getSelectionModel().getSelectedItem();

        txtName.setStyle(txtName.getStyle() + ";-fx-border-color:  #045ae6;");
        txtSalary.setStyle(txtSalary.getStyle() + ";-fx-border-color:  #045ae6;");
        cmbJob.setStyle(cmbJob.getStyle() + ";-fx-border-color: #045ae6;");

        String namePattern = "^[A-Za-z ]+$";
        String salaryPattern = "^(\\d+(\\.\\d{1,2})?)$";

        boolean isValidName = Name.matches(namePattern);
        boolean isValidSalary = Salary.matches(salaryPattern);

        if(!isValidName){
            System.out.println(txtName.getStyle());
            txtName.setStyle(txtName.getStyle() + ";-fx-border-color: red;");
            System.out.println("invalid name");
        }
        if(!isValidSalary){
            txtSalary.setStyle(txtSalary.getStyle() + ";-fx-border-color: red;");
        }
        if(isValidName && isValidSalary){
            EmployeeDTO employeeDTO = new EmployeeDTO(
                    EmployeeId,
                    Name,
                    Salary,
                    Job
            );
            boolean isSaved = employeeBO.save(employeeDTO);
            if (isSaved) {
                refreshPage();
                new Alert(Alert.AlertType.INFORMATION, "Employee saved...!").show();
            } else {
                new Alert(Alert.AlertType.ERROR, "Fail to save Employee...!").show();
            }
        }
    }

    @FXML
    void btnUpdateEmployeeOnAction(ActionEvent event) throws SQLException, ClassNotFoundException {
        String EmployeeId = lblEmployeeId.getText();
        String Name = txtName.getText();
        String Salary = txtSalary.getText();
        String Job = cmbJob.getSelectionModel().getSelectedItem();

        txtName.setStyle(txtName.getStyle() + ";-fx-border-color:  #045ae6;");
        txtSalary.setStyle(txtSalary.getStyle() + ";-fx-border-color:  #045ae6;");
        cmbJob.setStyle(cmbJob.getStyle() + ";-fx-border-color: #045ae6;");

        String namePattern = "^[A-Za-z ]+$";
        String salaryPattern = "^(\\d+(\\.\\d{1,2})?)$";

        boolean isValidName = Name.matches(namePattern);
        boolean isValidSalary = Salary.matches(salaryPattern);

        if(!isValidName){
            System.out.println(txtName.getStyle());
            txtName.setStyle(txtName.getStyle() + ";-fx-border-color: red;");
            System.out.println("invalid name");
        }
        if(!isValidSalary){
            txtSalary.setStyle(txtSalary.getStyle() + ";-fx-border-color: red;");
        }
        if(isValidName && isValidSalary){
            EmployeeDTO employeeDTO = new EmployeeDTO(
                    EmployeeId,
                    Name,
                    Salary,
                    Job
            );
            boolean isUpdate = employeeBO.update(employeeDTO);
            if (isUpdate) {
                refreshPage();
                new Alert(Alert.AlertType.INFORMATION, "Employee update...!").show();
            } else {
                new Alert(Alert.AlertType.ERROR, "Fail to update Employee...!").show();
            }
        }
    }


    @FXML
    void clickcmbJob(ActionEvent event) throws SQLException, ClassNotFoundException {
        String selectedServiceTypeName = cmbJob.getSelectionModel().getSelectedItem();
        ServiceType serviceTypeDTO = serviceTypeBO.findById(selectedServiceTypeName);

        if (serviceTypeDTO != null) {

            JobId.setText(serviceTypeDTO.getServiceId());
        }
    }

    @FXML
    void generateAllEmployeeReportOnAction(ActionEvent event) {

        try {
            JasperReport jasperReport = JasperCompileManager.compileReport(
                    getClass()
                            .getResourceAsStream("/report/employee_report.jrxml"
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

    @FXML
    void onClicktable(MouseEvent event) {
        EmployeeTM employeeTM = tblEmployee.getSelectionModel().getSelectedItem();
        if (employeeTM != null) {
            lblEmployeeId.setText(employeeTM.getEmployeeId());
            txtName.setText(employeeTM.getEmployeeName());
            txtSalary.setText(employeeTM.getSalary());
            cmbJob.setValue(employeeTM.getJob());

            try {
                String selectedJob = employeeTM.getJob();
                ServiceType serviceTypeDTO = serviceTypeBO.findById(selectedJob);

                if (serviceTypeDTO != null) {
                    JobId.setText(serviceTypeDTO.getServiceId());
                    System.out.println("ServiceTypeId set to: " + serviceTypeDTO.getServiceId());  // Debugging statement
                } else {
                    JobId.setText("Not Found");
                    System.out.println("No ServiceType found for: " + selectedJob);
                }

            } catch (SQLException | ClassNotFoundException e) {
                Alert alert = new Alert(Alert.AlertType.ERROR, "Cannot load ServiceTypeId...");
                alert.show();
                e.printStackTrace();
            }

            btnSaveEmployee.setDisable(true);
            btnUpdateEmployee.setDisable(false);
            btnDeleteEmployee.setDisable(false);
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
