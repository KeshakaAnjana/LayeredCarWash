package com.ijse.gdse.carwash.bo.custom;

import com.ijse.gdse.carwash.bo.SuperBO;
import com.ijse.gdse.carwash.dto.Payment1DTO;
import com.ijse.gdse.carwash.entity.Payment1;

import java.sql.SQLException;
import java.util.ArrayList;

public interface Payment1BO extends SuperBO {

    ArrayList<String> getAllPayment1Ids() throws SQLException, ClassNotFoundException;

    Payment1 findById(String selectedCustomerPaymentId) throws SQLException, ClassNotFoundException;

    String getNext() throws SQLException, ClassNotFoundException;

    boolean save(Payment1DTO DTO) throws SQLException, ClassNotFoundException;

    ArrayList<Payment1DTO> getAll() throws SQLException, ClassNotFoundException;

    boolean update(Payment1DTO DTO) throws SQLException, ClassNotFoundException;

    boolean delete(String Id) throws SQLException, ClassNotFoundException;

}
