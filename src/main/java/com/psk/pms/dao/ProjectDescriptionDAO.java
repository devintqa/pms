package com.psk.pms.dao;

import com.psk.pms.model.ProjDescDetail;

import java.util.List;

/**
 * Created by prakashbhanu57 on 7/6/2015.
 */
public interface ProjectDescriptionDAO {

    boolean saveProjDesc(ProjDescDetail projDescDetail);

    void deleteProjectDescriptionByProjectId(Integer projectId);

    void deleteProjectDescriptionBySubProjectId(Integer subProjectId) ;

    void deleteProjectDescription(String projectDescriptionId);

    ProjDescDetail getProjectDescDetail(String projDescId, String subProject);

    List<ProjDescDetail> getSubProjectDescDetailList(Integer subProjectId);

    List<ProjDescDetail> getProjectDescDetailList(Integer projectId,boolean searchUnderProject);

    boolean isAliasDescriptionAlreadyExisting(ProjDescDetail projectDescDetail);

    boolean isSerialNumberAlreadyExisting(ProjDescDetail projectDescDetail);

    void saveProjectDescriptionDetails(List<ProjDescDetail> projDescDetails);

    void saveSubProjectDescriptionDetails(List<ProjDescDetail> projDescDetails);

    boolean isProjectDescriptionDetailsExistsForProject(int projectId);

    boolean isProjectDescriptionDetailsExistsForSubProject(int subProjectId);

}
