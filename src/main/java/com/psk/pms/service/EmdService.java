package com.psk.pms.service;

import com.psk.pms.model.EMDDetail;

import java.util.List;


public interface EmdService {

    public boolean createEditEmd(EMDDetail emdDetail);

    List<EMDDetail> getEmdDetails();

    EMDDetail getEmdDetailsByEmdId(String emdId);

    void deleteEmd(Integer numericEmdId);
}
