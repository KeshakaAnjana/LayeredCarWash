package com.ijse.gdse.carwash.dao.custom.impl;

import com.ijse.gdse.carwash.dao.SQLUtil;
import com.ijse.gdse.carwash.dao.custom.Payment1DAO;
import com.ijse.gdse.carwash.entity.Payment1;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class Payment1DAOImpl implements Payment1DAO {
    public String getNext() throws SQLException, ClassNotFoundException {
        ResultSet rst = SQLUtil.execute("SELECT pay1_id FROM payment1 ORDER BY pay1_id DESC LIMIT 1");

        if (rst != null && !rst.next()) {

            return "v001";
        }

        String lastId = rst.getString(1);

        if (lastId != null && lastId.length() > 1) {
            String numericPart = lastId.substring(1);  // Remove 'b' and get the number part

            try {
                int numericValue = Integer.parseInt(numericPart); // Convert to integer
                int newIdIndex = numericValue + 1;  // Increment the numeric part

                return String.format("v%03d", newIdIndex);
            } catch (NumberFormatException e) {

                return "v001";
            }
        } else {

            return "v001";
        }
    }

    public boolean save(Payment1 payment1DTO) throws SQLException, ClassNotFoundException {
        return SQLUtil.execute(
                "insert into payment1 values(?,?,?,?)",
                payment1DTO.getCustomerPaymentId(),
                payment1DTO.getAmount(),
                payment1DTO.getPaymentMethod(),
                payment1DTO.getBookingId()

        );
    }
    public ArrayList<Payment1> getAll() throws SQLException, ClassNotFoundException {
        ResultSet rst = SQLUtil.execute("select * from payment1");

        ArrayList<Payment1> payment1DTOS = new ArrayList<>();

        while (rst.next()) {
            Payment1 payment1DTO = new Payment1(
                    rst.getString(1),
                    rst.getString(2),
                    rst.getString(3),
                    rst.getString(4)
            );
            payment1DTOS.add(payment1DTO);
        }
        return payment1DTOS;
    }

    public boolean update(Payment1 payment1DTO) throws SQLException, ClassNotFoundException {
        return SQLUtil.execute(
                "UPDATE payment1 SET amount=?, pay_method=?, booking_id=? WHERE pay1_id=?",
                payment1DTO.getAmount(),
                payment1DTO.getPaymentMethod(),
                payment1DTO.getBookingId(),
                payment1DTO.getCustomerPaymentId()
        );
    }

    public boolean delete(String CustomerPaymentId) throws SQLException, ClassNotFoundException {
        return SQLUtil.execute("DELETE FROM payment1 WHERE pay1_id=?", CustomerPaymentId);
    }

    public ArrayList<String> getAllPayment1Ids() throws SQLException, ClassNotFoundException {
        ResultSet rst = SQLUtil.execute("select pay1_id from payment1");

        ArrayList<String> payment1Ids = new ArrayList<>();

        while (rst.next()) {
            payment1Ids.add(rst.getString(1));
        }

        return payment1Ids;
    }

    public Payment1 findById(String selectedCustomerPaymentId) throws SQLException, ClassNotFoundException {
        ResultSet rst = SQLUtil.execute("select * from payment1 where pay1_id=?", selectedCustomerPaymentId);

        if (rst.next()) {
            return new Payment1(
                    rst.getString(1),
                    rst.getString(2),
                    rst.getString(3),
                    rst.getString(4)
            );
        }
        return null;
    }
}



