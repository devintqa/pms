package com.psk.pms.dao;

import com.mysql.jdbc.StringUtils;
import com.psk.pms.model.ProjDescDetail;
import org.apache.log4j.Logger;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.datasource.DriverManagerDataSource;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import static com.psk.pms.dao.PMSMasterQuery.*;

/**
 * Created by prakashbhanu57 on 7/6/2015.
 */
public class ProjectDescriptionDAOImpl implements ProjectDescriptionDAO {

    private static final Logger LOGGER = Logger.getLogger(ProjectDAOImpl.class);

    private DriverManagerDataSource dataSource;

    private JdbcTemplate jdbcTemplate;

    @Override
    public void deleteProjectDescriptionBySubProjectId(Integer subProjectId) {
        jdbcTemplate = new JdbcTemplate(dataSource);
        int noOfRows = jdbcTemplate.update(DELETEPROJECTDESCRIPTIONBYSUBPROJECTID, new Object[]{subProjectId});
        LOGGER.info("method = deleteProjectDescriptionBySubProjectId , Number of rows deleted : " + noOfRows + " subProjectId :" + subProjectId);
    }

    @Override
    public void deleteProjectDescriptionByProjectId(Integer projectId) {
        jdbcTemplate = new JdbcTemplate(dataSource);
        int noOfRows = jdbcTemplate.update(DELETEPROJECTDESCRIPTIONBYPROJECTID, new Object[]{projectId});
        LOGGER.info("method = deleteProjectDescriptionByProjectId , Number of rows deleted : " + noOfRows + " projectId :" + projectId);
    }

    public void deleteProjectDescription(String projectDescriptionId) {
        jdbcTemplate = new JdbcTemplate(dataSource);
        int noOfRows = jdbcTemplate.update(deleteProjDescDetailQuery, new Object[]{projectDescriptionId});
        LOGGER.info("Number of rows deleted : " + noOfRows);
    }

    @Override
    public ProjDescDetail getProjectDescDetail(String projDescId, String subProject) {
        String sql = null;
        LOGGER.info("subProject value" + subProject);
        if (!StringUtils.isNullOrEmpty(subProject)) {
            LOGGER.info("subProject value for sub project" + subProject);
            sql = projDescDetail + ",  p.AliasProjName, s.AliasSubProjName FROM projectdesc as d "
                    + "INNER JOIN subproject as s ON d.SubProjId = s.SubProjId "
                    + "JOIN project as p ON s.ProjId = p.ProjId WHERE d.ProjDescId = " + projDescId;
        } else {
            LOGGER.info("subProject value for project" + subProject);
            sql = projDescDetail + ",  p.AliasProjName FROM projectdesc as d "
                    + "JOIN project as p ON d.ProjId = p.ProjId WHERE d.ProjDescId = " + projDescId;
        }

        jdbcTemplate = new JdbcTemplate(dataSource);
        ProjDescDetail projDescDetail = null;
        List<Map<String, Object>> rows = jdbcTemplate.queryForList(sql);

        for (Map<String, Object> row : rows) {
            projDescDetail = buildProjectDescDetail(row);
        }
        return projDescDetail;
    }

