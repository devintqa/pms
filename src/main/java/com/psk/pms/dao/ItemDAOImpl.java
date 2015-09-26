package com.psk.pms.dao;

import com.psk.pms.Constants;
import com.psk.pms.constants.DescriptionType;
import com.psk.pms.model.*;
import com.psk.pms.model.DescItemDetail.ItemDetail;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.jdbc.core.BatchPreparedStatementSetter;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.*;

import static com.psk.pms.dao.PmsMasterQuery.*;

/**
 * Created by prakashbhanu57 on 7/6/2015.
 */
public class ItemDAOImpl implements ItemDAO {

    @Qualifier("jdbcTemplate")
    @Autowired
    private JdbcTemplate jdbcTemplate;

    private static final Logger LOGGER = Logger.getLogger(ItemDAOImpl.class);

    @Override
    public List<ItemDetail> searchItemName(String itemCode, String itemType) {
        List<DescItemDetail.ItemDetail> itemsDetail = new ArrayList<DescItemDetail.ItemDetail>();

        String sql;
        List<Map<String, Object>> rows = null;
        if ("" != itemCode) {
            sql = "select itemNo, itemName, itemUnit from itemcodes where itemType = '" + itemType + "' and itemName LIKE '%" + itemCode + "%'";
            rows = jdbcTemplate.queryForList(sql);
        }
        for (Map<String, Object> row : rows) {
            ItemDetail itemDetail = new ItemDetail();
            itemDetail.setLabel((String) row.get("itemName"));
            itemDetail.setItemName((String) row.get("itemName"));
            itemDetail.setItemUnit((String) row.get("itemUnit"));
            itemsDetail.add(itemDetail);
        }
        return itemsDetail;
    }

    public Map<String, String> getDescItemCodes(String itemCode,
                                                String itemType, String project) {
        Map<String, String> descItems = new LinkedHashMap<String, String>();
        String sql;
        List<Map<String, Object>> rows = null;
        if ("" != itemCode) {
            sql = "select itemNo, itemName, itemUnit from itemcodes where itemType = '" + itemType + "' and itemName LIKE '%" + itemCode + "%'";
            rows = jdbcTemplate.queryForList(sql);
        }
        for (Map<String, Object> row : rows) {
            descItems.put(String.valueOf(row.get("itemNo")), (String) row.get("itemName"));
        }
        return descItems;
    }

    public boolean isItemAlreadyExisting(String itemName) {
        String sql = "SELECT COUNT(*) FROM itemcodes where itemName = ?";
        int total = jdbcTemplate.queryForObject(sql, Integer.class,
                new Object[]{
                        itemName
                });
        if (total == 0) {
            return false;
        }
        return true;
    }

    public boolean saveItem(Item item) {
        jdbcTemplate.update(
                SAVEITEMS,
                new Object[]{
                        item.getItemName(), item.getItemUnit(),
                        item.getItemType()
                });
        return true;
    }

    @Override
    public Set<String> fetchItemNames() {
        String sql = "select itemName from itemcodes";
        List<Map<String, Object>> rows = jdbcTemplate.queryForList(sql);
        Set<String> itemNames = new HashSet<String>();
        for (Map<String, Object> row : rows) {
            itemNames.add((String) row.get("itemName"));
        }
        return itemNames;
    }

    @Override
    public Map<String, String> fetchItemInfo() {
        String sql = "select * from itemcodes";
        List<Map<String, Object>> rows = jdbcTemplate.queryForList(sql);
        Map<String, String> itemNames = new HashMap<String, String>();
        for (Map<String, Object> row : rows) {
            itemNames.put((String) row.get("itemName"), (String) row.get("itemType"));
        }
        return itemNames;
    }

