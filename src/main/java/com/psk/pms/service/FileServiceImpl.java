package com.psk.pms.service;

import com.psk.exception.BulkUploadException;
import com.psk.pms.Constants;
import com.psk.pms.builder.ItemRateDescriptionBuilder;
import com.psk.pms.builder.ProjectDescriptionDetailBuilder;
import com.psk.pms.dao.ItemDAO;
import com.psk.pms.factory.EmployeeTeam;
import com.psk.pms.factory.EmployeeTeamFactory;
import com.psk.pms.model.*;
import com.psk.pms.validator.BulkUploadDetailsValidator;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

public class FileServiceImpl implements FileService {


	@Autowired
	private SubProjectService subProjectService;

	@Autowired
	private ProjectDescriptionDetailBuilder projectDescriptionDetailBuilder;

	@Autowired
	private ItemRateDescriptionBuilder itemRateDescriptionBuilder;

	@Autowired
	private ItemDAO itemDAO;


    @Autowired
    EmployeeTeamFactory employeeTeamFactory;

    @Autowired
    EmployeeService employeeService;


	@Autowired
	private ProjectDescriptionService projectDescriptionService;

	@Autowired
	private BulkUploadDetailsValidator bulkUploadDetailsValidator;

	private static final Logger LOGGER = Logger.getLogger(FileServiceImpl.class);

	@Override
	public ExcelDetail saveProjectDescription(FileUpload fileUpload, String employeeId)
	throws IOException, BulkUploadException {
		String saveDirectory;
		ExcelDetail excelDetail = new ExcelDetail();
		ProjectDetail projectDetail = null;
		SubProjectDetail subProjDetail = null;
		List < MultipartFile > pmsFiles;
		
		boolean isSubProjectFileUpload = fileUpload.isSubProjectUpload();
        String aliasProjectName = fileUpload.getAliasProjectName();
        projectDetail = getProjectDocument(employeeId, aliasProjectName);
		LOGGER.info("method = uploadFiles() , Alias Project Name" + projectDetail.getAliasName());

		if (isSubProjectFileUpload) {
			subProjDetail = subProjectService.getSubProjectDocument(fileUpload.getAliasSubProjectName());
			saveDirectory = "C:/PMS/" + projectDetail.getAliasName() + "/" + subProjDetail.getAliasSubProjName() + "/";
		} else {
			saveDirectory = "C:/PMS/" + projectDetail.getAliasName() + "/";
		}

		pmsFiles = fileUpload.getFiles();
		
		String descType = fileUpload.isGovernmentEst()?Constants.GOVERNMENT:Constants.PSK;
		Integer projectId = projectDetail.getProjId();
		Integer subProjectId = isSubProjectFileUpload?subProjDetail.getSubProjId():null;
		
		if (null != pmsFiles && pmsFiles.size() > 0) {
			for (MultipartFile multipartFile: pmsFiles) {
				if (validateExcelSheet(saveDirectory, excelDetail, multipartFile)) return excelDetail;
				List < ProjDescDetail > descDetailList = projectDescriptionDetailBuilder.buildDescDetailList(saveDirectory, multipartFile);
				System.out.println("Description Details available: " + descDetailList.size());
				bulkUploadDetailsValidator.validateExtractedProjectDescriptionDetails(descDetailList);
				
				for (ProjDescDetail description: descDetailList) {
					description.setDescType(descType);
					description.setProjId(projectId);
					description.setSubProjId(subProjectId);
					description.setLastUpdatedBy(fileUpload.getEmployeeId());
				}
				System.out.println("encountered: " +descType);
				if (isSubProjectFileUpload) {
					projectDescriptionService.deleteAllTheDescriptionDetailsOfSubProject(subProjDetail.getSubProjId());
					populateSubProjectId(descDetailList, subProjDetail);
					projectDescriptionService.saveSubProjectDescriptionDetails(descDetailList);
				} else {
					if (fileUpload.isGovernmentEst()) {
						projectDescriptionService.deleteAllTheDescriptionDetailsOfProject(descType, projectDetail.getProjId());
						projectDescriptionService.saveGovProjectDescriptionDetails(descDetailList);
					} else {
						projectDescriptionService.deleteAllTheDescriptionDetailsOfProject(descType, projectDetail.getProjId());
						projectDescriptionService.saveProjectDescriptionDetails(descDetailList);
					}
				}
			}
		}
		return excelDetail;
	}

    private ProjectDetail getProjectDocument(String employeeId, String projectId) {
        Employee employee = employeeService.getEmployeeDetails(employeeId);
        EmployeeTeam employeeTeam = employeeTeamFactory.getEmployeeTeam(employee.getEmployeeTeam());
        return employeeTeam.getProjectDocument(projectId,employeeId);
    }

    private boolean validateExcelSheet(String saveDirectory, ExcelDetail excelDetail, MultipartFile multipartFile) {
		String path = saveDirectory + multipartFile.getOriginalFilename();
		boolean isExcel = isExcelType(path);
		if (!isExcel) {
			excelDetail.setExcel(false);
			return true;
		}
		return false;
	}

	private void populateSubProjectId(
	List < ProjDescDetail > extractedProjDescDetails,
	SubProjectDetail subProjectDetail) {
		for (ProjDescDetail extractedProjDescDetail: extractedProjDescDetails) {
			extractedProjDescDetail.setProjId(subProjectDetail.getProjId());
			extractedProjDescDetail.setSubProjId(subProjectDetail.getSubProjId());
		}
	}


