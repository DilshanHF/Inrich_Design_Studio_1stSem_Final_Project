package lk.ijse.InrichDesignStudio.Model;

import lk.ijse.InrichDesignStudio.Db.DbConnection;
import lk.ijse.InrichDesignStudio.dto.IncomeDto;

import java.sql.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class IncomeModel {
    public String generateNextId() throws SQLException {
        Connection connection = DbConnection.getInstance().getConnection();

        String sql = "SELECT income_id  FROM income ORDER BY income_id DESC LIMIT 1";
        PreparedStatement pstm = connection.prepareStatement(sql);

        ResultSet resultSet = pstm.executeQuery();
        if(resultSet.next()) {
            return splitOrderId(resultSet.getString(1));
        }
        return splitOrderId(null);

    }

    private String splitOrderId(String currentId) {
        if(currentId != null) {
            String[] split = currentId.split("IV0");

            int id = Integer.parseInt(split[1]); //01
            id++;
            return "IV0" + id;
        } else {
            return "IV001";
        }
    }

    public boolean saveIncome(String invoiceId, String orderId, String type, double amount, LocalDate date) throws SQLException {
        Connection connection = DbConnection.getInstance().getConnection();

        String sql = "INSERT INTO income VALUES(?, ?, ?,?,?)";
        PreparedStatement pstm = connection.prepareStatement(sql);
        pstm.setString(1, invoiceId);
        pstm.setString(2, orderId);
        pstm.setString(3, type);
        pstm.setString(4, String.valueOf(amount));
        pstm.setDate(5, Date.valueOf(date));


        return pstm.executeUpdate() > 0;

    }

    public List<IncomeDto> getAllIncome() throws SQLException {
        Connection connection = DbConnection.getInstance().getConnection();

        String sql = "SELECT * FROM income";
        PreparedStatement pstm = connection.prepareStatement(sql);
        ResultSet resultSet = pstm.executeQuery();

        ArrayList<IncomeDto> dtoList = new ArrayList<>();

        while(resultSet.next()) {
            dtoList.add(
                    new IncomeDto(
                            resultSet.getString(1),
                            resultSet.getString(2),
                            resultSet.getString(3),
                            resultSet.getDouble(4),
                            resultSet.getDate(5).toLocalDate()
                    )
            );
        }
        return dtoList;
    }

    public double getTotalIncome() throws SQLException {
        Connection connection = DbConnection.getInstance().getConnection();

        String sql = "SELECT SUM(amount) FROM income";
        PreparedStatement pstm = connection.prepareStatement(sql);

        ResultSet resultSet = pstm.executeQuery();

        if(resultSet.next()) {
            return resultSet.getDouble(1);
        }
        return 0;
    }


}
