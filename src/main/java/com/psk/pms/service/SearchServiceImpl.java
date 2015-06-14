package com.psk.pms.service;

import org.apache.log4j.Logger;
import com.psk.pms.model.SearchDetail;

public class SearchServiceImpl implements SearchService {
	
	private static final Logger LOGGER = Logger.getLogger(SearchServiceImpl.class);
	
	
	public SearchDetail buildSearchDetail(String search) {
		LOGGER.debug("Method: buildSearchDetail");
		SearchDetail searchDetail = new SearchDetail();
		if(search.equalsIgnoreCase("project")){
			searchDetail.setEditProject(true);
			searchDetail.setSearchProject("Y");
		}else if(search.equalsIgnoreCase("subProject")){
			searchDetail.setEditSubProject(true);
			searchDetail.setSearchSubProject("Y");
		}else if(search.equalsIgnoreCase("projectDesc")){
			searchDetail.setSearchProjectDescription(true);
			searchDetail.setSearchProjectDesc("Y");
		}
		return searchDetail;
	}

}
