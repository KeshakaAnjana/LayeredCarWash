package com.ijse.gdse.carwash.dao;

import com.ijse.gdse.carwash.dao.custom.CustomerDAO;
import com.ijse.gdse.carwash.dao.custom.impl.*;
import com.ijse.gdse.carwash.dto.CustomerDTO;

import static com.ijse.gdse.carwash.dao.DAOFactory.DAOType.CUSTOMER;

public class DAOFactory {
    private static DAOFactory daoFactory;
    private DAOFactory(){}

    public static DAOFactory getInstance(){
        return daoFactory==null?daoFactory = new DAOFactory():daoFactory;
    }

    public enum DAOType{
        CUSTOMER, BOOKING, EMPLOYEE, PAYMENT1, PAYMENT2, PRODUCT, SERVICETYPE, SUPPLIER, VEHICLE, BATCH
    }

    public CrudDAO getDAO(DAOType daotype) {
        switch (daotype) {
            case CUSTOMER:
                return new CustomerDAOImpl();
            case BATCH:
                return new BatchDAOImpl();
            case BOOKING:
                return new BookingDAOImpl();
            case EMPLOYEE:
                return new EmployeeDAOImpl();
            case PAYMENT1:
                return new Payment1DAOImpl();
            case PAYMENT2:
                return new Payment2DAOImpl();
            case PRODUCT:
                return new ProductDAOImpl();
            case SERVICETYPE:
                return new ServiceTypeDAOImpl();
            case SUPPLIER:
                return new SupplierDAOImpl();
            case VEHICLE:
                return new VehicleDAOImpl();
                default:
                    return null;
        }
    }
}
