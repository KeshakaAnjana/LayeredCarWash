package com.ijse.gdse.carwash.dao.custom;

import com.ijse.gdse.carwash.dao.CrudDAO;
import com.ijse.gdse.carwash.dao.custom.impl.BatchDAOImpl;
import com.ijse.gdse.carwash.entity.Product;

import java.sql.SQLException;
import java.util.ArrayList;

public interface ProductDAO extends CrudDAO<Product> {
    final BatchDAOImpl batchModel = new BatchDAOImpl();

//    String getNextProductId() throws SQLException;
//
//    boolean saveProduct(ProductDTO productDTO) throws SQLException;
//
//    ArrayList<ProductDTO> getAllProduct() throws SQLException;
//
//    boolean updateProduct(ProductDTO productDTO) throws SQLException;
//
//    boolean deleteProduct(String productId) throws SQLException;

    Product findById(String selectedProductId) throws SQLException, ClassNotFoundException;

    //boolean update(ProductDTO DTO) throws SQLException;

}
