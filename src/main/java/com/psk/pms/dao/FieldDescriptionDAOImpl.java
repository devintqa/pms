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
import java.util.HashMap;
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
import com.psk.pms.model.IndentDesc;
import com.psk.pms.model.IndentDesc.ItemDetail;
import com.psk.pms.model.ProjDescDetail;
import com.psk.pms.model.SearchDetail;

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
	public Integer saveIndentDescription(final Indent indent) {
		Integer indentId = 0;
		try {
			indentId = insertIndent(indent);
			
			for(IndentDesc indentDesc: indent.getIndentDescList()){
				Integer indentDescId = insertIndentDesc(indentId, indentDesc);
				insertIndentDescItem(indentDescId, indentDesc.getItemDetails());
			}

		} catch (Exception e) {
			e.printStackTrace();
			return 0;
		}
		return indentId;
	}


	Integer insertIndent(Indent indent) throws SQLException{

		Integer generatedIndentId = null;

		String indentDescSql = "INSERT INTO Indent (ProjId, StartDate, EndDate, LastUpdatedBy, LastUpdatedAt) "
				+ "VALUES (?, ?, ?, ?, ?)";

		Connection connection = jdbcTemplate.getDataSource().getConnection();
		PreparedStatement preparedStatement = connection.prepareStatement(indentDescSql, Statement.RETURN_GENERATED_KEYS);

		preparedStatement.setString(1, indent.getProjId());
		preparedStatement.setString(2, indent.getStartDate());
		preparedStatement.setString(3, indent.getEndDate());
		preparedStatement.setString(4, indent.getEmployeeId());
		preparedStatement.setDate(5, new java.sql.Date(Calendar.getInstance().getTime().getTime()));

		preparedStatement.executeUpdate();
		ResultSet keys = preparedStatement.getGeneratedKeys();

		if (keys.next()) {
			generatedIndentId = keys.getInt(1); //id returned after insert execution
		} 

		return generatedIndentId;

	}

	Integer insertIndentDesc(Integer indentId, IndentDesc indentDesc) throws SQLException{

		Integer generatedIndentDescId = null;
		
		String indentDescSql = "INSERT INTO IndentDesc (IndentId, ProjDescId, Quantity, Metric)"
				+ "VALUES (?, ?, ?, ?)";

		Connection connection = jdbcTemplate.getDataSource().getConnection();
		PreparedStatement preparedStatement = connection.prepareStatement(indentDescSql, Statement.RETURN_GENERATED_KEYS);

		preparedStatement.setString(1, indentId.toString());
		preparedStatement.setString(2, indentDesc.getProjDescId());
		preparedStatement.setDouble(3, indentDesc.getPlannedQty());
		preparedStatement.setString(4, indentDesc.getMetric());

		preparedStatement.executeUpdate();
		ResultSet keys = preparedStatement.getGeneratedKeys();

		if (keys.next()) {
			generatedIndentDescId = keys.getInt(1); //id returned after insert execution
		} 

		return generatedIndentDescId;
	}
	
	void insertIndentDescItem(final Integer indentDescId, final List<IndentDesc.ItemDetail> itemDetailList){
		String indentDescItemSql = "INSERT INTO IndentDescItem (IndentDescId, ItemName, ItemType, ItemQty, ItemUnit)"
				+ "VALUES (?, ?, ?, ?, ?)";
		jdbcTemplate.batchUpdate(indentDescItemSql, new BatchPreparedStatementSetter() {

			@Override
			public void setValues(PreparedStatement ps, int i)
					throws SQLException {
				ItemDetail itemDetail = itemDetailList.get(i);
				ps.setString(1, indentDescId.toString());
				ps.setString(2, itemDetail.getItemName());
				ps.setString(3, itemDetail.getItemType());
				ps.setString(4, itemDetail.getItemQty());
				ps.setString(5, itemDetail.getItemUnit());
			}

			@Override
			public int getBatchSize() {
				return itemDetailList.size();
			}
		});
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

	@Override
	public List<Indent> getIndentList(SearchDetail searchDetail) {
		List<Indent> indentList = new ArrayList<Indent>();
		String sql = null;
		if (null!=searchDetail.getProjId()) {
			sql = "SELECT IndentId, StartDate, EndDate, Status, ProjId from Indent where ProjId='"+searchDetail.getProjId()+"'";
		} 
		List < Map < String, Object >> rows = jdbcTemplate.queryForList(sql);

		for (Map < String, Object > row: rows) {
			indentList.add(buildIndent(row));
		}
		return indentList;
	}

	private Indent buildIndent(Map<String, Object> row) {
		Indent indent = new Indent();
		indent.setProjId(row.get("ProjId").toString());
		indent.setIndentId(row.get("IndentId").toString());	
		indent.setStatus((String) row.get("Status"));
		indent.setStartDate((String) row.get("StartDate"));
		indent.setEndDate((String) row.get("EndDate"));
		return indent;
	}

	@Override
	public String placeIndentRequest(Indent indent) {
		String status = "";
		if(indent.getStatus().equalsIgnoreCase("new")){
			indent.setStatus("PENDING LEVEL 1 APPROVAL");
			String updateIndentStatusSql = "UPDATE Indent set Status = ?, LastUpdatedBy = ? WHERE IndentId = ?";
			Integer row = jdbcTemplate.update(updateIndentStatusSql, new Object[]{indent.getStatus(), indent.getEmployeeId(), indent.getIndentId()});
			if(row==1)
				status = "Indent requested succesfully";
		}else{
			status = "Indents in "+indent.getStatus().toLowerCase()+" status is invalid";
		}
		return status;
	}
	
	Map<String, Object> buildDescQtyMap(Map < String, Object > row){
		
		return row;
	}

	@Override
	public Map<String, Object> getRequestedIndentQty(Integer projId) {
		Map < String, Object > descQty = new HashMap< String, Object>();
		String sql = null;
		if (null!=projId) {
			sql = "select sum(quantity) as qty, projdescid from indentdesc where indentId in (select indentId from indent where projid='"+projId+"' and status <> 'NEW') group by projdescid";
		} 
		List < Map < String, Object >> rows = jdbcTemplate.queryForList(sql);
		for (Map < String, Object > row: rows) {
			descQty.put(row.get("projdescid").toString(), row.get("qty"));
		}
		return descQty;
	}

	@Override
	public List<ProjDescDetail> getFieldDescDetailList(SearchDetail searchDetail) {
		String sql = "SELECT ProjId, SubProjId, SerialNumber, WorkType, Quantity, Metric, Description, AliasDescription, PricePerQuantity, "
				+ "TotalCost, ProjDescId FROM " + DescriptionType.getDescriptionTableName(searchDetail.getSearchOn()) + " where ProjId = '" + searchDetail.getProjId() + "'" + " and SubProjId is null";

		List<ProjDescDetail> projectDescDetailList = new ArrayList<ProjDescDetail>();
		BigDecimal availedDescIndentQtyBigD  = new BigDecimal(0); 
		List<Map<String, Object>> rows = jdbcTemplate.queryForList(sql);
		Map<String, Object> indentUsedOnDesc = getRequestedIndentQty(searchDetail.getProjId());
		for (Map<String, Object> row : rows) {
			ProjDescDetail fieldDesc = buildProjectDescDetail(row);
			Double totalQty = new Double(fieldDesc.getQuantity());
			if(null!=indentUsedOnDesc.get(fieldDesc.getProjDescId().toString()))
				availedDescIndentQtyBigD = (BigDecimal) indentUsedOnDesc.get(fieldDesc.getProjDescId().toString());
			Double availedQty = availedDescIndentQtyBigD.doubleValue();
			Double availableQty = totalQty - availedQty;
			fieldDesc.setAvailableQty(availableQty.toString());
			projectDescDetailList.add(fieldDesc);
		}
		return projectDescDetailList;
	}

}

