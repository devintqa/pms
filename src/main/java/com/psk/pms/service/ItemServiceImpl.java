package com.psk.pms.service;

import com.psk.pms.dao.ItemDAO;
import com.psk.pms.model.DescItemDetail;
import com.psk.pms.model.Item;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * Created by prakashbhanu57 on 7/6/2015.
 */
public class ItemServiceImpl implements ItemService {

    @Autowired
    ItemDAO itemDAO;

    @SuppressWarnings("unused")
    private static final Logger LOGGER = Logger.getLogger(ItemService.class);

    public boolean createEditItem(Item item){
        boolean isInsertSuccessful = itemDAO.saveItem(item);
        return isInsertSuccessful;
    }

    public boolean isItemAlreadyExisting(String itemName){
        boolean isAvailable = false;
        isAvailable = itemDAO.isItemAlreadyExisting(itemName);
        return isAvailable;
    }


    @Override
    public Map<String, String> getDescItemCodes(String itemCode) {
        Map<String, String> itemCodes = itemDAO.getDescItemCodes(itemCode);
        return itemCodes;
    }

    @Override
    public List<DescItemDetail.ItemDetail> getProjectData(Integer projId){
        List<DescItemDetail.ItemDetail> itemDetailList = itemDAO.getProjectData(projId);
        List<DescItemDetail.ItemDetail> finalItemDetailList = new ArrayList<DescItemDetail.ItemDetail>();
        if(itemDetailList.size() > 0){
            Set<String> itemNames = itemDAO.fetchItemNames();
            for(String itemName : itemNames){
                DescItemDetail.ItemDetail item = new DescItemDetail.ItemDetail();
                Double itemQty = 0.0;
                Double itemCost = 0.0;
                for(DescItemDetail.ItemDetail itemDetail : itemDetailList){
                    if(itemName.equalsIgnoreCase(itemDetail.getItemName())){
                        item.setItemName(itemDetail.getItemName());
                        item.setItemPrice(itemDetail.getItemPrice());
                        item.setItemUnit(itemDetail.getItemUnit());
                        itemQty = itemQty + Double.valueOf(itemDetail.getItemQty());
                        itemCost = itemCost + Double.valueOf(itemDetail.getItemCost());
                    }
                }
                if(item.getItemName() != null){
                    item.setItemQty(String.valueOf(itemQty));
                    item.setItemCost(String.valueOf(itemCost));
                    finalItemDetailList.add(item);
                }
            }
        }
        return finalItemDetailList;
    }

    @Override
    public Set<String> fetchItemNames() {
        Set<String> itemNames = itemDAO.fetchItemNames();
        return itemNames;
    }


    public DescItemDetail getDataDescription(final DescItemDetail descItemDetail){
        return itemDAO.getDataDescription(descItemDetail);
    }

    @Override
    public void deleteItemByDescriptionItemId(Integer projectDescriptionItemId) {
        itemDAO.deleteItemByProjectDescItemId(projectDescriptionItemId);
    }



    public boolean insertDataDescription(DescItemDetail descItemDetail){
        boolean isInsertSuccessful = false;
        if(descItemDetail.getItemDetail() != null){
            isInsertSuccessful = itemDAO.insertDataDescription(descItemDetail);
        }
        return isInsertSuccessful;
    }

    public List<String> fetchItemTypes() {
        List<String> itemTypes = itemDAO.fetchItemTypes();
        return itemTypes;
    }

    public List<String> fetchUniqueItemUnits() {
        List<String> itemUnits = itemDAO.fetchUniqueItemUnits();
        return itemUnits;
    }

}
