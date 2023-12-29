package lk.ijse.InrichDesignStudio.bo.custom.impl;

import lk.ijse.InrichDesignStudio.bo.custom.CustomerBO;
import lk.ijse.InrichDesignStudio.dao.custom.CustomerDAO;
import lk.ijse.InrichDesignStudio.dao.custom.impl.CustomerDAOImpl;
import lk.ijse.InrichDesignStudio.dto.CustomerDto;
import lk.ijse.InrichDesignStudio.entity.Customer;

import java.sql.SQLException;
import java.util.ArrayList;



public class CustomerBOImpl implements CustomerBO {

    CustomerDAO customerDAO = new CustomerDAOImpl();
    @Override
    public ArrayList<CustomerDto> getAllCustomer() throws SQLException, ClassNotFoundException {
        ArrayList<CustomerDto> customerDtos = new ArrayList<>();
        ArrayList<Customer> customers = customerDAO.getAll();

        for(Customer customer : customers){
            customerDtos.add(new CustomerDto(customer.getId(),customer.getName(),customer.getAddress(), customer.getTel(),customer.getEmail()));
        }
        return customerDtos;
    }

    @Override
    public boolean existCustomer(String id) throws SQLException, ClassNotFoundException {
        return customerDAO.exist(id);
    }

    @Override
    public boolean saveCustomer(CustomerDto dto) throws SQLException, ClassNotFoundException {
        return customerDAO.save(new Customer(dto.getId(), dto.getName(), dto.getAddress(), dto.getTel(), dto.getEmail()));
    }

    @Override
    public boolean updateCustomer(CustomerDto dto) throws SQLException, ClassNotFoundException {
        return customerDAO.update(new Customer(dto.getId(), dto.getName(), dto.getAddress(), dto.getTel(), dto.getEmail()));

    }

    @Override
    public boolean deleteCustomer(String id) throws SQLException, ClassNotFoundException {
        return customerDAO.delete(id);
    }

    @Override
    public CustomerDto searchCustomer(String id) throws SQLException, ClassNotFoundException {
        Customer customer = customerDAO.search(id);
        return  new CustomerDto(customer.getId(), customer.getName(), customer.getAddress(), customer.getTel(), customer.getEmail());

    }
}
