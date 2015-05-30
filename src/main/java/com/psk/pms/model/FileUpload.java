package com.psk.pms.model;

import java.util.List;
import org.springframework.web.multipart.MultipartFile;
 
public class FileUpload {
 
    private List<MultipartFile> pmsFiles;
	private boolean subProjectUpload;
	private String aliasProjectName;
	private String aliasSubProjectName;
	private String employeeId;
	
	public String getEmployeeId() {
		return employeeId;
	}

	public void setEmployeeId(String employeeId) {
		this.employeeId = employeeId;
	}

	public List<MultipartFile> getFiles() {
        return pmsFiles;
    }
 
    public void setFiles(List<MultipartFile> files) {
        this.pmsFiles = files;
    }
    
    public boolean isSubProjectUpload() {
		return subProjectUpload;
	}

	public void setSubProjectUpload(boolean subProjectUpload) {
		this.subProjectUpload = subProjectUpload;
	}

	public String getAliasProjectName() {
		return aliasProjectName;
	}

	public void setAliasProjectName(String aliasProjectName) {
		this.aliasProjectName = aliasProjectName;
	}

	public String getAliasSubProjectName() {
		return aliasSubProjectName;
	}

	public void setAliasSubProjectName(String aliasSubProjectName) {
		this.aliasSubProjectName = aliasSubProjectName;
	}
 
}
