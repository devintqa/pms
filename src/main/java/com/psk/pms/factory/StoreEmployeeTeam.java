package com.psk.pms.factory;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.ui.Model;

import com.psk.pms.Constants;
import com.psk.pms.model.DepositDetail;
import com.psk.pms.model.Employee;
import com.psk.pms.model.Indent;
import com.psk.pms.model.ProjectDetail;
import com.psk.pms.model.QuoteDetails.SupplierQuoteDetails;
import com.psk.pms.service.EmployeeService;
import com.psk.pms.service.ProjectService;
import com.psk.pms.service.PurchaseService;

/**
 * Created by Sony on 25-09-2015.
 */
@Component
public class StoreEmployeeTeam implements EmployeeTeam {
	@Autowired
	ProjectService projectService;

	@Autowired
	EmployeeService employeeService;

	@Autowired
	PurchaseService purchaseService;

	@Override
	public void performTeamActivity(Model model, String empId) {
		String purchaseStatus = Constants.PURCHASED;
		List<SupplierQuoteDetails> purchaseList = purchaseService
				.getPurchasesByStatus(purchaseStatus);
		if (!purchaseList.isEmpty()) {
			model.addAttribute("purchaseList", purchaseList);
		}
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
		return projectService.getAliasProjectNames(empId);
	}

	@Override
	public Map<String, String> fetchSubProjects(String projectId,
			String employeeId) {
		return null;
	}

	@Override
	public List<String> fetchSubProjectInfo(String subaliasProjectName,
			String empId) {
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