    public boolean insertProjectDescriptionItems(final DescItemDetail descItemDetail) {
        String descType = descItemDetail.getDescType();
        String sql = "INSERT INTO " + DescriptionType.getDescriptionItemTableName(descType)
                + " (ProjId, SubProjId, ProjDescId, ProjDescSerial, ItemName, ItemUnit, ItemQty, ItemPrice, ItemCost) "
                + "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?)";

        String deleteSql = "DELETE from " + DescriptionType.getDescriptionItemTableName(descType) + " where ProjDescId = "
                + descItemDetail.getProjDescId() + " and ProjDescSerial = '"
                + descItemDetail.getProjDescSerial() + "'";
        jdbcTemplate.execute(deleteSql);
        jdbcTemplate.batchUpdate(sql, new BatchPreparedStatementSetter() {

            @Override
            public void setValues(PreparedStatement ps, int i)
                    throws SQLException {
                DescItemDetail.ItemDetail itemDetail = descItemDetail.getItemDetail().get(i);
                ps.setInt(1, descItemDetail.getProjId());
                ps.setInt(2, descItemDetail.getSubProjId());
                ps.setInt(3, descItemDetail.getProjDescId());
                ps.setString(4, descItemDetail.getProjDescSerial());
                ps.setString(5, itemDetail.getItemName());
                ps.setString(6, itemDetail.getItemUnit());
                ps.setString(7, itemDetail.getItemQty());
                ps.setString(8, itemDetail.getItemPrice());
                ps.setString(9, itemDetail.getItemCost());
            }

            @Override
            public int getBatchSize() {
                return descItemDetail.getItemDetail().size();
            }
        });

        long sumItemCost = 0;
        for (DescItemDetail.ItemDetail itemDetail : descItemDetail.getItemDetail()) {
            long itemCost = Double.valueOf(itemDetail.getItemCost()).longValue();
            sumItemCost = sumItemCost + itemCost;
        }
        String projectDescEstimate = "SELECT Quantity from " + DescriptionType.getDescriptionTableName(descType) + " WHERE ProjDescId = '" + descItemDetail.getProjDescId() + "'";
        List<Map<String, Object>> rows = jdbcTemplate.queryForList(projectDescEstimate);

        long totalCost = 0;
        for (Map<String, Object> row : rows) {
            BigDecimal quantity = (BigDecimal) row.get("Quantity");
            long qty = quantity.toBigInteger().longValue();
            totalCost = sumItemCost * qty;
        }
        String updateSql = "UPDATE " + DescriptionType.getDescriptionTableName(descType) + " set PricePerQuantity = ?, TotalCost =?  WHERE ProjDescId = ?";
        jdbcTemplate.update(updateSql, new Object[]{sumItemCost, totalCost,
                descItemDetail.getProjDescId()});

        return true;
    }


    public DescItemDetail getBaseDescription(final DescItemDetail descItemDetail) {
        String sql = "Select * from basedescitem where BaseDescId = '" + descItemDetail.getBaseDescId() + "'";
        System.out.println("pull out call for base desc:" + sql);
        List<DescItemDetail.ItemDetail> itemDetailList = new ArrayList<DescItemDetail.ItemDetail>();
        List<Map<String, Object>> rows = jdbcTemplate.queryForList(sql);

        for (Map<String, Object> row : rows) {
            itemDetailList.add(buildBaseItemDetail(row));
        }
        descItemDetail.setItemDetail(itemDetailList);
        return descItemDetail;
    }


    private DescItemDetail.ItemDetail buildBaseItemDetail(Map<String, Object> row) {
        DescItemDetail.ItemDetail itemDetail = new DescItemDetail.ItemDetail();
        itemDetail.setItemName((String) row.get("ItemName"));
        itemDetail.setItemUnit((String) row.get("ItemUnit"));
        itemDetail.setItemQty((String) row.get("ItemQty"));
        itemDetail.setItemType((String) row.get("ItemType"));
        itemDetail.setItemPrice((String) row.get("ItemPrice"));
        return itemDetail;
    }


