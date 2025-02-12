package com.ijse.gdse.carwash.dao.custom.impl;

import com.ijse.gdse.carwash.dao.SQLUtil;
import com.ijse.gdse.carwash.dao.custom.BookingDAO;
import com.ijse.gdse.carwash.entity.Booking;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class BookingDAOImpl implements BookingDAO {

    public String getNext() throws SQLException, ClassNotFoundException {
        ResultSet rst = SQLUtil.execute("SELECT booking_id FROM booking ORDER BY booking_id DESC LIMIT 1");

        if (rst != null && !rst.next()) {
            return "b001";
        }

        String lastId = rst.getString(1);

        if (lastId != null && lastId.length() > 1) {
            String numericPart = lastId.substring(1);

            try {
                int numericValue = Integer.parseInt(numericPart);
                int newIdIndex = numericValue + 1;

                return String.format("b%03d", newIdIndex);
            } catch (NumberFormatException e) {
                return "b001";
            }
        } else {
            return "b001";
        }
    }

    public boolean save(Booking bookingDTO) throws SQLException, ClassNotFoundException {
        return SQLUtil.execute(
                "INSERT INTO booking (booking_id, date, time, c_id,service_id) VALUES (?,?,?,?,?)",
                bookingDTO.getBookingId(),
                bookingDTO.getDate(),
                bookingDTO.getTime(),
                bookingDTO.getCustomerId(),
                bookingDTO.getServiceId()
        );
    }

    public ArrayList<Booking> getAll() throws SQLException, ClassNotFoundException {
        ResultSet rst = SQLUtil.execute("SELECT * FROM booking");

        ArrayList<Booking> bookingDTOS = new ArrayList<>();
        while (rst.next()) {
            Booking bookingDTO = new Booking(
                    rst.getString(1),
                    rst.getString(2),
                    rst.getString(3),
                    rst.getString(4),
                    rst.getString(5)
            );
            bookingDTOS.add(bookingDTO);
        }
        return bookingDTOS;
    }

    public boolean update(Booking bookingDTO) throws SQLException, ClassNotFoundException {
        return SQLUtil.execute(
                "UPDATE booking SET date=?, time=?, c_id=?,service_id=? WHERE booking_id=?",
                bookingDTO.getDate(),
                bookingDTO.getTime(),
                bookingDTO.getCustomerId(),
                bookingDTO.getServiceId(),
                bookingDTO.getBookingId()

        );
    }

    public boolean delete(String bookingId) throws SQLException, ClassNotFoundException {
        return SQLUtil.execute("DELETE FROM booking WHERE booking_id=?", bookingId);
    }

    public static ArrayList<String> getAllBookingIds() throws SQLException, ClassNotFoundException {
        ResultSet rst = SQLUtil.execute("SELECT booking_id FROM booking");

        ArrayList<String> bookingIds = new ArrayList<>();

        while (rst.next()) {
            bookingIds.add(rst.getString(1));
        }

        return bookingIds;
    }

    public Booking findById(String selectedBookingId) throws SQLException, ClassNotFoundException {
        ResultSet rst = SQLUtil.execute("SELECT * FROM booking WHERE booking_id=?", selectedBookingId);

        if (rst.next()) {
            return new Booking(
                    rst.getString(1),
                    rst.getString(2),
                    rst.getString(3),
                    rst.getString(4),
                    rst.getString(5)
            );
        }
        return null;
    }

    public String servicePriceFindByServiceId(String bookingId) throws SQLException, ClassNotFoundException {
        ResultSet rst = SQLUtil.execute("SELECT service_id FROM booking WHERE booking_id=?;", bookingId);

        if (rst.next()) {
            String serviceId = rst.getString(1);
            System.out.println("Fetched Service ID: " + serviceId);
            return serviceId;
        } else {
            System.out.println("No service_id found for booking_id: " + bookingId);
            return null;
        }
    }

}
