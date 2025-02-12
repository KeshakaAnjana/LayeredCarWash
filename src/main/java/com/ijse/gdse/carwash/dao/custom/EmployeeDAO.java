package com.ijse.gdse.carwash.dao.custom;

import com.ijse.gdse.carwash.dao.CrudDAO;
import com.ijse.gdse.carwash.entity.Employee;

import java.sql.SQLException;
import java.util.ArrayList;

public interface EmployeeDAO extends CrudDAO<Employee> {
//    String getNextEmployeeId() throws SQLException;
//
//    boolean saveEmployee(EmployeeDTO employeeDTO) throws SQLException;
//
//    ArrayList<EmployeeDTO> getAllEmployee() throws SQLException;
//
//    boolean updateEmployee(EmployeeDTO employeeDTO) throws SQLException;
//
//    boolean deleteEmployee(String employeeId) throws SQLException;

    ArrayList<String> getAllEmployeeIds() throws SQLException, ClassNotFoundException;

    Employee findById(String selectedEmployeeId) throws SQLException, ClassNotFoundException;
}
