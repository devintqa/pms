package com.psk.pms.service;

import com.psk.pms.model.ProjDescComparisonDetail;
import com.psk.pms.model.ProjDescDetail;
import com.psk.pms.model.SearchDetail;

import java.util.List;

/**
 * Created by prakashbhanu57 on 7/6/2015.
 */
public interface ProjectDescriptionService {

	boolean createEditProjDesc(ProjDescDetail projDescDetail);

	ProjDescDetail getProjectDescDetail(String projDescId, String subProject);
	
	ProjDescDetail getBaseDescDetail(String baseDescId);

	List<ProjDescDetail> getSubProjectDescDetailList(Integer subProjId);

	List<ProjDescDetail> getProjectDescDetailList(Integer projId, boolean searchUnderProject);
	
	List<ProjDescDetail> getProjectDescDetailList(SearchDetail searchDetail);

	List<ProjDescComparisonDetail> getProjectDescComparisonDetail(Integer projId);

	boolean isAliasDescriptionAlreadyExisting(ProjDescDetail projectDescDetail);

	boolean isSerialNumberAlreadyExisting(ProjDescDetail projectDescDetail);

	void deleteProjectDescriptionDetail(String projectDescriptionId);

	void saveProjectDescriptionDetails(List<ProjDescDetail> projDescDetails);

	void saveSubProjectDescriptionDetails(List<ProjDescDetail> projDescDetails);

	boolean isProjectDescriptionDetailsExistsForProject(int projectId, String governmentEst);

	boolean isProjectDescriptionDetailsExistsForSubProject(int subProjectId, String governmentEst);

	void deleteAllTheDescriptionDetailsOfProject(int projectId);

	void deleteAllTheDescriptionDetailsOfSubProject(int subProjectId);

	void saveProposalProjectDescriptionDetails(List<ProjDescDetail> projDescDetails);

	void saveBaseProjectDescription(ProjDescDetail projDescDetail);

	List<ProjDescDetail> getBaseProjectDescriptions();

	void deleteBaseProjectDescription(String projectDescId);

    boolean isGlobalDescriptionAlreadyExisting(String  projectDescDetail);

    ProjDescDetail getBaseProjectDescription(String aliasDescription);

	ProjDescDetail getGovProjectDescDetail(String projDescId);

	
}
