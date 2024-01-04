package lk.ijse.InrichDesignStudio.bo.custom;

import lk.ijse.InrichDesignStudio.dto.AttendDto;
import lk.ijse.InrichDesignStudio.entity.Attend;

import java.sql.SQLException;
import java.util.ArrayList;

public interface AttendanceBO {
    AttendDto searchAttendance(String id) throws SQLException, ClassNotFoundException;
    boolean saveAttendance(ArrayList<AttendDto> attendance) throws SQLException, ClassNotFoundException;
    boolean existAttendance(ArrayList<AttendDto> adto) throws SQLException, ClassNotFoundException;
}
