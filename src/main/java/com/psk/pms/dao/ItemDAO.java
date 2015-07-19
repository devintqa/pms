package com.psk.pms.dao;

import java.util.List;
import java.util.Map;
import java.util.Set;

import com.psk.pms.model.DescItemDetail;
import com.psk.pms.model.DescItemDetail.ItemDetail;
import com.psk.pms.model.Item;

/**
 * Created by prakashbhanu57 on 7/6/2015.
 */
public interface ItemDAO {

    boolean saveItem(Item item);

    Set<String> fetchItemNames();
    
    Set<String> fetchItemNames(String itemCategory);

    boolean isItemAlreadyExisting(String itemName);
    
    List<ItemDetail> getDescItemNames(Map<String, Object> request);

    List<ItemDetail> searchItemName(String itemCode, String itemType);

    boolean insertDataDescription(DescItemDetail descItemDetail);

    DescItemDetail getDataDescription(final DescItemDetail descItemDetail);

    List<DescItemDetail.ItemDetail> getProjectData(Integer projId);

    void deleteItemByProjectId(Integer projectId);

    void deleteItemBySubProjectId(Integer subProjectId);

    void deleteItemByProjectDescriptionId(String projectDescId);

    void deleteItemByProjectDescItemId(Integer itemId);

}
