package com.psk.pms.service;

import com.psk.pms.model.SubProjectDetail;

import java.util.List;
import java.util.Map;

/**
 * Created by prakashbhanu57 on 7/6/2015.
 */
public interface SubProjectService {

    List<SubProjectDetail> getSubProjectDocumentList(Integer projectId);

    boolean isAliasSubProjectAlreadyExisting(String subAliasName, Integer projectId);

    void deleteSubProject(Integer subProjectId);

    SubProjectDetail getSubProjectDocument(String subProject);

    Map<String, String> getSubAliasProjectNames(String projectId);

    boolean createEditSubProject(SubProjectDetail subProjectDetail);
}
