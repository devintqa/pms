package com.psk.pms.controller;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

import com.psk.pms.model.DataDescriptionDetail;
import com.psk.pms.service.ProjectService;

@Controller
public class DataDescriptionController {
	
	@Autowired
	ProjectService projectService;
	
	public static void main(String args[]){
		DataDescriptionController cont = new DataDescriptionController();
		cont.buildData();
	}
	
	private void buildData(){
		DataDescriptionDetail dataDetail1 = new DataDescriptionDetail();
		DataDescriptionDetail dataDetail2 = new DataDescriptionDetail();
		DataDescriptionDetail dataDetail3 = new DataDescriptionDetail();
		List<DataDescriptionDetail> dataDetails = new ArrayList<DataDescriptionDetail>();
		dataDetail1.setProjDescId(1);
		dataDetail1.setSerialNumber("1a");
		dataDetail1.setAmount("200.45");
		dataDetail1.setMaterial("Cement");
		dataDetail1.setQuantityInFig("1");
		dataDetail1.setRateInFig("200.45");
		dataDetail1.setUnit("cft");
		dataDetails.add(dataDetail1);
		dataDetail2.setProjDescId(1);
		dataDetail2.setSerialNumber("1a");
		dataDetail2.setAmount("200.45");
		dataDetail2.setMaterial("Cement");
		dataDetail2.setQuantityInFig("1");
		dataDetail2.setRateInFig("200.45");
		dataDetail2.setUnit("cft");
		dataDetails.add(dataDetail2);
		dataDetail3.setProjDescId(1);
		dataDetail3.setSerialNumber("1a");
		dataDetail3.setAmount("200.45");
		dataDetail3.setMaterial("Cement");
		dataDetail3.setQuantityInFig("1");
		dataDetail3.setRateInFig("200.45");
		dataDetail3.setUnit("cft");
		dataDetails.add(dataDetail3);
		boolean isInsertSuccessful = projectService.insertDataDescription(dataDetails, "gsriram");
	}

}
