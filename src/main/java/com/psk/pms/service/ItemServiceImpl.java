package com.psk.pms.service;

import com.psk.pms.dao.ItemDAO;
import com.psk.pms.model.*;
import com.psk.pms.model.DescItemDetail.ItemDetail;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;

import static com.psk.pms.Constants.LABOUR;
import static com.psk.pms.Constants.OTHER;

/**
 * Created by prakashbhanu57 on 7/6/2015.
 */
public class ItemServiceImpl implements ItemService {

    @Autowired
    ItemDAO itemDAO;

    @SuppressWarnings("unused")
    private static final Logger LOGGER = Logger.getLogger(ItemService.class);

    public boolean createEditItem(Item item) {
        boolean isInsertSuccessful = itemDAO.saveItem(item);
        return isInsertSuccessful;
    }

    public boolean isItemAlreadyExisting(String itemName) {
        boolean isAvailable = false;
        isAvailable = itemDAO.isItemAlreadyExisting(itemName);
        return isAvailable;
    }

    @Override
    public List<ItemDetail> searchItemName(String itemName, String itemType) {
        List<ItemDetail> itemsDetail = itemDAO.searchItemName(itemName,
                itemType);
        return itemsDetail;
    }

	@Override
	public List<DescItemDetail.ItemDetail> getProjectData(ProjectConfiguration projectConfiguration, boolean isEditSubProject, String descType) {
		List<DescItemDetail.ItemDetail> itemDetailList = itemDAO.getProjectData(projectConfiguration,isEditSubProject,descType);
		List<DescItemDetail.ItemDetail> finalItemDetailList = new ArrayList<DescItemDetail.ItemDetail>();
		if (itemDetailList.size() > 0) {
			Map<String, String> itemNames = itemDAO.fetchItemInfo();
			for (Map.Entry<String, String> itemName : itemNames.entrySet()) {
				DescItemDetail.ItemDetail item = new DescItemDetail.ItemDetail();
				Double itemQty = 0.0;
				Double itemCost = 0.0;
				for (DescItemDetail.ItemDetail itemDetail : itemDetailList) {
					if (itemName.getKey().equalsIgnoreCase(
							itemDetail.getItemName())) {
						item.setItemName(itemDetail.getItemName());
						item.setItemPrice(itemDetail.getItemPrice());
						item.setItemUnit(itemDetail.getItemUnit());
						item.setItemType(itemName.getValue());
						itemQty = itemQty + Double.valueOf(itemDetail.getItemQty());
						itemCost = itemCost + Double.valueOf(itemDetail.getItemCost());
					}
				}
				if (item.getItemName() != null) {
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


    @Override
    public List<String> getItemNames(String itemType, String projectId) {
        return itemDAO.getItemNames(itemType, projectId);
    }

    public DescItemDetail getBaseDescription(final DescItemDetail descItemDetail) {
        return itemDAO.getBaseDescription(descItemDetail);
    }

    public boolean insertProjectDescriptionItems(DescItemDetail descItemDetail) {
        boolean isInsertSuccessful = false;
        if (descItemDetail.getItemDetail() != null) {
            isInsertSuccessful = itemDAO.insertProjectDescriptionItems(descItemDetail);
        }
        return isInsertSuccessful;
    }

    public boolean insertBaseDescriptionItems(DescItemDetail descItemDetail) {
        boolean isInsertSuccessful = false;
        if (descItemDetail.getItemDetail() != null) {
            isInsertSuccessful = itemDAO.insertBaseDescriptionItems(descItemDetail);
        }
        return isInsertSuccessful;
    }

    public DescItemDetail getProjectDescriptionItems(final DescItemDetail descItemDetail) {
        return itemDAO.getProjectDescriptionItems(descItemDetail);
    }

    @Override
    public void deleteItemByDescriptionItemId(Integer projectDescriptionItemId) {
        itemDAO.deleteItemByProjectDescItemId(projectDescriptionItemId);
    }

    public boolean insertDataDescription(DescItemDetail descItemDetail) {
        boolean isInsertSuccessful = false;
        if (descItemDetail.getItemDetail() != null) {
            isInsertSuccessful = itemDAO.insertProjectDescriptionItems(descItemDetail);
        }
        return isInsertSuccessful;
    }

    @Override
    public List<ItemDetail> getDescItemNames(Map<String, Object> request) {
        List<ItemDetail> itemsDetail = itemDAO.getDescItemNames(request);
        return itemsDetail;
    }

    public List<String> fetchUniqueItemUnits() {
        List<String> itemUnits = itemDAO.fetchUniqueItemUnits();
        return itemUnits;
    }

    @Override
    public boolean configureItemPrice(
            ProjectConfiguration projectItemConfiguration) {
        boolean isInsertSuccessful = false;
        if (projectItemConfiguration.getItemDetail() != null) {
            isInsertSuccessful = itemDAO.configureItemPrice(projectItemConfiguration);
        }
        return isInsertSuccessful;
    }

    @Override
    public ProjectConfiguration getProjectItemConfiguration(
            ProjectConfiguration projectConfiguration, boolean isEditSubProject) {
        return itemDAO.getProjectItemConfiguration(projectConfiguration,
                isEditSubProject);
    }

    @Override
    public List<ItemDetail> getBaseItemNames(Map<String, Object> request) {
        List<ItemDetail> itemsDetails = itemDAO.getBaseItemNames(request);
        String itemType = request.containsKey("itemType")?request.get("itemType").toString():null;
        for (ItemDetail itemDetail : itemsDetails) {
            if (LABOUR.equalsIgnoreCase(itemType) || OTHER.equalsIgnoreCase(itemType)) {
                String itemPrice = itemDetail.getItemPrice();
                BigDecimal tenPercentOfAmount = new BigDecimal(itemPrice).multiply(new BigDecimal("0.1"));
                itemDetail.setItemPrice(new BigDecimal(itemPrice).add(tenPercentOfAmount).toString());
            }
        }
        return itemsDetails;
    }

    @Override
    public List<String> getItemNames() {
        return itemDAO.getItemNames();
    }

    @Override
    public List<ProjectItemDescription> getProjectItemDescription(
    		ProjectConfiguration projectConfiguration, ViewDetail viewDetail) {
        return itemDAO.getProjectItemDescription(projectConfiguration, viewDetail);
    }

    @Override
    public List<com.psk.pms.model.ProjectConfiguration.ItemDetail> getMissingProjectDescriptionItems(Integer projectId) {
        List<com.psk.pms.model.ProjectConfiguration.ItemDetail> itemsDetail = itemDAO.getMissingProjectDescriptionItems(projectId);
        return itemsDetail;
    }

    @Override
    public boolean isItemDescriptionPresent() {
       return itemDAO.isItemPresent();
    }
}
