package com.psk.pms.service;

import java.util.List;
import java.util.Map;

import com.psk.pms.dao.FieldDescriptionDAO;
import com.psk.pms.model.Indent;
import com.psk.pms.model.ProjDescDetail;
import com.psk.pms.model.SearchDetail;

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
	public Integer saveIndentDescription(Indent indent) {
		 return fieldDescriptionDAO.saveIndentDescription(indent);
	}

	@Override
	public ProjDescDetail getPskFieldProjectDescription(String projDescId) {
		return fieldDescriptionDAO.getPskFieldProjectDescription(projDescId);
	}

	@Override
	public List<Indent> getIndentList(SearchDetail searchDetail) {
		return fieldDescriptionDAO.getIndentList(searchDetail);
	}

	@Override
	public String placeIndentRequest(Indent indent) {
		return fieldDescriptionDAO.placeIndentRequest(indent);
	}

	@Override
	public Map<String, Object> getRequestedIndentQty(Integer projId) {
		return fieldDescriptionDAO.getRequestedIndentQty(projId);
	}

	@Override
	public List<ProjDescDetail> getFieldDescDetailList(SearchDetail searchDetail) {
		return fieldDescriptionDAO.getFieldDescDetailList(searchDetail);
	}

}
