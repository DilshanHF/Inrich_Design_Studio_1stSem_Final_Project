package lk.ijse.InrichDesignStudio.bo.custom;

import lk.ijse.InrichDesignStudio.bo.SuperBO;
import lk.ijse.InrichDesignStudio.dto.InventoryDto;
import lk.ijse.InrichDesignStudio.entity.Inventory;

import java.sql.SQLException;
import java.util.ArrayList;

public interface InventoryBO extends SuperBO {
    ArrayList<InventoryDto> getAllInventory() throws SQLException, ClassNotFoundException;
}
