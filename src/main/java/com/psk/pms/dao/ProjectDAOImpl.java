package com.psk.pms.dao;

import com.mysql.jdbc.StringUtils;
import com.psk.pms.model.*;
import com.psk.pms.model.DescItemDetail.ItemDetail;
import org.apache.log4j.Logger;
import org.springframework.jdbc.core.BatchPreparedStatementSetter;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.datasource.DriverManagerDataSource;

import java.math.BigDecimal;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.*;

import static com.psk.pms.dao.PMSMasterQuery.*;
public class ProjectDAOImpl implements ProjectDAO {

	private DriverManagerDataSource dataSource;
	private JdbcTemplate jdbcTemplate;

	private static final Logger LOGGER = Logger.getLogger(ProjectDAOImpl.class);
	public void setDataSource(DriverManagerDataSource dataSource) {
		this.dataSource = dataSource;
	}

	public boolean saveProject(final ProjectDetail projectDetail){
		String createSql = "INSERT INTO project (ProjName, AliasProjName, AgreementNum, CERNum, Amount, "
				+ "ContractorName, ContractorAliasName , ContractorAdd, ContractorValue, AgreementValue, TenderValue, " +
				"ExcessInAmount, ExcessInPercentage, LessInPercentage , TenderDate, "
				+ "AgreementDate, CommencementDate, CompletedDate, AgreementPeriod , LastUpdatedBy ,LastUpdatedAt ,AddSecurityDeposit,PerformanceGuarantee) " +
				"VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?,?,?,?)";

		String updateSql = "UPDATE project set AgreementNum  = ?, CERNum = ?, Amount = ?, ContractorName = ?,ContractorAliasName = ?," +
				"ContractorAdd = ?, ContractorValue = ?, AgreementValue = ?, TenderValue=?, ExcessInAmount = ?," +
				"ExcessInPercentage = ?,LessInPercentage = ?, TenderDate = ?, AgreementDate = ?," +
				"CommencementDate = ?, CompletedDate = ?, AgreementPeriod = ? ,LastUpdatedBy = ?,LastUpdatedAt = ?,AddSecurityDeposit=?,PerformanceGuarantee=? WHERE ProjId = ?";

		jdbcTemplate = new JdbcTemplate(dataSource);
		if(!"Y".equalsIgnoreCase(projectDetail.getIsUpdate())){
			jdbcTemplate.update(createSql, new Object[] { projectDetail.getProjectName(),
					projectDetail.getAliasName(),
					projectDetail.getAgreementNo(), 
					projectDetail.getCerNo(), 
					projectDetail.getAmount(),
					projectDetail.getContractorName(),
					projectDetail.getAliasContractorName(),
					projectDetail.getContractorAddress(),
					projectDetail.getContractValue(), 
					projectDetail.getAgreementValue(), 
					projectDetail.getTenderValue(), 
					projectDetail.getExAmount(),
					projectDetail.getExPercentage(),
					projectDetail.getLessPercentage(),
					projectDetail.getTenderSqlDate(), 
					projectDetail.getAgreementSqlDate(),
					projectDetail.getCommencementSqlDate(), 
					projectDetail.getCompletionSqlDate(), 
					projectDetail.getAgreementPeriod(),
                    projectDetail.getLastUpdatedBy(),
                    projectDetail.getLastUpdatedAt(),
                    projectDetail.getAddSecurityDeposit(),
					projectDetail.getPerformanceGuarantee()
			});
		} else {
			jdbcTemplate.update(updateSql, new Object[] { 
					projectDetail.getAgreementNo(), 
					projectDetail.getCerNo(), 
					projectDetail.getAmount(),
					projectDetail.getContractorName(),
					projectDetail.getAliasContractorName(),
					projectDetail.getContractorAddress(),
					projectDetail.getContractValue(), 
					projectDetail.getAgreementValue(), 
					projectDetail.getTenderValue(), 
					projectDetail.getExAmount(),
					projectDetail.getExPercentage(),
					projectDetail.getLessPercentage(),
					projectDetail.getTenderSqlDate(), 
					projectDetail.getAgreementSqlDate(),
					projectDetail.getCommencementSqlDate(), 
					projectDetail.getCompletionSqlDate(), 
					projectDetail.getAgreementPeriod(),
                    projectDetail.getLastUpdatedBy(),
                    projectDetail.getLastUpdatedAt(),
                    projectDetail.getAddSecurityDeposit(),
					projectDetail.getPerformanceGuarantee(),
					projectDetail.getProjId()
			});
		}
		return true;
	}
	
	public boolean saveItem(Item item) {
		String createSql = "INSERT INTO itemcodes (itemName, itemUnit) "
				+ "VALUES (?, ?)";
		jdbcTemplate = new JdbcTemplate(dataSource);
		jdbcTemplate.update(createSql,
				new Object[] { item.getItemName(), item.getItemUnit() });
		return true;
	}

