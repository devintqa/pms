package com.psk.pms.service;

import com.psk.pms.Constants;
import com.psk.pms.builder.ProjectDescriptionDetailBuilder;
import com.psk.pms.model.ExcelDetail;
import com.psk.pms.model.FileUpload;
import com.psk.pms.model.ProjectDetail;
import com.psk.pms.model.SubProjectDetail;
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

    private static final Logger LOGGER = Logger.getLogger(FileServiceImpl.class);
    
    @Override
    public ExcelDetail saveProjectDescription(FileUpload fileUpload) throws IOException{
        String saveDirectory;
        ExcelDetail excelDetail = new ExcelDetail();
        ProjectDetail projectDetail = projectService.getProjectDocument(fileUpload.getAliasProjectName());
        LOGGER.info("method = uploadFiles() , Alias Project Name" + projectDetail.getAliasName());
        if (fileUpload.isSubProjectUpload()) {
            SubProjectDetail subProjDetail = subProjectService.getSubProjectDocument(fileUpload.getAliasSubProjectName());
            saveDirectory = "C:/PMS/" + projectDetail.getAliasName() + "/" + subProjDetail.getAliasSubProjName() + "/";
        } else {
            saveDirectory = "C:/PMS/" + projectDetail.getAliasName() + "/";
        }
        List<MultipartFile> pmsFiles = fileUpload.getFiles();
        if (null != pmsFiles && pmsFiles.size() > 0) {
            for (MultipartFile multipartFile : pmsFiles) {
            	String path = saveDirectory + multipartFile.getOriginalFilename();
            	boolean isExcel = isExcelType(path);
            	if(!isExcel){
            		excelDetail.setExcel(false);
            		return excelDetail;
            	}
            	projectDescriptionDetailBuilder.buildDescDetailList(saveDirectory, multipartFile);          
            }
        }
        return excelDetail;
    }
    
	private boolean isExcelType(String pathText) {
		try {
	    Path path = Paths.get(pathText);
	    String type = Files.probeContentType(path);
	    LOGGER.info("The uploaded file type" + type);
	    if(Constants.EXCEL_FILE_TYPE.equalsIgnoreCase(type)){
	    	LOGGER.info("Inside if type path");
	    	return true;
	    }
		} catch(Exception e){
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

        List<MultipartFile> pmsFiles = fileUpload.getFiles();
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

    @Override
    public List<FileUpload> downloadFiles(FileUpload downloadForm) {
        String path = null;
        String fileName;
        List<FileUpload> projectFileList = new ArrayList<FileUpload>();
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
}
