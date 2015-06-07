package com.psk.pms.service;

import com.psk.pms.dao.ProjectDAO;
import com.psk.pms.model.EMDDetail;
import com.psk.pms.model.ProjDescDetail;
import com.psk.pms.model.ProjectDetail;
import com.psk.pms.model.SubProjectDetail;
import org.apache.log4j.Logger;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

public class ProjectServiceImpl implements ProjectService {
	
	private ProjectDAO projectDAO;

	private static final Logger LOGGER = Logger.getLogger(ProjectService.class);

	public boolean createEditProject(ProjectDetail projectDetail){
		SimpleDateFormat formatter = new SimpleDateFormat("dd-MM-yyyy");
		projectDetail.setTenderSqlDate(getSQLDate(projectDetail.getTenderDate(), formatter));
		projectDetail.setAgreementSqlDate(getSQLDate(projectDetail.getAgreementDate(), formatter));
		projectDetail.setCommencementSqlDate(getSQLDate(projectDetail.getCommencementDate(), formatter));
		projectDetail.setCompletionSqlDate(getSQLDate(projectDetail.getCompletionDate(), formatter));
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
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String formattedDate = simpleDateFormat.format(date);
        return getSQLDate(formattedDate,simpleDateFormat);
    }

    public boolean createEditProjDesc(ProjDescDetail projDescDetail){
    	if(!projDescDetail.isSubProjectDesc())
        {
    		projDescDetail.setAliasSubProjectName(null);
        }
        projDescDetail.setLastUpdatedBy(projDescDetail.getEmployeeId());
        projDescDetail.setLastUpdatedAt(getCurrentDateTime());
		boolean isInsertSuccessful = projectDAO.saveProjDesc(projDescDetail);
		return isInsertSuccessful;
	}
	
	public boolean createEditSubProject(SubProjectDetail subProjectDetail){
		SimpleDateFormat formatter = new SimpleDateFormat("dd-MM-yyyy");
		subProjectDetail.setSubTenderSqlDate(getSQLDate(subProjectDetail.getSubTenderDate(), formatter));
		subProjectDetail.setSubAgreementSqlDate(getSQLDate(subProjectDetail.getSubAgreementDate(), formatter));
		subProjectDetail.setSubCommencementSqlDate(getSQLDate(subProjectDetail.getSubCommencementDate(), formatter));
		subProjectDetail.setSubCompletionSqlDate(getSQLDate(subProjectDetail.getSubCompletionDate(), formatter));
        subProjectDetail.setLastUpdatedBy(subProjectDetail.getEmployeeId());
        subProjectDetail.setLastUpdatedAt(getCurrentDateTime());
		if (subProjectDetail.getSubLessPercentage() == "") {
			subProjectDetail.setSubLessPercentage(null);
		}
		if (subProjectDetail.getSubExPercentage() == "") {
			subProjectDetail.setSubExPercentage(null);
		}
		boolean isInsertSuccessful = projectDAO.saveSubProject(subProjectDetail);
		return isInsertSuccessful;
	}
	
	public boolean isAliasProjectAlreadyExisting(String aliasName){
		boolean isAvailable = false;
		isAvailable = projectDAO.isAliasProjectAlreadyExisting(aliasName);
		return isAvailable;
	}
	
	public boolean isAliasSubProjectAlreadyExisting(String subAliasName, Integer projectId) {
		boolean isAvailable = false;
		isAvailable = projectDAO.isAliasSubProjectAlreadyExisting(subAliasName, projectId);
		return isAvailable;
	}
	
	public boolean isAliasDescriptionAlreadyExisting(ProjDescDetail projectDescDetail) {
		boolean isAvailable = false;
		isAvailable = projectDAO.isAliasDescriptionAlreadyExisting(projectDescDetail);
		return isAvailable;
	}
	
	public Map<String, String> getAliasProjectNames(){
		Map<String, String> aliasProjects = projectDAO.getAliasProjectNames();
		return aliasProjects;
	}
	
	public Map<String, String> getSubAliasProjectNames(String projectId) {
		Map<String, String> aliasSubProjectList = projectDAO.getSubAliasProjectNames(projectId);
		return aliasSubProjectList;
	}

