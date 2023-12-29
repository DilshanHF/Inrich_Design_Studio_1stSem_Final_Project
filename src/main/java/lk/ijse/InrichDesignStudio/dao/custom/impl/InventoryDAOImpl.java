package lk.ijse.InrichDesignStudio.dao.custom.impl;

import lk.ijse.InrichDesignStudio.dao.SQLUtil;
import lk.ijse.InrichDesignStudio.dao.custom.InventoryDAO;
import lk.ijse.InrichDesignStudio.entity.Inventory;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class InventoryDAOImpl implements InventoryDAO {
    @Override
    public ArrayList<Inventory> getAll() throws SQLException, ClassNotFoundException {
        ResultSet rst = SQLUtil.execute("SELECT * FROM inventory_details");
        ArrayList<Inventory>getAllInventory = new ArrayList<>();
        while (rst.next()){
            Inventory inventory = new Inventory(rst.getString(1),rst.getString(2),rst.getInt(3), rst.getDate(4).toLocalDate());
            getAllInventory.add(inventory);
        }
        return getAllInventory;
    }

    @Override
    public boolean save(Inventory dto) throws SQLException, ClassNotFoundException {
        return false;
    }

    @Override
    public boolean update(Inventory dto) throws SQLException, ClassNotFoundException {
        return false;
    }

    @Override
    public boolean delete(String id) throws SQLException, ClassNotFoundException {
        return false;
    }

    @Override
    public String generateNewId() throws SQLException, ClassNotFoundException {
        return null;
    }

    @Override
    public boolean exist(String id) throws SQLException, ClassNotFoundException {
        return false;
    }

    @Override
    public Inventory search(String id) throws SQLException, ClassNotFoundException {
        return null;
    }
}
