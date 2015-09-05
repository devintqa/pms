package com.psk.pms.controller;

import java.io.IOException;
import java.security.Principal;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.propertyeditors.CustomDateEditor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
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
import org.springframework.web.bind.support.SessionStatus;

import com.psk.pms.Constants;
import com.psk.pms.factory.EmployeeTeam;
import com.psk.pms.factory.EmployeeTeamFactory;
import com.psk.pms.model.Employee;
import com.psk.pms.model.Team;
import com.psk.pms.service.DepositDetailService;
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
    DepositDetailService depositDetailService;
    @Autowired
    EmployeeTeamFactory employeeTeamFactory;

    private static final Logger LOGGER = Logger
            .getLogger(EmployeeController.class);

    @RequestMapping(value = "/emp/myview/manageAccess.do", method = RequestMethod.POST, consumes = "application/json")
    public
    @ResponseBody
    int enableAccess(@RequestBody String json) {
        LOGGER.info("method = enableAccess() : request" + json);
        HashMap<String, String> details = (HashMap<String, String>) JsonHelper
                .jsonToMap(json);
        Employee employee = new Employee();
        String action = "";
        if ("enable".equalsIgnoreCase(details.get("action"))) {
            action = Constants.ENABLE_EMPLOYEE_ACCESS;
        } else if ("deny".equalsIgnoreCase(details.get("action"))) {
            action = Constants.DENY_EMPLOYEE_ACCESS;
        } else {
            action = Constants.DISABLE_EMPLOYEE_ACCESS;
        }
        employee.setEnabled(action);
        employee.setEmployeeId(details.get("user"));
        // employee.setEmployeeMail(details.get("mail"));
        LOGGER.info("Employee Id :" + employee.getEmployeeId()
                + " Employee Enabled : " + employee.getEnabled()
                + " Employee mail Id: " + employee.getEmployeeMail());
        int status = employeeService.manageUserAccess(employee);
        return status;
    }

    @RequestMapping(value = "/emp/myview/{empId}", method = RequestMethod.GET)
    public String getHomePage(@PathVariable String empId,
                              @RequestParam(value = "action", required = false) String action,
                              Model model, Principal principal) {
        Employee employee = employeeService.getEmployeeDetails(principal.getName());
        model.addAttribute("employeeObj", employee);
        model.addAttribute("action", action);
        EmployeeTeam employeeTeam = employeeTeamFactory.getEmployeeTeam(employee.getEmployeeTeam());
        employeeTeam.performTeamActivity(model);
        return "Welcome";
    }

    @RequestMapping(value = "/emp/login", method = RequestMethod.GET)
    public String initForm(ModelMap model, HttpServletRequest request) {
        HttpSession session = request.getSession(false);
        if (session == null) {
            LOGGER.info("No session available");
        } else {
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
    public String logoutForm(@ModelAttribute("employeeObj") Employee employee,
                             Model model) {
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
        model.addAttribute("sessionTimeOutMessage",
                "Session has got timed out. Please re-login.");
        return "SignIn";
    }

    @InitBinder
    public void initBinder(WebDataBinder binder) {
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        binder.registerCustomEditor(Date.class, new CustomDateEditor(
                dateFormat, true));
    }


    @RequestMapping(value = "/emp/myview/createRole/{empId}", method = RequestMethod.GET)
    public String getEmployeeRole(@PathVariable String empId,
                                  @RequestParam(value = "team", required = false) String team,
                                  Model model, Principal principal) {
        Team teamForm = new Team();
        Employee employee = employeeService.getEmployeeDetails(principal
                .getName());
        teamForm.setEmployeeId(empId);
        model.addAttribute("employeeObj", employee);
        model.addAttribute("teamForm", teamForm);
        return "BuildRole";
    }


    @RequestMapping(value = "/emp/myview/createRole/saveRole.do", method = RequestMethod.POST)
    public String saveRole(
            @ModelAttribute("teamForm") Team team,
            BindingResult result, Model model, SessionStatus status) throws IOException {
        employeeValidator.validate(team, result);
        if (!result.hasErrors()) {
            boolean isRoleSaveSuccessful = employeeService.saveTeamRoles(team);
            if (isRoleSaveSuccessful) {
                model.addAttribute("roleCreationMessage", "Role Creation is Successful.");
            } else {
                model.addAttribute("roleCreationErrorMessage", "Error in creating the Role");
            }
        }
        return "BuildRole";
    }

    @RequestMapping(value = "/emp/myview/createRole/getRole.do", method = RequestMethod.GET)
    @ResponseBody
    List<String> getRole(@RequestParam("teamName") String teamName) {
        LOGGER.info("method = getRole()");
        List<String> roleList = employeeService.getRolesForTeam(teamName);
        return roleList;
    }


    @ModelAttribute("teamNames")
    public Map<String, String> populateDepositDetailList() {
        Map<String, String> teamNames = employeeService.fetchTeamNames();
        teamNames.remove("Admin");
        return teamNames;
    }

    @RequestMapping(value = "/emp/myview/searchEmployee/{empId}", method = RequestMethod.GET)
    public String getAllEmployeeDetails(Model model) {
        List<Employee> employees = employeeService.getAllEmployeeDetails();
        model.addAttribute("employees", employees);
        return "EmployeeDetails";
    }

    @RequestMapping(value = "/emp/myview/searchEmployee/deleteEmployee.do", method = RequestMethod.POST)
    public String deleteEmployee(HttpServletRequest request, Model model) {
        String empId = request.getParameter("employeeId");
        employeeService.deleteEmployee(empId);
        return "EmployeeDetails";
    }

    @RequestMapping(value = "/emp/myview/updateEmployee/{empId}", method = RequestMethod.GET)
    public String searchEmployee(@PathVariable String empId, @RequestParam("team") String team, Model model) {
        Employee employee = employeeService.getEmployeeDetails(empId);
        List<String> roles = employeeService.getRolesForTeam(team);
        model.addAttribute("employeeRoleList", roles);
        model.addAttribute("updateEmpform", employee);
        return "UpdateEmployee";
    }

    @RequestMapping(value = "/emp/myview/updateEmployee/updateEmployeeRole.do", method = RequestMethod.POST)
    public String updateEmployee(@ModelAttribute("updateEmpform") Employee employee, Model model, BindingResult result) {
        employeeService.updateEmployee(employee);
        model.addAttribute("message", "updated role successfully");
        List<String> roles = employeeService.getRolesForTeam(employee.getEmployeeTeam());
        model.addAttribute("updateEmpform", employee);
        model.addAttribute("employeeRoleList", roles);
        return "UpdateEmployee";
    }
}