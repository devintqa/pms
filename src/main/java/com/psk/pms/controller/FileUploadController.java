package com.psk.pms.controller;
 
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
 
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import com.psk.pms.model.ProjectDetail;

import com.google.gson.Gson;
import com.psk.pms.model.Employee;
import com.psk.pms.model.FileUpload;
import com.psk.pms.model.SubProjectDetail;
import com.psk.pms.service.ProjectService;
 
@Controller
public class FileUploadController {
	
	@Autowired
	ProjectService projectService;

	private static final Logger LOGGER = Logger.getLogger(FileUploadController.class);
 
    @RequestMapping(value = "/emp/myview/uploadFile/{employeeId}", method = RequestMethod.GET)
    public String pmsDisplayForm(@PathVariable String employeeId, 
			Model model) {
    	LOGGER.info("Into File Upload");
    	FileUpload fileUpload = new FileUpload();
    	fileUpload.setEmployeeId(employeeId);
		model.addAttribute("uploadForm", fileUpload);
    	Map<String, String> aliasProjectList = populateAliasProjectList();
		if(aliasProjectList.size() == 0){
			model.addAttribute("noProjectCreated", "No Project Found To Be Created. Please Create a Project.");
			return "Welcome";
		} else{
			model.addAttribute("aliasProjectList", aliasProjectList);
		}
    	Employee employee = new Employee();
		employee.setEmployeeId(employeeId);
		model.addAttribute("employee", employee);
        return "UploadFile";
    }
    
    @RequestMapping(value = "/emp/myview/uploadFile/getSubAliasProject.do", method = RequestMethod.GET)
	@ResponseBody 
	public String getSubAliasProject(HttpServletRequest request, HttpServletResponse response) {
    	LOGGER.info("Alias Project Name" + request.getParameter("aliasProjectName"));
		Map<String, String> subAliasProjectList = populateSubAliasProjectList(request.getParameter("aliasProjectName"));
		subAliasProjectList.put("0", "--Please Select--");
		Gson gson = new Gson(); 
		String subAliasProjectJson = gson.toJson(subAliasProjectList); 
		return subAliasProjectJson;
	}
 
    @RequestMapping(value = "/emp/myview/uploadFile/saveFiles.do", method = RequestMethod.POST)
    public String fileSave(
            @ModelAttribute("uploadForm") FileUpload uploadForm,
            Model map) throws IllegalStateException, IOException {
    	File files = null;
    	String saveDirectory = null;
    	Map<String, String> aliasProjectList = populateAliasProjectList();
        ProjectDetail projectDetail = projectService.getProjectDocument(uploadForm.getAliasProjectName());
        LOGGER.info("Alias Project Name" + projectDetail.getAliasName());
        if(uploadForm.isSubProjectUpload()){
        	SubProjectDetail subProjDetail = projectService.getSubProjectDocument(uploadForm.getAliasSubProjectName());
            files = new File("C:\\PMS\\" + projectDetail.getAliasName() + "\\" + subProjDetail.getAliasSubProjName());
            saveDirectory = "C:/PMS/" + projectDetail.getAliasName() + "/" + subProjDetail.getAliasSubProjName() + "/";
            Map<String, String> subAliasProjectList = populateSubAliasProjectList(uploadForm.getAliasProjectName());
			map.addAttribute("subAliasProjectList", subAliasProjectList);
        }else{
            files = new File("C:\\PMS\\" + projectDetail.getAliasName());
            saveDirectory = "C:/PMS/" + projectDetail.getAliasName() + "/";
        }
        try{
			if (!files.exists()) {
				if (files.mkdirs()) {
					LOGGER.info("Multiple directories are created!");
				} else {
					LOGGER.info("Failed to create multiple directories!");
				}
			}
		}catch(Throwable e){
			LOGGER.info("Something went wrong!!");
		}
		
        List<MultipartFile> pmsFiles = uploadForm.getFiles();
 
        List<String> fileNames = new ArrayList<String>();
 
        if (null != pmsFiles && pmsFiles.size() > 0) {
            for (MultipartFile multipartFile : pmsFiles) {
                String fileName = multipartFile.getOriginalFilename();
                if (!"".equalsIgnoreCase(fileName)) {
                	LOGGER.info("File Name: " + fileName);
                    multipartFile.transferTo(new File(saveDirectory + fileName));
                    fileNames.add(fileName);
                }
            }
        }
        map.addAttribute("aliasProjectList", aliasProjectList);
        map.addAttribute("fileAdditionSuccessful", "Files have got uploaded successfully");
        return "UploadFile";
    }
    
	public Map<String, String> populateSubAliasProjectList(String project) {
		Map<String, String> aliasSubProjectName = projectService.getSubAliasProjectNames(project);
		return aliasSubProjectName;
	}
	
	public Map<String, String> populateAliasProjectList() {
		Map<String, String> aliasProjectName = projectService.getAliasProjectNames();
		return aliasProjectName;
	}
	
}
