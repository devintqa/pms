package com.psk.pms.dao;

import com.psk.pms.model.EMDDetail;

import java.util.List;

public interface EmdDAO {

    boolean saveEmd(EMDDetail emdDetail);

    List<EMDDetail> getEmdDetails();

    EMDDetail getEmdDetailsByEmdId(String emdId);

    void deleteEmdDetailByEmdId(Integer emdId);
}
