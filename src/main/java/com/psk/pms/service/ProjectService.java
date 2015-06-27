package com.psk.pms.service;

import java.util.List;
import java.util.Map;

import com.psk.pms.model.DescItemDetail;
import com.psk.pms.model.EMDDetail;
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
	
	public List<EMDDetail> getEmdEndAlertList();
	
	public ProjectDetail getProjectDocument(String projectId);
	
	public SubProjectDetail getSubProjectDocument(String subProject);
	
	public ProjDescDetail getProjectDescDetail(String projDescId, String subProject);

	public List<SubProjectDetail> getSubProjectDocumentList(Integer projectId);

	public List<ProjDescDetail> getSubProjectDescDetailList(Integer subProjId);
	
	public List<ProjDescDetail> getProjectDescDetailList(Integer projId,boolean searchUnderProject);
	
	public boolean isAliasProjectAlreadyExisting(String aliasName);
	
	public boolean isAliasSubProjectAlreadyExisting(String subAliasName, Integer projectId);
	
	public boolean isAliasDescriptionAlreadyExisting(ProjDescDetail projectDescDetail);

	public boolean isSerialNumberAlreadyExisting(ProjDescDetail projectDescDetail);

	public Map<String, String> getDescItemCodes(String itemCode);

	public void deleteProjectDescriptionDetail(String projectDescriptionId);
	
	public boolean insertDataDescription(List<DescItemDetail> dataDetailList, String employeeId);
	
}
