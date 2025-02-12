package com.ijse.gdse.carwash.bo.custom;

import com.ijse.gdse.carwash.bo.SuperBO;
import com.ijse.gdse.carwash.dto.VehicleDTO;
import com.ijse.gdse.carwash.entity.Vehicle;

import java.sql.SQLException;
import java.util.ArrayList;

public interface VehicleBO extends SuperBO {

    Vehicle findById(String selectedVehicleId) throws SQLException, ClassNotFoundException;

    String getNext() throws SQLException, ClassNotFoundException;

    boolean save(VehicleDTO DTO) throws SQLException, ClassNotFoundException;

    ArrayList<VehicleDTO> getAll() throws SQLException, ClassNotFoundException;

    boolean update(VehicleDTO DTO) throws SQLException, ClassNotFoundException;

    boolean delete(String Id) throws SQLException, ClassNotFoundException;
}
