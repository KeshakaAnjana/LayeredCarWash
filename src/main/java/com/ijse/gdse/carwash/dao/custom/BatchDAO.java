package com.ijse.gdse.carwash.dao.custom;

import com.ijse.gdse.carwash.dao.CrudDAO;
import com.ijse.gdse.carwash.entity.Batch;

import java.sql.SQLException;
import java.util.ArrayList;

public interface BatchDAO extends CrudDAO<Batch> {
//    String getNextBatchId() throws SQLException;
//
//    boolean saveBatch(BatchDTO batchDTO) throws SQLException;
//
//    ArrayList<BatchDTO> getAllBatch() throws SQLException;

    //boolean updateBatch(BatchDTO batchDTO) throws SQLException;

   // boolean deleteBatch(String batchId) throws SQLException;

     ArrayList<String> getAllBatchIds() throws SQLException, ClassNotFoundException;

    Batch findById(String selectedBatchId) throws SQLException, ClassNotFoundException;

    boolean updateQty(String pId ,String qty) throws SQLException, ClassNotFoundException;
}
