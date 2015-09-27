package com.psk.pms.dao;

import com.psk.pms.model.Indent;

/**
 * Created by prakashbhanu57 on 8/18/2015.
 */
public interface FieldDescriptionDAO {

    boolean isFieldDescriptionDetailsExistsForProject(int projectId);

    boolean isFieldDescriptionDetailsExistsForSubProject(int projectId);

    void createFieldDescription(int projectId,int subProjectId);

	boolean saveIndentDescription(Indent indent);
}
