package lk.ijse.InrichDesignStudio.bo.custom.impl;

import lk.ijse.InrichDesignStudio.bo.custom.DashboardBO;
import lk.ijse.InrichDesignStudio.dao.custom.*;
import lk.ijse.InrichDesignStudio.dao.custom.impl.*;
import lk.ijse.InrichDesignStudio.dao.factory.DAOFactory;
import lk.ijse.InrichDesignStudio.dao.factory.DAOTypes;

import java.sql.SQLException;

public class DashboardBOImpl implements DashboardBO {
    ExpensesDAO expensesDAO = (ExpensesDAO) DAOFactory.getDaoFactory().getDAO(DAOTypes.EXPENSES);
    IncomeDAO incomeDAO = (IncomeDAO) DAOFactory.getDaoFactory().getDAO(DAOTypes.INCOME);
    CustomerDAO customerDAO = (CustomerDAO) DAOFactory.getDaoFactory().getDAO(DAOTypes.CUSTOMER);
    OrderDAO orderDAO = (OrderDAO) DAOFactory.getDaoFactory().getDAO(DAOTypes.ORDER);
    EmployeeDAO employeeDAO = (EmployeeDAO) DAOFactory.getDaoFactory().getDAO(DAOTypes.EMPLOYEE);
    @Override
    public double getTotalExpenses() throws SQLException, ClassNotFoundException {
        return expensesDAO.getTotal();
    }

    @Override
    public double getTotalIncome() throws SQLException, ClassNotFoundException {
        return incomeDAO.getTotalIncome();
    }

    @Override
    public String getTotalCustomers() throws SQLException, ClassNotFoundException {
        return customerDAO.getTotalCustomers();
    }

    @Override
    public String getTotalOrders() throws SQLException, ClassNotFoundException {
        return orderDAO.getTotalOrders();
    }

    @Override
    public String getTotalEmployees() throws SQLException, ClassNotFoundException {
        return employeeDAO.getTotalEmployees();
    }
}
