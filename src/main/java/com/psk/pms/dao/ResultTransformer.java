package com.psk.pms.dao;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

import com.psk.pms.model.Indent;
import com.psk.pms.model.IndentDesc;
import com.psk.pms.model.ItemDetailDto;
import com.psk.pms.model.LeadDetailConfiguration;
import com.psk.pms.model.ProjDescComparisonDetail;
import com.psk.pms.model.ProjDescDetail;
import com.psk.pms.model.ProjectDetail;
import com.psk.pms.model.QuoteDetails.SupplierQuoteDetails;

public class ResultTransformer {

	
	
	ProjDescComparisonDetail buildProjDescComparisonDetail(Map < String, Object > row) {
		ProjDescComparisonDetail projDescComparisonDetail = new ProjDescComparisonDetail();

		projDescComparisonDetail.setSerialNumber((String) row.get("SerialNumber"));
		BigDecimal quantity = (BigDecimal) row.get("Quantity");
		projDescComparisonDetail.setMetric((String) row.get("Metric"));
		if (null == quantity) {
			projDescComparisonDetail.setQuantity("");
		} else {
			projDescComparisonDetail.setQuantity(quantity.toString());
		}
		projDescComparisonDetail.setAliasDescription((String) row.get("AliasDescription"));

		BigDecimal pricePerQuantity = (BigDecimal) row.get("PricePerQuantity");
		if (null == pricePerQuantity) {
			projDescComparisonDetail.setPricePerQuantity("");
		} else {
			projDescComparisonDetail.setPricePerQuantity(pricePerQuantity.toString());
		}
		BigDecimal totalCost = (BigDecimal) row.get("TotalCost");
		if (null == totalCost) {
			projDescComparisonDetail.setTotalCost("");
		} else {
			projDescComparisonDetail.setTotalCost(totalCost.toString());
		}
		BigDecimal deptPricePerQuantity = (BigDecimal) row.get("DeptPricePerQuantity");
		if (null == deptPricePerQuantity) {
			projDescComparisonDetail.setDeptPricePerQuantity("");
		} else {
			projDescComparisonDetail.setDeptPricePerQuantity(deptPricePerQuantity.toString());
		}
		BigDecimal deptTotalCost = (BigDecimal) row.get("DeptTotalCost");
		if (null == deptTotalCost) {
			projDescComparisonDetail.setDeptTotalCost("");
		} else {
			projDescComparisonDetail.setDeptTotalCost(deptTotalCost.toString());
		}
		return projDescComparisonDetail;
	}
	
	ProjDescDetail buildBaseDescDetail(Map < String, Object > row) {
		ProjDescDetail projDescDetail = new ProjDescDetail();
		projDescDetail.setBaseDescId((Integer) row.get("BaseDescId"));
		projDescDetail.setWorkType((String) row.get("WorkType"));
		BigDecimal quantity = (BigDecimal) row.get("Quantity");
		projDescDetail.setMetric((String) row.get("Metric"));
		projDescDetail.setBaseCategory((String) row.get("Category"));
		projDescDetail.setQuantity(quantity.toString());
		projDescDetail.setDescription((String) row.get("Description"));
		projDescDetail.setAliasDescription((String) row.get("BaseDescription"));
		BigDecimal pricePerQuantity = (BigDecimal) row.get("PricePerQuantity");
		if (null == pricePerQuantity) {
			projDescDetail.setPricePerQuantity("");
		} else {
			projDescDetail.setPricePerQuantity(pricePerQuantity.toString());
		}
		return projDescDetail;
	}
	
