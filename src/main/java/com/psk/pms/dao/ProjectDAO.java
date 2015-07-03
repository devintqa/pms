package com.psk.pms.dao;

import com.psk.pms.model.*;
import com.psk.pms.model.DescItemDetail.ItemDetail;

import java.util.List;
import java.util.Map;
import java.util.Set;

public interface ProjectDAO {
	
	public boolean saveProject(ProjectDetail projectDetail);
	
	public boolean saveItem(Item item);
	
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
	
	public boolean isItemAlreadyExisting(String itemName);
	
	public boolean isAliasSubProjectAlreadyExisting(String subAliasName, Integer projectId);
	
	public boolean isAliasDescriptionAlreadyExisting(ProjDescDetail projectDescDetail);

	public boolean isSerialNumberAlreadyExisting(ProjDescDetail projectDescDetail);

	public ProjDescDetail getProjectDescDetail(String projDescId, String subProject);

	public Map<String, String> getDescItemCodes(String itemCode);
	
	public void deleteProjectDescription(String projectDescriptionId);
	
	public boolean insertDataDescription(DescItemDetail descItemDetail);

	public void deleteProject(Integer projectId);

	public void deleteSubProjectByProjectId(Integer projectId);

	public void deleteSubProjectBySubProjectId(Integer subProjectId);

	public void deleteEmddetailByProjectId(Integer projectId);

	public void deleteEmddetailBySubProjectId(Integer subProjectId);

	public void deleteProjectDescriptionByProjectId(Integer projectId);

	public void deleteProjectDescriptionBySubProjectId(Integer subProjectId) ;
	
	public DescItemDetail getDataDescription(final DescItemDetail descItemDetail);
	
	public Set<String> fetchItemNames();
	
	public List<ItemDetail> getProjectData(Integer projId);
}
