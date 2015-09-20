package com.psk.pms.service;

import com.psk.pms.model.ProjectDetail;

import java.util.List;
import java.util.Map;

public interface ProjectService {

	boolean createEditProject(ProjectDetail projectDetail);

	Map<String, String> getAliasProjectNames(String empId);

	List<ProjectDetail> getProjectDocumentList(String employeeId);

	ProjectDetail getProjectDocument(String projectId, String employeeId);

	boolean isAliasProjectAlreadyExisting(String aliasName);

	void deleteProject(Integer projectId);

    List<String> getDropDownValuesFor(String type);


    Map<String,String> getAliasProjectNames();

    List<ProjectDetail> getProjectDocumentList();
}