    ProjectDetail buildProjectDetail(Map<String, Object> row) {
        ProjectDetail projDoc = new ProjectDetail();
        projDoc.setProjId((Integer) row.get("ProjId"));
        projDoc.setAliasProjectNameForSubProj((Integer) row.get("MainProjId"));
        projDoc.setProjectName((String) row.get("ProjName"));
        projDoc.setAliasName((String) row.get("AliasProjName"));
        projDoc.setProjectType((String) row.get("ProjectType"));
        projDoc.setAgreementNo((String) row.get("AgreementNum"));
        projDoc.setCerNo((String) row.get("CERNum"));
        projDoc.setContractorName((String) row.get("ContractorName"));
        projDoc.setAliasContractorName((String) row.get("ContractorAliasName"));
        projDoc.setContractorAddress((String) row.get("ContractorAdd"));
        projDoc.setTenderSqlDate((Date) row.get("TenderDate"));

        BigDecimal amount = (BigDecimal) row.get("Amount");
        projDoc.setAmount(amount.toString());

        BigDecimal aggValue = (BigDecimal) row.get("AgreementValue");
        projDoc.setAgreementValue(aggValue.toString());

        BigDecimal tenderValue = (BigDecimal) row.get("TenderValue");
        projDoc.setTenderValue(tenderValue.toString());


        BigDecimal exAmount = (BigDecimal) row.get("ExcessInAmount");
        projDoc.setExAmount(exAmount.toString());

        BigDecimal exPercentage = (BigDecimal) row.get("ExcessInPercentage");
        if (null == exPercentage) {
            projDoc.setExPercentage("");
        } else {
            projDoc.setExPercentage(exPercentage.toString());
        }

        BigDecimal lessPercentage = (BigDecimal) row.get("LessInPercentage");
        if (null == lessPercentage) {
            projDoc.setLessPercentage("");
        } else {
            projDoc.setLessPercentage(lessPercentage.toString());
        }
        projDoc.setWorkLocation((String) row.get("workLocation"));
        projDoc.setWorkoutPercentage((BigDecimal) row.get("workoutPercentage"));
        projDoc.setCompletionDateSqlForBonus((Date) row.get("CompletionDateForBonus"));
        projDoc.setAgreementSqlDate((Date) row.get("AgreementDate"));
        projDoc.setCommencementSqlDate((Date) row.get("CommencementDate"));
        projDoc.setCompletionSqlDate((Date) row.get("CompletedDate"));
        projDoc.setAgreementPeriod((Integer) row.get("AgreementPeriod"));

        return projDoc;
    }
    
    ProjDescDetail buildProjectDescDetail(Map < String, Object > row) {
		ProjDescDetail projDescDetail = new ProjDescDetail();
		projDescDetail.setProjId((Integer) row.get("ProjId"));
		projDescDetail.setAliasProjectName((String) row.get("AliasProjName"));
		projDescDetail.setSerialNumber((String) row.get("SerialNumber"));
		projDescDetail.setSubProjId((Integer) row.get("SubProjId"));
		projDescDetail.setAliasSubProjectName((String) row.get("AliasSubProjName"));
		projDescDetail.setWorkType((String) row.get("WorkType"));
		BigDecimal quantity = (BigDecimal) row.get("Quantity");
		projDescDetail.setMetric((String) row.get("Metric"));
		if (null == quantity) {
			projDescDetail.setQuantity("");
		} else {
			projDescDetail.setQuantity(quantity.toString());
		}
		projDescDetail.setDescription((String) row.get("Description"));
		projDescDetail.setAliasDescription((String) row.get("AliasDescription"));

		BigDecimal pricePerQuantity = (BigDecimal) row.get("PricePerQuantity");
		if (null == pricePerQuantity) {
			projDescDetail.setPricePerQuantity("");
		} else {
			projDescDetail.setPricePerQuantity(pricePerQuantity.toString());
		}
		BigDecimal totalCost = (BigDecimal) row.get("TotalCost");
		if (null == totalCost) {
			projDescDetail.setTotalCost("");
		} else {
			projDescDetail.setTotalCost(totalCost.toString());
		}
		projDescDetail.setProjDescId((Integer) row.get("ProjDescId"));
        BigDecimal  conversionRate = (BigDecimal) row.get("ConversionRate");
        if(null == conversionRate){
            projDescDetail.setConversionFalg(false);
        }else{
            projDescDetail.setConversionFalg(true);
            projDescDetail.setConversionValue(conversionRate.toString());
        }
		return projDescDetail;
	}
    
    LeadDetailConfiguration.LeadDetail buildLeadDetail(Map<String, Object> row) {
        LeadDetailConfiguration.LeadDetail leadDetail = new LeadDetailConfiguration.LeadDetail();
        Integer leadDetailId = (Integer) row.get("leadDetailId");
        leadDetail.setLeadDetailId(leadDetailId.toString());
        String material = (String) row.get("material");
        leadDetail.setMaterial(material);
        String sourceOfSupply = (String) row.get("sourceOfSupply");
        leadDetail.setSourceOfSupply(sourceOfSupply);
        BigDecimal distance = (BigDecimal) row.get("distance");
        leadDetail.setDistance(String.valueOf(distance));
        String unit = (String) row.get("unit");
        leadDetail.setUnit(unit);
        leadDetail.setSourceOfSupply(sourceOfSupply);
        BigDecimal cost = (BigDecimal) row.get("cost");
        leadDetail.setCost(String.valueOf(cost));
        BigDecimal ic = (BigDecimal) row.get("ic");
        leadDetail.setIc(String.valueOf(ic));
        BigDecimal leadCharges = (BigDecimal) row.get("leadCharges");
        leadDetail.setLeadCharges(String.valueOf(leadCharges));
        BigDecimal loadingUnloadingCharges = (BigDecimal) row.get("loadingUnloadingCharges");
        leadDetail.setLoadingUnloading(String.valueOf(loadingUnloadingCharges));
        BigDecimal total = (BigDecimal) row.get("total");
        leadDetail.setTotal(String.valueOf(total));
        return leadDetail;
    }
    
