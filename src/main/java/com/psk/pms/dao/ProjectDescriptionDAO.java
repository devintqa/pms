package com.psk.pms.dao;

import com.psk.pms.model.ProjDescComparisonDetail;
import com.psk.pms.model.ProjDescDetail;
import com.psk.pms.model.SearchDetail;

import java.util.List;

/**
 * Created by prakashbhanu57 on 7/6/2015.
 */
public interface ProjectDescriptionDAO {

	boolean saveProjDesc(ProjDescDetail projDescDetail);

	void deleteProjectDescriptionByProjectId(Integer projectId);

	void deleteProjectDescriptionBySubProjectId(Integer subProjectId);

	void deleteProjectDescription(String projectDescriptionId,String descType);

	ProjDescDetail getProjectDescDetail(String projDescId, String subProject, String descType);

	List<ProjDescDetail> getSubProjectDescDetailList(Integer subProjectId);

	List<ProjDescDetail> getProjectDescDetailList(Integer projectId, boolean searchUnderProject);
	
	List<ProjDescDetail> getProjectDescDetailList(SearchDetail searchDetail);

	List<ProjDescComparisonDetail> getProjectDescComparisonDetail(Integer projId);

	boolean isAliasDescriptionAlreadyExisting(ProjDescDetail projectDescDetail);

	boolean isSerialNumberAlreadyExisting(ProjDescDetail projectDescDetail);

	void saveProjectDescriptionDetails(List<ProjDescDetail> projDescDetails);

	void saveProposalProjectDescriptionDetails(List<ProjDescDetail> projDescDetails);

	void saveSubProjectDescriptionDetails(List<ProjDescDetail> projDescDetails);

	boolean isProjectDescriptionDetailsExistsForProject(int projectId, String governmentEst);

	boolean isProjectDescriptionDetailsExistsForSubProject(int subProjectId, String governmentEst);

	void saveBaseDescription(ProjDescDetail projDescDetail);

	public List<ProjDescDetail> getBaseDescriptions(String category);

	void deleteBaseProjectDescription(String serialNumber);

    boolean isGlobalDescriptionAlreadyExisting(String baseDescription);

	ProjDescDetail getBaseDescDetail(String baseDescId);
	
    ProjDescDetail getBaseDescription(String aliasDescription);

	ProjDescDetail getGovProjectDescDetail(String projDescId,String descType);
}
