package com.psk.pms.dao;

import static com.psk.pms.dao.PmsMasterQuery.DELETEBASEDESCRIPTION;
import static com.psk.pms.dao.PmsMasterQuery.DELETEPROJECTDESCRIPTIONBYSUBPROJECTID;
import static com.psk.pms.dao.PmsMasterQuery.FETCHBASEDESCRIPTIONS;
import static com.psk.pms.dao.PmsMasterQuery.GETBASEDESCRIPTION;
import static com.psk.pms.dao.PmsMasterQuery.INSERTBASEDESCRIPTION;
import static com.psk.pms.dao.PmsMasterQuery.INSERTGOVPROJECTDESCRIPTION;
import static com.psk.pms.dao.PmsMasterQuery.INSERTPROJECTDESCRIPTION;
import static com.psk.pms.dao.PmsMasterQuery.INSERTSUBPROJECTDESCRIPTION;
import static com.psk.pms.dao.PmsMasterQuery.ISBASEDESCEXISTS;
import static com.psk.pms.dao.PmsMasterQuery.NOOFPROJECTDESCASSOCIATEDTOPROJECT;
import static com.psk.pms.dao.PmsMasterQuery.NOOFPROJECTDESCASSOCIATEDTOSUBPROJECT;
import static com.psk.pms.dao.PmsMasterQuery.NO_OF_GOV_PROJECT_DESC_ASSOCIATED_TO_PROJECT;
import static com.psk.pms.dao.PmsMasterQuery.NO_OF_GOV_PROJECT_DESC_ASSOCIATED_TO_SUBPROJECT;
import static com.psk.pms.dao.PmsMasterQuery.UPDATEBASEDESCRIPTION;
import static com.psk.pms.dao.PmsMasterQuery.baseDescDetail;
import static com.psk.pms.dao.PmsMasterQuery.compareDataQuery;
import static com.psk.pms.dao.PmsMasterQuery.projDescDetail;
import static com.psk.pms.dao.PmsMasterQuery.projDescDetailQuery;

import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.jdbc.core.BatchPreparedStatementSetter;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.simple.ParameterizedBeanPropertyRowMapper;
import org.springframework.transaction.annotation.Transactional;

import com.mysql.jdbc.StringUtils;
import com.psk.pms.Constants;
import com.psk.pms.constants.DescriptionType;
import com.psk.pms.model.DescItemDetail;
import com.psk.pms.model.DescItemDetail.ItemDetail;
import com.psk.pms.model.ProjDescComparisonDetail;
import com.psk.pms.model.ProjDescDetail;
import com.psk.pms.model.SearchDetail;


public class ProjectDescriptionDAOImpl implements ProjectDescriptionDAO {

	private static final Logger LOGGER = Logger.getLogger(ProjectDescriptionDAOImpl.class);
	
	ResultTransformer transformer = new ResultTransformer();
	
	@Qualifier("jdbcTemplate")@Autowired
	private JdbcTemplate jdbcTemplate;

	@Autowired
	private ItemDAO itemDAO;

	@Autowired
	private NamedParameterJdbcTemplate namedParameterJdbcTemplate;

	@Override
	public void deleteProjectDescriptionBySubProjectId(Integer subProjectId) {

		int noOfRows = jdbcTemplate.update(DELETEPROJECTDESCRIPTIONBYSUBPROJECTID, new Object[] {
			subProjectId
		});
		LOGGER.info("method = deleteProjectDescriptionBySubProjectId , Number of rows deleted : " + noOfRows + " subProjectId :" + subProjectId);
	}

	@Override
	public void deleteProjectDescriptionByProjectId(String descType, Integer projectId) {
		int noOfRows = 0;
		if(descType.equalsIgnoreCase(Constants.ALL_DESCRIPTION_TYPE)){
			noOfRows = jdbcTemplate.update("DELETE FROM "+DescriptionType.getDescriptionTableName(Constants.PSK)+" WHERE ProjId = ?",
					new Object[] {
						projectId
					});
			noOfRows = jdbcTemplate.update("DELETE FROM "+DescriptionType.getDescriptionTableName(Constants.GOVERNMENT)+" WHERE ProjId = ?",
					new Object[] {
						projectId
					});
		}else{
			noOfRows = jdbcTemplate.update("DELETE FROM "+DescriptionType.getDescriptionTableName(descType)+" WHERE ProjId = ?",
					new Object[] {
				projectId
			});
		}
		LOGGER.info("method = deleteProjectDescriptionByProjectId , Number of rows deleted : " + noOfRows + " projectId :" + projectId);
	}

