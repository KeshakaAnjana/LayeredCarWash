package com.ijse.gdse.carwash.bo.custom;

import com.ijse.gdse.carwash.bo.SuperBO;
import com.ijse.gdse.carwash.dto.ServiceTypeDTO;
import com.ijse.gdse.carwash.entity.ServiceType;

import java.sql.SQLException;
import java.util.ArrayList;

public interface ServiceTypeBO extends SuperBO {

    ServiceType findById(String selectedServiceId) throws SQLException, ClassNotFoundException;

    String ServiceTypeFindById(String selectedServiceId) throws SQLException, ClassNotFoundException;

    String priceFindFbServiceId(String serviceId) throws SQLException, ClassNotFoundException;

    String getNext() throws SQLException, ClassNotFoundException;

    boolean save(ServiceTypeDTO DTO) throws SQLException, ClassNotFoundException;

    ArrayList<ServiceTypeDTO> getAll() throws SQLException, ClassNotFoundException;

    boolean update(ServiceTypeDTO DTO) throws SQLException, ClassNotFoundException;

    boolean delete(String Id) throws SQLException, ClassNotFoundException;
}
