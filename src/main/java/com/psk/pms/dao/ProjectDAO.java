package com.psk.pms.dao;

import com.psk.pms.model.ProjectDetail;

import java.util.List;
import java.util.Map;

public interface ProjectDAO {

    boolean saveProject(ProjectDetail projectDetail);

    Map<String, String> getAliasProjectNames(String empId);

    ProjectDetail getProjectDocument(String projectId, String employeeId);

    List<ProjectDetail> getProjectDocumentList(String employeeId);

    List<ProjectDetail> getProjectDocumentList();

    boolean isAliasProjectAlreadyExisting(String aliasName);

    void deleteProject(Integer projectId);

    List<String> getDropDownValues(String type);

    Map<String, String> getAliasProjectNames();
    
    String fetchMainProjectType(Integer mainProjectId);
    
    String getMainProjAliasName(Integer mainProjectId);


}
