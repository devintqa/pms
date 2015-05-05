package com.psk.pms.service;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Map;

import com.psk.pms.dao.ProjectDAO;
import com.psk.pms.model.ProjDescDetail;
import com.psk.pms.model.ProjectDetail;
import com.psk.pms.model.SubProjectDetail;

public class ProjectServiceImpl implements ProjectService {
	
	private ProjectDAO projectDAO;

	public boolean editProject(ProjectDetail projectDetail){
		boolean isInsertSuccessful = false;
		projectDAO.notify();
		return isInsertSuccessful;
	}
	
	public boolean createProject(ProjectDetail projectDetail){
		SimpleDateFormat formatter = new SimpleDateFormat("dd-MM-yyyy");
		projectDetail.setTenderSqlDate(getSQLDate(projectDetail.getTenderDate(), formatter));
		projectDetail.setAgreementSqlDate(getSQLDate(projectDetail.getAgreementDate(), formatter));
		projectDetail.setCommencementSqlDate(getSQLDate(projectDetail.getCommencementDate(), formatter));
		projectDetail.setCompletionSqlDate(getSQLDate(projectDetail.getCompletionDate(), formatter));
		boolean isInsertSuccessful = projectDAO.saveProject(projectDetail);
		return isInsertSuccessful;
	}
	
	public boolean editProjDesc(ProjDescDetail projDescDetail){
		boolean isInsertSuccessful = false;
		projectDAO.notify();
		return isInsertSuccessful;
	}
	
	public boolean createProjDesc(ProjDescDetail projDescDetail){
		boolean isInsertSuccessful = projectDAO.saveProjDesc(projDescDetail);
		return isInsertSuccessful;
	}
	
	public boolean editSubProject(SubProjectDetail subProjectDetail){
		boolean isInsertSuccessful = false;
		projectDAO.notify();
		return isInsertSuccessful;
	}
	
	public boolean createSubProject(SubProjectDetail subProjectDetail){
		SimpleDateFormat formatter = new SimpleDateFormat("dd-MM-yyyy");
		subProjectDetail.setSubTenderSqlDate(getSQLDate(subProjectDetail.getSubTenderDate(), formatter));
		subProjectDetail.setSubAgreementSqlDate(getSQLDate(subProjectDetail.getSubAgreementDate(), formatter));
		subProjectDetail.setSubCommencementSqlDate(getSQLDate(subProjectDetail.getSubCommencementDate(), formatter));
		subProjectDetail.setSubCompletionSqlDate(getSQLDate(subProjectDetail.getSubCompletionDate(), formatter));
		boolean isInsertSuccessful = projectDAO.saveSubProject(subProjectDetail);
		return isInsertSuccessful;
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
		return projectDocumentList;
	}

	@Override
	public ProjectDetail getProjectDocument(String projectId) {
		ProjectDetail projectDetail = projectDAO.getProjectDocument(projectId);
		return projectDetail;
	}

}
