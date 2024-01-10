package lk.ijse.InrichDesignStudio.dao.custom;

import lk.ijse.InrichDesignStudio.dao.CrudDAO;
import lk.ijse.InrichDesignStudio.entity.Orders;

import java.sql.SQLException;

public interface OrderDAO extends CrudDAO<Orders> {
    String getTotalOrders() throws SQLException,ClassNotFoundException;
}
