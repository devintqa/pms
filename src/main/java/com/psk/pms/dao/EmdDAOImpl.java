package com.psk.pms.dao;

import com.psk.pms.model.EmdDetail;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.datasource.DriverManagerDataSource;

import java.math.BigDecimal;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

import static com.psk.pms.dao.PmsMasterQuery.*;

public class EmdDAOImpl implements EmdDAO {


    @Qualifier("jdbcTemplate")
    @Autowired
    private JdbcTemplate jdbcTemplate;

    private static final Logger LOGGER = Logger.getLogger(EmdDAOImpl.class);

    @Override
    public boolean saveEmd(final EmdDetail emdDetail) {
        LOGGER.info("Save EMD DAO Start");
        if (!"Y".equalsIgnoreCase(emdDetail.getIsUpdate())) {
            jdbcTemplate.update(CREATEEMDDETAIL, new Object[]{
                    emdDetail.getAliasProjectName(), emdDetail.getAliasSubProjectName(), emdDetail.getEmdAmount(), emdDetail.getSqlEmdStartDate(),
                    emdDetail.getSqlEmdEndDate(), emdDetail.getEmdType(), emdDetail.getBgNumber(), emdDetail.getEmdPeriod(),
                    emdDetail.getEmdExtensionSqlDate(), emdDetail.getEmdLedgerNumber(),
                    emdDetail.getLastUpdatedBy(), emdDetail.getLastUpdatedAt(), emdDetail.getEmdSubmitter()
            });
        } else {
            jdbcTemplate.update(UPDATEEMDDETAIL, new Object[]
                    {emdDetail.getProjId(), emdDetail.getSubProjId(), emdDetail.getEmdAmount(), emdDetail.getSqlEmdStartDate(),
                            emdDetail.getSqlEmdEndDate(), emdDetail.getEmdType(), emdDetail.getBgNumber(), emdDetail.getEmdPeriod(),
                            emdDetail.getEmdExtensionSqlDate(), emdDetail.getEmdLedgerNumber(),
                            emdDetail.getLastUpdatedBy(), emdDetail.getLastUpdatedAt(), emdDetail.getEmdSubmitter(), emdDetail.getEmdId()
                    });
        }
        LOGGER.info("Save EMD DAO End");
        return true;
    }

    @Override
    public List<EmdDetail> getEmdDetails() {
        LOGGER.info("Fetch EMD Details Start");
        List<EmdDetail> emdDetails = new ArrayList<EmdDetail>();
        List<Map<String, Object>> rows = jdbcTemplate.queryForList(FETCHEMDDETAILS);
        for (Map<String, Object> row : rows) {
            EmdDetail emdDetail = new EmdDetail();
            emdDetail.setEmdId((Integer) row.get("EmdId"));
            emdDetail.setAliasProjectName((String) row.get("AliasProjName"));
            emdDetail.setAliasSubProjectName((String) row.get("AliasSubProjName"));
            BigDecimal emdAmount = (BigDecimal) row.get("EmdAmount");
            emdDetail.setEmdAmount(emdAmount.toString());
            emdDetail.setSqlEmdStartDate((Date) row.get("EmdStartDate"));
            emdDetail.setSqlEmdEndDate((Date) row.get("EmdEndDate"));
            emdDetail.setEmdType((String) row.get("EmdType"));
            emdDetails.add(emdDetail);
        }
        LOGGER.info("Fetch EMD Details End");
        return emdDetails;
    }

    @Override
    public EmdDetail getEmdDetailsByEmdId(String emdId) {
        EmdDetail emdDetail = new EmdDetail();
        emdDetail = (EmdDetail) jdbcTemplate.queryForObject(FETCHEMDDETAILBYEMDID, new Object[]{emdId}, new EmdDetailRowMapper());
        return emdDetail;
    }

    private class EmdDetailRowMapper implements org.springframework.jdbc.core.RowMapper<Object> {
        public Object mapRow(ResultSet rs, int rowNum) throws SQLException {
            EmdDetail emdDetail = new EmdDetail();
            emdDetail.setProjId(rs.getInt("ProjId"));
            emdDetail.setSubProjId(rs.getInt("SubProjId"));
            emdDetail.setEmdAmount(rs.getString("EmdAmount"));
            emdDetail.setEmdType(rs.getString("EmdType"));
            emdDetail.setBgNumber(rs.getString("BGNumber"));
            emdDetail.setEmdPeriod(rs.getInt("EmdPeriod"));
            emdDetail.setEmdLedgerNumber(rs.getString("EmdLedgerNumber"));
            emdDetail.setEmdSubmitter(rs.getString("EmdSubmitter"));
            emdDetail.setSqlEmdStartDate(rs.getDate("EmdStartDate"));
            emdDetail.setSqlEmdEndDate(rs.getDate("EmdEndDate"));
            emdDetail.setEmdExtensionSqlDate(rs.getDate("EmdExtensionDate"));
            return emdDetail;
        }
    }


