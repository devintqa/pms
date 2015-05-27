package com.psk.pms.controller;
 
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
 
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.multipart.MultipartFile;

import com.psk.pms.model.Employee;
import com.psk.pms.model.FileUpload;
 
@Controller
public class FileUploadController {
 
    @RequestMapping(value = "/emp/myview/uploadFile/{employeeId}", method = RequestMethod.GET)
    public String pmsDisplayForm(@PathVariable String employeeId, 
			Model model) {
    	Employee employee = new Employee();
		employee.setEmployeeId(employeeId);
		model.addAttribute("employee", employee);
        return "UploadFile";
    }
 
    @RequestMapping(value = "/emp/myview/uploadFile/saveFiles.do", method = RequestMethod.POST)
    public String fileSave(
            @ModelAttribute("uploadForm") FileUpload uploadForm,
            Model map) throws IllegalStateException, IOException {
        String saveDirectory = "C:/SHALU/";
 
        List<MultipartFile> pmsFiles = uploadForm.getFiles();
 
        List<String> fileNames = new ArrayList<String>();
 
        if (null != pmsFiles && pmsFiles.size() > 0) {
            for (MultipartFile multipartFile : pmsFiles) {
                String fileName = multipartFile.getOriginalFilename();
                if (!"".equalsIgnoreCase(fileName)) {
                	System.out.println("File Name: " + fileName);
                    multipartFile.transferTo(new File(saveDirectory + fileName));
                    fileNames.add(fileName);
                }
            }
        }
 
        map.addAttribute("fileAdditionSuccessful", "Files have got uploaded successfully");
        return "UploadFile";
    }
}
