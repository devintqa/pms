package com.psk.pms.controller;

import com.google.gson.Gson;
import com.psk.exception.BulkUploadException;
import com.psk.pms.Constants;
import com.psk.pms.model.Employee;
import com.psk.pms.model.ExcelDetail;
import com.psk.pms.model.FileUpload;
import com.psk.pms.service.FileService;
import com.psk.pms.service.ProjectDescriptionService;
import com.psk.pms.validator.FileUploadValidator;
import org.apache.log4j.Logger;
import org.apache.poi.util.IOUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.net.URL;
import java.util.List;
import java.util.Map;

@Controller
public class FileUploadController extends BaseController {

	@Autowired
	private ServletContext context;

	@Autowired
	private FileUploadValidator fileUploadValidator;

	@Autowired
	private FileService fileService;

	@Autowired
	private ProjectDescriptionService projectDescriptionService;

	private static final Logger LOGGER = Logger.getLogger(FileUploadController.class);

	@RequestMapping(value = "/emp/myview/uploadFile/{employeeId}", method = RequestMethod.GET)
	public String pmsDisplayForm(@PathVariable String employeeId, Model model) {
		LOGGER.info("Into File Upload");
		FileUpload fileUpload = new FileUpload();
		fileUpload.setEmployeeId(employeeId);
		model.addAttribute("uploadForm", fileUpload);
		Map < String, String > aliasProjectList = populateAliasProjectList();
		if (aliasProjectList.size() == 0) {
			model.addAttribute("noProjectCreated",
				"No Project Found To Be Created. Please Create a Project.");
			return "Welcome";
		} else {
			model.addAttribute("aliasProjectList", aliasProjectList);
		}
		Employee employee = new Employee();
		employee.setEmployeeId(employeeId);
		model.addAttribute("employee", employee);
		return "UploadFile";
	}

	@RequestMapping(value = "/emp/myview/uploadExcel/{employeeId}", method = RequestMethod.GET)
	public String pmsExcelUploadForm(@PathVariable String employeeId,
	Model model) {
		LOGGER.info("Into Excel Upload");
		FileUpload fileUpload = new FileUpload();
		fileUpload.setEmployeeId(employeeId);
		model.addAttribute("uploadForm", fileUpload);
		Map < String, String > aliasProjectList = populateAliasProjectList();
		if (aliasProjectList.size() == 0) {
			model.addAttribute("noProjectCreated",
				"No Project Found To Be Created. Please Create a Project.");
			return "Welcome";
		} else {
			model.addAttribute("aliasProjectList", aliasProjectList);
		}
		Employee employee = new Employee();
		employee.setEmployeeId(employeeId);
		model.addAttribute("employee", employee);
		return "UploadExcel";
	}

	@RequestMapping(value = "/emp/myview/uploadFile/getSubAliasProject.do", method = RequestMethod.GET)@ResponseBody
	public String getSubAliasProject(HttpServletRequest request,
	HttpServletResponse response) {
		return getAliasSubProjectNames(request);
	}

	@RequestMapping(value = "/emp/myview/uploadExcel/getSubAliasProject.do", method = RequestMethod.GET)@ResponseBody
	public String getSubAliasProjectForExcelUpload(HttpServletRequest request,
	HttpServletResponse response) {
		return getAliasSubProjectNames(request);
	}

	@RequestMapping(value = "/emp/myview/downloadFile/getSubAliasProject.do", method = RequestMethod.GET)@ResponseBody
	public String getSubAliasProjectDownload(HttpServletRequest request,
	HttpServletResponse response) {
		return getAliasSubProjectNames(request);
	}

	private String getAliasSubProjectNames(HttpServletRequest request) {
		LOGGER.info("Alias Project Name" + request.getParameter("aliasProjectName"));
		Map < String, String > subAliasProjectList = populateSubAliasProjectList(request.getParameter("aliasProjectName"));
		subAliasProjectList.put("0", "--Please Select--");
		Gson gson = new Gson();
		String subAliasProjectJson = gson.toJson(subAliasProjectList);
		return subAliasProjectJson;
	}

	@RequestMapping(value = "/emp/myview/uploadFile/saveFiles.do", method = RequestMethod.POST)
	public String fileSave(@ModelAttribute("uploadForm") FileUpload uploadForm,
	BindingResult result, Model map) throws IllegalStateException,
	IOException {
		Map < String, String > aliasProjectList = populateAliasProjectList();
		Map < String, String > subAliasProjectList = populateSubAliasProjectList(uploadForm.getAliasProjectName());
		fileUploadValidator.validate(uploadForm, result);
		if (!result.hasErrors()) {
			fileService.uploadFiles(uploadForm);
		} else {
			if (uploadForm.getFiles().size() == 0) {
				map.addAttribute("fileAdditionSuccessful", "Please select one or more files");
			}
			populateAliasProjectAndSubprojectAlias(map, aliasProjectList, subAliasProjectList);
			return "UploadFile";
		}
		subAliasProjectList.put("0", "--Please Select--");
		map.addAttribute("subAliasProjectList", subAliasProjectList);
		map.addAttribute("aliasProjectList", aliasProjectList);
		map.addAttribute("fileAdditionSuccessful", "Files have got uploaded successfully");
		return "UploadFile";
	}

