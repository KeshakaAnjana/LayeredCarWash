package com.ijse.gdse.carwash.dao.custom;

import com.ijse.gdse.carwash.dao.CrudDAO;
import com.ijse.gdse.carwash.entity.Payment2;

import java.sql.SQLException;
import java.util.ArrayList;

public interface Payment2DAO extends CrudDAO<Payment2> {
//    String getNextPayment2Id() throws SQLException;
//
//    boolean savePayment2(Payment2DTO payment2DTO) throws SQLException;
//
//    ArrayList<Payment2DTO> getAllPayment2() throws SQLException;
//
//    boolean updatePayment2(Payment2DTO payment2DTO) throws SQLException;
//
//    boolean deletePayment2(String SupplierPaymentId) throws SQLException;

    ArrayList<String> getAllPayment2Ids() throws SQLException, ClassNotFoundException;

    Payment2 findById(String selectedSupplierPaymentId) throws SQLException, ClassNotFoundException;

}
