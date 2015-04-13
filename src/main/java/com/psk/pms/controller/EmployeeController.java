package com.psk.pms.controller;

import java.security.Principal;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.LinkedHashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.propertyeditors.CustomDateEditor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.support.SessionStatus;

import com.psk.pms.model.Employee;
import com.psk.pms.service.EmployeeService;
import com.psk.pms.validator.EmployeeValidator;

@Controller
public class EmployeeController {

	@Autowired
	EmployeeValidator employeeValidator;
	@Autowired
	EmployeeService employeeService;

	@RequestMapping(value = "/emp/login.do", method = RequestMethod.POST)
	public String logIn(@ModelAttribute("employeeForm") Employee employee, BindingResult result, Model model, SessionStatus status) {
		boolean isEmployeeAvailable = false;
		employeeValidator.validate(employee, result);
		System.out.println("EMPLOYEE");
		if (!result.hasErrors()) {
			isEmployeeAvailable = employeeService.isValidLogin(employee.getEmpId(), employee.getEmpPassword());
		}
		if (result.hasErrors()) {
			return "SignIn";
		} else if (!isEmployeeAvailable) {
			model.addAttribute("loginMessage", "User Details Not Found. Please Sign Up.");
			return "SignIn";
		} else {
			status.setComplete();
			model.addAttribute("employee", employee);
			System.out.println("checkpoint:"+employee.getEmployeeTeam());
			return "Welcome";
		}
	}

	@RequestMapping("/emp/myview")
	public String mypage(Model model, Principal principal) {
		System.out.println("MyView" + principal.getName());
		Employee employee = new Employee();
		String userRole = employeeService.getUserRole(principal.getName());
		employee.setEmpId(principal.getName());
		employee.setEmployeeRole(userRole);
		model.addAttribute("employee", employee);
		return "Welcome";
	}

	@RequestMapping(value = "/emp/login", method = RequestMethod.GET)
	public String initForm(ModelMap model) {
		Employee employee = new Employee();
		model.addAttribute("employeeForm", employee);
		return "SignIn";
	}

	@InitBinder
	public void initBinder(WebDataBinder binder) {
		SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
		binder.registerCustomEditor(Date.class, new CustomDateEditor(
				dateFormat, true));
	}
	
	@ModelAttribute("employeeTeamList")
	public Map<String, String> populateTeamList() {
		Map<String, String> employeeTeam = new LinkedHashMap<String, String>();
		employeeTeam.put("Admin", "Admin");
		employeeTeam.put("Account", "Account");
		employeeTeam.put("Management", "Management");
		employeeTeam.put("Purchase", "Purchase");
		employeeTeam.put("Technical", "Technical");
		return employeeTeam;
	}

}
