package lk.ijse.InrichDesignStudio.dao.custom;

import lk.ijse.InrichDesignStudio.dao.CrudDAO;
import lk.ijse.InrichDesignStudio.entity.Employee;

import java.sql.SQLException;

public interface EmployeeDAO extends CrudDAO<Employee> {
    String getTotalEmployees() throws SQLException,ClassNotFoundException;
}
