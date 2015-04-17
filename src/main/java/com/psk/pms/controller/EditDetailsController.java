package com.psk.pms.controller;

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
import com.psk.pms.validator.SignUpValidator;

@Controller
public class EditDetailsController {
	
	@Autowired
	SignUpValidator signupValidator;
	@Autowired
	EmployeeService employeeService;
	
	@RequestMapping(value = "/emp/myview/details/edit/{empId}", method = RequestMethod.GET)
	public String editEmployeeDetails(@PathVariable String empId, Model model){
		Employee employee = employeeService.getEmployeeDetails(empId);
		model.addAttribute("employee", employee);
		return "EditDetails";
	}
	
	@RequestMapping(value = "/emp/myview/myhome/{empId}", method = RequestMethod.GET)
	public String getHomePage(@PathVariable String empId, Model model) {
		Employee employee = new Employee();
		String userRole = employeeService.getUserRole(empId);
		employee.setEmployeeId(empId);
		employee.setEmployeeRole(userRole);
		model.addAttribute("employee", employee);
		return "Welcome";
	}
	
	@RequestMapping(value = "/emp/myview/details/edit/editDetails.do", method = RequestMethod.POST)
    public String saveUpdateAction(
            @ModelAttribute("employee") Employee employee,
            BindingResult result, Model model, SessionStatus status) {
		boolean isUpdateSuccessful = false;
		isUpdateSuccessful = employeeService.updateEmployee(employee);
		System.out.println("isUpdateSuccessful : " + isUpdateSuccessful);
		status.setComplete();
		if(isUpdateSuccessful){
		model.addAttribute("employee", employee);
		model.addAttribute("updateSuccessMessage", "Employee Details updated successfully!");
		}
		return "EditDetails";
    }

}
