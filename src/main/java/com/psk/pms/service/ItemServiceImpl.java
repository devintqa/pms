package com.psk.pms.service;

import com.psk.pms.Constants;
import com.psk.pms.dao.ItemDAO;
import com.psk.pms.dao.ProjectDAO;
import com.psk.pms.dao.ProjectDescriptionDAO;
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

    @Autowired
    ProjectDAO projectDAO;

    @Autowired
    ProjectDescriptionDAO projectDescriptionDAO;

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
				BigDecimal itemQty =  new BigDecimal("0");
				BigDecimal itemCost = new BigDecimal("0");
				BigDecimal temp;
				for (DescItemDetail.ItemDetail itemDetail : itemDetailList) {
					if (itemName.getKey().equalsIgnoreCase(
							itemDetail.getItemName())) {
						item.setItemName(itemDetail.getItemName());
						item.setItemPrice(itemDetail.getItemPrice());
						item.setItemUnit(itemDetail.getItemUnit());
						item.setItemType(itemName.getValue());
						temp = new BigDecimal(itemDetail.getItemQty()).multiply(new BigDecimal(itemDetail.getQuantity()));
						itemQty = itemQty.add(temp);
						itemCost = itemCost.add(temp.multiply(new BigDecimal(itemDetail.getItemPrice())));
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

    public boolean saveBaseDescriptionItems(DescItemDetail descItemDetail) {
        boolean isInsertSuccessful = false;
        if (descItemDetail.getItemDetail() != null) {
            itemDAO.deleteBaseDescriptionById(descItemDetail.getBaseDescId());
            isInsertSuccessful = itemDAO.insertBaseDescriptionItems(descItemDetail);
            if(Constants.GOVERNMENT.equalsIgnoreCase(descItemDetail.getDescType())){
                itemDAO.updateBaseDescTotalItemCost(getDescriptionItemsTotalCost(descItemDetail.getItemDetail()),descItemDetail.getBaseDescId());
            }
        }
        return isInsertSuccessful;
    }

    private long getDescriptionItemsTotalCost(List<ItemDetail> itemDetails) {
        long sumItemCost = 0;
        for (ItemDetail itemDetail : itemDetails) {
            long itemPrice = Double.valueOf(itemDetail.getItemPrice()).longValue();
            long itemQty = Double.valueOf(itemDetail.getItemQty()).longValue();
            long itemCost = itemPrice * itemQty;
            sumItemCost = sumItemCost + itemCost;
        }
        return sumItemCost;
    }

    public DescItemDetail getProjectDescriptionItems(final DescItemDetail descItemDetail) {
        return itemDAO.getProjectDescriptionItems(descItemDetail);
    }

    @Override
    public void deleteItemByDescriptionItemId(Integer projectDescriptionItemId) {
        itemDAO.deleteItemByProjectDescItemId(projectDescriptionItemId);
    }

/*    public boolean insertDataDescription(DescItemDetail descItemDetail) {
        boolean isInsertSuccessful = false;
        if (descItemDetail.getItemDetail() != null) {
            isInsertSuccessful = itemDAO.insertProjectDescriptionItems(descItemDetail);
        }
        return isInsertSuccessful;
    }*/

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
        LOGGER.info("Saving changed item prices into pskPriceDetail");
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


    @Override
    public LeadDetailConfiguration getLeadDetails(String projectId, String subProjectId) {
        LeadDetailConfiguration leadDetailConfiguration = new LeadDetailConfiguration();
        leadDetailConfiguration.setProjectId(Integer.valueOf(projectId));
        leadDetailConfiguration.setSubProjectId(Integer.valueOf(subProjectId));
        leadDetailConfiguration.setLeadDetails(itemDAO.getLeadDetails(projectId, subProjectId));
        return leadDetailConfiguration;
    }

    public void saveLeadDetails(LeadDetailConfiguration leadDetailConfiguration){
        itemDAO.saveLeadDetails(leadDetailConfiguration);
    }
    
    public DescItemDetail getPskFieldDescriptionItems(final String projDescId) {
    	return itemDAO.getPskFieldDescriptionItems(projDescId);
    }

    @Override
    public void updateMaterialPriceWithLeadDetailsPrice(List<ItemDetail> itemDetailList,String projectId,String subProjectId) {
        LOGGER.info("Loading item data ,Updating item price for materials with lead detail price");
        List<LeadDetailConfiguration.LeadDetail> leadDetailList = itemDAO.getLeadDetails(projectId,subProjectId);
        for(ItemDetail itemDetail:itemDetailList) {
            for(LeadDetailConfiguration.LeadDetail leadDetail : leadDetailList){
                    if(itemDetail.getItemName().equalsIgnoreCase(leadDetail.getMaterial())) {
                        itemDetail.setItemPrice(leadDetail.getTotal());
                    }
            }
        }
    }

    @Override
    public void applyWorkoutPercentage(List<ItemDetail> itemDetails, BigDecimal workOutPercentage) {
        LOGGER.info("Loading item Data, Updating labour and other charges with calculated workout percentage");
        for (ItemDetail itemDetail : itemDetails) {
            if (LABOUR.equalsIgnoreCase(itemDetail.getItemType()) || OTHER.equalsIgnoreCase(itemDetail.getItemType())) {
                BigDecimal itemPrice = new BigDecimal(itemDetail.getItemPrice());
                BigDecimal amountAfterPercentage = (itemPrice.multiply(workOutPercentage)).divide(new BigDecimal("100"));
                itemDetail.setItemPrice(itemPrice.add(amountAfterPercentage).toString());
            }
        }
    }

    public void updatePriceAndCostForConfiguredItems(Integer projectId, Map<String, BigDecimal> itemNamePriceMap,
                                                     Map<Integer, BigDecimal> descIdItemCostMap) {
        List<ItemDetailDto> itemDetailDtos = itemDAO.getAllItemsConfiguredToProject(projectId, Constants.PSK);
        applyPriceAndCostForAllItems(itemDetailDtos, itemNamePriceMap, descIdItemCostMap);
        itemDAO.updateProjectDescItems(itemDetailDtos);
    }

    private void applyPriceAndCostForAllItems(List<ItemDetailDto> itemDetailDtos, Map<String, BigDecimal> itemDetails,
                                              Map<Integer, BigDecimal> descIdItemCostMap) {
        LOGGER.info("Applying new price and recalculated cost");
        String itemName;
        Integer itemQunatity;
        BigDecimal itemCost;
        BigDecimal itemPrice;
        for (ItemDetailDto itemDetailDto : itemDetailDtos) {
            itemName = itemDetailDto.getItemName();
            itemQunatity = itemDetailDto.getItemQuantity();
            if (itemDetails.containsKey(itemName)) {
                itemPrice = itemDetails.get(itemName);
                itemDetailDto.setItemPrice(itemPrice);
                itemCost = new BigDecimal(itemQunatity).multiply(itemPrice);
                itemDetailDto.setItemCost(itemCost);
            } else {
                itemCost = itemDetailDto.getItemCost();
            }
            calculateTotalOfItemCostPerDescription(itemCost, itemDetailDto.getProjectDescId(), descIdItemCostMap);
        }
    }

    private void calculateTotalOfItemCostPerDescription(BigDecimal itemCost, Integer projectDescId, Map<Integer, BigDecimal> descIdItemCostMap) {
        if (descIdItemCostMap.isEmpty()) {
            descIdItemCostMap.put(projectDescId, itemCost);
        } else {
            if (descIdItemCostMap.containsKey(projectDescId)) {
                BigDecimal previousCost = descIdItemCostMap.get(projectDescId);
                descIdItemCostMap.put(projectDescId, previousCost.add(itemCost));
            } else {
                descIdItemCostMap.put(projectDescId, itemCost);
            }
        }
    }

    @Override
    public void updateProjectDescriptionWithRecalculatedCost(Integer projId, Map<Integer, BigDecimal> descIdItemCostMap) {
        List<ProjDescDetail> projDescDetails = projectDescriptionDAO.getProjectDescDetailList(descIdItemCostMap.keySet(),Constants.PSK);
        LOGGER.info("Applying new pricePerQuantity and totalCost ");
        for (ProjDescDetail projDescDetail : projDescDetails) {
            if(descIdItemCostMap.containsKey(projDescDetail.getProjDescId())){
                BigDecimal descriptionQty = new BigDecimal(projDescDetail.getQuantity());
                BigDecimal descPrice = descIdItemCostMap.get(projDescDetail.getProjDescId());
                projDescDetail.setPricePerQuantity(descPrice.toString());
                BigDecimal totalCost = descPrice.multiply(descriptionQty);
                projDescDetail.setTotalCost(totalCost.toString());
            }
        }
        projectDescriptionDAO.updateProjectDescriptions(projDescDetails);
    }

	@Override
	public List<com.psk.pms.model.IndentDesc.ItemDetail> getIndentItemForRequest(String indentId) {
		return itemDAO.getIndentItemForRequest(indentId);
	}
}
