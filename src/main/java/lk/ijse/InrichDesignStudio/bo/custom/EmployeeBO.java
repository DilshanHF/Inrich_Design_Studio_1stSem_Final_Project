package lk.ijse.InrichDesignStudio.bo.custom;

import lk.ijse.InrichDesignStudio.bo.SuperBO;
import lk.ijse.InrichDesignStudio.dto.EmployeeDto;
import lk.ijse.InrichDesignStudio.entity.Employee;

import java.sql.SQLException;
import java.util.ArrayList;

public interface EmployeeBO extends SuperBO {
    boolean existEmployee(String id) throws SQLException, ClassNotFoundException;
    boolean saveEmployee(EmployeeDto dto) throws SQLException, ClassNotFoundException;
    boolean updateEmployee(EmployeeDto dto) throws SQLException, ClassNotFoundException;
    boolean deleteEmployee(String id) throws SQLException, ClassNotFoundException;

    ArrayList<EmployeeDto> getAllEmployee() throws SQLException, ClassNotFoundException;
    EmployeeDto searchEmployee(String id) throws SQLException, ClassNotFoundException;
}