	public void deleteProjectDescription(String projectDescriptionId,String descType) {
		itemDAO.deleteItemByProjectDescriptionId(projectDescriptionId,descType);

		int noOfRows = jdbcTemplate.update("DELETE FROM " + DescriptionType.getDescriptionTableName(descType) + " where ProjDescId = ?",
				new Object[]{
						projectDescriptionId
				});
		LOGGER.info("Number of rows deleted : " + noOfRows);
	}

	@Override
	public ProjDescDetail getProjectDescDetail(String projDescId, String subProject, String descType) {
		String sql = null;
		LOGGER.info("subProject value" + subProject);
		if (!StringUtils.isNullOrEmpty(subProject)) {
			LOGGER.info("subProject value for sub project" + subProject);
			sql = projDescDetail + ",  p.AliasProjName, s.AliasSubProjName FROM "+ DescriptionType.getDescriptionTableName(descType) +" as d " + "INNER JOIN subproject as s ON d.SubProjId = s.SubProjId " + "JOIN project as p ON s.ProjId = p.ProjId WHERE d.ProjDescId = " + projDescId;
		} else {
			LOGGER.info("subProject value for project" + subProject);
			sql = projDescDetail + ",  p.AliasProjName FROM "+ DescriptionType.getDescriptionTableName(descType) +" as d " + "JOIN project as p ON d.ProjId = p.ProjId WHERE d.ProjDescId = " + projDescId;
		}

		ProjDescDetail projDescDetail = null;
		List < Map < String, Object >> rows = jdbcTemplate.queryForList(sql);

		for (Map < String, Object > row: rows) {
			projDescDetail = transformer.buildProjectDescDetail(row);
		}
		return projDescDetail;
	}


	public boolean isAliasDescriptionAlreadyExisting(
	ProjDescDetail projectDescDetail) {
		String sql = null;
		int total = 0;

		if (!projectDescDetail.isSubProjectDesc()) {
			LOGGER.info("There is no sub project selected");
			sql = "SELECT COUNT(*) FROM projectdesc where ProjId = ? and AliasDescription = ?";
			total = jdbcTemplate.queryForObject(sql, Integer.class,
			new Object[] {
				projectDescDetail.getAliasProjectName(),
				projectDescDetail.getAliasDescription()
			});
		} else {
			LOGGER.info("There is a sub project selected");
			sql = "SELECT COUNT(*) FROM projectdesc where ProjId = ? and SubProjId = ? and AliasDescription = ?";
			total = jdbcTemplate.queryForObject(sql, Integer.class,
			new Object[] {
				projectDescDetail.getAliasProjectName(),
				projectDescDetail.getAliasSubProjectName(),
				projectDescDetail.getAliasDescription()
			});
		}

		if (total == 0) {
			return false;
		}
		return true;
	}

	public List < ProjDescDetail > getProjectDescDetailList(Integer projectId,
	boolean searchUnderProject) {
		LOGGER.info("Getting projectDescriptions for projectId:"+projectId);
		String sql;
		if (searchUnderProject) {
			sql = projDescDetailQuery + " where ProjId = " + projectId + " and SubProjId is null";
		} else {
			sql = projDescDetailQuery + " where SubProjId = " + projectId;
		}

		List < ProjDescDetail > projectDescDetailList = new ArrayList < ProjDescDetail > ();
		List < Map < String, Object >> rows = jdbcTemplate.queryForList(sql);

		for (Map < String, Object > row: rows) {
			projectDescDetailList.add(transformer.buildProjectDescDetail(row));
		}
		return projectDescDetailList;
	}