    public DescItemDetail getProjectDescriptionItems(final DescItemDetail descItemDetail) {
        String sql = "";
//        sql = "Select * from  "+ DescriptionType.getDescriptionItemTableName(descItemDetail.getDescType()) +"  where ProjDescId = " + descItemDetail.getProjDescId() + " and ProjDescSerial = '" + descItemDetail.getProjDescSerial() + "'";
        if (descItemDetail.getDescType().equalsIgnoreCase(Constants.PSK))
            sql = "Select  pdi.ProjId, pdi.SubProjId, pdi.ProjDescId, pdi.ProjDescSerial, pdi.ItemName, pdi.ItemUnit," +
                    " pdi.ItemQty, pdi.ItemCost, pdi.DescItemId, ppd.itemType, ppd.itemPrice from  projdescitem  pdi, pskpricedetail ppd where pdi.itemName = ppd.ItemName " +
                    "and pdi.ProjId = ppd.projectId and ppd.active = '1' and pdi.ProjDescId = '" + descItemDetail.getProjDescId() + "' and pdi.ProjDescSerial = '" + descItemDetail.getProjDescSerial() + "' ";
        else
            sql = "Select  pdi.ProjId, pdi.SubProjId, pdi.ProjDescId, pdi.ProjDescSerial, pdi.ItemName, pdi.ItemUnit, pdi.ItemQty," +
                    " pdi.ItemCost, pdi.DescItemId, ppd.itemType, ppd.itemPrice from  govprojdescitem  pdi, govpricedetail ppd where pdi.itemName = ppd.ItemName and " +
                    "ppd.active = '1' and pdi.ProjDescId = '" + descItemDetail.getProjDescId() + "' and pdi.ProjDescSerial = '" + descItemDetail.getProjDescSerial() + "' ";
        System.out.println(sql);
        List<DescItemDetail.ItemDetail> itemDetailList = new ArrayList<DescItemDetail.ItemDetail>();
        List<Map<String, Object>> rows = jdbcTemplate.queryForList(sql);

        for (Map<String, Object> row : rows) {
            itemDetailList.add(buildItemDetail(row));
        }
        descItemDetail.setItemDetail(itemDetailList);
        return descItemDetail;
    }
    
    public DescItemDetail getPskDescriptionItems(final String projDescId) {
        String sql = "Select  pdi.ProjId, pdi.SubProjId, pdi.ProjDescId, pdi.ProjDescSerial, pdi.ItemName, pdi.ItemUnit, pdi.ItemQty, pdi.ItemCost, pdi.DescItemId, ppd.itemPrice, ppd.ItemType from  projdescitem  pdi, pskpricedetail ppd where pdi.itemName = ppd.ItemName and pdi.ProjId = ppd.projectId and ppd.active = '1' and pdi.ProjDescId = '" + projDescId + "'";
        System.out.println(sql);
        List<DescItemDetail.ItemDetail> itemDetailList = new ArrayList<DescItemDetail.ItemDetail>();
        List<Map<String, Object>> rows = jdbcTemplate.queryForList(sql);
        DescItemDetail descItems = new DescItemDetail();
        for (Map<String, Object> row : rows) {
            itemDetailList.add(buildItemDetail(row));
        }
        descItems.setItemDetail(itemDetailList);
        return descItems;
    }


    public List<DescItemDetail.ItemDetail> getProjectData(ProjectConfiguration projectConfiguration, boolean isEditSubProject, String descType) {
        String sql;
        if (isEditSubProject) {
            LOGGER.info("Into getProjectData() " + isEditSubProject + " Sub Project Id" + projectConfiguration.getSubProjId());
            sql = "Select * from " + DescriptionType.getDescriptionItemTableName(descType) + " where SubProjId = " + projectConfiguration.getSubProjId();
        } else {
            sql = "Select * from " + DescriptionType.getDescriptionItemTableName(descType) + " where ProjId = " + projectConfiguration.getProjId() + " and SubProjId = " + projectConfiguration.getSubProjId();
        }
        List<DescItemDetail.ItemDetail> itemDetailList = new ArrayList<DescItemDetail.ItemDetail>();
        List<Map<String, Object>> rows = jdbcTemplate.queryForList(sql);
        LOGGER.info("getProjectData rows:" + rows.size());
        for (Map<String, Object> row : rows) {
            itemDetailList.add(buildItemDetail(row));
        }
        LOGGER.info("getProjectData itemDetailList:" + itemDetailList.size());
        return itemDetailList;
    }

    private DescItemDetail.ItemDetail buildItemDetail(Map<String, Object> row) {
        DescItemDetail.ItemDetail itemDetail = new DescItemDetail.ItemDetail();
        itemDetail.setItemName((String) row.get("ItemName"));
        itemDetail.setItemUnit((String) row.get("ItemUnit"));
        itemDetail.setItemQty((String) row.get("ItemQty"));
        if (null != row.get("itemType"))
            itemDetail.setItemType((row.get("itemType")).toString());
        
        if (null != row.get("itemPrice"))
            itemDetail.setItemPrice((row.get("itemPrice")).toString());
        
        itemDetail.setItemCost((String) row.get("ItemCost"));
        return itemDetail;
    }

