package com.ijse.gdse.carwash.dao.custom;

import com.ijse.gdse.carwash.dao.CrudDAO;
import com.ijse.gdse.carwash.entity.Supplier;

import java.sql.SQLException;

public interface SupplierDAO extends CrudDAO<Supplier> {
//    String getNextSupplierId() throws SQLException;
//
//    boolean saveSupplier(SupplierDTO supplierDTO) throws SQLException;
//
//    ArrayList<SupplierDTO> getAllSupplier() throws SQLException;
//
//    boolean updateSupplier(SupplierDTO supplierDTO) throws SQLException;
//
//    boolean deleteSupplier(String SupplierId) throws SQLException;

    Supplier findByName(String selectedSupplierId) throws SQLException, ClassNotFoundException;

    String SupplierIdFindByStatus(String stetus) throws SQLException, ClassNotFoundException;
}
