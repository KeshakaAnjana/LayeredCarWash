package com.ijse.gdse.carwash.bo.custom.impl;

import com.ijse.gdse.carwash.bo.custom.Payment2BO;
import com.ijse.gdse.carwash.dao.DAOFactory;
import com.ijse.gdse.carwash.dao.custom.Payment2DAO;
import com.ijse.gdse.carwash.dao.custom.SupplierDAO;
import com.ijse.gdse.carwash.dto.Payment2DTO;
import com.ijse.gdse.carwash.entity.Payment2;

import java.sql.SQLException;
import java.util.ArrayList;

public class Payment2BOImpl implements Payment2BO {

    private final Payment2DAO payment2DAO = (Payment2DAO) DAOFactory.getInstance().getDAO(DAOFactory.DAOType.PAYMENT2);
    private final SupplierDAO supplierDAO = (SupplierDAO) DAOFactory.getInstance().getDAO(DAOFactory.DAOType.SUPPLIER);

    @Override
    public ArrayList<String> getAllPayment2Ids() throws SQLException, ClassNotFoundException {
        return payment2DAO.getAllPayment2Ids();
    }

    @Override
    public Payment2 findById(String selectedSupplierPaymentId) throws SQLException, ClassNotFoundException {
        return payment2DAO.findById(selectedSupplierPaymentId);
    }

    @Override
    public String getNext() throws SQLException, ClassNotFoundException {
        return payment2DAO.getNext();
    }

    @Override
    public boolean save(Payment2DTO DTO) throws SQLException, ClassNotFoundException {
        return payment2DAO.save(new Payment2(DTO.getSupplierPaymentId(),DTO.getDate(),DTO.getSupplierId(),DTO.getQty(),DTO.getUnitPrice()));
    }

    @Override
    public ArrayList<Payment2DTO> getAll() throws SQLException, ClassNotFoundException {
        ArrayList<Payment2> payment2s = payment2DAO.getAll();
        ArrayList<Payment2DTO> payment2DTOS = new ArrayList<>();
        for (Payment2 payment2 : payment2s) {
            payment2DTOS.add(new Payment2DTO(payment2.getSupplierPaymentId(),payment2.getDate(),payment2.getSupplierId(),payment2.getQty(),payment2.getUnitPrice()));
        }
        return payment2DTOS;
    }

    @Override
    public boolean update(Payment2DTO DTO) throws SQLException, ClassNotFoundException {
        return payment2DAO.update(new Payment2(DTO.getSupplierPaymentId(),DTO.getDate(),DTO.getSupplierId(),DTO.getQty(),DTO.getUnitPrice()));
    }

    @Override
    public boolean delete(String Id) throws SQLException, ClassNotFoundException {
        return payment2DAO.delete(Id);
    }
}
