package lk.ijse.InrichDesignStudio.bo.custom;

import lk.ijse.InrichDesignStudio.bo.SuperBO;

import java.sql.SQLException;

public interface DashboardBO extends SuperBO {
    double getTotalExpenses() throws SQLException,ClassNotFoundException;
    double getTotalIncome() throws SQLException, ClassNotFoundException;
    String getTotalCustomers() throws SQLException, ClassNotFoundException;
    String getTotalOrders() throws SQLException, ClassNotFoundException;
    String getTotalEmployees() throws SQLException, ClassNotFoundException;
}
