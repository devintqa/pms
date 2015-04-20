package com.psk.pms.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.psk.pms.model.Employee;
import com.psk.pms.model.ProjectDetail;
import com.psk.pms.service.ProjectService;
import com.psk.pms.validator.ProjectDetailValidator;

@Controller
public class ProjectController {

	@Autowired
	ProjectDetailValidator projectDetailValidator;
	
	@Autowired
	ProjectService projectService;

	@RequestMapping(value = "/emp/myview/buildProject/{employeeId}", method = RequestMethod.GET)
	public String buildProject(@PathVariable String employeeId, Model model) {
		ProjectDetail projectDetail = new ProjectDetail();
		projectDetail.setEmployeeId(employeeId);
		model.addAttribute("createProjectForm", projectDetail);
		Employee employee = new Employee();
		employee.setEmployeeId(employeeId);
		model.addAttribute("employee", employee);
		return "BuildProject";
	}
	
}