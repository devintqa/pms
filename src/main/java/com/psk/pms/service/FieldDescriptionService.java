package com.psk.pms.service;

/**
 * Created by prakashbhanu57 on 8/18/2015.
 */
public interface FieldDescriptionService {

    boolean isFieldDescriptionDetailsExistsForProject(int projectId);

    boolean isFieldDescriptionDetailsExistsForSubProject(int subProjectId);

    void createFieldDescription(int projectId, int subProjectId);
}
