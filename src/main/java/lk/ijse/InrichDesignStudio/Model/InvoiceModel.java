package lk.ijse.InrichDesignStudio.Model;

import lk.ijse.InrichDesignStudio.Db.DbConnection;
import lk.ijse.InrichDesignStudio.dto.Tm.cartTm;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.List;

public class InvoiceModel {

    public boolean saveData(String orderId, List<cartTm> cartTmList) throws SQLException {
        for(cartTm tm : cartTmList) {
            if(!saveOrderDetails(orderId, tm)) {
                return false;
            }
        }
        return true;
}

    private boolean saveOrderDetails(String orderId, cartTm tm) throws SQLException {
        Connection connection = DbConnection.getInstance().getConnection();

        String sql = "INSERT INTO invoice VALUES(?, ?, ?, ?,?)";
        PreparedStatement pstm = connection.prepareStatement(sql);

        pstm.setString(1, orderId);
        pstm.setString(2, tm.getCode());
        pstm.setString(3,tm.getDescription());
        pstm.setInt(4, tm.getQty());
        pstm.setDouble(5,tm.getTot());

        return pstm.executeUpdate() > 0;
    }
}
