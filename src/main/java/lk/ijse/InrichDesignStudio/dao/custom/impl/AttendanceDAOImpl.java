package lk.ijse.InrichDesignStudio.dao.custom.impl;

import lk.ijse.InrichDesignStudio.Db.DbConnection;
import lk.ijse.InrichDesignStudio.dao.SQLUtil;
import lk.ijse.InrichDesignStudio.dao.custom.AttendanceDAO;
import lk.ijse.InrichDesignStudio.dto.AttendDto;
import lk.ijse.InrichDesignStudio.entity.Attend;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class AttendanceDAOImpl implements AttendanceDAO {
    @Override
    public ArrayList<Attend> getAll() throws SQLException, ClassNotFoundException {
        return null;
    }

    @Override
    public boolean save(Attend dto) throws SQLException, ClassNotFoundException {
       return SQLUtil.execute("INSERT INTO attandance VALUES(?,?,?,?,?,)",
               dto.getEmployeeId(),dto.getName(),dto.getNic(),dto.getDate(),dto.getStatus());
    }

    @Override
    public boolean update(Attend dto) throws SQLException, ClassNotFoundException {
        return false;
    }

    @Override
    public boolean delete(String id) throws SQLException, ClassNotFoundException {
        return false;
    }

    @Override
    public String generateNewId() throws SQLException, ClassNotFoundException {
        return null;
    }

    @Override
    public boolean exist(String id) throws SQLException, ClassNotFoundException {
        return false;
    }

    @Override
    public boolean exist(Attend dto) throws SQLException, ClassNotFoundException {
        ResultSet rst = SQLUtil.execute("SELECT COUNT(*) FROM attandance WHERE e_id = ? AND attandance_date = ?",dto.getEmployeeId(),dto.getDate());
       return rst.next();
    }

    @Override
    public Attend search(String id) throws SQLException, ClassNotFoundException {
        ResultSet rst = SQLUtil.execute("SELECT * FROM attandance WHERE attandance_date = ?",id);
        rst.next();
        return new Attend(rst.getString(1),rst.getString(2),rst.getString(3), rst.getDate(4).toLocalDate(),rst.getString(5));
    }

   /* @Override
    public boolean existId(Attend dto) throws SQLException {
        return false;
    }*/



    /*@Override
    public boolean countAttendance(ArrayList<AttendDto> adto) throws SQLException {
        return
    }*/
}
