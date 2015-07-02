package com.psk.pms.dao;

import com.psk.pms.model.EMDDetail;
import org.apache.log4j.Logger;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.datasource.DriverManagerDataSource;

import java.math.BigDecimal;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

public class EmdDaoImpl implements EmdDAO {

    private DriverManagerDataSource dataSource;
    private JdbcTemplate jdbcTemplate;

    private static final Logger LOGGER = Logger.getLogger(EmdDaoImpl.class);

    @Override
    public boolean saveEmd(final EMDDetail emdDetail) {
    	LOGGER.info("Save EMD DAO Start");
        jdbcTemplate = new JdbcTemplate(dataSource);
        if (!"Y".equalsIgnoreCase(emdDetail.getIsUpdate())) {
            jdbcTemplate.update(PMSMasterQuery.CREATEEMDDETAIL, new Object[]{
                    emdDetail.getAliasProjectName(), emdDetail.getAliasSubProjectName(), emdDetail.getEmdAmount(), emdDetail.getSqlEmdStartDate(),
                    emdDetail.getSqlEmdEndDate(), emdDetail.getEmdType(), emdDetail.getBgNumber(), emdDetail.getEmdPeriod(),
                    emdDetail.getEmdExtensionSqlDate(), emdDetail.getEmdLedgerNumber(),
                     emdDetail.getLastUpdatedBy(), emdDetail.getLastUpdatedAt(),emdDetail.getEmdSubmitter()
            });
        } else {
            jdbcTemplate.update(PMSMasterQuery.UPDATEEMDDETAIL, new Object[]
                    {emdDetail.getProjId(), emdDetail.getSubProjId(), emdDetail.getEmdAmount(), emdDetail.getSqlEmdStartDate(),
                            emdDetail.getSqlEmdEndDate(), emdDetail.getEmdType(), emdDetail.getBgNumber(), emdDetail.getEmdPeriod(),
                            emdDetail.getEmdExtensionSqlDate(), emdDetail.getEmdLedgerNumber(),
                            emdDetail.getLastUpdatedBy(), emdDetail.getLastUpdatedAt(),emdDetail.getEmdSubmitter(), emdDetail.getEmdId()
                    });
        }
        LOGGER.info("Save EMD DAO End");
        return true;
    }

    @Override
    public List<EMDDetail> getEmdDetails() {
    	LOGGER.info("Fetch EMD Details Start");
        List<EMDDetail> emdDetails = new ArrayList<EMDDetail>();
        jdbcTemplate = new JdbcTemplate(dataSource);
        List<Map<String, Object>> rows =  jdbcTemplate.queryForList(PMSMasterQuery.FETCHEMDDETAILS);
        for(Map<String,Object> row : rows)
        {
            EMDDetail emdDetail =new EMDDetail();
            emdDetail.setEmdId((Integer) row.get("EmdId"));
            emdDetail.setAliasProjectName((String) row.get("AliasProjName"));
            emdDetail.setAliasSubProjectName((String) row.get("AliasSubProjName"));
            BigDecimal emdAmount = (BigDecimal)row.get("EmdAmount");
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
    public EMDDetail getEmdDetailsByEmdId(String emdId) {
        EMDDetail emdDetail = new EMDDetail();
        jdbcTemplate = new JdbcTemplate(dataSource);
        emdDetail = (EMDDetail) jdbcTemplate.queryForObject(PMSMasterQuery.FETCHEMDDETAILBYEMDID,new Object[] {emdId},new EmdDetailRowMapper());
        return emdDetail;
    }

    private class EmdDetailRowMapper implements org.springframework.jdbc.core.RowMapper<Object> {
        public Object mapRow(ResultSet rs, int rowNum) throws SQLException {
            EMDDetail emdDetail = new EMDDetail();
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
    
    public void setDataSource(DriverManagerDataSource dataSource) {
        this.dataSource = dataSource;
    }

    public void setJdbcTemplate(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public void deleteEmdDetailByEmdId(Integer emdId)
    {
        JdbcTemplate jdbcTemplate = new JdbcTemplate(dataSource);
        int noOfRows = jdbcTemplate.update(PMSMasterQuery.DELTEPEMDDETAILBYEMDID ,new Object []{emdId});
        LOGGER.info("method = deleteEmdDetailByEmdId , Number of rows deleted : "+ noOfRows +" subProjectId :" + emdId );
    }
    
}
