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

	@RequestMapping(value = "/emp/myview/buildProject/{empId}", method = RequestMethod.GET)
	public String buildProject(@PathVariable String empId, Model model) {
		ProjectDetail projectDetail = new ProjectDetail();
		projectDetail.setEmpId(empId);
		model.addAttribute("createProjectForm", projectDetail);
		Employee employee = new Employee();
		employee.setEmpId(empId);
		model.addAttribute("employee", employee);
		return "BuildProject";
	}
	
}