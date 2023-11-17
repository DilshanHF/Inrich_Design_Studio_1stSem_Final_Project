package lk.ijse.InrichDesignStudio.Model;


import lk.ijse.InrichDesignStudio.Db.DbConnection;
import lk.ijse.InrichDesignStudio.dto.employeeDto;
import lombok.*;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;


public class employeeModel {


    public boolean exitEmployee(String id) throws SQLException {
        Connection connection = DbConnection.getInstance().getConnection();

        String sql = "SELECT e_id FROM employee WHERE e_id = ?";
        PreparedStatement pstm = connection.prepareStatement(sql);
        pstm.setString(1, id);

        ResultSet resultSet =pstm.executeQuery();
        return resultSet.next();
    }

    public boolean saveEmployee(employeeDto edto) throws SQLException {
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
}
