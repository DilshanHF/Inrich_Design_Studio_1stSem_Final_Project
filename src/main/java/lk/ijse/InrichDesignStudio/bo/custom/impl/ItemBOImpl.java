package lk.ijse.InrichDesignStudio.bo.custom.impl;

import lk.ijse.InrichDesignStudio.bo.custom.ItemBO;
import lk.ijse.InrichDesignStudio.dao.custom.ItemDAO;
import lk.ijse.InrichDesignStudio.dao.custom.impl.ItemDAOImpl;
import lk.ijse.InrichDesignStudio.dao.factory.DAOFactory;
import lk.ijse.InrichDesignStudio.dao.factory.DAOTypes;
import lk.ijse.InrichDesignStudio.dto.ItemDto;
import lk.ijse.InrichDesignStudio.entity.Item;

import java.sql.SQLException;
import java.util.ArrayList;

public class ItemBOImpl implements ItemBO {
    ItemDAO itemDAO = (ItemDAO) DAOFactory.getDaoFactory().getDAO(DAOTypes.ITEM);

    @Override
    public ArrayList<ItemDto> getAllItem() throws SQLException, ClassNotFoundException {
        ArrayList<ItemDto>itemDtos = new ArrayList<>();
        ArrayList<Item> items = itemDAO.getAll();

        for (Item item : items){
            itemDtos.add(new ItemDto(item.getId(),item.getName(),item.getType(),item.getAmount()));
        }
        return itemDtos;
    }

    @Override
    public boolean saveItem(ItemDto dto) throws SQLException, ClassNotFoundException {
        return itemDAO.save(new Item(dto.getId(),dto.getName(),dto.getType(), dto.getAmount()));
    }

    @Override
    public boolean updateItem(ItemDto dto) throws SQLException, ClassNotFoundException {
        return itemDAO.update(new Item(dto.getId(), dto.getName(), dto.getType(), dto.getAmount()));
    }

    @Override
    public boolean deleteItem(String id) throws SQLException, ClassNotFoundException {
        return itemDAO.delete(id);
    }

    @Override
    public boolean existItem(String id) throws SQLException, ClassNotFoundException {
        return itemDAO.exist(id);
    }
}
