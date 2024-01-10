package lk.ijse.InrichDesignStudio.bo.custom.impl;

import lk.ijse.InrichDesignStudio.bo.custom.UserBO;
import lk.ijse.InrichDesignStudio.dao.custom.UserDAO;
import lk.ijse.InrichDesignStudio.dao.custom.impl.UserDAOimpl;
import lk.ijse.InrichDesignStudio.dao.factory.DAOFactory;
import lk.ijse.InrichDesignStudio.dao.factory.DAOTypes;
import lk.ijse.InrichDesignStudio.dto.UserDto;
import lk.ijse.InrichDesignStudio.entity.User;

import java.sql.SQLException;

public class UserBOImpl implements UserBO {

    UserDAO userDAO = (UserDAO) DAOFactory.getDaoFactory().getDAO(DAOTypes.USER);
    @Override
    public boolean existUser(String email) throws SQLException, ClassNotFoundException {
        return userDAO.exist(email);
    }

    @Override
    public boolean saveUser(UserDto dto) throws SQLException, ClassNotFoundException {
        return userDAO.save(new User(dto.getEmail(), dto.getFirst_name(), dto.getLast_name(), dto.getPassword()));
    }

    @Override
    public boolean checkCredentials(String username, String password) throws SQLException {
        return userDAO.checkCredentials(username,password);
    }
}
