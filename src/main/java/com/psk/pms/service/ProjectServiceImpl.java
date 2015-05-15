package com.psk.pms.service;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

import com.psk.pms.dao.ProjectDAO;
import com.psk.pms.model.ProjDescDetail;
import com.psk.pms.model.ProjectDetail;
import com.psk.pms.model.SubProjectDetail;

public class ProjectServiceImpl implements ProjectService {
	
	private ProjectDAO projectDAO;
	
	public boolean createEditProject(ProjectDetail projectDetail){
		SimpleDateFormat formatter = new SimpleDateFormat("dd-MM-yyyy");
		projectDetail.setTenderSqlDate(getSQLDate(projectDetail.getTenderDate(), formatter));
		projectDetail.setAgreementSqlDate(getSQLDate(projectDetail.getAgreementDate(), formatter));
		projectDetail.setCommencementSqlDate(getSQLDate(projectDetail.getCommencementDate(), formatter));
		projectDetail.setCompletionSqlDate(getSQLDate(projectDetail.getCompletionDate(), formatter));
		projectDetail.setEmdStartSqlDate(getSQLDate(projectDetail.getEmdStartDate(), formatter));
		projectDetail.setEmdEndSqlDate(getSQLDate(projectDetail.getEmdEndDate(), formatter));
		boolean isInsertSuccessful = projectDAO.saveProject(projectDetail);
		return isInsertSuccessful;
	}
	
	public boolean createEditProjDesc(ProjDescDetail projDescDetail){
		boolean isInsertSuccessful = projectDAO.saveProjDesc(projDescDetail);
		return isInsertSuccessful;
	}
	
	public boolean createEditSubProject(SubProjectDetail subProjectDetail){
		SimpleDateFormat formatter = new SimpleDateFormat("dd-MM-yyyy");
		subProjectDetail.setSubTenderSqlDate(getSQLDate(subProjectDetail.getSubTenderDate(), formatter));
		subProjectDetail.setSubAgreementSqlDate(getSQLDate(subProjectDetail.getSubAgreementDate(), formatter));
		subProjectDetail.setSubCommencementSqlDate(getSQLDate(subProjectDetail.getSubCommencementDate(), formatter));
		subProjectDetail.setSubCompletionSqlDate(getSQLDate(subProjectDetail.getSubCompletionDate(), formatter));
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
	
	public boolean isAliasDescriptionAlreadyExisting(String projectId, String subProjId, String aliasDescription) {
		boolean isAvailable = false;
		isAvailable = projectDAO.isAliasDescriptionAlreadyExisting(projectId, subProjId, aliasDescription);
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
			date = (Date) formatter.parse(dateToBeFormatted);
		} catch (ParseException e) {
			System.out.println("Error in parsing the date");
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
	
	public List<ProjectDetail> getEmdEndAlertList() {
		Date todayDate = new Date();
		SimpleDateFormat formatter = new SimpleDateFormat("dd-MM-yyyy");
		List<ProjectDetail> projectDocumentList = projectDAO.getProjectDocumentList();
		List<ProjectDetail> emdDocumentList = new ArrayList<ProjectDetail>();
		for(ProjectDetail projectDetail : projectDocumentList){
			long diff = projectDetail.getEmdEndSqlDate().getTime() - todayDate.getTime();
			long diffDays = diff / (24 * 60 * 60 * 1000);
			if(diffDays < 14){
				projectDetail.setEmdStartDate(getStringDate(projectDetail.getEmdStartSqlDate(), formatter));
				projectDetail.setEmdEndDate(getStringDate(projectDetail.getEmdEndSqlDate(), formatter));
				emdDocumentList.add(projectDetail);
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
		projectDetail.setEmdStartDate(getStringDate(projectDetail.getEmdStartSqlDate(), formatter));
		projectDetail.setEmdEndDate(getStringDate(projectDetail.getEmdEndSqlDate(), formatter));
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
	public ProjDescDetail getProjectDescDetail(String projDescId) {
		ProjDescDetail projDescDetail = projectDAO.getProjectDescDetail(projDescId);
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
	public List<ProjDescDetail> getProjectDescDetailList(Integer subProjectId) {
		List<ProjDescDetail> projectDescDetailList = projectDAO.projectDescDetailList(subProjectId);
		return projectDescDetailList;
	}

	

}
