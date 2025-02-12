package com.ijse.gdse.carwash.dao.custom;

import com.ijse.gdse.carwash.dao.CrudDAO;
import com.ijse.gdse.carwash.entity.Payment1;

import java.sql.SQLException;
import java.util.ArrayList;

public interface Payment1DAO extends CrudDAO<Payment1> {
//    String getNextPayment1Id() throws SQLException;
//
//    boolean savePayment1(Payment1DTO payment1DTO) throws SQLException;
//
//    ArrayList<Payment1DTO> getAllPayment1() throws SQLException;
//
//    boolean updatePayment1(Payment1DTO payment1DTO) throws SQLException;
//
//    boolean deletePayment1(String CustomerPaymentId) throws SQLException;

    ArrayList<String> getAllPayment1Ids() throws SQLException, ClassNotFoundException;

    Payment1 findById(String selectedCustomerPaymentId) throws SQLException, ClassNotFoundException;

}
