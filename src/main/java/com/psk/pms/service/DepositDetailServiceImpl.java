package com.psk.pms.service;

import com.mysql.jdbc.StringUtils;
import com.psk.pms.dao.DepositDetailDAO;
import com.psk.pms.model.DepositDetail;
import com.psk.pms.utils.DateFormatter;

import org.apache.log4j.Logger;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import static com.mysql.jdbc.StringUtils.isNullOrEmpty;

public class DepositDetailServiceImpl implements DepositDetailService {

    private DepositDetailDAO depositDetailDAO;

    private static final Logger LOGGER = Logger.getLogger(DepositDetailServiceImpl.class);

    @Override
    public boolean createEditDeposit(DepositDetail depositDetail) {
        if (!depositDetail.isSubProjectDepositDetail()) {
            depositDetail.setAliasSubProjectName(null);
        }
        SimpleDateFormat formatter = new SimpleDateFormat("dd-MM-yyyy");
        depositDetail.setSqlDepositEndDate(getSQLDate(depositDetail.getDepositEndDate(),
                formatter));
        depositDetail.setSqlDepositStartDate(getSQLDate(depositDetail.getDepositStartDate(),
                formatter));
        depositDetail.setDepositExtensionSqlDate(getSQLDate(
                depositDetail.getDepositExtensionDate(), formatter));
        depositDetail.setSqlDepositRecievedDate(getSQLDate(depositDetail.getDepositRecievedDate(),formatter));
        depositDetail.setLastUpdatedBy(depositDetail.getEmployeeId());
        depositDetail.setLastUpdatedAt(getCurrentDateTime());
        if ("" == depositDetail.getDepositDetailSubmitter()) {
            depositDetail.setDepositDetailSubmitter("PSK");
        }
        if (isNullOrEmpty(depositDetail.getDepositStatus())){
            depositDetail.setDepositStatus("Submitted");
        }
        boolean isEmdUpdateSuccess = depositDetailDAO.saveDepositDetail(depositDetail);
        return isEmdUpdateSuccess;
    }

    @Override
    public List<DepositDetail> getDepositDetails() {
        SimpleDateFormat formatter = new SimpleDateFormat("dd-MM-yyyy");
        List<DepositDetail> depositDetails = depositDetailDAO.getDepositDetails();
        List<DepositDetail> depositFinalList = new ArrayList<DepositDetail>();
        for (DepositDetail depositDetail : depositDetails) {
            depositDetail.setDepositStartDate(DateFormatter.getStringDate(
                    depositDetail.getSqlDepositStartDate(), formatter));
            depositDetail.setDepositEndDate(DateFormatter.getStringDate(
                    depositDetail.getSqlDepositEndDate(), formatter));
            depositFinalList.add(depositDetail);
        }
        return depositFinalList;
    }

    @Override
    public DepositDetail getDepositDetailsById(String depositId) {
        SimpleDateFormat formatter = new SimpleDateFormat("dd-MM-yyyy");
        DepositDetail depositDetail = depositDetailDAO.getDepositDetailsById(depositId);
        depositDetail.setDepositStartDate(DateFormatter.getStringDate(
                depositDetail.getSqlDepositStartDate(), formatter));
        depositDetail.setDepositEndDate(DateFormatter.getStringDate(
                depositDetail.getSqlDepositEndDate(), formatter));
        depositDetail.setDepositExtensionDate(DateFormatter.getStringDate(
                depositDetail.getDepositExtensionSqlDate(), formatter));
        depositDetail.setDepositRecievedDate(DateFormatter.getStringDate(
                depositDetail.getSqlDepositRecievedDate(),formatter));
        return depositDetail;
    }

    private Date getSQLDate(String dateToBeFormatted, SimpleDateFormat formatter) {
        Date date = null;
        try {
            if (null != dateToBeFormatted) {
                date = (Date) formatter.parse(dateToBeFormatted);
            }
        } catch (ParseException e) {
            LOGGER.error("Error in parsing the date " + e.getMessage());
        }
        return date;
    }

