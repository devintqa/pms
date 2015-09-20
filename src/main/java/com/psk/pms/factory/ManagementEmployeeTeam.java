package com.psk.pms.factory;

import com.psk.pms.model.DepositDetail;
import com.psk.pms.model.ProjectDetail;
import org.springframework.stereotype.Component;
import org.springframework.ui.Model;

import java.util.List;
import java.util.Map;

/**
 * Created by Sony on 04-09-2015.
 */

@Component
public class ManagementEmployeeTeam implements EmployeeTeam {
    @Override
    public void performTeamActivity(Model model, String empId) {

    }

    @Override
    public List<ProjectDetail> getProjectDocumentList(String employeeId) {
        return null;
    }

    @Override
    public List<String> fetchProjects(String name, String empId) {
        return null;
    }

    @Override
    public Map<String, String> fetchProjects(String empId) {
        return null;
    }

    @Override
    public Map<String, String> fetchSubProjects(String projectId, String employeeId) {
        return null;
    }

    @Override
    public List<String> fetchSubProjectInfo(String subaliasProjectName, String empId) {
        return null;
    }

    @Override
    public List<DepositDetail> fetchDepositDetails(String employeeId) {
        return null;
    }
}
