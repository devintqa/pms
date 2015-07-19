package com.psk.pms.service;

import com.mysql.jdbc.StringUtils;
import com.psk.pms.model.FileUpload;
import com.psk.pms.model.ProjDescDetail;
import com.psk.pms.model.ProjectDetail;
import com.psk.pms.model.SubProjectDetail;
import org.apache.log4j.Logger;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class FileServiceImpl implements FileService {

    @Autowired
    private ProjectService projectService;

    @Autowired
    private SubProjectService subProjectService;

    private static final Logger LOGGER = Logger.getLogger(FileServiceImpl.class);
    
    @Override
    public void saveProjectDescription(FileUpload fileUpload) throws IOException{
        String saveDirectory;
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
                try
                {
                    FileInputStream file = new FileInputStream(new File(saveDirectory + multipartFile.getOriginalFilename()));
                    List<ProjDescDetail> detailList = new ArrayList<ProjDescDetail>();
                    //Create Workbook instance holding reference to .xlsx file
                    XSSFWorkbook workbook = new XSSFWorkbook(file);
         
                    //Get first/desired sheet from the workbook
                    XSSFSheet sheet = workbook.getSheetAt(0);
         
                    //Iterate through each rows one by one
                    Iterator<Row> rowIterator = sheet.iterator();
                    while (rowIterator.hasNext())
                    {
                        Row row = rowIterator.next();
                        ProjDescDetail projDescDetail = new ProjDescDetail();
                        if(row.getRowNum()==0){
                            continue; //just skip the rows if row number is 1 or 2
                         }
                        //For each row, iterate through all the columns
                        Iterator<Cell> cellIterator = row.cellIterator();
                         
                        while (cellIterator.hasNext())
                        {
                            Cell cell = cellIterator.next();
                            //Check the cell type and format accordingly
                            switch (cell.getCellType())
                            {
                                case Cell.CELL_TYPE_NUMERIC:
                                	if (cell.getColumnIndex() == 0 && row.getCell(1) != null)
                                    {	
                                		if(cell.getColumnIndex() == 0)projDescDetail.setSerialNumber(String.valueOf(cell.getNumericCellValue()));
                                    }
                                	if (cell.getColumnIndex() == 2 && row.getCell(3) != null)
                                    {	
                                		if(cell.getColumnIndex() == 2)projDescDetail.setQuantityInFig(String.valueOf(cell.getNumericCellValue()));
                                    }
                                	break;
                                case Cell.CELL_TYPE_STRING:	
                                		if(cell.getColumnIndex() == 0 && row.getCell(1) != null)projDescDetail.setSerialNumber(cell.getStringCellValue());
                                		if(cell.getColumnIndex() == 1)projDescDetail.setWorkType(cell.getStringCellValue());
                                		if(cell.getColumnIndex() == 3)projDescDetail.setQuantityInWords(cell.getStringCellValue());
                                		if(cell.getColumnIndex() == 4)projDescDetail.setDescription(cell.getStringCellValue());
                                		if(cell.getColumnIndex() == 5)projDescDetail.setAliasDescription(cell.getStringCellValue());
                                    break;
                            }
                        }
                        if (!StringUtils.isNullOrEmpty(projDescDetail.getSerialNumber()))
                        {
                    		detailList.add(projDescDetail);
                        }          
                    }
                    for(ProjDescDetail descDetail : detailList){
                    	LOGGER.info(descDetail.getSerialNumber());
                    	LOGGER.info(descDetail.getWorkType());
                    	LOGGER.info(descDetail.getQuantityInFig());
                    	LOGGER.info(descDetail.getQuantityInWords());
                    	LOGGER.info(descDetail.getDescription());
                    	LOGGER.info(descDetail.getAliasDescription());
                    }
                    file.close();
                }
                catch (Exception e)
                {
                    e.printStackTrace();
                }
                
            }
        }
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