    public List<ProjDescDetail> getProjectDescDetailList(SearchDetail searchDetail) {
		LOGGER.info("Fetch all projectDescriptions configured for the project"+ searchDetail.getProjId() +
				" for descriptionType:"+searchDetail.getSearchOn());
        String sql = "SELECT ProjId, SubProjId, SerialNumber, WorkType, Quantity, Metric, Description, AliasDescription, PricePerQuantity, TotalCost, ProjDescId";
        if ("project".equalsIgnoreCase(searchDetail.getSearchUnder())) {
            sql = sql + " FROM " + DescriptionType.getDescriptionTableName(searchDetail.getSearchOn()) + " where ProjId = '" + searchDetail.getProjId() + "'" + " and SubProjId is null";
        } else {
            sql = sql + " FROM " + DescriptionType.getDescriptionTableName(searchDetail.getSearchOn()) + " where SubProjId = " + searchDetail.getProjId();
        }

        List<ProjDescDetail> projectDescDetailList = new ArrayList<ProjDescDetail>();
        List<Map<String, Object>> rows = jdbcTemplate.queryForList(sql);

        for (Map<String, Object> row : rows) {
            projectDescDetailList.add(transformer.buildProjectDescDetail(row));
        }
        return projectDescDetailList;
    }

	public List < ProjDescComparisonDetail > getProjectDescComparisonDetail(
	Integer projId) {
		LOGGER.info("Fetching the comparison detail for the project: " + projId);
		String sql;
		sql = compareDataQuery + "  and p.ProjId = " + projId;

		List < ProjDescComparisonDetail > projDescComparisonDetailList = new ArrayList < ProjDescComparisonDetail > ();
		List < Map < String, Object >> rows = jdbcTemplate.queryForList(sql);

		for (Map < String, Object > row: rows) {
			projDescComparisonDetailList.add(transformer.buildProjDescComparisonDetail(row));
		}
		return projDescComparisonDetailList;
	}

	public List < ProjDescDetail > getSubProjectDescDetailList(Integer subProjectId) {
		String sql = projDescDetailQuery + " where SubProjId = " + subProjectId;

		List < ProjDescDetail > projectDescDetailList = new ArrayList < ProjDescDetail > ();
		List < Map < String, Object >> rows = jdbcTemplate.queryForList(sql);

		for (Map < String, Object > row: rows) {
			projectDescDetailList.add(transformer.buildProjectDescDetail(row));
		}
		return projectDescDetailList;
	}

	public boolean saveProjDesc(final ProjDescDetail projDescDetail) {
		String descTable = projDescDetail.getDescType().equalsIgnoreCase(Constants.PSK) ? "projectdesc" : "govprojectdesc";
		String updateSql = "UPDATE "+descTable+" set WorkType  = ?, Quantity = ?, Metric = ?, Description = ?," + "AliasDescription = ?, PricePerQuantity = ?, TotalCost=?, LastUpdatedBy =?,LastUpdatedAt=? WHERE ProjDescId = ?";
		String insertSql = null;

		if (!"Y".equalsIgnoreCase(projDescDetail.getIsUpdate())) {
			if (projDescDetail.isSubProjectDesc()) {
				insertSql = "INSERT INTO "+descTable+" (ProjId, SubProjId, SerialNumber, WorkType, Quantity, Metric, " + "Description, AliasDescription, PricePerQuantity, TotalCost, LastUpdatedBy ,LastUpdatedAt) " + "VALUES (?, ? , ?, ?, ?, ?, ?, ?, ?, ?, ?,?)";
				jdbcTemplate.update(
				insertSql,
				new Object[] {
					projDescDetail.getProjId(),
					projDescDetail.getSubProjId(),
					projDescDetail.getSerialNumber(),
					projDescDetail.getWorkType(),
					projDescDetail.getQuantity(),
					projDescDetail.getMetric(),
					projDescDetail.getDescription(),
					projDescDetail.getAliasDescription(),
					projDescDetail.getPricePerQuantity(),
					projDescDetail.getTotalCost(),
					projDescDetail.getLastUpdatedBy(),
					projDescDetail.getLastUpdatedAt()
				});
			} else {
				insertSql = "INSERT INTO "+descTable+" (ProjId, SerialNumber, WorkType, Quantity, Metric, " + "Description, AliasDescription, PricePerQuantity, TotalCost, LastUpdatedBy, LastUpdatedAt) " + "VALUES (?, ?, ? , ?, ?, ?, ?, ?, ?, ?,?)";
				jdbcTemplate.update(
				insertSql,
				new Object[] {
					projDescDetail.getProjId(),
					projDescDetail.getSerialNumber(),
					projDescDetail.getWorkType(),
					projDescDetail.getQuantity(),
					projDescDetail.getMetric(),
					projDescDetail.getDescription(),
					projDescDetail.getAliasDescription(),
					projDescDetail.getPricePerQuantity(),
					projDescDetail.getTotalCost(),
					projDescDetail.getLastUpdatedBy(),
					projDescDetail.getLastUpdatedAt()
				});
			}

		} else {
			jdbcTemplate.update(
			updateSql,
			new Object[] {
				projDescDetail.getWorkType(),
				projDescDetail.getQuantity(),
				projDescDetail.getMetric(),
				projDescDetail.getDescription(),
				projDescDetail.getAliasDescription(),
				projDescDetail.getPricePerQuantity(),
				projDescDetail.getTotalCost(),
				projDescDetail.getLastUpdatedBy(),
				projDescDetail.getLastUpdatedAt(),
				projDescDetail.getProjDescId()
			});
		}

		return true;
	}

