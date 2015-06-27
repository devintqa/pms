package com.psk.pms.dao;

import java.util.List;
import java.util.Map;

import com.psk.pms.model.DescItemDetail;
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

	public List<ProjDescDetail> getSubProjectDescDetailList(Integer subProjectId);
	
	public List<ProjDescDetail> getProjectDescDetailList(Integer projectId,boolean searchUnderProject);
	
	public boolean isAliasProjectAlreadyExisting(String aliasName);
	
	public boolean isAliasSubProjectAlreadyExisting(String subAliasName, Integer projectId);
	
	public boolean isAliasDescriptionAlreadyExisting(ProjDescDetail projectDescDetail);

	public boolean isSerialNumberAlreadyExisting(ProjDescDetail projectDescDetail);

	public ProjDescDetail getProjectDescDetail(String projDescId, String subProject);

	public Map<String, String> getDescItemCodes(String itemCode);
	
	public void deleteProjectDescription(String projectDescriptionId);
	
	public boolean insertDataDescription(final List<DescItemDetail> dataDetails);
	
}
