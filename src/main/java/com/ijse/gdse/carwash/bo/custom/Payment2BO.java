package com.ijse.gdse.carwash.bo.custom;

import com.ijse.gdse.carwash.bo.SuperBO;
import com.ijse.gdse.carwash.dto.Payment2DTO;
import com.ijse.gdse.carwash.entity.Payment2;

import java.sql.SQLException;
import java.util.ArrayList;

public interface Payment2BO extends SuperBO {

    ArrayList<String> getAllPayment2Ids() throws SQLException, ClassNotFoundException;

    public Payment2 findById(String selectedSupplierPaymentId) throws SQLException, ClassNotFoundException;

    String getNext() throws SQLException, ClassNotFoundException;

    boolean save(Payment2DTO DTO) throws SQLException, ClassNotFoundException;

    ArrayList<Payment2DTO> getAll() throws SQLException, ClassNotFoundException;

    boolean update(Payment2DTO DTO) throws SQLException, ClassNotFoundException;

    boolean delete(String Id) throws SQLException, ClassNotFoundException;
}