    private ProjDescDetail buildProjectDescDetail(Map<String, Object> row) {
        ProjDescDetail projDescDetail = new ProjDescDetail();
        projDescDetail.setProjId((Integer) row.get("ProjId"));
        projDescDetail.setAliasProjectName((String) row.get("AliasProjName"));
        projDescDetail.setSerialNumber((String) row.get("SerialNumber"));
        projDescDetail.setSubProjId((Integer) row.get("SubProjId"));
        projDescDetail.setAliasSubProjectName((String) row.get("AliasSubProjName"));
        projDescDetail.setWorkType((String) row.get("WorkType"));
        BigDecimal quantityInFig = (BigDecimal) row.get("QuantityInFig");
        if (null == quantityInFig) {
            projDescDetail.setQuantityInFig("");
        } else {
            projDescDetail.setQuantityInFig(quantityInFig.toString());
        }
        projDescDetail.setQuantityInWords((String) row.get("QuantityInWords"));
        projDescDetail.setDescription((String) row.get("Description"));
        projDescDetail.setAliasDescription((String) row.get("AliasDescription"));
        BigDecimal rateInFig = (BigDecimal) row.get("RateInFig");
        projDescDetail.setRateInFig(rateInFig.toString());
        projDescDetail.setRateInWords((String) row.get("RateInWords"));
        BigDecimal amount = (BigDecimal) row.get("Amount");
        if (null == amount) {
            projDescDetail.setProjDescAmount("");
        } else {
            projDescDetail.setProjDescAmount(amount.toString());
        }
        projDescDetail.setProjDescId((Integer) row.get("ProjDescId"));
        return projDescDetail;
    }

    public boolean isAliasDescriptionAlreadyExisting(ProjDescDetail projectDescDetail) {
        String sql = null;
        int total = 0;
        jdbcTemplate = new JdbcTemplate(dataSource);
        if (!projectDescDetail.isSubProjectDesc()) {
            LOGGER.info("There is no sub project selected");
            sql = "SELECT COUNT(*) FROM projectdesc where ProjId = ? and AliasDescription = ?";
            total = jdbcTemplate.queryForObject(sql, Integer.class,
                    new Object[]{projectDescDetail.getAliasProjectName(), projectDescDetail.getAliasDescription()});
        } else {
            LOGGER.info("There is a sub project selected");
            sql = "SELECT COUNT(*) FROM projectdesc where ProjId = ? and SubProjId = ? and AliasDescription = ?";
            total = jdbcTemplate.queryForObject(sql, Integer.class,
                    new Object[]{projectDescDetail.getAliasProjectName(), projectDescDetail.getAliasSubProjectName(), projectDescDetail.getAliasDescription()});
        }

        if (total == 0) {
            return false;
        }
        return true;
    }

    public List<ProjDescDetail> getProjectDescDetailList(Integer projectId, boolean searchUnderProject) {
        String sql;
        if (searchUnderProject) {
            sql = projDescDetailQuery + " where ProjId = " + projectId + " and SubProjId is null";
        } else {
            sql = projDescDetailQuery + " where SubProjId = " + projectId;
        }
        jdbcTemplate = new JdbcTemplate(dataSource);

        List<ProjDescDetail> projectDescDetailList = new ArrayList<ProjDescDetail>();
        List<Map<String, Object>> rows = jdbcTemplate.queryForList(sql);

        for (Map<String, Object> row : rows) {
            projectDescDetailList.add(buildProjectDescDetail(row));
        }
        return projectDescDetailList;
    }

    public List<ProjDescDetail> getSubProjectDescDetailList(Integer subProjectId) {
        String sql = projDescDetailQuery + " where SubProjId = " + subProjectId;
        jdbcTemplate = new JdbcTemplate(dataSource);

        List<ProjDescDetail> projectDescDetailList = new ArrayList<ProjDescDetail>();
        List<Map<String, Object>> rows = jdbcTemplate.queryForList(sql);

        for (Map<String, Object> row : rows) {
            projectDescDetailList.add(buildProjectDescDetail(row));
        }
        return projectDescDetailList;
    }

