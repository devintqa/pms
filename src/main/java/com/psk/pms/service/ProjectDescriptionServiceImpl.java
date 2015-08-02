package com.psk.pms.service;

import com.psk.pms.dao.ProjectDescriptionDAO;
import com.psk.pms.model.ProjDescComparisonDetail;
import com.psk.pms.model.ProjDescDetail;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

/**
 * Created by prakashbhanu57 on 7/6/2015.
 */
public class ProjectDescriptionServiceImpl implements  ProjectDescriptionService{

    @Autowired
    ProjectDescriptionDAO projectDescriptionDAO;

    private static final Logger LOGGER = Logger.getLogger(ProjectDescriptionServiceImpl.class);

    public boolean createEditProjDesc(ProjDescDetail projDescDetail){
        fillEmptyProjDesValuesWithNull(projDescDetail);
        projDescDetail.setLastUpdatedBy(projDescDetail.getEmployeeId());
        projDescDetail.setLastUpdatedAt(getCurrentDateTime());
        boolean isInsertSuccessful = projectDescriptionDAO.saveProjDesc(projDescDetail);
        return isInsertSuccessful;
    }

    private Date getCurrentDateTime() {
        Calendar calendar = Calendar.getInstance();
        calendar.getTimeInMillis();
        Date date = new Date();
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String formattedDate = simpleDateFormat.format(date);
        return getSQLDate(formattedDate,simpleDateFormat);
    }


    public boolean isAliasDescriptionAlreadyExisting(ProjDescDetail projectDescDetail) {
        boolean isAvailable = false;
        isAvailable = projectDescriptionDAO.isAliasDescriptionAlreadyExisting(projectDescDetail);
        return isAvailable;
    }

    public boolean isSerialNumberAlreadyExisting(ProjDescDetail projectDescDetail) {
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
    public ProjDescDetail getProjectDescDetail(String projDescId, String subProject) {
        ProjDescDetail projDescDetail = projectDescriptionDAO.getProjectDescDetail(projDescId, subProject);
        projDescDetail.setIsUpdate("Y");
        return projDescDetail;
    }



    @Override
    public List<ProjDescDetail> getSubProjectDescDetailList(Integer subProjectId) {
        List<ProjDescDetail> projectDescDetailList = projectDescriptionDAO.getSubProjectDescDetailList(subProjectId);
        return projectDescDetailList;
    }

    @Override
    public List<ProjDescDetail> getProjectDescDetailList(Integer projId,boolean searchUnderProject) {
        List<ProjDescDetail> projectDescDetailList = projectDescriptionDAO.getProjectDescDetailList(projId, searchUnderProject);
        return projectDescDetailList;
    }
    
    @Override
    public List<ProjDescComparisonDetail> getProjectDescComparisonDetail(Integer projId) {
    	 List<ProjDescComparisonDetail> projDescComparisonDetail = projectDescriptionDAO.getProjectDescComparisonDetail(projId);
         return projDescComparisonDetail;
    }


    @Override
    public void deleteProjectDescriptionDetail(String projectDescriptionId) {
        projectDescriptionDAO.deleteProjectDescription(projectDescriptionId);
    }

    private Date getSQLDate(String dateToBeFormatted, SimpleDateFormat formatter){
        Date date = null;
        try {
            if(null != dateToBeFormatted) {
                date = (Date) formatter.parse(dateToBeFormatted);
            }
        } catch (ParseException e) {
            LOGGER.error("Error in parsing the date");
        }
        return date;
    }

    public void saveProjectDescriptionDetails(List<ProjDescDetail> projDescDetails){
        projectDescriptionDAO.saveProjectDescriptionDetails(projDescDetails);
    }
    

    public void saveProposalProjectDescriptionDetails(List<ProjDescDetail> projDescDetails){
        projectDescriptionDAO.saveProposalProjectDescriptionDetails(projDescDetails);
    }

    
    public void saveSubProjectDescriptionDetails(List<ProjDescDetail> projDescDetails){
        projectDescriptionDAO.saveSubProjectDescriptionDetails(projDescDetails);
    }

    @Override
    public boolean isProjectDescriptionDetailsExistsForProject(int projectId) {
        return projectDescriptionDAO.isProjectDescriptionDetailsExistsForProject(projectId);
    }

    @Override
    public boolean isProjectDescriptionDetailsExistsForSubProject(int subProjectId) {
        return projectDescriptionDAO.isProjectDescriptionDetailsExistsForSubProject(subProjectId);
    }

    @Override
    public void deleteAllTheDescriptionDetailsOfProject(int projectId) {
        LOGGER.info("Deleting all the project description items for project Id: " +projectId);
        projectDescriptionDAO.deleteProjectDescriptionByProjectId(projectId);
    }

    @Override
    public void deleteAllTheDescriptionDetailsOfSubProject(int subProjectId) {
        LOGGER.info("Deleting all the project description items for Sub project Id: " +subProjectId);
        projectDescriptionDAO.deleteProjectDescriptionBySubProjectId(subProjectId);
    }
}