    List<ItemDetailDto> buildItemDetailDao(List<Map<String, Object>> rows) {
        List<ItemDetailDto> itemDetailDtos = new ArrayList<ItemDetailDto>();
        for (Map<String, Object> itemDetail : rows) {
            ItemDetailDto itemDetailDto = new ItemDetailDto();
            itemDetailDto.setProjectId((Integer) itemDetail.get("ProjId"));
            itemDetailDto.setSubProjectId((Integer) itemDetail.get("SubProjId"));
            itemDetailDto.setItemName((String) itemDetail.get("ItemName"));
            itemDetailDto.setItemUnit((String) itemDetail.get("ItemUnit"));
            itemDetailDto.setItemQuantity(Integer.parseInt((String) itemDetail.get("ItemQty")));
            itemDetailDto.setItemPrice(new BigDecimal((String) itemDetail.get("ItemPrice")));
            itemDetailDto.setItemCost(new BigDecimal((String) itemDetail.get("ItemCost")));
            itemDetailDto.setProjectDescId((Integer) itemDetail.get("ProjDescId"));
            itemDetailDto.setProjectDescSerial((String) itemDetail.get("ProjDescSerial"));
            itemDetailDto.setProjectDescItemId((Integer) itemDetail.get("DescItemId"));
            itemDetailDtos.add(itemDetailDto);
        }
        return itemDetailDtos;
    }

    Indent buildIndent(Map<String, Object> row) {
		Indent indent = new Indent();
		indent.setProjId(row.get("ProjId").toString());
		indent.setIndentId(row.get("IndentId").toString());	
		indent.setStatus((String) row.get("Status"));
		indent.setStartDate((String) row.get("StartDate"));
		indent.setEndDate((String) row.get("EndDate"));
		if(row.containsKey("aliasProjName")){
			indent.setDescription(row.get("aliasProjName").toString());
		}
		
		return indent;
	}
    
    SupplierQuoteDetails buildSupplierList(Map<String, Object> row) {
    	SupplierQuoteDetails supplierList = new SupplierQuoteDetails();
    	supplierList.setItemQty(row.get("itemQty").toString());
    	supplierList.setItemName(row.get("itemName").toString());
    	supplierList.setItemType(row.get("itemType").toString());
    	supplierList.setAliasProjName(row.get("aliasProjName").toString());
    	supplierList.setSupplierQuoteStatus(row.get("supplierquotestatus").toString());
		return supplierList;
	}

	IndentDesc buildIndentDesc(Map<String, Object> row) {
		IndentDesc indentDesc = new IndentDesc();
		indentDesc.setIndentId(row.get("IndentId").toString());
		indentDesc.setIndentDescId(row.get("IndentDescId").toString());	
		indentDesc.setProjDescId(row.get("ProjDescId").toString());
		indentDesc.setMetric((String) row.get("Metric"));
		indentDesc.setComments((String) row.get("Comments"));
		if(row.containsKey("AliasDescription")){
			indentDesc.setAliasProjDesc(row.get("AliasDescription").toString());
		}
		BigDecimal qty = (BigDecimal) row.get("Quantity");
		if(null==qty){
			qty = new BigDecimal(0);
		}
		indentDesc.setPlannedQty(qty.doubleValue());
		return indentDesc;
	}

	public IndentDesc.ItemDetail buildIndentDescItem(Map<String, Object> row) {
		IndentDesc.ItemDetail indentItem = new IndentDesc.ItemDetail();
		indentItem.setIndentItemId(row.get("IndentItemId").toString());
		indentItem.setIndentDescId(row.get("IndentDescId").toString());
		indentItem.setItemName(row.get("ItemName").toString());
		indentItem.setItemType(row.get("ItemType").toString());	
		indentItem.setItemUnit(row.get("ItemUnit").toString());
		indentItem.setItemQty(row.get("ItemQty").toString());
		return indentItem;
	}

}
