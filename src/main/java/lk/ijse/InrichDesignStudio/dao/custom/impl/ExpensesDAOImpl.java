package lk.ijse.InrichDesignStudio.dao.custom.impl;

import lk.ijse.InrichDesignStudio.dao.custom.ExpensesDAO;
import lk.ijse.InrichDesignStudio.entity.Expense;

import java.sql.SQLException;
import java.util.ArrayList;

public class ExpensesDAOImpl implements ExpensesDAO {
    @Override
    public ArrayList<Expense> getAll() throws SQLException, ClassNotFoundException {
        return null;
    }

    @Override
    public boolean save(Expense dto) throws SQLException, ClassNotFoundException {
        return false;
    }

    @Override
    public boolean update(Expense dto) throws SQLException, ClassNotFoundException {
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
    public Expense search(String id) throws SQLException, ClassNotFoundException {
        return null;
    }
}