    public void deleteEmdDetailByEmdId(Integer emdId) {
        int noOfRows = jdbcTemplate.update(DELTEPEMDDETAILBYEMDID, new Object[]{emdId});
        LOGGER.info("method = deleteEmdDetailByEmdId , Number of rows deleted : " + noOfRows + " subProjectId :" + emdId);
    }

    @Override
    public List<EmdDetail> getEmdDetailsByProjectId(Integer projectId) {
        LOGGER.info("Fetch EMD Details By ProjectId :" + projectId);
        List<EmdDetail> emdDetails = new ArrayList<EmdDetail>();
        List<Map<String, Object>> rows = jdbcTemplate.queryForList(GETEMDDETAILSBYPROJECTID, new Object[]{projectId});
        for (Map<String, Object> row : rows) {
            EmdDetail emdDetail = new EmdDetail();
            emdDetail.setEmdId((Integer) row.get("EmdId"));
            emdDetail.setAliasProjectName((String) row.get("AliasProjName"));
            emdDetail.setAliasSubProjectName("");
            BigDecimal emdAmount = (BigDecimal) row.get("EmdAmount");
            emdDetail.setEmdAmount(emdAmount.toString());
            emdDetail.setSqlEmdStartDate((Date) row.get("EmdStartDate"));
            emdDetail.setSqlEmdEndDate((Date) row.get("EmdEndDate"));
            emdDetail.setEmdType((String) row.get("EmdType"));
            emdDetails.add(emdDetail);
        }
        return emdDetails;
    }

    @Override
    public List<EmdDetail> getEmdDetailsBySubProjectId(Integer subProjectId) {
        LOGGER.info("Fetch EMD Details By  subProjectId :" + subProjectId);
        List<EmdDetail> emdDetails = new ArrayList<EmdDetail>();
        List<Map<String, Object>> rows = jdbcTemplate.queryForList(GETEMDDETAILSBYSUBPROJECTID, new Object[]{subProjectId});
        for (Map<String, Object> row : rows) {
            EmdDetail emdDetail = new EmdDetail();
            emdDetail.setEmdId((Integer) row.get("EmdId"));
            emdDetail.setAliasProjectName((String) row.get("AliasProjName"));
            emdDetail.setAliasSubProjectName((String) row.get("AliasSubProjName"));
            BigDecimal emdAmount = (BigDecimal) row.get("EmdAmount");
            emdDetail.setEmdAmount(emdAmount.toString());
            emdDetail.setSqlEmdStartDate((Date) row.get("EmdStartDate"));
            emdDetail.setSqlEmdEndDate((Date) row.get("EmdEndDate"));
            emdDetail.setEmdType((String) row.get("EmdType"));
            emdDetails.add(emdDetail);
        }
        return emdDetails;
    }

    @Override
    public void deleteEmddetailBySubProjectId(Integer subProjectId) {
        int noOfRows = jdbcTemplate.update(DELETEEMDDETAILBYSUBPROJECTID, new Object[]{subProjectId});
        LOGGER.info("method = deleteEmddetailBySubProjectId , Number of rows deleted : " + noOfRows + " subProjectId :" + subProjectId);
    }

    @Override
    public void deleteEmddetailByProjectId(Integer projectId) {
        int noOfRows = jdbcTemplate.update(DELETEEMDDETAILBYPROJECTID, new Object[]{projectId});
        LOGGER.info("method = deleteEmddetailByProjectId , Number of rows deleted : " + noOfRows + " projectId :" + projectId);
    }

    public List<EmdDetail> getEMDDatesList() {
        List<EmdDetail> emdList = new ArrayList<EmdDetail>();
        List<Map<String, Object>> rows = jdbcTemplate.queryForList(emdDatesQuery);
        for (Map<String, Object> row : rows) {
            emdList.add(buildEMDDetail(row));
        }
        return emdList;
    }

    private EmdDetail buildEMDDetail(Map<String, Object> row) {
        EmdDetail emdDetail = new EmdDetail();
        BigDecimal emdAmount = (BigDecimal) row.get("EmdAmount");
        emdDetail.setEmdAmount(emdAmount.toString());
        emdDetail.setSqlEmdStartDate((Date) row.get("EmdStartDate"));
        emdDetail.setSqlEmdEndDate((Date) row.get("EmdEndDate"));
        emdDetail.setEmdType((String) row.get("EmdType"));
        emdDetail.setEmdExtensionSqlDate((Date) row.get("EmdExtensionDate"));
        return emdDetail;
    }

    @Override
    public List<String> fetchEmdTypes() {
        LOGGER.info("method = fetchEmdTypes");
        List<String> itemTypes = jdbcTemplate.queryForList(PmsMasterQuery.FETCHEMDTYPES, String.class);
        LOGGER.info("No of rows fetched :" + itemTypes.size());
        return itemTypes;
    }


}
