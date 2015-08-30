package com.psk.pms.service;

import com.psk.pms.model.DepositDetail;

import java.util.List;

public interface DepositDetailService {

	boolean createEditDeposit(DepositDetail depositDetail);

	List<DepositDetail> getDepositDetails();

	DepositDetail getDepositDetailsById(String depositId);

	void deleteDeposit(Integer depositId);

	List<DepositDetail> getDepositDetailsByProjectId(Integer projectId);

	List<DepositDetail> getDepositDetailsBySubProjectId(Integer subProjectId);

	List<DepositDetail> getDepositEndAlertList();

	List<String> fetchDepositTypes();

    List<DepositDetail> getDepositDetailsInSubmittedStatus();
}
