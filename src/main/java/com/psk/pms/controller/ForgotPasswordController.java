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
import com.psk.pms.validator.ForgotpasswordValidator;
import com.psk.pms.validator.ResetPasswordValidator;

@Controller
public class ForgotPasswordController {

	@Autowired
	ForgotpasswordValidator forgotPasswordValidator;
	@Autowired
	ResetPasswordValidator resetPasswordValidator;
	@Autowired
	EmployeeService employeeService;

	@RequestMapping(value = "/emp/forgotpassword", method = RequestMethod.GET)
	public String initForm(ModelMap model) {
		Employee employee = new Employee();
		model.addAttribute("forgotPasswordForm", employee);
		return "ForgotPassword";
	}

	@RequestMapping(value = "/emp/forgotpassword.do", method = RequestMethod.POST)
	public String userValidationWithDB(
			@ModelAttribute("forgotPasswordForm") Employee employee,
			BindingResult result, Model model, SessionStatus status) {
		boolean isMotherMaidenValid = false;
		forgotPasswordValidator.validate(employee, result);
		if (!result.hasErrors()) {
			boolean isEmployeeExisting = employeeService
					.isEmployeeExisting(employee.getEmployeeId());
			if (!isEmployeeExisting) {
				forgotPasswordValidator.validateForgotPwdEmpId(employee,
						result, true);
				return "ForgotPassword";
			} else {
				isMotherMaidenValid = employeeService
						.isEmployeeMotherMaidenExisting(
								employee.getEmployeeId(),
								employee.getEmployeeMotherMaidenName());
				if (!isMotherMaidenValid) {
					forgotPasswordValidator.validateForgotPwdMotherMaiden(
							employee, result, true);
					return "ForgotPassword";
				}
			}
		}
		if (result.hasErrors()) {
			return "ForgotPassword";
		} else {
			status.setComplete();
			model.addAttribute("ResetForm", employee);
			return "ResetPassword";
		}
	}

	@RequestMapping(value = "/emp/resetForm.do", method = RequestMethod.POST)
	public String saveRestPassword(
			@ModelAttribute("ResetForm") Employee employee,
			BindingResult result, Model model, SessionStatus status) {
		boolean isPwdResetSuccessful = false;
		resetPasswordValidator.validate(employee, result);

		if (!result.hasErrors()) {
			isPwdResetSuccessful = employeeService.resetPassword(
					employee.getEmployeeId(), employee.getEmployeePwd());
		}
		if (result.hasErrors() || !isPwdResetSuccessful) {

			return "ResetPassword";
		} else {
			status.setComplete();
			Employee emp = new Employee();
			model.addAttribute("employeeForm", emp);
			model.addAttribute("loginMessage",
					"Reset Password successful. Please login to continue.");
			return "SignIn";
		}
	}

}
