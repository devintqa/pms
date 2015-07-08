package com.psk.pms.dao;

import com.psk.pms.model.EMDDetail;

import java.util.List;

public interface EmdDAO {

    boolean saveEmd(EMDDetail emdDetail);

    List<EMDDetail> getEmdDetails();

    EMDDetail getEmdDetailsByEmdId(String emdId);

    void deleteEmdDetailByEmdId(Integer emdId);

    List<EMDDetail> getEmdDetailsByProjectId(Integer projectId);

    List<EMDDetail> getEmdDetailsBySubProjectId(Integer subProjectId);

    List<EMDDetail> getEMDDatesList();

    void deleteEmddetailByProjectId(Integer projectId);

    void deleteEmddetailBySubProjectId(Integer subProjectId);
}
