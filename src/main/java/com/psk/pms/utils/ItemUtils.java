package com.psk.pms.utils;

import static org.apache.commons.collections.CollectionUtils.filter;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.apache.commons.collections.Predicate;

import com.psk.pms.model.ItemDetailDto;

/**
 * Created by prakashbhanu57 on 10/10/2015.
 */
public class ItemUtils {

    public static List<ItemDetailDto> filterItemNames(List<ItemDetailDto> itemDetailDtos, List<String> itemNames) {
        for (final String itemName : itemNames) {
            filter(itemDetailDtos, new Predicate() {
                @Override
                public boolean evaluate(Object o) {
                    ItemDetailDto itemDetailDto = (ItemDetailDto) o;
                    return itemName.equalsIgnoreCase(itemDetailDto.getItemName());
                }
            });
        }
        return itemDetailDtos;
    }

    public static List<ItemDetailDto> seperteOutItemsTobeRemoved(Map<String, BigDecimal> materialNameCostMap, List<ItemDetailDto> itemsDetailsOfMaterialType) {
        List<ItemDetailDto> itemsToBeRemoved = new ArrayList<ItemDetailDto>();
        Iterator<ItemDetailDto> itemDetailsIterator = itemsDetailsOfMaterialType.iterator();
        while (itemDetailsIterator.hasNext()) {
            ItemDetailDto itemDetailDto = (ItemDetailDto) itemDetailsIterator.next();
            if (!materialNameCostMap.containsKey(itemDetailDto.getItemName())) {
                itemsToBeRemoved.add(itemDetailDto);
                itemDetailsIterator.remove();
            }
        }
        return itemsToBeRemoved;
    }
}
