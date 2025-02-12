package com.ijse.gdse.carwash.dao.custom.impl;

import com.ijse.gdse.carwash.dao.SQLUtil;
import com.ijse.gdse.carwash.dao.custom.BatchDAO;
import com.ijse.gdse.carwash.entity.Batch;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class BatchDAOImpl implements BatchDAO {

    public String getNext() throws SQLException, ClassNotFoundException {
        ResultSet rst = SQLUtil.execute("SELECT batch_id FROM batch ORDER BY batch_id DESC LIMIT 1");

        if (rst != null && !rst.next()) {
            return "t001";
        }

        String lastId = rst.getString(1);

        if (lastId != null && lastId.length() > 1) {
            String numericPart = lastId.substring(1);

            try {
                int numericValue = Integer.parseInt(numericPart);
                int newIdIndex = numericValue + 1;

                return String.format("t%03d", newIdIndex);
            } catch (NumberFormatException e) {
                return "t001";
            }
        } else {
            return "t001";
        }
    }

    public boolean save(Batch batchDTO) throws SQLException, ClassNotFoundException {
        return SQLUtil.execute(
                "INSERT INTO batch (batch_id,date,qty,price,p_id) VALUES (?,?,?,?,?)",
                batchDTO.getBatchID(),
                batchDTO.getDate(),
                batchDTO.getQty(),
                batchDTO.getPrice(),
                batchDTO.getProductId()
        );
    }

    public ArrayList<Batch> getAll() throws SQLException, ClassNotFoundException {
        ResultSet rst = SQLUtil.execute("SELECT * FROM batch");

        ArrayList<Batch> batchDTOS = new ArrayList<>();
        while (rst.next()) {
            Batch batchDTO = new Batch(
                    rst.getString(1),
                    rst.getString(2),
                    rst.getString(3),
                    rst.getString(4),
                    rst.getString(5)
            );
            batchDTOS.add(batchDTO);
        }
        return batchDTOS;
    }

    public boolean update(Batch batchDTO) throws SQLException, ClassNotFoundException {
        return SQLUtil.execute(
                "UPDATE batch SET date=?, qty=?, price=?, p_id=? WHERE batch_id=?",
                batchDTO.getDate(),
                batchDTO.getQty(),
                batchDTO.getPrice(),
                batchDTO.getProductId(),
                batchDTO.getBatchID()
        );
    }

    public boolean delete(String batchId) throws SQLException, ClassNotFoundException {
        return SQLUtil.execute("DELETE FROM batch WHERE batch_id=?", batchId);
    }

    public  ArrayList<String> getAllBatchIds() throws SQLException, ClassNotFoundException {
        ResultSet rst = SQLUtil.execute("select batch_id from batch");

        ArrayList<String> batchIds = new ArrayList<>();

        while (rst.next()) {
            batchIds.add(rst.getString(1));
        }

        return batchIds;
    }

    public Batch findById(String selectedBatchId) throws SQLException, ClassNotFoundException {
        ResultSet rst = SQLUtil.execute("select * from batch where batch_id=?", selectedBatchId);

        if (rst.next()) {
            return new Batch(
                    rst.getString(1),
                    rst.getString(2),
                    rst.getString(3),
                    rst.getString(4),
                    rst.getString(5)
            );
        }
        return null;
    }
    public boolean updateQty(String pId ,String qty) throws SQLException, ClassNotFoundException {
        return SQLUtil.execute("UPDATE batch SET qty = ?  WHERE p_id =?",
                qty, pId
        );
    }

}