	public Map<String, String> getAliasProjectNames(){
		String sql = "select ProjId, AliasProjName from project";	 
		jdbcTemplate = new JdbcTemplate(dataSource);
		List<Map<String, Object>> rows = jdbcTemplate.queryForList(sql);
		Map<String, String> aliasProjects = new LinkedHashMap<String, String>();
		aliasProjects.put("0", "--Please Select--");
		for (Map<String, Object> row : rows) {
			aliasProjects.put(String.valueOf(row.get("ProjId")), (String)row.get("AliasProjName"));
		}	 
		return aliasProjects;
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

	public Map<String, String> getSubAliasProjectNames(String projectId) {
		Map<String, String> aliasSubProjects = new LinkedHashMap<String, String>();
		jdbcTemplate = new JdbcTemplate(dataSource);
		String sql;
		List<Map<String, Object>> rows;
		if ("" != projectId) {
			sql = "select SubProjId, AliasSubProjName from subproject where ProjId = ?";
			rows = jdbcTemplate.queryForList(sql, new Object[]{projectId});
		} else {
			sql = "select SubProjId, AliasSubProjName from subproject";
			rows = jdbcTemplate.queryForList(sql);
		}

		for (Map<String, Object> row : rows) {
			aliasSubProjects.put(String.valueOf(row.get("SubProjId")), (String)row.get("AliasSubProjName"));
		}	 
		return aliasSubProjects;
	}

	public boolean saveSubProject(final SubProjectDetail subProjectDetail){
		String insertSql = "INSERT INTO subproject (ProjId, SubProjName, AliasSubProjName, AgreementNum, CERNum, "
				+ "Amount, ContractorName,ContractorAliasName, ContractorAdd, ContractorValue, AgreementValue, TenderValue, ExcessInAmount, "
				+ "ExcessInPercentage,LessInPercentage, TenderDate, AgreementDate, CommencementDate, CompletedDate, AgreementPeriod," +
				"LastUpdatedBy ,LastUpdatedAt ,SubAddSecurityDeposit,SubPerformanceGuarantee) " +
				"VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?,?,?,?,?,?,?)";

		String updateSql = "UPDATE subproject set AgreementNum  = ?, CERNum = ?, Amount = ?, ContractorName = ?,ContractorAliasName = ?," +
				"ContractorAdd = ?, ContractorValue = ?, AgreementValue = ?, TenderValue=?, ExcessInAmount = ?," +
				"ExcessInPercentage = ?,LessInPercentage = ?, TenderDate = ?, AgreementDate = ?, CommencementDate = ?, CompletedDate = ?,"+
				"AgreementPeriod = ?, LastUpdatedBy = ?,  LastUpdatedAt = ?, SubAddSecurityDeposit = ? ,SubPerformanceGuarantee = ? WHERE SubProjId = ?";

		jdbcTemplate = new JdbcTemplate(dataSource);	
		if(!"Y".equalsIgnoreCase(subProjectDetail.getIsUpdate())){
			jdbcTemplate.update(insertSql, new Object[] {
					subProjectDetail.getProjId(), 
					subProjectDetail.getSubProjectName(),
					subProjectDetail.getAliasSubProjName(), 
					subProjectDetail.getSubAgreementNo(), 
					subProjectDetail.getSubCerNo(), 
					subProjectDetail.getSubAmount(),
					subProjectDetail.getSubContractorName(),
					subProjectDetail.getSubAliasContractorName(),
					subProjectDetail.getSubContractorAddress(),
					subProjectDetail.getSubContractValue(), 
					subProjectDetail.getSubAgreementValue(), 
					subProjectDetail.getSubTenderValue(), 
					subProjectDetail.getSubExAmount(),
					subProjectDetail.getSubExPercentage(),
					subProjectDetail.getSubLessPercentage(),
					subProjectDetail.getSubTenderSqlDate(), 
					subProjectDetail.getSubAgreementSqlDate(),
					subProjectDetail.getSubCommencementSqlDate(), 
					subProjectDetail.getSubCompletionSqlDate(), 
					subProjectDetail.getSubAgreementPeriod(),
                    subProjectDetail.getLastUpdatedBy(),
                    subProjectDetail.getLastUpdatedAt(),
                    subProjectDetail.getSubAddSecurityDeposit(),
					subProjectDetail.getSubPerformanceGuarantee()
			});
		}else {
			jdbcTemplate.update(updateSql, new Object[] { 
					subProjectDetail.getSubAgreementNo(), 
					subProjectDetail.getSubCerNo(), 
					subProjectDetail.getSubAmount(),
					subProjectDetail.getSubContractorName(),
					subProjectDetail.getSubAliasContractorName(),
					subProjectDetail.getSubContractorAddress(),
					subProjectDetail.getSubContractValue(), 
					subProjectDetail.getSubAgreementValue(), 
					subProjectDetail.getSubTenderValue(), 
					subProjectDetail.getSubExAmount(),
					subProjectDetail.getSubExPercentage(),
					subProjectDetail.getSubLessPercentage(),
					subProjectDetail.getSubTenderSqlDate(), 
					subProjectDetail.getSubAgreementSqlDate(),
					subProjectDetail.getSubCommencementSqlDate(), 
					subProjectDetail.getSubCompletionSqlDate(), 
					subProjectDetail.getSubAgreementPeriod(),
                    subProjectDetail.getLastUpdatedBy(),
                    subProjectDetail.getLastUpdatedAt(),
                    subProjectDetail.getSubAddSecurityDeposit(),
					subProjectDetail.getSubPerformanceGuarantee(),
                    subProjectDetail.getSubProjId()
			});
		}
		return true;
	}

	public boolean saveProjDesc(final ProjDescDetail projDescDetail){

		String updateSql = "UPDATE projectDesc set WorkType  = ?, QuantityInFig = ?, QuantityInWords = ?, Description = ?," +
				"AliasDescription = ?, RateInFig = ?, RateInWords = ?, Amount=?, LastUpdatedBy =?,LastUpdatedAt=? WHERE ProjDescId = ?";
		String insertSql = null;
		jdbcTemplate = new JdbcTemplate(dataSource);
		if(!"Y".equalsIgnoreCase(projDescDetail.getIsUpdate())){
			if(projDescDetail.isSubProjectDesc()){
				insertSql = "INSERT INTO projectDesc (ProjId, SubProjId,SerialNumber ,WorkType, QuantityInFig, QuantityInWords, "
					+ "Description, AliasDescription, RateInFig, RateInWords, Amount,LastUpdatedBy ,LastUpdatedAt) " +
					"VALUES (?, ? , ?, ?, ?, ?, ?, ?, ?, ?, ?,?,?)";
				jdbcTemplate.update(insertSql, new Object[] {projDescDetail.getAliasProjectName(), projDescDetail.getAliasSubProjectName(),projDescDetail.getSerialNumber(),
						projDescDetail.getWorkType(),projDescDetail.getQuantityInFig(), projDescDetail.getQuantityInWords(), 
						projDescDetail.getDescription(),projDescDetail.getAliasDescription(), 
						projDescDetail.getRateInFig(),projDescDetail.getRateInWords(), 
						projDescDetail.getProjDescAmount(),projDescDetail.getLastUpdatedBy(),projDescDetail.getLastUpdatedAt()
				});
			} else {
				insertSql = "INSERT INTO projectDesc (ProjId,SerialNumber ,WorkType, QuantityInFig, QuantityInWords, "
					+ "Description, AliasDescription, RateInFig, RateInWords, Amount,LastUpdatedBy ,LastUpdatedAt) " +
					"VALUES (?, ?, ? , ?, ?, ?, ?, ?, ?, ?,?,?)";
				jdbcTemplate.update(insertSql, new Object[] {projDescDetail.getAliasProjectName(),projDescDetail.getSerialNumber(),
						projDescDetail.getWorkType(),projDescDetail.getQuantityInFig(), projDescDetail.getQuantityInWords(), 
						projDescDetail.getDescription(),projDescDetail.getAliasDescription(), 
						projDescDetail.getRateInFig(),projDescDetail.getRateInWords(), 
						projDescDetail.getProjDescAmount(),projDescDetail.getLastUpdatedBy(),projDescDetail.getLastUpdatedAt()
				});
			}
			
			
		} else {
			jdbcTemplate.update(updateSql, new Object[] {projDescDetail.getWorkType(),projDescDetail.getQuantityInFig(), projDescDetail.getQuantityInWords(), 
					projDescDetail.getDescription(),projDescDetail.getAliasDescription(), 
					projDescDetail.getRateInFig(),projDescDetail.getRateInWords(), 
					projDescDetail.getProjDescAmount(),projDescDetail.getLastUpdatedBy(),projDescDetail.getLastUpdatedAt(), projDescDetail.getProjDescId()
			});
		}
		return true;
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
				ItemDetail itemDetail = descItemDetail.getItemDetail().get(i);
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
	
	public List<ItemDetail> getProjectData(final Integer projId){
		LOGGER.info("getProjectData projId:" + projId);
		String sql = "Select * from projdescitem where ProjId = "+projId;
		jdbcTemplate = new JdbcTemplate(dataSource);
		List<ItemDetail> itemDetailList = new ArrayList<ItemDetail>();
		List<Map<String, Object>> rows = jdbcTemplate.queryForList(sql);
		LOGGER.info("getProjectData rows:" + rows.size());
		for (Map<String, Object> row : rows) {
			itemDetailList.add(buildItemDetail(row));
		}
		LOGGER.info("getProjectData itemDetailList:" + itemDetailList.size());
		return itemDetailList;
	}

	public DescItemDetail getDataDescription(final DescItemDetail descItemDetail){
		String sql = "Select * from projdescitem where ProjDescId = "+descItemDetail.getProjDescId()+" and ProjDescSerial = '"+descItemDetail.getProjDescSerial()+"'";
		jdbcTemplate = new JdbcTemplate(dataSource);             

		List<ItemDetail> itemDetailList = new ArrayList<ItemDetail>();
		List<Map<String, Object>> rows = jdbcTemplate.queryForList(sql);

		for (Map<String, Object> row : rows) {
			itemDetailList.add(buildItemDetail(row));
		} 
		descItemDetail.setItemDetail(itemDetailList);
		return descItemDetail;
	}

	
	private ItemDetail buildItemDetail(Map<String, Object> row) {
		ItemDetail itemDetail = new ItemDetail();
		itemDetail.setItemName((String) row.get("ItemName"));
		itemDetail.setItemUnit((String) row.get("ItemUnit"));
		itemDetail.setItemQty((String) row.get("ItemQty"));
		itemDetail.setItemPrice((String) row.get("ItemPrice"));
		itemDetail.setItemCost((String) row.get("ItemCost"));
		return itemDetail;
	}

	public List<ProjectDetail> getProjectDocumentList() {
		jdbcTemplate = new JdbcTemplate(dataSource);             

		List<ProjectDetail> projDocList = new ArrayList<ProjectDetail>();
		List<Map<String, Object>> rows = jdbcTemplate.queryForList(projQuery);

		for (Map<String, Object> row : rows) {
			projDocList.add(buildProjectDetail(row));
		} 
		return projDocList;
	}
	
	public List<EMDDetail> getEMDDatesList() {
		jdbcTemplate = new JdbcTemplate(dataSource);             

		List<EMDDetail> emdList = new ArrayList<EMDDetail>();
		List<Map<String, Object>> rows = jdbcTemplate.queryForList(emdDatesQuery);

		for (Map<String, Object> row : rows) {
			emdList.add(buildEMDDetail(row));
		} 
		return emdList;
	}

	public ProjectDetail getProjectDocument(String projectId) {
		String sql = projQuery + " where ProjId ="+projectId;	
		jdbcTemplate = new JdbcTemplate(dataSource);             
		ProjectDetail projDoc = null;
		List<Map<String, Object>> rows = jdbcTemplate.queryForList(sql);

		for (Map<String, Object> row : rows) {
			projDoc = buildProjectDetail(row);
		} 
		return projDoc;
	}

	public SubProjectDetail getSubProjectDocument(String subProjectId) {
		String sql = subProj + ", p.AliasProjName from subproject s, project as p "
				+ "WHERE p.ProjId = s.ProjId and s.SubProjId ="+subProjectId;	

		jdbcTemplate = new JdbcTemplate(dataSource);             
		SubProjectDetail subProjDoc = null;
		List<Map<String, Object>> rows = jdbcTemplate.queryForList(sql);

		for (Map<String, Object> row : rows) {
			subProjDoc = buildSubProjectDetail(row);
		} 
		return subProjDoc;
	}
	
	private EMDDetail buildEMDDetail(Map<String, Object> row){
		EMDDetail emdDetail = new EMDDetail();
		BigDecimal emdAmount = (BigDecimal)row.get("EmdAmount");
		emdDetail.setEmdAmount(emdAmount.toString());		
		emdDetail.setSqlEmdStartDate((Date)row.get("EmdStartDate"));
		emdDetail.setSqlEmdEndDate((Date)row.get("EmdEndDate"));
		emdDetail.setEmdType((String)row.get("EmdType"));
		emdDetail.setEmdExtensionSqlDate((Date)row.get("EmdExtensionDate"));
		return emdDetail;
	}

	private ProjectDetail buildProjectDetail(Map<String, Object> row){
		ProjectDetail projDoc = new ProjectDetail();
		projDoc.setProjId((Integer) row.get("ProjId"));
		projDoc.setProjectName((String) row.get("ProjName"));
		projDoc.setAliasName((String) row.get("AliasProjName"));
		projDoc.setAgreementNo((String) row.get("AgreementNum"));
		projDoc.setCerNo((String) row.get("CERNum"));
		projDoc.setContractorName((String) row.get("ContractorName"));
		projDoc.setAliasContractorName((String) row.get("ContractorAliasName"));
		projDoc.setContractorAddress((String) row.get("ContractorAdd"));
		projDoc.setTenderSqlDate((Date)row.get("TenderDate"));
		
		BigDecimal amount = (BigDecimal)row.get("Amount");
		projDoc.setAmount(amount.toString());
		
		BigDecimal aggValue = (BigDecimal)row.get("AgreementValue");
		projDoc.setAgreementValue(aggValue.toString());
		
		BigDecimal tenderValue = (BigDecimal)row.get("TenderValue");
		projDoc.setTenderValue(tenderValue.toString());
		
		BigDecimal contValue = (BigDecimal)row.get("ContractorValue");
		projDoc.setContractValue(contValue.toString());
		
		BigDecimal exAmount = (BigDecimal)row.get("ExcessInAmount");
		projDoc.setExAmount(exAmount.toString());

		BigDecimal exPercentage = (BigDecimal) row.get("ExcessInPercentage");
		if (null == exPercentage) {
			projDoc.setExPercentage("");
		} else {
			projDoc.setExPercentage(exPercentage.toString());
		}

		BigDecimal lessPercentage = (BigDecimal)row.get("LessInPercentage");
		if (null == lessPercentage) {
			projDoc.setLessPercentage("");
		}else {
			projDoc.setLessPercentage(lessPercentage.toString());
		}
		
		projDoc.setAgreementSqlDate((Date)row.get("AgreementDate"));
		projDoc.setCommencementSqlDate((Date)row.get("CommencementDate"));
		projDoc.setCompletionSqlDate((Date)row.get("CompletedDate"));
		projDoc.setAgreementPeriod((Integer) row.get("AgreementPeriod"));

        BigDecimal addSecurityDeposit = (BigDecimal)row.get("AddSecurityDeposit");
        projDoc.setAddSecurityDeposit(addSecurityDeposit.toString());

		BigDecimal performanceGuarantee = (BigDecimal)row.get("PerformanceGuarantee");
		if (null == performanceGuarantee) {
			projDoc.setPerformanceGuarantee("");
		} else {
			projDoc.setPerformanceGuarantee(performanceGuarantee.toString());
		}
		projDoc.setAddSecurityDeposit(addSecurityDeposit.toString());

		return projDoc;
	}

	private SubProjectDetail buildSubProjectDetail(Map<String, Object> row){
		SubProjectDetail subProjDoc = new SubProjectDetail();
		subProjDoc.setProjId((Integer) row.get("ProjId"));
		subProjDoc.setAliasProjName((String) row.get("AliasProjName"));
		subProjDoc.setSubProjId((Integer) row.get("SubProjId"));
		subProjDoc.setSubProjectName((String) row.get("SubProjName"));
		subProjDoc.setAliasSubProjName((String) row.get("AliasSubProjName"));
		subProjDoc.setSubAgreementNo((String) row.get("AgreementNum"));
		subProjDoc.setSubCerNo((String) row.get("CERNum"));
		subProjDoc.setSubContractorName((String) row.get("ContractorName"));
		subProjDoc.setSubAliasContractorName((String) row.get("ContractorAliasName"));
		subProjDoc.setSubContractorAddress((String) row.get("ContractorAdd"));

		BigDecimal amount = (BigDecimal)row.get("Amount");
		subProjDoc.setSubAmount(amount.toString());

		BigDecimal aggValue = (BigDecimal)row.get("AgreementValue");
		subProjDoc.setSubAgreementValue(aggValue.toString());

		BigDecimal tenderValue = (BigDecimal)row.get("TenderValue");
		subProjDoc.setSubTenderValue(tenderValue.toString());

		BigDecimal contValue = (BigDecimal)row.get("ContractorValue");
		subProjDoc.setSubContractValue(contValue.toString());

		BigDecimal exAmount = (BigDecimal)row.get("ExcessInAmount");
		subProjDoc.setSubExAmount(exAmount.toString());

		BigDecimal exPercentage = (BigDecimal) row.get("ExcessInPercentage");
		if (null == exPercentage) {
			subProjDoc.setSubExPercentage("");
		} else {
			subProjDoc.setSubExPercentage(exPercentage.toString());
		}

		BigDecimal lessPercentage = (BigDecimal) row.get("LessInPercentage");
		if (null == lessPercentage) {
			subProjDoc.setSubLessPercentage("");
		} else {
			subProjDoc.setSubLessPercentage(lessPercentage.toString());
		}

		subProjDoc.setSubTenderSqlDate((Date)row.get("TenderDate"));
		subProjDoc.setSubAgreementSqlDate((Date)row.get("AgreementDate"));
		subProjDoc.setSubCommencementSqlDate((Date)row.get("CommencementDate"));
		subProjDoc.setSubCompletionSqlDate((Date)row.get("CompletedDate"));
		subProjDoc.setSubAgreementPeriod((Integer) row.get("AgreementPeriod"));

        BigDecimal addSecurityDeposit = (BigDecimal)row.get("SubAddSecurityDeposit");
        subProjDoc.setSubAddSecurityDeposit(addSecurityDeposit.toString());
		subProjDoc.setSubAddSecurityDeposit(addSecurityDeposit.toString());

		BigDecimal subPerformanceGuarantee = (BigDecimal)row.get("SubPerformanceGuarantee");
		if (null == subPerformanceGuarantee) {
			subProjDoc.setSubPerformanceGuarantee("");
		} else {
			subProjDoc.setSubPerformanceGuarantee(subPerformanceGuarantee.toString());
		}
		return subProjDoc;
	}

	public List<SubProjectDetail> getSubProjectDocumentList(Integer projectId) {
		String sql = subProjQuery + " where ProjId = "+projectId;
		jdbcTemplate = new JdbcTemplate(dataSource);             

		List<SubProjectDetail> subProjDocList = new ArrayList<SubProjectDetail>();
		List<Map<String, Object>> rows = jdbcTemplate.queryForList(sql);

		for (Map<String, Object> row : rows) {
			subProjDocList.add(buildSubProjectDetail(row));
		} 
		return subProjDocList;
	}

	public List<ProjDescDetail> getSubProjectDescDetailList(Integer subProjectId) {
		String sql = projDescDetailQuery + " where SubProjId = "+subProjectId;
		jdbcTemplate = new JdbcTemplate(dataSource);             

		List<ProjDescDetail> projectDescDetailList = new ArrayList<ProjDescDetail>();
		List<Map<String, Object>> rows = jdbcTemplate.queryForList(sql);

		for (Map<String, Object> row : rows) {
			projectDescDetailList.add(buildProjectDescDetail(row));
		} 
		return projectDescDetailList;
	}
	
	public List<ProjDescDetail> getProjectDescDetailList(Integer projectId,boolean searchUnderProject) {
		String sql;
		if (searchUnderProject) {
			sql = projDescDetailQuery + " where ProjId = " + projectId + " and SubProjId is null";
		} else {
			sql = projDescDetailQuery + " where SubProjId = " + projectId;
		}
		jdbcTemplate = new JdbcTemplate(dataSource);             

		List<ProjDescDetail> projectDescDetailList = new ArrayList<ProjDescDetail>();
		List<Map<String, Object>> rows = jdbcTemplate.queryForList(sql);

		for (Map<String, Object> row : rows) {
			projectDescDetailList.add(buildProjectDescDetail(row));
		} 
		return projectDescDetailList;
	}
	
	public boolean isAliasProjectAlreadyExisting(String aliasName){
		String sql = "SELECT COUNT(*) FROM project where AliasProjName = ?";
		jdbcTemplate = new JdbcTemplate(dataSource);
		int total = jdbcTemplate.queryForObject(sql, Integer.class,
				new Object[]{aliasName});
		if (total == 0) {
			return false;
		}
		return true;
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

	public boolean isAliasSubProjectAlreadyExisting(String subAliasName, Integer projectId) {
		String sql = "SELECT COUNT(*) FROM subproject where AliasSubProjName = ? and ProjId = ?";
		jdbcTemplate = new JdbcTemplate(dataSource);
		int total = jdbcTemplate.queryForObject(sql, Integer.class,
				new Object[] { subAliasName,projectId });
		if (total == 0) {
			return false;
		}
		return true;
	}

	public boolean isAliasDescriptionAlreadyExisting(ProjDescDetail projectDescDetail) {
		String sql = null;
		int total = 0;
		jdbcTemplate = new JdbcTemplate(dataSource);
		if(!projectDescDetail.isSubProjectDesc()){
			LOGGER.info("There is no sub project selected");
			sql = "SELECT COUNT(*) FROM projectdesc where ProjId = ? and AliasDescription = ?";
			total = jdbcTemplate.queryForObject(sql, Integer.class,
					new Object[] { projectDescDetail.getAliasProjectName(), projectDescDetail.getAliasDescription() });
		}else{
			LOGGER.info("There is a sub project selected");
			sql = "SELECT COUNT(*) FROM projectdesc where ProjId = ? and SubProjId = ? and AliasDescription = ?";
			total = jdbcTemplate.queryForObject(sql, Integer.class,
					new Object[] { projectDescDetail.getAliasProjectName(), projectDescDetail.getAliasSubProjectName(), projectDescDetail.getAliasDescription() });
		}
		
		if (total == 0) {
			return false;
		}
		return true;
	}

	public boolean isSerialNumberAlreadyExisting(ProjDescDetail projectDescDetail) {
		String sql = null;
		int total = 0;
		jdbcTemplate = new JdbcTemplate(dataSource);
		if(!projectDescDetail.isSubProjectDesc()){
			LOGGER.info("method {} , There is no sub project selected" + "isSerialNumberAlreadyExisting");
			sql = "SELECT COUNT(*) FROM projectdesc where ProjId = ? and SerialNumber = ?";
			total = jdbcTemplate.queryForObject(sql, Integer.class,
					new Object[] { projectDescDetail.getAliasProjectName(), projectDescDetail.getSerialNumber() });
		}else{
			LOGGER.info("method {} , There is sub project selected" + "isSerialNumberAlreadyExisting");
			sql = "SELECT COUNT(*) FROM projectdesc where ProjId = ? and SubProjId = ? and SerialNumber = ?";
			total = jdbcTemplate.queryForObject(sql, Integer.class,
					new Object[] { projectDescDetail.getAliasProjectName(), projectDescDetail.getAliasSubProjectName(), projectDescDetail.getSerialNumber() });
		}

		if (total == 0) {
			return false;
		}
		return true;
	}

	private ProjDescDetail buildProjectDescDetail(Map<String, Object> row) {
		ProjDescDetail projDescDetail = new ProjDescDetail();
		projDescDetail.setProjId((Integer) row.get("ProjId"));
		projDescDetail.setAliasProjectName((String) row.get("AliasProjName"));
		projDescDetail.setSerialNumber((String) row.get("SerialNumber"));
		projDescDetail.setSubProjId((Integer) row.get("SubProjId"));
		projDescDetail.setAliasSubProjectName((String) row.get("AliasSubProjName"));
		projDescDetail.setWorkType((String) row.get("WorkType"));
		BigDecimal quantityInFig = (BigDecimal)row.get("QuantityInFig");
		if (null == quantityInFig) {
			projDescDetail.setQuantityInFig("");
		} else {
			projDescDetail.setQuantityInFig(quantityInFig.toString());
		}
		projDescDetail.setQuantityInWords((String) row.get("QuantityInWords"));
		projDescDetail.setDescription((String) row.get("Description"));
		projDescDetail.setAliasDescription((String) row.get("AliasDescription"));
		BigDecimal rateInFig = (BigDecimal)row.get("RateInFig");
		projDescDetail.setRateInFig(rateInFig.toString());
		projDescDetail.setRateInWords((String) row.get("RateInWords"));
		BigDecimal amount = (BigDecimal)row.get("Amount");
		if (null == amount) {
			projDescDetail.setProjDescAmount("");
		} else {
			projDescDetail.setProjDescAmount(amount.toString());
		}
		projDescDetail.setProjDescId((Integer) row.get("ProjDescId"));
		return projDescDetail;
	}

	@Override
	public ProjDescDetail getProjectDescDetail(String projDescId, String subProject) {
		String sql = null;
		LOGGER.info("subProject value" + subProject);
		if(!StringUtils.isNullOrEmpty(subProject)){
			LOGGER.info("subProject value for sub project" + subProject);
			sql = projDescDetail + ",  p.AliasProjName, s.AliasSubProjName FROM projectdesc as d "
			+ "INNER JOIN subproject as s ON d.SubProjId = s.SubProjId "
			+ "JOIN project as p ON s.ProjId = p.ProjId WHERE d.ProjDescId = "+projDescId;
		}else{
			LOGGER.info("subProject value for project" + subProject);
			sql = projDescDetail + ",  p.AliasProjName FROM projectdesc as d "
			+ "JOIN project as p ON d.ProjId = p.ProjId WHERE d.ProjDescId = "+projDescId;
		}
		
		jdbcTemplate = new JdbcTemplate(dataSource);             
		ProjDescDetail projDescDetail = null;
		List<Map<String, Object>> rows = jdbcTemplate.queryForList(sql);

		for (Map<String, Object> row : rows) {
			projDescDetail = buildProjectDescDetail(row);
		} 
		return projDescDetail;
	}

	public void deleteProjectDescription(String projectDescriptionId) {
		jdbcTemplate = new JdbcTemplate(dataSource);
		int noOfRows = jdbcTemplate.update(deleteProjDescDetailQuery, new Object []{projectDescriptionId});
		LOGGER.info("Number of rows deleted : "+ noOfRows);
	}
	
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
	

	private String projQuery = "SELECT  ProjId, ProjName, AliasProjName, AgreementNum, "
			+ "CERNum, Amount, ContractorName,ContractorAliasName, ContractorAdd, AgreementValue, "
			+ "TenderValue, ContractorValue, ExcessInAmount, ExcessInPercentage,LessInPercentage, "
			+ "TenderDate, AgreementDate, CommencementDate, CompletedDate, "
			+ "AgreementPeriod,AddSecurityDeposit,PerformanceGuarantee FROM project";

	private String subProjQuery = "SELECT SubProjId, SubProjName, AliasSubProjName, AgreementNum, "
			+ "CERNum, Amount, ContractorName,ContractorAliasName, ContractorAdd, AgreementValue, "
			+ "TenderValue, ContractorValue, ExcessInAmount, ExcessInPercentage,LessInPercentage ,"
			+ "TenderDate, AgreementDate, CommencementDate, CompletedDate, "
			+ "AgreementPeriod, ProjId, SubAddSecurityDeposit,SubPerformanceGuarantee FROM subproject";

	private String subProj = "SELECT s.SubProjId, s.SubProjName, s.AliasSubProjName, s.AgreementNum, "
			+ "s.CERNum, s.Amount, s.ContractorName,s.ContractorAliasName, s.ContractorAdd, s.AgreementValue, "
			+ "s.TenderValue, s.ContractorValue, s.ExcessInAmount, s.ExcessInPercentage,s.LessInPercentage, "
			+ "s.TenderDate, s.AgreementDate, s.CommencementDate, s.CompletedDate, "
			+ "s.AgreementPeriod, s.ProjId ,s.SubAddSecurityDeposit , s.SubPerformanceGuarantee";

	private String projDescDetailQuery = "SELECT ProjId, SubProjId, SerialNumber , WorkType, QuantityInFig, QuantityInWords, "
			+ "Description, AliasDescription, RateInFig, RateInWords, Amount, ProjDescId FROM projectdesc";

	private String projDescDetail = "SELECT d.ProjId, d.SubProjId , d.SerialNumber , d.WorkType, d.QuantityInFig, d.QuantityInWords, "
			+ "d.Description, d.AliasDescription, d.RateInFig, d.RateInWords, d.Amount, d.ProjDescId";
	
	private String emdDatesQuery = "select EmdAmount, EmdStartDate, EmdEndDate, EmdType, EmdExtensionDate from emddetail";
	
	private String deleteProjDescDetailQuery = "DELETE FROM projectdesc where ProjDescId = ?";

	@Override
	public void deleteProject(Integer projectId) {
		deleteEmddetailByProjectId(projectId);
		deleteProjectDescriptionByProjectId(projectId);
		deleteSubProjectByProjectId(projectId);
		jdbcTemplate = new JdbcTemplate(dataSource);
		int noOfRows = jdbcTemplate.update(DELETEPROJECTBYPROJECTID, new Object []{projectId});
		LOGGER.info("method = deleteProject , Number of rows deleted : "+ noOfRows +" projectId :" + projectId );
	}

	@Override
	public void deleteSubProjectByProjectId(Integer projectId) {
		jdbcTemplate = new JdbcTemplate(dataSource);
		int noOfRows = jdbcTemplate.update(DELETESUBPROJECTBYPROJECTID, new Object []{projectId});
		LOGGER.info("method = deleteSubProjectByProjectId , Number of rows deleted : "+ noOfRows +" projectId :" + projectId );
	}

	@Override
	public void deleteSubProjectBySubProjectId(Integer subProjectId) {
		deleteEmddetailBySubProjectId(subProjectId);
		deleteProjectDescriptionBySubProjectId(subProjectId);
		jdbcTemplate = new JdbcTemplate(dataSource);
		int noOfRows = jdbcTemplate.update(DELETESUBPROJECTBYSUBPROJECTID, new Object []{subProjectId});
		LOGGER.info("method = deleteSubProjectBySubProjectId , Number of rows deleted : "+ noOfRows +" subProjectId :" + subProjectId );
	}

	@Override
	public void deleteEmddetailByProjectId(Integer projectId) {
		jdbcTemplate = new JdbcTemplate(dataSource);
		int noOfRows = jdbcTemplate.update(DELETEEMDDETAILBYPROJECTID, new Object []{projectId});
		LOGGER.info("method = deleteEmddetailByProjectId , Number of rows deleted : "+ noOfRows +" projectId :" + projectId );
	}

	@Override
	public void deleteEmddetailBySubProjectId(Integer subProjectId) {
		jdbcTemplate = new JdbcTemplate(dataSource);
		int noOfRows = jdbcTemplate.update(DELETEEMDDETAILBYSUBPROJECTID, new Object []{subProjectId});
		LOGGER.info("method = deleteEmddetailBySubProjectId , Number of rows deleted : "+ noOfRows +" subProjectId :" + subProjectId );
	}

	@Override
	public void deleteProjectDescriptionByProjectId(Integer projectId) {
		jdbcTemplate = new JdbcTemplate(dataSource);
		int noOfRows = jdbcTemplate.update(DELETEPROJECTDESCRIPTIONBYPROJECTID, new Object []{projectId});
		LOGGER.info("method = deleteProjectDescriptionByProjectId , Number of rows deleted : "+ noOfRows +" projectId :" + projectId );
	}

	@Override
	public void deleteProjectDescriptionBySubProjectId(Integer subProjectId) {
		jdbcTemplate = new JdbcTemplate(dataSource);
		int noOfRows = jdbcTemplate.update(DELETEPROJECTDESCRIPTIONBYSUBPROJECTID, new Object []{subProjectId});
		LOGGER.info("method = deleteProjectDescriptionBySubProjectId , Number of rows deleted : "+ noOfRows +" subProjectId :" + subProjectId );
	}
}
