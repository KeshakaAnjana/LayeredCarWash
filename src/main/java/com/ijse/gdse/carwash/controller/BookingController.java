package com.ijse.gdse.carwash.controller;

import com.ijse.gdse.carwash.bo.BOFactory;
import com.ijse.gdse.carwash.bo.custom.BatchBO;
import com.ijse.gdse.carwash.bo.custom.BookingBO;
import com.ijse.gdse.carwash.bo.custom.CustomerBO;
import com.ijse.gdse.carwash.bo.custom.ServiceTypeBO;
import com.ijse.gdse.carwash.dao.DAOFactory;
import com.ijse.gdse.carwash.dao.custom.BookingDAO;
import com.ijse.gdse.carwash.dao.custom.CustomerDAO;
import com.ijse.gdse.carwash.dao.custom.ServiceTypeDAO;
import com.ijse.gdse.carwash.dto.BookingDTO;
import com.ijse.gdse.carwash.dto.CustomerDTO;
import com.ijse.gdse.carwash.dto.ServiceTypeDTO;
import com.ijse.gdse.carwash.dto.tm.BookingTM;
import com.ijse.gdse.carwash.dao.custom.impl.BookingDAOImpl;
import com.ijse.gdse.carwash.dao.custom.impl.CustomerDAOImpl;
import com.ijse.gdse.carwash.dao.custom.impl.ServiceTypeDAOImpl;
import com.ijse.gdse.carwash.entity.Customer;
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

import java.io.IOException;
import java.net.URL;
import java.sql.Date;
import java.sql.SQLException;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Optional;
import java.util.ResourceBundle;

public class BookingController implements Initializable {

 @FXML
 private ComboBox<String> cmbServiceTypeName;

 @FXML
 private Label bookingTime;

 @FXML
 private Label ServiceId;

 @FXML
 private Label bookingDate;

 @FXML
 private Button btnBack;

 @FXML
 private Button btnDeleteBooking;

 @FXML
 private Button btnReset;

 @FXML
 private Button btnSaveBooking;

 @FXML
 private Button btnUpdateBooking;

 @FXML
 private ComboBox<String> cmbCustomerId;

 @FXML
 private TableColumn<BookingTM, String> colCustomerId;

 @FXML
 private TableColumn<BookingTM, Date> colDate;

 @FXML
 private TableColumn<BookingTM, String> colTime;

 @FXML
 private TableColumn<BookingTM, String> colBookingId;

 @FXML
 private TableColumn<BookingTM, String> colServiceId;

 @FXML
 private Label customername;

 @FXML
 private Label lblbookingId;

 @FXML
 private TableView<BookingTM> tblBooking;

 ServiceTypeBO serviceTypeBO = (ServiceTypeBO) BOFactory.getInstance().getBO(BOFactory.BOType.SERVICETYPE);
 BookingBO bookingBO = (BookingBO) BOFactory.getInstance().getBO(BOFactory.BOType.BOOKING);
 CustomerBO customerBO = (CustomerBO) BOFactory.getInstance().getBO(BOFactory.BOType.CUSTOMER);

 public void initialize(URL url, ResourceBundle resourceBundle) {
  colBookingId.setCellValueFactory(new PropertyValueFactory<>("BookingId"));
  colDate.setCellValueFactory(new PropertyValueFactory<>("date"));
  colTime.setCellValueFactory(new PropertyValueFactory<>("time"));
  colCustomerId.setCellValueFactory(new PropertyValueFactory<>("customerId"));
  colServiceId.setCellValueFactory(new PropertyValueFactory<>("ServiceId"));

  try {
   refreshPage();
  } catch (Exception e) {
   e.printStackTrace();
   new Alert(Alert.AlertType.ERROR, "Fail to load booking id" + e.getMessage()).show();
  }
 }

 private void refreshPage() throws SQLException, ClassNotFoundException {
  loadCustomerId();
  loadServiceTypeName();
  loadNextBookingId();
  loadTableData();

  btnSaveBooking.setDisable(false);
  btnUpdateBooking.setDisable(true);
  btnDeleteBooking.setDisable(true);

  DateTimeFormatter formatter = DateTimeFormatter.ofPattern("hh:mm a");
  String formattedTime = LocalTime.now().format(formatter);
  bookingTime.setText(formattedTime);
  bookingDate.setText(LocalDate.now().toString());
  customername.setText("");
  ServiceId.setText("");
  cmbCustomerId.getSelectionModel().clearSelection();
  cmbServiceTypeName.getSelectionModel().clearSelection();
 }

 private void loadNextBookingId() throws SQLException, ClassNotFoundException {
  String nextBookingId = bookingBO.getNext();
  lblbookingId.setText(nextBookingId);
 }

 private void loadServiceTypeName() throws SQLException, ClassNotFoundException {
  ArrayList<String> ServiceTypeName = ServiceTypeDAOImpl.getAllserviceNames();
  ObservableList<String> observableList = FXCollections.observableArrayList();
  observableList.addAll(ServiceTypeName);
  cmbServiceTypeName.setItems(observableList);
 }

 private void loadCustomerId() throws SQLException, ClassNotFoundException {
  ArrayList<String> customerId = CustomerDAOImpl.getAllCustomerIds();
  ObservableList<String> observableList = FXCollections.observableArrayList();
  observableList.addAll(customerId);
  cmbCustomerId.setItems(observableList);
 }