	public boolean isSerialNumberAlreadyExisting(
	ProjDescDetail projectDescDetail) {
		String sql = null;
		int total = 0;

		if (!projectDescDetail.isSubProjectDesc()) {
			LOGGER.info("method {} , There is no sub project selected" + "isSerialNumberAlreadyExisting");
			sql = "SELECT COUNT(*) FROM projectdesc where ProjId = ? and SerialNumber = ?";
			total = jdbcTemplate.queryForObject(sql, Integer.class,
			new Object[] {
				projectDescDetail.getAliasProjectName(),
				projectDescDetail.getSerialNumber()
			});
		} else {
			LOGGER.info("method {} , There is sub project selected" + "isSerialNumberAlreadyExisting");
			sql = "SELECT COUNT(*) FROM projectdesc where ProjId = ? and SubProjId = ? and SerialNumber = ?";
			total = jdbcTemplate.queryForObject(sql, Integer.class,
			new Object[] {
				projectDescDetail.getAliasProjectName(),
				projectDescDetail.getAliasSubProjectName(),
				projectDescDetail.getSerialNumber()
			});
		}

		if (total == 0) {
			return false;
		}
		return true;
	}
	
	private Integer getProjectDescDetailByAlias(String descType, String aliasDescription) {
		String sql = "SELECT ProjDescId FROM "+DescriptionType.getDescriptionTableName(descType)+" where AliasDescription = ?";
		List < Map < String, Object >> maps = jdbcTemplate.queryForList(sql, new Object[] {
				aliasDescription
		});
		System.out.println("getProjectDescDetailByAlias" + maps.size());
		Integer projectDescriptionId = (Integer) maps.get(0).get("ProjDescId");
		return projectDescriptionId;
	}

    @Transactional
	public void saveProjectDescriptionDetails(final List < ProjDescDetail > projDescDetails) {

		jdbcTemplate.batchUpdate(INSERTPROJECTDESCRIPTION,
		new BatchPreparedStatementSetter() {@Override
			public void setValues(PreparedStatement ps, int i)
			throws SQLException {
				ProjDescDetail projDescDetail = projDescDetails.get(i);
				ps.setInt(1, projDescDetail.getProjId());
				ps.setString(2, projDescDetail.getSerialNumber());
				ps.setString(3, projDescDetail.getWorkType());
				ps.setString(4, projDescDetail.getQuantity());
				ps.setString(5, projDescDetail.getMetric());
				ps.setString(6, projDescDetail.getDescription());
				ps.setString(7, projDescDetail.getAliasDescription());
				ps.setString(8, projDescDetail.getLastUpdatedBy());
				ps.setDate(9, new java.sql.Date(Calendar.getInstance().getTimeInMillis()));
				ps.setString(10, projDescDetail.getPricePerQuantity());
				ps.setString(11, projDescDetail.getTotalCost());
			}

			@Override
			public int getBatchSize() {
				return projDescDetails.size();
			}

		});

		LOGGER.info("saveProjectDescriptionDetails:projDescDetails.size(): " + projDescDetails.size());
		for (ProjDescDetail projDescDetail: projDescDetails) {
			try {
				if (null != projDescDetail.getBaseDescName() && !projDescDetail.getBaseDescName().isEmpty()) {
					ProjDescDetail baseDescDetail = this.getBaseDescription(Constants.PSK, projDescDetail.getBaseDescName());
					if (null == baseDescDetail.getSubProjId()) {
						baseDescDetail.setSubProjId(0);
					}
					LOGGER.debug("saveProjectDescriptionDetails:projDescDetails:baseDescDetail" + baseDescDetail.getBaseDescId());
					DescItemDetail descItemDetail = new DescItemDetail();
					descItemDetail.setBaseDescId(new Integer(baseDescDetail.getBaseDescId()));
					descItemDetail.setProjId(projDescDetail.getProjId());
					descItemDetail.setDescType(Constants.PSK);
					descItemDetail.setSubProjId(baseDescDetail.getSubProjId());
					descItemDetail.setProjDescId(this.getProjectDescDetailByAlias(Constants.PSK, projDescDetail.getAliasDescription()));
					descItemDetail.setProjDescSerial(projDescDetail.getSerialNumber());
					List < ItemDetail > itemDetailList = itemDAO.getBaseDescription(descItemDetail).getItemDetail();
					LOGGER.debug("saveProjectDescriptionDetails:itemDetailList: " + itemDetailList.size());
					descItemDetail.setItemDetail(itemDetailList);
					if (itemDetailList.size() > 0) {
						itemDAO.insertProjectDescriptionItems(descItemDetail);
					}
				}
			} catch (Exception e) {
				LOGGER.error("Save project Desc items failed"+e.getMessage());
			}
		}

	}

