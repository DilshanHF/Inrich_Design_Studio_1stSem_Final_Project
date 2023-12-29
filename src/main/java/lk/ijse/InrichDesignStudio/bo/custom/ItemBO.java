package lk.ijse.InrichDesignStudio.bo.custom;

import lk.ijse.InrichDesignStudio.dto.ItemDto;
import lk.ijse.InrichDesignStudio.entity.Item;

import java.sql.SQLException;
import java.util.ArrayList;

public interface ItemBO {
    ArrayList<ItemDto> getAllItem() throws SQLException, ClassNotFoundException;
    boolean saveItem(ItemDto dto) throws SQLException, ClassNotFoundException;
    boolean updateItem(ItemDto dto) throws SQLException, ClassNotFoundException;
    boolean deleteItem(String id) throws SQLException, ClassNotFoundException;
    boolean existItem(String id) throws SQLException, ClassNotFoundException;
}
