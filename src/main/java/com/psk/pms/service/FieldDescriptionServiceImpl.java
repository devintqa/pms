package com.psk.pms.service;

import com.psk.pms.dao.FieldDescriptionDAO;
import com.psk.pms.model.Indent;

import org.springframework.beans.factory.annotation.Autowired;

/**
 * Created by prakashbhanu57 on 8/18/2015.
 */
public class FieldDescriptionServiceImpl implements FieldDescriptionService {

    @Autowired
    private FieldDescriptionDAO fieldDescriptionDAO;

    @Override
    public boolean isFieldDescriptionDetailsExistsForProject(int projectId) {
        return fieldDescriptionDAO.isFieldDescriptionDetailsExistsForProject(projectId);
    }

    @Override
    public boolean isFieldDescriptionDetailsExistsForSubProject(int subProjectId) {
        return fieldDescriptionDAO.isFieldDescriptionDetailsExistsForSubProject(subProjectId);
    }

    @Override
    public void createFieldDescription(int projectId, int subProjectId) {
        fieldDescriptionDAO.createFieldDescription(projectId, subProjectId);
    }

	@Override
	public boolean saveIndentDescription(Indent indent) {
		 return fieldDescriptionDAO.saveIndentDescription(indent);
	}
}
