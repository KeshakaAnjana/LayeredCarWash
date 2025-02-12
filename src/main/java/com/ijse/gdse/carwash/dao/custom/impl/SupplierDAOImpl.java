package com.ijse.gdse.carwash.dao.custom.impl;

import com.ijse.gdse.carwash.dao.SQLUtil;
import com.ijse.gdse.carwash.dao.custom.SupplierDAO;
import com.ijse.gdse.carwash.entity.Supplier;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class SupplierDAOImpl implements SupplierDAO {
    public String getNext() throws SQLException, ClassNotFoundException {
        ResultSet rst = SQLUtil.execute("SELECT s_id FROM supplier ORDER BY s_id DESC LIMIT 1");

        if (rst != null && !rst.next()) {
            return "a001";
        }

        String lastId = rst.getString(1);

        if (lastId != null && lastId.length() > 1) {
            String numericPart = lastId.substring(1);

            try {
                int numericValue = Integer.parseInt(numericPart);
                int newIdIndex = numericValue + 1;

                return String.format("a%03d", newIdIndex);
            } catch (NumberFormatException e) {
                return "a001";
            }
        } else {
            return "a001";
        }
    }

    public boolean save(Supplier supplierDTO) throws SQLException, ClassNotFoundException {
        return SQLUtil.execute(
                "INSERT INTO supplier (s_id,name,status,contact_no,unit_price,qty) VALUES (?,?,?,?,?,?)",
                supplierDTO.getSupplierId(),
                supplierDTO.getSupplierName(),
                supplierDTO.getStatus(),
                supplierDTO.getContactNo(),
                supplierDTO.getUnitPrice(),
                supplierDTO.getQty()
        );
    }

    public ArrayList<Supplier> getAll() throws SQLException, ClassNotFoundException {
        ResultSet rst = SQLUtil.execute("SELECT * FROM supplier");

        ArrayList<Supplier> supplierDTOS = new ArrayList<>();
        while (rst.next()) {
            Supplier supplierDTO = new Supplier(
                    rst.getString(1),
                    rst.getString(2),
                    rst.getString(3),
                    rst.getString(4),
                    rst.getString(5),
                    rst.getString(6)
            );
            supplierDTOS.add(supplierDTO);
        }
        return supplierDTOS;
    }

    public boolean update(Supplier supplierDTO) throws SQLException, ClassNotFoundException {
        return SQLUtil.execute(
                "UPDATE supplier SET name=?, status=?, contact_no=?, unit_price=?, qty=? WHERE s_id=?",
                supplierDTO.getSupplierName(),
                supplierDTO.getStatus(),
                supplierDTO.getContactNo(),
                supplierDTO.getUnitPrice(),
                supplierDTO.getQty(),
                supplierDTO.getSupplierId()
        );
    }

    public boolean delete(String SupplierId) throws SQLException, ClassNotFoundException {
        return SQLUtil.execute("DELETE FROM supplier WHERE s_id=?", SupplierId);
    }

    public static ArrayList<String> getAllSupplierStatus() throws SQLException, ClassNotFoundException {
        ResultSet rst = SQLUtil.execute("SELECT status FROM supplier");

        ArrayList<String> supplierStatus = new ArrayList<>();

        while (rst.next()) {
            supplierStatus.add(rst.getString(1));
        }

        return supplierStatus;
    }

    public Supplier findByName(String selectedSupplierId) throws SQLException, ClassNotFoundException {
        ResultSet rst = SQLUtil.execute("SELECT * FROM supplier WHERE status=?", selectedSupplierId);

        if (rst.next()) {
            return new Supplier(
                    rst.getString(1),
                    rst.getString(2),
                    rst.getString(3),
                    rst.getString(4),
                    rst.getString(5),
                    rst.getString(6)
            );
        }
        return null;
    }

    public String SupplierIdFindByStatus(String stetus) throws SQLException, ClassNotFoundException {
       // System.out.println("Selected Supplier ID: " + stetus);

        ResultSet rst = SQLUtil.execute("SELECT s_id FROM supplier WHERE status=?", stetus);

        if (rst.next()) {
            String productName = rst.getString(1);
            return productName;
        } else {
            return null;
        }
    }
}
//    public String priceFindFbServiceId(String serviceId) throws SQLException {
//        // Execute query to fetch the price from the service table using service_id
//        ResultSet resultSet = CrudUtil.execute("SELECT price FROM service WHERE service_id=?", serviceId);
//
//        // Check if the result set contains any data
//        if (resultSet.next()) {
//            return resultSet.getString(1);  // Return the price
//        } else {
//            // If no record is found, return null
//            System.out.println("No price found for service_id: " + serviceId);
//            return null;
//        }
//        }
//}
