package lk.ijse.InrichDesignStudio.Model;

import lk.ijse.InrichDesignStudio.Db.DbConnection;
import lk.ijse.InrichDesignStudio.dto.employeeDto;
import lk.ijse.InrichDesignStudio.dto.expenseDto;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class expensesModel {

    public boolean exitExpensesId(String id) throws SQLException {
        Connection connection = DbConnection.getInstance().getConnection();

        String sql = "SELECT expense_id FROM expense WHERE expense_id = ?";
        PreparedStatement pstm = connection.prepareStatement(sql);
        pstm.setString(1, id);

        ResultSet resultSet =pstm.executeQuery();
        return resultSet.next();

    }

    public boolean saveExpense(expenseDto dto) throws SQLException {
        Connection connection = DbConnection.getInstance().getConnection();

        String sql = "INSERT INTO expense VALUES(?,?,?,?,?)";
        PreparedStatement pstm = connection.prepareStatement(sql);

        pstm.setString(1,dto.getId());
        pstm.setString(2, String.valueOf(dto.getDate()));
        pstm.setString(3,dto.getDes());
        pstm.setString(4,dto.getType());
        pstm.setString(5, String.valueOf(dto.getAmount()));

        return pstm.executeUpdate()>0;
    }

    public List<expenseDto> getAllExpenses() throws SQLException {
        Connection connection = DbConnection.getInstance().getConnection();

        String sql = "SELECT * FROM expense";
        PreparedStatement pstm = connection.prepareStatement(sql);
        ResultSet resultSet = pstm.executeQuery();

        ArrayList<expenseDto> dtoList = new ArrayList<>();

        while(resultSet.next()) {
            dtoList.add(
                    new expenseDto(
                            resultSet.getString(1),
                            resultSet.getDate(2).toLocalDate(),
                            resultSet.getString(3),
                            resultSet.getString(4),
                            resultSet.getDouble(5)
                    )
            );
        }
        return dtoList;
    }

    public boolean deleteExpense(String id) throws SQLException {
        Connection connection = DbConnection.getInstance().getConnection();

        String sql = "DELETE FROM expense WHERE expense_id = ?";
        PreparedStatement pstm =connection.prepareStatement(sql);
        pstm.setString(1,id);
        return pstm.executeUpdate()>0;
    }

    public boolean saveUpdate(expenseDto dto) throws SQLException {
        Connection connection = DbConnection.getInstance().getConnection();

        String sql = "UPDATE expense SET date = ?, descripton = ?, type = ? ,amount = ? WHERE e_id = ?";
        PreparedStatement pstm = connection.prepareStatement(sql);

        pstm.setString(1, String.valueOf(dto.getDate()));
        pstm.setString(2, dto.getDes());
        pstm.setString(3, dto.getType());
        pstm.setString(4, String.valueOf(dto.getAmount()));
        pstm.setString(5, dto.getId());

        return pstm.executeUpdate() > 0;
    }
}
