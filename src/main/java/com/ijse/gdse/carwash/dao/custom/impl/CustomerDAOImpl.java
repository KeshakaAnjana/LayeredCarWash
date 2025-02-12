package com.ijse.gdse.carwash.dao.custom.impl;

import com.ijse.gdse.carwash.dao.SQLUtil;
import com.ijse.gdse.carwash.dao.custom.CustomerDAO;
import com.ijse.gdse.carwash.entity.Customer;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class CustomerDAOImpl implements CustomerDAO {

    public String getNext() throws SQLException, ClassNotFoundException {
        ResultSet rst = SQLUtil.execute("SELECT c_id FROM customer ORDER BY c_id DESC LIMIT 1");

        if (rst.next()) {
            String lastId = rst.getString(1); // Last customer ID
            String substring = lastId.substring(1); // Remove 'c' from the ID
            int i = Integer.parseInt(substring);
            int newIdIndex = i + 1;
            return String.format("c%03d", newIdIndex); // Return next ID like c001, c002, etc.
        }
        return "c001"; // Default ID if no records are found
    }

    public boolean save(Customer customerDTO) throws SQLException, ClassNotFoundException {
        return SQLUtil.execute(
                "INSERT INTO customer (c_id, c_name, email, contact_no, id) VALUES (?,?,?,?,?)",
                customerDTO.getCustomerId(),
                customerDTO.getName(),
                customerDTO.getEmail(),
                customerDTO.getPhone(),
                customerDTO.getUserId()
        );
    }

    public ArrayList<Customer> getAll() throws SQLException, ClassNotFoundException {
        ResultSet rst = SQLUtil.execute("SELECT * FROM customer");

        ArrayList<Customer> customerDTOS = new ArrayList<>();
        while (rst.next()) {
            Customer customerDTO = new Customer(
                    rst.getString(1),
                    rst.getString(2),
                    rst.getString(3),
                    rst.getString(4),
                    rst.getString(5)
            );
            customerDTOS.add(customerDTO);
        }
        return customerDTOS;
    }

    public boolean update(Customer customerDTO) throws SQLException, ClassNotFoundException {
        return SQLUtil.execute(
                "UPDATE customer SET c_name=?, email=?, contact_no=?, id=? WHERE c_id=?",
                customerDTO.getName(),
                customerDTO.getEmail(),
                customerDTO.getPhone(),
                customerDTO.getUserId(),
                customerDTO.getCustomerId()
        );
    }

    public boolean delete(String customerId) throws SQLException, ClassNotFoundException {
        return SQLUtil.execute("DELETE FROM customer WHERE c_id=?", customerId);
    }

    public static ArrayList<String> getAllCustomerIds() throws SQLException, ClassNotFoundException {
        ResultSet rst = SQLUtil.execute("select c_id from customer");

        ArrayList<String> customerIds = new ArrayList<>();

        while (rst.next()) {
            customerIds.add(rst.getString(1));
        }

        return customerIds;
    }

    public Customer findById(String selectedCusId) throws SQLException, ClassNotFoundException {
        ResultSet rst = SQLUtil.execute("select * from customer where c_id=?", selectedCusId);

        if (rst.next()) {
            return new Customer(
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
