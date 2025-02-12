package com.ijse.gdse.carwash.dao.custom;

import com.ijse.gdse.carwash.dao.CrudDAO;
import com.ijse.gdse.carwash.entity.Customer;

import java.sql.SQLException;

public interface CustomerDAO extends CrudDAO<Customer> {
//   String getNextCustomerId() throws SQLException;
//
//    boolean saveCustomer(CustomerDTO customerDTO) throws SQLException;
//
//    ArrayList<CustomerDTO> getAllCustomers() throws SQLException;
//
//    boolean updateCustomer(CustomerDTO customerDTO) throws SQLException;
//
//    boolean deleteCustomer(String customerId) throws SQLException;
//
    Customer findById(String selectedCusId) throws SQLException, ClassNotFoundException;
}
