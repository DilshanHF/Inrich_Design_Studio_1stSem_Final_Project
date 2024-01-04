package lk.ijse.InrichDesignStudio.bo.custom.impl;

import lk.ijse.InrichDesignStudio.bo.custom.IncomeBO;
import lk.ijse.InrichDesignStudio.bo.custom.PlaceOrderBO;
import lk.ijse.InrichDesignStudio.dao.custom.CustomerDAO;
import lk.ijse.InrichDesignStudio.dao.custom.IncomeDAO;
import lk.ijse.InrichDesignStudio.dao.custom.ItemDAO;
import lk.ijse.InrichDesignStudio.dao.custom.impl.CustomerDAOImpl;
import lk.ijse.InrichDesignStudio.dao.custom.impl.IncomeDAOImpl;
import lk.ijse.InrichDesignStudio.dao.custom.impl.ItemDAOImpl;
import lk.ijse.InrichDesignStudio.dto.CustomerDto;
import lk.ijse.InrichDesignStudio.dto.ItemDto;
import lk.ijse.InrichDesignStudio.entity.Customer;
import lk.ijse.InrichDesignStudio.entity.Item;

import java.sql.SQLException;
import java.util.ArrayList;

public class PlaceOrderBOImpl implements PlaceOrderBO {
    IncomeDAO incomeDAO = new IncomeDAOImpl();
    ItemDAO itemDAO = new ItemDAOImpl();
    CustomerDAO customerDAO = new CustomerDAOImpl();

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
}
