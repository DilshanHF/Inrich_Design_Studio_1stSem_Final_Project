package lk.ijse.InrichDesignStudio.Model;

import lk.ijse.InrichDesignStudio.Db.DbConnection;
import lk.ijse.InrichDesignStudio.dto.Tm.cartTm;
import lk.ijse.InrichDesignStudio.dto.inventoryDto;

import java.sql.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class inventoryModel {
    public boolean saveInventory(List<cartTm> cartTmList, String orderId, LocalDate handOverDate) throws SQLException {
        for(cartTm tm : cartTmList) {
            if(!saveOrderDetails(orderId, tm,handOverDate)) {
                return false;
            }
        }
        return true;
    }

    private boolean saveOrderDetails(String orderId, cartTm tm, LocalDate handOverDate) throws SQLException {
        Connection connection = DbConnection.getInstance().getConnection();

        String sql = "INSERT INTO inventory_details VALUES(?, ?, ?, ?)";
        PreparedStatement pstm = connection.prepareStatement(sql);

        pstm.setString(1, orderId);
        pstm.setString(2, tm.getCode());
        pstm.setInt(3, tm.getQty());
        pstm.setDate(4, Date.valueOf(handOverDate));

        return pstm.executeUpdate() > 0;
    }

    public ArrayList<inventoryDto> getAllInventory() throws SQLException {
        Connection connection = DbConnection.getInstance().getConnection();

        String sql = "SELECT * FROM inventory_details";
        PreparedStatement pstm = connection.prepareStatement(sql);
        ResultSet resultSet = pstm.executeQuery();

        ArrayList<inventoryDto> dtoList = new ArrayList<>();

        while(resultSet.next()) {
            dtoList.add(
                    new inventoryDto(
                            resultSet.getString(1),
                            resultSet.getString(2),
                            resultSet.getInt(3),
                            resultSet.getDate(4).toLocalDate()
                    )
            );
        }
        return dtoList;
    }
}
