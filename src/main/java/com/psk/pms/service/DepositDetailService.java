package com.psk.pms.service;

import com.psk.pms.model.DepositDetail;

import java.util.List;

public interface DepositDetailService {

	boolean createEditDeposit(DepositDetail depositDetail);

	List<DepositDetail> getDepositDetails(String employeeId);

	DepositDetail getDepositDetailsById(String depositId);

	void deleteDeposit(Integer depositId);

	List<DepositDetail> getDepositDetailsByProjectId(Integer projectId);

	List<DepositDetail> getDepositDetailsBySubProjectId(Integer subProjectId);

}
