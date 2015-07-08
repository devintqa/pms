package com.psk.pms.controller;

import java.security.Principal;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import com.psk.pms.service.EmdService;
import org.apache.log4j.Logger;
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
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.SessionAttributes;

import com.psk.pms.Constants;
import com.psk.pms.model.EmdDetail;
import com.psk.pms.model.Employee;
import com.psk.pms.model.ProjectDetail;
import com.psk.pms.service.EmployeeService;
import com.psk.pms.service.ProjectService;
import com.psk.pms.utils.JsonHelper;
import com.psk.pms.validator.EmployeeValidator;

@Controller
@SessionAttributes("employeeObj")
public class EmployeeController {

	@Autowired
	EmployeeValidator employeeValidator;
	@Autowired
	EmployeeService employeeService;
	@Autowired
	ProjectService projectService;
	@Autowired
	EmdService emdService;

	private static final Logger LOGGER = Logger.getLogger(EmployeeController.class);

	@RequestMapping(value = "/emp/myview/manageAccess.do", method = RequestMethod.POST, consumes="application/json")
	public @ResponseBody int enableAccess(@RequestBody String json){
		LOGGER.info("method = enableAccess() : request" +json);
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
		//employee.setEmployeeMail(details.get("mail"));
		LOGGER.info("Employee Id :"+employee.getEmployeeId()+" Employee Enabled : "+employee.getEnabled()+" Employee mail Id: "+employee.getEmployeeMail());
		int status = employeeService.manageUserAccess(employee);
		return status;
	}

	@RequestMapping(value = "/emp/myview/{empId}", method = RequestMethod.GET)
	public String getHomePage(@PathVariable String empId, @RequestParam(value="action", required=false) String action, Model model, Principal principal) {
		Employee employee = employeeService.getEmployeeDetails(principal.getName());
		model.addAttribute("employeeObj", employee);
		if("admin".equalsIgnoreCase(employee.getEmployeeTeam())){
			List<Employee> newSignupRequestList = employeeService.getNewRegistrationRequest(null);
			if(!newSignupRequestList.isEmpty()){
				model.addAttribute("newSignupRequestList", newSignupRequestList);
			}
		}
		if("technical".equalsIgnoreCase(employee.getEmployeeTeam())){
			List<ProjectDetail> projectDocumentList = projectService.getProjectDocumentList();
			if(!projectDocumentList.isEmpty()){
				model.addAttribute("projectDocumentList", projectDocumentList);
				model.addAttribute("action", action);
			}
		}
		if("admin".equalsIgnoreCase(employee.getEmployeeTeam())){
			List<EmdDetail> emdEndAlertList = emdService.getEmdEndAlertList();
			if(emdEndAlertList.size() > 0){
				model.addAttribute("emdDocumentList", emdEndAlertList);
			}
		}
		return "Welcome";
	}

	@RequestMapping(value = "/emp/login", method = RequestMethod.GET)
	public String initForm(ModelMap model, HttpServletRequest request) {
		HttpSession session = request.getSession(false);
		if(session == null){
			LOGGER.info("No session available");
		}else{
			LOGGER.info("This is old session");
			Employee employee = new Employee();
			model.addAttribute("employeeObj", employee);
			model.addAttribute("employeeForm", employee);
			return "SignIn";
		}
		Employee employee = new Employee();
		model.addAttribute("employeeForm", employee);
		return "SignIn";
		
	}
	
	@RequestMapping(value = "/emp/logout", method = RequestMethod.GET)
	public String logoutForm(@ModelAttribute("employeeObj") Employee employee, Model model) {
		employee.setEmployeeTeam("");
		model.addAttribute("employeeObj", employee);
		Employee newEmployee = new Employee();
		model.addAttribute("employeeForm", newEmployee);
		return "SignIn";
	}
	
	@RequestMapping(value = "/emp/sessionTimeout", method = RequestMethod.GET)
	public String expiredForm(ModelMap model, HttpSession session) {
		session.invalidate();
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
