package com.psk.pms.service;

import com.psk.pms.dao.ProjectDescriptionDAO;
import com.psk.pms.model.ProjDescComparisonDetail;
import com.psk.pms.model.ProjDescDetail;
import com.psk.pms.model.SearchDetail;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * Created by prakashbhanu57 on 7/6/2015.
 */
public class ProjectDescriptionServiceImpl implements ProjectDescriptionService {

	@Autowired
	ProjectDescriptionDAO projectDescriptionDAO;

	@Autowired
	ItemService itemService;

	private static final Logger LOGGER = Logger.getLogger(ProjectDescriptionServiceImpl.class);

	public void createEditProjDesc(ProjDescDetail projDescDetail) {
		fillEmptyProjDesValuesWithNull(projDescDetail);
		projDescDetail.setLastUpdatedBy(projDescDetail.getEmployeeId());
		projDescDetail.setLastUpdatedAt(getCurrentDateTime());
		if("Y".equalsIgnoreCase(projDescDetail.getIsUpdate())){
			recalculateProjectDescriptionTotalPrice(projDescDetail);
			projectDescriptionDAO.updateProjectDescriptionDetail(projDescDetail);
		}else{
			projectDescriptionDAO.saveProjDesc(projDescDetail);
		}
	}

	private void recalculateProjectDescriptionTotalPrice(ProjDescDetail projDescDetail) {
		LOGGER.debug("Recalculating project description total .."+projDescDetail.getAliasDescription());
		BigDecimal newQuantity = new BigDecimal(projDescDetail.getQuantity()).setScale(2, RoundingMode.CEILING);
		BigDecimal pricePerQuantity = new BigDecimal(projDescDetail.getPricePerQuantity());
		BigDecimal newTotalCost = newQuantity.multiply(pricePerQuantity).setScale(2, RoundingMode.CEILING);
		projDescDetail.setTotalCost(newTotalCost.toString());
	}

	private Date getCurrentDateTime() {
		Calendar calendar = Calendar.getInstance();
		calendar.getTimeInMillis();
		Date date = new Date();
		SimpleDateFormat simpleDateFormat = new SimpleDateFormat(
			"yyyy-MM-dd HH:mm:ss");
		String formattedDate = simpleDateFormat.format(date);
		return getSQLDate(formattedDate, simpleDateFormat);
	}

	public boolean isAliasDescriptionAlreadyExisting(ProjDescDetail projectDescDetail) {
		boolean isAvailable = false;
		isAvailable = projectDescriptionDAO.isAliasDescriptionAlreadyExisting(projectDescDetail);
		return isAvailable;
	}

	public boolean isSerialNumberAlreadyExisting(
	ProjDescDetail projectDescDetail) {
		boolean isAvailable = false;
		isAvailable = projectDescriptionDAO.isSerialNumberAlreadyExisting(projectDescDetail);
		return isAvailable;
	}

	private void fillEmptyProjDesValuesWithNull(ProjDescDetail projDescDetail) {
		if (!projDescDetail.isSubProjectDesc()) {
			projDescDetail.setAliasSubProjectName(null);
		}
		if (StringUtils.isEmpty(projDescDetail.getQuantity())) {
			projDescDetail.setQuantity(null);
		}
		if (StringUtils.isEmpty(projDescDetail.getTotalCost())) {
			projDescDetail.setTotalCost(null);
		}
	}

	@Override
	public ProjDescDetail getProjectDescDetail(String projDescId, String subProject, String descType) {
		ProjDescDetail projDescDetail = projectDescriptionDAO.getProjectDescDetail(projDescId, subProject, descType);
        projDescDetail.setDescType(descType);
		projDescDetail.setIsUpdate("Y");
		return projDescDetail;
	}


	@Override
	public List < ProjDescDetail > getSubProjectDescDetailList(Integer subProjectId) {
		List < ProjDescDetail > projectDescDetailList = projectDescriptionDAO.getSubProjectDescDetailList(subProjectId);
		return projectDescDetailList;
	}

	@Override
	public List < ProjDescDetail > getProjectDescDetailList(Integer projId, boolean searchUnderProject) {
		List < ProjDescDetail > projectDescDetailList = projectDescriptionDAO.getProjectDescDetailList(projId, searchUnderProject);
		return projectDescDetailList;
	}

	@Override
	public List<ProjDescDetail> getProjectDescDetailList(SearchDetail searchDetail){
		List<ProjDescDetail> projectDescDetailList = projectDescriptionDAO.getProjectDescDetailList(searchDetail);
        for(ProjDescDetail projDescDetail : projectDescDetailList){
            projDescDetail.setDescType(searchDetail.getSearchOn());
        }
		return projectDescDetailList;
	}

	@Override
	public List < ProjDescComparisonDetail > getProjectDescComparisonDetail(
	Integer projId) {
		List < ProjDescComparisonDetail > projDescComparisonDetail = projectDescriptionDAO.getProjectDescComparisonDetail(projId);
		return projDescComparisonDetail;
	}

	@Override
	public void deleteProjectDescriptionDetail(String projectDescriptionId,String descType) {
		projectDescriptionDAO.deleteProjectDescription(projectDescriptionId,descType);
	}

	private Date getSQLDate(String dateToBeFormatted, SimpleDateFormat formatter) {
		Date date = null;
		try {
			if (null != dateToBeFormatted) {
				date = (Date) formatter.parse(dateToBeFormatted);
			}
		} catch (ParseException e) {
			LOGGER.error("Error in parsing the date");
		}
		return date;
	}

