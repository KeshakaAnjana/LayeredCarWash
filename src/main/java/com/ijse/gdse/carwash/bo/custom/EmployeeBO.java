package com.ijse.gdse.carwash.bo.custom;

import com.ijse.gdse.carwash.bo.SuperBO;
import com.ijse.gdse.carwash.dto.EmployeeDTO;
import com.ijse.gdse.carwash.entity.Employee;

import java.sql.SQLException;
import java.util.ArrayList;

public interface EmployeeBO extends SuperBO {

    ArrayList<String> getAllEmployeeIds() throws SQLException, ClassNotFoundException;

    Employee findById(String selectedEmployeeId) throws SQLException, ClassNotFoundException;

    String getNext() throws SQLException, ClassNotFoundException;

    boolean save(EmployeeDTO DTO) throws SQLException, ClassNotFoundException;

    ArrayList<EmployeeDTO> getAll() throws SQLException, ClassNotFoundException;

    boolean update(EmployeeDTO DTO) throws SQLException, ClassNotFoundException;

    boolean delete(String Id) throws SQLException, ClassNotFoundException;
}
