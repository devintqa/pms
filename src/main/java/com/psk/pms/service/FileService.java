package com.psk.pms.service;

import com.psk.exception.BulkUploadException;
import com.psk.pms.model.ExcelDetail;
import com.psk.pms.model.FileUpload;
import com.psk.pms.model.Item;

import java.io.IOException;
import java.util.List;

public interface FileService {

	void uploadFiles(FileUpload fileUpload, String employeeId) throws IOException;

	ExcelDetail saveProjectDescription(FileUpload fileUpload, String employeeId)
			throws IOException, BulkUploadException;

	List<FileUpload> downloadFiles(FileUpload fileUpload, String employeeId);

	void deleteFile(String filePath);

    ExcelDetail saveProjectItemDescription(Item item) throws IOException, BulkUploadException;
}
