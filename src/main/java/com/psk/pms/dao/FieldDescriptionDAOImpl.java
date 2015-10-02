package com.psk.pms.dao;

import static com.psk.pms.dao.PmsMasterQuery.CREATEPROJECTFIELDDESCRIPTION;
import static com.psk.pms.dao.PmsMasterQuery.CREATEPROJECTFIELDDESCRIPTIONITEM;
import static com.psk.pms.dao.PmsMasterQuery.CREATESUBPROJECTFIELDDESCRIPTION;
import static com.psk.pms.dao.PmsMasterQuery.CREATESUBPROJECTFIELDDESCRIPTIONITEM;
import static com.psk.pms.dao.PmsMasterQuery.DELETEPROJECTFIELDDESCRIPTION;
import static com.psk.pms.dao.PmsMasterQuery.DELETEPROJECTFIELDDESCRIPTIONITEM;
import static com.psk.pms.dao.PmsMasterQuery.DELETESUBPROJECTFIELDDESCRIPTION;
import static com.psk.pms.dao.PmsMasterQuery.DELETESUBPROJECTFIELDDESCRIPTIONITEM;
import static com.psk.pms.dao.PmsMasterQuery.NOOFFIELDDESCASSOCIATEDTOPROJECT;
import static com.psk.pms.dao.PmsMasterQuery.NOOFFIELDDESCASSOCIATEDTOSUBPROJECT;
import static com.psk.pms.dao.PmsMasterQuery.projDescDetail;

import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.jdbc.core.BatchPreparedStatementSetter;
import org.springframework.jdbc.core.JdbcTemplate;

import com.mysql.jdbc.Statement;
import com.mysql.jdbc.StringUtils;
import com.psk.pms.Constants;
import com.psk.pms.constants.DescriptionType;
import com.psk.pms.model.Indent;
import com.psk.pms.model.Indent.ItemDetail;
import com.psk.pms.model.ProjDescDetail;

/**
 * Created by prakashbhanu57 on 8/18/2015.
 */
public class FieldDescriptionDAOImpl implements FieldDescriptionDAO {

	@Qualifier("jdbcTemplate")
	@Autowired
	private JdbcTemplate jdbcTemplate;

	private static final Logger LOGGER = Logger.getLogger(FieldDescriptionDAOImpl.class);

	@Override
	public boolean isFieldDescriptionDetailsExistsForProject(int projectId) {
		int noOfRows;

		noOfRows = jdbcTemplate.queryForObject(NOOFFIELDDESCASSOCIATEDTOPROJECT, Integer.class,
				(Integer) projectId);
		LOGGER.info("method = isFieldDescriptionDetailsExistsForProject , isDataPresent :" + (noOfRows != 0));
		if (noOfRows != 0) {
			return true;
		}
		return false;
	}

	@Override
	public boolean isFieldDescriptionDetailsExistsForSubProject(int subProjectId) {
		int noOfRows;
		noOfRows = jdbcTemplate.queryForObject(
				NOOFFIELDDESCASSOCIATEDTOSUBPROJECT, Integer.class,
				(Integer) subProjectId);
		LOGGER.info("method = isFieldDescriptionDetailsExistsForSubProject , isDataPresent :" + (noOfRows != 0));
		if (noOfRows != 0) {
			return true;
		}
		return false;
	}

	@Override
	public void createFieldDescription(int projectId, int subProjectId) {
		LOGGER.info("method = createFieldDescription, creating field description,for projectId :"
				+ projectId + " , subProjectId:" + subProjectId);
		if (0 == subProjectId) {
			jdbcTemplate.update(DELETEPROJECTFIELDDESCRIPTION, projectId);
			jdbcTemplate.update(DELETEPROJECTFIELDDESCRIPTIONITEM, projectId);
			jdbcTemplate.update(CREATEPROJECTFIELDDESCRIPTION, projectId);
			jdbcTemplate.update(CREATEPROJECTFIELDDESCRIPTIONITEM, projectId);
		} else {
			jdbcTemplate.update(DELETESUBPROJECTFIELDDESCRIPTION, subProjectId);
			jdbcTemplate.update(DELETESUBPROJECTFIELDDESCRIPTIONITEM, subProjectId);
			jdbcTemplate.update(CREATESUBPROJECTFIELDDESCRIPTION, subProjectId);
			jdbcTemplate.update(CREATESUBPROJECTFIELDDESCRIPTIONITEM, subProjectId);
		}
	}

