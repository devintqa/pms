package com.psk.pms.dao;

import com.psk.pms.model.DepositDetail;

import java.util.List;

public interface DepositDetailDAO {

    boolean saveDepositDetail(DepositDetail depositDetail);

    List<DepositDetail> getDepositDetails(String employeeId);

    DepositDetail getDepositDetailsById(String depositId);

    void deleteDepositDetailById(Integer depositId);

    List<DepositDetail> getDepositDetailsByProjectId(Integer projectId);

    List<DepositDetail> getDepositDetailsBySubProjectId(Integer subProjectId);

    void deleteDepositDetailByProjectId(Integer projectId);

    void deleteDepositDetailBySubProjectId(Integer subProjectId);

}
