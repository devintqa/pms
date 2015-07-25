package com.psk.pms.dao;

import static com.psk.pms.dao.PmsMasterQuery.DELETESUBPROJECTBYPROJECTID;
import static com.psk.pms.dao.PmsMasterQuery.DELETESUBPROJECTBYSUBPROJECTID;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.datasource.DriverManagerDataSource;

import com.psk.pms.model.SubProjectDetail;

/**
 * Created by prakashbhanu57 on 7/6/2015.
 */
public class SubProjectDAOImpl implements SubProjectDAO {

    private static final Logger LOGGER = Logger.getLogger(SubProjectDAOImpl.class);

    private DriverManagerDataSource dataSource;

    private JdbcTemplate jdbcTemplate;

    @Autowired
    private EmdDAO emdDAO;

    @Autowired
    private ItemDAO itemDAO;

    @Autowired
    private ProjectDescriptionDAO projectDescriptionDAO;

    @Override
    public void deleteSubProjectByProjectId(Integer projectId) {
        jdbcTemplate = new JdbcTemplate(dataSource);
        int noOfRows = jdbcTemplate.update(DELETESUBPROJECTBYPROJECTID, new Object []{projectId});
        LOGGER.info("method = deleteSubProjectByProjectId , Number of rows deleted : "+ noOfRows +" projectId :" + projectId );
    }

    public boolean isAliasSubProjectAlreadyExisting(String subAliasName, Integer projectId) {
        String sql = "SELECT COUNT(*) FROM subproject where AliasSubProjName = ? and ProjId = ?";
        jdbcTemplate = new JdbcTemplate(dataSource);
        int total = jdbcTemplate.queryForObject(sql, Integer.class,
                new Object[]{subAliasName, projectId});
        if (total == 0) {
            return false;
        }
        return true;
    }

    private static String subProjQuery = "SELECT SubProjId, SubProjName, AliasSubProjName, AgreementNum, "
            + "CERNum, Amount, ContractorName,ContractorAliasName, ContractorAdd, AgreementValue, "
            + "TenderValue, ContractorValue, ExcessInAmount, ExcessInPercentage,LessInPercentage ,"
            + "TenderDate, AgreementDate, CommencementDate, CompletedDate, "
            + "AgreementPeriod, ProjId, SubAddSecurityDeposit,SubPerformanceGuarantee FROM subproject";

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

    private SubProjectDetail buildSubProjectDetail(Map<String, Object> row) {
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

        BigDecimal amount = (BigDecimal) row.get("Amount");
        subProjDoc.setSubAmount(amount.toString());

        BigDecimal aggValue = (BigDecimal) row.get("AgreementValue");
        subProjDoc.setSubAgreementValue(aggValue.toString());

        BigDecimal tenderValue = (BigDecimal) row.get("TenderValue");
        subProjDoc.setSubTenderValue(tenderValue.toString());

        BigDecimal contValue = (BigDecimal) row.get("ContractorValue");
        subProjDoc.setSubContractValue(contValue.toString());

        BigDecimal exAmount = (BigDecimal) row.get("ExcessInAmount");
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

        subProjDoc.setSubTenderSqlDate((Date) row.get("TenderDate"));
        subProjDoc.setSubAgreementSqlDate((Date) row.get("AgreementDate"));
        subProjDoc.setSubCommencementSqlDate((Date) row.get("CommencementDate"));
        subProjDoc.setSubCompletionSqlDate((Date) row.get("CompletedDate"));
        subProjDoc.setSubAgreementPeriod((Integer) row.get("AgreementPeriod"));

        BigDecimal addSecurityDeposit = (BigDecimal) row.get("SubAddSecurityDeposit");
        subProjDoc.setSubAddSecurityDeposit(addSecurityDeposit.toString());
        subProjDoc.setSubAddSecurityDeposit(addSecurityDeposit.toString());

        BigDecimal subPerformanceGuarantee = (BigDecimal) row.get("SubPerformanceGuarantee");
        if (null == subPerformanceGuarantee) {
            subProjDoc.setSubPerformanceGuarantee("");
        } else {
            subProjDoc.setSubPerformanceGuarantee(subPerformanceGuarantee.toString());
        }
        return subProjDoc;
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

    private static String subProj = "SELECT s.SubProjId, s.SubProjName, s.AliasSubProjName, s.AgreementNum, "
            + "s.CERNum, s.Amount, s.ContractorName,s.ContractorAliasName, s.ContractorAdd, s.AgreementValue, "
            + "s.TenderValue, s.ContractorValue, s.ExcessInAmount, s.ExcessInPercentage,s.LessInPercentage, "
            + "s.TenderDate, s.AgreementDate, s.CommencementDate, s.CompletedDate, "
            + "s.AgreementPeriod, s.ProjId ,s.SubAddSecurityDeposit , s.SubPerformanceGuarantee";

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
            aliasSubProjects.put(String.valueOf(row.get("SubProjId")), (String) row.get("AliasSubProjName"));
        }
        return aliasSubProjects;
    }

    @Override
    public void deleteSubProjectBySubProjectId(Integer subProjectId) {
        itemDAO.deleteItemBySubProjectId(subProjectId);
        emdDAO.deleteEmddetailBySubProjectId(subProjectId);
        projectDescriptionDAO.deleteProjectDescriptionBySubProjectId(subProjectId);
        jdbcTemplate = new JdbcTemplate(dataSource);
        int noOfRows = jdbcTemplate.update(DELETESUBPROJECTBYSUBPROJECTID, new Object[]{subProjectId});
        LOGGER.info("method = deleteSubProjectBySubProjectId , Number of rows deleted : " + noOfRows + " subProjectId :" + subProjectId);
    }

    public DriverManagerDataSource getDataSource() {
        return dataSource;
    }

    public void setDataSource(DriverManagerDataSource dataSource) {
        this.dataSource = dataSource;
    }
}
