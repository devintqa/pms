package com.psk.pms.builder;

import com.psk.exception.BulkUploadException;
import com.psk.pms.Constants;
import com.psk.pms.model.ItemRateDescription;
import com.psk.pms.model.SheetNames;
import org.apache.log4j.Logger;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/**
 * Created by Sony on 18-08-2015.
 */
public class ItemRateDescriptionBuilder {

    private static final Logger LOGGER = Logger
            .getLogger(ItemRateDescriptionBuilder.class);

    public List<ItemRateDescription> buildItemRateDescription(String saveDirectory,
                                                              MultipartFile multipartFile) throws IOException, BulkUploadException {
        List<ItemRateDescription> itemRateDescriptions = new ArrayList<ItemRateDescription>();
        FileInputStream fileInputStream = null;
        try {
            String path = saveDirectory + multipartFile.getOriginalFilename();
            File file = new File(path);
            fileInputStream = new FileInputStream(file);
            XSSFWorkbook workbook = new XSSFWorkbook(fileInputStream);
            for (SheetNames sheetName : SheetNames.values()) {
                XSSFSheet sheet = workbook.getSheet(sheetName.getSheetName());
                Iterator<Row> rowIterator = sheet.iterator();
                while (rowIterator.hasNext()) {
                    Row row = rowIterator.next();
                    ItemRateDescription itemRateDescription = new ItemRateDescription();
                    if (row.getRowNum() == 0) {
                        continue;
                    }
                    Iterator<Cell> cellIterator = row.cellIterator();
                    if (sheetName == SheetNames.MATERIAL_RATES) {
                        while (cellIterator.hasNext()) {
                            itemRateDescription = getItemDescription(row, itemRateDescription, cellIterator, Constants.MATERIAL);
                        }

                    }
                    if (sheetName == SheetNames.LABOUR_RATES) {
                        while (cellIterator.hasNext()) {
                            itemRateDescription = getItemDescription(row, itemRateDescription, cellIterator, Constants.LABOUR);
                        }
                    }
                    if (sheetName == SheetNames.MATERIAL_RATES) {
                        while (cellIterator.hasNext()) {
                            itemRateDescription = getItemDescription(row, itemRateDescription, cellIterator, Constants.MACHINERY);
                        }
                    }
                    if (sheetName == SheetNames.OTHER_RATES) {
                        while (cellIterator.hasNext()) {
                            itemRateDescription = getItemDescription(row, itemRateDescription, cellIterator, Constants.OTHER);
                        }
                    }
                    itemRateDescriptions.add(itemRateDescription);
                }
            }


        } catch (Exception e) {
            LOGGER.error("Exception while reading excel", e);
            throw new BulkUploadException("Error in Uploading the file");
        } finally {
            if (fileInputStream != null) {
                fileInputStream.close();
            }
        }
        return itemRateDescriptions;
    }

    private ItemRateDescription getItemDescription(Row row, ItemRateDescription itemRateDescription,
                                                   Iterator<Cell> cellIterator, String workType) {
        Cell cell = cellIterator.next();
        switch (cell.getCellType()) {
            case Cell.CELL_TYPE_NUMERIC:
                if (cell.getColumnIndex() == 4
                        && row.getCell(1) != null) {
                    if (cell.getColumnIndex() == 4)
                        itemRateDescription
                                .setItemRate(String.valueOf(cell
                                        .getNumericCellValue()));
                }

                if (cell.getColumnIndex() == 0
                        && row.getCell(1) != null) {
                    if (cell.getColumnIndex() == 0)
                        itemRateDescription
                                .setScheduleItemNumber(String.valueOf(cell
                                        .getNumericCellValue()));
                }

                break;
            case Cell.CELL_TYPE_STRING:
                if (cell.getColumnIndex() == 0
                        && row.getCell(1) != null)
                    itemRateDescription.setScheduleItemNumber(cell
                            .getStringCellValue());
                if (cell.getColumnIndex() == 1)
                    itemRateDescription.setItemDescription(cell
                            .getStringCellValue());
                if (cell.getColumnIndex() == 2)
                    itemRateDescription.setItemName(cell
                            .getStringCellValue());
                if (cell.getColumnIndex() == 3)
                    itemRateDescription.setItemUnit(cell
                            .getStringCellValue());
                if (cell.getColumnIndex() == 4)
                    itemRateDescription.setItemRate(cell
                            .getStringCellValue());
                break;
        }
        itemRateDescription.setWorkType(workType);
        return itemRateDescription;
    }
}
