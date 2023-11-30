package lk.ijse.InrichDesignStudio.Model;

import javafx.collections.ObservableList;
import lk.ijse.InrichDesignStudio.Db.DbConnection;
import lk.ijse.InrichDesignStudio.dto.attendDto;

import java.sql.*;
import java.time.LocalDate;

public class attendanceModel {
    public boolean existAttendance(LocalDate date) throws SQLException {
        Connection connection = DbConnection.getInstance().getConnection();
        String sql = "SELECT attandance_date FROM attandance WHERE date = ?";

        PreparedStatement pstm = connection.prepareStatement(sql);
        pstm.setDate(1, Date.valueOf(String.valueOf(date)));

        ResultSet resultSet =pstm.executeQuery();
        return resultSet.next();

    }

    public boolean saveAttendance(ObservableList<attendDto> att) throws SQLException {
        Connection connection = DbConnection.getInstance().getConnection();

        String sql = "INSERT INTO attandance VALUES(?,?,?,?)";
        PreparedStatement pstm = connection.prepareStatement(sql);

        pstm.setString(1.att.getE_id());

    }




    /*public boolean saveAttendance(ObservableList<attendDto> att) throws SQLException {
        Connection connection = DbConnection.getInstance().getConnection();

        String sql = "INSERT INTO attandance VALUES(?,?,?,?)";
        PreparedStatement pstm = connection.prepareStatement(sql);

        pstm.setString(1,att.getE_id());
        pstm.setString(2, String.valueOf(att.getTime()));
        pstm.setString(3, String.valueOf(att.getDate()));
        pstm.setString(4,att.getStatus());


        return pstm.executeUpdate()>0;
    }*/
}