    private ProjectConfiguration.ItemDetail buildPricedItem(Map<String, Object> row) {
        ProjectConfiguration.ItemDetail itemDetail = new ProjectConfiguration.ItemDetail();
        itemDetail.setItemName((String) row.get("ItemName"));
        itemDetail.setItemType((String) row.get("ItemType"));
        itemDetail.setItemUnit((String) row.get("ItemUnit"));
        if (null != row.get("itemPrice"))
            itemDetail.setItemPrice(((BigDecimal) row.get("itemPrice")).toString());
        return itemDetail;
    }

    public void deleteItemByProjectId(Integer projectId) {
        LOGGER.info("method = deleteItemByProjectId()");
        int noOfrowsDeleted = jdbcTemplate.update(DELETEPROJDESCITEMBYPROJECTID, new Object[]{
                projectId
        });
        LOGGER.info("No of rows deleted :" + noOfrowsDeleted);
    }


    public void deleteItemBySubProjectId(Integer subProjectId) {
        LOGGER.info("method = deleteItemBySubProjectId()");
        int noOfrowsDeleted = jdbcTemplate.update(DELETEPROJDESCITEMBYSUBPROJECTID, new Object[]{
                subProjectId
        });
        LOGGER.info("No of rows deleted :" + noOfrowsDeleted);
    }

    @Override
    public void deleteItemByProjectDescriptionId(String projectDescId, String descType) {
        LOGGER.info("method = deleteItemByProjectDescriptionId()");
        String DELETEPROJDESCAITEMBYPROJECTDESCID = "DELETE FROM " + DescriptionType.getDescriptionItemTableName(descType) + " WHERE ProjDescId = ?";
        int noOfrowsDeleted = jdbcTemplate.update(DELETEPROJDESCAITEMBYPROJECTDESCID, new Object[]{projectDescId});
        LOGGER.info("No of rows deleted :" + noOfrowsDeleted);
    }

    @Override
    public void deleteItemByProjectDescItemId(Integer projectDescItemId) {
        LOGGER.info("method = deleteItemByProjectDescriptionItemId()");
        int noOfrowsDeleted = jdbcTemplate.update(DELETEPROJDESCAITEMBYPROJECTDESCITEMID, new Object[]{
                projectDescItemId
        });
        LOGGER.info("No of rows deleted :" + noOfrowsDeleted);
    }

    @Override
    public List<ItemDetail> getDescItemNames(Map<String, Object> request) {
        List<DescItemDetail.ItemDetail> itemsDetail = new ArrayList<DescItemDetail.ItemDetail>();
        String itemType = (String) request.get("itemType");
        String sql;
        List<Map<String, Object>> rows = null;
        if ("" != request.get("itemName")) {
            if (request.get("descType").toString().equalsIgnoreCase(Constants.PSK)) {
                sql = "select itemName, itemUnit, itemPrice from pskpricedetail where projectId = '" + request.get("projectId") + "' and subProjectId = '" + request.get("subProjectId") + "' and itemType = '" + itemType + "' and itemName LIKE '%" + request.get("itemName") + "%' and active = '1'";
            } else {
                sql = "select itemName, itemUnit, itemPrice from govpricedetail where itemType = '" + itemType + "' and itemName LIKE '%" + request.get("itemName") + "%' and active = '1'";
            }

            rows = jdbcTemplate.queryForList(sql);
        }
        for (Map<String, Object> row : rows) {
            ItemDetail itemDetail = new ItemDetail();
            itemDetail.setLabel((String) row.get("itemName"));
            itemDetail.setItemName((String) row.get("itemName"));
            itemDetail.setItemUnit((String) row.get("itemUnit"));
            itemDetail.setItemPrice(((BigDecimal) row.get("itemPrice")).toString());
            itemDetail.setItemType(itemType);
            itemsDetail.add(itemDetail);
        }
        return itemsDetail;
    }

