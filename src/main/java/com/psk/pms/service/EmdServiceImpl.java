package com.psk.pms.service;

import com.psk.pms.dao.EmdDAO;
import com.psk.pms.model.EMDDetail;
import org.apache.log4j.Logger;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

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

    public EmdDAO getEmdDAO() {
        return emdDAO;
    }

    public void setEmdDAO(EmdDAO emdDAO) {
        this.emdDAO = emdDAO;
    }
}