	private ProjDescDetail getBaseDescription(String descType, String baseDescName) {
			ProjDescDetail baseDescDetail = new ProjDescDetail();
			String sql = "SELECT * FROM basedesc WHERE category = ? and BaseDescription = ?";;
			List < Map < String, Object >> maps = jdbcTemplate.queryForList(sql, new Object[] {
					descType,
					baseDescName
			});
			for (Map < String, Object > map: maps) {
				baseDescDetail = transformer.buildBaseDescDetail(map);
			}
			return baseDescDetail;
	}

	public void saveGovProjectDescriptionDetails(final List < ProjDescDetail > projDescDetails) {

		if(projDescDetails.size() > 0)
			deleteGovProjectDescriptionByProjectId(projDescDetails.get(0).getProjId());

		jdbcTemplate.batchUpdate(INSERTGOVPROJECTDESCRIPTION,
		new BatchPreparedStatementSetter() {@Override
			public void setValues(PreparedStatement ps, int i)
			throws SQLException {
				ProjDescDetail projDescDetail = projDescDetails.get(i);
				ps.setInt(1, projDescDetail.getProjId());
				ps.setString(2, projDescDetail.getSerialNumber());
				ps.setString(3, projDescDetail.getWorkType());
				ps.setString(4, projDescDetail.getQuantity());
				ps.setString(5, projDescDetail.getMetric());
				ps.setString(6, projDescDetail.getDescription());
				ps.setString(7, projDescDetail.getAliasDescription());
				ps.setString(8, projDescDetail.getLastUpdatedBy());
				ps.setDate(9, new java.sql.Date(Calendar.getInstance().getTimeInMillis()));
				ps.setString(10, projDescDetail.getPricePerQuantity());
				ps.setString(11, projDescDetail.getTotalCost());
			}

			@Override
			public int getBatchSize() {
				return projDescDetails.size();
			}
		});

		for (ProjDescDetail projDescDetail: projDescDetails) {
			try {
				if (null != projDescDetail.getBaseDescName() && !projDescDetail.getBaseDescName().isEmpty()) {
					ProjDescDetail baseDescDetail = this.getBaseDescription(Constants.GOVERNMENT, projDescDetail.getBaseDescName());
					if (null == baseDescDetail.getSubProjId()) {
						baseDescDetail.setSubProjId(0);
					}
					DescItemDetail descItemDetail = new DescItemDetail();
					descItemDetail.setBaseDescId(new Integer(baseDescDetail.getBaseDescId()));
					descItemDetail.setProjId(projDescDetail.getProjId());
					descItemDetail.setSubProjId(baseDescDetail.getSubProjId());
					descItemDetail.setDescType(Constants.GOVERNMENT);
					descItemDetail.setProjDescId(this.getProjectDescDetailByAlias(Constants.GOVERNMENT, projDescDetail.getAliasDescription()));
					descItemDetail.setProjDescSerial(projDescDetail.getSerialNumber());
					List < ItemDetail > itemDetailList = itemDAO.getBaseDescription(descItemDetail).getItemDetail();
					descItemDetail.setItemDetail(itemDetailList);
					if (itemDetailList.size() > 0) {
						itemDAO.insertProjectDescriptionItems(descItemDetail);
					}
				}
			} catch (Exception e) {

			}
		}
	}

