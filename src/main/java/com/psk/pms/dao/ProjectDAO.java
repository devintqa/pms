package com.psk.pms.dao;

import java.util.List;
import java.util.Map;

import com.psk.pms.model.ProjDescDetail;
import com.psk.pms.model.ProjectDetail;
import com.psk.pms.model.SubProjectDetail;

public interface ProjectDAO {
	
	public boolean saveProject(ProjectDetail projectDetail);
	
	public boolean editProject(ProjectDetail projectDetail);
	
	public boolean saveSubProject(SubProjectDetail subProjectDetail);
	
	public boolean editSubProject(SubProjectDetail subProjectDetail);
	
	public boolean saveProjDesc(ProjDescDetail projDescDetail);
	
	public boolean editProjDesc(ProjDescDetail projDescDetail);
	
	public Map<String, String> getAliasProjectNames();
	
	public List<String> getSubAliasProjectNames(String aliasProjectName);
	
	public ProjectDetail getProjectDocument(String projectId);
	
	public List<ProjectDetail> getProjectDocumentList();
	
	
}
