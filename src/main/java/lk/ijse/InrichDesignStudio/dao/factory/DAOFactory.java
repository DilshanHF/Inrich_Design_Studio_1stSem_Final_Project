package lk.ijse.InrichDesignStudio.dao.factory;

import lk.ijse.InrichDesignStudio.dao.SuperDAO;
import lk.ijse.InrichDesignStudio.dao.custom.impl.*;

public class DAOFactory {

    private static DAOFactory daoFactory;

    private DAOFactory(){

    }

    public static DAOFactory getDaoFactory(){
        return (daoFactory == null)? daoFactory = new DAOFactory() : daoFactory ;
    }

    public SuperDAO getDAO(DAOTypes daoTypes){
        switch (daoTypes){
            case USER:
                return new UserDAOimpl();
            case CUSTOMER:
                return new CustomerDAOImpl();
            case EMPLOYEE:
                return new EmployeeDAOImpl();
            case ORDER:
                return new OrderDAOImpl();
            case ITEM:
                return new ItemDAOImpl();
            case INCOME:
                return new IncomeDAOImpl();
            case EXPENSES:
                return new ExpensesDAOImpl();
            case INVENTORY:
                return  new InventoryDAOImpl();
            case INVOICE:
                return new InvoiceDAOImpl();
            case ATTENDANCE:
                return new AttendanceDAOImpl();
            default:
                return  null;
        }
    }

}
