package com.psk.pms.controller;

import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

public class IndentController {

	 @RequestMapping(value = "/emp/myview/indent/create.do", method = RequestMethod.GET)
	    public String createIndent(	@RequestParam(value = "employeeId") int employeeId,
	    							@RequestParam(value = "projectId") int projectId, 
	    							@RequestParam(value = "subProjectId") int subProjectId,
	    							@RequestParam(value = "projDescs") String projDescs,
	    							Model model) {

	        return "CreateIndent";
	    }
}
