package lk.ijse.InrichDesignStudio.dao.custom.impl;

import lk.ijse.InrichDesignStudio.dao.SQLUtil;
import lk.ijse.InrichDesignStudio.dao.custom.InvoiveDAO;
import lk.ijse.InrichDesignStudio.entity.Invoice;

import java.sql.SQLException;
import java.util.ArrayList;

public class InvoiceDAOImpl implements InvoiveDAO {
    @Override
    public ArrayList<Invoice> getAll() throws SQLException, ClassNotFoundException {
        return null;
    }

    @Override
    public boolean save(Invoice dto) throws SQLException, ClassNotFoundException {
        return SQLUtil.execute("INSERT INTO invoice VALUES(?, ?, ?, ?,?)",
                dto.getOrderId(),dto.getItemCode(),dto.getDescription(),dto.getQty(),dto.getUnitPrice());
    }

    @Override
    public boolean update(Invoice dto) throws SQLException, ClassNotFoundException {
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
    public Invoice search(String id) throws SQLException, ClassNotFoundException {
        return null;
    }
}
