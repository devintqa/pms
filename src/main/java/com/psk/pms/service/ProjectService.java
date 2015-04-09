package com.psk.pms.service;

import com.psk.pms.model.ProjectDetail;

public interface ProjectService {
	
	public boolean createProject(ProjectDetail cabDetail);
	
	public boolean editProject(ProjectDetail cabDetail);
}
