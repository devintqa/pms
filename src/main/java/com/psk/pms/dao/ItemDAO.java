package com.psk.pms.dao;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;
import java.util.Set;

import com.psk.pms.model.*;
import com.psk.pms.model.DescItemDetail.ItemDetail;

/**
 * Created by prakashbhanu57 on 7/6/2015.
 */
public interface ItemDAO {

    boolean saveItem(Item item);

    Set<String> fetchItemNames();

    public Map<String, String> fetchItemInfo();

    boolean isItemAlreadyExisting(String itemName);

    List<ItemDetail> getDescItemNames(Map<String, Object> request);

	boolean insertProjectDescriptionItems(DescItemDetail descItemDetail);
	
	boolean insertBaseDescriptionItems(DescItemDetail descItemDetail);

	List<ItemDetail> searchItemName(String itemCode, String itemType);

    DescItemDetail getProjectDescriptionItems(final DescItemDetail descItemDetail);
    
    public DescItemDetail getPskFieldDescriptionItems(final String projDescId);

    List<DescItemDetail.ItemDetail> getProjectData(ProjectConfiguration projectConfiguration, boolean isEditSubProject, String descriptionType);

    void deleteItemByProjectId(Integer projectId);

    void deleteItemBySubProjectId(Integer subProjectId);

    void deleteItemByProjectDescriptionId(String projectDescId, String descType);

    void deleteItemByProjectDescItemId(Integer itemId, String descType);

    List<String> fetchUniqueItemUnits();

	ProjectConfiguration getProjectItemConfiguration(ProjectConfiguration projectConfiguration, boolean isEditSubProject);

	boolean configureItemPrice(ProjectConfiguration projItemConfiguration);

    List<String> getItemNames();

	List<ProjectItemDescription> getProjectItemDescription(ProjectConfiguration projectConfiguration, ViewDetail viewDetail);

	DescItemDetail getBaseDescription(DescItemDetail descItemDetail);

    List<ItemDetail> getBaseItemNames(Map<String, Object> request);

    void saveItemRateDescriptions(List<ItemRateDescription> itemRateDescriptions);

    List<String> getItemNames(String itemType, String projectId);
    
    List<com.psk.pms.model.ProjectConfiguration.ItemDetail> getMissingProjectDescriptionItems(Integer projectId);

    boolean isItemPresent();

    void deleteItemDescription();

    List<LeadDetailConfiguration.LeadDetail> getLeadDetails(String projectId,String subProjectId);

    void saveLeadDetails(LeadDetailConfiguration leadDetailConfiguration);

    void deleteBaseDescriptionById(Integer baseDescId);

    void updateBaseDescTotalItemCost(long totalItemsCost,Integer baseDescId);

    List<ItemDetailDto> getAllItemsConfiguredToProject(Integer projectId, String descriptionType);

    void updatePriceAndCostOfProjectDescItems(List<ItemDetailDto> itemDetailDtos,String descriptiontype);

	List<com.psk.pms.model.IndentDesc.ItemDetail> getIndentItemForRequestView(String projId);

	List<com.psk.pms.model.IndentDesc.ItemDetail> getIndentItemForRequest(String indentId);

    Map<String ,BigDecimal> getItemPrices(List<String> itemNames);

    List<Item> getItems();

    void updateItem(Item item);

    void deleteItem(String itemName, String itemType);
}
