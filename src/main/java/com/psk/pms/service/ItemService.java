package com.psk.pms.service;

import com.psk.pms.model.*;
import com.psk.pms.model.DescItemDetail.ItemDetail;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * Created by prakashbhanu57 on 7/6/2015.
 */
public interface ItemService {

    boolean createEditItem(Item item);

    boolean isItemAlreadyExisting(String itemName);

    List<ItemDetail> getDescItemNames(Map<String, Object> request);

    List<ItemDetail> searchItemName(String itemName, String itemType);

    Set<String> fetchItemNames();

    List<DescItemDetail.ItemDetail> getProjectData(ProjectConfiguration projectConfiguration, boolean isEditSubProject, String descType);

    boolean insertProjectDescriptionItems(DescItemDetail descItemDetail);

    boolean saveBaseDescriptionItems(DescItemDetail descItemDetail);

    //boolean insertDataDescription(DescItemDetail descItemDetail);

    DescItemDetail getProjectDescriptionItems(final DescItemDetail descItemDetail);
    
    DescItemDetail getPskFieldDescriptionItems(final String projDescId);

    void deleteItemByDescriptionItemId(Integer subProjectid, String descriptionType);

    List<String> fetchUniqueItemUnits();

    boolean configureItemPrice(ProjectConfiguration projectItemConfiguration);

    ProjectConfiguration getProjectItemConfiguration(ProjectConfiguration projectConfiguration, boolean isEditSubProject);

    List<String> getItemNames();

    List<ItemDetail> getBaseItemNames(Map<String, Object> request);

    DescItemDetail getBaseDescription(DescItemDetail descItemDetail);

	List<ProjectItemDescription> getProjectItemDescription(ProjectConfiguration projectConfiguration, ViewDetail viewDetail);

    List<String> getItemNames(String itemType, String projectId);

    List<com.psk.pms.model.ProjectConfiguration.ItemDetail> getMissingProjectDescriptionItems(Integer projectId);

    boolean isItemDescriptionPresent();

    LeadDetailConfiguration getLeadDetails(String projectId, String subProjectId);

    void saveLeadDetails(LeadDetailConfiguration leadDetailConfiguration);

    void updateMaterialPriceWithLeadDetailsPrice(List<ItemDetail> itemDetail, String projId, String subProjId);

    void applyWorkoutPercentage(List<ItemDetail> itemDetails, BigDecimal workoutPercentage);

    void updatePriceAndCostForConfiguredItems(Integer projectId, Map<String, BigDecimal> itemDetails, Map<Integer , BigDecimal> descIdItemCostMap ,String descriptionType);

    void updateProjectDescriptionWithRecalculatedCost(Integer projId, Map<Integer, BigDecimal> descIdItemCostMap);
    
    List<com.psk.pms.model.IndentDesc.ItemDetail> getIndentItemForRequest(String indentId);    

	List<com.psk.pms.model.IndentDesc.ItemDetail> getIndentItemForRequestView(String projId);

    void updatePricesForAlreadyConfiguredItems(Integer projectId, Map<String, BigDecimal> materialNameCostMap, List<String > materialNames, String descriptionType);

    Map<Integer, BigDecimal> getTotalCostOfItemsProjectDescIdForProject(Integer projectId, String descriptionType);

    List<Item> fetchItems();

    void updateItem(Item item);

    void deleteItem(String itemName, String itemType);
}
