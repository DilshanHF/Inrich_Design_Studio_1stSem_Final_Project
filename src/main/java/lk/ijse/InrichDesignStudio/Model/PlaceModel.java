package lk.ijse.InrichDesignStudio.Model;

import lk.ijse.InrichDesignStudio.Db.DbConnection;
import lk.ijse.InrichDesignStudio.dto.PlaceOrderDto;

import java.sql.Connection;
import java.sql.SQLException;
import java.time.LocalDate;

public class PlaceModel {

    private OrderModel oModel = new OrderModel();
    private IncomeModel inModel = new IncomeModel();
    private InventoryModel invenModel = new InventoryModel();
    private InvoiceModel invModel = new InvoiceModel();

    public boolean placeOrder(PlaceOrderDto dto) throws SQLException {
        boolean result = false;

        String orderId = dto.getOrderId();
        String customerId = dto.getCustomerId();
        LocalDate date = dto.getDate();
        String invoiceId = dto.getInvoiceId();
        LocalDate handOverDate = dto.getHandOverdate();
        String type = dto.getType();
        double amount = dto.getAmount();

        Connection connection = null;
       try {
         connection = DbConnection.getInstance().getConnection();
         connection.setAutoCommit(false);

         boolean isOrderSaved = oModel.saveOrder(orderId,date,customerId,handOverDate);
            System.out.println("1");
         if(isOrderSaved){
             boolean isSavedIncome = inModel.saveIncome(invoiceId,orderId,type,amount,date);
             System.out.println("1");
            if(isSavedIncome){
                 boolean isSavedInventory = invenModel.saveInventory(dto.getCartTmList(),orderId,handOverDate);
                 //invModel.saveData(orderId,dto.getCartTmList());
                 System.out.println("1");

                   if(isSavedInventory){
                        boolean isSavedInvoice = invModel.saveData(orderId,dto.getCartTmList());
                       System.out.println("1");

                       if (isSavedInvoice) {
                           connection.commit();
                           result = true;
                       }

                    }
             }
         }

        } catch (SQLException e) {
            //throw new RuntimeException(e);
            connection.rollback();
        }finally {
            connection.setAutoCommit(true);
        }
            return result;
    }
}
