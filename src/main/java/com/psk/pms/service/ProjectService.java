package com.psk.pms.service;

import java.util.List;
import java.util.Map;

import com.psk.pms.model.ProjDescDetail;
import com.psk.pms.model.ProjectDetail;
import com.psk.pms.model.SubProjectDetail;

public interface ProjectService {
	
	public boolean createProject(ProjectDetail projectDetail);
	
	public boolean editProject(ProjectDetail projectDetail);
	
	public boolean createProjDesc(ProjDescDetail projDescDetail);
	
	public boolean editProjDesc(ProjDescDetail projDescDetail);
	
	public boolean createSubProject(SubProjectDetail subProjectDetail);
	
	public boolean editSubProject(SubProjectDetail subProjectDetail);
	
	public Map<String, String> getAliasProjectNames();
	
	public List<String> getSubAliasProjectNames(String aliasProjectName);
	
	public List<ProjectDetail> getProjectDocumentList();
	
	public ProjectDetail getProjectDocument(String projectId);
}
