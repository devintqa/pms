package com.psk.pms.dao;

import com.psk.pms.model.ProjDescDetail;
import com.psk.pms.model.ProjectDetail;
import com.psk.pms.model.SubProjectDetail;

public interface ProjectDAO {
	
	public boolean saveProject(ProjectDetail projectDetail);
	
	public boolean editProject(ProjectDetail projectDetail);
	
	public boolean saveSubProject(SubProjectDetail subProjectDetail);
	
	public boolean editSubProject(SubProjectDetail subProjectDetail);
	
	public boolean saveProjDesc(ProjDescDetail projDescDetail);
	
	public boolean editProjDesc(ProjDescDetail projDescDetail);
	
}
