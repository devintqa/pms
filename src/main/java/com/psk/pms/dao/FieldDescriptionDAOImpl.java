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
import com.psk.pms.model.QuoteDetails.SupplierQuoteDetails;
import com.psk.pms.model.SearchDetail;


public class FieldDescriptionDAOImpl implements FieldDescriptionDAO {

	@Qualifier("jdbcTemplate")
	@Autowired
	private JdbcTemplate jdbcTemplate;

	ResultTransformer transformer = new ResultTransformer();
	
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
			if(indent.getIndentDescList().size() > 0){
				indentId = insertIndent(indent);
				
				for(IndentDesc indentDesc: indent.getIndentDescList()){
					Integer indentDescId = insertIndentDesc(indentId, indentDesc);
					insertIndentDescItem(indentDescId, indentDesc.getItemDetails());
				}
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
		
		String indentDescSql = "INSERT INTO IndentDesc (IndentId, ProjDescId, Quantity, Metric, Comments)"
				+ "VALUES (?, ?, ?, ?, ?)";

		Connection connection = jdbcTemplate.getDataSource().getConnection();
		PreparedStatement preparedStatement = connection.prepareStatement(indentDescSql, Statement.RETURN_GENERATED_KEYS);

		preparedStatement.setString(1, indentId.toString());
		preparedStatement.setString(2, indentDesc.getProjDescId());
		preparedStatement.setDouble(3, indentDesc.getPlannedQty());
		preparedStatement.setString(4, indentDesc.getMetric());
		preparedStatement.setString(5, indentDesc.getComments());

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
			projDescDetail = transformer.buildProjectDescDetail(row);
		}
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
			indentList.add(transformer.buildIndent(row));
		}
		return indentList;
	}

	private Indent fetchIndent(String indentId) {
		String sql = null;
		if (null!=indentId) {
			sql = "SELECT IndentId, StartDate, EndDate, Status, ProjId from Indent where indentId='"+indentId+"'";
		} 
		List < Map < String, Object >> rows = jdbcTemplate.queryForList(sql);
		Indent indent = transformer.buildIndent(rows.get(0));
		return indent;
	}
	
	private List<IndentDesc> fetchIndentDesc(String indentId) {
		List<IndentDesc> indentDescList = new ArrayList<IndentDesc>();
		String sql = null;
		if (null!=indentId) {
			sql = "SELECT fd.AliasDescription, d.ProjDescId, d.Quantity, d.Metric, d.IndentDescId, d.IndentId, d.Comments from IndentDesc d, fieldprojectdesc fd where fd.ProjDescId = d.ProjDescId and IndentId='"+indentId+"'";
		} 
		List < Map < String, Object >> rows = jdbcTemplate.queryForList(sql);
		for(Map < String, Object > row : rows){
			IndentDesc indentDesc = transformer.buildIndentDesc(row);
			indentDescList.add(indentDesc);
		}
		return indentDescList;
	}
	
	private List<IndentDesc.ItemDetail> fetchIndentDescItem(String indentDescId) {
		List<IndentDesc.ItemDetail> indentDescItemList = new ArrayList<IndentDesc.ItemDetail>();
		String sql = null;
		if (null!=indentDescId) {
			sql = "SELECT ItemName, ItemType, ItemQty, ItemUnit, IndentItemId, IndentDescId from IndentDescItem where IndentDescId='"+indentDescId+"'";
		} 
		List < Map < String, Object >> rows = jdbcTemplate.queryForList(sql);
		for(Map < String, Object > row : rows){
			IndentDesc.ItemDetail itemDetail = transformer.buildIndentDescItem(row);
			indentDescItemList.add(itemDetail);
		}
		return indentDescItemList;
	}
	
	@Override
	public String placeIndentRequest(Indent indent) {
		String status = "";
		
		indent.setStatus(getNextIndentStatus(indent.getStatus()));
		String updateIndentStatusSql = "UPDATE Indent set Status = ?, LastUpdatedBy = ? WHERE IndentId = ?";
		Integer row = jdbcTemplate.update(updateIndentStatusSql, new Object[]{indent.getStatus(), indent.getEmployeeId(), indent.getIndentId()});
		if(row==1)
			updateIndentStatusSql = "UPDATE IndentDescItem set IndentItemStatus = ? where IndentDescId in (SELECT IndentDescId from IndentDesc WHERE IndentId = ?)";
			row = jdbcTemplate.update(updateIndentStatusSql, new Object[]{indent.getStatus(), indent.getIndentId()});
			status = "Indent successfully processed for "+indent.getStatus();
		return status;
	}
	
	private String getNextIndentStatus(String currentStatus){
		if(currentStatus.equalsIgnoreCase("NEW")){
			return "PENDING LEVEL 1 APPROVAL";
		}else if(currentStatus.equalsIgnoreCase("PENDING LEVEL 1 APPROVAL")){
			return "PENDING LEVEL 2 APPROVAL";
		}else if(currentStatus.equalsIgnoreCase("PENDING LEVEL 2 APPROVAL")){
			return "PENDING PURCHASE";
		}else if(currentStatus.equalsIgnoreCase("REJECTED")){
			return currentStatus;
		}else{
			return null;
		}
	}
	
	private String getIndentStatusFilterByRole(String role){
		if(role == null){
			return "'NEW', 'REJECTED'";
		}else if(role.equalsIgnoreCase("No Role Tagged")){
			return "'NEW', 'REJECTED'";
		}else if(role.isEmpty()){
			return "'NEW', 'REJECTED', 'PENDING PURCHASE'";
		}else if(role.equalsIgnoreCase("SITE MANAGER")){
			return "'NEW', 'REJECTED', 'PENDING PURCHASE'";
		}else if(role.equalsIgnoreCase("GENERAL MANAGER")){
			return "'NEW', 'REJECTED', 'PENDING LEVEL 2 APPROVAL'";
		}else if(role.equalsIgnoreCase("TECHNICAL MANAGER")){
			return "'NEW', 'REJECTED', 'PENDING LEVEL 1 APPROVAL'";
		}else{
			return null;
		}
	}
	
	@Override
	public Map<String, Object> getRequestedIndentQty(Integer projId, String userRole) {
		Map < String, Object > descQty = new HashMap< String, Object>();
		String status = getIndentStatusFilterByRole(userRole);
		String sql = null;
		if (null!=projId) {
			sql = "select sum(quantity) as qty, projdescid from indentdesc where indentId in (select indentId from indent where projid='"+projId+"' and status not in ("+status+")) group by projdescid";
		} 
		System.out.println(sql);
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
		Map<String, Object> indentUsedOnDesc = getRequestedIndentQty(searchDetail.getProjId(), searchDetail.getRole());
		for (Map<String, Object> row : rows) {
			ProjDescDetail fieldDesc = transformer.buildProjectDescDetail(row);
			Double totalQty = new Double(fieldDesc.getQuantity());
			if(null!=indentUsedOnDesc.get(fieldDesc.getProjDescId().toString())){
				availedDescIndentQtyBigD = (BigDecimal) indentUsedOnDesc.get(fieldDesc.getProjDescId().toString());
			}
			else{
				availedDescIndentQtyBigD  = new BigDecimal(0);
			}
			Double availedQty = availedDescIndentQtyBigD.doubleValue();
			Double availableQty = new Double(0);
			if(availedQty <= totalQty){
				availableQty = totalQty - availedQty;
			}
			
			fieldDesc.setAvailableQty(availableQty.toString());
			projectDescDetailList.add(fieldDesc);
		}
		return projectDescDetailList;
	}

	@Override
	public Indent getIndent(String indentId) {
		Indent indent = null;
		List<IndentDesc> indentDescList = new ArrayList<IndentDesc>();
		if(null!=indentId){
			indent = fetchIndent(indentId);
		}
		List<IndentDesc> tmpIndentDescList = fetchIndentDesc(indentId);
		for(IndentDesc indentDesc : tmpIndentDescList){
			indentDesc.setItemDetails(fetchIndentDescItem(indentDesc.getIndentDescId()));
			indentDescList.add(indentDesc);
		}
		indent.setIndentDescList(indentDescList);
		return indent;
	}

	@Override
	public List<Indent> getIndentListByStatus(String status) {
		List<Indent> indentList = new ArrayList<Indent>();
		String sql = null;
		if (null!=status) {
			sql = "SELECT i.IndentId, i.StartDate, i.EndDate, i.Status, i.ProjId, p.aliasProjName from Indent i, Project p where i.ProjId = p.ProjId and i.Status='"+status+"' group by i.projId";
		} 
		List < Map < String, Object >> rows = jdbcTemplate.queryForList(sql);

		for (Map < String, Object > row: rows) {
			indentList.add(transformer.buildIndent(row));
		}
		return indentList;
	}
	
	@Override
	public Integer upateIndentDescription(Indent indent) {
		Integer indentId = new Integer(indent.getIndentId());
		try {
			if(indent.getIndentDescList().size() > 0){
				updateIndent(indent);
				
				for(IndentDesc indentDesc: indent.getIndentDescList()){
					Integer indentDescId = new Integer(indentDesc.getIndentDescId());
					updateIndentDesc(indentId, indentDesc);
					updateIndentDescItem(indentDescId, indentDesc.getItemDetails());
				}
			}

		} catch (Exception e) {
			e.printStackTrace();
			return 0;
		}
		return indentId;
	}
	
	@Override
	public Map<String, String> isActiveIndentExistForDescription(String projId){
		String sql = "select i.IndentId, i.ProjDescId, f.AliasDescription from indentdesc i, fieldprojectdesc f where i.indentId in (select indentId from indent where projid='"+projId+"' and status  in ('PENDING LEVEL 1 APPROVAL')) and i.ProjDescId = f.ProjDescId";
		List<Map<String, Object>> rows = jdbcTemplate.queryForList(sql);
		Map<String, String> activeIndent= null;
		if(rows.size() > 1){
			activeIndent = new HashMap<String, String>();
		}
		for (Map < String, Object > row: rows) {
			activeIndent.put(row.get("ProjDescId").toString(), row.get("AliasDescription").toString() + " has active indent:" +row.get("IndentId").toString());
		}
		return activeIndent;
	}

	private Integer updateIndent(Indent indent) {
		indent.setStatus(indent.getStatus());
		String updateIndentStatusSql = "UPDATE Indent set StartDate = ?, EndDate = ?, LastUpdatedBy = ? WHERE IndentId = ?";
		Integer updateStatus = jdbcTemplate.update(updateIndentStatusSql, new Object[]{indent.getStartDate(), indent.getEndDate(), indent.getEmployeeId(), indent.getIndentId()});
		return updateStatus;
	}
	
	Integer updateIndentDesc(Integer indentId, IndentDesc indentDesc) throws SQLException{
		
		String updateIndentStatusSql = "UPDATE IndentDesc set Quantity = ?, Comments = ? where IndentDescId = ? and IndentId = ?";
		Integer updateStatus = jdbcTemplate.update(updateIndentStatusSql, new Object[]{new Double(indentDesc.getPlannedQty()), indentDesc.getComments(), indentDesc.getIndentDescId(), indentId});
		
		return updateStatus;
	}
	
	void updateIndentDescItem(final Integer indentDescId, final List<IndentDesc.ItemDetail> itemDetailList){
		
		jdbcTemplate.update("DELETE FROM IndentDescItem WHERE IndentDescId = ?", indentDescId);
		
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

}