    @Override
    public List<ItemDetail> getBaseItemNames(Map<String, Object> request) {
        List<DescItemDetail.ItemDetail> itemsDetail = new ArrayList<DescItemDetail.ItemDetail>();
        String sql = null;
        List<Map<String, Object>> rows = null;
        if ("" != request.get("itemName")) {
            if (Constants.GOVERNMENT.equalsIgnoreCase(request.get("descType").toString())) {
                sql = "select itemType, itemName, itemUnit, itemPrice from govpricedetail where itemType = '" + request.get("itemType") + "' and itemName LIKE '%" + request.get("itemName") + "%' and active = '1'";
            } else {
                sql = "select itemName, itemUnit, itemNo, itemType from itemcodes where itemType = '" + request.get("itemType") + "' and itemName LIKE '%" + request.get("itemName") + "%'";
            }
            rows = jdbcTemplate.queryForList(sql);
        }
        for (Map<String, Object> row : rows) {
            ItemDetail itemDetail = new ItemDetail();
            itemDetail.setLabel((String) row.get("itemName"));
            itemDetail.setItemName((String) row.get("itemName"));
            itemDetail.setItemUnit((String) row.get("itemUnit"));
            itemDetail.setItemType((String) row.get("itemType"));
            if (null != row.get("itemPrice"))
                itemDetail.setItemPrice(((BigDecimal) row.get("itemPrice")).toString());
            itemsDetail.add(itemDetail);
        }
        return itemsDetail;
    }


    @Override
    public List<String> fetchUniqueItemUnits() {
        LOGGER.info("method = fetchUniqueItemUnit");
        List<String> uniqueItemUnits = jdbcTemplate.queryForList(FETCHUNIQUEITEMUNIT, String.class);
        LOGGER.info("No of rows fetched :" + uniqueItemUnits.size());
        return uniqueItemUnits;
    }

    @Override
    public boolean configureItemPrice(final ProjectConfiguration projectItemConfiguration) {
        deactivateExistingPrices(projectItemConfiguration);
        jdbcTemplate.batchUpdate(INSERTPRICEFORITEMS, new BatchPreparedStatementSetter() {
            @Override
            public void setValues(PreparedStatement ps, int i) throws SQLException {
                ProjectConfiguration.ItemDetail itemDetail = projectItemConfiguration.getItemDetail().get(i);
                ps.setInt(1, projectItemConfiguration.getProjId());
                ps.setInt(2, projectItemConfiguration.getSubProjId());
                ps.setString(3, itemDetail.getItemName());
                ps.setString(4, itemDetail.getItemUnit());
                ps.setString(5, itemDetail.getItemPrice());
                ps.setString(6, itemDetail.getItemType());
                ps.setDate(7, new java.sql.Date(Calendar.getInstance().getTimeInMillis()));
            }

            @Override
            public int getBatchSize() {
                return projectItemConfiguration.getItemDetail().size();
            }
        });
        return true;
    }

    private void deactivateExistingPrices(ProjectConfiguration projectItemConfiguration) {
        jdbcTemplate.update(DEACTIVATEEXISTINGPRICES, projectItemConfiguration.getProjId(), projectItemConfiguration.getSubProjId());
    }

    @Override
    public ProjectConfiguration getProjectItemConfiguration(ProjectConfiguration projectConfiguration, boolean isEditSubProject) {
        String sql;
        if (isEditSubProject) {
            LOGGER.info("Into Edit Sub Project " + isEditSubProject + " Sub Project Id" + projectConfiguration.getSubProjId());
            sql = "Select * from pskpricedetail where active = 1 and subProjectId = " + projectConfiguration.getSubProjId();
        } else {
            sql = "Select * from pskpricedetail where active = 1 and projectId = " + projectConfiguration.getProjId() + " and subProjectId = " + projectConfiguration.getSubProjId();
        }
        List<ProjectConfiguration.ItemDetail> itemList = new ArrayList<ProjectConfiguration.ItemDetail>();
        List<Map<String, Object>> rows = jdbcTemplate.queryForList(sql);

        for (Map<String, Object> row : rows) {
            itemList.add(buildPricedItem(row));
        }
        projectConfiguration.setItemDetail(itemList);
        return projectConfiguration;
    }


