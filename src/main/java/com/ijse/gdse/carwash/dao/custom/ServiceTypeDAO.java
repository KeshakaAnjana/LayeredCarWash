package com.ijse.gdse.carwash.dao.custom;

import com.ijse.gdse.carwash.dao.CrudDAO;
import com.ijse.gdse.carwash.entity.ServiceType;

import java.sql.SQLException;

public interface ServiceTypeDAO extends CrudDAO<ServiceType> {

//    String getNextServiceId() throws SQLException;
//
//    boolean saveService(ServiceTypeDTO serviceTypeDTO) throws SQLException;
//
//    ArrayList<ServiceTypeDTO> getAllService() throws SQLException;
//
//    boolean updateService(ServiceTypeDTO serviceTypeDTO) throws SQLException;
//
//    boolean deleteService(String ServiceId) throws SQLException;

    //ArrayList<String> getAllserviceNames() throws SQLException;

    ServiceType findById(String selectedServiceId) throws SQLException, ClassNotFoundException;

    String ServiceTypeFindById(String selectedServiceId) throws SQLException, ClassNotFoundException;

    String priceFindFbServiceId(String serviceId) throws SQLException, ClassNotFoundException;
}
