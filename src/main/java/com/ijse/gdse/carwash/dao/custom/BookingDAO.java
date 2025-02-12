package com.ijse.gdse.carwash.dao.custom;

import com.ijse.gdse.carwash.dao.CrudDAO;
import com.ijse.gdse.carwash.dao.custom.impl.ServiceTypeDAOImpl;
import com.ijse.gdse.carwash.entity.Booking;

import java.sql.SQLException;
import java.util.ArrayList;

public interface BookingDAO extends CrudDAO<Booking> {

    final ServiceTypeDAOImpl serviceTypeModel = new ServiceTypeDAOImpl();

//    String getNextBookingId() throws SQLException;
//
//    boolean saveBooking(BookingDTO bookingDTO) throws SQLException;
//
//    ArrayList<BookingDTO> getAllBooking() throws SQLException;
//
//    boolean updateBooking(BookingDTO bookingDTO) throws SQLException;
//
//    boolean deleteBooking(String bookingId) throws SQLException;

    //ArrayList<String> getAllBookingIds() throws SQLException;

    Booking findById(String selectedBookingId) throws SQLException, ClassNotFoundException;

    String servicePriceFindByServiceId(String bookingId) throws SQLException, ClassNotFoundException;

}
