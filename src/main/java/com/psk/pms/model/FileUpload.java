package com.psk.pms.model;

import java.util.List;
import org.springframework.web.multipart.MultipartFile;
 
public class FileUpload {
 
    private List<MultipartFile> pmsFiles;
	
	public List<MultipartFile> getFiles() {
        return pmsFiles;
    }
 
    public void setFiles(List<MultipartFile> files) {
        this.pmsFiles = files;
    }
 
}
