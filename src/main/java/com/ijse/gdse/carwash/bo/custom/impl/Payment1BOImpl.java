package com.ijse.gdse.carwash.bo.custom.impl;

import com.ijse.gdse.carwash.bo.custom.Payment1BO;
import com.ijse.gdse.carwash.dao.DAOFactory;
import com.ijse.gdse.carwash.dao.custom.BookingDAO;
import com.ijse.gdse.carwash.dao.custom.Payment1DAO;
import com.ijse.gdse.carwash.dao.custom.ServiceTypeDAO;
import com.ijse.gdse.carwash.dto.Payment1DTO;
import com.ijse.gdse.carwash.entity.Payment1;

import java.sql.SQLException;
import java.util.ArrayList;

public class Payment1BOImpl implements Payment1BO {

    private final Payment1DAO payment1DAO = (Payment1DAO) DAOFactory.getInstance().getDAO(DAOFactory.DAOType.PAYMENT1);
    private final BookingDAO bookingDAO = (BookingDAO) DAOFactory.getInstance().getDAO(DAOFactory.DAOType.BOOKING);
    private final ServiceTypeDAO serviceTypeDAO = (ServiceTypeDAO) DAOFactory.getInstance().getDAO(DAOFactory.DAOType.SERVICETYPE);
    @Override
    public ArrayList<String> getAllPayment1Ids() throws SQLException, ClassNotFoundException {
        return payment1DAO.getAllPayment1Ids();
    }

    @Override
    public Payment1 findById(String selectedCustomerPaymentId) throws SQLException, ClassNotFoundException {
        return payment1DAO.findById(selectedCustomerPaymentId);
    }

    @Override
    public String getNext() throws SQLException, ClassNotFoundException {
        return payment1DAO.getNext();
    }

    @Override
    public boolean save(Payment1DTO DTO) throws SQLException, ClassNotFoundException {
        return payment1DAO.save(new Payment1(DTO.getCustomerPaymentId(),DTO.getAmount(),DTO.getPaymentMethod(),DTO.getBookingId()));
    }

    @Override
    public ArrayList<Payment1DTO> getAll() throws SQLException, ClassNotFoundException {
        ArrayList<Payment1> payment1s = payment1DAO.getAll();
        ArrayList<Payment1DTO> payment1DTOS = new ArrayList<>();
        for (Payment1 payment1 : payment1s) {
            Payment1DTO payment1DTO = new Payment1DTO(payment1.getCustomerPaymentId(),payment1.getAmount(),payment1.getPaymentMethod(),payment1.getBookingId());
        }
        return payment1DTOS;
    }

    @Override
    public boolean update(Payment1DTO DTO) throws SQLException, ClassNotFoundException {
        return payment1DAO.update(new Payment1(DTO.getCustomerPaymentId(),DTO.getAmount(),DTO.getPaymentMethod(),DTO.getBookingId()));
    }

    @Override
    public boolean delete(String Id) throws SQLException, ClassNotFoundException {
        return payment1DAO.delete(Id);
    }
}
