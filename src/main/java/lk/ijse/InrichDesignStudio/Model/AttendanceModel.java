package lk.ijse.InrichDesignStudio.Model;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.Alert;
import lk.ijse.InrichDesignStudio.Db.DbConnection;
import lk.ijse.InrichDesignStudio.dto.Tm.attendanceTm;
import lk.ijse.InrichDesignStudio.dto.AttendDto;

import java.sql.*;
import java.time.LocalDate;
import java.util.ArrayList;

public class AttendanceModel {
    public boolean existAttendance(LocalDate date) throws SQLException {


        //return  false;
        Connection connection = DbConnection.getInstance().getConnection();
        String sql = "SELECT attandance_date FROM attandance WHERE attandance_date = ?";

        PreparedStatement pstm = connection.prepareStatement(sql);
        pstm.setDate(1, Date.valueOf(String.valueOf(date)));

        ResultSet resultSet =pstm.executeQuery();
        return resultSet.next();

    }

    public boolean saveAttendance(ArrayList<AttendDto> adto) throws SQLException {
        Connection connection = DbConnection.getInstance().getConnection();

        String sql = "INSERT INTO attandance VALUES(?,?,?,?,?)";
        PreparedStatement pstm = connection.prepareStatement(sql);
for (AttendDto adto1 : adto) {
    pstm.setString(1, adto1.getEmployeeId());
    pstm.setString(2, adto1.getName());;
    pstm.setString(3, adto1.getNic());;
    pstm.setString(4, String.valueOf(adto1.getDate()));
    pstm.setString(5, adto1.getStatus());
    pstm.addBatch();
}
      int[] result = pstm.executeBatch();

            for (int i : result) {
                if (i <= 0) {
                    return false;
                }
            }
            return true;


    }

    public boolean existId(ArrayList<AttendDto> adto) throws SQLException {
        Connection connection = DbConnection.getInstance().getConnection();

        String sql = "SELECT COUNT(*) FROM attandance WHERE e_id = ? AND attandance_date = ?";

        try {
            PreparedStatement pstm = connection.prepareStatement(sql);
            pstm.setString(1,adto.get(0).getEmployeeId());
            pstm.setDate(2,Date.valueOf(String.valueOf(adto.get(0).getDate())));

            try {
                ResultSet resultSet = pstm.executeQuery();
                if (resultSet.next()) {
                    if (resultSet.getInt(1) > 0) {
                        return true;
                    }
                }
            } catch (SQLException e) {
                //throw new RuntimeException(e);
                new Alert(Alert.AlertType.ERROR,e.getMessage()).showAndWait();
            }
        } catch (SQLException e) {
            //throw new RuntimeException(e);
            new Alert(Alert.AlertType.ERROR,e.getMessage()).showAndWait();
        }
        return false;
    }

    public ObservableList<attendanceTm> getAttendanceOfDay(String date) throws SQLException {
        Connection connection = DbConnection.getInstance().getConnection();

        String sql = "SELECT * FROM attandance WHERE attandance_date = ?";
        PreparedStatement pstm = connection.prepareStatement(sql);
        pstm.setString(1, date);

        ResultSet resultSet =pstm.executeQuery();
        ObservableList<attendanceTm> tmList = FXCollections.observableArrayList();

        while(resultSet.next()){
            final var add = tmList.add(new attendanceTm(resultSet.getString(1), resultSet.getString(2), resultSet.getString(3), resultSet.getString(5)));
        }
return tmList;

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
