package com.psk.pms.factory;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.ui.Model;

import com.psk.pms.Constants;
import com.psk.pms.model.DepositDetail;
import com.psk.pms.model.Indent;
import com.psk.pms.model.ProjectDetail;
import com.psk.pms.model.QuoteDetails.SupplierQuoteDetails;
import com.psk.pms.service.EmployeeService;
import com.psk.pms.service.FieldDescriptionService;

@Component
public class PurchaseEmployeeTeam implements EmployeeTeam {

	@Autowired
	EmployeeService employeeService;

	@Autowired
	FieldDescriptionService fieldDescriptionService;

	@Override
	public void performTeamActivity(Model model, String empId) {
		List<Indent> indentList = fieldDescriptionService.getIndentListByStatus(Constants.PENDING_PURCHASE);
		List<SupplierQuoteDetails> purchaseList = fieldDescriptionService.getPurchaseListByStatus(Constants.APPROVED);
		if (!indentList.isEmpty()) {
			model.addAttribute("indentList", indentList);
		}
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

	@Override
	public ProjectDetail getProjectDocument(String projectId, String employeeId) {
		return null;
	}
}
