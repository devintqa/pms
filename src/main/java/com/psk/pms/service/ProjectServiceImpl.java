package com.psk.pms.service;

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
		boolean isInsertSuccessful = projectDAO.saveProject(projectDetail);
		return isInsertSuccessful;
	}

	public void setProjectDAO(ProjectDAO projectDAO) {
		this.projectDAO = projectDAO;
	}

}
