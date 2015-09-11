package com.psk.pms.dao;

import com.psk.pms.model.ProjectDetail;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.jdbc.core.JdbcTemplate;

import java.math.BigDecimal;
import java.util.*;

import static com.psk.pms.dao.PmsMasterQuery.DELETEPROJECTBYPROJECTID;
import static com.psk.pms.dao.PmsMasterQuery.GET_DROP_DOWN_VALUES;

public class ProjectDAOImpl implements ProjectDAO {


    @Qualifier("jdbcTemplate")
    @Autowired
    private JdbcTemplate jdbcTemplate;

    private static final Logger LOGGER = Logger.getLogger(ProjectDAOImpl.class);

    public boolean saveProject(final ProjectDetail projectDetail) {
        String createSql = "INSERT INTO project (ProjName, AliasProjName,ProjectType,AgreementNum, CERNum, Amount, "
                + "ContractorName, ContractorAliasName , ContractorAdd, AgreementValue, TenderValue, " +
                "ExcessInAmount, ExcessInPercentage, LessInPercentage ,CompletionDateForBonus , TenderDate, "
                + "AgreementDate, CommencementDate, CompletedDate, AgreementPeriod , LastUpdatedBy ,LastUpdatedAt ) " +
                "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?,?,?)";

        String updateSql = "UPDATE project set ProjectType = ? ,AgreementNum  = ?, CERNum = ?, Amount = ?, ContractorName = ?,ContractorAliasName = ?," +
                "ContractorAdd = ?, AgreementValue = ?, TenderValue=?, ExcessInAmount = ?," +
                "ExcessInPercentage = ?,LessInPercentage = ?,CompletionDateForBonus =?, TenderDate = ?, AgreementDate = ?," +
                "CommencementDate = ?, CompletedDate = ?, AgreementPeriod = ? ,LastUpdatedBy = ?,LastUpdatedAt = ? WHERE ProjId = ?";

        if (!"Y".equalsIgnoreCase(projectDetail.getIsUpdate())) {
            jdbcTemplate.update(createSql, new Object[]{projectDetail.getProjectName(),
                    projectDetail.getAliasName(),
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
                    projectDetail.getLastUpdatedAt()

            });
        } else {
            jdbcTemplate.update(updateSql, new Object[]{
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
                    projectDetail.getProjId()
            });
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


    public List<ProjectDetail> getProjectDocumentList() {
        List<ProjectDetail> projDocList = new ArrayList<ProjectDetail>();
        List<Map<String, Object>> rows = jdbcTemplate.queryForList(projQuery);

        for (Map<String, Object> row : rows) {
            projDocList.add(buildProjectDetail(row));
        }
        return projDocList;
    }

    public ProjectDetail getProjectDocument(String projectId) {
        String sql = projQuery + " where ProjId =" + projectId;
        ProjectDetail projDoc = null;
        List<Map<String, Object>> rows = jdbcTemplate.queryForList(sql);

        for (Map<String, Object> row : rows) {
            projDoc = buildProjectDetail(row);
        }
        return projDoc;
    }

    private ProjectDetail buildProjectDetail(Map<String, Object> row) {
        ProjectDetail projDoc = new ProjectDetail();
        projDoc.setProjId((Integer) row.get("ProjId"));
        projDoc.setProjectName((String) row.get("ProjName"));
        projDoc.setAliasName((String) row.get("AliasProjName"));
        projDoc.setProjectType((String) row.get("ProjectType"));
        projDoc.setAgreementNo((String) row.get("AgreementNum"));
        projDoc.setCerNo((String) row.get("CERNum"));
        projDoc.setContractorName((String) row.get("ContractorName"));
        projDoc.setAliasContractorName((String) row.get("ContractorAliasName"));
        projDoc.setContractorAddress((String) row.get("ContractorAdd"));
        projDoc.setTenderSqlDate((Date) row.get("TenderDate"));

        BigDecimal amount = (BigDecimal) row.get("Amount");
        projDoc.setAmount(amount.toString());

        BigDecimal aggValue = (BigDecimal) row.get("AgreementValue");
        projDoc.setAgreementValue(aggValue.toString());

        BigDecimal tenderValue = (BigDecimal) row.get("TenderValue");
        projDoc.setTenderValue(tenderValue.toString());


        BigDecimal exAmount = (BigDecimal) row.get("ExcessInAmount");
        projDoc.setExAmount(exAmount.toString());

        BigDecimal exPercentage = (BigDecimal) row.get("ExcessInPercentage");
        if (null == exPercentage) {
            projDoc.setExPercentage("");
        } else {
            projDoc.setExPercentage(exPercentage.toString());
        }

        BigDecimal lessPercentage = (BigDecimal) row.get("LessInPercentage");
        if (null == lessPercentage) {
            projDoc.setLessPercentage("");
        } else {
            projDoc.setLessPercentage(lessPercentage.toString());
        }
        projDoc.setCompletionDateSqlForBonus((Date) row.get("CompletionDateForBonus"));
        projDoc.setAgreementSqlDate((Date) row.get("AgreementDate"));
        projDoc.setCommencementSqlDate((Date) row.get("CommencementDate"));
        projDoc.setCompletionSqlDate((Date) row.get("CompletedDate"));
        projDoc.setAgreementPeriod((Integer) row.get("AgreementPeriod"));

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

    private String projQuery = "SELECT  ProjId, ProjName, ProjectType ,AliasProjName, AgreementNum, "
            + "CERNum, Amount, ContractorName,ContractorAliasName, ContractorAdd, AgreementValue, "
            + "TenderValue , ExcessInAmount, ExcessInPercentage,LessInPercentage, "
            + "CompletionDateForBonus ,TenderDate, AgreementDate, CommencementDate, CompletedDate, "
            + "AgreementPeriod FROM project";


    @Override
    public void deleteProject(Integer projectId) {
        int noOfRows = jdbcTemplate.update(DELETEPROJECTBYPROJECTID, new Object[]{projectId});
        LOGGER.info("method = deleteProject , Number of rows deleted : " + noOfRows + " projectId :" + projectId);
    }

    @Override
    public List<String> getDropDownValues(String type) {
        LOGGER.info("method = getDropDownValues for type "+type);
        List<String> values = new ArrayList<String>();
        List<Map<String, Object>> rows = jdbcTemplate.queryForList(GET_DROP_DOWN_VALUES, new Object[]{type});
        for (Map<String, Object> row : rows) {
            values.add(String.valueOf(row.get("Value")));
        }
        return values;
    }
}
