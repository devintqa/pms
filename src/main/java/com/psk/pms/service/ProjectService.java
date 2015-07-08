package com.psk.pms.service;

import com.psk.pms.model.ProjectDetail;

import java.util.List;
import java.util.Map;

public interface ProjectService {
	
	boolean createEditProject(ProjectDetail projectDetail);

	Map<String, String> getAliasProjectNames();

	List<ProjectDetail> getProjectDocumentList();

	ProjectDetail getProjectDocument(String projectId);

	boolean isAliasProjectAlreadyExisting(String aliasName);
	
	void deleteProject(Integer projectId);

}
