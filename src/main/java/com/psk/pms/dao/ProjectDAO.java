package com.psk.pms.dao;

import java.util.List;
import java.util.Map;

import com.psk.pms.model.EMDDetail;
import com.psk.pms.model.ProjDescDetail;
import com.psk.pms.model.ProjectDetail;
import com.psk.pms.model.SubProjectDetail;

public interface ProjectDAO {
	
	public boolean saveProject(ProjectDetail projectDetail);
	
	public boolean saveSubProject(SubProjectDetail subProjectDetail);
	
	public boolean saveProjDesc(ProjDescDetail projDescDetail);
	
	public Map<String, String> getAliasProjectNames();
	
	public Map<String, String> getSubAliasProjectNames(String projectId);
	
	public ProjectDetail getProjectDocument(String projectId);
	
	public List<ProjectDetail> getProjectDocumentList();
	
	public List<EMDDetail> getEMDDatesList();

	public List<SubProjectDetail> getSubProjectDocumentList(Integer projectId);

	public SubProjectDetail getSubProjectDocument(String subProjectId);

	public List<ProjDescDetail> projectDescDetailList(Integer subProjectId);
	
	public boolean isAliasProjectAlreadyExisting(String aliasName);
	
	public boolean isAliasSubProjectAlreadyExisting(String subAliasName, Integer projectId);
	
	public boolean isAliasDescriptionAlreadyExisting(ProjDescDetail projectDescDetail);

	public ProjDescDetail getProjectDescDetail(String projDescId);
	
	
}