	public void saveProjectDescriptionDetails(List < ProjDescDetail > projDescDetails) {
		projectDescriptionDAO.saveProjectDescriptionDetails(projDescDetails);
	}

	public void saveGovProjectDescriptionDetails(List < ProjDescDetail > projDescDetails) {
		projectDescriptionDAO.saveGovProjectDescriptionDetails(projDescDetails);
	}

	public void saveSubProjectDescriptionDetails(
	List < ProjDescDetail > projDescDetails) {
		projectDescriptionDAO.saveSubProjectDescriptionDetails(projDescDetails);
	}

	@Override
	public boolean isProjectDescriptionDetailsExistsForProject(int projectId, String governmentEst) {
		return projectDescriptionDAO.isProjectDescriptionDetailsExistsForProject(projectId, governmentEst);
	}

	@Override
	public boolean isProjectDescriptionDetailsExistsForSubProject(
	int subProjectId, String governmentEst) {
		return projectDescriptionDAO.isProjectDescriptionDetailsExistsForSubProject(subProjectId, governmentEst);
	}

	@Override
	public void deleteAllTheDescriptionDetailsOfProject(String descType, int projectId) {
		LOGGER.info("Deleting all the project description items for project Id: " + projectId);
		projectDescriptionDAO.deleteProjectDescriptionByProjectId(descType, projectId);
	}

	@Override
	public void deleteAllTheDescriptionDetailsOfSubProject(int subProjectId) {
		LOGGER.info("Deleting all the project description items for Sub project Id: " + subProjectId);
		projectDescriptionDAO.deleteProjectDescriptionBySubProjectId(subProjectId);
	}

	@Override
	public void saveBaseProjectDescription(ProjDescDetail projDescDetail) {
		LOGGER.info("Saving base description : baseDescription " + projDescDetail.getAliasProjectName());
		projDescDetail.setLastUpdatedBy(projDescDetail.getEmployeeId());
		projDescDetail.setLastUpdatedAt(getCurrentDateTime());
		projectDescriptionDAO.saveBaseDescription(projDescDetail);
	}

	@Override
	public List < ProjDescDetail > getBaseDescriptions(String category) {
		LOGGER.info("method = getBaseDescriptions()");
		return projectDescriptionDAO.getBaseDescriptions(category);
	}

	@Override
	public void deleteBaseProjectDescription(String aliasBaseDescription) {
		LOGGER.info("method = deleteBaseProjectDescription()");
		projectDescriptionDAO.deleteBaseProjectDescription(aliasBaseDescription);
	}

	@Override
	public boolean isGlobalDescriptionAlreadyExisting(String baseDescription) {
		return projectDescriptionDAO.isGlobalDescriptionAlreadyExisting(baseDescription);
	}

	@Override
	public ProjDescDetail getBaseDescDetail(String baseDescId) {
		ProjDescDetail projDescDetail = projectDescriptionDAO.getBaseDescDetail(baseDescId);
		return projDescDetail;
	}

	@Override
	public ProjDescDetail getBaseDescription(String descId) {
		LOGGER.info("method = getBaseProjectDescription()");
		return projectDescriptionDAO.getBaseDescription(descId);
	}

	@Override
    public void recalculateProjectPrices(Integer projectId, String descriptionType) {
        LOGGER.info("Recalculating project prices for projectId:" + projectId);
        SearchDetail searchDetail = new SearchDetail();
        searchDetail.setSearchUnder("project");
        searchDetail.setProjId(projectId);
        searchDetail.setSearchOn(descriptionType);
        Map<Integer, BigDecimal> projectDescIdPricePerQuantityMap = itemService.getTotalCostOfItemsProjectDescIdForProject(projectId, descriptionType);
        List<ProjDescDetail> projDescDetails = projectDescriptionDAO.getProjectDescDetailList(searchDetail);
        applyRecalculatedPriceForProjectDescriptions(projectDescIdPricePerQuantityMap, projDescDetails);
        projectDescriptionDAO.updateProjectDescriptions(projDescDetails, descriptionType);
        LOGGER.info("Recalculating project prices for projectId:" + projectId + " Complete.");
    }

    private void applyRecalculatedPriceForProjectDescriptions(Map<Integer, BigDecimal> projectDescIdPricePerQuantityMap, List<ProjDescDetail> itemDetailDtos) {
        LOGGER.info("Applying recalculated Prices for each project descriptions.");
        BigDecimal itemQunatity;
        BigDecimal totalCost;
        BigDecimal pricePerQuantity;
        Iterator<ProjDescDetail> itemDetailsIterator = itemDetailDtos.iterator();
        while (itemDetailsIterator.hasNext()) {
            ProjDescDetail projDescDetail = (ProjDescDetail) itemDetailsIterator.next();
            if (projectDescIdPricePerQuantityMap.containsKey(projDescDetail.getProjDescId())) {
                itemQunatity = new BigDecimal(projDescDetail.getQuantity());
                pricePerQuantity = projectDescIdPricePerQuantityMap.get(projDescDetail.getProjDescId());
                projDescDetail.setPricePerQuantity(pricePerQuantity.toString());
                totalCost = itemQunatity.multiply(pricePerQuantity);
                projDescDetail.setTotalCost(totalCost.toString());
            } else {
                itemDetailsIterator.remove();
            }
        }
    }
}