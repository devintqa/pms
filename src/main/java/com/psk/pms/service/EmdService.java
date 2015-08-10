package com.psk.pms.service;

import com.psk.pms.model.EmdDetail;

import java.util.List;

public interface EmdService {

	boolean createEditEmd(EmdDetail emdDetail);

	List<EmdDetail> getEmdDetails();

	EmdDetail getEmdDetailsByEmdId(String emdId);

	void deleteEmd(Integer numericEmdId);

	List<EmdDetail> getEmdDetailsByProjectId(Integer projectId);

	List<EmdDetail> getEmdDetailsBySubProjectId(Integer subProjectId);

	List<EmdDetail> getEmdEndAlertList();

	List<String> fetchEmdTypes();
}
