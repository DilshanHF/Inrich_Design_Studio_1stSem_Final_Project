package lk.ijse.InrichDesignStudio.dao.custom;

import lk.ijse.InrichDesignStudio.dao.CrudDAO;
import lk.ijse.InrichDesignStudio.entity.Income;

import java.sql.SQLException;

public interface IncomeDAO extends CrudDAO<Income> {
    double getTotalIncome() throws SQLException,ClassNotFoundException;
}
