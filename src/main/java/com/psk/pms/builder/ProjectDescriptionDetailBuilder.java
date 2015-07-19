package com.psk.pms.builder;

import java.io.File;
import java.io.FileInputStream;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.apache.log4j.Logger;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.web.multipart.MultipartFile;

import com.mysql.jdbc.StringUtils;
import com.psk.pms.model.ProjDescDetail;

public class ProjectDescriptionDetailBuilder {
	
	private static final Logger LOGGER = Logger.getLogger(ProjectDescriptionDetailBuilder.class);
	
	public List<ProjDescDetail> buildDescDetailList(String saveDirectory, MultipartFile multipartFile){
        List<ProjDescDetail> detailList = new ArrayList<ProjDescDetail>();
        try
        {	
        	String path = saveDirectory + multipartFile.getOriginalFilename();
        	File file = new File(path);
            FileInputStream fileInputStream = new FileInputStream(file);
            //Create Workbook instance holding reference to .xlsx file
            XSSFWorkbook workbook = new XSSFWorkbook(fileInputStream);
            //Get first/desired sheet from the workbook
            XSSFSheet sheet = workbook.getSheetAt(0);
            //Iterate through each rows one by one
            Iterator<Row> rowIterator = sheet.iterator();
            while (rowIterator.hasNext())
            {
                Row row = rowIterator.next();
                ProjDescDetail projDescDetail = new ProjDescDetail();
                if(row.getRowNum()==0){
                    continue;
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
            fileInputStream.close();
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
        return detailList;
	}

}
