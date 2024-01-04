package lk.ijse.InrichDesignStudio.dao.custom;

import lk.ijse.InrichDesignStudio.dao.CrudDAO;
import lk.ijse.InrichDesignStudio.dto.AttendDto;
import lk.ijse.InrichDesignStudio.entity.Attend;

import java.sql.SQLException;
import java.util.ArrayList;

public interface AttendanceDAO extends CrudDAO<Attend> {
    boolean exist(Attend dto) throws SQLException,ClassNotFoundException;
}
