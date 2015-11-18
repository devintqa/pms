package com.psk.pms.dao;

import java.util.List;
import java.util.Map;

import com.psk.pms.model.Indent;
import com.psk.pms.model.ProjDescDetail;
import com.psk.pms.model.SearchDetail;
import com.psk.pms.model.QuoteDetails.SupplierQuoteDetails;

/**
 * Created by prakashbhanu57 on 8/18/2015.
 */
public interface FieldDescriptionDAO {

    boolean isFieldDescriptionDetailsExistsForProject(int projectId);

    boolean isFieldDescriptionDetailsExistsForSubProject(int projectId);

    void createFieldDescription(int projectId, int subProjectId);

    Integer saveIndentDescription(Indent indent);

    ProjDescDetail getPskFieldProjectDescription(String projDescId);

    List<Indent> getIndentList(SearchDetail searchDetail);

    String placeIndentRequest(Indent indent);

    Map<String, Object> getRequestedIndentQty(Integer projId, String userRole);

    List<ProjDescDetail> getFieldDescDetailList(SearchDetail searchDetail);

    Indent getIndent(String indentId);

    List<Indent> getIndentListByStatus(String status);

    Integer upateIndentDescription(Indent indent);

	Map<String, String> isActiveIndentExistForDescription(String projId);

}
