package com.psk.pms.service;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

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
		//boolean isInsertSuccessful = projDescDAO.saveProjDesc(projDescDetail);
		return true;
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
		//boolean isInsertSuccessful = subProjectDAO.saveSubProject(subProjectDetail);
		return true;
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

}