	public void saveSubProjectDescriptionDetails(
	final List < ProjDescDetail > projDescDetails) {

		jdbcTemplate.batchUpdate(INSERTSUBPROJECTDESCRIPTION,
		new BatchPreparedStatementSetter() {@Override
			public void setValues(PreparedStatement ps, int i)
			throws SQLException {
				ProjDescDetail projDescDetail = projDescDetails.get(i);
				ps.setInt(1, projDescDetail.getProjId());
				ps.setInt(2, projDescDetail.getSubProjId());
				ps.setString(3, projDescDetail.getSerialNumber());
				ps.setString(4, projDescDetail.getWorkType());
				ps.setString(5, projDescDetail.getQuantity());
				ps.setString(6, projDescDetail.getMetric());
				ps.setString(7, projDescDetail.getDescription());
				ps.setString(8, projDescDetail.getAliasDescription());
				ps.setString(9, projDescDetail.getLastUpdatedBy());
				ps.setDate(10, new java.sql.Date(Calendar.getInstance().getTimeInMillis()));
			}

			@Override
			public int getBatchSize() {
				return projDescDetails.size();
			}
		});
	}

	@Override
	public boolean isProjectDescriptionDetailsExistsForProject(int projectId, String governmentEst) {
		int noOfrows = 0;
		String sql = "";
		if ("Y".equalsIgnoreCase(governmentEst)) {
			sql = NO_OF_GOV_PROJECT_DESC_ASSOCIATED_TO_PROJECT;
		} else {
			sql = NOOFPROJECTDESCASSOCIATEDTOPROJECT;
		}
		noOfrows = jdbcTemplate.queryForObject(sql, Integer.class,
		new Object[] {
			(Integer) projectId
		});
		LOGGER.info("method = isProjectDescriptionDetailsExistsForProject , isDataPresent :" + (noOfrows != 0));
		if (noOfrows != 0) {
			return true;
		}
		return false;
	}

	@Override
	public boolean isProjectDescriptionDetailsExistsForSubProject(
	int subProjectId, String governmentEst) {
		int noOfrows = 0;
		String sql = "";
		if ("Y".equalsIgnoreCase(governmentEst)) {
			sql = NO_OF_GOV_PROJECT_DESC_ASSOCIATED_TO_SUBPROJECT;
		} else {
			sql = NOOFPROJECTDESCASSOCIATEDTOSUBPROJECT;
		}
		noOfrows = jdbcTemplate.queryForObject(
		sql, Integer.class,
		new Object[] {
			(Integer) subProjectId
		});
		LOGGER.info("method = isProjectDescriptionDetailsExistsForSubProject , isDataPresent :" + (noOfrows != 0));
		if (noOfrows != 0) {
			return true;
		}
		return false;
	}

	@Override
	public List < ProjDescDetail > getBaseDescriptions(String category) {
		List < Map < String, Object >> rows = jdbcTemplate.queryForList(FETCHBASEDESCRIPTIONS + " where category = '" + category + "'");
		List < ProjDescDetail > projDescDetails = new ArrayList < ProjDescDetail > ();
		for (Map < String, Object > row: rows) {
			projDescDetails.add(transformer.buildBaseDescDetail(row));
		}
		return projDescDetails;
	}

	@Override
	public void deleteBaseProjectDescription(String aliasBaseDescription) {
		int noOfRows = jdbcTemplate.update(DELETEBASEDESCRIPTION,
		new Object[] {
				aliasBaseDescription
		});
		LOGGER.info("No of base descriptions deleted =" + noOfRows);
	}

	@Override
	public void saveBaseDescription(ProjDescDetail projDescDetail) {
		LOGGER.info("method = saveBaseDescription , baseDescription alias :" + projDescDetail.getAliasDescription());
		if ("Y".equalsIgnoreCase(projDescDetail.getIsUpdate())) {
			jdbcTemplate.update(UPDATEBASEDESCRIPTION, new Object[] {
				projDescDetail.getWorkType(), projDescDetail.getMetric(),
				projDescDetail.getLastUpdatedBy(), projDescDetail.getLastUpdatedAt(),
				projDescDetail.getDescription(), projDescDetail.getAliasDescription()
			});
		} else {
			jdbcTemplate.update(INSERTBASEDESCRIPTION, new Object[] {
					Constants.PSK,
					projDescDetail.getWorkType(), projDescDetail.getMetric(),
					projDescDetail.getQuantity(), projDescDetail.getTotalCost(),
					projDescDetail.getLastUpdatedBy(), projDescDetail.getLastUpdatedAt(),
					projDescDetail.getDescription(), projDescDetail.getAliasDescription()
			});
			LOGGER.info("Inserting an entry with same details for government");
			jdbcTemplate.update(INSERTBASEDESCRIPTION, new Object[] {
					Constants.GOVERNMENT,
					projDescDetail.getWorkType(), projDescDetail.getMetric(),
					projDescDetail.getQuantity(), projDescDetail.getTotalCost(),
					projDescDetail.getLastUpdatedBy(), projDescDetail.getLastUpdatedAt(),
					projDescDetail.getDescription(), projDescDetail.getAliasDescription()
			});
		}
	}

