package com.ijse.gdse.carwash.dao.custom.impl;

import com.ijse.gdse.carwash.dao.SQLUtil;
import com.ijse.gdse.carwash.dao.custom.Payment2DAO;
import com.ijse.gdse.carwash.entity.Payment2;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class Payment2DAOImpl implements Payment2DAO {
    public String getNext() throws SQLException, ClassNotFoundException {
        ResultSet rst = SQLUtil.execute("SELECT pay2_id FROM payment2 ORDER BY pay2_id DESC LIMIT 1");

        if (rst != null && !rst.next()) {

            return "W001";
        }

        String lastId = rst.getString(1);

        if (lastId != null && lastId.length() > 1) {
            String numericPart = lastId.substring(1);

            try {
                int numericValue = Integer.parseInt(numericPart);
                int newIdIndex = numericValue + 1;

                return String.format("w%03d", newIdIndex);
            } catch (NumberFormatException e) {

                return "w001";
            }
        } else {

            return "w001";
        }
    }

    public boolean save(Payment2 payment2DTO) throws SQLException, ClassNotFoundException {
        return SQLUtil.execute(
                "insert into payment2 values(?,?,?,?,?)",
                payment2DTO.getSupplierPaymentId(),
                payment2DTO.getDate(),
                payment2DTO.getSupplierId(),
                payment2DTO.getQty(),
                payment2DTO.getUnitPrice()

        );
    }
    public ArrayList<Payment2> getAll() throws SQLException, ClassNotFoundException {
        ResultSet rst = SQLUtil.execute("select * from payment2");

        ArrayList<Payment2> payment2DTOS = new ArrayList<>();

        while (rst.next()) {
            Payment2 payment2DTO = new Payment2(
                    rst.getString(1),
                    rst.getString(2),
                    rst.getString(3),
                    rst.getString(4),
                    rst.getString(5)
            );
            payment2DTOS.add(payment2DTO);
        }
        return payment2DTOS;
    }

    public boolean update(Payment2 payment2DTO) throws SQLException, ClassNotFoundException {
        return SQLUtil.execute(
                "UPDATE payment2 SET date=?, s_id=?, qty=?, unit_price=? WHERE pay2_id=?",
                payment2DTO.getDate(),
                payment2DTO.getSupplierId(),
                payment2DTO.getQty(),
                payment2DTO.getUnitPrice(),
                payment2DTO.getSupplierPaymentId()
        );
    }

    public boolean delete(String SupplierPaymentId) throws SQLException, ClassNotFoundException {
        return SQLUtil.execute("DELETE FROM payment2 WHERE pay2_id=?", SupplierPaymentId);
    }

    public ArrayList<String> getAllPayment2Ids() throws SQLException, ClassNotFoundException {
        ResultSet rst = SQLUtil.execute("select pay2_id from payment2");

        ArrayList<String> payment2Ids = new ArrayList<>();

        while (rst.next()) {
            payment2Ids.add(rst.getString(1));
        }

        return payment2Ids;
    }

    public Payment2 findById(String selectedSupplierPaymentId) throws SQLException, ClassNotFoundException {
        ResultSet rst = SQLUtil.execute("select * from payment2 where pay2_id=?", selectedSupplierPaymentId);

        if (rst.next()) {
            return new Payment2(
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
