package com.ijse.gdse.carwash.bo.custom.impl;

import com.ijse.gdse.carwash.bo.custom.BatchBO;
import com.ijse.gdse.carwash.dao.DAOFactory;
import com.ijse.gdse.carwash.dao.custom.BatchDAO;
import com.ijse.gdse.carwash.dao.custom.ProductDAO;
import com.ijse.gdse.carwash.dto.BatchDTO;
import com.ijse.gdse.carwash.dto.VehicleDTO;
import com.ijse.gdse.carwash.entity.Batch;
import com.ijse.gdse.carwash.entity.Vehicle;

import java.sql.SQLException;
import java.util.ArrayList;

public class BatchBOImpl implements BatchBO {

    private final BatchDAO batchDAO = (BatchDAO) DAOFactory.getInstance().getDAO(DAOFactory.DAOType.BATCH);
    private final ProductDAO productDAO = (ProductDAO) DAOFactory.getInstance().getDAO(DAOFactory.DAOType.PRODUCT);


    @Override
    public ArrayList<String> getAllBatchIds() throws SQLException, ClassNotFoundException {
        return batchDAO.getAllBatchIds();
    }

    @Override
    public Batch findById(String selectedBatchId) throws SQLException, ClassNotFoundException {
        return batchDAO.findById(selectedBatchId);
    }

    @Override
    public boolean updateQty(String pId, String qty) throws SQLException, ClassNotFoundException {
        return batchDAO.updateQty(pId, qty);
    }

    @Override
    public String getNext() throws SQLException, ClassNotFoundException {
        return batchDAO.getNext();
    }

    @Override
    public boolean save(BatchDTO DTO) throws SQLException, ClassNotFoundException {
        return batchDAO.save(new Batch(DTO.getBatchID(),DTO.getDate(),DTO.getQty(),DTO.getPrice(),DTO.getProductId()));
    }

    @Override
    public ArrayList<BatchDTO> getAll() throws SQLException, ClassNotFoundException {
        ArrayList<Batch> batches = batchDAO.getAll();
        ArrayList<BatchDTO> batchDTOS = new ArrayList<>();
        for (Batch batch : batches) {
            batchDTOS.add(new BatchDTO(batch.getBatchID(),batch.getDate(),batch.getQty(),batch.getPrice(),batch.getProductId()));
        }
        return batchDTOS;
    }

    @Override
    public boolean update(BatchDTO DTO) throws SQLException, ClassNotFoundException {
        return batchDAO.update(new Batch(DTO.getBatchID(),DTO.getDate(),DTO.getQty(),DTO.getPrice(),DTO.getProductId()));
    }

    @Override
    public boolean delete(String Id) throws SQLException, ClassNotFoundException {
        return batchDAO.delete(Id);
    }
}
