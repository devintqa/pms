package com.psk.pms.dao;

import com.psk.pms.model.SubProjectDetail;

import java.util.List;
import java.util.Map;

/**
 * Created by prakashbhanu57 on 7/6/2015.
 */
public interface SubProjectDAO {

	void deleteSubProjectBySubProjectId(Integer subProjectId);

	void deleteSubProjectByProjectId(Integer projectId);

	List<SubProjectDetail> getSubProjectDocumentList(Integer projectId);

	SubProjectDetail getSubProjectDocument(String subProjectId);

	boolean isAliasSubProjectAlreadyExisting(String subAliasName,
			Integer projectId);

	Map<String, String> getSubAliasProjectNames(String projectId);

	boolean saveSubProject(SubProjectDetail subProjectDetail);
}
