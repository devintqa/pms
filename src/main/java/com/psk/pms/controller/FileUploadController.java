package com.psk.pms.controller;
 
import com.google.gson.Gson;
import com.psk.pms.model.Employee;
import com.psk.pms.model.FileUpload;
import com.psk.pms.model.ProjectDetail;
import com.psk.pms.model.SubProjectDetail;
import com.psk.pms.service.ProjectService;
import com.psk.pms.validator.FileUploadValidator;
import org.apache.log4j.Logger;
import org.apache.poi.util.IOUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
 
@Controller
public class FileUploadController {
	
	@Autowired
	ProjectService projectService;
	
	@Autowired
    ServletContext context;

	@Autowired
	FileUploadValidator fileUploadValidator;

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
    	return getAliasSubProjectNames(request);
	}
    
    @RequestMapping(value = "/emp/myview/downloadFile/getSubAliasProject.do", method = RequestMethod.GET)
	@ResponseBody 
	public String getSubAliasProjectDownload(HttpServletRequest request, HttpServletResponse response) {
    	return getAliasSubProjectNames(request);
	}
    
    private String getAliasSubProjectNames(HttpServletRequest request){
    	LOGGER.info("Alias Project Name" + request.getParameter("aliasProjectName"));
		Map<String, String> subAliasProjectList = populateSubAliasProjectList(request.getParameter("aliasProjectName"));
		subAliasProjectList.put("0", "--Please Select--");
		Gson gson = new Gson(); 
		String subAliasProjectJson = gson.toJson(subAliasProjectList); 
		return subAliasProjectJson;
    }
 
    @RequestMapping(value = "/emp/myview/uploadFile/saveFiles.do", method = RequestMethod.POST)
    public String fileSave(
            @ModelAttribute("uploadForm") FileUpload uploadForm
            ,BindingResult result,Model map) throws IllegalStateException, IOException {
    	File files = null;
    	String saveDirectory = null;
    	Map<String, String> aliasProjectList = populateAliasProjectList();
		Map<String, String> subAliasProjectList = populateSubAliasProjectList(uploadForm.getAliasProjectName());
		fileUploadValidator.validate(uploadForm, result);
		if(!result.hasErrors())
		{
			uploadfiel(uploadForm, map);
		}else
		{
			if(uploadForm.getFiles().size()==0)
			{
				map.addAttribute("fileAdditionSuccessful", "Please select one or more files");
			}
			map.addAttribute("aliasProjectList", aliasProjectList);
			subAliasProjectList.put("0", "--Please Select--");
			map.addAttribute("subAliasProjectList",subAliasProjectList);
			map.addAttribute("aliasProjectList", aliasProjectList);
			return "UploadFile";
		}


        map.addAttribute("aliasProjectList", aliasProjectList);
        map.addAttribute("fileAdditionSuccessful", "Files have got uploaded successfully");
        return "UploadFile";
    }

	private void uploadfiel(@ModelAttribute("uploadForm") FileUpload uploadForm, Model map) throws IOException {
		File files;
		String saveDirectory;ProjectDetail projectDetail = projectService.getProjectDocument(uploadForm.getAliasProjectName());
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
    }

    @RequestMapping(value = "/emp/myview/downloadFile/{employeeId}", method = RequestMethod.GET)
    public String pmsDownloadFile(@PathVariable String employeeId,
			Model model) {
    	LOGGER.info("Into File Download");
    	FileUpload fileUpload = new FileUpload();
    	fileUpload.setEmployeeId(employeeId);
		model.addAttribute("downloadForm", fileUpload);
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
        return "DownloadFile";
    }
    
	@RequestMapping(value = "/emp/myview/downloadFile/getFiles.do", method = RequestMethod.POST)
	public String fileDownload(
			@ModelAttribute("downloadForm") FileUpload downloadForm,BindingResult result, Model map)
			throws IllegalStateException, IOException {
		Map<String, String> aliasProjectList = populateAliasProjectList();
		Map<String,String> subAliasProjectList = populateSubAliasProjectList(downloadForm.getAliasProjectName());
		fileUploadValidator.validate(downloadForm, result);
		if(!result.hasErrors())
		{
			downloadFile(downloadForm, map, aliasProjectList);
			return "DownloadFile";
		}else
		{

			map.addAttribute("aliasProjectList", aliasProjectList);
			subAliasProjectList.put("0", "--Please Select--");
			map.addAttribute("subAliasProjectList",subAliasProjectList);
			map.addAttribute("aliasProjectList", aliasProjectList);
			return "DownloadFile";
		}


	}

	private void downloadFile(@ModelAttribute("downloadForm") FileUpload downloadForm, Model map, Map<String, String> aliasProjectList) {
		List<FileUpload> projectFileList = new ArrayList<FileUpload>();
		ProjectDetail projectDetail = projectService.getProjectDocument(downloadForm.getAliasProjectName());
		LOGGER.info("method = downloadFile(), Alias Project Name :" + projectDetail.getAliasName());

		String path = null;

		if(downloadForm.isSubProjectUpload()){
			SubProjectDetail subProjDetail = projectService.getSubProjectDocument(downloadForm.getAliasSubProjectName());
        	path = "C:\\PMS\\" + projectDetail.getAliasName() + "\\" + subProjDetail.getAliasSubProjName();
        	 Map<String, String> subAliasProjectList = populateSubAliasProjectList(downloadForm.getAliasProjectName());
        	map.addAttribute("subAliasProjectList", subAliasProjectList);
        }else{
        	path = "C:\\PMS\\" + projectDetail.getAliasName();
        }

		String fileName;
		File folder = new File(path);
		File[] listOfFiles = folder.listFiles();

		if(listOfFiles != null){
			for (int i = 0; i < listOfFiles.length; i++) {
				FileUpload fileUpload = new FileUpload();
				if (listOfFiles[i].isFile()) {
					fileName = listOfFiles[i].getName();
					fileUpload.setFileName(fileName);
					fileUpload.setFilePath(listOfFiles[i].getAbsolutePath());
					projectFileList.add(fileUpload);
					LOGGER.info("File name : " +fileName);
				}
			}
			map.addAttribute("projectFileList", projectFileList);
			map.addAttribute("projectFileSize", projectFileList.size());
		}
		map.addAttribute("aliasProjectList", aliasProjectList);
	}

	@RequestMapping(value = "/emp/myview/downloadFile/downloadFiles.web", method = RequestMethod.GET)
	public @ResponseBody void downloadFiles(@RequestParam(value="path", required=true) String path, HttpServletRequest request,
			HttpServletResponse response) {
		
		File downloadFile = new File(path);
		FileInputStream inputStream = null;
		OutputStream outStream = null;
		
		try {
			inputStream = new FileInputStream(downloadFile);
 
			response.setContentLength((int) downloadFile.length());
			response.setContentType(context.getMimeType(path));			
 
			// response header
			String headerKey = "Content-Disposition";
			String headerValue = String.format("attachment; filename=\"%s\"",downloadFile.getName());
			response.setHeader(headerKey, headerValue);
 
			// Write response
			outStream = response.getOutputStream();
			IOUtils.copy(inputStream, outStream);
 
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {
				if (null != inputStream)
					inputStream.close();
				if (null != inputStream)
					outStream.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
 
		}
 
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
