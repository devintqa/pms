package com.psk.pms.dao;

import java.math.BigDecimal;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

import com.psk.pms.model.DepositDetail;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.jdbc.core.JdbcTemplate;

import static com.psk.pms.dao.PmsMasterQuery.*;

public class DepositDetailDAOImpl implements DepositDetailDAO {


    @Qualifier("jdbcTemplate")
    @Autowired
    private JdbcTemplate jdbcTemplate;

    private static final Logger LOGGER = Logger.getLogger(DepositDetailDAOImpl.class);

    @Override
    public boolean saveDepositDetail(final DepositDetail depositDetail) {
        LOGGER.info("Save EMD DAO Start");
        if (!"Y".equalsIgnoreCase(depositDetail.getIsUpdate())) {
            jdbcTemplate.update(CREATE_DEPOSIT_DETAIL, new Object[]{
                    depositDetail.getAliasProjectName(), depositDetail.getAliasSubProjectName(), depositDetail.getDepositAmount(), depositDetail.getSqlDepositStartDate(),
                    depositDetail.getSqlDepositEndDate(), depositDetail.getDepositType(), depositDetail.getBgNumber(), depositDetail.getDepositPeriod(),
                    depositDetail.getDepositExtensionSqlDate(), depositDetail.getDepositLedgerNumber(),
                    depositDetail.getLastUpdatedBy(), depositDetail.getLastUpdatedAt(), depositDetail.getDepositDetailSubmitter(), depositDetail.getDepDetail(),depositDetail.getDepositStatus()
                    ,depositDetail.getSqlDepositRecievedDate(),depositDetail.getDepositRecievedComments()
            });
        } else {
            jdbcTemplate.update(UPDATE_DEPOSIT_DETAIL, new Object[]
                    {depositDetail.getProjId(), depositDetail.getSubProjId(), depositDetail.getDepositAmount(), depositDetail.getSqlDepositStartDate(),
                            depositDetail.getSqlDepositEndDate(), depositDetail.getDepositType(), depositDetail.getBgNumber(), depositDetail.getDepositPeriod(),
                            depositDetail.getDepositExtensionSqlDate(), depositDetail.getDepositLedgerNumber(),
                            depositDetail.getLastUpdatedBy(), depositDetail.getLastUpdatedAt(), depositDetail.getDepositDetailSubmitter(), depositDetail.getDepDetail(),
                            depositDetail.getDepositStatus(),depositDetail.getSqlDepositRecievedDate(),depositDetail.getDepositRecievedComments(),depositDetail.getDepositId()
                    });
        }
        LOGGER.info("Save EMD DAO End");
        return true;
    }

    @Override
    public List<DepositDetail> getDepositDetails() {
        LOGGER.info("Fetch EMD Details Start");
        List<DepositDetail> depositDetails = new ArrayList<DepositDetail>();
        List<Map<String, Object>> rows = jdbcTemplate.queryForList(FETCH_DEPOSIT_DETAILS);
        for (Map<String, Object> row : rows) {
            getDepositDetails(depositDetails, row);
        }
        LOGGER.info("Fetch EMD Details End");
        return depositDetails;
    }

    @Override
    public DepositDetail getDepositDetailsById(String depositId) {
        DepositDetail depositDetail = new DepositDetail();
        depositDetail = (DepositDetail) jdbcTemplate.queryForObject(FETCH_DEPOSIT_DETAIL_BY_DEPOSIT_ID, new Object[]{depositId}, new DepositDetailRowMapper());
        return depositDetail;
    }

    private class DepositDetailRowMapper implements org.springframework.jdbc.core.RowMapper<Object> {
        public Object mapRow(ResultSet rs, int rowNum) throws SQLException {
            DepositDetail depositDetail = new DepositDetail();
            depositDetail.setProjId(rs.getInt("ProjId"));
            depositDetail.setSubProjId(rs.getInt("SubProjId"));
            depositDetail.setDepositAmount(rs.getString("DepositAmount"));
            depositDetail.setDepositType(rs.getString("DepositType"));
            depositDetail.setBgNumber(rs.getString("BGNumber"));
            depositDetail.setDepositPeriod(rs.getInt("DepositPeriod"));
            depositDetail.setDepositLedgerNumber(rs.getString("DepositLedgerNumber"));
            depositDetail.setDepositDetailSubmitter(rs.getString("DepositSubmitter"));
            depositDetail.setSqlDepositStartDate(rs.getDate("DepositStartDate"));
            depositDetail.setSqlDepositEndDate(rs.getDate("DepositEndDate"));
            depositDetail.setDepositExtensionSqlDate(rs.getDate("DepositExtensionDate"));
            depositDetail.setDepositStatus(rs.getString("DepositStatus"));
            depositDetail.setSqlDepositRecievedDate(rs.getDate("DepositRecievedDate"));
            depositDetail.setDepositRecievedComments(rs.getString("DepositRecievedNote"));
            return depositDetail;
        }
    }