	@Override
	public boolean saveIndentDescription(final Indent indent) {
		Integer generatedIndentId = null;
		try {
			String indentDescSql = "INSERT INTO " + DescriptionType.getDescriptionTableName(Constants.INDENT)
					+ " (ProjId, ProjDescId, Quantity, Metric, StartDate, EndDate, LastUpdatedBy, LastUpdatedAt) "
					+ "VALUES (?, ?, ?, ?, ?, ?, ?, ?)";

			Connection connection = jdbcTemplate.getDataSource().getConnection();
			PreparedStatement preparedStatement = connection.prepareStatement(indentDescSql, Statement.RETURN_GENERATED_KEYS);

			preparedStatement.setString(1, indent.getProjId());
			preparedStatement.setString(2, indent.getProjDescId());
			preparedStatement.setString(3, indent.getPlannedArea().toString());
			preparedStatement.setString(4, indent.getMetric());
			preparedStatement.setString(5, indent.getStartDate());
			preparedStatement.setString(6, indent.getEndDate());
			preparedStatement.setString(7, indent.getEmployeeId());
			preparedStatement.setDate(8, new java.sql.Date(Calendar.getInstance().getTime().getTime()));

			preparedStatement.executeUpdate();
			ResultSet keys = preparedStatement.getGeneratedKeys();

			if (keys.next()) {
				generatedIndentId = keys.getInt(1); //id returned after insert execution
			} 


			final Integer indentId = generatedIndentId;
			String indentDescItemSql = "INSERT INTO " + DescriptionType.getDescriptionItemTableName(Constants.INDENT)
					+ " (ProjId, IndentId, ProjDescId, ItemName, ItemType, ItemQty, ItemUnit, ItemPrice) "
					+ "VALUES (?, ?, ?, ?, ?, ?, ?, ?)";
			jdbcTemplate.batchUpdate(indentDescItemSql, new BatchPreparedStatementSetter() {

				@Override
				public void setValues(PreparedStatement ps, int i)
						throws SQLException {
					Indent.ItemDetail itemDetail = indent.getItemDetails().get(i);
					ps.setString(1, indent.getProjId());
					ps.setInt(2, indentId);
					ps.setString(3, indent.getProjDescId());
					ps.setString(4, itemDetail.getItemName());
					ps.setString(5, itemDetail.getItemType());
					ps.setString(6, itemDetail.getItemQty());
					ps.setString(7, itemDetail.getItemUnit());
					ps.setString(8, itemDetail.getItemPrice());
				}

				@Override
				public int getBatchSize() {
					return indent.getItemDetails().size();
				}
			});

		} catch (SQLException e) {
			e.printStackTrace();
			return false;
		}
		return true;
	}

	@Override
	public List<Indent> getIndentDescAndItems(int projDescId) {
		String sql = "SELECT ProjId, ProjDescId, Quantity, Metric, LastUpdatedBy, LastUpdatedAt, IndentId, StartDate, EndDate FROM indentdesc WHERE ProjDescId ="+projDescId;

		List<Indent> indentDescList = new ArrayList<Indent>();
		List<Map<String, Object>> rows = jdbcTemplate.queryForList(sql);
		ProjDescDetail projDescDetail = getPskFieldProjectDescription(new Integer(projDescId).toString());
		for (Map<String, Object> row : rows) {
			Indent indent = buildIndentDescDetail(row);
			indent.setAliasProjDesc(projDescDetail.getAliasDescription());
			indent.setMetric(projDescDetail.getMetric());
			indentDescList.add(indent);
		}
		return indentDescList;
	}