    @Override
    public List<ProjectItemDescription> getProjectItemDescription(ProjectConfiguration projectConfiguration, ViewDetail viewDetail) {
        String sql;
        boolean isEditSubProject = viewDetail.isEditSubProject();
        String itemName = viewDetail.getItemName();
        String descType = viewDetail.getDescType();
        if (isEditSubProject) {
            LOGGER.info("getProjectItemDescription() " + isEditSubProject + " Sub Project Id" + projectConfiguration.getSubProjId());
            sql = "select pdi.ProjDescId, pdi.ProjDescSerial, pd.AliasDescription,\n" +
                    "pdi.itemName, pdi.ItemQty, pdi.itemUnit from " + DescriptionType.getDescriptionItemTableName(descType) + " pdi," +
                    " " + DescriptionType.getDescriptionTableName(descType) + " pd where\n" +
                    "pdi.ProjDescId = pd.ProjDescId and pdi.SubProjId = '" + projectConfiguration.getSubProjId() + "' and pdi.itemName = '" + itemName + "'";
        } else {
            sql = "select pdi.ProjDescId, pdi.ProjDescSerial, pd.AliasDescription,\n" +
                    "pdi.itemName, pdi.ItemQty, pdi.itemUnit from " + DescriptionType.getDescriptionItemTableName(descType) + " pdi," +
                    " " + DescriptionType.getDescriptionTableName(descType) + " pd where\n" +
                    "pdi.ProjDescId = pd.ProjDescId and pdi.ProjId = '" + projectConfiguration.getProjId() + "' and pdi.SubProjId = '" + projectConfiguration.getSubProjId() + "' and pdi.itemName = '" + itemName + "'";
        }
        List<Map<String, Object>> rows = jdbcTemplate.queryForList(sql);
        List<ProjectItemDescription> projectItemDescriptions = new ArrayList<ProjectItemDescription>();
        for (Map<String, Object> row : rows) {
            ProjectItemDescription projectItemDescription = new ProjectItemDescription();
            projectItemDescription.setProjectDescId((Integer) row.get("ProjDescId"));
            projectItemDescription.setProjectDescSerialNumber((String) row.get("ProjDescSerial"));
            projectItemDescription.setItemName((String) row.get("itemName"));
            projectItemDescription.setItemQuantity((String) row.get("ItemQty"));
            projectItemDescription.setItemUnit((String) row.get("itemUnit"));
            projectItemDescription.setAliasDescription((String) row.get("AliasDescription"));
            projectItemDescriptions.add(projectItemDescription);
        }
        return projectItemDescriptions;
    }

    @Override
    public List<String> getItemNames() {
        String PROJECT_ITEM_NAMES = "select distinct(itemName) from itemCodes;";
        List<String> types = jdbcTemplate.queryForList(PROJECT_ITEM_NAMES,
                String.class);
        return types;
    }

    @Override
    public List<String> getItemNames(String itemType, String projectId) {
        String PROJECT_ITEM_NAMES = "select distinct(itemName) from pskpricedetail where itemType=? and projectId=? ";
        List<String> itemNames = jdbcTemplate.queryForList(PROJECT_ITEM_NAMES, new Object[]{
                itemType, projectId
        }, String.class);
        return itemNames;

    }

    @Override
    public boolean insertBaseDescriptionItems(final DescItemDetail descItemDetail) {
        String sql = "INSERT INTO basedescitem" + "(BaseDescId, ItemName, ItemUnit, ItemType, ItemQty, ItemPrice) " + "VALUES (?, ?, ?, ?, ?, ?)";

        String deleteSql = "DELETE from basedescitem where BaseDescId = '" + descItemDetail.getBaseDescId() + "'";
        jdbcTemplate.execute(deleteSql);

        jdbcTemplate.batchUpdate(sql, new BatchPreparedStatementSetter() {

            @Override
            public void setValues(PreparedStatement ps, int i) throws SQLException {
                DescItemDetail.ItemDetail itemDetail = descItemDetail.getItemDetail().get(i);
                ps.setInt(1, descItemDetail.getBaseDescId());
                ps.setString(2, itemDetail.getItemName());
                ps.setString(3, itemDetail.getItemUnit());
                ps.setString(4, itemDetail.getItemType());
                ps.setString(5, itemDetail.getItemQty());
                if (descItemDetail.getDescType().equalsIgnoreCase(Constants.GOVERNMENT)) {
                    ps.setString(6, itemDetail.getItemPrice());
                } else {
                    ps.setString(6, "0");
                }
            }

            @Override
            public int getBatchSize() {
                return descItemDetail.getItemDetail().size();
            }
        });

        long sumItemCost = 0;

        if (descItemDetail.getDescType().equalsIgnoreCase(Constants.GOVERNMENT)) {
            for (DescItemDetail.ItemDetail itemDetail : descItemDetail.getItemDetail()) {
                long itemPrice = Double.valueOf(itemDetail.getItemPrice()).longValue();
                long itemQty = Double.valueOf(itemDetail.getItemQty()).longValue();
                long itemCost = itemPrice * itemQty;
                sumItemCost = sumItemCost + itemCost;
            }
        }

        String updateSql = "UPDATE basedesc set PricePerQuantity = ? where Category = '" + descItemDetail.getDescType() + "' and BaseDescId = '" + descItemDetail.getBaseDescId() + "'";
        System.out.println(updateSql);
        jdbcTemplate.update(updateSql, new Object[]{
                sumItemCost
        });


        return true;
    }


