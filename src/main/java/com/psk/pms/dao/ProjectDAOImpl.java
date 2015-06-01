package com.psk.pms.dao;
import com.psk.pms.model.EMDDetail;
import com.psk.pms.model.ProjDescDetail;
import com.psk.pms.model.ProjectDetail;
import com.psk.pms.model.SubProjectDetail;
import org.apache.log4j.Logger;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.datasource.DriverManagerDataSource;

import java.math.BigDecimal;
import java.util.*;

public class ProjectDAOImpl implements ProjectDAO {

	private DriverManagerDataSource dataSource;
	private JdbcTemplate jdbcTemplate;

	private static final Logger LOGGER = Logger.getLogger(ProjectDAOImpl.class);
	public void setDataSource(DriverManagerDataSource dataSource) {
		this.dataSource = dataSource;
	}

	public boolean saveProject(final ProjectDetail projectDetail){
		String createSql = "INSERT INTO project (ProjName, AliasProjName, AgreementNum, CERNum, Amount, "
				+ "ContractorName, ContractorAdd, ContractorValue, AgreementValue, TenderValue, " +
				"ExcessInAmount, ExcessInPercentage, TenderDate, "
				+ "AgreementDate, CommencementDate, CompletedDate, AgreementPeriod , LastUpdatedBy ,LastUpdatedAt ,AddSecurityDeposit) " +
				"VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";

		String updateSql = "UPDATE project set AgreementNum  = ?, CERNum = ?, Amount = ?, ContractorName = ?," +
				"ContractorAdd = ?, ContractorValue = ?, AgreementValue = ?, TenderValue=?, ExcessInAmount = ?," +
				"ExcessInPercentage = ?, TenderDate = ?, AgreementDate = ?," +
				"CommencementDate = ?, CompletedDate = ?, AgreementPeriod = ? ,LastUpdatedBy = ?,LastUpdatedAt = ?,AddSecurityDeposit=? WHERE ProjId = ?";

		jdbcTemplate = new JdbcTemplate(dataSource);
		if(!"Y".equalsIgnoreCase(projectDetail.getIsUpdate())){
			jdbcTemplate.update(createSql, new Object[] { projectDetail.getProjectName(),
					projectDetail.getAliasName(),
					projectDetail.getAgreementNo(), 
					projectDetail.getCerNo(), 
					projectDetail.getAmount(),
					projectDetail.getContractorName(), 
					projectDetail.getContractorAddress(),
					projectDetail.getContractValue(), 
					projectDetail.getAgreementValue(), 
					projectDetail.getTenderValue(), 
					projectDetail.getExAmount(),
					projectDetail.getExPercentage(), 
					projectDetail.getTenderSqlDate(), 
					projectDetail.getAgreementSqlDate(),
					projectDetail.getCommencementSqlDate(), 
					projectDetail.getCompletionSqlDate(), 
					projectDetail.getAgreementPeriod(),
                    projectDetail.getLastUpdatedBy(),
                    projectDetail.getLastUpdatedAt(),
                    projectDetail.getAddSecurityDeposit()
			});
		} else {
			jdbcTemplate.update(updateSql, new Object[] { 
					projectDetail.getAgreementNo(), 
					projectDetail.getCerNo(), 
					projectDetail.getAmount(),
					projectDetail.getContractorName(), 
					projectDetail.getContractorAddress(),
					projectDetail.getContractValue(), 
					projectDetail.getAgreementValue(), 
					projectDetail.getTenderValue(), 
					projectDetail.getExAmount(),
					projectDetail.getExPercentage(), 
					projectDetail.getTenderSqlDate(), 
					projectDetail.getAgreementSqlDate(),
					projectDetail.getCommencementSqlDate(), 
					projectDetail.getCompletionSqlDate(), 
					projectDetail.getAgreementPeriod(),
                    projectDetail.getLastUpdatedBy(),
                    projectDetail.getLastUpdatedAt(),
                    projectDetail.getAddSecurityDeposit(),
					projectDetail.getProjId()
			});
		}
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

	public Map<String, String> getSubAliasProjectNames(String projectId) {
		String sql = "select SubProjId, AliasSubProjName from subproject where ProjId = ?";	 
		Map<String, String> aliasSubProjects = new LinkedHashMap<String, String>();
		jdbcTemplate = new JdbcTemplate(dataSource);
		List<Map<String, Object>> rows = jdbcTemplate.queryForList(sql, new Object[] {projectId});
		for (Map<String, Object> row : rows) {
			aliasSubProjects.put(String.valueOf(row.get("SubProjId")), (String)row.get("AliasSubProjName"));
		}	 
		return aliasSubProjects;
	}

	public boolean saveSubProject(final SubProjectDetail subProjectDetail){
		String insertSql = "INSERT INTO subproject (ProjId, SubProjName, AliasSubProjName, AgreementNum, CERNum, "
				+ "Amount, ContractorName, ContractorAdd, ContractorValue, AgreementValue, TenderValue, ExcessInAmount, "
				+ "ExcessInPercentage, TenderDate, AgreementDate, CommencementDate, CompletedDate, AgreementPeriod,LastUpdatedBy ,LastUpdatedAt ,SubAddSecurityDeposit) " +
				"VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?,?,?,?)";

		String updateSql = "UPDATE subproject set AgreementNum  = ?, CERNum = ?, Amount = ?, ContractorName = ?," +
				"ContractorAdd = ?, ContractorValue = ?, AgreementValue = ?, TenderValue=?, ExcessInAmount = ?," +
				"ExcessInPercentage = ?, TenderDate = ?, AgreementDate = ?, CommencementDate = ?, CompletedDate = ?,"+ 
				"AgreementPeriod = ?, LastUpdatedBy = ?,  LastUpdatedAt = ?, SubAddSecurityDeposit = ? WHERE SubProjId = ?";

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
					subProjectDetail.getSubContractorAddress(),
					subProjectDetail.getSubContractValue(), 
					subProjectDetail.getSubAgreementValue(), 
					subProjectDetail.getSubTenderValue(), 
					subProjectDetail.getSubExAmount(),
					subProjectDetail.getSubExPercentage(), 
					subProjectDetail.getSubTenderSqlDate(), 
					subProjectDetail.getSubAgreementSqlDate(),
					subProjectDetail.getSubCommencementSqlDate(), 
					subProjectDetail.getSubCompletionSqlDate(), 
					subProjectDetail.getSubAgreementPeriod(),
                    subProjectDetail.getLastUpdatedBy(),
                    subProjectDetail.getLastUpdatedAt(),
                    subProjectDetail.getSubAddSecurityDeposit()
			});
		}else {
			jdbcTemplate.update(updateSql, new Object[] { 
					subProjectDetail.getSubAgreementNo(), 
					subProjectDetail.getSubCerNo(), 
					subProjectDetail.getSubAmount(),
					subProjectDetail.getSubContractorName(), 
					subProjectDetail.getSubContractorAddress(),
					subProjectDetail.getSubContractValue(), 
					subProjectDetail.getSubAgreementValue(), 
					subProjectDetail.getSubTenderValue(), 
					subProjectDetail.getSubExAmount(),
					subProjectDetail.getSubExPercentage(), 
					subProjectDetail.getSubTenderSqlDate(), 
					subProjectDetail.getSubAgreementSqlDate(),
					subProjectDetail.getSubCommencementSqlDate(), 
					subProjectDetail.getSubCompletionSqlDate(), 
					subProjectDetail.getSubAgreementPeriod(),
                    subProjectDetail.getLastUpdatedBy(),
                    subProjectDetail.getLastUpdatedAt(),
                    subProjectDetail.getSubAddSecurityDeposit(),
                    subProjectDetail.getSubProjId()
			});
		}
		return true;
	}

	public boolean saveProjDesc(final ProjDescDetail projDescDetail){
		String insertSql = "INSERT INTO projectDesc (ProjId, SubProjId, WorkType, QuantityInFig, QuantityInWords, "
				+ "Description, AliasDescription, RateInFig, RateInWords, Amount,LastUpdatedBy ,LastUpdatedAt) " +
				"VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?,?,?)";

		String updateSql = "UPDATE projectDesc set WorkType  = ?, QuantityInFig = ?, QuantityInWords = ?, Description = ?," +
				"AliasDescription = ?, RateInFig = ?, RateInWords = ?, Amount=?, LastUpdatedBy =?,LastUpdatedAt=? WHERE ProjDescId = ?";

		jdbcTemplate = new JdbcTemplate(dataSource);
		if(!"Y".equalsIgnoreCase(projDescDetail.getIsUpdate())){
			jdbcTemplate.update(insertSql, new Object[] {projDescDetail.getAliasProjectName(), projDescDetail.getAliasSubProjectName(),
					projDescDetail.getWorkType(),projDescDetail.getQuantityInFig(), projDescDetail.getQuantityInWords(), 
					projDescDetail.getDescription(),projDescDetail.getAliasDescription(), 
					projDescDetail.getRateInFig(),projDescDetail.getRateInWords(), 
					projDescDetail.getProjDescAmount(),projDescDetail.getLastUpdatedBy(),projDescDetail.getLastUpdatedAt()
			});
		} else {
			jdbcTemplate.update(updateSql, new Object[] {projDescDetail.getWorkType(),projDescDetail.getQuantityInFig(), projDescDetail.getQuantityInWords(), 
					projDescDetail.getDescription(),projDescDetail.getAliasDescription(), 
					projDescDetail.getRateInFig(),projDescDetail.getRateInWords(), 
					projDescDetail.getProjDescAmount(),projDescDetail.getLastUpdatedBy(),projDescDetail.getLastUpdatedAt(), projDescDetail.getProjDescId()
			});
		}
		return true;
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
		
		BigDecimal exPercentage = (BigDecimal)row.get("ExcessInPercentage");
		projDoc.setExPercentage(exPercentage.toString());
		
		projDoc.setAgreementSqlDate((Date)row.get("AgreementDate"));
		projDoc.setCommencementSqlDate((Date)row.get("CommencementDate"));
		projDoc.setCompletionSqlDate((Date)row.get("CompletedDate"));
		projDoc.setAgreementPeriod((Integer) row.get("AgreementPeriod"));

        BigDecimal addSecurityDeposit = (BigDecimal)row.get("AddSecurityDeposit");
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
		
		BigDecimal exPercentage = (BigDecimal)row.get("ExcessInPercentage");
		subProjDoc.setSubExPercentage(exPercentage.toString());

		subProjDoc.setSubTenderSqlDate((Date)row.get("TenderDate"));
		subProjDoc.setSubAgreementSqlDate((Date)row.get("AgreementDate"));
		subProjDoc.setSubCommencementSqlDate((Date)row.get("CommencementDate"));
		subProjDoc.setSubCompletionSqlDate((Date)row.get("CompletedDate"));
		subProjDoc.setSubAgreementPeriod((Integer) row.get("AgreementPeriod"));

        BigDecimal addSecurityDeposit = (BigDecimal)row.get("SubAddSecurityDeposit");
        subProjDoc.setSubAddSecurityDeposit(addSecurityDeposit.toString());
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

	public List<ProjDescDetail> projectDescDetailList(Integer subProjectId) {
		String sql = projDescDetailQuery + " where SubProjId = "+subProjectId;
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
				new Object[] { aliasName });
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

	public boolean isAliasDescriptionAlreadyExisting(String projectId, String subProjId, String aliasDescription) {
		String sql = "SELECT COUNT(*) FROM projectdesc where ProjId = ? and SubProjId = ? and AliasDescription = ?";
		jdbcTemplate = new JdbcTemplate(dataSource);
		int total = jdbcTemplate.queryForObject(sql, Integer.class,
				new Object[] { projectId, subProjId, aliasDescription });
		if (total == 0) {
			return false;
		}
		return true;
	}

	private ProjDescDetail buildProjectDescDetail(Map<String, Object> row) {
		ProjDescDetail projDescDetail = new ProjDescDetail();
		projDescDetail.setProjId((Integer) row.get("ProjId"));
		projDescDetail.setAliasProjectName((String) row.get("AliasProjName"));
		projDescDetail.setSubProjId((Integer) row.get("SubProjId"));
		projDescDetail.setAliasSubProjectName((String) row.get("AliasSubProjName"));
		projDescDetail.setWorkType((String) row.get("WorkType"));
		BigDecimal quantityInFig = (BigDecimal)row.get("QuantityInFig");
		projDescDetail.setQuantityInFig(quantityInFig.toString());
		projDescDetail.setQuantityInWords((String) row.get("QuantityInWords"));
		projDescDetail.setDescription((String) row.get("Description"));
		projDescDetail.setAliasDescription((String) row.get("AliasDescription"));
		BigDecimal rateInFig = (BigDecimal)row.get("RateInFig");
		projDescDetail.setRateInFig(rateInFig.toString());
		projDescDetail.setRateInWords((String) row.get("RateInWords"));
		BigDecimal amount = (BigDecimal)row.get("Amount");
		projDescDetail.setProjDescAmount(amount.toString());
		projDescDetail.setProjDescId((Integer) row.get("ProjDescId"));
		return projDescDetail;
	}

	@Override
	public ProjDescDetail getProjectDescDetail(String projDescId) {
		String sql = projDescDetail + ",  p.AliasProjName, s.AliasSubProjName FROM projectdesc as d "
				+ "INNER JOIN subproject as s ON d.SubProjId = s.SubProjId "
				+ "JOIN project as p ON s.ProjId = p.ProjId WHERE d.ProjDescId = "+projDescId;
		jdbcTemplate = new JdbcTemplate(dataSource);             
		ProjDescDetail projDescDetail = null;
		List<Map<String, Object>> rows = jdbcTemplate.queryForList(sql);

		for (Map<String, Object> row : rows) {
			projDescDetail = buildProjectDescDetail(row);
		} 
		return projDescDetail;
	}

	private String projQuery = "SELECT  ProjId, ProjName, AliasProjName, AgreementNum, "
			+ "CERNum, Amount, ContractorName, ContractorAdd, AgreementValue, "
			+ "TenderValue, ContractorValue, ExcessInAmount, ExcessInPercentage, "
			+ "TenderDate, AgreementDate, CommencementDate, CompletedDate, "
			+ "AgreementPeriod,AddSecurityDeposit FROM project";

	private String subProjQuery = "SELECT SubProjId, SubProjName, AliasSubProjName, AgreementNum, "
			+ "CERNum, Amount, ContractorName, ContractorAdd, AgreementValue, "
			+ "TenderValue, ContractorValue, ExcessInAmount, ExcessInPercentage, "
			+ "TenderDate, AgreementDate, CommencementDate, CompletedDate, "
			+ "AgreementPeriod, ProjId, SubAddSecurityDeposit FROM subproject";

	private String subProj = "SELECT s.SubProjId, s.SubProjName, s.AliasSubProjName, s.AgreementNum, "
			+ "s.CERNum, s.Amount, s.ContractorName, s.ContractorAdd, s.AgreementValue, "
			+ "s.TenderValue, s.ContractorValue, s.ExcessInAmount, s.ExcessInPercentage, "
			+ "s.TenderDate, s.AgreementDate, s.CommencementDate, s.CompletedDate, "
			+ "s.AgreementPeriod, s.ProjId ,s.SubAddSecurityDeposit";

	private String projDescDetailQuery = "SELECT ProjId, SubProjId, WorkType, QuantityInFig, QuantityInWords, "
			+ "Description, AliasDescription, RateInFig, RateInWords, Amount, ProjDescId FROM projectdesc";

	private String projDescDetail = "SELECT d.ProjId, d.SubProjId, d.WorkType, d.QuantityInFig, d.QuantityInWords, "
			+ "d.Description, d.AliasDescription, d.RateInFig, d.RateInWords, d.Amount, d.ProjDescId";
	
	private String emdDatesQuery = "select EmdAmount, EmdStartDate, EmdEndDate, EmdType, EmdExtensionDate from emddetail";


}