	@RequestMapping(value = "/emp/myview/uploadExcel/saveProjectDescription.do", method = RequestMethod.POST)
	public String saveProjectDescription(@ModelAttribute("uploadForm") FileUpload uploadForm,
	BindingResult result, Model map) throws IllegalStateException,
	IOException {
		Map < String, String > aliasProjectList = populateAliasProjectList();
		Map < String, String > subAliasProjectList = populateSubAliasProjectList(uploadForm.getAliasProjectName());
		fileUploadValidator.validate(uploadForm, result);
		if (!result.hasErrors()) {
			fileService.uploadFiles(uploadForm);
			ExcelDetail excelDetail = null;
			try {
				excelDetail = fileService.saveProjectDescription(uploadForm);
			} catch (BulkUploadException e) {
				map.addAttribute("uploadProjectDescriptionFailed", String.format("%s%s", Constants.UPLOADFAILED, e.getMessage()));
				populateAliasProjectAndSubprojectAlias(map, aliasProjectList, subAliasProjectList);
				return "UploadExcel";
			}
			if (!excelDetail.isExcel()) {
				map.addAttribute("fileAdditionSuccessful", "Please Select Valid File Format");
				populateAliasProjectAndSubprojectAlias(map, aliasProjectList, subAliasProjectList);
				return "UploadExcel";
			}
		} else {
			if (uploadForm.getFiles().size() == 0) {
				map.addAttribute("fileAdditionSuccessful",
					"Please select one or more files");
			}
			populateAliasProjectAndSubprojectAlias(map, aliasProjectList, subAliasProjectList);
			return "UploadExcel";
		}
		subAliasProjectList.put("0", "--Please Select--");
		map.addAttribute("subAliasProjectList", subAliasProjectList);
		map.addAttribute("aliasProjectList", aliasProjectList);
		map.addAttribute("fileAdditionSuccessful", "Project Description Creation Successful");
		return "UploadExcel";
	}

	private void populateAliasProjectAndSubprojectAlias(Model map,
	Map < String, String > aliasProjectList,
	Map < String, String > subAliasProjectList) {
		map.addAttribute("aliasProjectList", aliasProjectList);
		subAliasProjectList.put("0", "--Please Select--");
		map.addAttribute("subAliasProjectList", subAliasProjectList);
		map.addAttribute("aliasProjectList", aliasProjectList);
	}

	@RequestMapping(value = "/emp/myview/downloadFile/{employeeId}", method = RequestMethod.GET)
	public String pmsDownloadFile(@PathVariable String employeeId, Model model) {
		LOGGER.info("Into File Download");
		FileUpload fileUpload = new FileUpload();
		fileUpload.setEmployeeId(employeeId);
		model.addAttribute("downloadForm", fileUpload);
		Map < String, String > aliasProjectList = populateAliasProjectList();
		if (aliasProjectList.size() == 0) {
			model.addAttribute("noProjectCreated",
				"No Project Found To Be Created. Please Create a Project.");
			return "Welcome";
		} else {
			model.addAttribute("aliasProjectList", aliasProjectList);
		}
		Employee employee = new Employee();
		employee.setEmployeeId(employeeId);
		model.addAttribute("employee", employee);
		return "DownloadFile";
	}

	@RequestMapping(value = "/emp/myview/downloadFile/getFiles.do", method = RequestMethod.POST)
	public String fileDownload(@ModelAttribute("downloadForm") FileUpload downloadForm,
	BindingResult result, Model map) throws IllegalStateException,
	IOException {
		Map < String, String > aliasProjectList = populateAliasProjectList();
		Map < String, String > subAliasProjectList = populateSubAliasProjectList(downloadForm.getAliasProjectName());
		populateAliasProjectAndSubprojectAlias(map, aliasProjectList,
		subAliasProjectList);
		fileUploadValidator.validate(downloadForm, result);
		if (!result.hasErrors()) {
			fileService.downloadFiles(downloadForm);
			List < FileUpload > projectFileList = fileService.downloadFiles(downloadForm);
			map.addAttribute("projectFileList", projectFileList);
			map.addAttribute("projectFileSize", projectFileList.size());
			return "DownloadFile";
		} else {
			return "DownloadFile";
		}
	}

