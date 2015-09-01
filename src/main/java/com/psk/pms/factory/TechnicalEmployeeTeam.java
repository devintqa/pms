package com.psk.pms.factory;

import com.psk.pms.model.DepositDetail;
import com.psk.pms.model.ProjectDetail;
import com.psk.pms.service.DepositDetailService;
import com.psk.pms.service.ProjectService;
import com.psk.pms.utils.DateFormatter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.ui.Model;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Created by Sony on 04-09-2015.
 */
@Component
public class TechnicalEmployeeTeam implements EmployeeTeam {

    @Autowired
    ProjectService projectService;

    @Autowired
    DepositDetailService depositDetailService;

    public static final String SUBMITTED = "Submitted";

    @Override
    public void performTeamActivity(Model model) {
        List<ProjectDetail> projectDocumentList = projectService
                .getProjectDocumentList();
        if (!projectDocumentList.isEmpty()) {
            model.addAttribute("projectDocumentList", projectDocumentList);
        }
        List<DepositDetail> depositEndAlertList = depositDetailService.getDepositDetails();
        List<DepositDetail> depositDocumentList = getExpiringDepositDetails(depositEndAlertList);
        if (depositDocumentList.size() > 0) {
            model.addAttribute("depositDocumentList", depositDocumentList);
        }
    }

    private List<DepositDetail> getExpiringDepositDetails(List<DepositDetail> depositEndAlertList) {
        Date todayDate = new Date();
        SimpleDateFormat formatter = new SimpleDateFormat("dd-MM-yyyy");
        List<DepositDetail> depositDocumentList = new ArrayList<DepositDetail>();
        for (DepositDetail depositDetail : depositEndAlertList) {
            if (SUBMITTED.equalsIgnoreCase(depositDetail.getDepositStatus())) {
                long diff = depositDetail.getSqlDepositEndDate().getTime()
                        - todayDate.getTime();
                long diffDays = diff / (24 * 60 * 60 * 1000);
                if (diffDays < 20) {
                    depositDetail.setDepositStartDate(DateFormatter.getStringDate(
                            depositDetail.getSqlDepositStartDate(), formatter));
                    depositDetail.setDepositEndDate(DateFormatter.getStringDate(
                            depositDetail.getSqlDepositEndDate(), formatter));
                    depositDetail.setDepositExtensionDate(DateFormatter.getStringDate(
                            depositDetail.getDepositExtensionSqlDate(), formatter));
                    depositDocumentList.add(depositDetail);
                }
            }
        }
        return depositDocumentList;
    }
}