	private boolean isExcelType(String pathText) {
		try {
			Path path = Paths.get(pathText);
			String type = Files.probeContentType(path);
			LOGGER.info("The uploaded file type" + type);
			if (Constants.EXCEL_FILE_TYPE.equalsIgnoreCase(type)) {
				LOGGER.info("Inside if type path");
				return true;
			}
		} catch (Exception e) {
			LOGGER.info("Error In Determining The Excel Type");
		}
		return false;
	}

	@Override
	public void uploadFiles(FileUpload fileUpload, String employeeId) throws IOException {
		File files;
		String saveDirectory;
		ProjectDetail projectDetail = getProjectDocument(employeeId, fileUpload.getAliasProjectName());
		LOGGER.info("method = uploadFiles() , Alias Project Name" + projectDetail.getAliasName());
		if (fileUpload.isSubProjectUpload()) {
			SubProjectDetail subProjDetail = subProjectService.getSubProjectDocument(fileUpload.getAliasSubProjectName());
			files = new File("C:\\PMS\\" + projectDetail.getAliasName() + "\\" + subProjDetail.getAliasSubProjName());
			saveDirectory = "C:/PMS/" + projectDetail.getAliasName() + "/" + subProjDetail.getAliasSubProjName() + "/";
		} else {
			files = new File("C:\\PMS\\" + projectDetail.getAliasName());
			saveDirectory = "C:/PMS/" + projectDetail.getAliasName() + "/";
		}
		createFileDirectory(files);
		List < MultipartFile > pmsFiles = fileUpload.getFiles();
		saveFiles(saveDirectory, pmsFiles);
	}

	private void createFileDirectory(File files) {
		try {
			if (!files.exists()) {
				if (files.mkdirs()) {
					LOGGER.info("Multiple directories are created!");
				} else {
					LOGGER.info("Failed to create multiple directories!");
				}
			}
		} catch (Exception e) {
			LOGGER.info("Something went wrong!!", e);
		}
	}

	private void saveFiles(String saveDirectory, List < MultipartFile > files) throws IOException {
		List < String > fileNames = new ArrayList < String > ();
		if (null != files && files.size() > 0) {
			for (MultipartFile multipartFile: files) {
				String fileName = multipartFile.getOriginalFilename();
				if (!"".equalsIgnoreCase(fileName)) {
					LOGGER.info("File Name: " + fileName);
					multipartFile.transferTo(new File(saveDirectory + fileName));
					fileNames.add(fileName);
				}
			}
		}
	}

	@Override
	public List < FileUpload > downloadFiles(FileUpload downloadForm, String employeeId) {
		String path = null;
		String fileName;
		List < FileUpload > projectFileList = new ArrayList < FileUpload > ();
		ProjectDetail projectDetail = getProjectDocument(employeeId, downloadForm.getAliasProjectName());
		LOGGER.info("method = downloadFile(), Alias Project Name :" + projectDetail.getAliasName());

		if (downloadForm.isSubProjectUpload()) {
			SubProjectDetail subProjDetail = subProjectService.getSubProjectDocument(downloadForm.getAliasSubProjectName());
			path = "C:\\PMS\\" + projectDetail.getAliasName() + "\\" + subProjDetail.getAliasSubProjName();
		} else {
			path = "C:\\PMS\\" + projectDetail.getAliasName();
		}

		File folder = new File(path);
		File[] listOfFiles = folder.listFiles();
		if (listOfFiles != null) {
			for (int i = 0; i < listOfFiles.length; i++) {
				FileUpload fileUpload = new FileUpload();
				if (listOfFiles[i].isFile()) {
					fileName = listOfFiles[i].getName();
					fileUpload.setFileName(fileName);
					fileUpload.setFilePath(listOfFiles[i].getAbsolutePath());
					projectFileList.add(fileUpload);
					LOGGER.info("File name : " + fileName);
				}
			}
		}
		return projectFileList;
	}

	@Override
	public void deleteFile(String filePath, String fileName,
			String aliasProjectName, String empId) {
		ProjectDetail projectDetail = getProjectDocument(empId, aliasProjectName);
		String str = null;
		str = filePath.substring(0, 2) + "\\"+"PMS"+ "\\"+projectDetail.getAliasName()+"\\"+fileName;
		LOGGER.info("Constructed Path is ::::  "+str);
		File file = new File(str);
		if (file.exists()) {
			LOGGER.info("File exists and proceeding to delete ");
			file.delete();
		}
	}

	@Override
	public ExcelDetail saveProjectItemDescription(Item item) throws IOException, BulkUploadException {
		String directory = "C:/PMS/ItemDescriptions/";
		ExcelDetail excelDetail = new ExcelDetail();
		File files;
		files = new File("C:\\PMS\\ItemDescriptions");
		createFileDirectory(files);
		List < MultipartFile > itemFiles = item.getFiles();
		saveFiles(directory, itemFiles);
		for (MultipartFile file: itemFiles) {
			if (validateExcelSheet(directory, excelDetail, file)) return excelDetail;
			List < ItemRateDescription > itemRateDescriptions = itemRateDescriptionBuilder.buildItemRateDescription(directory, file);
			bulkUploadDetailsValidator.validateFields(itemRateDescriptions);
            itemDAO.deleteItemDescription();
			itemDAO.saveItemRateDescriptions(itemRateDescriptions);
		}
		return excelDetail;
	}
}