	public void setProjectDAO(ProjectDAO projectDAO) {
		this.projectDAO = projectDAO;
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

	public List<ProjectDetail> getProjectDocumentList() {
		List<ProjectDetail> projectDocumentList = projectDAO.getProjectDocumentList();
		for(ProjectDetail projectDetail : projectDocumentList){
			if(projectDetail.getCompletionSqlDate() != null){
				projectDetail.setProjectStatus("Completed");
			} else if(projectDetail.getCommencementSqlDate() != null){
				projectDetail.setProjectStatus("Active");
			} else if(projectDetail.getTenderSqlDate() != null){
				projectDetail.setProjectStatus("Tendered");
			}
		}
		return projectDocumentList;
	}
	
	public List<EMDDetail> getEmdEndAlertList() {
		Date todayDate = new Date();
		SimpleDateFormat formatter = new SimpleDateFormat("dd-MM-yyyy");
		List<EMDDetail> projectDocumentList = projectDAO.getEMDDatesList();
		List<EMDDetail> emdDocumentList = new ArrayList<EMDDetail>();
		for(EMDDetail emdDetail : projectDocumentList){
			long diff = emdDetail.getSqlEmdEndDate().getTime() - todayDate.getTime();
			long diffDays = diff / (24 * 60 * 60 * 1000);
			if(diffDays < 14){
				emdDetail.setEmdStartDate(getStringDate(emdDetail.getSqlEmdStartDate(), formatter));
				emdDetail.setEmdEndDate(getStringDate(emdDetail.getSqlEmdEndDate(), formatter));
				emdDetail.setEmdExtensionDate(getStringDate(emdDetail.getEmdExtensionSqlDate(), formatter));
				emdDocumentList.add(emdDetail);
			}
		}
		return emdDocumentList;
	}

	@Override
	public ProjectDetail getProjectDocument(String projectId) {
		SimpleDateFormat formatter = new SimpleDateFormat("dd-MM-yyyy");
		ProjectDetail projectDetail = projectDAO.getProjectDocument(projectId);
		projectDetail.setTenderDate(getStringDate(projectDetail.getTenderSqlDate(), formatter));
		projectDetail.setAgreementDate(getStringDate(projectDetail.getAgreementSqlDate(), formatter));
		projectDetail.setCommencementDate(getStringDate(projectDetail.getCommencementSqlDate(), formatter));
		projectDetail.setCompletionDate(getStringDate(projectDetail.getCompletionSqlDate(), formatter));
		return projectDetail;
	}
	
	@Override
	public SubProjectDetail getSubProjectDocument(String subProjectId) {
		SimpleDateFormat formatter = new SimpleDateFormat("dd-MM-yyyy");
		SubProjectDetail subProjectDetail = projectDAO.getSubProjectDocument(subProjectId);
		subProjectDetail.setSubTenderDate(getStringDate(subProjectDetail.getSubTenderSqlDate(), formatter));
		subProjectDetail.setSubAgreementDate(getStringDate(subProjectDetail.getSubAgreementSqlDate(), formatter));
		subProjectDetail.setSubCommencementDate(getStringDate(subProjectDetail.getSubCommencementSqlDate(), formatter));
		subProjectDetail.setSubCompletionDate(getStringDate(subProjectDetail.getSubCompletionSqlDate(), formatter));
		return subProjectDetail;
	}
	
	@Override
	public ProjDescDetail getProjectDescDetail(String projDescId, String subProject) {
		ProjDescDetail projDescDetail = projectDAO.getProjectDescDetail(projDescId, subProject);
		projDescDetail.setIsUpdate("Y");
		return projDescDetail;
	}
	
	private String getStringDate(Date dateToBeFormatted, SimpleDateFormat formatter){
		String date = null;
		if(dateToBeFormatted != null){
			date = formatter.format(dateToBeFormatted);
			return date;
		}		
		return date;
	}

	@Override
	public List<SubProjectDetail> getSubProjectDocumentList(Integer projectId) {
		List<SubProjectDetail> subProjectDocumentList = projectDAO.getSubProjectDocumentList(projectId);
		return subProjectDocumentList;
	}
	
	@Override
	public List<ProjDescDetail> getSubProjectDescDetailList(Integer subProjectId) {
		List<ProjDescDetail> projectDescDetailList = projectDAO.getSubProjectDescDetailList(subProjectId);
		return projectDescDetailList;
	}
	
	@Override
	public List<ProjDescDetail> getProjectDescDetailList(Integer projId) {
		List<ProjDescDetail> projectDescDetailList = projectDAO.getProjectDescDetailList(projId);
		return projectDescDetailList;
	}

	

}
