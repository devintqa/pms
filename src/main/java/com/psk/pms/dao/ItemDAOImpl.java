package com.psk.pms.dao;

import java.math.BigDecimal;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.apache.log4j.Logger;
import org.springframework.jdbc.core.BatchPreparedStatementSetter;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.datasource.DriverManagerDataSource;

import com.psk.pms.model.DescItemDetail;
import com.psk.pms.model.DescItemDetail.ItemDetail;
import com.psk.pms.model.Item;
import com.psk.pms.model.ProjectConfiguration;

/**
 * Created by prakashbhanu57 on 7/6/2015.
 */
public class ItemDAOImpl implements ItemDAO {

	private DriverManagerDataSource dataSource;

	private JdbcTemplate jdbcTemplate;

	private static final Logger LOGGER = Logger.getLogger(ItemDAOImpl.class);

	@Override
	public List<ItemDetail> searchItemName(String itemCode, String itemType) {
		List<DescItemDetail.ItemDetail> itemsDetail = new ArrayList<DescItemDetail.ItemDetail>();

		jdbcTemplate = new JdbcTemplate(dataSource);
		String sql;
		List<Map<String, Object>> rows = null;
		if ("" != itemCode) {
			sql = "select itemNo, itemName, itemUnit from itemcodes where itemType = '"+itemType+"' and itemName LIKE '%"+itemCode+"%'";
			rows = jdbcTemplate.queryForList(sql);
		}
		for (Map<String, Object> row : rows) {
			ItemDetail itemDetail = new ItemDetail();
			itemDetail.setLabel((String)row.get("itemName"));
			itemDetail.setItemName((String)row.get("itemName"));
			itemDetail.setItemUnit((String)row.get("itemUnit"));
			itemsDetail.add(itemDetail);
		}
		return itemsDetail;
	}