	@Override
	public ProjDescDetail getBaseDescription(String descId) {
		ProjDescDetail baseDescDetail = new ProjDescDetail();
		List < Map < String, Object >> maps = jdbcTemplate.queryForList(GETBASEDESCRIPTION, new Object[] {
				descId
		});
		for (Map < String, Object > map: maps) {
			baseDescDetail = transformer.buildBaseDescDetail(map);
		}
		return baseDescDetail;
	}

	@Override
	public boolean isGlobalDescriptionAlreadyExisting(String baseDescription) {
		LOGGER.info("method = isGlobalDescriptionAlreadyExisting , baseDescription :" + baseDescription);
		int noOfrows = 0;
		List < Map < String, Object >> result = jdbcTemplate.queryForList(ISBASEDESCEXISTS,
		new Object[] {
			baseDescription
		});
		LOGGER.info("method = isGlobalDescriptionAlreadyExisting , isDataPresent :" + (noOfrows != 0));
		if (result.size() != 0) {
			return true;
		}
		return false;
	}

	@Override
	public ProjDescDetail getBaseDescDetail(String baseDescId) {
		String sql = null;
		LOGGER.info("base description value" + baseDescId);
		if (!StringUtils.isNullOrEmpty(baseDescId)) {
			sql = baseDescDetail + " WHERE BaseDescId = " + baseDescId;
		}

		ProjDescDetail projDescDetail = null;
		List < Map < String, Object >> rows = jdbcTemplate.queryForList(sql);

		for (Map < String, Object > row: rows) {
			projDescDetail = transformer.buildBaseDescDetail(row);
		}
		return projDescDetail;
	}

	public void deleteGovProjectDescriptionByProjectId(Integer projectId) {

		int noOfRows = jdbcTemplate.update("DELETE FROM govprojectdesc WHERE ProjId = ?",
		new Object[] {
			projectId
		});
		LOGGER.info("method = deleteProjectDescriptionByProjectId , Number of rows deleted : " + noOfRows + " projectId :" + projectId);
	}

	public void updateProjectDescriptions(final List<ProjDescDetail> projDescDetails,String descriptionType) {
		LOGGER.info("batch updating projectDescriptions with new priceperQuantity and totalCost");
		String UPDATE_PRICE_COST_DESCRIPTIONS =  "update "+DescriptionType.getDescriptionTableName(descriptionType)+" set PricePerQuantity = ? , TotalCost = ? where ProjDescId = ?";
		try{
			jdbcTemplate.batchUpdate(UPDATE_PRICE_COST_DESCRIPTIONS, new BatchPreparedStatementSetter() {
				@Override
				public void setValues(PreparedStatement ps, int i) throws SQLException {
					ProjDescDetail projDescDetail = projDescDetails.get(i);
					ps.setString(1, projDescDetail.getPricePerQuantity());
					ps.setString(2, projDescDetail.getTotalCost());
					ps.setInt(3, projDescDetail.getProjDescId());
				}

				@Override
				public int getBatchSize() {
					return projDescDetails.size();
				}
			});
		}catch (Exception e){
			LOGGER.error("Error got occurred"+e);
		}

	}

	@Override
	public List<ProjDescDetail> getProjectDescDetailList(Set<Integer> descItemIds, String descType) {
		String sqlQuery = "select * from " + DescriptionType.getDescriptionTableName(descType) + " where ProjDescId in (:ids)";
		MapSqlParameterSource parameters = new MapSqlParameterSource();
		parameters.addValue("ids", descItemIds);
		List<ProjDescDetail> projDescDetails = namedParameterJdbcTemplate.query(sqlQuery, parameters, ParameterizedBeanPropertyRowMapper.newInstance(ProjDescDetail.class));
		return projDescDetails;
	}
}