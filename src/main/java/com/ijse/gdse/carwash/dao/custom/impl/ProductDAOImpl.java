package com.ijse.gdse.carwash.dao.custom.impl;

import com.ijse.gdse.carwash.dao.SQLUtil;
import com.ijse.gdse.carwash.dao.custom.BatchDAO;
import com.ijse.gdse.carwash.dao.custom.ProductDAO;
import com.ijse.gdse.carwash.entity.Product;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class ProductDAOImpl implements ProductDAO {

    private final BatchDAO batchDAO = new BatchDAOImpl();

    public String getNext() throws SQLException, ClassNotFoundException {
        ResultSet rst = SQLUtil.execute("SELECT p_id FROM product ORDER BY p_id DESC LIMIT 1");

        if (rst != null && !rst.next()) {
            return "p001";
        }

        String lastId = rst.getString(1);

        if (lastId != null && lastId.length() > 1) {
            String numericPart = lastId.substring(1);

            try {
                int numericValue = Integer.parseInt(numericPart);
                int newIdIndex = numericValue + 1;

                return String.format("p%03d", newIdIndex);
            } catch (NumberFormatException e) {
                return "p001";
            }
        } else {
            return "p001";
        }
    }
    public boolean save(Product productDTO) throws SQLException, ClassNotFoundException {
        return SQLUtil.execute(
                "INSERT INTO product (p_id,name,date,price,qty) VALUES (?,?,?,?,?)",
                productDTO.getProductId(),
                productDTO.getProductName(),
                productDTO.getDate(),
                productDTO.getPrice(),
                productDTO.getQty()
        );
    }


    public ArrayList<Product> getAll() throws SQLException, ClassNotFoundException {
        ResultSet rst = SQLUtil.execute("SELECT * FROM product");

        ArrayList<Product> productDTOS = new ArrayList<>();
        while (rst.next()) {
            Product productDTO = new Product(
                    rst.getString(1),
                    rst.getString(2),
                    rst.getString(3),
                    rst.getString(4),
                    rst.getString(5)
            );
            productDTOS.add(productDTO);
        }
        return productDTOS;
    }

    @Override
    public boolean update(Product productDTO) throws SQLException {
        return Boolean.parseBoolean(null);
    }

    public boolean delete(String productId) throws SQLException, ClassNotFoundException {
        return SQLUtil.execute("DELETE FROM product WHERE p_id=?", productId);
    }

    public static ArrayList<String> getAllProductIds() throws SQLException, ClassNotFoundException {
        ResultSet rst = SQLUtil.execute("SELECT p_id FROM product");

        ArrayList<String> productIds = new ArrayList<>();

        while (rst.next()) {
            productIds.add(rst.getString(1));
        }

        return productIds;
    }

    public Product findById(String selectedProductId) throws SQLException, ClassNotFoundException {
        ResultSet rst = SQLUtil.execute("SELECT * FROM product WHERE p_id=?", selectedProductId);

        if (rst.next()) {
            return new Product(
                    rst.getString(1),
                    rst.getString(2),
                    rst.getString(3),
                    rst.getString(4),
                    rst.getString(5)
            );
        }
        return null;
    }
}
