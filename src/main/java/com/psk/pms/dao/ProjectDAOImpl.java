package com.psk.pms.dao;

import com.psk.pms.model.ProjectDetail;
import org.apache.log4j.Logger;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.datasource.DriverManagerDataSource;

import java.math.BigDecimal;
import java.util.*;

import static com.psk.pms.dao.PmsMasterQuery.*;

public class ProjectDAOImpl implements ProjectDAO {

    private SubProjectDAO subProjectDAO;

    private ProjectDescriptionDAO projectDescriptionDAO;

    private EmdDAO emdDAO;

    private DriverManagerDataSource dataSource;
    private JdbcTemplate jdbcTemplate;

    private static final Logger LOGGER = Logger.getLogger(ProjectDAOImpl.class);

    public void setDataSource(DriverManagerDataSource dataSource) {
        this.dataSource = dataSource;
    }

    public boolean saveProject(final ProjectDetail projectDetail) {
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
        if (!"Y".equalsIgnoreCase(projectDetail.getIsUpdate())) {
            jdbcTemplate.update(createSql, new Object[]{projectDetail.getProjectName(),
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
            jdbcTemplate.update(updateSql, new Object[]{
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

    public Map<String, String> getAliasProjectNames() {
        String sql = "select ProjId, AliasProjName from project";
        jdbcTemplate = new JdbcTemplate(dataSource);
        List<Map<String, Object>> rows = jdbcTemplate.queryForList(sql);
        Map<String, String> aliasProjects = new LinkedHashMap<String, String>();
        aliasProjects.put("0", "--Please Select--");
        for (Map<String, Object> row : rows) {
            aliasProjects.put(String.valueOf(row.get("ProjId")), (String) row.get("AliasProjName"));
        }
        return aliasProjects;
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
        String sql = projQuery + " where ProjId =" + projectId;
        jdbcTemplate = new JdbcTemplate(dataSource);
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

        BigDecimal contValue = (BigDecimal) row.get("ContractorValue");
        projDoc.setContractValue(contValue.toString());

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

        projDoc.setAgreementSqlDate((Date) row.get("AgreementDate"));
        projDoc.setCommencementSqlDate((Date) row.get("CommencementDate"));
        projDoc.setCompletionSqlDate((Date) row.get("CompletedDate"));
        projDoc.setAgreementPeriod((Integer) row.get("AgreementPeriod"));

        BigDecimal addSecurityDeposit = (BigDecimal) row.get("AddSecurityDeposit");
        projDoc.setAddSecurityDeposit(addSecurityDeposit.toString());

        BigDecimal performanceGuarantee = (BigDecimal) row.get("PerformanceGuarantee");
        if (null == performanceGuarantee) {
            projDoc.setPerformanceGuarantee("");
        } else {
            projDoc.setPerformanceGuarantee(performanceGuarantee.toString());
        }
        projDoc.setAddSecurityDeposit(addSecurityDeposit.toString());

        return projDoc;
    }

    public boolean isAliasProjectAlreadyExisting(String aliasName) {
        String sql = "SELECT COUNT(*) FROM project where AliasProjName = ?";
        jdbcTemplate = new JdbcTemplate(dataSource);
        int total = jdbcTemplate.queryForObject(sql, Integer.class,
                new Object[]{aliasName});
        if (total == 0) {
            return false;
        }
        return true;
    }

    private String projQuery = "SELECT  ProjId, ProjName, AliasProjName, AgreementNum, "
            + "CERNum, Amount, ContractorName,ContractorAliasName, ContractorAdd, AgreementValue, "
            + "TenderValue, ContractorValue, ExcessInAmount, ExcessInPercentage,LessInPercentage, "
            + "TenderDate, AgreementDate, CommencementDate, CompletedDate, "
            + "AgreementPeriod,AddSecurityDeposit,PerformanceGuarantee FROM project";





    @Override
    public void deleteProject(Integer projectId) {
        emdDAO.deleteEmddetailByProjectId(projectId);
        projectDescriptionDAO.deleteProjectDescriptionByProjectId(projectId);
        subProjectDAO.deleteSubProjectByProjectId(projectId);
        jdbcTemplate = new JdbcTemplate(dataSource);
        int noOfRows = jdbcTemplate.update(DELETEPROJECTBYPROJECTID, new Object[]{projectId});
        LOGGER.info("method = deleteProject , Number of rows deleted : " + noOfRows + " projectId :" + projectId);
    }

}
