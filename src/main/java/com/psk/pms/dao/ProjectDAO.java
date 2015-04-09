package com.psk.pms.dao;

import java.util.List;

import com.psk.pms.model.ProjectDetail;

public interface ProjectDAO {
	
	public boolean saveProject(List<ProjectDetail> projectDetails);
	
	public boolean editProject(List<ProjectDetail> projectDetails);
	
}
