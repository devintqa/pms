package com.psk.pms.service;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import com.psk.pms.dao.ProjectDAO;
import com.psk.pms.model.ProjectDetail;

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
