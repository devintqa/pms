package com.psk.pms.service;

import com.psk.pms.model.FileUpload;

import java.io.IOException;
import java.util.List;

public interface FileService {

    void uploadFiles(FileUpload fileUpload) throws IOException;
    
    void saveProjectDescription(FileUpload fileUpload) throws IOException;

    List<FileUpload> downloadFiles(FileUpload fileUpload);

    void deleteFile(String filePath);
}
