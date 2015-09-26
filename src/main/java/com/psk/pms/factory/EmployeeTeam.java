package com.psk.pms.factory;

import com.psk.pms.model.DepositDetail;
import com.psk.pms.model.ProjectDetail;
import org.springframework.ui.Model;

import java.util.List;
import java.util.Map;

/**
 * Created by Sony on 04-09-2015.
 */
public interface EmployeeTeam {

    void performTeamActivity(Model model, String empId);

    List<ProjectDetail> getProjectDocumentList(String employeeId);

    List<String> fetchProjects(String name, String empId);

    Map<String, String> fetchProjects(String empId);

    Map<String, String> fetchSubProjects(String projectId, String employeeId);

    List<String> fetchSubProjectInfo(String subaliasProjectName, String empId);

    List<DepositDetail> fetchDepositDetails(String employeeId);

    ProjectDetail getProjectDocument(String projectId, String employeeId);
}
