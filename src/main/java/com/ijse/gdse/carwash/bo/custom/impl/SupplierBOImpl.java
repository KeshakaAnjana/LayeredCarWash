package com.ijse.gdse.carwash.bo.custom.impl;

import com.ijse.gdse.carwash.bo.custom.SupplierBO;
import com.ijse.gdse.carwash.dao.DAOFactory;
import com.ijse.gdse.carwash.dao.custom.SupplierDAO;
import com.ijse.gdse.carwash.dto.SupplierDTO;
import com.ijse.gdse.carwash.dto.VehicleDTO;
import com.ijse.gdse.carwash.entity.Supplier;
import com.ijse.gdse.carwash.entity.Vehicle;

import java.sql.SQLException;
import java.util.ArrayList;

public class SupplierBOImpl implements SupplierBO {

    private final SupplierDAO supplierDAO = (SupplierDAO) DAOFactory.getInstance().getDAO(DAOFactory.DAOType.SUPPLIER);
    @Override
    public Supplier findByName(String selectedSupplierId) throws SQLException, ClassNotFoundException {
        return supplierDAO.findByName(selectedSupplierId);
    }

    @Override
    public String SupplierIdFindByStatus(String stetus) throws SQLException, ClassNotFoundException {
        return supplierDAO.SupplierIdFindByStatus(stetus);
    }

    @Override
    public String getNext() throws SQLException, ClassNotFoundException {
        return supplierDAO.getNext();
    }

    @Override
    public boolean save(SupplierDTO DTO) throws SQLException, ClassNotFoundException {
        return supplierDAO.save(new Supplier(DTO.getSupplierId(),DTO.getSupplierName(),DTO.getStatus(),DTO.getContactNo(),DTO.getUnitPrice(),DTO.getQty()));
    }

    @Override
    public ArrayList<SupplierDTO> getAll() throws SQLException, ClassNotFoundException {
        ArrayList<Supplier> suppliers = supplierDAO.getAll();
        ArrayList<SupplierDTO> supplierDTOS = new ArrayList<>();
        for (Supplier supplier : suppliers) {
            supplierDTOS.add(new SupplierDTO(supplier.getSupplierId(),supplier.getSupplierName(),supplier.getStatus(),supplier.getContactNo(),supplier.getUnitPrice(),supplier.getQty()));
        }
        return supplierDTOS;
    }

    @Override
    public boolean update(SupplierDTO DTO) throws SQLException, ClassNotFoundException {
        return supplierDAO.update(new Supplier(DTO.getSupplierId(),DTO.getSupplierName(),DTO.getStatus(),DTO.getContactNo(),DTO.getUnitPrice(),DTO.getQty()));
    }

    @Override
    public boolean delete(String Id) throws SQLException, ClassNotFoundException {
        return supplierDAO.delete(Id);
    }
}
