package com.psk.pms.dao;

import com.psk.pms.model.DescItemDetail;
import com.psk.pms.model.Item;
import org.apache.log4j.Logger;
import org.springframework.jdbc.core.BatchPreparedStatementSetter;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.datasource.DriverManagerDataSource;

import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.*;

/**
 * Created by prakashbhanu57 on 7/6/2015.
 */
public class ItemDAOImpl implements ItemDAO {

    private DriverManagerDataSource dataSource;

    private JdbcTemplate jdbcTemplate;

    private static final Logger LOGGER = Logger.getLogger(ItemDAOImpl.class);

    @Override
    public Map<String, String> getDescItemCodes(String itemCode) {
        Map<String, String> descItems = new LinkedHashMap<String, String>();
        jdbcTemplate = new JdbcTemplate(dataSource);
        String sql;
        List<Map<String, Object>> rows = null;
        if ("" != itemCode) {
            sql = "select itemNo, itemName from itemcodes where itemName LIKE '%"+itemCode+"%'";
            rows = jdbcTemplate.queryForList(sql);
        }
        for (Map<String, Object> row : rows) {
            descItems.put(String.valueOf(row.get("itemNo")), (String)row.get("itemName"));
        }
        return descItems;
    }

    public boolean isItemAlreadyExisting(String itemName){
        String sql = "SELECT COUNT(*) FROM itemcodes where itemName = ?";
        jdbcTemplate = new JdbcTemplate(dataSource);
        int total = jdbcTemplate.queryForObject(sql, Integer.class,
                new Object[]{itemName});
        if (total == 0) {
            return false;
        }
        return true;
    }

    public boolean saveItem(Item item) {
        String createSql = "INSERT INTO itemcodes (itemName, itemUnit,itemType) "
                + "VALUES (?, ? , ?)";
        jdbcTemplate = new JdbcTemplate(dataSource);
        jdbcTemplate.update(createSql,
                new Object[] { item.getItemName(), item.getItemUnit() ,item.getItemType()});
        return true;
    }

    @Override
    public Set<String> fetchItemNames() {
        String sql = "select itemName from itemcodes";
        jdbcTemplate = new JdbcTemplate(dataSource);
        List<Map<String, Object>> rows = jdbcTemplate.queryForList(sql);
        Set<String> itemNames = new HashSet<String>();
        for (Map<String, Object> row : rows) {
            itemNames.add((String)row.get("itemName"));
        }
        return itemNames;
    }

    public boolean insertDataDescription(final DescItemDetail descItemDetail){
        String sql = "INSERT INTO projdescitem" +
                "(ProjId, SubProjId, ProjDescId, ProjDescSerial, ItemName, ItemUnit, ItemQty, ItemPrice, ItemCost) " +
                "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?)";
        jdbcTemplate = new JdbcTemplate(dataSource);

        String deleteSql = "DELETE from projdescitem where ProjDescId = "+descItemDetail.getProjDescId()+" and ProjDescSerial = '"+descItemDetail.getProjDescSerial()+"'";
        jdbcTemplate.execute(deleteSql);

        jdbcTemplate.batchUpdate(sql, new BatchPreparedStatementSetter() {
            @Override
            public void setValues(PreparedStatement ps, int i) throws SQLException {
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
        return true;
    }

    public DescItemDetail getDataDescription(final DescItemDetail descItemDetail){
        String sql = "Select * from projdescitem where ProjDescId = "+descItemDetail.getProjDescId()+" and ProjDescSerial = '"+descItemDetail.getProjDescSerial()+"'";
        jdbcTemplate = new JdbcTemplate(dataSource);

        List<DescItemDetail.ItemDetail> itemDetailList = new ArrayList<DescItemDetail.ItemDetail>();
        List<Map<String, Object>> rows = jdbcTemplate.queryForList(sql);

        for (Map<String, Object> row : rows) {
            itemDetailList.add(buildItemDetail(row));
        }
        descItemDetail.setItemDetail(itemDetailList);
        return descItemDetail;
    }


    public List<DescItemDetail.ItemDetail> getProjectData(final Integer projId){
        LOGGER.info("getProjectData projId:" + projId);
        String sql = "Select * from projdescitem where ProjId = "+projId;
        jdbcTemplate = new JdbcTemplate(dataSource);
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
        itemDetail.setItemPrice((String) row.get("ItemPrice"));
        itemDetail.setItemCost((String) row.get("ItemCost"));
        return itemDetail;
    }

    public void deleteItemByProjectId(Integer projectId)
    {
        LOGGER.info("method = deleteItemByProjectId()");
        jdbcTemplate = new JdbcTemplate(dataSource);
        int noOfrowsDeleted =jdbcTemplate.update(PmsMasterQuery.DELETEPROJDESCITEMBYPROJECTID,new Object[] {projectId});
        LOGGER.info("No of rows deleted :" + noOfrowsDeleted);
    }


    public void deleteItemBySubProjectId(Integer subProjectId)
    {
        LOGGER.info("method = deleteItemBySubProjectId()");
        jdbcTemplate = new JdbcTemplate(dataSource);
        int noOfrowsDeleted =jdbcTemplate.update(PmsMasterQuery.DELETEPROJDESCITEMBYSUBPROJECTID,new Object[] {subProjectId});
        LOGGER.info("No of rows deleted :"+noOfrowsDeleted);
    }


    @Override
    public void deleteItemByProjectDescriptionId(String projectDescId)
    {
        LOGGER.info("method = deleteItemByProjectDescriptionId()");
        jdbcTemplate = new JdbcTemplate(dataSource);
        int noOfrowsDeleted =jdbcTemplate.update(PmsMasterQuery.DELETEPROJDESCAITEMBYPROJECTDESCID,new Object[] {projectDescId});
        LOGGER.info("No of rows deleted :"+noOfrowsDeleted);
    }

    @Override
    public void deleteItemByProjectDescItemId(Integer projectDescItemId)
    {
        LOGGER.info("method = deleteItemByProjectDescriptionItemId()");
        jdbcTemplate = new JdbcTemplate(dataSource);
        int noOfrowsDeleted =jdbcTemplate.update(PmsMasterQuery.DELETEPROJDESCAITEMBYPROJECTDESCITEMID,new Object[] {projectDescItemId});
        LOGGER.info("No of rows deleted :"+noOfrowsDeleted);
    }

    @Override
    public List<String> fetchItemTypes()
    {
        LOGGER.info("method = deleteItemfetchItemTypes");
        jdbcTemplate = new JdbcTemplate(dataSource);
        List<String> itemTypes =  jdbcTemplate.queryForList(PmsMasterQuery.FETCHITEMTYPES,String.class);
        LOGGER.info("No of rows fetched :"+itemTypes.size());
        return itemTypes;
    }

    public DriverManagerDataSource getDataSource() {
        return dataSource;
    }

    public void setDataSource(DriverManagerDataSource dataSource) {
        this.dataSource = dataSource;
    }
}
