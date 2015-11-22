package com.psk.pms.dao;

import static com.psk.pms.dao.PmsMasterQuery.DELETEPROJECTBYPROJECTID;
import static com.psk.pms.dao.PmsMasterQuery.GET_DROP_DOWN_VALUES;
import static org.springframework.util.StringUtils.isEmpty;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.jdbc.core.JdbcTemplate;

import com.psk.pms.model.ProjectDetail;

public class ProjectDAOImpl implements ProjectDAO {


    @Qualifier("jdbcTemplate")
    @Autowired
    private JdbcTemplate jdbcTemplate;
    
    ResultTransformer transformer = new ResultTransformer();

    private static final Logger LOGGER = Logger.getLogger(ProjectDAOImpl.class);

    public boolean saveProject(final ProjectDetail projectDetail) {
        String createSql = "INSERT INTO project (ProjName, AliasProjName, MainProjId, ProjectType,AgreementNum, CERNum, Amount, "
                + "ContractorName, ContractorAliasName , ContractorAdd, AgreementValue, TenderValue, " +
                "ExcessInAmount, ExcessInPercentage, LessInPercentage ,CompletionDateForBonus , TenderDate, "
                + "AgreementDate, CommencementDate, CompletedDate, AgreementPeriod , LastUpdatedBy ,LastUpdatedAt,workoutPercentage,workLocation ) " +
                "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?,?,?)";

        String updateSql = "UPDATE project set ProjectType = ? ,AgreementNum  = ?, CERNum = ?, Amount = ?, ContractorName = ?,ContractorAliasName = ?," +
                "ContractorAdd = ?, AgreementValue = ?, TenderValue=?, ExcessInAmount = ?," +
                "ExcessInPercentage = ?,LessInPercentage = ?,CompletionDateForBonus =?, TenderDate = ?, AgreementDate = ?," +
                "CommencementDate = ?, CompletedDate = ?, AgreementPeriod = ? ,LastUpdatedBy = ?,LastUpdatedAt = ? ,workoutPercentage=?, workLocation = ? WHERE ProjId = ?";

        if (!"Y".equalsIgnoreCase(projectDetail.getIsUpdate())) {
            jdbcTemplate.update(createSql, projectDetail.getProjectName(),
                    projectDetail.getAliasName(),
                    projectDetail.getAliasProjectNameForSubProj(),
                    projectDetail.getProjectType(),
                    projectDetail.getAgreementNo(),
                    projectDetail.getCerNo(),
                    projectDetail.getAmount(),
                    projectDetail.getContractorName(),
                    projectDetail.getAliasContractorName(),
                    projectDetail.getContractorAddress(),
                    projectDetail.getAgreementValue(),
                    projectDetail.getTenderValue(),
                    projectDetail.getExAmount(),
                    projectDetail.getExPercentage(),
                    projectDetail.getLessPercentage(),
                    projectDetail.getCompletionDateSqlForBonus(),
                    projectDetail.getTenderSqlDate(),
                    projectDetail.getAgreementSqlDate(),
                    projectDetail.getCommencementSqlDate(),
                    projectDetail.getCompletionSqlDate(),
                    projectDetail.getAgreementPeriod(),
                    projectDetail.getLastUpdatedBy(),
                    projectDetail.getLastUpdatedAt(),
                    projectDetail.getWorkoutPercentage(),
                    projectDetail.getWorkLocation());
        } else {
            jdbcTemplate.update(updateSql, projectDetail.getProjectType(),
                    projectDetail.getAgreementNo(),
                    projectDetail.getCerNo(),
                    projectDetail.getAmount(),
                    projectDetail.getContractorName(),
                    projectDetail.getAliasContractorName(),
                    projectDetail.getContractorAddress(),
                    projectDetail.getAgreementValue(),
                    projectDetail.getTenderValue(),
                    projectDetail.getExAmount(),
                    projectDetail.getExPercentage(),
                    projectDetail.getLessPercentage(),
                    projectDetail.getCompletionDateSqlForBonus(),
                    projectDetail.getTenderSqlDate(),
                    projectDetail.getAgreementSqlDate(),
                    projectDetail.getCommencementSqlDate(),
                    projectDetail.getCompletionSqlDate(),
                    projectDetail.getAgreementPeriod(),
                    projectDetail.getLastUpdatedBy(),
                    projectDetail.getLastUpdatedAt(),
                    projectDetail.getWorkoutPercentage(),
                    projectDetail.getWorkLocation(),
                    projectDetail.getProjId());
        }
        return true;
    }

    public Map<String, String> getAliasProjectNames() {
        String sql = "select ProjId, AliasProjName from project";
        List<Map<String, Object>> rows = jdbcTemplate.queryForList(sql);
        Map<String, String> aliasProjects = new LinkedHashMap<String, String>();
        aliasProjects.put("0", "--Please Select--");
        for (Map<String, Object> row : rows) {
            aliasProjects.put(String.valueOf(row.get("ProjId")), (String) row.get("AliasProjName"));
        }
        return aliasProjects;
    }

