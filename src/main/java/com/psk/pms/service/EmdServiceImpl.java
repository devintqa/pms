package com.psk.pms.service;

import com.psk.pms.dao.EmdDAO;
import com.psk.pms.model.EMDDetail;
import com.psk.pms.utils.PMSUtil;

import org.apache.log4j.Logger;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

public class EmdServiceImpl implements EmdService {

    private EmdDAO emdDAO;

    private static final Logger LOGGER = Logger.getLogger(EmdServiceImpl.class);

    @Override
    public boolean createEditEmd(EMDDetail emdDetail) {
    	if(!emdDetail.isSubProjectEMD())
        {
    		emdDetail.setAliasSubProjectName(null);
        }
        SimpleDateFormat formatter = new SimpleDateFormat("dd-MM-yyyy");
        emdDetail.setSqlEmdEndDate(getSQLDate(emdDetail.getEmdEndDate() , formatter ));
        emdDetail.setSqlEmdStartDate(getSQLDate(emdDetail.getEmdStartDate(), formatter));
        emdDetail.setEmdExtensionSqlDate(getSQLDate(emdDetail.getEmdExtensionDate(),formatter));
        emdDetail.setLastUpdatedBy(emdDetail.getEmployeeId());
        emdDetail.setLastUpdatedAt(getCurrentDateTime());
        if("" == emdDetail.getEmdSubmitter())
        {
            emdDetail.setEmdSubmitter("PSK");
        }
        boolean isEmdUpdateSuccess = emdDAO.saveEmd(emdDetail);
        return isEmdUpdateSuccess;
    }

    @Override
    public List<EMDDetail> getEmdDetails() {
    	SimpleDateFormat formatter = new SimpleDateFormat("dd-MM-yyyy");
    	List<EMDDetail> emdDetails = emdDAO.getEmdDetails();
    	List<EMDDetail> emdFinalList = new ArrayList<EMDDetail>();
    	for(EMDDetail emdDetail : emdDetails){
            emdDetail.setEmdStartDate(PMSUtil.getStringDate(emdDetail.getSqlEmdStartDate(), formatter));
    		emdDetail.setEmdEndDate(PMSUtil.getStringDate(emdDetail.getSqlEmdEndDate(), formatter));
    		emdFinalList.add(emdDetail);
    	}
        return emdFinalList;
    }

    @Override
    public EMDDetail getEmdDetailsByEmdId(String emdId) {
    	SimpleDateFormat formatter = new SimpleDateFormat("dd-MM-yyyy");
        EMDDetail emdDetail = emdDAO.getEmdDetailsByEmdId(emdId);
        emdDetail.setEmdStartDate(PMSUtil.getStringDate(emdDetail.getSqlEmdStartDate(), formatter));
    	emdDetail.setEmdEndDate(PMSUtil.getStringDate(emdDetail.getSqlEmdEndDate(), formatter));
    	emdDetail.setEmdExtensionDate(PMSUtil.getStringDate(emdDetail.getEmdExtensionSqlDate(), formatter));
    	return emdDetail;
    }

    private Date getSQLDate(String dateToBeFormatted, SimpleDateFormat formatter){
        Date date = null;
        try {
            if(null != dateToBeFormatted) {
                date = (Date) formatter.parse(dateToBeFormatted);
            }
        } catch (ParseException e) {
            LOGGER.error("Error in parsing the date "+ e.getMessage());
        }
        return date;
    }

    private Date getCurrentDateTime() {
        Calendar calendar = Calendar.getInstance();
        calendar.getTimeInMillis();
        Date date = new Date();
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String formattedDate = simpleDateFormat.format(date);
        return getSQLDate(formattedDate,simpleDateFormat);
    }

    @Override
    public List<EMDDetail> getEmdDetailsBySubProjectId(Integer subProjectId) {
        List<EMDDetail> emdDetails = emdDAO.getEmdDetailsBySubProjectId(subProjectId);
        SimpleDateFormat formatter = new SimpleDateFormat("dd-MM-yyyy");
        for(EMDDetail emdDetail : emdDetails)
        {
            emdDetail.setEmdStartDate(PMSUtil.getStringDate(emdDetail.getSqlEmdStartDate(), formatter));
            emdDetail.setEmdEndDate(PMSUtil.getStringDate(emdDetail.getSqlEmdEndDate(), formatter));
        }
        return emdDetails;
    }

    @Override
    public List<EMDDetail> getEmdDetailsByProjectId(Integer projectId) {
        List<EMDDetail> emdDetails = emdDAO.getEmdDetailsByProjectId(projectId);
        SimpleDateFormat formatter = new SimpleDateFormat("dd-MM-yyyy");
        for(EMDDetail emdDetail : emdDetails)
        {
            emdDetail.setEmdStartDate(PMSUtil.getStringDate(emdDetail.getSqlEmdStartDate(), formatter));
            emdDetail.setEmdEndDate(PMSUtil.getStringDate(emdDetail.getSqlEmdEndDate(), formatter));
        }
        return emdDetails;
    }

    public List<EMDDetail> getEmdEndAlertList() {
        Date todayDate = new Date();
        SimpleDateFormat formatter = new SimpleDateFormat("dd-MM-yyyy");
        List<EMDDetail> projectDocumentList = emdDAO.getEMDDatesList();
        List<EMDDetail> emdDocumentList = new ArrayList<EMDDetail>();
        for(EMDDetail emdDetail : projectDocumentList){
            long diff = emdDetail.getSqlEmdEndDate().getTime() - todayDate.getTime();
            long diffDays = diff / (24 * 60 * 60 * 1000);
            if(diffDays < 14){
                emdDetail.setEmdStartDate(PMSUtil.getStringDate(emdDetail.getSqlEmdStartDate(), formatter));
                emdDetail.setEmdEndDate(PMSUtil.getStringDate(emdDetail.getSqlEmdEndDate(), formatter));
                emdDetail.setEmdExtensionDate(PMSUtil.getStringDate(emdDetail.getEmdExtensionSqlDate(), formatter));
                emdDocumentList.add(emdDetail);
            }
        }
        return emdDocumentList;
    }

    public void deleteEmd(Integer numericEmdId) {
        emdDAO.deleteEmdDetailByEmdId(numericEmdId);
    }

    public EmdDAO getEmdDAO() {
        return emdDAO;
    }

    public void setEmdDAO(EmdDAO emdDAO) {
        this.emdDAO = emdDAO;
    }
}
