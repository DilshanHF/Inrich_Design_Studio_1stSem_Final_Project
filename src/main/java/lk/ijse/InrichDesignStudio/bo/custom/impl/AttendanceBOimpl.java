package lk.ijse.InrichDesignStudio.bo.custom.impl;

import lk.ijse.InrichDesignStudio.bo.custom.AttendanceBO;
import lk.ijse.InrichDesignStudio.dao.custom.AttendanceDAO;
import lk.ijse.InrichDesignStudio.dao.custom.impl.AttendanceDAOImpl;
import lk.ijse.InrichDesignStudio.dao.factory.DAOFactory;
import lk.ijse.InrichDesignStudio.dao.factory.DAOTypes;
import lk.ijse.InrichDesignStudio.dto.AttendDto;
import lk.ijse.InrichDesignStudio.entity.Attend;

import java.sql.SQLException;
import java.util.ArrayList;

public class AttendanceBOimpl implements AttendanceBO {
    AttendanceDAO attendanceDAO = (AttendanceDAO) DAOFactory.getDaoFactory().getDAO(DAOTypes.ATTENDANCE);
    @Override
    public AttendDto searchAttendance(String id) throws SQLException, ClassNotFoundException {
        Attend attend = attendanceDAO.search(id);
        return new AttendDto(attend.getEmployeeId(),attend.getName(),attend.getNic(),attend.getDate(),attend.getStatus());
    }

    @Override
    public boolean saveAttendance(ArrayList<AttendDto> attendance) throws SQLException, ClassNotFoundException {
        for(AttendDto adto : attendance){
            attendanceDAO.save(new Attend(adto.getEmployeeId(),adto.getName(),adto.getNic(),adto.getDate(),adto.getStatus()));
        }
        return true;
    }

    @Override
    public boolean existAttendance(ArrayList<AttendDto> adto) throws SQLException, ClassNotFoundException {
        for (AttendDto dto : adto){
            attendanceDAO.exist(new Attend(dto.getEmployeeId(),dto.getName(),dto.getNic(),dto.getDate(),dto.getStatus()));
        }
        return  true;
    }
}