    public boolean saveProjDesc(final ProjDescDetail projDescDetail) {

        String updateSql = "UPDATE projectDesc set WorkType  = ?, QuantityInFig = ?, QuantityInWords = ?, Description = ?," +
                "AliasDescription = ?, RateInFig = ?, RateInWords = ?, Amount=?, LastUpdatedBy =?,LastUpdatedAt=? WHERE ProjDescId = ?";
        String insertSql = null;
        jdbcTemplate = new JdbcTemplate(dataSource);
        if (!"Y".equalsIgnoreCase(projDescDetail.getIsUpdate())) {
            if (projDescDetail.isSubProjectDesc()) {
                insertSql = "INSERT INTO projectDesc (ProjId, SubProjId,SerialNumber ,WorkType, QuantityInFig, QuantityInWords, "
                        + "Description, AliasDescription, RateInFig, RateInWords, Amount,LastUpdatedBy ,LastUpdatedAt) " +
                        "VALUES (?, ? , ?, ?, ?, ?, ?, ?, ?, ?, ?,?,?)";
                jdbcTemplate.update(insertSql, new Object[]{projDescDetail.getAliasProjectName(), projDescDetail.getAliasSubProjectName(), projDescDetail.getSerialNumber(),
                        projDescDetail.getWorkType(), projDescDetail.getQuantityInFig(), projDescDetail.getQuantityInWords(),
                        projDescDetail.getDescription(), projDescDetail.getAliasDescription(),
                        projDescDetail.getRateInFig(), projDescDetail.getRateInWords(),
                        projDescDetail.getProjDescAmount(), projDescDetail.getLastUpdatedBy(), projDescDetail.getLastUpdatedAt()
                });
            } else {
                insertSql = "INSERT INTO projectDesc (ProjId,SerialNumber ,WorkType, QuantityInFig, QuantityInWords, "
                        + "Description, AliasDescription, RateInFig, RateInWords, Amount,LastUpdatedBy ,LastUpdatedAt) " +
                        "VALUES (?, ?, ? , ?, ?, ?, ?, ?, ?, ?,?,?)";
                jdbcTemplate.update(insertSql, new Object[]{projDescDetail.getAliasProjectName(), projDescDetail.getSerialNumber(),
                        projDescDetail.getWorkType(), projDescDetail.getQuantityInFig(), projDescDetail.getQuantityInWords(),
                        projDescDetail.getDescription(), projDescDetail.getAliasDescription(),
                        projDescDetail.getRateInFig(), projDescDetail.getRateInWords(),
                        projDescDetail.getProjDescAmount(), projDescDetail.getLastUpdatedBy(), projDescDetail.getLastUpdatedAt()
                });
            }


        } else {
            jdbcTemplate.update(updateSql, new Object[]{projDescDetail.getWorkType(), projDescDetail.getQuantityInFig(), projDescDetail.getQuantityInWords(),
                    projDescDetail.getDescription(), projDescDetail.getAliasDescription(),
                    projDescDetail.getRateInFig(), projDescDetail.getRateInWords(),
                    projDescDetail.getProjDescAmount(), projDescDetail.getLastUpdatedBy(), projDescDetail.getLastUpdatedAt(), projDescDetail.getProjDescId()
            });
        }
        return true;
    }


    public boolean isSerialNumberAlreadyExisting(ProjDescDetail projectDescDetail) {
        String sql = null;
        int total = 0;
        jdbcTemplate = new JdbcTemplate(dataSource);
        if (!projectDescDetail.isSubProjectDesc()) {
            LOGGER.info("method {} , There is no sub project selected" + "isSerialNumberAlreadyExisting");
            sql = "SELECT COUNT(*) FROM projectdesc where ProjId = ? and SerialNumber = ?";
            total = jdbcTemplate.queryForObject(sql, Integer.class,
                    new Object[]{projectDescDetail.getAliasProjectName(), projectDescDetail.getSerialNumber()});
        } else {
            LOGGER.info("method {} , There is sub project selected" + "isSerialNumberAlreadyExisting");
            sql = "SELECT COUNT(*) FROM projectdesc where ProjId = ? and SubProjId = ? and SerialNumber = ?";
            total = jdbcTemplate.queryForObject(sql, Integer.class,
                    new Object[]{projectDescDetail.getAliasProjectName(), projectDescDetail.getAliasSubProjectName(), projectDescDetail.getSerialNumber()});
        }

        if (total == 0) {
            return false;
        }
        return true;
    }

    public DriverManagerDataSource getDataSource() {
        return dataSource;
    }

    public void setDataSource(DriverManagerDataSource dataSource) {
        this.dataSource = dataSource;
    }
}