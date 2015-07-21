package com.psk.pms.dao;

import com.psk.pms.model.EmdDetail;

import java.util.List;

public interface EmdDAO {

    boolean saveEmd(EmdDetail emdDetail);

    List<EmdDetail> getEmdDetails();

    EmdDetail getEmdDetailsByEmdId(String emdId);

    void deleteEmdDetailByEmdId(Integer emdId);

    List<EmdDetail> getEmdDetailsByProjectId(Integer projectId);

    List<EmdDetail> getEmdDetailsBySubProjectId(Integer subProjectId);

    List<EmdDetail> getEMDDatesList();

    void deleteEmddetailByProjectId(Integer projectId);

    void deleteEmddetailBySubProjectId(Integer subProjectId);

    List<String> fetchEmdTypes();
}
