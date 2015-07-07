package com.psk.pms.service;

import com.psk.pms.dao.SubProjectDAO;
import com.psk.pms.model.SubProjectDetail;
import com.psk.pms.utils.PMSUtil;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * Created by prakashbhanu57 on 7/6/2015.
 */
public class SubProjectServiceImpl implements SubProjectService {

    private static final Logger LOGGER = Logger.getLogger(SubProjectServiceImpl.class);

    @Autowired
    private SubProjectDAO subProjectDAO;

    public boolean createEditSubProject(SubProjectDetail subProjectDetail) {
        SimpleDateFormat formatter = new SimpleDateFormat("dd-MM-yyyy");
        subProjectDetail.setSubTenderSqlDate(getSQLDate(subProjectDetail.getSubTenderDate(), formatter));
        subProjectDetail.setSubAgreementSqlDate(getSQLDate(subProjectDetail.getSubAgreementDate(), formatter));
        subProjectDetail.setSubCommencementSqlDate(getSQLDate(subProjectDetail.getSubCommencementDate(), formatter));
        subProjectDetail.setSubCompletionSqlDate(getSQLDate(subProjectDetail.getSubCompletionDate(), formatter));
        subProjectDetail.setLastUpdatedBy(subProjectDetail.getEmployeeId());
        subProjectDetail.setLastUpdatedAt(getCurrentDateTime());
        if (subProjectDetail.getSubLessPercentage() == "") {
            subProjectDetail.setSubLessPercentage(null);
        }
        if (subProjectDetail.getSubExPercentage() == "") {
            subProjectDetail.setSubExPercentage(null);
        }
        if (subProjectDetail.getSubPerformanceGuarantee() == "") {
            subProjectDetail.setSubPerformanceGuarantee(null);
        }
        boolean isInsertSuccessful = subProjectDAO.saveSubProject(subProjectDetail);
        return isInsertSuccessful;
    }

    private Date getSQLDate(String dateToBeFormatted, SimpleDateFormat formatter) {
        Date date = null;
        try {
            if (null != dateToBeFormatted) {
                date = (Date) formatter.parse(dateToBeFormatted);
            }
        } catch (ParseException e) {
            LOGGER.error("Error in parsing the date");
        }
        return date;
    }

    private Date getCurrentDateTime() {
        Calendar calendar = Calendar.getInstance();
        calendar.getTimeInMillis();
        Date date = new Date();
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String formattedDate = simpleDateFormat.format(date);
        return getSQLDate(formattedDate, simpleDateFormat);
    }

    public boolean isAliasSubProjectAlreadyExisting(String subAliasName, Integer projectId) {
        boolean isAvailable = false;
        isAvailable = subProjectDAO.isAliasSubProjectAlreadyExisting(subAliasName, projectId);
        return isAvailable;
    }

    public Map<String, String> getSubAliasProjectNames(String projectId) {
        Map<String, String> aliasSubProjectList = subProjectDAO.getSubAliasProjectNames(projectId);
        return aliasSubProjectList;
    }


    @Override
    public SubProjectDetail getSubProjectDocument(String subProjectId) {
        SimpleDateFormat formatter = new SimpleDateFormat("dd-MM-yyyy");
        SubProjectDetail subProjectDetail = subProjectDAO.getSubProjectDocument(subProjectId);
        subProjectDetail.setSubTenderDate(PMSUtil.getStringDate(subProjectDetail.getSubTenderSqlDate(), formatter));
        subProjectDetail.setSubAgreementDate(PMSUtil.getStringDate(subProjectDetail.getSubAgreementSqlDate(), formatter));
        subProjectDetail.setSubCommencementDate(PMSUtil.getStringDate(subProjectDetail.getSubCommencementSqlDate(), formatter));
        subProjectDetail.setSubCompletionDate(PMSUtil.getStringDate(subProjectDetail.getSubCompletionSqlDate(), formatter));
        return subProjectDetail;
    }

    @Override
    public List<SubProjectDetail> getSubProjectDocumentList(Integer projectId) {
        List<SubProjectDetail> subProjectDocumentList = subProjectDAO.getSubProjectDocumentList(projectId);
        return subProjectDocumentList;
    }

    @Override
    public void deleteSubProject(Integer subProjectId) {
        subProjectDAO.deleteSubProjectBySubProjectId(subProjectId);
    }
}
