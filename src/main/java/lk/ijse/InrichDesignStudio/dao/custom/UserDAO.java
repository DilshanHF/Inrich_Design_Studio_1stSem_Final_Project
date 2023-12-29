package lk.ijse.InrichDesignStudio.dao.custom;

import lk.ijse.InrichDesignStudio.dao.CrudDAO;
import lk.ijse.InrichDesignStudio.entity.User;

import java.sql.SQLException;

public interface UserDAO extends CrudDAO<User> {

    boolean checkCredentials(String username,String password) throws SQLException;
}