	public List<Indent.ItemDetail> getIndentItems(String indentId) {
		String sql = "SELECT ProjId, IndentId, ProjDescId, ItemName, ItemType, ItemQty, ItemUnit, ItemPrice, IndentItemId FROM indentitem WHERE IndentId ="+indentId;

		List<Indent.ItemDetail> itemList = new ArrayList<Indent.ItemDetail>();
		List<Map<String, Object>> rows = jdbcTemplate.queryForList(sql);

		for (Map<String, Object> row : rows) {
			itemList.add(buildIndentItem(row));
		}
		return itemList;
	}

	private ItemDetail buildIndentItem(Map<String, Object> row) {
		ItemDetail item = new ItemDetail();
		item.setItemName((String) row.get("itemName"));
		item.setItemType((String) row.get("itemType"));
		item.setItemQty((String) row.get("itemQty"));
		item.setItemUnit((String) row.get("itemUnit"));
		item.setItemPrice((String) row.get("itemPrice"));
		return item;
	}

	private Indent buildIndentDescDetail(Map<String, Object> row) {
		Indent indent = new Indent();
		indent.setIndentId(row.get("IndentId").toString());
		indent.setProjId(row.get("ProjId").toString());
		indent.setProjDescId(row.get("ProjDescId").toString());
		BigDecimal plannedArea = (BigDecimal) row.get("Quantity");
		if (null == plannedArea) {
			indent.setPlannedArea(null);
		} else {
			indent.setPlannedArea(plannedArea.doubleValue());
		}

		indent.setMetric((String) row.get("Metric"));
		indent.setStartDate((String) row.get("StartDate"));
		indent.setEndDate((String) row.get("EndDate"));
		indent.setItemDetails(getIndentItems(indent.getIndentId()));
		return indent;
	}

	@Override
	public ProjDescDetail getPskFieldProjectDescription(String projDescId) {
		String sql = null;
		if (!StringUtils.isNullOrEmpty(projDescId)) {
			sql = projDescDetail + " FROM "+ DescriptionType.getDescriptionTableName(Constants.FIELD) +" as d WHERE d.ProjDescId = " + projDescId;
		} 
		System.out.println(sql);
		ProjDescDetail projDescDetail = null;
		List < Map < String, Object >> rows = jdbcTemplate.queryForList(sql);

		for (Map < String, Object > row: rows) {
			projDescDetail = buildProjectDescDetail(row);
		}
		return projDescDetail;
	}
	private ProjDescDetail buildProjectDescDetail(Map < String, Object > row) {
		ProjDescDetail projDescDetail = new ProjDescDetail();
		projDescDetail.setProjId((Integer) row.get("ProjId"));
		projDescDetail.setAliasProjectName((String) row.get("AliasProjName"));
		projDescDetail.setSerialNumber((String) row.get("SerialNumber"));
		projDescDetail.setSubProjId((Integer) row.get("SubProjId"));
		projDescDetail.setAliasSubProjectName((String) row.get("AliasSubProjName"));
		projDescDetail.setWorkType((String) row.get("WorkType"));
		BigDecimal quantity = (BigDecimal) row.get("Quantity");
		projDescDetail.setMetric((String) row.get("Metric"));
		if (null == quantity) {
			projDescDetail.setQuantity("");
		} else {
			projDescDetail.setQuantity(quantity.toString());
		}
		projDescDetail.setDescription((String) row.get("Description"));
		projDescDetail.setAliasDescription((String) row.get("AliasDescription"));

		BigDecimal pricePerQuantity = (BigDecimal) row.get("PricePerQuantity");
		if (null == pricePerQuantity) {
			projDescDetail.setPricePerQuantity("");
		} else {
			projDescDetail.setPricePerQuantity(pricePerQuantity.toString());
		}
		BigDecimal totalCost = (BigDecimal) row.get("TotalCost");
		if (null == totalCost) {
			projDescDetail.setTotalCost("");
		} else {
			projDescDetail.setTotalCost(totalCost.toString());
		}
		projDescDetail.setProjDescId((Integer) row.get("ProjDescId"));
		return projDescDetail;
	}

	
}
