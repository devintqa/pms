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
        String createSql = "INSERT INTO emddetail (ProjId , SubProjId , EmdAmount , EmdStartDate ,EmdEndDate ,EmdType, BGNumber ," +
                "EmdPeriod,EmdExtensionDate,EmdLedgerNumber, LastUpdatedBy,LastUpdatedAt,EmdSubmitter)" +
                "VALUES ( ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? ,?)";


        String updateSql = "UPDATE emddetail set ProjId = ? , SubProjId = ? ,EmdAmount = ? , EmdStartDate = ? ,EmdEndDate = ?,EmdType =? , BGNumber =?," +
                "EmdPeriod = ?,EmdExtensionDate = ? ,EmdLedgerNumber =? , " +
                "LastUpdatedBy = ?,LastUpdatedAt =? ,EmdSubmitter =? WHERE EmdId = ? ";

        jdbcTemplate = new JdbcTemplate(dataSource);
        if (!"Y".equalsIgnoreCase(emdDetail.getIsUpdate())) {
            jdbcTemplate.update(createSql, new Object[]{
                    emdDetail.getAliasProjectName(), emdDetail.getAliasSubProjectName(), emdDetail.getEmdAmount(), emdDetail.getSqlEmdStartDate(),
                    emdDetail.getSqlEmdEndDate(), emdDetail.getEmdType(), emdDetail.getBgNumber(), emdDetail.getEmdPeriod(),
                    emdDetail.getEmdExtensionSqlDate(), emdDetail.getEmdLedgerNumber(),
                     emdDetail.getLastUpdatedBy(), emdDetail.getLastUpdatedAt(),emdDetail.getEmdSubmitter()
            });
        } else {
            jdbcTemplate.update(updateSql, new Object[]
                    {emdDetail.getProjId(), emdDetail.getSubProjId(), emdDetail.getEmdAmount(), emdDetail.getSqlEmdStartDate(),
                            emdDetail.getSqlEmdEndDate(), emdDetail.getEmdType(), emdDetail.getBgNumber(), emdDetail.getEmdPeriod(),
                            emdDetail.getEmdExtensionSqlDate(), emdDetail.getEmdLedgerNumber(),
                            emdDetail.getLastUpdatedBy(), emdDetail.getLastUpdatedAt(),emdDetail.getEmdSubmitter(), emdDetail.getEmdId()
                    });
        }
        return true;
    }

    @Override
    public List<EMDDetail> getEmdDetails() {
        List<EMDDetail> emdDetails = new ArrayList<>();
        jdbcTemplate = new JdbcTemplate(dataSource);
        String emdDetailsQuery = "select e.EmdId ,e.ProjId , p.AliasProjName , s.AliasSubProjName ," +
                " s.SubProjName , e.EmdType , e.EmdAmount , e.EmdStartDate , e.EmdEndDate " +
                "from emddetail e left join project as p on e.ProjId = p.ProjId " +
                "left join subproject as s on e.SubProjId=s.SubProjId ";

        List<Map<String, Object>> rows =  jdbcTemplate.queryForList(emdDetailsQuery);
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
        return emdDetails;
    }

    @Override
    public EMDDetail getEmdDetailsByEmdId(String emdId) {
        EMDDetail emdDetail = new EMDDetail();
        jdbcTemplate = new JdbcTemplate(dataSource);
        String emdDetailByEmdIdQuery = "select  ProjId , SubProjId , EmdAmount , EmdStartDate ,EmdEndDate ,EmdType, BGNumber ," +
                "EmdPeriod,EmdExtensionDate,EmdLedgerNumber, EmdSubmitter from emddetail where EmdId = ?";
        emdDetail = (EMDDetail) jdbcTemplate.queryForObject(emdDetailByEmdIdQuery,new Object[] {emdId},new EmdDetailRowMapper());
        return emdDetail;
    }

    public DriverManagerDataSource getDataSource() {
        return dataSource;
    }

    public void setDataSource(DriverManagerDataSource dataSource) {
        this.dataSource = dataSource;
    }

    public JdbcTemplate getJdbcTemplate() {
        return jdbcTemplate;
    }

    public void setJdbcTemplate(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
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
            emdDetail.setEmdStartDate(rs.getString("EmdStartDate"));
            emdDetail.setEmdEndDate(rs.getString("EmdEndDate"));
            emdDetail.setEmdExtensionDate(rs.getString("EmdExtensionDate"));
            return emdDetail;
        }
    }
}
