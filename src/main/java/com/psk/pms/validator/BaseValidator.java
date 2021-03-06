package com.psk.pms.validator;

import java.util.Map;

import com.psk.pms.factory.EmployeeTeam;
import com.psk.pms.factory.EmployeeTeamFactory;
import com.psk.pms.model.Employee;
import com.psk.pms.service.EmployeeService;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;

import com.psk.pms.service.ProjectService;
import com.psk.pms.service.SubProjectService;

public class BaseValidator {

    @Autowired
    protected ProjectService projectService;

    @Autowired
    protected SubProjectService subProjectService;

    @Autowired
    EmployeeTeamFactory employeeTeamFactory;

    @Autowired
    EmployeeService employeeService;

    private static final Logger LOGGER = Logger.getLogger(BaseValidator.class);

    public static final String MOBILE_PATTERN = "[0-9]{10}";
    public static final String NAME_PATTERN = "^[a-zA-Z]{2,20}$";
    public static final String EMAIL_PATTERN = "^[_A-Za-z0-9-\\+]+(\\.[_A-Za-z0-9-]+)*@"
            + "[A-Za-z0-9-]+(\\.[A-Za-z0-9]+)*(\\.[A-Za-z]{2,})$";
    String ID_PATTERN = "[0-9]+";
    String AMOUNT_PATTERN = "\\d+(?:\\.\\d+)?$";

    public String fetchProjectId(String aliasProjectName, String employeeId) {
        LOGGER.info("method = fetchProjectId()");
        Employee employee = employeeService.getEmployeeDetails(employeeId);
        EmployeeTeam employeeTeam = employeeTeamFactory.getEmployeeTeam(employee.getEmployeeTeam());
        Map<String, String> aliasProjectList = employeeTeam.fetchProjects(employeeId);
        for (Map.Entry<String, String> entry : aliasProjectList.entrySet()) {
            if (entry.getValue().equalsIgnoreCase(aliasProjectName)) {
                return entry.getKey();
            }
        }
        return null;
    }

    public String fetchSubProjectId(String aliasSubProjName, String employeeId) {
        LOGGER.info("method = fetchSubProjectId()");
        Employee employee = employeeService.getEmployeeDetails(employeeId);
        EmployeeTeam employeeTeam = employeeTeamFactory.getEmployeeTeam(employee.getEmployeeTeam());
        Map<String, String> aliasSubProjectList = employeeTeam.fetchSubProjects("", employeeId);
        for (Map.Entry<String, String> entry : aliasSubProjectList.entrySet()) {
            if (entry.getValue().equalsIgnoreCase(aliasSubProjName)) {
                return entry.getKey();
            }
        }
        return null;
    }
}