    public void deleteDepositDetailById(Integer depositId) {
        int noOfRows = jdbcTemplate.update(DELETE_DEPOSIT_DETAIL_BY_DEPOSIT_ID, new Object[]{depositId});
        LOGGER.info("method = deleteDepositDetailById , Number of rows deleted : " + noOfRows + " subProjectId :" + depositId);
    }

    @Override
    public List<DepositDetail> getDepositDetailsByProjectId(Integer projectId) {
        LOGGER.info("Fetch EMD Details By ProjectId :" + projectId);
        List<DepositDetail> depositDetails = new ArrayList<DepositDetail>();
        List<Map<String, Object>> rows = jdbcTemplate.queryForList(GET_DEPOSIT_DETAILS_BY_PROJECTID, new Object[]{projectId});
        for (Map<String, Object> row : rows) {
            DepositDetail depositDetail = new DepositDetail();
            depositDetail.setAliasSubProjectName("");
            depositDetail.setDepositId((Integer) row.get("DepositId"));
            depositDetail.setAliasProjectName((String) row.get("AliasProjName"));
            BigDecimal depositAmount = (BigDecimal) row.get("DepositAmount");
            depositDetail.setDepositAmount(depositAmount.toString());
            depositDetail.setSqlDepositStartDate((Date) row.get("DepositStartDate"));
            depositDetail.setSqlDepositEndDate((Date) row.get("DepositEndDate"));
            depositDetail.setDepositType((String) row.get("DepositType"));
            depositDetails.add(depositDetail);
        }
        return depositDetails;
    }

    @Override
    public List<DepositDetail> getDepositDetailsBySubProjectId(Integer subProjectId) {
        LOGGER.info("Fetch EMD Details By  subProjectId :" + subProjectId);
        List<DepositDetail> depositDetails = new ArrayList<DepositDetail>();
        List<Map<String, Object>> rows = jdbcTemplate.queryForList(GET_DEPOSIT_DETAILS_BY_SUBPROJECTID, new Object[]{subProjectId});
        for (Map<String, Object> row : rows) {
            getDepositDetails(depositDetails, row);
        }
        return depositDetails;
    }

    private void getDepositDetails(List<DepositDetail> depositDetails, Map<String, Object> row) {
        DepositDetail depositDetail = new DepositDetail();
        depositDetail.setDepositId((Integer) row.get("DepositId"));
        depositDetail.setAliasProjectName((String) row.get("AliasProjName"));
        depositDetail.setAliasSubProjectName((String) row.get("AliasSubProjName"));
        BigDecimal depositAmount = (BigDecimal) row.get("DepositAmount");
        depositDetail.setDepositAmount(depositAmount.toString());
        depositDetail.setSqlDepositStartDate((Date) row.get("DepositStartDate"));
        depositDetail.setSqlDepositEndDate((Date) row.get("DepositEndDate"));
        depositDetail.setDepositType((String) row.get("DepositType"));
        depositDetails.add(depositDetail);
    }

    @Override
    public void deleteDepositDetailBySubProjectId(Integer subProjectId) {
        int noOfRows = jdbcTemplate.update(DELETE_DEPOSIT_DETAIL_BY_SUB_PROJECTID, new Object[]{subProjectId});
        LOGGER.info("method = deleteDepositDetailBySubProjectId , Number of rows deleted : " + noOfRows + " subProjectId :" + subProjectId);
    }

    @Override
    public void deleteDepositDetailByProjectId(Integer projectId) {
        int noOfRows = jdbcTemplate.update(DELETE_DEPOSIT_DETAIL_BY_PROJECTID, new Object[]{projectId});
        LOGGER.info("method = deleteDepositDetailByProjectId , Number of rows deleted : " + noOfRows + " projectId :" + projectId);
    }

    public List<DepositDetail> getDepositDatesList() {
        List<DepositDetail> depositDetails = new ArrayList<DepositDetail>();
        List<Map<String, Object>> rows = jdbcTemplate.queryForList(DEPOSIT_DATE_QUERY);
        for (Map<String, Object> row : rows) {
            depositDetails.add(buildEMDDetail(row));
        }
        return depositDetails;
    }

    private DepositDetail buildEMDDetail(Map<String, Object> row) {
        DepositDetail depositDetail = new DepositDetail();
        BigDecimal depositAmount = (BigDecimal) row.get("DepositAmount");
        depositDetail.setDepositAmount(depositAmount.toString());
        depositDetail.setSqlDepositStartDate((Date) row.get("DepositStartDate"));
        depositDetail.setSqlDepositEndDate((Date) row.get("DepositEndDate"));
        depositDetail.setDepositType((String) row.get("DepositType"));
        depositDetail.setDepositExtensionSqlDate((Date) row.get("DepositExtensionDate"));
        return depositDetail;
    }

    @Override
    public List<String> fetchDepositTypes() {
        LOGGER.info("method = fetchDepositTypes");
        List<String> itemTypes = jdbcTemplate.queryForList(PmsMasterQuery.FETCH_DEPOSIT_TYPES, String.class);
        LOGGER.info("No of rows fetched :" + itemTypes.size());
        return itemTypes;
    }


}
