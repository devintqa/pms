package com.psk.pms.service;

import com.psk.pms.model.DescItemDetail;
import com.psk.pms.model.DescItemDetail.ItemDetail;
import com.psk.pms.model.Item;
import com.psk.pms.model.ProjectConfiguration;
import com.psk.pms.model.ProjectItemDescription;

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

    List<DescItemDetail.ItemDetail> getProjectData(Integer projId);

    boolean insertDataDescription(DescItemDetail descItemDetail);

    DescItemDetail getDataDescription(final DescItemDetail descItemDetail);

    void deleteItemByDescriptionItemId(Integer subProjectid);

    List<String> fetchItemTypes();

    List<String> fetchUniqueItemUnits();

    boolean configureItemPrice(ProjectConfiguration projectItemConfiguration);

    ProjectConfiguration getProjectItemConfiguration(
            ProjectConfiguration projectConfiguration, boolean isEditSubProject);

    List<String> getItemNames();

    List<ItemDetail> getBaseItemNames(Map<String, Object> request);

    List<ProjectItemDescription> getProjectItemDescription(Integer projId,
                                                           String itemName);
}
