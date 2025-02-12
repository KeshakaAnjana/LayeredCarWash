package com.ijse.gdse.carwash.bo.custom;

import com.ijse.gdse.carwash.bo.SuperBO;
import com.ijse.gdse.carwash.dto.BatchDTO;
import com.ijse.gdse.carwash.entity.Batch;

import java.sql.SQLException;
import java.util.ArrayList;

public interface BatchBO extends SuperBO {

    ArrayList<String> getAllBatchIds() throws SQLException, ClassNotFoundException;

    Batch findById(String selectedBatchId) throws SQLException, ClassNotFoundException;

    boolean updateQty(String pId ,String qty) throws SQLException, ClassNotFoundException;

    String getNext() throws SQLException, ClassNotFoundException;

    boolean save(BatchDTO DTO) throws SQLException, ClassNotFoundException;

    ArrayList<BatchDTO> getAll() throws SQLException, ClassNotFoundException;

    boolean update(BatchDTO DTO) throws SQLException, ClassNotFoundException;

    boolean delete(String Id) throws SQLException, ClassNotFoundException;
}
