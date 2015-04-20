package com.psk.pms.service;

import com.psk.pms.model.ProjectDetail;

public interface ProjectService {
	
	public boolean createProject(ProjectDetail projectDetail);
	
	public boolean editProject(ProjectDetail projectDetail);
}