    public Map<String, String> getAliasProjectNames(String empId) {
        Map<String, String> aliasProjects = new LinkedHashMap<String, String>();
        String sql;
        List<Map<String, Object>> rows;
        if (!isEmpty(empId)) {
            sql = "select ProjId, AliasProjName from project where ProjId in (select projectId from authoriseproject where empId = ?)";
            rows = jdbcTemplate.queryForList(sql, empId);
        } else {
            sql = "select ProjId, AliasProjName from project";
            rows = jdbcTemplate.queryForList(sql);
        }
        aliasProjects.put("0", "--Please Select--");
        for (Map<String, Object> row : rows) {
            aliasProjects.put(String.valueOf(row.get("ProjId")), (String) row.get("AliasProjName"));
        }
        return aliasProjects;
    }
    
    public String fetchMainProjectType(Integer mainProjectId){
    	
    	String sql = "select ProjectType from project where ProjId = ?";
		 
    	String mainProjectType = (String)jdbcTemplate.queryForObject(
    			sql, new Object[] { mainProjectId }, String.class);
    	
    	return mainProjectType;
    }
    
    public String getMainProjAliasName(Integer mainProjectId){
    	String sql = "select AliasProjName from project where ProjId = ?";
		 
    	String mainAliasProjName = (String)jdbcTemplate.queryForObject(
    			sql, new Object[] { mainProjectId }, String.class);
    	
    	return mainAliasProjName;
    }


    public List<ProjectDetail> getProjectDocumentList(String employeeId) {
        List<ProjectDetail> projDocList = new ArrayList<ProjectDetail>();
        List<Map<String, Object>> rows = jdbcTemplate.queryForList((projQuery + " where ProjId in(select projectId from authoriseproject where empId = ?)"), employeeId);
        for (Map<String, Object> row : rows) {
            projDocList.add(transformer.buildProjectDetail(row));
        }
        return projDocList;
    }

    @Override
    public List<ProjectDetail> getProjectDocumentList() {
        List<ProjectDetail> projDocList = new ArrayList<ProjectDetail>();
        List<Map<String, Object>> rows = jdbcTemplate.queryForList(projQuery);
        for (Map<String, Object> row : rows) {
            projDocList.add(transformer.buildProjectDetail(row));
        }
        return projDocList;
    }

    public ProjectDetail getProjectDocument(String projectId, String employeeId) {
        String sql;
        List<Map<String, Object>> rows;
        if (!isEmpty(employeeId)) {
            sql = projQuery + " where ProjId in(select projectId from authoriseproject where empId = ?) and ProjId =" + projectId;
            rows = jdbcTemplate.queryForList(sql, employeeId);
        } else {
            sql = projQuery + " where ProjId =" + projectId;
            rows = jdbcTemplate.queryForList(sql);
        }
        ProjectDetail projDoc = null;
        for (Map<String, Object> row : rows) {
            projDoc = transformer.buildProjectDetail(row);
        }
        return projDoc;
    }

    public boolean isAliasProjectAlreadyExisting(String aliasName) {
        String sql = "SELECT COUNT(*) FROM project where AliasProjName = ?";
        int total = jdbcTemplate.queryForObject(sql, Integer.class,
                new Object[]{aliasName});
        if (total == 0) {
            return false;
        }
        return true;
    }

    private String projQuery = "SELECT  ProjId, MainProjId, ProjName, ProjectType ,AliasProjName, AgreementNum, "
            + "CERNum, Amount, ContractorName,ContractorAliasName, ContractorAdd, AgreementValue, "
            + "TenderValue , ExcessInAmount, ExcessInPercentage,LessInPercentage, "
            + "CompletionDateForBonus ,TenderDate, AgreementDate, CommencementDate, CompletedDate,workoutPercentage,workLocation, "
            + "AgreementPeriod FROM project";


    @Override
    public void deleteProject(Integer projectId) {
        int noOfRows = jdbcTemplate.update(DELETEPROJECTBYPROJECTID, new Object[]{projectId});
        LOGGER.info("method = deleteProject , Number of rows deleted : " + noOfRows + " projectId :" + projectId);
    }

    @Override
    @Cacheable(value="masterTableCache")
    public List<String> getDropDownValues(String type) {
        LOGGER.info("method = getDropDownValues for type " + type);
        List<String> values = new ArrayList<String>();
        List<Map<String, Object>> rows = jdbcTemplate.queryForList(GET_DROP_DOWN_VALUES, new Object[]{type});
        for (Map<String, Object> row : rows) {
            values.add(String.valueOf(row.get("Value")));
        }
        return values;
    }
}
