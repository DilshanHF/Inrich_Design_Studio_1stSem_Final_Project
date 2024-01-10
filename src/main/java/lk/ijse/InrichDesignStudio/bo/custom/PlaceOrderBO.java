package lk.ijse.InrichDesignStudio.bo.custom;

import lk.ijse.InrichDesignStudio.bo.SuperBO;
import lk.ijse.InrichDesignStudio.dto.CustomerDto;
import lk.ijse.InrichDesignStudio.dto.ItemDto;
import lk.ijse.InrichDesignStudio.dto.PlaceOrderDto;
import lk.ijse.InrichDesignStudio.entity.Customer;
import lk.ijse.InrichDesignStudio.entity.Item;

import java.sql.SQLException;
import java.util.ArrayList;

public interface PlaceOrderBO extends SuperBO {
    String generateNewInvoiceId() throws SQLException, ClassNotFoundException;
    ArrayList<ItemDto> getAllItem() throws SQLException, ClassNotFoundException;
    ItemDto searchItem(String id) throws SQLException, ClassNotFoundException;
    ArrayList<CustomerDto> getAllCustomer() throws SQLException, ClassNotFoundException;
    CustomerDto searchCustomer(String id) throws SQLException, ClassNotFoundException;
    String generateNewOrderId() throws SQLException, ClassNotFoundException;
    boolean placeOrder(PlaceOrderDto dto) throws SQLException,ClassNotFoundException;
}
