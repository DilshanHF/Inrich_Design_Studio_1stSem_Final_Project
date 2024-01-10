package lk.ijse.InrichDesignStudio.dao.custom.impl;

import lk.ijse.InrichDesignStudio.dao.SQLUtil;
import lk.ijse.InrichDesignStudio.dao.custom.CustomerDAO;
import lk.ijse.InrichDesignStudio.dto.CustomerDto;
import lk.ijse.InrichDesignStudio.entity.Customer;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class CustomerDAOImpl implements CustomerDAO {

    @Override
    public ArrayList<Customer> getAll() throws SQLException, ClassNotFoundException {
        ResultSet rst = SQLUtil.execute("SELECT * FROM customer");
        ArrayList<Customer> getAllCustomer = new ArrayList<>();
        while (rst.next()){
            Customer entity=new Customer(rst.getString(1),
                    rst.getString(2), rst.getString(3),rst.getString(4),rst.getString(5));
            getAllCustomer.add(entity);
        }
        return getAllCustomer;
    }

    @Override
    public boolean save(Customer dto) throws SQLException, ClassNotFoundException {
        return SQLUtil.execute("INSERT INTO customer (id,name, address,tel,email) VALUES (?,?,?,?,?)",
                dto.getId(),dto.getName(),dto.getAddress(),dto.getTel(),dto.getEmail());
    }

    @Override
    public boolean update(Customer dto) throws SQLException, ClassNotFoundException {
        return SQLUtil.execute("UPDATE customer SET customer_name=?, address=?,c_tel =?,c_email=? WHERE customer_id=?",
                dto.getName(),dto.getAddress(),dto.getTel(),dto.getEmail(),dto.getId());
    }

    @Override
    public boolean delete(String id) throws SQLException, ClassNotFoundException {
        return SQLUtil.execute("DELETE FROM customer WHERE customer_id=?",id);
    }

    @Override
    public String generateNewId() throws SQLException, ClassNotFoundException {
        return null;
    }

    @Override
    public boolean exist(String id) throws SQLException, ClassNotFoundException {
        ResultSet rst = SQLUtil.execute("SELECT id FROM customer WHERE customer_id=?",id);
        return rst.next();
    }

    @Override
    public Customer search(String id) throws SQLException, ClassNotFoundException {
        ResultSet rst = SQLUtil.execute("SELECT * FROM customer WHERE customer_id=?",id);
        rst.next();
        return new Customer(id + "", rst.getString(2), rst.getString(3),rst.getString(4),rst.getString(5));
    }

    @Override
    public String getTotalCustomers() throws SQLException, ClassNotFoundException {
        ResultSet rst = SQLUtil.execute("SELECT count(customer_id) FROM customer");

        if(rst.next()){
            return rst.getString(1);
        }
        return null;
    }
}
