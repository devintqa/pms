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
	
	public Map<String, String> getSubAliasProjectNames(String projectId);
	
	public ProjectDetail getProjectDocument(String projectId);
	
	public List<ProjectDetail> getProjectDocumentList();

	public List<SubProjectDetail> getSubProjectDocumentList(Integer projectId);

	public SubProjectDetail getSubProjectDocument(String subProjectId);

	public List<ProjDescDetail> projectDescDetailList(Integer subProjectId);
	
	public boolean isAliasProjectAlreadyExisting(String aliasName);
	
	public boolean isAliasSubProjectAlreadyExisting(String subAliasName);
	
	public boolean isAliasDescriptionAlreadyExisting(Integer projectId, Integer subProjId, String aliasDescription);

	public ProjDescDetail getProjectDescDetail(String projDescId);
	
	
}
