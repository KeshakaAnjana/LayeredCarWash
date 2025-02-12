package com.ijse.gdse.carwash.bo.custom;

import com.ijse.gdse.carwash.bo.SuperBO;
import com.ijse.gdse.carwash.dto.CustomerDTO;
import com.ijse.gdse.carwash.entity.Customer;

import java.sql.SQLException;
import java.util.ArrayList;

public interface CustomerBO extends SuperBO {
    Customer findById(String selectedCusId) throws SQLException, ClassNotFoundException;

    String getNext() throws SQLException, ClassNotFoundException;

    boolean save(CustomerDTO DTO) throws SQLException, ClassNotFoundException;

    ArrayList<CustomerDTO> getAll() throws SQLException, ClassNotFoundException;

    boolean update(CustomerDTO DTO) throws SQLException, ClassNotFoundException;

    boolean delete(String Id) throws SQLException, ClassNotFoundException;
}