	public Map<String, String> getDescItemCodes(String itemCode, String itemType, String project) {
		Map<String, String> descItems = new LinkedHashMap<String, String>();
		jdbcTemplate = new JdbcTemplate(dataSource);
		String sql;
		List<Map<String, Object>> rows = null;
		if ("" != itemCode) {
			sql = "select itemNo, itemName, itemUnit from itemcodes where itemType = '"+itemType+"' and itemName LIKE '%"+itemCode+"%'";
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
		jdbcTemplate = new JdbcTemplate(dataSource);
		jdbcTemplate.update(PmsMasterQuery.SAVEITEMS,
				new Object[] { item.getItemName(), item.getItemUnit(), item.getItemType() });
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
	
	@Override
	public Map<String,String> fetchItemInfo() {
		String sql = "select * from itemcodes";
		jdbcTemplate = new JdbcTemplate(dataSource);
		List<Map<String, Object>> rows = jdbcTemplate.queryForList(sql);
		Map<String,String> itemNames = new HashMap<String,String>();
		for (Map<String, Object> row : rows) {
			itemNames.put((String)row.get("itemName"),(String)row.get("itemType"));
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
		
		long sumItemCost = 0;
		for (DescItemDetail.ItemDetail itemDetail: descItemDetail.getItemDetail()){
			long itemCost = Double.valueOf(itemDetail.getItemCost()).longValue();
			sumItemCost = sumItemCost + itemCost;
		}
		
		String projectDescEstimate = "SELECT Quantity from projectdesc WHERE ProjDescId = '"+descItemDetail.getProjDescId()+"'";
		jdbcTemplate = new JdbcTemplate(dataSource);
		List<Map<String, Object>> rows = jdbcTemplate.queryForList(projectDescEstimate);

		long totalCost = 0;
		for (Map<String, Object> row : rows) {
			BigDecimal quantity = (BigDecimal) row.get("Quantity");
			long qty = quantity.toBigInteger().longValue();
			totalCost = sumItemCost * qty;
		}
	        
		 String updateSql = "UPDATE projectdesc set PricePerQuantity = ?, TotalCost =?  WHERE ProjDescId = ?";
         jdbcTemplate.update(updateSql, new Object[]{sumItemCost, totalCost, descItemDetail.getProjDescId()});
	        
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
	
	private ProjectConfiguration.ItemDetail buildPricedItem(Map<String, Object> row) {
		ProjectConfiguration.ItemDetail itemDetail = new ProjectConfiguration.ItemDetail();
		itemDetail.setItemName((String) row.get("ItemName"));
		itemDetail.setItemType((String) row.get("ItemType"));
		itemDetail.setItemUnit((String) row.get("ItemUnit"));
		itemDetail.setItemPrice(row.get("ItemPrice").toString());
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

	public DriverManagerDataSource getDataSource() {
		return dataSource;
	}

	public void setDataSource(DriverManagerDataSource dataSource) {
		this.dataSource = dataSource;
	}

	@Override
	public Set<String> fetchItemNames(String itemCategory) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<ItemDetail> getDescItemNames(Map<String, Object> request) {
		List<DescItemDetail.ItemDetail> itemsDetail = new ArrayList<DescItemDetail.ItemDetail>();

		jdbcTemplate = new JdbcTemplate(dataSource);
		String sql;
		List<Map<String, Object>> rows = null;
		if ("" != request.get("itemName")) {
			sql = "select itemName, itemUnit, itemPrice from pricedetail where itemType = '"+request.get("itemType")+"' and itemName LIKE '%"+request.get("itemName")+"%' and active = '1'";
			rows = jdbcTemplate.queryForList(sql);
		}
		for (Map<String, Object> row : rows) {
			ItemDetail itemDetail = new ItemDetail();
			itemDetail.setLabel((String)row.get("itemName"));
			itemDetail.setItemName((String)row.get("itemName"));
			itemDetail.setItemUnit((String)row.get("itemUnit"));
			itemDetail.setItemPrice(((BigDecimal) row.get("itemPrice")).toString());

			itemsDetail.add(itemDetail);
		}
		return itemsDetail;
	}

	 @Override
	    public List<String> fetchItemTypes()
	    {
	        LOGGER.info("method = fetchItemTypes");
	        jdbcTemplate = new JdbcTemplate(dataSource);
	        List<String> itemTypes =  jdbcTemplate.queryForList(PmsMasterQuery.FETCHITEMTYPES, String.class);
	        LOGGER.info("No of rows fetched :"+itemTypes.size());
	        return itemTypes;
	    }

	    @Override
	    public List<String> fetchUniqueItemUnits()
	    {
	        LOGGER.info("method = fetchUniqueItemUnit");
	        jdbcTemplate = new JdbcTemplate(dataSource);
	        List<String> uniqueItemUnits = jdbcTemplate.queryForList(PmsMasterQuery.FETCHUNIQUEITEMUNIT,String.class);
	        LOGGER.info("No of rows fetched :" + uniqueItemUnits.size());
	        return uniqueItemUnits;
	    }

		@Override
		public boolean configureItemPrice(final ProjectConfiguration projectItemConfiguration){
				String sql = "INSERT INTO pricedetail" +
						"(projectId, subProjectId, itemName, itemUnit, itemPrice, itemType, priceFeed) " +
						"VALUES (?, ?, ?, ?, ?, ?, ?)";
				jdbcTemplate = new JdbcTemplate(dataSource);
				
				String updateSql = "update pricedetail set active ='0' where projectId = "+projectItemConfiguration.getProjId();
				jdbcTemplate.execute(updateSql);
				
				jdbcTemplate.batchUpdate(sql, new BatchPreparedStatementSetter() {
					@Override
					public void setValues(PreparedStatement ps, int i) throws SQLException {
						ProjectConfiguration.ItemDetail itemDetail = projectItemConfiguration.getItemDetail().get(i);
						ps.setInt(1, projectItemConfiguration.getProjId());
						ps.setInt(2, projectItemConfiguration.getSubProjId()==null?projectItemConfiguration.getSubProjId():0);
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

		@Override
		public ProjectConfiguration getProjectItemConfiguration(ProjectConfiguration projectConfiguration) {
			String sql = "Select * from pricedetail where active = 1 and projectId = '"+projectConfiguration.getProjId()+"'";
			jdbcTemplate = new JdbcTemplate(dataSource);

			List<ProjectConfiguration.ItemDetail> itemList = new ArrayList<ProjectConfiguration.ItemDetail>();
			List<Map<String, Object>> rows = jdbcTemplate.queryForList(sql);

			for (Map<String, Object> row : rows) {
				itemList.add(buildPricedItem(row));
			}
			projectConfiguration.setItemDetail(itemList);
			return projectConfiguration;
		}

}
