package com.ijse.gdse.carwash.dao.custom;

import com.ijse.gdse.carwash.dao.CrudDAO;
import com.ijse.gdse.carwash.entity.Vehicle;

import java.sql.SQLException;
import java.util.ArrayList;

public interface VehicleDAO extends CrudDAO<Vehicle> {

//    String getNextVehicleId() throws SQLException;
//
//    boolean saveVehicle(VehicleDTO vehicleDTO) throws SQLException;
//
//    ArrayList<VehicleDTO> getAllVehicles() throws SQLException;
//
//    boolean updateVehicle(VehicleDTO vehicleDTO) throws SQLException;
//
//    boolean deleteVehicle(String VehicleId) throws SQLException;

    ArrayList<String> getAllVehicleIds() throws SQLException, ClassNotFoundException;

    Vehicle findById(String selectedVehicleId) throws SQLException, ClassNotFoundException;

}
