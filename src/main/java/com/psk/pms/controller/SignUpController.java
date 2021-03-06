package com.psk.pms.controller;

import java.util.List;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.support.SessionStatus;

import com.psk.pms.Constants;
import com.psk.pms.model.Employee;
import com.psk.pms.service.EmployeeService;
import com.psk.pms.service.ProjectService;
import com.psk.pms.validator.SignUpValidator;

@Controller
public class SignUpController {

	@Autowired
	SignUpValidator signupValidator;
	@Autowired
	EmployeeService employeeService;
    @Autowired
    ProjectService projectService;

	private static final Logger LOGGER = Logger
			.getLogger(SignUpController.class);

	@RequestMapping(value = "/emp/signup", method = RequestMethod.GET)
	public String initForm(ModelMap model) {
		Employee employee = new Employee();
		model.addAttribute("signupForm", employee);
		return "SignUp";
	}

	@RequestMapping(value = "/emp/signup.do", method = RequestMethod.POST)
	public String saveSignUpAction(
			@ModelAttribute("signupForm") Employee employee,
			BindingResult result, Model model, SessionStatus status) {
		boolean isSignUpSuccessful = false;
		signupValidator.validate(employee, result);

		if (!result.hasErrors()) {
			employee.setEnabled(Constants.REQUEST_EMPLOYEE_ACCESS);
			if ("M".equalsIgnoreCase(employee.getEmployeeGender())) {
				employee.setEmployeeGender("Male");
			} else {
				employee.setEmployeeGender("Female");
			}
			isSignUpSuccessful = employeeService.signupEmployee(employee);
		}

		if (result.hasErrors() || !isSignUpSuccessful) {
			return "SignUp";
		} else {
			status.setComplete();
			Employee emp = new Employee();
			model.addAttribute("employeeForm", emp);
			model.addAttribute(
					"loginMessage",
					"Signup successful. Awaiting Admin Approval. You will be able to login to application on approval.");
			return "SignIn";
		}
	}

	@ModelAttribute("employeeTeamList")
	public List<String> populateTeamList() {
		List<String> employeeTeam = projectService.getDropDownValuesFor(Constants.TEAM);
		LOGGER.info("method = populateTeamList()");
		return employeeTeam;
	}
}
