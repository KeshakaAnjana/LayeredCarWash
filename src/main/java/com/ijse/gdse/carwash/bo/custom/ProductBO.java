package com.ijse.gdse.carwash.bo.custom;

import com.ijse.gdse.carwash.bo.SuperBO;
import com.ijse.gdse.carwash.dto.ProductDTO;
import com.ijse.gdse.carwash.entity.Product;

import java.sql.SQLException;
import java.util.ArrayList;

public interface ProductBO extends SuperBO {

    Product findById(String selectedProductId) throws SQLException, ClassNotFoundException;

    boolean update(ProductDTO DTO) throws SQLException, ClassNotFoundException;

    String getNext() throws SQLException, ClassNotFoundException;

    boolean save(ProductDTO DTO) throws SQLException, ClassNotFoundException;

    ArrayList<ProductDTO> getAll() throws SQLException, ClassNotFoundException;

    //boolean update(ProductDTO DTO) throws SQLException;

    boolean delete(String Id) throws SQLException, ClassNotFoundException;
}