	@RequestMapping(value = "/emp/myview/downloadFile/downloadFiles.web", method = RequestMethod.GET)
	public@ResponseBody void downloadFiles(@RequestParam(value = "path", required = true) String path,
	HttpServletRequest request, HttpServletResponse response) {
		LOGGER.info("download file" + path);
		File downloadFile = new File(path);
		FileInputStream inputStream = null;
		OutputStream outStream = null;

		try {
			inputStream = new FileInputStream(downloadFile);

			response.setContentLength((int) downloadFile.length());
			response.setContentType(context.getMimeType(path));

			// response header
			String headerKey = "Content-Disposition";
			String headerValue = String.format("attachment; filename=\"%s\"", downloadFile.getName());
			response.setHeader(headerKey, headerValue);

			// Write response
			outStream = response.getOutputStream();
			IOUtils.copy(inputStream, outStream);

		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {
				if (null != inputStream) inputStream.close();
				if (null != inputStream) outStream.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}

	@RequestMapping(value = "/emp/myview/downloadTemplate.do", method = RequestMethod.GET)
	public void getTemplate(HttpServletRequest request,
	HttpServletResponse response) {
		ClassLoader classLoader = this.getClass().getClassLoader();
		URL url = null;
		File file = null;
		FileInputStream fStream = null;
		try {
			url = classLoader.getResource("ProjectDescription.xlsx");
			file = new File(url.getPath());
			fStream = new FileInputStream(file);
			String headerValue = String.format("attachment; filename=\"%s\"",
			file.getName());
			response.setHeader("Content-Disposition", headerValue);
			response.setContentLength((int) file.length());
			response.setContentType(context.getMimeType(url.getPath()));
			org.apache.commons.io.IOUtils.copy(fStream,
			response.getOutputStream());
			response.flushBuffer();
		} catch (Exception e) {
			e.printStackTrace();
		}

	}

    @RequestMapping(value = "/emp/myview/downloadItemDescTemplate.do", method = RequestMethod.GET)
    public void getItemDescriptionTemplate(HttpServletRequest request,
                            HttpServletResponse response) {
        ClassLoader classLoader = this.getClass().getClassLoader();
        URL url = null;
        File file = null;
        FileInputStream fStream = null;
        try {
            url = classLoader.getResource("ItemDescription.xlsx");
            file = new File(url.getPath());
            fStream = new FileInputStream(file);
            String headerValue = String.format("attachment; filename=\"%s\"",
                    file.getName());
            response.setHeader("Content-Disposition", headerValue);
            response.setContentLength((int) file.length());
            response.setContentType(context.getMimeType(url.getPath()));
            org.apache.commons.io.IOUtils.copy(fStream,
                    response.getOutputStream());
            response.flushBuffer();
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

	@RequestMapping(value = "/emp/myview/downloadFile/deleteFile.do", method = RequestMethod.POST)
	public void deleteFile(@RequestParam(value = "path", required = true) String path,
	HttpServletRequest request, HttpServletResponse response) {
		LOGGER.info("method = deleteFile() ,file Name :" + path);
		fileService.deleteFile(path);
	}

	@RequestMapping(value = "/emp/myview/uploadExcel/checkProjectDesc.do", method = RequestMethod.GET)@ResponseBody
	public String checkProjectDescriptionAlreadyExistForProject(
	HttpServletRequest request, HttpServletResponse response, @RequestParam("projectId") String projectId
	,@RequestParam("governmentEst") String governmentEst) {
		int projId = Integer.parseInt(projectId);
		LOGGER.info("method = checkProjectDescriptionAlreadyExistForProject , governmentEst  :" + governmentEst);
		LOGGER.info("method = checkProjectDescriptionAlreadyExistForProject , project Id  :" + projId);
		if (projectDescriptionService.isProjectDescriptionDetailsExistsForProject(projId, governmentEst)) {
			return Constants.EXISTS;
		} else {
			return Constants.DOESNOTEXISTS;
		}
	}

	@RequestMapping(value = "/emp/myview/uploadExcel/checkSubProjectDesc.do", method = RequestMethod.GET)@ResponseBody
	public String checkProjectDescriptionAlreadyExistForSubProject(
	HttpServletRequest request, HttpServletResponse response, @RequestParam("subProjectId") String subProjectId
	,@RequestParam("governmentEst") String governmentEst) {
		int subProjId = Integer.parseInt(subProjectId);
		LOGGER.info("method = checkProjectDescriptionAlreadyExistForSubProject , governmentEst  :" + governmentEst);
		LOGGER.info("method = checkProjectDescriptionAlreadyExistForSubProject , sub project Id  :" + subProjId);
		if (projectDescriptionService.isProjectDescriptionDetailsExistsForSubProject(subProjId, governmentEst)) {
			return Constants.EXISTS;
		} else {
			return Constants.DOESNOTEXISTS;
		}
	}

}