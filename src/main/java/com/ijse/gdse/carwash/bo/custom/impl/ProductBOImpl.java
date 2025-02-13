package com.ijse.gdse.carwash.bo.custom.impl;

import com.ijse.gdse.carwash.bo.custom.ProductBO;
import com.ijse.gdse.carwash.dao.DAOFactory;
import com.ijse.gdse.carwash.dao.SQLUtil;
import com.ijse.gdse.carwash.dao.custom.BatchDAO;
import com.ijse.gdse.carwash.dao.custom.ProductDAO;
import com.ijse.gdse.carwash.dao.custom.SupplierDAO;
import com.ijse.gdse.carwash.db.DBConnection;
import com.ijse.gdse.carwash.dto.ProductDTO;
import com.ijse.gdse.carwash.entity.Product;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;

public class ProductBOImpl implements ProductBO {

    private final ProductDAO productDAO = (ProductDAO) DAOFactory.getInstance().getDAO(DAOFactory.DAOType.PRODUCT);
    private final SupplierDAO supplierDAO = (SupplierDAO) DAOFactory.getInstance().getDAO(DAOFactory.DAOType.SUPPLIER);
    private final BatchDAO batchDAO = (BatchDAO) DAOFactory.getInstance().getDAO(DAOFactory.DAOType.BATCH);
    @Override
    public Product findById(String selectedProductId) throws SQLException, ClassNotFoundException {
        return productDAO.findById(selectedProductId);
    }

//    @Override
//    public boolean update(ProductDTO DTO) throws SQLException, ClassNotFoundException {
//        return productDAO.update(new Product(DTO.getProductId(),DTO.getProductName(),DTO.getDate(),DTO.getPrice(),DTO.getQty()));
//    }

    @Override
    public String getNext() throws SQLException, ClassNotFoundException {
        return productDAO.getNext();
    }

    @Override
    public boolean save(ProductDTO DTO) throws SQLException, ClassNotFoundException {
        return productDAO.save(new Product(DTO.getProductId(),DTO.getProductName(),DTO.getDate(),DTO.getPrice(),DTO.getQty()));
    }

    @Override
    public ArrayList<ProductDTO> getAll() throws SQLException, ClassNotFoundException {
        ArrayList<Product> products = productDAO.getAll();
        ArrayList<ProductDTO> productDTOs = new ArrayList<>();
        for (Product product : products) {
            productDTOs.add(new ProductDTO(product.getProductId(),product.getProductName(),product.getDate(),product.getPrice(),product.getQty()));
        }
        return productDTOs;
    }

    @Override
    public boolean delete(String Id) throws SQLException, ClassNotFoundException {
        return productDAO.delete(Id);
    }

    public boolean update(ProductDTO DTO) throws SQLException {
        Connection connection = DBConnection.getInstance().getConnection();

        try {
            connection.setAutoCommit(false);
            boolean isUpdate = productDAO.update(new Product(DTO.getProductId(),DTO.getProductName(),DTO.getDate(),DTO.getPrice(),DTO.getQty()));

//                    "UPDATE product SET name=?, date=?, price=?, qty=? WHERE p_id=?",
//                    productDTO.getProductName(),
//                    productDTO.getDate(),
//                    productDTO.getPrice(),
//                    productDTO.getQty(),
//                    productDTO.getProductId()
//            );
            
            if (isUpdate) {
                boolean isUpdateBatch = batchDAO.updateQty(DTO.getProductId(),DTO.getQty());
                if (isUpdateBatch) {
                    connection.commit();
                    return true;
                }
            
                connection.rollback();
                return false;
            }
            connection.rollback();
            return false;
        }catch (Exception e) {
            // @catch: Rolls back the transaction in case of any exception
            connection.rollback();
            return false;
        }finally {
            // @finally: Resets auto-commit to true after the operation
            connection.setAutoCommit(true); // 4
        }
    }
}
