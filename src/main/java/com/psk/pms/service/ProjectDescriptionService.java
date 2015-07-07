package com.psk.pms.service;

import com.psk.pms.model.ProjDescDetail;

import java.util.List;

/**
 * Created by prakashbhanu57 on 7/6/2015.
 */
public interface ProjectDescriptionService {

    boolean createEditProjDesc(ProjDescDetail projDescDetail);

    ProjDescDetail getProjectDescDetail(String projDescId, String subProject);

    List<ProjDescDetail> getSubProjectDescDetailList(Integer subProjId);

    List<ProjDescDetail> getProjectDescDetailList(Integer projId,boolean searchUnderProject);

    boolean isAliasDescriptionAlreadyExisting(ProjDescDetail projectDescDetail);

    boolean isSerialNumberAlreadyExisting(ProjDescDetail projectDescDetail);

    void deleteProjectDescriptionDetail(String projectDescriptionId);

}
