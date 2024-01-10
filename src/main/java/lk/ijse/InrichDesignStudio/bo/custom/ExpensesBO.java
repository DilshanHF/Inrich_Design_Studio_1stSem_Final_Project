package lk.ijse.InrichDesignStudio.bo.custom;

import lk.ijse.InrichDesignStudio.bo.SuperBO;
import lk.ijse.InrichDesignStudio.dto.ExpenseDto;
import lk.ijse.InrichDesignStudio.entity.Expense;

import java.sql.SQLException;
import java.util.ArrayList;


public interface ExpensesBO extends SuperBO {
    ArrayList<ExpenseDto> getAllExpenses() throws SQLException, ClassNotFoundException;
    boolean saveExpenses(ExpenseDto dto) throws SQLException, ClassNotFoundException;
    boolean updateExpenses(ExpenseDto dto) throws SQLException, ClassNotFoundException;
    boolean deleteExpenses(String id) throws SQLException, ClassNotFoundException;
    boolean existExpenses(String id) throws SQLException, ClassNotFoundException;
    double getTotalExpenses() throws SQLException, ClassNotFoundException;

}
