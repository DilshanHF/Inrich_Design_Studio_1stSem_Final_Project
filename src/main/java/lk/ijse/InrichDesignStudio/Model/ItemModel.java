package lk.ijse.InrichDesignStudio.Model;

import lk.ijse.InrichDesignStudio.Db.DbConnection;
import lk.ijse.InrichDesignStudio.dto.ItemDto;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class ItemModel {
    public boolean exitItem(String id) throws SQLException {
        Connection connection = DbConnection.getInstance().getConnection();

        String sql = "SELECT item_code FROM item WHERE item_code = ?";
        PreparedStatement pstm = connection.prepareStatement(sql);
        pstm.setString(1, id);

        ResultSet resultSet =pstm.executeQuery();
        return resultSet.next();
    }

    public boolean saveItem(ItemDto dto) throws SQLException {
        Connection connection = DbConnection.getInstance().getConnection();

        String sql = "INSERT INTO item VALUES(?,?,?,?)";
        PreparedStatement pstm = connection.prepareStatement(sql);

        pstm.setString(1,dto.getId());
        pstm.setString(2,dto.getName());
        pstm.setString(3,dto.getType());
        pstm.setString(4, String.valueOf(dto.getAmount()));


        return pstm.executeUpdate()>0;
    }

    public List<ItemDto> getAllItem() throws SQLException {
        Connection connection = DbConnection.getInstance().getConnection();

        String sql = "SELECT * FROM item";
        PreparedStatement pstm = connection.prepareStatement(sql);
        ResultSet resultSet = pstm.executeQuery();

        ArrayList<ItemDto> dtoList = new ArrayList<>();

        while(resultSet.next()) {
            dtoList.add(
                    new ItemDto(
                            resultSet.getString(1),
                            resultSet.getString(2),
                            resultSet.getString(3),
                            resultSet.getDouble(4)

                    )
            );
        }
        return dtoList;

    }

    public boolean deleteItem(String id) throws SQLException {
        Connection connection = DbConnection.getInstance().getConnection();

        String sql = "DELETE FROM item WHERE item_code = ?";
        PreparedStatement pstm =connection.prepareStatement(sql);
        pstm.setString(1,id);
        return pstm.executeUpdate()>0;
    }

    public boolean updateItem(ItemDto dto) throws SQLException {
        Connection connection = DbConnection.getInstance().getConnection();

        String sql = "UPDATE item SET item_name = ?, Wood_type = ?, unit_price= ? WHERE item_code = ?";
        PreparedStatement pstm = connection.prepareStatement(sql);


        pstm.setString(1, dto.getName());
        pstm.setString(2, dto.getType());
        pstm.setString(3, String.valueOf(dto.getAmount()));
        pstm.setString(4, dto.getId());

        return pstm.executeUpdate() > 0;
    }

    public ItemDto searchItem(String code) throws SQLException {
        Connection connection = DbConnection.getInstance().getConnection();
        String sql = "SELECT * FROM item WHERE item_code = ?";

        PreparedStatement pstm = connection.prepareStatement(sql);
        pstm.setString(1, code);

        ResultSet resultSet = pstm.executeQuery();

        ItemDto dto = null;

        if(resultSet.next()) {

            String iCode = resultSet.getString(1);
            String iName = resultSet.getString(2);
            String iType = resultSet.getString(3);
            double iPrice = resultSet.getDouble(4);

            dto = new ItemDto(iCode,iName,iType,iPrice);

        }
        return dto;
    }
}
