package com.psk.pms.dao;
import java.util.ArrayList;
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
	
	public List<String> getAliasProjectNames(){
		String sql = "select AliasProjName from project";	 
		List<String> projects = new ArrayList<String>();
		jdbcTemplate = new JdbcTemplate(dataSource);
		List<Map<String, Object>> rows = jdbcTemplate.queryForList(sql);
		for (Map<String, Object> row : rows) {
			projects.add((String)row.get("AliasProjName"));
		}	 
		return projects;
	}
	
	public List<String> getSubAliasProjectNames(String aliasProjectName) {
		String sql = "select AliasSubProjName from subproject where AliasProjName = ?";	 
		List<String> projects = new ArrayList<String>();
		jdbcTemplate = new JdbcTemplate(dataSource);
		List<Map<String, Object>> rows = jdbcTemplate.queryForList(sql, new Object[] {aliasProjectName});
		for (Map<String, Object> row : rows) {
			projects.add((String)row.get("AliasSubProjName"));
		}	 
		return projects;
	}
	
	public boolean saveSubProject(final SubProjectDetail subProjectDetail){
		String sql = "INSERT INTO subproject" +
		"(AliasProjName, SubProjName, AliasSubProjName, AgreementNum, CERNum, Amount, ContractorName, ContractorAdd, ContractorValue, AgreementValue, TenderValue, " +
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
		"(AliasProjName, AliasSubProjName, WorkType, QuantityInFig, QuantityInWords, Description, AliasDescription, RateInFig, RateInWords, Amount) " +
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
}
