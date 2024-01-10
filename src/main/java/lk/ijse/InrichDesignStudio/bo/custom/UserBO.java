package lk.ijse.InrichDesignStudio.bo.custom;

import lk.ijse.InrichDesignStudio.bo.SuperBO;
import lk.ijse.InrichDesignStudio.dto.UserDto;
import lk.ijse.InrichDesignStudio.entity.User;

import java.sql.SQLException;

public interface UserBO extends SuperBO {
    boolean existUser(String email) throws SQLException, ClassNotFoundException;
    boolean saveUser(UserDto dto) throws SQLException, ClassNotFoundException;
    boolean checkCredentials(String username, String password) throws SQLException;
}
