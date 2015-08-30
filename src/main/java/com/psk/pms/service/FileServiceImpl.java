package com.psk.pms.service;

import com.psk.exception.BulkUploadException;
import com.psk.pms.Constants;
import com.psk.pms.builder.ItemRateDescriptionBuilder;
import com.psk.pms.builder.ProjectDescriptionDetailBuilder;
import com.psk.pms.dao.ItemDAO;
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
	private ProjectService projectService;

	@Autowired
	private SubProjectService subProjectService;

	@Autowired
	private ProjectDescriptionDetailBuilder projectDescriptionDetailBuilder;

	@Autowired
	private ItemRateDescriptionBuilder itemRateDescriptionBuilder;

	@Autowired
	private ItemDAO itemDAO;


	@Autowired
	private ProjectDescriptionService projectDescriptionService;

	@Autowired
	private BulkUploadDetailsValidator bulkUploadDetailsValidator;

	private static final Logger LOGGER = Logger.getLogger(FileServiceImpl.class);

	@Override
	public ExcelDetail saveProjectDescription(FileUpload fileUpload)
	throws IOException, BulkUploadException {
		String saveDirectory;
		ExcelDetail excelDetail = new ExcelDetail();
		ProjectDetail projectDetail = null;
		SubProjectDetail subProjDetail = null;
		List < MultipartFile > pmsFiles;
		String sheetName = "";
		boolean isSubProjectFileUpload = fileUpload.isSubProjectUpload();

		projectDetail = projectService.getProjectDocument(fileUpload.getAliasProjectName());
		LOGGER.info("method = uploadFiles() , Alias Project Name" + projectDetail.getAliasName());

		if (isSubProjectFileUpload) {
			subProjDetail = subProjectService.getSubProjectDocument(fileUpload.getAliasSubProjectName());
			saveDirectory = "C:/PMS/" + projectDetail.getAliasName() + "/" + subProjDetail.getAliasSubProjName() + "/";
		} else {
			saveDirectory = "C:/PMS/" + projectDetail.getAliasName() + "/";
		}

		if (fileUpload.isGovernmentEst()) {
			LOGGER.info("Is Government Copy" + fileUpload.isGovernmentEst());
			sheetName = Constants.GOVERNMENT_PROJECT_DESCRIPTION;
		} else {
			sheetName = Constants.PSK_PROJECT_DESCRIPTION;
		}
		pmsFiles = fileUpload.getFiles();
		if (null != pmsFiles && pmsFiles.size() > 0) {
			for (MultipartFile multipartFile: pmsFiles) {
				if (validateExcelSheet(saveDirectory, excelDetail, multipartFile)) return excelDetail;
				LOGGER.info("Processing Sheet Name" + sheetName);
				List < ProjDescDetail > extractedProjDescDetails = projectDescriptionDetailBuilder.buildDescDetailList(saveDirectory, multipartFile, sheetName);
				LOGGER.info("Description Details available: " + extractedProjDescDetails.size());
				System.out.println("Description Details available: " + extractedProjDescDetails.size());
				bulkUploadDetailsValidator.validateExtractedProjectDescriptionDetails(extractedProjDescDetails);
				populateProjectDetail(extractedProjDescDetails, projectDetail, fileUpload.getEmployeeId());

				if (isSubProjectFileUpload) {
					projectDescriptionService.deleteAllTheDescriptionDetailsOfSubProject(subProjDetail.getSubProjId());
					populateSubProjectId(extractedProjDescDetails, subProjDetail);
					projectDescriptionService.saveSubProjectDescriptionDetails(extractedProjDescDetails);
				} else {
					if (fileUpload.isGovernmentEst()) {
						projectDescriptionService.saveProposalProjectDescriptionDetails(extractedProjDescDetails);
					} else {
						projectDescriptionService.deleteAllTheDescriptionDetailsOfProject(projectDetail.getProjId());
						projectDescriptionService.saveProjectDescriptionDetails(extractedProjDescDetails);
					}
				}
			}
		}
		return excelDetail;
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

	private void populateProjectDetail(
	List < ProjDescDetail > extractedProjDescDetails,
	ProjectDetail projectDetail, String employeeId) {
		for (ProjDescDetail extractedProjDescDetail: extractedProjDescDetails) {
			extractedProjDescDetail.setProjId(projectDetail.getProjId());
			extractedProjDescDetail.setLastUpdatedBy(employeeId);
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
	public void uploadFiles(FileUpload fileUpload) throws IOException {
		File files;
		String saveDirectory;
		ProjectDetail projectDetail = projectService.getProjectDocument(fileUpload.getAliasProjectName());
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
		} catch (Throwable e) {
			LOGGER.info("Something went wrong!!");
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
	public List < FileUpload > downloadFiles(FileUpload downloadForm) {
		String path = null;
		String fileName;
		List < FileUpload > projectFileList = new ArrayList < FileUpload > ();
		ProjectDetail projectDetail = projectService.getProjectDocument(downloadForm.getAliasProjectName());
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
	public void deleteFile(String filePath) {
		File file = new File(filePath);
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
			itemDAO.saveItemRateDescriptions(itemRateDescriptions);
		}
		return excelDetail;
	}
}