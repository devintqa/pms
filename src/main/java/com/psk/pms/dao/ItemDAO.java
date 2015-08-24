package com.psk.pms.dao;

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

    List<DescItemDetail.ItemDetail> getProjectData(ProjectConfiguration projectConfiguration, boolean isEditSubProject);

    void deleteItemByProjectId(Integer projectId);

    void deleteItemBySubProjectId(Integer subProjectId);

    void deleteItemByProjectDescriptionId(String projectDescId);

    void deleteItemByProjectDescItemId(Integer itemId);

    List<String> fetchItemTypes();

    List<String> fetchUniqueItemUnits();

	ProjectConfiguration getProjectItemConfiguration(ProjectConfiguration projectConfiguration, boolean isEditSubProject);

	boolean configureItemPrice(ProjectConfiguration projItemConfiguration);

    List<String> getItemNames();

	List<ProjectItemDescription> getProjectItemDescription(ProjectConfiguration projectConfiguration, boolean isEditSubProject, String itemName);

	DescItemDetail getBaseDescription(DescItemDetail descItemDetail);

    List<ItemDetail> getBaseItemNames(Map<String, Object> request);

    void saveItemRateDescriptions(List<ItemRateDescription> itemRateDescriptions);

    List<String> getItemNames(String itemType, String projectId);
}
