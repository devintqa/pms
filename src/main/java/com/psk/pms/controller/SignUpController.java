package com.psk.pms.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.support.SessionStatus;

import com.psk.pms.model.Employee;
import com.psk.pms.service.EmployeeService;
import com.psk.pms.validator.SignUpValidator;

@Controller
public class SignUpController {
	
	@Autowired
	SignUpValidator signupValidator;
	@Autowired
	EmployeeService employeeService;
	
	@RequestMapping(value = "/emp/signup", method = RequestMethod.GET)
	public String initForm(ModelMap model){ 
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
		if(!result.hasErrors()){
			boolean isEmployeeExisting = employeeService.isEmployeeExisting(employee.getEmpId()); 
			if(isEmployeeExisting){
				signupValidator.validateSignUp(employee, result, true);
				return "SignUp";
			}
			isSignUpSuccessful = employeeService.signupEmployee(employee);
		}
		if(result.hasErrors() || !isSignUpSuccessful) {
			return "SignUp";
		} else {
			status.setComplete();
			Employee emp = new Employee();
			model.addAttribute("employeeForm", emp);
			model.addAttribute("loginMessage", "Signup successful. Please login to continue.");
			return "SignIn";
		}
    }

}
