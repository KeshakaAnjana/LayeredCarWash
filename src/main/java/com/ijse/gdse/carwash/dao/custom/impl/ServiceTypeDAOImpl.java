package com.ijse.gdse.carwash.dao.custom.impl;

import com.ijse.gdse.carwash.dao.SQLUtil;
import com.ijse.gdse.carwash.dao.custom.ServiceTypeDAO;
import com.ijse.gdse.carwash.entity.ServiceType;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class ServiceTypeDAOImpl implements ServiceTypeDAO {

        public String getNext() throws SQLException, ClassNotFoundException {

            ResultSet rst = SQLUtil.execute("SELECT service_id FROM service ORDER BY service_id DESC LIMIT 1");

            if (rst != null && !rst.next()) {
                return "s001";
            }

            String lastId = rst.getString(1);

            if (lastId != null && lastId.length() > 1) {
                String numericPart = lastId.substring(1);

                try {
                    int numericValue = Integer.parseInt(numericPart);
                    int newIdIndex = numericValue + 1;

                    return String.format("s%03d", newIdIndex);
                } catch (NumberFormatException e) {
                    return "s001";
                }
            } else {
                return "s001";
            }
        }

        public boolean save(ServiceType serviceTypeDTO) throws SQLException, ClassNotFoundException {
            return SQLUtil.execute(
                    "INSERT INTO service VALUES (?,?,?)",
                    serviceTypeDTO.getServiceId(),
                    serviceTypeDTO.getServiceTypeName(),
                    serviceTypeDTO.getPrice()
            );
        }

        public ArrayList<ServiceType> getAll() throws SQLException, ClassNotFoundException {
            ResultSet rst = SQLUtil.execute("SELECT * FROM service");

            ArrayList<ServiceType> serviceTypeDTOS = new ArrayList<>();
            while (rst.next()) {
                ServiceType serviceTypeDTO = new ServiceType(
                        rst.getString(1),
                        rst.getString(2),
                        rst.getString(3)
                );
                serviceTypeDTOS.add(serviceTypeDTO);
            }
            return serviceTypeDTOS;
        }

        public boolean update(ServiceType serviceTypeDTO) throws SQLException, ClassNotFoundException {
            return SQLUtil.execute(
                    "UPDATE service SET service_type_name=?, price=? WHERE service_id=?",
                    serviceTypeDTO.getServiceTypeName(),
                    serviceTypeDTO.getPrice(),
                    serviceTypeDTO.getServiceId()
            );
        }

        public boolean delete(String ServiceId) throws SQLException, ClassNotFoundException {
            return SQLUtil.execute("DELETE FROM service WHERE service_id=?", ServiceId);
        }

        public static ArrayList<String> getAllserviceNames() throws SQLException, ClassNotFoundException {
            ResultSet rst = SQLUtil.execute("SELECT service_type_name FROM service");

            ArrayList<String> serviceNames = new ArrayList<>();

            while (rst.next()) {
                serviceNames.add(rst.getString(1));
            }

            return serviceNames;
        }

        public ServiceType findById(String selectedServiceId) throws SQLException, ClassNotFoundException {
            ResultSet rst = SQLUtil.execute("SELECT * FROM service WHERE service_type_name=?", selectedServiceId);

            if (rst.next()) {
                return new ServiceType(
                        rst.getString(1),
                        rst.getString(2),
                        rst.getString(3)
                );
            }
            return null;
        }

    public String ServiceTypeFindById(String selectedServiceId) throws SQLException, ClassNotFoundException {
        System.out.println("Selected Service ID: " + selectedServiceId);

        ResultSet rst = SQLUtil.execute("SELECT service_type_name FROM service WHERE service_id=?", selectedServiceId);

        if (rst.next()) {
            String serviceTypeName = rst.getString("service_type_name");
            return serviceTypeName;
        } else {
            return null;
        }
    }

    public String priceFindFbServiceId(String serviceId) throws SQLException, ClassNotFoundException {
        // Execute query to fetch the price from the service table using service_id
        ResultSet resultSet = SQLUtil.execute("SELECT price FROM service WHERE service_id=?", serviceId);

        // Check if the result set contains any data
        if (resultSet.next()) {
            return resultSet.getString(1);  // Return the price
        } else {
            // If no record is found, return null
            System.out.println("No price found for service_id: " + serviceId);
            return null;
        }
    }


}

