package com.psk.pms.service;

import com.psk.pms.dao.DepositDetailDAO;
import com.psk.pms.dao.ItemDAO;
import com.psk.pms.dao.ProjectDAO;
import com.psk.pms.dao.ProjectDescriptionDAO;
import com.psk.pms.dao.SubProjectDAO;
import com.psk.pms.model.ProjectDetail;
import com.psk.pms.utils.DateFormatter;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Map;

public class ProjectServiceImpl implements ProjectService {

    private ProjectDAO projectDAO;

    @Autowired
    private SubProjectDAO subProjectDAO;

    @Autowired
    private ProjectDescriptionDAO projectDescriptionDAO;

    @Autowired
    private DepositDetailDAO depositDetailDAO;

    @Autowired
    private ItemDAO itemDAO;

    private static final Logger LOGGER = Logger
            .getLogger(ProjectServiceImpl.class);

    public boolean createEditProject(ProjectDetail projectDetail) {
        SimpleDateFormat formatter = new SimpleDateFormat("dd-MM-yyyy");
        projectDetail.setCompletionDateSqlForBonus(getSQLDate(
                projectDetail.getCompletionDateForBonus(), formatter));
        projectDetail.setTenderSqlDate(getSQLDate(
                projectDetail.getTenderDate(), formatter));
        projectDetail.setAgreementSqlDate(getSQLDate(
                projectDetail.getAgreementDate(), formatter));
        projectDetail.setCommencementSqlDate(getSQLDate(
                projectDetail.getCommencementDate(), formatter));
        projectDetail.setCompletionSqlDate(getSQLDate(
                projectDetail.getCompletionDate(), formatter));
        projectDetail.setLastUpdatedBy(projectDetail.getEmployeeId());
        projectDetail.setLastUpdatedAt(getCurrentDateTime());
        if (projectDetail.getLessPercentage() == "") {
            projectDetail.setLessPercentage(null);
        }
        if (projectDetail.getExPercentage() == "") {
            projectDetail.setExPercentage(null);
        }
        boolean isInsertSuccessful = projectDAO.saveProject(projectDetail);
        return isInsertSuccessful;
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

    public boolean isAliasProjectAlreadyExisting(String aliasName) {
        boolean isAvailable = false;
        isAvailable = projectDAO.isAliasProjectAlreadyExisting(aliasName);
        return isAvailable;
    }

    public Map<String, String> getAliasProjectNames() {
        Map<String, String> aliasProjects = projectDAO.getAliasProjectNames();
        return aliasProjects;
    }

    public void setProjectDAO(ProjectDAO projectDAO) {
        this.projectDAO = projectDAO;
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

    public List<ProjectDetail> getProjectDocumentList() {
        List<ProjectDetail> projectDocumentList = projectDAO
                .getProjectDocumentList();
        for (ProjectDetail projectDetail : projectDocumentList) {
            if (projectDetail.getCompletionSqlDate() != null) {
                projectDetail.setProjectStatus("Completed");
            } else if (projectDetail.getCommencementSqlDate() != null) {
                projectDetail.setProjectStatus("Active");
            } else if (projectDetail.getTenderSqlDate() != null) {
                projectDetail.setProjectStatus("Tendered");
            }
        }
        return projectDocumentList;
    }

    @Override
    public ProjectDetail getProjectDocument(String projectId) {
        SimpleDateFormat formatter = new SimpleDateFormat("dd-MM-yyyy");
        ProjectDetail projectDetail = projectDAO.getProjectDocument(projectId);
        projectDetail.setCompletionDateForBonus(DateFormatter.getStringDate(
                projectDetail.getCompletionDateSqlForBonus(), formatter));
        projectDetail.setTenderDate(DateFormatter.getStringDate(
                projectDetail.getTenderSqlDate(), formatter));
        projectDetail.setAgreementDate(DateFormatter.getStringDate(
                projectDetail.getAgreementSqlDate(), formatter));
        projectDetail.setCommencementDate(DateFormatter.getStringDate(
                projectDetail.getCommencementSqlDate(), formatter));
        projectDetail.setCompletionDate(DateFormatter.getStringDate(
                projectDetail.getCompletionSqlDate(), formatter));
        return projectDetail;
    }

    @Override
    public void deleteProject(Integer projectId) {
        itemDAO.deleteItemByProjectId(projectId);
        depositDetailDAO.deleteDepositDetailByProjectId(projectId);
        projectDescriptionDAO.deleteProjectDescriptionByProjectId(projectId);
        subProjectDAO.deleteSubProjectByProjectId(projectId);
        projectDAO.deleteProject(projectId);
    }


    @Override
    public List<String> getDropDownValuesFor(String type) {
        return projectDAO.getDropDownValues(type);
    }
}
