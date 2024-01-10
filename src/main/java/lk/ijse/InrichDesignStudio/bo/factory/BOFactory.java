package lk.ijse.InrichDesignStudio.bo.factory;

import lk.ijse.InrichDesignStudio.bo.SuperBO;
import lk.ijse.InrichDesignStudio.bo.custom.impl.*;

public class BOFactory {
    private static BOFactory boFactory;

    private BOFactory(){

    }

    public static BOFactory getBoFactory() {
        return (boFactory == null)? boFactory = new BOFactory() : boFactory;
    }

    public SuperBO getBO(BOTypes boTypes){
        switch (boTypes){
            case EMPLOYEE:
                return new EmployeeBOImpl();
            case CUSTOMER:
                return new CustomerBOImpl();
            case USER:
                return new UserBOImpl();
            case ITEM:
                return new ItemBOImpl();
            case INCOME:
                return new IncomeBOImpl();
            case EXPENSES:
                return new ExpensesBOImpl();
            case DASHBOARD:
                return  new DashboardBOImpl();
            case INVENTORY:
                return new InventoryBOImpl();
            case ATTENDANCE:
                return new AttendanceBOimpl();
            case PLACEORDER:
                return new PlaceOrderBOImpl();
            default:
                return null;

        }
    }
}
