package lk.ijse.InrichDesignStudio.Model;

import lk.ijse.InrichDesignStudio.Db.DbConnection;
import lk.ijse.InrichDesignStudio.dto.UserDto;

import java.sql.*;

public class UserModel {

    public boolean saveUser(UserDto dto) throws SQLException {
        Connection connection = DbConnection.getInstance().getConnection();

        String sql = "INSERT INTO user VALUES(?,?,?,?)";
        PreparedStatement pstm = connection.prepareStatement(sql);

        pstm.setString(1,dto.getEmail());
        pstm.setString(2,dto.getFirst_name());
        pstm.setString(3,dto.getLast_name());
        pstm.setString(4,dto.getPassword());

        return pstm.executeUpdate()>0;
    }

    public boolean checkCredentials(String username,String password) throws SQLException {
        Connection connection = DbConnection.getInstance().getConnection();

        String sql = "SELECT * FROM user WHERE email=? AND password=? ";
        PreparedStatement pstm = connection.prepareStatement(sql);

        pstm.setString(1, username);
        pstm.setString(2, password);

        ResultSet resultSet = pstm.executeQuery();
        return resultSet.next();

    }

    public boolean exitUser(String email) throws SQLException {
        Connection connection = DbConnection.getInstance().getConnection();

        String sql = "SELECT email FROM user WHERE email = ?";
        PreparedStatement pstm = connection.prepareStatement(sql);
        pstm.setString(1, email);

        ResultSet resultSet =pstm.executeQuery();
        return resultSet.next();

    }
}
