package com.psk.pms.dao;

import com.psk.pms.model.ProjectDetail;

public interface ProjectDAO {
	
	public boolean saveProject(ProjectDetail projectDetail);
	
	public boolean editProject(ProjectDetail projectDetail);
	
}
