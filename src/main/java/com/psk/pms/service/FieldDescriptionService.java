package com.psk.pms.service;

import java.util.List;
import java.util.Map;

import com.psk.pms.model.Indent;
import com.psk.pms.model.ProjDescDetail;
import com.psk.pms.model.SearchDetail;


public interface FieldDescriptionService {

    boolean isFieldDescriptionDetailsExistsForProject(int projectId);

    boolean isFieldDescriptionDetailsExistsForSubProject(int subProjectId);

    void createFieldDescription(int projectId, int subProjectId);
    
    Integer saveIndentDescription(Indent indent);
    
    public ProjDescDetail getPskFieldProjectDescription(String projDescId);

	List<Indent> getIndentList(SearchDetail searchDetail);

	String placeIndentRequest(Indent indent);

	Map<String, Object> getRequestedIndentQty(Integer projId);

	List<ProjDescDetail> getFieldDescDetailList(SearchDetail searchDetail);

	Indent getIndent(String indentId);

	List<Indent> getIndentListByStatus(String status);

}
