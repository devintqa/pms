package com.psk.pms.validator;

import com.psk.pms.model.FileUpload;

import org.apache.log4j.Logger;
import org.springframework.validation.Errors;
import org.springframework.validation.ValidationUtils;
import org.springframework.validation.Validator;
import org.springframework.web.multipart.MultipartFile;

import java.util.Iterator;

public class FileUploadValidator implements Validator {

	@SuppressWarnings("unused")
	private static final Logger LOGGER = Logger
			.getLogger(FileUploadValidator.class);

	@Override
	public boolean supports(@SuppressWarnings("rawtypes") Class clazz) {
		return FileUpload.class.isAssignableFrom(clazz);
	}

	@Override
	public void validate(Object target, Errors errors) {

		ValidationUtils.rejectIfEmptyOrWhitespace(errors, "aliasProjectName",
				"required.aliasProjectName",
				"Please Select Alias Project Name.");

		FileUpload fileUpload = (FileUpload) target;

		if ("0".equalsIgnoreCase(fileUpload.getAliasProjectName())) {
			errors.rejectValue("aliasProjectName", "required.aliasProjectName",
					"Please Select Alias Project Name");
		}

		if (fileUpload.isSubProjectUpload()) {
			ValidationUtils.rejectIfEmptyOrWhitespace(errors,
					"aliasSubProjectName", "required.aliasSubProjectName",
					"Please Select Alias Sub Project Name");
			if ("0".equals(fileUpload.getAliasSubProjectName())) {
				errors.rejectValue("aliasSubProjectName",
						"required.aliasSubProjectName",
						"Please Select Alias Sub Project Name");
			}
		}

		if (null != fileUpload.getFiles()) {
			Iterator<MultipartFile> listOfFiles = fileUpload.getFiles()
					.listIterator();
			while (listOfFiles.hasNext()) {
				MultipartFile multipartFile = listOfFiles.next();
				if (null == multipartFile || multipartFile.getSize() == 0) {
					listOfFiles.remove();
				}
			}
			if (fileUpload.getFiles().size() == 0) {
				errors.reject("no files found");
			}
		}

	}
}
