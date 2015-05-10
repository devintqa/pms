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
	
	public Map<String, String> getSubAliasProjectNames(String projectId);
	
	public List<ProjectDetail> getProjectDocumentList();
	
	public List<ProjectDetail> getEmdEndAlertList();
	
	public ProjectDetail getProjectDocument(String projectId);
	
	public SubProjectDetail getSubProjectDocument(String subProject);
	
	public ProjDescDetail getProjectDescDetail(String projDescId);

	public List<SubProjectDetail> getSubProjectDocumentList(Integer projectId);

	public List<ProjDescDetail> getProjectDescDetailList(Integer subProjId);
	
	public boolean isAliasProjectAlreadyExisting(String aliasName);
	
	public boolean isAliasSubProjectAlreadyExisting(String subAliasName);
	
	public boolean isAliasDescriptionAlreadyExisting(Integer projectId, Integer subProjId, String aliasDescription);
	
}
