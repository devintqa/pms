package com.psk.pms.factory;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.ui.Model;

import com.psk.pms.model.DepositDetail;
import com.psk.pms.model.Employee;
import com.psk.pms.model.Indent;
import com.psk.pms.model.ProjectDetail;
import com.psk.pms.service.EmployeeService;
import com.psk.pms.service.FieldDescriptionService;
import com.psk.pms.service.ProjectService;

/**
 * Created by Sony on 04-09-2015.
 */

@Component
public class ManagementEmployeeTeam implements EmployeeTeam {

    public static final String GENERAL_MANAGER = "GENERAL MANAGER";
    @Autowired
	ProjectService projectService;

	@Autowired
	EmployeeService employeeService;


	@Autowired
	FieldDescriptionService fieldDescriptionService;

	@Override
	public void performTeamActivity(Model model, String empId) {
		String indentStatus = "";
		Employee employee = employeeService.getEmployeeDetails(empId);
		if(GENERAL_MANAGER.equalsIgnoreCase(employee.getEmployeeRole())){
			indentStatus = "PENDING LEVEL 2 APPROVAL";
		}
		List<Indent> indentList = fieldDescriptionService.getIndentListByStatus(indentStatus);
		if (!indentList.isEmpty()) {
			model.addAttribute("indentList", indentList);
		}
	}

	@Override
	public List<ProjectDetail> getProjectDocumentList(String employeeId) {
		return null;
	}

	@Override
	public List<String> fetchProjects(String name, String empId) {
		List<String> result = new ArrayList<String>();
		Map<String, String> aliasProjectNames = projectService.getAliasProjectNames(empId);
		for (Map.Entry<String, String> entry : aliasProjectNames.entrySet()) {
			if (entry.getValue().toUpperCase().contains(name.toUpperCase())) {
				result.add(entry.getValue());
			}
		}
		return result;
	}

	@Override
	public Map<String, String> fetchProjects(String empId) {
		return projectService.getAliasProjectNames(empId);
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

	@Override
	public ProjectDetail getProjectDocument(String projectId, String employeeId) {
		return null;
	}
}
