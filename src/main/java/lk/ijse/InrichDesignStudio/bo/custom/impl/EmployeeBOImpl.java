package lk.ijse.InrichDesignStudio.bo.custom.impl;

import com.beust.ah.A;
import lk.ijse.InrichDesignStudio.bo.custom.EmployeeBO;
import lk.ijse.InrichDesignStudio.dao.custom.EmployeeDAO;
import lk.ijse.InrichDesignStudio.dao.custom.impl.EmployeeDAOImpl;
import lk.ijse.InrichDesignStudio.dto.EmployeeDto;
import lk.ijse.InrichDesignStudio.entity.Employee;

import java.sql.SQLException;
import java.util.ArrayList;

public class EmployeeBOImpl implements EmployeeBO {

    EmployeeDAO employeeDAO = new EmployeeDAOImpl();
    @Override
    public boolean existEmployee(String id) throws SQLException, ClassNotFoundException {
        return employeeDAO.exist(id);
    }

    @Override
    public boolean saveEmployee(EmployeeDto dto) throws SQLException, ClassNotFoundException {
        return employeeDAO.save(new Employee(dto.getId(),dto.getName(),dto.getAddress(),dto.getTel(),dto.getNic()));
    }

    @Override
    public boolean updateEmployee(EmployeeDto dto) throws SQLException, ClassNotFoundException {
        return employeeDAO.update(new Employee(dto.getId(), dto.getName(), dto.getAddress(), dto.getTel(), dto.getNic()));
    }

    @Override
    public boolean deleteEmployee(String id) throws SQLException, ClassNotFoundException {
        return  employeeDAO.delete(id);
    }

    @Override
    public ArrayList<EmployeeDto> getAllEmployee() throws SQLException, ClassNotFoundException {
        ArrayList<EmployeeDto> employeeDtos = new ArrayList<>();
        ArrayList<Employee> employees  = employeeDAO.getAll();

        for(Employee employee : employees){
            employeeDtos.add(new EmployeeDto(employee.getId(),employee.getName(),employee.getAddress(),employee.getTel(),employee.getNic()));

        }
        return employeeDtos;
    }

    @Override
    public EmployeeDto searchEmployee(String id) throws SQLException, ClassNotFoundException {
            Employee employee = employeeDAO.search(id);
            return new EmployeeDto(employee.getId(),employee.getName(),employee.getAddress(),employee.getTel(),employee.getNic());
    }


}
