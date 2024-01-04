package lk.ijse.InrichDesignStudio.dao.custom.impl;

import lk.ijse.InrichDesignStudio.dao.SQLUtil;
import lk.ijse.InrichDesignStudio.dao.custom.ExpensesDAO;
import lk.ijse.InrichDesignStudio.entity.Expense;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class ExpensesDAOImpl implements ExpensesDAO {
    @Override
    public ArrayList<Expense> getAll() throws SQLException, ClassNotFoundException {
        ResultSet rst = SQLUtil.execute("SELECT * FROM expense");
        ArrayList<Expense> getAllExpenses = new ArrayList<>();
        while (rst.next()){
            Expense expense = new Expense(rst.getString(1), rst.getDate(2).toLocalDate(),rst.getString(3),rst.getString(4),rst.getDouble(5));
            getAllExpenses.add(expense);
        }
        return  getAllExpenses;
    }

    @Override
    public boolean save(Expense dto) throws SQLException, ClassNotFoundException {
        return SQLUtil.execute("INSERT INTO expense VALUES(?,?,?,?,?)",
                dto.getId(),dto.getDate(),dto.getDes(),dto.getType(),dto.getAmount());
    }

    @Override
    public boolean update(Expense dto) throws SQLException, ClassNotFoundException {
        return SQLUtil.execute("UPDATE expense SET date = ?, descripton = ?, type = ? ,amount = ? WHERE e_id = ?",
                dto.getDate(),dto.getDes(),dto.getType(),dto.getAmount(),dto.getId());
    }

    @Override
    public boolean delete(String id) throws SQLException, ClassNotFoundException {
        return SQLUtil.execute("DELETE FROM expense WHERE expense_id = ?",id);
    }

    @Override
    public String generateNewId() throws SQLException, ClassNotFoundException {
        return null;
    }

    @Override
    public boolean exist(String id) throws SQLException, ClassNotFoundException {
        ResultSet rst = SQLUtil.execute("SELECT expense_id FROM expense WHERE expense_id = ?",id);
        return rst.next();
    }

    @Override
    public Expense search(String id) throws SQLException, ClassNotFoundException {
        return null;
    }

    @Override
    public double getTotal() throws SQLException, ClassNotFoundException {
        ResultSet rst = SQLUtil.execute("SELECT SUM(amount) FROM expense");

        if(rst.next()){
            return rst.getDouble(1);
        }
        return 0;

    }
}
