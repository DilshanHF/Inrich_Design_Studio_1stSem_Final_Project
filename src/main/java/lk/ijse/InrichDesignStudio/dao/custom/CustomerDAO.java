package lk.ijse.InrichDesignStudio.dao.custom;

import lk.ijse.InrichDesignStudio.dao.CrudDAO;
import lk.ijse.InrichDesignStudio.dto.CustomerDto;
import lk.ijse.InrichDesignStudio.entity.Customer;

import java.sql.SQLException;

public interface CustomerDAO extends CrudDAO<Customer> {
    String getTotalCustomers() throws SQLException,ClassNotFoundException;
}