    private Date getCurrentDateTime() {
        Calendar calendar = Calendar.getInstance();
        calendar.getTimeInMillis();
        Date date = new Date();
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat(
                "yyyy-MM-dd HH:mm:ss");
        String formattedDate = simpleDateFormat.format(date);
        return getSQLDate(formattedDate, simpleDateFormat);
    }

    @Override
    public List<DepositDetail> getDepositDetailsBySubProjectId(Integer subProjectId) {
        List<DepositDetail> depositDetails = depositDetailDAO
                .getDepositDetailsBySubProjectId(subProjectId);
        SimpleDateFormat formatter = new SimpleDateFormat("dd-MM-yyyy");
        for (DepositDetail depositDetail : depositDetails) {
            depositDetail.setDepositStartDate(DateFormatter.getStringDate(
                    depositDetail.getSqlDepositStartDate(), formatter));
            depositDetail.setDepositEndDate(DateFormatter.getStringDate(
                    depositDetail.getSqlDepositEndDate(), formatter));
        }
        return depositDetails;
    }

    @Override
    public List<DepositDetail> getDepositDetailsByProjectId(Integer projectId) {
        List<DepositDetail> depositDetails = depositDetailDAO.getDepositDetailsByProjectId(projectId);
        SimpleDateFormat formatter = new SimpleDateFormat("dd-MM-yyyy");
        for (DepositDetail depositDetail : depositDetails) {
            depositDetail.setDepositStartDate(DateFormatter.getStringDate(
                    depositDetail.getSqlDepositStartDate(), formatter));
            depositDetail.setDepositEndDate(DateFormatter.getStringDate(
                    depositDetail.getSqlDepositEndDate(), formatter));
        }
        return depositDetails;
    }

    public List<DepositDetail> getDepositEndAlertList() {
        Date todayDate = new Date();
        SimpleDateFormat formatter = new SimpleDateFormat("dd-MM-yyyy");
        List<DepositDetail> projectDocumentList = depositDetailDAO.getDepositDatesList();
        List<DepositDetail> depositDocumentList = new ArrayList<DepositDetail>();
        for (DepositDetail depositDetail : projectDocumentList) {
            long diff = depositDetail.getSqlDepositEndDate().getTime()
                    - todayDate.getTime();
            long diffDays = diff / (24 * 60 * 60 * 1000);
            if (diffDays < 14) {
                depositDetail.setDepositStartDate(DateFormatter.getStringDate(
                        depositDetail.getSqlDepositStartDate(), formatter));
                depositDetail.setDepositEndDate(DateFormatter.getStringDate(
                        depositDetail.getSqlDepositEndDate(), formatter));
                depositDetail.setDepositExtensionDate(DateFormatter.getStringDate(
                        depositDetail.getDepositExtensionSqlDate(), formatter));
                depositDocumentList.add(depositDetail);
            }
        }
        return depositDocumentList;
    }

    public List<String> fetchDepositTypes() {
        return depositDetailDAO.fetchDepositTypes();
    }


    @Override
    public List<DepositDetail> getDepositDetailsInSubmittedStatus() {
        List<DepositDetail> depositDetailsInSubmittedStatus = depositDetailDAO.getDepositDetailsInSubmittedStatus();
        SimpleDateFormat formatter = new SimpleDateFormat("dd-MM-yyyy");
        for (DepositDetail depositDetail : depositDetailsInSubmittedStatus) {
            depositDetail.setDepositStartDate(DateFormatter.getStringDate(
                    depositDetail.getSqlDepositStartDate(), formatter));
            depositDetail.setDepositEndDate(DateFormatter.getStringDate(
                    depositDetail.getSqlDepositEndDate(), formatter));
        }
        return depositDetailsInSubmittedStatus;
    }

    public void deleteDeposit(Integer depositId) {
        depositDetailDAO.deleteDepositDetailById(depositId);
    }

    public DepositDetailDAO getDepositDetailDAO() {
        return depositDetailDAO;
    }

    public void setDepositDetailDAO(DepositDetailDAO depositDetailDAO) {
        this.depositDetailDAO = depositDetailDAO;
    }
}
