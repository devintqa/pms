package com.psk.pms.dao;

import com.psk.pms.model.ProjectDetail;
import java.util.List;
import java.util.Map;

public interface ProjectDAO {

    boolean saveProject(ProjectDetail projectDetail);

    Map<String, String> getAliasProjectNames();

    ProjectDetail getProjectDocument(String projectId);

    List<ProjectDetail> getProjectDocumentList();

    boolean isAliasProjectAlreadyExisting(String aliasName);

    void deleteProject(Integer projectId);

    List<String> getProjectTypes();

}
