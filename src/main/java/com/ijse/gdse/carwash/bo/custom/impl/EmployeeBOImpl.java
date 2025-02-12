package com.ijse.gdse.carwash.bo.custom.impl;

import com.ijse.gdse.carwash.bo.custom.EmployeeBO;
import com.ijse.gdse.carwash.dao.DAOFactory;
import com.ijse.gdse.carwash.dao.custom.EmployeeDAO;
import com.ijse.gdse.carwash.dao.custom.ServiceTypeDAO;
import com.ijse.gdse.carwash.dto.EmployeeDTO;
import com.ijse.gdse.carwash.entity.Employee;

import java.sql.SQLException;
import java.util.ArrayList;

public class EmployeeBOImpl implements EmployeeBO {

    private final ServiceTypeDAO serviceTypeDAO = (ServiceTypeDAO) DAOFactory.getInstance().getDAO(DAOFactory.DAOType.SERVICETYPE);
    private final EmployeeDAO employeeDAO = (EmployeeDAO) DAOFactory.getInstance().getDAO(DAOFactory.DAOType.EMPLOYEE);

    @Override
    public ArrayList<String> getAllEmployeeIds() throws SQLException, ClassNotFoundException {
        return employeeDAO.getAllEmployeeIds();
    }

    @Override
    public Employee findById(String selectedEmployeeId) throws SQLException, ClassNotFoundException {
        return employeeDAO.findById(selectedEmployeeId);
    }

    @Override
    public String getNext() throws SQLException, ClassNotFoundException {
        return employeeDAO.getNext();
    }

    @Override
    public boolean save(EmployeeDTO DTO) throws SQLException, ClassNotFoundException {
        return employeeDAO.save(new Employee(DTO.getEmployeeId(),DTO.getEmployeeName(),DTO.getSalary(),DTO.getJob()));
    }

    @Override
    public ArrayList<EmployeeDTO> getAll() throws SQLException, ClassNotFoundException {
        ArrayList<Employee> employees = employeeDAO.getAll();
        ArrayList<EmployeeDTO> employeeDTOS = new ArrayList<>();
        for (Employee employee : employees) {
            EmployeeDTO employeeDTO = new EmployeeDTO(employee.getEmployeeId(),employee.getEmployeeName(),employee.getSalary(),employee.getJob());
        }
        return employeeDTOS;
    }

    @Override
    public boolean update(EmployeeDTO DTO) throws SQLException, ClassNotFoundException {
        return employeeDAO.update(new Employee(DTO.getEmployeeId(),DTO.getEmployeeName(),DTO.getSalary(),DTO.getJob()));
    }

    @Override
    public boolean delete(String Id) throws SQLException, ClassNotFoundException {
        return employeeDAO.delete(Id);
    }
}
