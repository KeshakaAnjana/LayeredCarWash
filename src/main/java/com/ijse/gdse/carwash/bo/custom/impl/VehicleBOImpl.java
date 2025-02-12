package com.ijse.gdse.carwash.bo.custom.impl;

import com.ijse.gdse.carwash.bo.custom.VehicleBO;
import com.ijse.gdse.carwash.dao.DAOFactory;
import com.ijse.gdse.carwash.dao.custom.CustomerDAO;
import com.ijse.gdse.carwash.dao.custom.VehicleDAO;
import com.ijse.gdse.carwash.dto.VehicleDTO;
import com.ijse.gdse.carwash.entity.Vehicle;

import java.sql.SQLException;
import java.util.ArrayList;

public class VehicleBOImpl implements VehicleBO {

    VehicleDAO vehicleDAO = (VehicleDAO) DAOFactory.getInstance().getDAO(DAOFactory.DAOType.VEHICLE);
    CustomerDAO customerDAO = (CustomerDAO) DAOFactory.getInstance().getDAO(DAOFactory.DAOType.CUSTOMER);

    @Override
    public Vehicle findById(String selectedVehicleId) throws SQLException, ClassNotFoundException {
        return vehicleDAO.findById(selectedVehicleId);
    }

    @Override
    public String getNext() throws SQLException, ClassNotFoundException {
        return vehicleDAO.getNext();
    }

    @Override
    public boolean save(VehicleDTO DTO) throws SQLException, ClassNotFoundException {
        return vehicleDAO.save(new Vehicle(DTO.getVehicleId(),DTO.getLicencePlate(),DTO.getModel(),DTO.getCustomerId()));
    }

    @Override
    public ArrayList<VehicleDTO> getAll() throws SQLException, ClassNotFoundException {
        ArrayList<Vehicle> vehicles = vehicleDAO.getAll();
        ArrayList<VehicleDTO> vehicleDTOS = new ArrayList<>();
        for (Vehicle vehicle : vehicles) {
            vehicleDTOS.add(new VehicleDTO(vehicle.getVehicleId(),vehicle.getLicencePlate(),vehicle.getModel(),vehicle.getCustomerId()));
        }
        return vehicleDTOS;
    }

    @Override
    public boolean update(VehicleDTO DTO) throws SQLException, ClassNotFoundException {
        return vehicleDAO.update(new Vehicle(DTO.getVehicleId(),DTO.getLicencePlate(),DTO.getModel(),DTO.getCustomerId()));
    }

    @Override
    public boolean delete(String Id) throws SQLException, ClassNotFoundException {
        return vehicleDAO.delete(Id);
    }
}
