package lk.ijse.InrichDesignStudio.bo.custom.impl;

import lk.ijse.InrichDesignStudio.bo.custom.ExpensesBO;
import lk.ijse.InrichDesignStudio.dao.custom.ExpensesDAO;
import lk.ijse.InrichDesignStudio.dao.custom.impl.ExpensesDAOImpl;
import lk.ijse.InrichDesignStudio.dto.ExpenseDto;
import lk.ijse.InrichDesignStudio.entity.Expense;

import java.sql.SQLException;
import java.util.ArrayList;

public class ExpensesBOImpl implements ExpensesBO {

    ExpensesDAO expensesDAO = new ExpensesDAOImpl();
    @Override
    public ArrayList<ExpenseDto> getAllExpenses() throws SQLException, ClassNotFoundException {
        ArrayList<ExpenseDto>expenseDtos = new ArrayList<>();
        ArrayList<Expense> expenses = expensesDAO.getAll();

        for (Expense expense : expenses){
            expenseDtos.add(new ExpenseDto(expense.getId(),expense.getDate(),expense.getDes(),expense.getType(),expense.getAmount()));
        }
        return expenseDtos;
    }

    @Override
    public boolean saveExpenses(ExpenseDto dto) throws SQLException, ClassNotFoundException {
        return expensesDAO.save(new Expense(dto.getId(), dto.getDate(), dto.getDes(), dto.getType(), dto.getAmount()));
    }

    @Override
    public boolean updateExpenses(ExpenseDto dto) throws SQLException, ClassNotFoundException {
        return expensesDAO.update(new Expense(dto.getId(), dto.getDate(), dto.getDes(), dto.getType(), dto.getAmount()));
    }

    @Override
    public boolean deleteExpenses(String id) throws SQLException, ClassNotFoundException {
       return expensesDAO.delete(id);
    }

    @Override
    public boolean existExpenses(String id) throws SQLException, ClassNotFoundException {
        return expensesDAO.exist(id);
    }

    @Override
    public double getTotalExpenses() throws SQLException, ClassNotFoundException {
        return expensesDAO.getTotal();
    }
}
