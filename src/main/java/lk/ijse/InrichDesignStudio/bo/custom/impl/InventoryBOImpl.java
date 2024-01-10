package lk.ijse.InrichDesignStudio.bo.custom.impl;

import lk.ijse.InrichDesignStudio.bo.custom.InventoryBO;
import lk.ijse.InrichDesignStudio.dao.custom.InventoryDAO;
import lk.ijse.InrichDesignStudio.dao.custom.impl.InventoryDAOImpl;
import lk.ijse.InrichDesignStudio.dao.factory.DAOFactory;
import lk.ijse.InrichDesignStudio.dao.factory.DAOTypes;
import lk.ijse.InrichDesignStudio.dto.InventoryDto;
import lk.ijse.InrichDesignStudio.entity.Inventory;

import java.sql.SQLException;
import java.util.ArrayList;

public class InventoryBOImpl implements InventoryBO {

    InventoryDAO inventoryDAO = (InventoryDAO) DAOFactory.getDaoFactory().getDAO(DAOTypes.INVENTORY);
    @Override
    public ArrayList<InventoryDto> getAllInventory() throws SQLException, ClassNotFoundException {
        ArrayList<InventoryDto> inventoryDtos = new ArrayList<>();
        ArrayList<Inventory> inventories = inventoryDAO.getAll();

        for (Inventory inventory : inventories){
            inventoryDtos.add(new InventoryDto(inventory.getOrderId(),inventory.getItemCode(),inventory.getQty(),inventory.getDate()));

        }
        return inventoryDtos;
    }
}