 private void loadTableData() throws SQLException, ClassNotFoundException {
  ArrayList<BookingDTO> bookingDTOS = bookingBO.getAll();
  ObservableList<BookingTM> bookingTMS = FXCollections.observableArrayList();

  for (BookingDTO bookingDTO : bookingDTOS) {
   BookingTM bookingTM = new BookingTM(
           bookingDTO.getBookingId(),
           bookingDTO.getDate(),
           bookingDTO.getTime(),
           bookingDTO.getCustomerId(),
           bookingDTO.getServiceId()
   );
   bookingTMS.add(bookingTM);
  }
  tblBooking.setItems(bookingTMS);
 }


 @FXML
 void btnDeleteBookingOnAction(ActionEvent event) throws SQLException, ClassNotFoundException {
    String BookingId = lblbookingId.getText();

    Alert alert = new Alert(Alert.AlertType.CONFIRMATION,"Are you sure?",ButtonType.YES, ButtonType.NO);
  Optional<ButtonType> optionalButtonType = alert.showAndWait();

    if(optionalButtonType.isPresent() && optionalButtonType.get() == ButtonType.YES){
     boolean isDeleted = bookingBO.delete(BookingId);
     if (isDeleted) {
      refreshPage();
      new Alert(Alert.AlertType.INFORMATION, "booking deleted...!").show();
     } else {
      new Alert(Alert.AlertType.ERROR, "Fail to delete ...!").show();
     }
    }
 }

 @FXML
 void btnResetOnAction(ActionEvent event) throws SQLException, ClassNotFoundException {
  refreshPage();
 }

 @FXML
 void btnSaveBookingOnAction(ActionEvent event) throws SQLException, ClassNotFoundException {
    String BookingId = lblbookingId.getText();
    String date = bookingDate.getText();
    String time = bookingTime.getText();
    String customerId = cmbCustomerId.getSelectionModel().getSelectedItem();
    String serviceId = ServiceId.getText();

    cmbCustomerId.setStyle(cmbCustomerId.getStyle() + ";-fx-border-color: #045ae6;");
    cmbServiceTypeName.setStyle(cmbServiceTypeName.getStyle() + ";-fx-border-color: #045ae6;");

    BookingDTO bookingDTO = new BookingDTO(
        BookingId,
        date,
        time,
        customerId,
            serviceId
    );
    boolean isSaved = bookingBO.save(bookingDTO);
    if(isSaved) {
     refreshPage();
     new Alert(Alert.AlertType.INFORMATION, "Booking saved successfully").show();
    }else{
     new Alert(Alert.AlertType.ERROR, "Fail to save booking").show();
    }
    }


 @FXML
 void btnUpdateBookingOnAction(ActionEvent event) throws SQLException, ClassNotFoundException {
  String BookingId = lblbookingId.getText();
  String date = bookingDate.getText();
  String time = bookingTime.getText();
  String customerId = cmbCustomerId.getSelectionModel().getSelectedItem();
  String serviceId = ServiceId.getText();

  cmbCustomerId.setStyle(cmbCustomerId.getStyle() + ";-fx-border-color: #045ae6;");
  cmbServiceTypeName.setStyle(cmbServiceTypeName.getStyle() + ";-fx-border-color: #045ae6;");

  BookingDTO bookingDTO = new BookingDTO(
          BookingId,
          date,
          time,
          customerId,
          serviceId
  );
  boolean isUpdate = bookingBO.update(bookingDTO);
  if(isUpdate) {
   refreshPage();
   new Alert(Alert.AlertType.INFORMATION, "Booking update successfully").show();
  }else{
   new Alert(Alert.AlertType.ERROR, "Fail to update booking").show();
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
 void clickcmbCustomerId(ActionEvent event) throws SQLException, ClassNotFoundException {
  String selectedCustomerId = cmbCustomerId.getSelectionModel().getSelectedItem();
  Customer customerDTO = customerBO.findById(selectedCustomerId);

  if (customerDTO != null) {

   customername.setText(customerDTO.getName());
  }
 }

 public void clickcmbServiceTypeName(ActionEvent actionEvent) throws SQLException, ClassNotFoundException {
  String selectedServiceTypeName = cmbServiceTypeName.getSelectionModel().getSelectedItem();
  ServiceType serviceTypeDTO = serviceTypeBO.findById(selectedServiceTypeName);

  if (serviceTypeDTO != null) {

   ServiceId.setText(serviceTypeDTO.getServiceId());
  }
 }

 @FXML
 void onClicktable(MouseEvent event) {
     BookingTM bookingTM = tblBooking.getSelectionModel().getSelectedItem();
     if (bookingTM != null) {
      lblbookingId.setText(bookingTM.getBookingId());
      bookingDate.setText(bookingTM.getDate());
      cmbCustomerId.setValue(bookingTM.getCustomerId());
      //customername.setText(bookingTM.getCustomerId());
      bookingTime.setText(bookingTM.getTime());
      ServiceId.setText(bookingTM.getServiceId());
      try {
        String SerciceType = serviceTypeBO.ServiceTypeFindById(ServiceId.getText());
       cmbServiceTypeName.setValue(SerciceType);

      } catch (SQLException | ClassNotFoundException e) {
        Alert alert = new Alert(Alert.AlertType.ERROR,"can not load...");
        alert.show();
      }
      }


      btnSaveBooking.setDisable(true);
      btnUpdateBooking.setDisable(false);
      btnDeleteBooking.setDisable(false);
     }
 }
