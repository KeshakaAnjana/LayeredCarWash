package com.ijse.gdse.carwash.bo.custom;

import com.ijse.gdse.carwash.bo.SuperBO;
import com.ijse.gdse.carwash.dto.SupplierDTO;
import com.ijse.gdse.carwash.entity.Supplier;

import java.sql.SQLException;
import java.util.ArrayList;

public interface SupplierBO extends SuperBO {

    Supplier findByName(String selectedSupplierId) throws SQLException, ClassNotFoundException;

    String SupplierIdFindByStatus(String stetus) throws SQLException, ClassNotFoundException;

    String getNext() throws SQLException, ClassNotFoundException;

    boolean save(SupplierDTO DTO) throws SQLException, ClassNotFoundException;

    ArrayList<SupplierDTO> getAll() throws SQLException, ClassNotFoundException;

    boolean update(SupplierDTO DTO) throws SQLException, ClassNotFoundException;

    boolean delete(String Id) throws SQLException, ClassNotFoundException;
}
