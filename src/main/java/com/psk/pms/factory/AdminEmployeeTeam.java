package com.psk.pms.factory;

import com.psk.pms.model.DepositDetail;
import com.psk.pms.model.Employee;
import com.psk.pms.model.ProjectDetail;
import com.psk.pms.service.DepositDetailService;
import com.psk.pms.service.EmployeeService;
import com.psk.pms.service.ProjectService;
import com.psk.pms.service.SubProjectService;
import com.psk.pms.utils.DateFormatter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.ui.Model;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * Created by Sony on 04-09-2015.
 */
@Component
public class AdminEmployeeTeam implements EmployeeTeam {

    public static final String SUBMITTED = "Submitted";
    @Autowired
    EmployeeService employeeService;

    @Autowired
    ProjectService projectService;
    @Autowired
    DepositDetailService depositDetailService;

    @Autowired
    SubProjectService subProjectService;

    /**
     *
     * @param model
     * @param empId - Pass employee id as empty("") for Admin
     */
    @Override
    public void performTeamActivity(Model model, String empId) {
        List<Employee> newSignupRequestList = employeeService
                .getNewRegistrationRequest(null);
        if (!newSignupRequestList.isEmpty()) {
            model.addAttribute("newSignupRequestList", newSignupRequestList);
        }
        List<DepositDetail> depositEndAlertList = depositDetailService.getDepositDetails("");
        List<DepositDetail> depositDocumentList = getExpiringDepositDetails(depositEndAlertList);
        if (depositDocumentList.size() > 0) {
            model.addAttribute("depositDocumentList", depositDocumentList);
        }
    }

    @Override
    public List<ProjectDetail> getProjectDocumentList(String employeeId) {
        return projectService.getProjectDocumentList();
    }


    @Override
    public List<String> fetchProjects(String name, String empId) {
        List<String> result = new ArrayList<String>();
        Map<String, String> aliasProjectNames = projectService.getAliasProjectNames("");
        for (Map.Entry<String, String> entry : aliasProjectNames.entrySet()) {
            if (entry.getValue().toUpperCase().contains(name.toUpperCase())) {
                result.add(entry.getValue());
            }
        }
        return result;
    }

    @Override
    public List<String> fetchSubProjectInfo(String subaliasProjectName, String empId) {
        List<String> result = new ArrayList<String>();
        Map<String, String> subAliasProjectNames = subProjectService.getSubAliasProjectNames(subaliasProjectName, "");
        // intentionally passing empty to get all sub projectNames
        for (Map.Entry<String, String> entry : subAliasProjectNames.entrySet()) {
            if (entry.getValue().toUpperCase().contains(subaliasProjectName.toUpperCase())) {
                result.add(entry.getValue());
            }
        }
        return result;
    }

    @Override
    public Map<String, String> fetchProjects(String empId) {
        return projectService.getAliasProjectNames("");
    }

    @Override
    public Map<String, String> fetchSubProjects(String projectId, String employeeId) {
        return subProjectService.getSubAliasProjectNames(projectId,"");
    }

    @Override
    public List<DepositDetail> fetchDepositDetails(String employeeId) {
        return depositDetailService.getDepositDetails("");
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
                if (diffDays < 10) {
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

    @Override
    public ProjectDetail getProjectDocument(String projectId, String employeeId) {
        return projectService.getProjectDocument(projectId, "");
    }
}
