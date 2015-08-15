package com.psk.pms.dao;

import com.psk.pms.model.DescItemDetail;
import com.psk.pms.model.DescItemDetail.ItemDetail;
import com.psk.pms.model.Item;
import com.psk.pms.model.ProjectConfiguration;
import com.psk.pms.model.ProjectItemDescription;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.jdbc.core.BatchPreparedStatementSetter;
import org.springframework.jdbc.core.JdbcTemplate;

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
			sql = "select itemNo, itemName, itemUnit from itemcodes where itemType = '"
					+ itemType + "' and itemName LIKE '%" + itemCode + "%'";
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
			System.out.println("57");
			sql = "select itemNo, itemName, itemUnit from itemcodes where itemType = '"
					+ itemType + "' and itemName LIKE '%" + itemCode + "%'";
			rows = jdbcTemplate.queryForList(sql);
		}
		for (Map<String, Object> row : rows) {
			descItems.put(String.valueOf(row.get("itemNo")),
					(String) row.get("itemName"));
		}
		return descItems;
	}

	public boolean isItemAlreadyExisting(String itemName) {
		String sql = "SELECT COUNT(*) FROM itemcodes where itemName = ?";
		int total = jdbcTemplate.queryForObject(sql, Integer.class,
				new Object[] { itemName });
		if (total == 0) {
			return false;
		}
		return true;
	}

	public boolean saveItem(Item item) {
		jdbcTemplate.update(
				SAVEITEMS,
				new Object[] { item.getItemName(), item.getItemUnit(),
						item.getItemType() });
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
			itemNames.put((String) row.get("itemName"),
					(String) row.get("itemType"));
		}
		return itemNames;
	}

	public boolean insertProjectDescriptionItems(final DescItemDetail descItemDetail) {
		String sql = "INSERT INTO projdescitem"
				+ "(ProjId, SubProjId, ProjDescId, ProjDescSerial, ItemName, ItemUnit, ItemQty, ItemPrice, ItemCost) "
				+ "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?)";

		String deleteSql = "DELETE from projdescitem where ProjDescId = "
				+ descItemDetail.getProjDescId() + " and ProjDescSerial = '"
				+ descItemDetail.getProjDescSerial() + "'";
		jdbcTemplate.execute(deleteSql);

		jdbcTemplate.batchUpdate(sql, new BatchPreparedStatementSetter() {

			@Override
			public void setValues(PreparedStatement ps, int i)
					throws SQLException {
				DescItemDetail.ItemDetail itemDetail = descItemDetail
						.getItemDetail().get(i);
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
		for (DescItemDetail.ItemDetail itemDetail : descItemDetail
				.getItemDetail()) {
			long itemCost = Double.valueOf(itemDetail.getItemCost())
					.longValue();
			sumItemCost = sumItemCost + itemCost;
		}

		String projectDescEstimate = "SELECT Quantity from projectdesc WHERE ProjDescId = '"
				+ descItemDetail.getProjDescId() + "'";
		List<Map<String, Object>> rows = jdbcTemplate
				.queryForList(projectDescEstimate);

		long totalCost = 0;
		for (Map<String, Object> row : rows) {
			BigDecimal quantity = (BigDecimal) row.get("Quantity");
			long qty = quantity.toBigInteger().longValue();
			totalCost = sumItemCost * qty;
		}

		String updateSql = "UPDATE projectdesc set PricePerQuantity = ?, TotalCost =?  WHERE ProjDescId = ?";
		jdbcTemplate.update(updateSql, new Object[] { sumItemCost, totalCost,
				descItemDetail.getProjDescId() });

		return true;
	}

	
	public DescItemDetail getBaseDescription(final DescItemDetail descItemDetail) {
		String sql = "Select * from basedescitem where BaseDescId = '" + descItemDetail.getBaseDescId() + "'";

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
		itemDetail.setItemPrice((String) row.get("ItemPrice"));
		return itemDetail;
	}



    public boolean insertDataDescription(final DescItemDetail descItemDetail) {
        String sql = "INSERT INTO projdescitem" +
                "(ProjId, SubProjId, ProjDescId, ProjDescSerial, ItemName, ItemUnit, ItemQty, ItemPrice, ItemCost) " +
                "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?)";

        String deleteSql = "DELETE from projdescitem where ProjDescId = " + descItemDetail.getProjDescId() + " and ProjDescSerial = '" + descItemDetail.getProjDescSerial() + "'";
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
        for (DescItemDetail.ItemDetail itemDetail : descItemDetail.getItemDetail()) {
            long itemCost = Double.valueOf(itemDetail.getItemCost()).longValue();
            sumItemCost = sumItemCost + itemCost;
        }

        String projectDescEstimate = "SELECT Quantity from projectdesc WHERE ProjDescId = '" + descItemDetail.getProjDescId() + "'";
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

    public DescItemDetail getDataDescription(final DescItemDetail descItemDetail) {
        String sql = "Select * from projdescitem where ProjDescId = " + descItemDetail.getProjDescId() + " and ProjDescSerial = '" + descItemDetail.getProjDescSerial() + "'";

        List<DescItemDetail.ItemDetail> itemDetailList = new ArrayList<DescItemDetail.ItemDetail>();
        List<Map<String, Object>> rows = jdbcTemplate.queryForList(sql);

        for (Map<String, Object> row : rows) {
            itemDetailList.add(buildItemDetail(row));
        }
        descItemDetail.setItemDetail(itemDetailList);
        return descItemDetail;
    }


    public List<DescItemDetail.ItemDetail> getProjectData(ProjectConfiguration projectConfiguration, boolean isEditSubProject) {
        String sql;
        if(isEditSubProject){
        	LOGGER.info("Into getProjectData() " + isEditSubProject + " Sub Project Id" + projectConfiguration.getSubProjId());
        	sql = "Select * from projdescitem where SubProjId = " + projectConfiguration.getSubProjId();
        }else{
        	sql = "Select * from projdescitem where ProjId = " + projectConfiguration.getProjId() + " and SubProjId = " + projectConfiguration.getSubProjId();
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

    public void deleteItemByProjectId(Integer projectId) {
        LOGGER.info("method = deleteItemByProjectId()");
        int noOfrowsDeleted = jdbcTemplate.update(DELETEPROJDESCITEMBYPROJECTID, new Object[]{projectId});
        LOGGER.info("No of rows deleted :" + noOfrowsDeleted);
    }


    public void deleteItemBySubProjectId(Integer subProjectId) {
        LOGGER.info("method = deleteItemBySubProjectId()");
        int noOfrowsDeleted = jdbcTemplate.update(DELETEPROJDESCITEMBYSUBPROJECTID, new Object[]{subProjectId});
        LOGGER.info("No of rows deleted :" + noOfrowsDeleted);
    }


    @Override
    public void deleteItemByProjectDescriptionId(String projectDescId) {
        LOGGER.info("method = deleteItemByProjectDescriptionId()");
        int noOfrowsDeleted = jdbcTemplate.update(DELETEPROJDESCAITEMBYPROJECTDESCID, new Object[]{projectDescId});
        LOGGER.info("No of rows deleted :" + noOfrowsDeleted);
    }

    @Override
    public void deleteItemByProjectDescItemId(Integer projectDescItemId) {
        LOGGER.info("method = deleteItemByProjectDescriptionItemId()");
        int noOfrowsDeleted = jdbcTemplate.update(DELETEPROJDESCAITEMBYPROJECTDESCITEMID, new Object[]{projectDescItemId});
        LOGGER.info("No of rows deleted :" + noOfrowsDeleted);
    }


    @Override
    public Set<String> fetchItemNames(String itemCategory) {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public List<ItemDetail> getDescItemNames(Map<String, Object> request) {
        List<DescItemDetail.ItemDetail> itemsDetail = new ArrayList<DescItemDetail.ItemDetail>();

        String sql;
        List<Map<String, Object>> rows = null;
        if ("" != request.get("itemName")) {
            sql = "select itemName, itemUnit, itemPrice from pricedetail where projectId = '" + request.get("projectId") + "' and subProjectId = '" + request.get("subProjectId")+ "' and itemType = '" + request.get("itemType") + "' and itemName LIKE '%" + request.get("itemName") + "%' and active = '1'";
            rows = jdbcTemplate.queryForList(sql);
        }
        for (Map<String, Object> row : rows) {
            ItemDetail itemDetail = new ItemDetail();
            itemDetail.setLabel((String) row.get("itemName"));
            itemDetail.setItemName((String) row.get("itemName"));
            itemDetail.setItemUnit((String) row.get("itemUnit"));
            itemDetail.setItemPrice(((BigDecimal) row.get("itemPrice")).toString());
            itemsDetail.add(itemDetail);
        }
        return itemsDetail;
    }
    
    @Override
    public List<ItemDetail> getBaseItemNames(Map<String, Object> request) {
        List<DescItemDetail.ItemDetail> itemsDetail = new ArrayList<DescItemDetail.ItemDetail>();
        System.out.println(request);
        String sql = null;
        List<Map<String, Object>> rows = null;
        if ("" != request.get("itemName")) {
            sql = "select itemType, itemName, itemUnit, itemPrice from govestpricedetail where itemType = '" + request.get("itemType") + "' and itemName LIKE '%" + request.get("itemName") + "%' and active = '1'";
            System.out.println(sql);
            rows = jdbcTemplate.queryForList(sql);
        }
        for (Map<String, Object> row : rows) {
            ItemDetail itemDetail = new ItemDetail();
            itemDetail.setLabel((String) row.get("itemName"));
            itemDetail.setItemName((String) row.get("itemName"));
            itemDetail.setItemUnit((String) row.get("itemUnit"));
            itemDetail.setItemType((String) row.get("itemType"));
            itemDetail.setItemPrice(((BigDecimal) row.get("itemPrice")).toString());
            itemsDetail.add(itemDetail);
        }
        return itemsDetail;
    }

    @Override
    public List<String> fetchItemTypes() {
        LOGGER.info("method = fetchItemTypes");
        List<String> itemTypes = jdbcTemplate.queryForList(FETCHITEMTYPES, String.class);
        LOGGER.info("No of rows fetched :" + itemTypes.size());
        return itemTypes;
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
        jdbcTemplate.update(DEACTIVATEEXISTINGPRICES,projectItemConfiguration.getProjId(),projectItemConfiguration.getSubProjId());
    }

    @Override
    public ProjectConfiguration getProjectItemConfiguration(ProjectConfiguration projectConfiguration, boolean isEditSubProject) {
        String sql;
        if(isEditSubProject){
        	LOGGER.info("Into Edit Sub Project " + isEditSubProject + " Sub Project Id" + projectConfiguration.getSubProjId());
        	sql = "Select * from pricedetail where active = 1 and subProjectId = " + projectConfiguration.getSubProjId();
        }else{
        	sql = "Select * from pricedetail where active = 1 and projectId = " + projectConfiguration.getProjId() + " and subProjectId = " + projectConfiguration.getSubProjId();
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
    public List<ProjectItemDescription> getProjectItemDescription(ProjectConfiguration projectConfiguration, boolean isEditSubProject, String itemName) {
    	String sql;
        if(isEditSubProject){
        	LOGGER.info("getProjectItemDescription() " + isEditSubProject + " Sub Project Id" + projectConfiguration.getSubProjId());
        	sql = "select pdi.ProjDescId,pdi.ProjDescSerial,pd.AliasDescription,\n" +
            "pdi.itemName,pdi.ItemQty,pdi.itemUnit from projdescitem pdi, projectdesc pd where\n" +
            "pdi.ProjDescId = pd.ProjDescId and pdi.SubProjId = '"+projectConfiguration.getSubProjId()+"' and pdi.itemName = '"+itemName+"'";
        }else{
        	sql = "select pdi.ProjDescId,pdi.ProjDescSerial,pd.AliasDescription,\n" +
            "pdi.itemName,pdi.ItemQty,pdi.itemUnit from projdescitem pdi, projectdesc pd where\n" +
            "pdi.ProjDescId = pd.ProjDescId and pdi.ProjId = '"+projectConfiguration.getProjId()+"' and pdi.SubProjId = '"+projectConfiguration.getSubProjId()+"' and pdi.itemName = '"+itemName+"'";
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
	public boolean insertBaseDescriptionItems(final DescItemDetail descItemDetail) {
		String sql = "INSERT INTO basedescitem"
				+ "(BaseDescId, ItemName, ItemUnit, ItemType, ItemQty, ItemPrice) "
				+ "VALUES (?, ?, ?, ?, ?, ?)";

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
				System.out.println("############"+itemDetail.getItemPrice());
				ps.setString(6, itemDetail.getItemPrice());
			}

			@Override
			public int getBatchSize() {
				return descItemDetail.getItemDetail().size();
			}
		});

		long sumItemPrice = 0;
		for (DescItemDetail.ItemDetail itemDetail : descItemDetail.getItemDetail()) {
			long itemPrice = Double.valueOf(itemDetail.getItemPrice()).longValue();
			sumItemPrice = sumItemPrice + itemPrice;
		}

		String updateSql = "UPDATE basedesc set PricePerQuantity = ? where BaseDescId = '" + descItemDetail.getBaseDescId() + "'";
		jdbcTemplate.update(updateSql, new Object[] { sumItemPrice});

		return true;
	}

}