    @Override
    @Transactional
    public void saveItemRateDescriptions(final List<ItemRateDescription> itemRateDescriptions) {
        jdbcTemplate.batchUpdate(PmsMasterQuery.INSERT_ITEM_RATE_DESCRIPTION, new BatchPreparedStatementSetter() {
            @Override
            public void setValues(PreparedStatement ps, int i) throws SQLException {
                ItemRateDescription itemRateDescription = itemRateDescriptions.get(i);
                ps.setString(1, itemRateDescription.getItemName());
                ps.setString(2, itemRateDescription.getItemUnit());
                ps.setString(3, itemRateDescription.getWorkType());
                ps.setString(4, itemRateDescription.getItemRate());
                ps.setDate(5, new java.sql.Date(Calendar.getInstance().getTimeInMillis()));
                ps.setString(6, itemRateDescription.getScheduleItemNumber());
                ps.setString(7, itemRateDescription.getItemDescription());
            }

            @Override
            public int getBatchSize() {
                return itemRateDescriptions.size();
            }
        });
    }


    public List<com.psk.pms.model.ProjectConfiguration.ItemDetail> getMissingProjectDescriptionItems(Integer projectId) {
        List<com.psk.pms.model.ProjectConfiguration.ItemDetail> itemsDetail = new ArrayList<com.psk.pms.model.ProjectConfiguration.ItemDetail>();
        String sql = null;
        List<Map<String, Object>> rows = null;
        if (null != projectId) {
            sql = "select distinct(pi.itemName), pi.itemUnit, bi.itemType from projdescitem as pi, basedescitem as bi " +
                    "where pi.projId ='" + projectId + "' and pi.itemName = bi.itemName and " +
                    "not exists " +
                    "  (select 1 from pskpricedetail b where b.itemName = pi.itemName and b.active = 1)";

            rows = jdbcTemplate.queryForList(sql);
        }
        for (Map<String, Object> row : rows) {
            com.psk.pms.model.ProjectConfiguration.ItemDetail itemDetail = new com.psk.pms.model.ProjectConfiguration.ItemDetail();
            itemDetail.setItemName((String) row.get("itemName"));
            itemDetail.setItemUnit((String) row.get("itemUnit"));
            itemDetail.setItemType((String) row.get("itemType"));
            itemsDetail.add(itemDetail);
        }
        System.out.println("getMissingProjectDescriptionItems: " + itemsDetail.size());
        return itemsDetail;
    }

    @Override
    public boolean isItemPresent() {

        String sql = "SELECT COUNT(*) FROM govpricedetail";
        int total = jdbcTemplate.queryForObject(sql, Integer.class);
        if (total != 0) {
            return true;
        }
        return false;
    }

    @Override
    public void deleteItemDescription() {
       jdbcTemplate.update("delete  from govpricedetail ");
    }


