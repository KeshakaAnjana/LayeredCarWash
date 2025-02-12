package com.ijse.gdse.carwash.bo.custom.impl;

import com.ijse.gdse.carwash.bo.custom.CustomerBO;
import com.ijse.gdse.carwash.dao.DAOFactory;
import com.ijse.gdse.carwash.dao.custom.CustomerDAO;
import com.ijse.gdse.carwash.dto.CustomerDTO;
import com.ijse.gdse.carwash.entity.Customer;

import java.sql.SQLException;
import java.util.ArrayList;

public class CustomerBOImpl implements CustomerBO {

    private final CustomerDAO customerDAO = (CustomerDAO) DAOFactory.getInstance().getDAO(DAOFactory.DAOType.CUSTOMER);

    @Override
    public Customer findById(String selectedCusId) throws SQLException, ClassNotFoundException {
        return customerDAO.findById(selectedCusId);
    }

    @Override
    public String getNext() throws SQLException, ClassNotFoundException {
        return customerDAO.getNext();
    }

    @Override
    public boolean save(CustomerDTO DTO) throws SQLException, ClassNotFoundException {
        return customerDAO.save(new Customer(DTO.getCustomerId(),DTO.getName(),DTO.getEmail(),DTO.getPhone(),DTO.getUserId()));
    }

    @Override
    public ArrayList<CustomerDTO> getAll() throws SQLException, ClassNotFoundException {
        ArrayList<Customer> customers = customerDAO.getAll();
        ArrayList<CustomerDTO> customerDTOS = new ArrayList<>();
        for (Customer customer : customers) {
            customerDTOS.add(new CustomerDTO(customer.getCustomerId(),customer.getName(),customer.getEmail(),customer.getPhone(),customer.getUserId()));
        }
        return customerDTOS;
    }

    @Override
    public boolean update(CustomerDTO DTO) throws SQLException, ClassNotFoundException {
        return customerDAO.update(new Customer(DTO.getCustomerId(),DTO.getName(),DTO.getEmail(),DTO.getPhone(),DTO.getUserId()));
    }

    @Override
    public boolean delete(String Id) throws SQLException, ClassNotFoundException {
        return customerDAO.delete(Id);
    }
}
