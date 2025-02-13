package com.ijse.gdse.carwash.dao;

import com.ijse.gdse.carwash.dto.CustomerDTO;

import java.sql.SQLException;
import java.util.ArrayList;

public interface CrudDAO <T> extends SuperDAO{
    String getNext() throws SQLException, ClassNotFoundException;

    boolean save(T DTO) throws SQLException, ClassNotFoundException;

    ArrayList<T> getAll() throws SQLException, ClassNotFoundException;

    boolean update(T DTO) throws SQLException, ClassNotFoundException;

    boolean delete(String Id) throws SQLException, ClassNotFoundException;

    //CustomerDTO findById(String selectedCusId) throws SQLException;
}
