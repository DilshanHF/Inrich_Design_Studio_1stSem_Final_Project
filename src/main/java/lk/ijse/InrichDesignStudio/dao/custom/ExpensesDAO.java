package lk.ijse.InrichDesignStudio.dao.custom;

import lk.ijse.InrichDesignStudio.dao.CrudDAO;
import lk.ijse.InrichDesignStudio.entity.Expense;

import java.sql.SQLException;

public interface ExpensesDAO extends CrudDAO<Expense> {
    double getTotal() throws SQLException,ClassNotFoundException;

}
