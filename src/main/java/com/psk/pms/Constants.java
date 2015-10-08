package com.psk.pms;

public interface Constants {

	String REQUEST_EMPLOYEE_ACCESS = "0";
	String ENABLE_EMPLOYEE_ACCESS = "1";
	String DENY_EMPLOYEE_ACCESS = "2";
	String DISABLE_EMPLOYEE_ACCESS = "3";
	String EXCEL_FILE_TYPE = "application/vnd.openxmlformats-officedocument.spreadsheetml.sheet";
	String ITEM_SAVE_SUCCESSFUL = "Items Configured Successfully.";
	String DUPLICATED_ITEM_NAMES = "Same Item Name Cannot Be Added More Than Once Per Project.";
	String EXISTS = "Exists";
	String DOESNOTEXISTS = "Does not Exists";
	String SERIALNUMBEREMPTY = "Serial Number can't be Empty";
	String WORKTYPEEMPTY = "WorkType can't be Empty";
	String QUANTITYINFIGEMPTY = "Quantity in figures can't be Empty";
	String QUANTITYINUNITEMPTY = "Quantity in Unit can't be Empty";
	String DESCEMPTY = "Description can't be Empty";
	String ALIASDESCEMPTY = "Alias description can't be Empty";
	String SERIALNOTUNIQUE = "Serial numbers are not unique";
	String ALIASNOTUNIQUE = "AliasDescriptions are not unique";
	String UPLOADFAILED = "Upload Failed.  ";
	String PSK_PROJECT_DESCRIPTION = "PSK_DESC";
	String GOVERNMENT_PROJECT_DESCRIPTION = "GOV_DESC";
	String MATERIAL = "Material";
	String LABOUR = "Labour";
	String MACHINERY = "Machinery";
	String OTHER = "Other";
	String PROJECT_ITEM_DESCRIPTION_NOT_UNIQUE = "Item types and Scheduled number are not unique";
	String PROJECT_ITEM_NAME_TOO_BIG = "Item name should not exceed 100";
	String ITEM_NAME_EMPTY = "Item name can't be Empty";
	String ITEM_UNIT_EMPTY = " unit can't be Empty";
	String ITEM_RATE_EMPTY = " rate can't be Empty";
	String ITEM_SCHDEDULE_NUMBER_EMPTY = " schedule number can't be Empty";
	String CANT_BE_EMPTY = " can't be Empty";
	String NOTEXIST = "NotExist";
	String ALREADYEXIST = "AlreadyExist";
	String SUCCESS = "Success";
	String NULL_STRING = "null";
	String PSK = "psk";
	String GOVERNMENT = "government";
	String FIELD = "field";
	String INDENT = "indent";
	String PSK_SHEET = "PSK_DESC";
	String GOV_SHEET = "GOV_DESC";
	String DESC_SHEET = "DESCRIPTION";
	String NO_ROLE_TAGGED = "No Role Tagged";

    //Drop down Types
    String TEAM = "team";
    String PROJECT_TYPE = "projectType";
    String DEPOSIT_TYPE = "depositType";
    String DEPOSIT_DETAIL = "depositDetail";
    String DEPOSIT_STATUS = "depositStatus";
    String ITEM_TYPE = "itemType";
    String METRIC = "metric";
    String WORKLOCATION = "workLocation";
    String ALL_DESCRIPTION_TYPE = "all description";
    String DISPATCHED = "Dispatched";

    String AUTHORIZE = "authorize";
	String REQUESTED_FOR = "baseDescription";
}
