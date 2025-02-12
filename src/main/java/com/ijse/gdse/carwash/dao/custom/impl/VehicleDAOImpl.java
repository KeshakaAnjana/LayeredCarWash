package com.ijse.gdse.carwash.dao.custom.impl;

import com.ijse.gdse.carwash.dao.SQLUtil;
import com.ijse.gdse.carwash.dao.custom.VehicleDAO;
import com.ijse.gdse.carwash.entity.Vehicle;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class VehicleDAOImpl implements VehicleDAO {
    public String getNext() throws SQLException, ClassNotFoundException {
        ResultSet rst = SQLUtil.execute("select v_id from vehicle order by v_id desc limit 1");

        if (rst.next()) {
            String lastId = rst.getString(1);
            String substring = lastId.substring(1);
            int i = Integer.parseInt(substring);
            int newIdIndex = i + 1;
            return String.format("v%03d", newIdIndex);
        }
        return "v001";
    }
    public boolean save(Vehicle vehicleDTO) throws SQLException, ClassNotFoundException {
        return SQLUtil.execute(
                "insert into vehicle values (?,?,?,?)",
                vehicleDTO.getVehicleId(),
                vehicleDTO.getLicencePlate(),
                vehicleDTO.getModel(),
                vehicleDTO.getCustomerId()
        );
    }

    public ArrayList<Vehicle> getAll() throws SQLException, ClassNotFoundException {
        ResultSet rst = SQLUtil.execute("select * from vehicle");

        ArrayList<Vehicle> vehicleDTOS = new ArrayList<>();
        while (rst.next()) {
            Vehicle vehicleDTO = new Vehicle(
                    rst.getString(1),
                    rst.getString(2),
                    rst.getString(3),
                    rst.getString(4)
            );
            vehicleDTOS.add(vehicleDTO);
        }
        return vehicleDTOS;
    }
    public boolean update(Vehicle vehicleDTO) throws SQLException, ClassNotFoundException {
        return SQLUtil.execute(
                "update vehicle set licence_plate = ?, model = ?, c_id = ? where v_id = ?",
                vehicleDTO.getLicencePlate(),
                vehicleDTO.getModel(),
                vehicleDTO.getCustomerId(),
                vehicleDTO.getVehicleId()
        );
    }

    public boolean delete(String VehicleId) throws SQLException, ClassNotFoundException {
        return SQLUtil.execute("delete from vehicle where v_id=?", VehicleId);
    }

    public ArrayList<String> getAllVehicleIds() throws SQLException, ClassNotFoundException {
        ResultSet rst = SQLUtil.execute("select v_id from vehicle");

        ArrayList<String> vehicleIds = new ArrayList<>();

        while (rst.next()) {
            vehicleIds.add(rst.getString(1));
        }

        return vehicleIds;
    }

    public Vehicle findById(String selectedVehicleId) throws SQLException, ClassNotFoundException {
        ResultSet rst = SQLUtil.execute("select * from vehicle where v_id=?", selectedVehicleId);

        if (rst.next()) {
            return new Vehicle(
                    rst.getString(1),
                    rst.getString(2),
                    rst.getString(3),
                    rst.getString(4)
            );
        }
        return null;
    }
}


