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
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.SessionAttributes;

import com.psk.pms.model.Employee;
import com.psk.pms.service.EmployeeService;
import com.psk.pms.Constants;
import com.psk.pms.utils.JsonHelper;
import com.psk.pms.validator.EmployeeValidator;

@Controller
@SessionAttributes("employeeObj")
public class EmployeeController {

	@Autowired
	EmployeeValidator employeeValidator;
	@Autowired
	EmployeeService employeeService;

	@RequestMapping(value = "/emp/myview/manageAccess.do", method = RequestMethod.POST, consumes="application/json")
	public @ResponseBody int enableAccess(@RequestBody String json){
		System.out.println("called in java"+json);
		HashMap<String, String> details = (HashMap<String, String>) JsonHelper.jsonToMap(json);
		Employee employee = new Employee();
		String action = "";
		if(details.get("action").equalsIgnoreCase("enable")){
			action = Constants.ENABLE_EMPLOYEE_ACCESS;
		}else if(details.get("action").equalsIgnoreCase("deny")){
			action = Constants.DENY_EMPLOYEE_ACCESS;
		}else{
			action = Constants.DISABLE_EMPLOYEE_ACCESS;
		}
		employee.setEnabled(action);
		employee.setEmployeeId(details.get("user"));
		employee.setEmployeeMail(details.get("mail"));
		System.out.println("called in java"+employee.getEmployeeId()+employee.getEnabled()+employee.getEmployeeMail());
		int status = employeeService.manageUserAccess(employee);
		return status;
	}

	@RequestMapping(value = "/emp/myview/{empId}", method = RequestMethod.GET)
	public String getHomePage(@PathVariable String empId, Model model, Principal principal) {
		Employee employee = employeeService.getEmployeeDetails(principal.getName());
		model.addAttribute("employeeObj", employee);
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
