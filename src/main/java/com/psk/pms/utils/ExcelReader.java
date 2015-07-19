package com.psk.pms.utils;

import java.io.File;
import java.io.FileInputStream;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import com.mysql.jdbc.StringUtils;
import com.psk.pms.model.ProjDescDetail;

public class ExcelReader {
	
	public static void main(String[] args)
    {
        try
        {
            FileInputStream file = new FileInputStream(new File("C:/Users/Srividya/Desktop/Project_Description.xlsx"));
            List<ProjDescDetail> detailList = new ArrayList<ProjDescDetail>();
            String aliasProjectName = "";
            String subAliasProjectName = "";
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
                if(row.getRowNum()==1 || row.getRowNum()==2){
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
                        	if (row.getRowNum()>=3 && cell.getColumnIndex() == 0 && row.getCell(1) != null)
                            {	
                        		if(cell.getColumnIndex() == 0)projDescDetail.setSerialNumber(String.valueOf(cell.getNumericCellValue()));
                            }
                        	if (row.getRowNum()>=3 && cell.getColumnIndex() == 2 && row.getCell(3) != null)
                            {	
                        		if(cell.getColumnIndex() == 2)projDescDetail.setQuantityInFig(String.valueOf(cell.getNumericCellValue()));
                            }
                        	break;
                        case Cell.CELL_TYPE_STRING:
                        	if (row.getRowNum()==0)
                            {
                        		if(cell.getColumnIndex() == 1 && row.getCell(2) != null){
                        			aliasProjectName = cell.getStringCellValue();
                            		break;
                        		}
                        		if(cell.getColumnIndex() == 3 && row.getCell(4) != null){
                        			subAliasProjectName = cell.getStringCellValue();
                            		break;
                        		}
                        		
                            }
                        	if (row.getRowNum()>=3)
                            {	
                        		if(cell.getColumnIndex() == 0 && row.getCell(1) != null)projDescDetail.setSerialNumber(cell.getStringCellValue());
                        		if(cell.getColumnIndex() == 1)projDescDetail.setWorkType(cell.getStringCellValue());
                        		if(cell.getColumnIndex() == 3)projDescDetail.setQuantityInWords(cell.getStringCellValue());
                        		if(cell.getColumnIndex() == 4)projDescDetail.setDescription(cell.getStringCellValue());
                        		if(cell.getColumnIndex() == 5)projDescDetail.setAliasDescription(cell.getStringCellValue());
                            }
                            break;
                    }
                }
                if (row.getRowNum()>=3 && !StringUtils.isNullOrEmpty(projDescDetail.getSerialNumber()))
                {
            		detailList.add(projDescDetail);
                }          
            }
            for(ProjDescDetail descDetail : detailList){
            	System.out.println(aliasProjectName);
            	System.out.println(subAliasProjectName);
            	System.out.println(descDetail.getSerialNumber());
            	System.out.println(descDetail.getWorkType());
            	System.out.println(descDetail.getQuantityInFig());
            	System.out.println(descDetail.getQuantityInWords());
            	System.out.println(descDetail.getDescription());
            	System.out.println(descDetail.getAliasDescription());
            }
            file.close();
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
    }

}
