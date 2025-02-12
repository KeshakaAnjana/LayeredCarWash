package com.ijse.gdse.carwash.bo.custom.impl;

import com.ijse.gdse.carwash.bo.custom.BookingBO;
import com.ijse.gdse.carwash.dao.DAOFactory;
import com.ijse.gdse.carwash.dao.custom.BookingDAO;
import com.ijse.gdse.carwash.dao.custom.CustomerDAO;
import com.ijse.gdse.carwash.dao.custom.ServiceTypeDAO;
import com.ijse.gdse.carwash.dto.BookingDTO;
import com.ijse.gdse.carwash.entity.Booking;

import java.sql.SQLException;
import java.util.ArrayList;

public class BookingBOImpl implements BookingBO {

    private final ServiceTypeDAO serviceTypeDAO = (ServiceTypeDAO) DAOFactory.getInstance().getDAO(DAOFactory.DAOType.SERVICETYPE);
    private final BookingDAO bookingDAO = (BookingDAO) DAOFactory.getInstance().getDAO(DAOFactory.DAOType.BOOKING);
    private final CustomerDAO customerDAO = (CustomerDAO) DAOFactory.getInstance().getDAO(DAOFactory.DAOType.CUSTOMER);
    @Override
    public Booking findById(String selectedBookingId) throws SQLException, ClassNotFoundException {
        return bookingDAO.findById(selectedBookingId);
    }

    @Override
    public String servicePriceFindByServiceId(String bookingId) throws SQLException, ClassNotFoundException {
        return bookingDAO.servicePriceFindByServiceId(bookingId);
    }

    @Override
    public String getNext() throws SQLException, ClassNotFoundException {
        return bookingDAO.getNext();
    }

    @Override
    public boolean save(BookingDTO DTO) throws SQLException, ClassNotFoundException {
        return bookingDAO.save(new Booking(DTO.getBookingId(),DTO.getDate(),DTO.getTime(),DTO.getCustomerId(),DTO.getServiceId()));
    }

    @Override
    public ArrayList<BookingDTO> getAll() throws SQLException, ClassNotFoundException {
        ArrayList<Booking> bookings = bookingDAO.getAll();
        ArrayList<BookingDTO> bookingDTOS = new ArrayList<>();
        for (Booking booking : bookings) {
            bookingDTOS.add(new BookingDTO(booking.getBookingId(),booking.getDate(),booking.getTime(),booking.getCustomerId(),booking.getServiceId()));
        }
        return bookingDTOS;
    }

    @Override
    public boolean update(BookingDTO DTO) throws SQLException, ClassNotFoundException {
        return bookingDAO.update(new Booking(DTO.getBookingId(),DTO.getDate(),DTO.getTime(),DTO.getCustomerId(),DTO.getServiceId()));
    }

    @Override
    public boolean delete(String Id) throws SQLException, ClassNotFoundException {
        return bookingDAO.delete(Id);
    }
}
