package lk.ijse.InrichDesignStudio.dao.custom.impl;

import lk.ijse.InrichDesignStudio.dao.SQLUtil;
import lk.ijse.InrichDesignStudio.dao.custom.EmployeeDAO;
import lk.ijse.InrichDesignStudio.entity.Employee;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class EmployeeDAOImpl implements EmployeeDAO {
    @Override
    public ArrayList<Employee> getAll() throws SQLException, ClassNotFoundException {
        ResultSet rst = SQLUtil.execute("SELECT * FROM employee");
        ArrayList<Employee>getAllEmployee = new ArrayList<>();
        while (rst.next()){
            Employee entity = new Employee(rst.getString(1),rst.getString(2),rst.getString(3),rst.getString(4),rst.getString(5));
            getAllEmployee.add(entity);
        }
        return  getAllEmployee;
    }

    @Override
    public boolean save(Employee dto) throws SQLException, ClassNotFoundException {
        return SQLUtil.execute("INSERT INTO employee VALUES(?,?,?,?,?)",
                dto.getId(),dto.getName(),dto.getAddress(),dto.getTel(),dto.getNic()
                );
    }

    @Override
    public boolean update(Employee dto) throws SQLException, ClassNotFoundException {
        return SQLUtil.execute("UPDATE employee SET e_name = ?, e_address = ?, e_tel = ?,e_nic = ? WHERE e_id = ?",
                dto.getName(),dto.getAddress(),dto.getTel(),dto.getNic(),dto.getId());
    }

    @Override
    public boolean delete(String id) throws SQLException, ClassNotFoundException {
        return SQLUtil.execute( "DELETE FROM employee WHERE e_id = ?",id);
    }

    @Override
    public String generateNewId() throws SQLException, ClassNotFoundException {
        return null;
    }

    @Override
    public boolean exist(String id) throws SQLException, ClassNotFoundException {
        ResultSet rst = SQLUtil.execute("SELECT e_id FROM employee WHERE e_id = ?",id);
        return rst.next();
    }

    @Override
    public Employee search(String id) throws SQLException, ClassNotFoundException {
        ResultSet rst = SQLUtil.execute("SELECT * FROM employee WHERE e_id = ?",id);
        rst.next();
        return new Employee(rst.getString(1),rst.getString(2),rst.getString(3),rst.getString(4),rst.getString(5));

    }
}
