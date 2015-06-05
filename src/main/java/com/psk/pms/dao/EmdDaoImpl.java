package com.psk.pms.dao;

import com.psk.pms.model.EMDDetail;
import org.apache.log4j.Logger;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.datasource.DriverManagerDataSource;

public class EmdDaoImpl implements EmdDAO {

    private DriverManagerDataSource dataSource;
    private JdbcTemplate jdbcTemplate;

    private static final Logger LOGGER = Logger.getLogger(EmdDaoImpl.class);

    @Override
    public boolean saveEmd(final EMDDetail emdDetail) {
        String createSql = "INSERT INTO emddetail (ProjId , SubProjId , EmdAmount , EmdStartDate ,EmdEndDate ,EmdType, BGNumber ," +
                "EmdPeriod,EmdExtensionDate,EmdLedgerNumber, LastUpdatedBy,LastUpdatedAt,CompetitorName)" +
                "VALUES ( ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? ,?)";


        String updateSql = "UPDATE emddetail set ProjId = ? . SubProjId = ? ,EmdAmount = ? , EmdStartDate = ? ,EmdEndDate = ?,EmdType =? , BGNumber =?," +
                "EmdPeriod = ?,EmdExtensionDate = ? ,EmdLedgerNumber =? ,SubProjectEmd = ? , " +
                "LastUpdatedBy = ?,LastUpdatedAt =? ,CompetitorName =? WHERE EmdId = ? ";

        jdbcTemplate = new JdbcTemplate(dataSource);
        if (!"Y".equalsIgnoreCase(emdDetail.getIsUpdate())) {
            jdbcTemplate.update(createSql, new Object[]{
                    emdDetail.getAliasProjectName(), emdDetail.getAliasSubProjectName(), emdDetail.getEmdAmount(), emdDetail.getSqlEmdStartDate(),
                    emdDetail.getSqlEmdEndDate(), emdDetail.getEmdType(), emdDetail.getBgNumber(), emdDetail.getEmdPeriod(),
                    emdDetail.getEmdExtensionSqlDate(), emdDetail.getEmdLedgerNumber(),
                     emdDetail.getLastUpdatedBy(), emdDetail.getLastUpdatedAt(),emdDetail.getCompetitorName()
            });
        } else {
            jdbcTemplate.update(updateSql, new Object[]
                    {emdDetail.getAliasProjectName(), emdDetail.getAliasSubProjectName(), emdDetail.getEmdAmount(), emdDetail.getSqlEmdStartDate(),
                            emdDetail.getSqlEmdEndDate(), emdDetail.getEmdType(), emdDetail.getBgNumber(), emdDetail.getEmdPeriod(),
                            emdDetail.getEmdExtensionSqlDate(), emdDetail.getEmdLedgerNumber(),
                            emdDetail.getLastUpdatedBy(), emdDetail.getLastUpdatedAt(),emdDetail.getCompetitorName(), emdDetail.getEmdId()
                    });
        }
        return true;
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
}
