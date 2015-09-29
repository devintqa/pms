package com.psk.pms.dao;

import java.util.List;

import com.psk.pms.model.Indent;
import com.psk.pms.model.ProjDescDetail;

/**
 * Created by prakashbhanu57 on 8/18/2015.
 */
public interface FieldDescriptionDAO {

    boolean isFieldDescriptionDetailsExistsForProject(int projectId);

    boolean isFieldDescriptionDetailsExistsForSubProject(int projectId);

    void createFieldDescription(int projectId,int subProjectId);

	boolean saveIndentDescription(Indent indent);

	List<Indent> getIndentDescAndItems(int projDescId);

	ProjDescDetail getPskFieldProjectDescription(String projDescId);
}
