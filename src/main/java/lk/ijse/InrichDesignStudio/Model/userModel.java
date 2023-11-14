package lk.ijse.InrichDesignStudio.Model;

import lk.ijse.InrichDesignStudio.Db.DbConnection;
import lk.ijse.InrichDesignStudio.dto.userDto;

import java.sql.*;

public class userModel {

    public boolean saveUser(userDto dto) throws SQLException {
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
}
