package lk.ijse.InrichDesignStudio.Model;

import lk.ijse.InrichDesignStudio.Db.DbConnection;
import lk.ijse.InrichDesignStudio.dto.placeOrderDto;

import java.sql.Connection;
import java.sql.SQLException;
import java.time.LocalDate;

public class placeModel {

    private orderModel oModel = new orderModel();
    private incomeModel inModel = new incomeModel();
    private inventoryModel invenModel = new inventoryModel();

    public boolean placeOrder(placeOrderDto dto) throws SQLException {
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
                 System.out.println("1");

                   if(isSavedInventory){
                        connection.commit();
                        result  = true;

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
