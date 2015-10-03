package com.psk.pms.service;

import java.util.List;

import com.psk.pms.model.Indent;
import com.psk.pms.model.ProjDescDetail;
import com.psk.pms.model.SearchDetail;


public interface FieldDescriptionService {

    boolean isFieldDescriptionDetailsExistsForProject(int projectId);

    boolean isFieldDescriptionDetailsExistsForSubProject(int subProjectId);

    void createFieldDescription(int projectId, int subProjectId);
    
    boolean saveIndentDescription(Indent indent);
    
    List<Indent> getIndentDescAndItems(int projDescId);
    
    public ProjDescDetail getPskFieldProjectDescription(String projDescId);

	List<Indent> getIndentList(SearchDetail searchDetail);
}
