package lk.ijse.InrichDesignStudio.bo.custom;

import lk.ijse.InrichDesignStudio.bo.SuperBO;
import lk.ijse.InrichDesignStudio.dto.IncomeDto;
import lk.ijse.InrichDesignStudio.entity.Income;

import java.sql.SQLException;
import java.util.ArrayList;

public interface IncomeBO extends SuperBO {
    ArrayList<IncomeDto> getAllIncome() throws SQLException, ClassNotFoundException;
}
