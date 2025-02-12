package com.ijse.gdse.carwash.bo.custom;

import com.ijse.gdse.carwash.bo.SuperBO;
import com.ijse.gdse.carwash.dto.BookingDTO;
import com.ijse.gdse.carwash.entity.Booking;

import java.sql.SQLException;
import java.util.ArrayList;

public interface BookingBO extends SuperBO {

    Booking findById(String selectedBookingId) throws SQLException, ClassNotFoundException;

    String servicePriceFindByServiceId(String bookingId) throws SQLException, ClassNotFoundException;

    String getNext() throws SQLException, ClassNotFoundException;

    boolean save(BookingDTO DTO) throws SQLException, ClassNotFoundException;

    ArrayList<BookingDTO> getAll() throws SQLException, ClassNotFoundException;

    boolean update(BookingDTO DTO) throws SQLException, ClassNotFoundException;

    boolean delete(String Id) throws SQLException, ClassNotFoundException;
}
