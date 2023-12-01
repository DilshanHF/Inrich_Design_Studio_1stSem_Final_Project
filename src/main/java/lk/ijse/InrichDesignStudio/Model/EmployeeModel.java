package lk.ijse.InrichDesignStudio.Model;


import lk.ijse.InrichDesignStudio.Db.DbConnection;
import lk.ijse.InrichDesignStudio.dto.CustomerDto;
import lk.ijse.InrichDesignStudio.dto.EmployeeDto;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;


public class EmployeeModel {


    public boolean exitEmployee(String id) throws SQLException {
        Connection connection = DbConnection.getInstance().getConnection();

        String sql = "SELECT e_id FROM employee WHERE e_id = ?";
        PreparedStatement pstm = connection.prepareStatement(sql);
        pstm.setString(1, id);

        ResultSet resultSet =pstm.executeQuery();
        return resultSet.next();
    }

    public boolean saveEmployee(EmployeeDto edto) throws SQLException {
        Connection connection = DbConnection.getInstance().getConnection();

        String sql = "INSERT INTO employee VALUES(?,?,?,?,?)";
        PreparedStatement pstm = connection.prepareStatement(sql);

        pstm.setString(1,edto.getId());
        pstm.setString(2,edto.getName());
        pstm.setString(3,edto.getAddress());
        pstm.setString(4,edto.getTel());
        pstm.setString(5,edto.getNic());

        return pstm.executeUpdate()>0;

    }

    public List<EmployeeDto> getAllEmployee() throws SQLException {
        Connection connection = DbConnection.getInstance().getConnection();

        String sql = "SELECT * FROM employee ";
        PreparedStatement pstm = connection.prepareStatement(sql);
        ResultSet resultSet = pstm.executeQuery();

        ArrayList<EmployeeDto> dtoList = new ArrayList<>();

        while(resultSet.next()) {
            dtoList.add(
                    new EmployeeDto(
                            resultSet.getString(1),
                            resultSet.getString(2),
                            resultSet.getString(3),
                            resultSet.getString(4),
                            resultSet.getString(5)
                    )
            );
        }
        return dtoList;
    }

    public boolean updateEmployee(CustomerDto dto) throws SQLException {
        Connection connection = DbConnection.getInstance().getConnection();

        String sql = "UPDATE employee SET e_name = ?, e_address = ?, e_tel = ?,e_nic = ? WHERE e_id = ?";
        PreparedStatement pstm = connection.prepareStatement(sql);
        pstm.setString(1, dto.getName());
        pstm.setString(2, dto.getAddress());
        pstm.setString(3, dto.getTel());
        pstm.setString(4, dto.getEmail());
        pstm.setString(5, dto.getId());

        return pstm.executeUpdate() > 0;

    }

    public boolean exitCustomer(String id) throws SQLException {
        Connection connection = DbConnection.getInstance().getConnection();

        String sql = "SELECT e_id FROM employee WHERE e_id = ?";
        PreparedStatement pstm = connection.prepareStatement(sql);
        pstm.setString(1, id);

        ResultSet resultSet =pstm.executeQuery();
        return resultSet.next();
    }

    public boolean deleteEmployee(String id) throws SQLException {
        Connection connection = DbConnection.getInstance().getConnection();

        String sql = "DELETE FROM employee WHERE e_id = ?";
        PreparedStatement pstm =connection.prepareStatement(sql);
        pstm.setString(1,id);
        return pstm.executeUpdate()>0;
    }

    public String getTotalEmployees() throws SQLException {
        Connection connection = DbConnection.getInstance().getConnection();

        String sql = "SELECT COUNT(e_id) FROM employee";
        PreparedStatement pstm = connection.prepareStatement(sql);

        ResultSet resultSet = pstm.executeQuery();
        if(resultSet.next()) {
            return resultSet.getString(1);

        }
        return null;

    }


    public static EmployeeDto searchEmployee(String id) throws SQLException {
        Connection connection = DbConnection.getInstance().getConnection ();

        String sql = "SELECT * FROM employee WHERE e_id = ?";
        PreparedStatement pstm = connection.prepareStatement(sql);
        pstm.setString(1, id);

        ResultSet resultSet = pstm.executeQuery();
        EmployeeDto dto = null;

        if(resultSet.next()) {
            String e_id = resultSet.getString(1);
            String e_name = resultSet.getString(2);
            String e_address = resultSet.getString(3);
            String e_tel = resultSet.getString(4);
            String e_nic = resultSet.getString(5);

            dto = new EmployeeDto(e_id, e_name, e_address, e_tel,e_nic);
        }
        return dto;
    }


}
