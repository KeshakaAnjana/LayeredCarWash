package com.ijse.gdse.carwash.dao.custom.impl;

import com.ijse.gdse.carwash.dao.SQLUtil;
import com.ijse.gdse.carwash.dao.custom.EmployeeDAO;
import com.ijse.gdse.carwash.entity.Employee;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class EmployeeDAOImpl implements EmployeeDAO {
    public String getNext() throws SQLException, ClassNotFoundException {
        ResultSet rst = SQLUtil.execute("SELECT employee_id FROM employee ORDER BY employee_id DESC LIMIT 1");

        if (rst != null && !rst.next()) {
            return "e001";
        }

        String lastId = rst.getString(1);

        if (lastId != null && lastId.length() > 1) {
            String numericPart = lastId.substring(1);

            try {
                int numericValue = Integer.parseInt(numericPart);
                int newIdIndex = numericValue + 1;

                return String.format("e%03d", newIdIndex);
            } catch (NumberFormatException e) {
                return "e001";
            }
        } else {
            return "e001";
        }
    }

    public boolean save(Employee employeeDTO) throws SQLException, ClassNotFoundException {
        return SQLUtil.execute(
                "INSERT INTO employee (employee_id,name,salary,job) VALUES (?,?,?,?)",
                employeeDTO.getEmployeeId(),
                employeeDTO.getEmployeeName(),
                employeeDTO.getSalary(),
                employeeDTO.getJob()
        );
    }

    public ArrayList<Employee> getAll() throws SQLException, ClassNotFoundException {
        ResultSet rst = SQLUtil.execute("SELECT * FROM employee");

        ArrayList<Employee> employeeDTOS = new ArrayList<>();
        while (rst.next()) {
            Employee employeeDTO = new Employee(
                    rst.getString(1),
                    rst.getString(2),
                    rst.getString(3),
                    rst.getString(4)
            );
            employeeDTOS.add(employeeDTO);
        }
        return employeeDTOS;
    }

    public boolean update(Employee employeeDTO) throws SQLException, ClassNotFoundException {
        return SQLUtil.execute(
                "UPDATE employee SET name=?, salary=?, job=? WHERE employee_id=?",
                employeeDTO.getEmployeeName(),
                employeeDTO.getSalary(),
                employeeDTO.getJob(),
                employeeDTO.getEmployeeId()
        );
    }

    public boolean delete(String employeeId) throws SQLException, ClassNotFoundException {
        return SQLUtil.execute("DELETE FROM employee WHERE employee_id=?", employeeId);
    }

    public ArrayList<String> getAllEmployeeIds() throws SQLException, ClassNotFoundException {
        ResultSet rst = SQLUtil.execute("SELECT employee_id FROM employee");

        ArrayList<String> employeeIds = new ArrayList<>();

        while (rst.next()) {
            employeeIds.add(rst.getString(1));
        }

        return employeeIds;
    }

    public Employee findById(String selectedEmployeeId) throws SQLException, ClassNotFoundException {
        ResultSet rst = SQLUtil.execute("SELECT * FROM employee WHERE employee_id=?", selectedEmployeeId);

        if (rst.next()) {
            return new Employee(
                    rst.getString(1),
                    rst.getString(2),
                    rst.getString(3),
                    rst.getString(4)
            );
        }
        return null;
    }
}
