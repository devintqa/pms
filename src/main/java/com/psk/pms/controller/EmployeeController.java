package com.psk.pms.controller;

import java.security.Principal;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
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
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.support.SessionStatus;

import com.psk.pms.model.Employee;
import com.psk.pms.service.EmployeeService;
import com.psk.pms.utils.JsonHelper;
import com.psk.pms.validator.EmployeeValidator;

@Controller
public class EmployeeController {

	@Autowired
	EmployeeValidator employeeValidator;
	@Autowired
	EmployeeService employeeService;

	@RequestMapping(value = "/emp/login.do", method = RequestMethod.POST)
	public String saveEmployeeAction(
			@ModelAttribute("employeeForm") Employee employee,
			BindingResult result, Model model, SessionStatus status) {
		boolean isEmployeeAvailable = false;
		employeeValidator.validate(employee, result);
		if(!result.hasErrors()){
			isEmployeeAvailable = employeeService.isValidLogin(employee.getEmployeeId(), employee.getEmployeePwd());
		}
		if (result.hasErrors()) {
			return "EmployeeForm";
		} else if(!isEmployeeAvailable){
			model.addAttribute("loginMessage", "User Details Not Found. Please Sign Up.");
			return "EmployeeForm";
		} else {
			status.setComplete();
			model.addAttribute("employee", employee);
			return "EmployeeSuccess";
		}
	}

	@RequestMapping(value = "/emp/manageAccess.do", method = RequestMethod.POST, consumes="application/json")
	public @ResponseBody int enableAccess(@RequestBody String json){
		System.out.println("called in java"+json);
		HashMap<String, String> details = (HashMap<String, String>) JsonHelper.jsonToMap(json);
		Employee employee = new Employee();
		String action = (details.get("action").equalsIgnoreCase("enable")) ? "1" : "0"; 
		employee.setEnabled(action);
		employee.setEmployeeId(details.get("user"));
		System.out.println("called in java"+employee.getEmployeeId()+employee.getEnabled());
		int status = employeeService.manageUserAccess(employee);
		return status;
	}

	@RequestMapping("/emp/myview")
	public String mypage(Model model, Principal principal) {
		String userRole = employeeService.getUserRole(principal.getName());
		Employee employee = employeeService.getEmployeeDetails(principal.getName());
		employee.setEmployeeId(principal.getName());
		System.out.println("user: " + principal.getName());
		System.out.println("team: "+employee.getEmployeeTeam());
		employee.setEmployeeRole(userRole);
		model.addAttribute("employee", employee);
		if("admin".equalsIgnoreCase(employee.getEmployeeTeam())){
			List<Employee> newSignupRequestList = employeeService.getNewRegistrationRequest(null);
			if(!newSignupRequestList.isEmpty()){
				model.addAttribute("newSignupRequestList", newSignupRequestList);
			}
		}
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
