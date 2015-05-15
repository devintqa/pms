package com.psk.pms.service;

import java.util.List;
import java.util.Map;

import com.psk.pms.model.ProjDescDetail;
import com.psk.pms.model.ProjectDetail;
import com.psk.pms.model.SubProjectDetail;

public interface ProjectService {
	
	public boolean createEditProject(ProjectDetail projectDetail);
	
	public boolean createEditProjDesc(ProjDescDetail projDescDetail);
	
	public boolean createEditSubProject(SubProjectDetail subProjectDetail);
	
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
	
	public boolean isAliasSubProjectAlreadyExisting(String subAliasName, Integer projectId);
	
	public boolean isAliasDescriptionAlreadyExisting(String projectId, String subProjId, String aliasDescription);
	
}
