package com.psk.pms.service;

import com.psk.pms.model.*;

import java.util.List;
import java.util.Map;
import java.util.Set;

public interface ProjectService {
	
	public boolean createEditProject(ProjectDetail projectDetail);
	
	public boolean createEditItem(Item item);
	
	public boolean createEditProjDesc(ProjDescDetail projDescDetail);
	
	public boolean createEditSubProject(SubProjectDetail subProjectDetail);
	
	public Map<String, String> getAliasProjectNames();
	
	public Map<String, String> getSubAliasProjectNames(String projectId);
	
	public List<ProjectDetail> getProjectDocumentList();
	
	public List<EMDDetail> getEmdEndAlertList();
	
	public ProjectDetail getProjectDocument(String projectId);
	
	public SubProjectDetail getSubProjectDocument(String subProject);
	
	public ProjDescDetail getProjectDescDetail(String projDescId, String subProject);

	public List<SubProjectDetail> getSubProjectDocumentList(Integer projectId);

	public List<ProjDescDetail> getSubProjectDescDetailList(Integer subProjId);
	
	public List<ProjDescDetail> getProjectDescDetailList(Integer projId,boolean searchUnderProject);
	
	public boolean isAliasProjectAlreadyExisting(String aliasName);
	
	public boolean isItemAlreadyExisting(String itemName);
	
	public boolean isAliasSubProjectAlreadyExisting(String subAliasName, Integer projectId);
	
	public boolean isAliasDescriptionAlreadyExisting(ProjDescDetail projectDescDetail);

	public boolean isSerialNumberAlreadyExisting(ProjDescDetail projectDescDetail);

	public Map<String, String> getDescItemCodes(String itemCode);

	public void deleteProjectDescriptionDetail(String projectDescriptionId);
	
	void deleteProject(Integer projectId);

	void deleteSubProject(Integer subProjectId);

	public boolean insertDataDescription(DescItemDetail descItemDetail);
	
	public DescItemDetail getDataDescription(final DescItemDetail descItemDetail);
	
	public Set<String> fetchItemNames();
	
}
