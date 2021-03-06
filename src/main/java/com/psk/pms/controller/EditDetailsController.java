package com.psk.pms.controller;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.support.SessionStatus;

import com.psk.pms.model.Employee;
import com.psk.pms.service.EmployeeService;
import com.psk.pms.validator.EditDetailValidator;

@Controller
public class EditDetailsController {

	@Autowired
	EditDetailValidator editDetailValidator;
	@Autowired
	EmployeeService employeeService;

	private static final Logger LOGGER = Logger
			.getLogger(EditDetailsController.class);

	@RequestMapping(value = "/emp/myview/details/edit/{empId}", method = RequestMethod.GET)
	public String editEmployeeDetails(@PathVariable String empId, Model model) {
		Employee employee = employeeService.getEmployeeDetails(empId);
		model.addAttribute("employee", employee);
		return "EditDetails";
	}

	@RequestMapping(value = "/emp/myview/details/edit/editDetails.do", method = RequestMethod.POST)
	public String saveUpdateAction(
			@ModelAttribute("employee") Employee employee,
			BindingResult result, Model model, SessionStatus status) {
		boolean isUpdateSuccessful = false;
		editDetailValidator.validate(employee, result);
		if (!result.hasErrors()) {
			isUpdateSuccessful = employeeService.updateEmployee(employee);
		}
		if (result.hasErrors() || !isUpdateSuccessful) {
			return "EditDetails";
		}
		LOGGER.info("isUpdateSuccessful : " + isUpdateSuccessful);
		status.setComplete();
		if (isUpdateSuccessful) {
			model.addAttribute("employee", employee);
			model.addAttribute("updateSuccessMessage",
					"Employee Details updated successfully!");
		}
		return "EditDetails";
	}

}
