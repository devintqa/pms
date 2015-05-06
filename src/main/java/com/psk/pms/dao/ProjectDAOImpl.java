package com.psk.pms.dao;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.datasource.DriverManagerDataSource;

import com.psk.pms.model.ProjDescDetail;
import com.psk.pms.model.ProjectDetail;
import com.psk.pms.model.SubProjectDetail;

public class ProjectDAOImpl implements ProjectDAO {

	private DriverManagerDataSource dataSource;
	private JdbcTemplate jdbcTemplate;

	public void setDataSource(DriverManagerDataSource dataSource) {
		this.dataSource = dataSource;
	}

	public boolean saveProject(final ProjectDetail projectDetail){
		String sql = "INSERT INTO project" +
				"(ProjName, AliasProjName, AgreementNum, CERNum, Amount, ContractorName, ContractorAdd, ContractorValue, AgreementValue, TenderValue, " +
				"ExcessInAmount, ExcessInPercentage, TenderDate, AgreementDate, CommencementDate, CompletedDate, AgreementPeriod) " +
				"VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
		jdbcTemplate = new JdbcTemplate(dataSource);	
		jdbcTemplate.update(sql, new Object[] { projectDetail.getProjectName(),
				projectDetail.getAliasName(),projectDetail.getAgreementNo(), projectDetail.getCerNo(), projectDetail.getAmount(),
				projectDetail.getContractorName(), projectDetail.getContractorAddress(),
				projectDetail.getContractValue(), projectDetail.getAgreementValue(), projectDetail.getTenderValue(), projectDetail.getExAmount(),
				projectDetail.getExPercentage(), projectDetail.getTenderSqlDate(), projectDetail.getAgreementSqlDate(),
				projectDetail.getCommencementSqlDate(), projectDetail.getCompletionSqlDate(), projectDetail.getAgreementPeriod()
		});
		return true;
	}

	public Map<String, String> getAliasProjectNames(){
		String sql = "select ProjId, AliasProjName from project";	 
		jdbcTemplate = new JdbcTemplate(dataSource);
		List<Map<String, Object>> rows = jdbcTemplate.queryForList(sql);
		Map<String, String> aliasProjects = new LinkedHashMap<String, String>();
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
			aliasSubProjects.put(new Integer((int) row.get("SubProjId")).toString(), (String)row.get("AliasSubProjName"));
		}	 
		return aliasSubProjects;
	}

	public boolean saveSubProject(final SubProjectDetail subProjectDetail){
		String sql = "INSERT INTO subproject" +
				"(ProjId, SubProjName, AliasSubProjName, AgreementNum, CERNum, Amount, ContractorName, ContractorAdd, ContractorValue, AgreementValue, TenderValue, " +
				"ExcessInAmount, ExcessInPercentage, TenderDate, AgreementDate, CommencementDate, CompletedDate, AgreementPeriod) " +
				"VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
		jdbcTemplate = new JdbcTemplate(dataSource);	
		jdbcTemplate.update(sql, new Object[] {subProjectDetail.getAliasProjectName(), subProjectDetail.getSubProjectName(),
				subProjectDetail.getSubAliasName(),subProjectDetail.getSubAgreementNo(), subProjectDetail.getSubCerNo(), subProjectDetail.getSubAmount(),
				subProjectDetail.getSubContractorName(), subProjectDetail.getSubContractorAddress(),
				subProjectDetail.getSubContractValue(), subProjectDetail.getSubAgreementValue(), subProjectDetail.getSubTenderValue(), subProjectDetail.getSubExAmount(),
				subProjectDetail.getSubExPercentage(), subProjectDetail.getSubTenderSqlDate(), subProjectDetail.getSubAgreementSqlDate(),
				subProjectDetail.getSubCommencementSqlDate(), subProjectDetail.getSubCompletionSqlDate(), subProjectDetail.getSubAgreementPeriod()
		});
		return true;
	}

	public boolean saveProjDesc(final ProjDescDetail projDescDetail){
		String sql = "INSERT INTO projectDesc" +
				"(ProjId, SubProjId, WorkType, QuantityInFig, QuantityInWords, Description, AliasDescription, RateInFig, RateInWords, Amount) " +
				"VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
		jdbcTemplate = new JdbcTemplate(dataSource);	
		jdbcTemplate.update(sql, new Object[] {projDescDetail.getAliasProjectName(), projDescDetail.getAliasSubProjectName(),
				projDescDetail.getWorkType(),projDescDetail.getQuantityInFig(), projDescDetail.getQuantityInWords(), 
				projDescDetail.getDescription(),projDescDetail.getAliasDescription(), 
				projDescDetail.getRateInFig(),projDescDetail.getRateInWords(), 
				projDescDetail.getProjDescAmount()
		});
		return true;
	}

	public boolean editProjDesc(final ProjDescDetail projDescDetail){
		return true;
	}

	public boolean editSubProject(final SubProjectDetail subProjectDetail){
		return true;
	}

	public boolean editProject(final ProjectDetail projectDetail){
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
	
	public ProjectDetail getProjectDocument(String projectId) {
		String sql = projQuery + "where ProjId ="+projectId;	
		jdbcTemplate = new JdbcTemplate(dataSource);             
		ProjectDetail projDoc = null;
		List<Map<String, Object>> rows = jdbcTemplate.queryForList(sql);

		for (Map<String, Object> row : rows) {
			projDoc = buildProjectDetail(row);
		} 
		return projDoc;
	}
	
	private ProjectDetail buildProjectDetail(Map<String, Object> row){
		ProjectDetail projDoc = new ProjectDetail();
		projDoc.setProjId((Integer) row.get("ProjId"));
		projDoc.setProjectName((String) row.get("ProjName"));
		projDoc.setAliasName((String) row.get("AliasProjName"));
		projDoc.setAgreementNo((String) row.get("AgreementNum"));
		projDoc.setCerNo((String) row.get("CERNum"));
		projDoc.setAmount((String) row.get("Amount"));
		projDoc.setContractorName((String) row.get("ContractorName"));
		projDoc.setContractorAddress((String) row.get("ContractorAdd"));
		projDoc.setAgreementValue((String) row.get("AgreementValue"));
		projDoc.setTenderValue((String) row.get("TenderValue"));
		projDoc.setContractValue((String) row.get("ContractorValue"));
		projDoc.setExAmount((String) row.get("ExcessInAmount"));
		projDoc.setExPercentage((String) row.get("ExcessInPercentage"));
		projDoc.setTenderDate(row.get("TenderDate").toString());
		if(row.get("AgreementDate") != null){
			projDoc.setAgreementDate(row.get("AgreementDate").toString());
		}
		if(row.get("CommencementDate") != null){
			projDoc.setCommencementDate(row.get("CommencementDate").toString());
		}
		if(row.get("CompletedDate") != null){
			projDoc.setCompletionDate(row.get("CompletedDate").toString());
		}
		if(row.get("AgreementPeriod") != null){
			projDoc.setAgreementPeriod((Integer) row.get("AgreementPeriod"));
		}	
		return projDoc;
	}
	private String projQuery = "SELECT  ProjId, ProjName, AliasProjName, AgreementNum, "
			+ "CERNum, Amount, ContractorName, ContractorAdd, AgreementValue, "
			+ "TenderValue, ContractorValue, ExcessInAmount, ExcessInPercentage, "
			+ "TenderDate, AgreementDate, CommencementDate, CompletedDate, "
			+ "AgreementPeriod FROM `pms`.`project`";


}
