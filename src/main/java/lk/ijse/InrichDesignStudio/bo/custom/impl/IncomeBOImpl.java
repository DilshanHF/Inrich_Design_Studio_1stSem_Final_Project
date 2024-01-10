package lk.ijse.InrichDesignStudio.bo.custom.impl;

import lk.ijse.InrichDesignStudio.bo.custom.IncomeBO;
import lk.ijse.InrichDesignStudio.dao.custom.IncomeDAO;
import lk.ijse.InrichDesignStudio.dao.custom.impl.IncomeDAOImpl;
import lk.ijse.InrichDesignStudio.dao.factory.DAOFactory;
import lk.ijse.InrichDesignStudio.dao.factory.DAOTypes;
import lk.ijse.InrichDesignStudio.dto.IncomeDto;
import lk.ijse.InrichDesignStudio.entity.Income;

import java.sql.SQLException;
import java.util.ArrayList;

public class IncomeBOImpl implements IncomeBO {
    IncomeDAO incomeDAO = (IncomeDAO) DAOFactory.getDaoFactory().getDAO(DAOTypes.INCOME);
    @Override
    public ArrayList<IncomeDto> getAllIncome() throws SQLException, ClassNotFoundException {
        ArrayList<IncomeDto> incomeDtos = new ArrayList<>();
        ArrayList<Income> incomes = incomeDAO.getAll();

        for (Income income : incomes){
            incomeDtos.add(new IncomeDto(income.getInvoiceId(),income.getOrderId(),income.getType(),
                    income.getAmount(),income.getDate()));

        }
        return incomeDtos;
    }
}
