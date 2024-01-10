package lk.ijse.InrichDesignStudio.bo.custom.impl;

import lk.ijse.InrichDesignStudio.Db.DbConnection;
import lk.ijse.InrichDesignStudio.bo.custom.IncomeBO;
import lk.ijse.InrichDesignStudio.bo.custom.PlaceOrderBO;
import lk.ijse.InrichDesignStudio.dao.custom.*;
import lk.ijse.InrichDesignStudio.dao.custom.impl.*;
import lk.ijse.InrichDesignStudio.dao.factory.DAOFactory;
import lk.ijse.InrichDesignStudio.dao.factory.DAOTypes;
import lk.ijse.InrichDesignStudio.dto.CustomerDto;
import lk.ijse.InrichDesignStudio.dto.ItemDto;
import lk.ijse.InrichDesignStudio.dto.OrderDto;
import lk.ijse.InrichDesignStudio.dto.PlaceOrderDto;
import lk.ijse.InrichDesignStudio.dto.Tm.cartTm;
import lk.ijse.InrichDesignStudio.entity.*;

import java.sql.Connection;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class PlaceOrderBOImpl implements PlaceOrderBO {
    IncomeDAO incomeDAO = (IncomeDAO) DAOFactory.getDaoFactory().getDAO(DAOTypes.INCOME);
    ItemDAO itemDAO = (ItemDAO) DAOFactory.getDaoFactory().getDAO(DAOTypes.ITEM);
    CustomerDAO customerDAO = (CustomerDAO) DAOFactory.getDaoFactory().getDAO(DAOTypes.CUSTOMER);
    OrderDAO orderDAO = (OrderDAO) DAOFactory.getDaoFactory().getDAO(DAOTypes.ORDER);
    InventoryDAO inventoryDAO = (InventoryDAO) DAOFactory.getDaoFactory().getDAO(DAOTypes.INVENTORY);

    InvoiveDAO invoiveDAO = (InvoiveDAO) DAOFactory.getDaoFactory().getDAO(DAOTypes.INVOICE);

    @Override
    public String generateNewInvoiceId() throws SQLException, ClassNotFoundException {
        return incomeDAO.generateNewId();
    }

    @Override
    public ArrayList<ItemDto> getAllItem() throws SQLException, ClassNotFoundException {
        ArrayList<ItemDto>itemDtos = new ArrayList<>();
        ArrayList<Item> items = itemDAO.getAll();

        for (Item item : items){
            itemDtos.add(new ItemDto(item.getId(),item.getName(),item.getType(),item.getAmount()));
        }
        return itemDtos;
    }

    @Override
    public ItemDto searchItem(String id) throws SQLException, ClassNotFoundException {
        Item item = itemDAO.search(id);
        return new ItemDto(item.getId(),item.getName(),item.getType(),item.getAmount());
    }

    @Override
    public ArrayList<CustomerDto> getAllCustomer() throws SQLException, ClassNotFoundException {
        ArrayList<CustomerDto> customerDtos = new ArrayList<>();
        ArrayList<Customer> customers = customerDAO.getAll();
        for (Customer customer : customers){
            customerDtos.add(new CustomerDto(customer.getId(),customer.getName(),customer.getAddress(),customer.getTel(),customer.getEmail()));

        }
        return customerDtos;
    }

    @Override
    public CustomerDto searchCustomer(String id) throws SQLException, ClassNotFoundException {
        Customer customer = customerDAO.search(id);
        return new CustomerDto(customer.getId(),customer.getName(),customer.getAddress(),customer.getTel(),customer.getEmail());
    }

    @Override
    public String generateNewOrderId() throws SQLException, ClassNotFoundException {
        return orderDAO.generateNewId();
    }

    @Override
    public boolean placeOrder(PlaceOrderDto dto) throws SQLException, ClassNotFoundException {
        boolean result = false;

        String orderId = dto.getOrderId();
        String customerId = dto.getCustomerId();
        LocalDate date = dto.getDate();
        String invoiceId = dto.getInvoiceId();
        LocalDate handOverDate = dto.getHandOverdate();
        String type = dto.getType();
        double amount = dto.getAmount();



           Connection connection = null;

            connection = DbConnection.getInstance().getConnection();
            try{
                connection.setAutoCommit(false);
                boolean isOrderSaved = orderDAO.save(new Orders(orderId,date,customerId,handOverDate));
                System.out.println("1");
                if(!isOrderSaved){
                    connection.rollback();
                    connection.setAutoCommit(true);
                    return false;
                }
                boolean isSavedIncome = incomeDAO.save(new Income(invoiceId,orderId,type,amount,date));
                System.out.println("1");
                if(!isSavedIncome){
                    connection.rollback();
                    connection.setAutoCommit(true);
                    return false;
                }
                List<cartTm> cart = dto.getCartTmList();
                for(cartTm tm : cart){
                    boolean isSavedInventory = inventoryDAO.save(new Inventory(orderId, tm.getCode(), tm.getQty(), handOverDate));
                    System.out.println("1");
                    if(!isSavedInventory){
                        connection.rollback();
                        connection.setAutoCommit(true);
                        return false;
                    }
                    boolean isSavedInvoice = invoiveDAO.save(new Invoice(orderId, tm.getCode(), tm.getDescription(), tm.getQty(), tm.getUnitPrice()));
                    System.out.println("1");
                    if (!isSavedInvoice){
                        connection.rollback();
                        connection.setAutoCommit(true);
                        return false;
                    }

                }
                connection.commit();
                connection.setAutoCommit(true);
                return true;

            } catch (SQLException e) {
                e.printStackTrace();
                connection.rollback();
                connection.setAutoCommit(true);
                return true;
            }


    }
}
