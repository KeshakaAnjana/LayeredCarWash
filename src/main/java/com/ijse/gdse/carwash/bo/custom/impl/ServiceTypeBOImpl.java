package com.ijse.gdse.carwash.bo.custom.impl;

import com.ijse.gdse.carwash.bo.custom.ServiceTypeBO;
import com.ijse.gdse.carwash.dao.DAOFactory;
import com.ijse.gdse.carwash.dao.custom.ServiceTypeDAO;
import com.ijse.gdse.carwash.dto.ServiceTypeDTO;
import com.ijse.gdse.carwash.entity.ServiceType;

import java.sql.SQLException;
import java.util.ArrayList;

public class ServiceTypeBOImpl implements ServiceTypeBO {

    private final ServiceTypeDAO serviceTypeDAO = (ServiceTypeDAO) DAOFactory.getInstance().getDAO(DAOFactory.DAOType.SERVICETYPE);

    @Override
    public ServiceType findById(String selectedServiceId) throws SQLException, ClassNotFoundException {
        return serviceTypeDAO.findById(selectedServiceId);
    }

    @Override
    public String ServiceTypeFindById(String selectedServiceId) throws SQLException, ClassNotFoundException {
        return serviceTypeDAO.ServiceTypeFindById(selectedServiceId);
    }

    @Override
    public String priceFindFbServiceId(String serviceId) throws SQLException, ClassNotFoundException {
        return serviceTypeDAO.priceFindFbServiceId(serviceId);
    }

    @Override
    public String getNext() throws SQLException, ClassNotFoundException {
        return serviceTypeDAO.getNext();
    }

    @Override
    public boolean save(ServiceTypeDTO DTO) throws SQLException, ClassNotFoundException {
        return serviceTypeDAO.save(new ServiceType(DTO.getServiceId(),DTO.getServiceTypeName(),DTO.getPrice()));
    }

    @Override
    public ArrayList<ServiceTypeDTO> getAll() throws SQLException, ClassNotFoundException {
        ArrayList<ServiceType> serviceTypes = serviceTypeDAO.getAll();
        ArrayList<ServiceTypeDTO> serviceTypeDTOS = new ArrayList<>();
        for (ServiceType serviceType : serviceTypes) {
            serviceTypeDTOS.add(new ServiceTypeDTO(serviceType.getServiceId(),serviceType.getServiceTypeName(),serviceType.getPrice()));
        }
        return serviceTypeDTOS;
    }

    @Override
    public boolean update(ServiceTypeDTO DTO) throws SQLException, ClassNotFoundException {
        return serviceTypeDAO.update(new ServiceType(DTO.getServiceId(),DTO.getServiceTypeName(),DTO.getPrice()));
    }

    @Override
    public boolean delete(String Id) throws SQLException, ClassNotFoundException {
        return serviceTypeDAO.delete(Id);
    }
}
