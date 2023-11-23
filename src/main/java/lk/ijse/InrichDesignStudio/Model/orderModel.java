package lk.ijse.InrichDesignStudio.Model;

import lk.ijse.InrichDesignStudio.Db.DbConnection;

import java.sql.*;
import java.time.LocalDate;

public class orderModel {

    public String generateNextId() throws SQLException {
        Connection connection = DbConnection.getInstance().getConnection();

        String sql = "SELECT  order_id FROM orders ORDER BY order_id DESC LIMIT 1";
        PreparedStatement pstm = connection.prepareStatement(sql);

        ResultSet resultSet = pstm.executeQuery();
        if(resultSet.next()) {
            return splitOrderId(resultSet.getString(1));
        }
        return splitOrderId(null);
    }

    private String splitOrderId(String currentOrderId) {
        if(currentOrderId != null) {
            String[] split = currentOrderId.split("O0");

            int id = Integer.parseInt(split[1]); //01
            id++;
            return "O00" + id;
        } else {
            return "O001";
        }

    }

    public boolean saveOrder(String orderId, LocalDate date, String customerId, LocalDate handOverDate) throws SQLException {
        Connection connection = DbConnection.getInstance().getConnection();

        String sql = "INSERT INTO orders VALUES(?, ?, ?,?)";
        PreparedStatement pstm = connection.prepareStatement(sql);
        pstm.setString(1, orderId);
        pstm.setDate(2, Date.valueOf(date));
        pstm.setString(3,customerId);
        pstm.setDate(4, Date.valueOf(handOverDate));

        return pstm.executeUpdate() > 0;

    }

    public String getTotalOrders() throws SQLException {
        Connection connection = DbConnection.getInstance().getConnection();

        String sql = "SELECT COUNT(*) FROM orders";
        PreparedStatement pstm = connection.prepareStatement(sql);

        ResultSet resultSet =pstm.executeQuery();

        if(resultSet.next()) {
            return resultSet.getString(1);

        }
        return null;

    }
}
