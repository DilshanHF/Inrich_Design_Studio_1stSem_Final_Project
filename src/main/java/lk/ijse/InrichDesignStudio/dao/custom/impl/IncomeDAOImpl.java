package lk.ijse.InrichDesignStudio.dao.custom.impl;

import lk.ijse.InrichDesignStudio.dao.SQLUtil;
import lk.ijse.InrichDesignStudio.dao.custom.IncomeDAO;
import lk.ijse.InrichDesignStudio.entity.Income;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class IncomeDAOImpl implements IncomeDAO {
    @Override
    public ArrayList<Income> getAll() throws SQLException, ClassNotFoundException {
        ResultSet rst = SQLUtil.execute("SELECT * FROM income");
        ArrayList<Income> getAllIncome = new ArrayList<>();
        while (rst.next()){
           Income income = new Income(rst.getString(1),rst.getString(2),rst.getString(3),rst.getDouble(4), rst.getDate(5).toLocalDate());
           getAllIncome.add(income);
        }
        return  getAllIncome;
    }

    @Override
    public boolean save(Income dto) throws SQLException, ClassNotFoundException {
        return SQLUtil.execute("INSERT INTO income VALUES(?, ?, ?,?,?)",
                dto.getInvoiceId(),dto.getOrderId(),dto.getType(),dto.getAmount(),dto.getDate());
    }

    @Override
    public boolean update(Income dto) throws SQLException, ClassNotFoundException {
        return false;
    }

    @Override
    public boolean delete(String id) throws SQLException, ClassNotFoundException {
        return false;
    }

    @Override
    public String generateNewId() throws SQLException, ClassNotFoundException {
        ResultSet rst = SQLUtil.execute("SELECT income_id  FROM income ORDER BY income_id DESC LIMIT 1");
        return rst.next()? String.format("IV%03d",Integer.parseInt(rst.getString(1).split("IV")[1])+1):"IV001";
    }

    @Override
    public boolean exist(String id) throws SQLException, ClassNotFoundException {
        return false;
    }

    @Override
    public Income search(String id) throws SQLException, ClassNotFoundException {
        return null;
    }

    @Override
    public double getTotalIncome() throws SQLException, ClassNotFoundException {
        ResultSet rst = SQLUtil.execute("SELECT SUM(amount) FROM income");

        if(rst.next()){
            return rst.getDouble(1);
        }
        return 0;
    }
}