    @Override
    public List<LeadDetailConfiguration.LeadDetail> getLeadDetails(String projectId, String subProjectId) {
        LOGGER.info("Getting lead details for projectId:" + projectId + ", subProjectId:" + subProjectId);
        List<LeadDetailConfiguration.LeadDetail> leadDetails = new ArrayList<LeadDetailConfiguration.LeadDetail>();
        try {
            List<Map<String, Object>> rows = jdbcTemplate.queryForList(GET_LEAD_DETAILS, projectId, subProjectId);
            for (Map<String, Object> row : rows) {
                leadDetails.add(buildLeadDetail(row));
            }
        } catch (Exception e) {
            LOGGER.error("Error occurred while getting lead detail " + e);
        }
        return leadDetails;
    }

    @Override
    public void saveLeadDetails(final LeadDetailConfiguration leadDetailConfiguration) {
        LOGGER.info("Saving lead Details for, ProjectId:" + leadDetailConfiguration.getProjectId() + " ,subProjectId:" + leadDetailConfiguration.getSubProjectId());
        try {
            deactivateLeadDetails(leadDetailConfiguration.getProjectId(),leadDetailConfiguration.getSubProjectId());
            jdbcTemplate.batchUpdate(CREATE_LEAD_DETAILS,
                    new BatchPreparedStatementSetter() {
                        @Override
                        public void setValues(PreparedStatement ps, int i)
                                throws SQLException {
                            LeadDetailConfiguration.LeadDetail leadDetail = leadDetailConfiguration.getLeadDetails().get(i);
                            ps.setInt(1, leadDetailConfiguration.getProjectId());
                            ps.setInt(2, leadDetailConfiguration.getSubProjectId());
                            ps.setString(3, leadDetail.getMaterial());
                            ps.setString(4, leadDetail.getSourceOfSupply());
                            ps.setString(5, leadDetail.getDistance());
                            ps.setString(6,leadDetail.getUnit());
                            ps.setString(7, leadDetail.getCost());
                            ps.setString(8, leadDetail.getIc());
                            ps.setString(9, leadDetail.getLeadCharges());
                            ps.setString(10, leadDetail.getLoadingUnloading());
                            ps.setString(11, leadDetail.getTotal());
                            ps.setString(12, leadDetailConfiguration.getEmployeeId());
                            ps.setDate(13, new java.sql.Date(Calendar.getInstance().getTimeInMillis()));
                        }

                        @Override
                        public int getBatchSize() {
                            return leadDetailConfiguration.getLeadDetails().size();
                        }
                    });
        } catch (Exception e) {
            LOGGER.error("Error occurred while saving lead details " + e);
        }
    }

    private void deactivateLeadDetails(int projectId, int subProjectId) {
        LOGGER.info("Deactivating leadDetails fr projectId:"+projectId +" subProjectId:"+subProjectId);
        jdbcTemplate.update(DEACTIVATE_EXISTING_LEAD_DETAILS, projectId, subProjectId);
    }


    private LeadDetailConfiguration.LeadDetail buildLeadDetail(Map<String, Object> row) {
        LeadDetailConfiguration.LeadDetail leadDetail = new LeadDetailConfiguration.LeadDetail();
        Integer leadDetailId = (Integer) row.get("leadDetailId");
        leadDetail.setLeadDetailId(leadDetailId.toString());
        String material = (String) row.get("material");
        leadDetail.setMaterial(material);
        String sourceOfSupply = (String) row.get("sourceOfSupply");
        leadDetail.setSourceOfSupply(sourceOfSupply);
        BigDecimal distance = (BigDecimal) row.get("distance");
        leadDetail.setDistance(String.valueOf(distance));
        String unit = (String) row.get("unit");
        leadDetail.setUnit(unit);
        leadDetail.setSourceOfSupply(sourceOfSupply);
        BigDecimal cost = (BigDecimal) row.get("cost");
        leadDetail.setCost(String.valueOf(cost));
        BigDecimal ic = (BigDecimal) row.get("ic");
        leadDetail.setIc(String.valueOf(ic));
        BigDecimal leadCharges = (BigDecimal) row.get("leadCharges");
        leadDetail.setLeadCharges(String.valueOf(leadCharges));
        BigDecimal loadingUnloadingCharges = (BigDecimal) row.get("loadingUnloadingCharges");
        leadDetail.setLoadingUnloading(String.valueOf(loadingUnloadingCharges));
        BigDecimal total = (BigDecimal) row.get("total");
        leadDetail.setTotal(String.valueOf(total));
        return leadDetail;
    }
}