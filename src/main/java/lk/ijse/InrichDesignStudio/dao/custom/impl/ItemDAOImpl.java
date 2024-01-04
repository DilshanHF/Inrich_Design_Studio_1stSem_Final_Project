package lk.ijse.InrichDesignStudio.dao.custom.impl;

import lk.ijse.InrichDesignStudio.dao.SQLUtil;
import lk.ijse.InrichDesignStudio.dao.custom.ItemDAO;
import lk.ijse.InrichDesignStudio.entity.Item;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class ItemDAOImpl implements ItemDAO {
    @Override
    public ArrayList<Item> getAll() throws SQLException, ClassNotFoundException {
        ResultSet rst = SQLUtil.execute("SELECT * FROM item");
        ArrayList<Item> getAllItem = new ArrayList<>();
        while (rst.next()){
            Item item = new Item(rst.getString(1),rst.getString(2),rst.getString(3),rst.getDouble(4));
            getAllItem.add(item);
        }
        return getAllItem;
    }

    @Override
    public boolean save(Item dto) throws SQLException, ClassNotFoundException {
        return SQLUtil.execute("INSERT INTO item VALUES(?,?,?,?)",
                dto.getId(),dto.getName(),dto.getType(),dto.getAmount());
    }

    @Override
    public boolean update(Item dto) throws SQLException, ClassNotFoundException {
        return SQLUtil.execute("UPDATE item SET item_name = ?, Wood_type = ?, unit_price= ? WHERE item_code = ?",
                dto.getName(),dto.getType(),dto.getAmount(),dto.getId());
    }

    @Override
    public boolean delete(String id) throws SQLException, ClassNotFoundException {
        return SQLUtil.execute("DELETE FROM item WHERE item_code = ?",id);
    }

    @Override
    public String generateNewId() throws SQLException, ClassNotFoundException {
        return null;
    }

    @Override
    public boolean exist(String id) throws SQLException, ClassNotFoundException {
       ResultSet resultSet = SQLUtil.execute("SELECT item_code FROM item WHERE item_code = ?",id);
       return resultSet.next();

    }

    @Override
    public Item search(String id) throws SQLException, ClassNotFoundException {
        ResultSet rst = SQLUtil.execute("SELECT * FROM item WHERE item_code = ?",id);
        rst.next();
        return new Item(rst.getString(1),rst.getString(2),rst.getString(3),rst.getDouble(4));
    }
}
