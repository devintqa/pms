package com.psk.pms.service;

import com.psk.pms.model.DescItemDetail;
import com.psk.pms.model.Item;

import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * Created by prakashbhanu57 on 7/6/2015.
 */
public interface ItemService {

    boolean createEditItem(Item item);

    boolean isItemAlreadyExisting(String itemName);

    Map<String, String> getDescItemCodes(String itemCode);

    Set<String> fetchItemNames();

    List<DescItemDetail.ItemDetail> getProjectData(Integer projId);

    boolean insertDataDescription(DescItemDetail descItemDetail);

    DescItemDetail getDataDescription(final DescItemDetail descItemDetail);

    void deleteItemByDescriptionItemId(Integer subProjectid);
}
