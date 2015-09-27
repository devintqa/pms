package com.psk.pms.service;

import com.psk.pms.model.Indent;

/**
 * Created by prakashbhanu57 on 8/18/2015.
 */
public interface FieldDescriptionService {

    boolean isFieldDescriptionDetailsExistsForProject(int projectId);

    boolean isFieldDescriptionDetailsExistsForSubProject(int subProjectId);

    void createFieldDescription(int projectId, int subProjectId);
    
    boolean saveIndentDescription(Indent indent);
}
