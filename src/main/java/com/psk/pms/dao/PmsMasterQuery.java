package com.psk.pms.dao;

public class PmsMasterQuery {

    public static final String NOOFFIELDDESCASSOCIATEDTOPROJECT = "SELECT count(*) FROM fieldprojectdesc WHERE ProjId = ?";

    public static final String NOOFFIELDDESCASSOCIATEDTOSUBPROJECT = "SELECT count(*) FROM fieldprojectdesc WHERE SubProjId = ?";

    public static final String CREATEPROJECTFIELDDESCRIPTION = "insert into fieldprojectdesc select * from projectdesc where ProjId = ? and subProjId is null";

    public static final String CREATESUBPROJECTFIELDDESCRIPTION = "insert into fieldprojectdesc select * from projectdesc where SubProjId = ?;";

    public static final String DELETEPROJECTFIELDDESCRIPTION = "delete from fieldprojectdesc where ProjId = ? and SubProjId is null";

    public static final String DELETESUBPROJECTFIELDDESCRIPTION = "delete from fieldprojectdesc where SubProjId = ?";

    public static final String DELETEPROJECTFIELDDESCRIPTIONITEM = "delete from fieldprojdescitem where ProjId = ? and SubProjId = 0";

    public static final String DELETESUBPROJECTFIELDDESCRIPTIONITEM = "delete from fieldprojdescitem where SubProjId = ?";

    public static final String CREATEPROJECTFIELDDESCRIPTIONITEM = "insert into fieldprojdescitem select * from projdescitem where ProjId = ? and subProjId =0";

    public static final String CREATESUBPROJECTFIELDDESCRIPTIONITEM = "insert into fieldprojdescitem select * from projdescitem where SubProjId = ?";

    public static final String CREATE_DEPOSIT_DETAIL = "INSERT INTO depositdetail (ProjId , SubProjId , DepositAmount , DepositStartDate ,DepositEndDate ,DepositType, depositTypeNumber ,"
            + "DepositPeriod,DepositExtensionDate,DepositLedgerNumber, LastUpdatedBy,LastUpdatedAt,DepositSubmitter,DepositDetail,DepositStatus,DepositRecievedDate,DepositRecievedNote)"
            + "VALUES ( ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? ,?,?,?,?,?)";

    public static final String UPDATE_DEPOSIT_DETAIL = "UPDATE depositdetail set ProjId = ? , SubProjId = ? ,DepositAmount = ? , DepositStartDate = ? ,DepositEndDate = ?,DepositType =? , depositTypeNumber =?,"
            + "DepositPeriod = ?,DepositExtensionDate = ? ,DepositLedgerNumber =? , "
            + "LastUpdatedBy = ?,LastUpdatedAt =? ,DepositSubmitter =?, DepositDetail =?,DepositStatus=?,DepositRecievedDate=?,DepositRecievedNote=? WHERE DepositId = ? ";

    public static final String FETCH_DEPOSIT_DETAILS = "select e.DepositId ,e.ProjId , p.AliasProjName , s.AliasSubProjName ,"
            + " s.SubProjName , e.DepositType , e.DepositAmount , e.DepositStartDate , e.DepositEndDate ,e.DepositStatus, e.DepositExtensionDate "
            + " from depositdetail e left join project as p on e.ProjId = p.ProjId "
            + "left join subproject as s on e.SubProjId=s.SubProjId ";

    public static final String FETCH_DEPOSIT_DETAIL_BY_DEPOSIT_ID = "select  ProjId ,DepositDetail, SubProjId , DepositAmount , DepositStartDate ,DepositEndDate ,DepositType, depositTypeNumber ,"
            + "DepositPeriod,DepositExtensionDate,DepositLedgerNumber, DepositSubmitter,DepositStatus,DepositRecievedDate,DepositRecievedNote from depositdetail where DepositId = ?";

    public static final String DELETEPROJECTBYPROJECTID = "DELETE FROM project WHERE ProjId = ?";

    public static final String DELETESUBPROJECTBYPROJECTID = "DELETE FROM subproject WHERE ProjId = ?";

    public static final String DELETESUBPROJECTBYSUBPROJECTID = "DELETE FROM subproject WHERE SubProjId = ?";

    public static final String DELETE_DEPOSIT_DETAIL_BY_PROJECTID = "DELETE FROM depositdetail WHERE ProjId = ?";

    public static final String DELETE_DEPOSIT_DETAIL_BY_SUB_PROJECTID = "DELETE FROM depositdetail WHERE SubProjId = ?";

    public static final String DELETEPROJECTDESCRIPTIONBYPROJECTID = "DELETE FROM projectdesc WHERE ProjId = ?";

    public static final String DELETEPROJECTDESCRIPTIONBYSUBPROJECTID = "DELETE FROM projectdesc WHERE SubProjId = ?";

    public static final String DELETE_DEPOSIT_DETAIL_BY_DEPOSIT_ID = "DELETE FROM depositdetail WHERE DepositId = ?";

    public static final String GET_DEPOSIT_DETAILS_BY_PROJECTID = "select e.DepositId ,e.ProjId , p.AliasProjName,e.DepositStatus, e.DepositType , e.DepositAmount , e.DepositStartDate , e.DepositEndDate "
            + "                from depositdetail e  left join project as p on e.ProjId = p.ProjId "
            + "                where e.ProjId = ? and e.SubProjId is null ";

    public static final String GET_DEPOSIT_DETAILS_BY_SUBPROJECTID = "select e.DepositId ,e.ProjId , p.AliasProjName , s.AliasSubProjName ,"
            + "     s.SubProjName , e.DepositType , e.DepositAmount , e.DepositStartDate ,e.DepositStatus, e.DepositEndDate ,e.DepositExtensionDate"
            + "    from depositdetail e left join project as p on e.ProjId = p.ProjId "
            + "    left join subproject as s on e.SubProjId=s.SubProjId where e.SubProjId=?";


    public static final String projDescDetailQuery = "SELECT ProjId, SubProjId, SerialNumber, WorkType, Quantity, Metric,"
            + "Description, AliasDescription, PricePerQuantity, TotalCost, ProjDescId FROM projectdesc";

    public static final String compareDataQuery = "SELECT distinct p.SerialNumber, p.Quantity, p.Metric, p.AliasDescription, p.PricePerQuantity, p.TotalCost, pp.PricePerQuantity as DeptPricePerQuantity, pp.TotalCost as DeptTotalCost "
            + "FROM projectdesc p INNER JOIN govprojectdesc pp on p.ProjId = pp.ProjId "
            + "and p.AliasDescription = pp.AliasDescription";

    public static final String projDescDetail = "SELECT d.ProjId, d.SubProjId, d.SerialNumber, d.WorkType, d.Quantity, d.Metric, "
            + "d.Description, d.AliasDescription, d.PricePerQuantity, d.TotalCost, d.ProjDescId , d.ConversionRate";

    public static final String baseDescDetail = "SELECT BaseDescId, WorkType, Metric, Quantity, PricePerQuantity, LastUpdatedBy, LastUpdatedAt, Description, BaseDescription FROM basedesc";

    public static final String deleteProjDescDetailQuery = "DELETE FROM projectdesc where ProjDescId = ?";

    public static final String DELETEPROJDESCITEMBYPROJECTID = "DELETE FROM projdescitem WHERE ProjId = ?";

    public static final String DELETEPROJDESCITEMBYSUBPROJECTID = "DELETE FROM  projdescitem WHERE SubProjId = ?";

    public static final String DELETEPROJDESCAITEMBYPROJECTDESCID = "DELETE FROM projdescitem WHERE ProjDescId = ?";

    public static final String DELETEPROJDESCAITEMBYPROJECTDESCITEMID = "DELETE FROM projdescitem WHERE DescItemId = ?";

    public static final String FETCHUNIQUEITEMUNIT = "SELECT DISTINCT itemUnit FROM itemcodes";

    public static final String INSERTSUBPROJECTDESCRIPTION = "INSERT INTO projectDesc (ProjId, SubProjId,SerialNumber ,WorkType, Quantity, Metric, "
            + "Description, AliasDescription,LastUpdatedBy ,LastUpdatedAt) "
            + "VALUES (?, ? , ?, ?, ?, ?, ?, ?, ?, ?)";

    public static final String INSERTPROJECTDESCRIPTION = "INSERT INTO projectDesc (ProjId, SerialNumber, WorkType, Quantity, Metric, "
            + "Description, AliasDescription, LastUpdatedBy, LastUpdatedAt) "
            + "VALUES (?, ?, ? , ?, ?, ?, ?, ?, ?)";

    public static final String INSERTGOVPROJECTDESCRIPTION = "INSERT INTO govprojectdesc (ProjId, SerialNumber, WorkType, Quantity, Metric, "
            + "Description, AliasDescription, LastUpdatedBy, LastUpdatedAt) "
            + "VALUES (?, ?, ? , ?, ?, ?, ?, ?, ?)";

    public static final String NOOFPROJECTDESCASSOCIATEDTOPROJECT = "SELECT count(*) FROM projectdesc WHERE ProjId = ?";

    public static final String NO_OF_GOV_PROJECT_DESC_ASSOCIATED_TO_PROJECT = "SELECT count(*) FROM govprojectdesc WHERE ProjId = ?";

    public static final String NOOFPROJECTDESCASSOCIATEDTOSUBPROJECT = "SELECT count(*) FROM projectdesc WHERE SubProjId = ?";

    public static final String NO_OF_GOV_PROJECT_DESC_ASSOCIATED_TO_SUBPROJECT = "SELECT count(*) FROM govprojectdesc WHERE SubProjId = ?";

    public static final String SAVEITEMS = "INSERT INTO itemcodes (itemName, itemUnit ,itemType) VALUES (?, ?, ?)";

    public static final String UPDATEBASEDESCRIPTION = "UPDATE basedesc set WorkType = ?, Metric = ?, LastUpdatedBy = ?, LastUpdatedAt = ?, Description= ? where BaseDescription = ? ";

    public static final String DEACTIVATEEXISTINGPRICES = "UPDATE pskpricedetail SET active ='0' WHERE projectId = ? AND subProjectId = ?";

    public static final String INSERTPRICEFORITEMS = "INSERT INTO pskpricedetail (projectId, subProjectId, itemName, itemUnit, itemPrice, itemType, priceFeed) "
            + "VALUES (?, ?, ?, ?, ?, ?, ?)";

    public static final String INSERTBASEDESCRIPTION = "INSERT INTO basedesc(Category, WorkType, Metric, Quantity, PricePerQuantity , LastUpdatedBy, LastUpdatedAt, Description, BaseDescription) values (?, ?, ?, ?, ?, ?, ?, ?, ?)";

    public static final String FETCHBASEDESCRIPTIONS = "SELECT * FROM basedesc";

    public static final String DELETEBASEDESCRIPTION = "DELETE FROM basedesc WHERE BaseDescription = ?";

    public static final String ISBASEDESCEXISTS = "SELECT * FROM basedesc WHERE BaseDescription = ?";

    public static final String GETBASEDESCRIPTION = "SELECT * FROM basedesc WHERE BaseDescId = ?";

    public static final String INSERT_ITEM_RATE_DESCRIPTION = "INSERT INTO govpricedetail (itemName, itemUnit, itemType, itemPrice, priceFeed,scheduledItemNumber,itemDescription) "
            + "VALUES (?, ?, ?, ?, ?, ?, ?)";


    public static final String SAVE_ROLES = "INSERT INTO teamRole (teamName, roleName) VALUES (?, ?)";

    public static final String GET_ALL_EMPLOYEE = "SELECT * FROM employee where empTeam != 'Admin'";

    public static final String GET_SELECTED_EMPLOYEES = "SELECT distinct(a.empId) FROM employee e,authoriseproject a where e.empTeam = ? and e.enabled =1\n" +
            " and a.projectId=? and a.teamName=e.empTeam";

    public static final String GET_AVAILABLE_EMPLOYEES = "select empId from employee where empId not in (\n" +
            "SELECT distinct(a.empId) FROM employee e,authoriseproject a where e.empTeam = ? and e.enabled =1\n" +
            " and a.projectId=? and a.teamName=e.empTeam) and empTeam =? and enabled=1";

    public static final String DELETE_EMPLOYEE = "DELETE FROM employee WHERE empId = ? ";


    public static final String GET_ROLES = "select roleName from teamRole where teamName = ?";


    public static final String GET_DROP_DOWN_VALUES = "select Value from pmsmastertable where Type = ?";

    public static final String INSERT_INTO_AUTHORISE_PROJECT = "INSERT INTO authoriseProject (projectId, empId,teamName) "
            + "VALUES (?, ?,?)";

    public static final String DELETE_AUTHORIZE_PROJECT = "delete from authoriseproject where teamName=? and empId =? and projectId = ?";

    public static final String GET_LEAD_DETAILS = "select * from projectLeadDetail where projectId = ? and subProjectId = ? and active = 1";

    public static final String CREATE_LEAD_DETAILS = "INSERT INTO projectLeadDetail " +
            "(projectId, subProjectId, material, sourceOfSupply, distance, unit, cost, ic, leadCharges, loadingUnloadingCharges, total, LastUpdatedBy, LastUpdatedAt) " +
            " values (?, ?, ? ,?, ? ,?, ? ,?, ?, ?, ?, ?, ?)";

    public static final String DEACTIVATE_EXISTING_LEAD_DETAILS = "update projectLeadDetail set active = 0 where projectId = ? and subProjectId = ?";


    public static final String CREATE_STORE_DETAIL = "INSERT INTO storeDetail (ProjId , itemType , itemName , supplierName, invoiceNumber, vehicleNo, quantityRecieved, recievedDate , recievedBy, checkedBy, tripSheetNumber, storeType, comments,brandName)\n"
            + "VALUES ( ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?,?)";

    public static final String GET_STORE_DETAILS = "select * from storeDetail where projId = ?";

    public static final String GET_STOCK_DETAILS = "select * from stockDetail where projectId = ? and itemName=?";

    public static final String CREATE_STOCK_DETAILS = "insert into stockDetail(projectId, itemName, totalQuantity) values (?,?,?)";

    public static final String UPDATE_STOCK_DETAILS = "update stockDetail set totalQuantity=? where projectId=? and itemName=?";

    public static final String CREATE_TRANSACTION_DETAILS = "insert into dispatchdetail(projectId, itemName, dispactchedDate,fieldUser,dispatchDesc,quantity) values (?,?,?,?,?,?)";

    public static final String GET_DISPATCH_DETAILS = "SELECT d.fieldUser,d.dispactchedDate,\n" +
            "  d.ItemName,\n" +
            "  SUM(CASE WHEN d.dispatchDesc = 'Dispatched' THEN d.quantity ELSE 0 END) AS Dispatched_Qty,\n" +
            "  SUM(CASE WHEN d.dispatchDesc = 'Returned' THEN d.quantity ELSE 0 END) AS Returned_Qty \n" +
            "FROM dispatchdetail d where d.projectId=?  group by d.fieldUser, d.itemname,d.dispactchedDate";


    public static final String GET_ITEMS = "select * from itemCodes";

    public static final String UPDATE_ITEM = "update itemCodes set itemUnit = ? where itemName= ? and itemType= ?";

    public static final String DELETE_ITEM = "delete from  itemCodes where itemName= ? and itemType= ?";


    public static final String GET_SUPPLIER_DETAILS = "select * from supplierdetails";

    public static final String DELETE_SUPPLIER_DETAIL = "delete from supplierdetails where SupplierId = ?";

    public static final String INSERT_SUPPLIER_DETAIL = "insert into supplierdetails ( TINNumber, Reason,SupplierName, SupplierAliasName," +
            "PhoneNumber, Email, SupplierDescription, LastUpdatedBy, LastUpdatedAt,SupplierType) values ( ?, ?, ?, ?, ?,?, ?, ?, ?,?)";

    public static final String UPDATE_SUPPLIER_DETAIL = "update supplierdetails set TINNumber = ?,Reason =?, SupplierName = ?," +
            "SupplierAliasName = ?, PhoneNumber = ?, Email = ?, SupplierDescription = ?, LastUpdatedBy = ?,SupplierType = ?, LastUpdatedAt = ?" +
            " where SupplierId = ?";

    public static final String ALIAS_SUPPLIER_NAME_EXIST = "select count(*) from supplierdetails where SupplierAliasName = ?";

    public static final String CREATE_QUOTE_DETAILS = "insert into supplierQuoteDetails(AliasProjName, itemName, itemQty, ItemType," +
            "SupplierAliasName,emailAddress,PhoneNumber,quotePrice,totalPrice,supplierQuoteStatus,brandName) values (?,?,?,?,?,?,?,?,?,?,?)";

    public static final String GET_SUPPLIER_QUOTE_DETAILS = "select sq.AliasProjName,sq.ItemName,sq.ItemType,sq.ItemQty,sq.SupplierAliasName,sq.emailAddress,sq.PhoneNumber,\n" +
            "sq.quotePrice,sq.totalPrice,sq.supplierQuoteStatus,sq.brandName,sq.tentativeDeliveryDate,sd.supplierType,sq.comments from supplierquotedetails sq,supplierdetails sd \n" +
            "where sq.AliasProjName= ? and sq.ItemType=? and sq.ItemName=? and sq.SupplierAliasName=sd.SupplierAliasName";

    public static final String DELETE_SUPPLIER_QUOTE_DETAILS = "delete from supplierQuoteDetails where AliasProjName = ? and itemType=? and itemName=?";

    public static final String UPDATE_INDENT_DESC_STATUS = "update indentdescitem set IndentItemStatus= ? where ItemName = ? and itemType =? and IndentItemStatus =?" +
            "and IndentDescId in (select IndentDescId from indentdesc where IndentId in ( select IndentId from indent where projid = ?)) ";

    public static final String UPDATE_SUPPLIER_QUOTE_DETAILS = "update supplierQuoteDetails set itemQty = ?, totalPrice = ?, supplierQuoteStatus = ?, tentativeDeliveryDate = ?, " +
            "comments = ? where ItemName = ? and  SupplierAliasName = ? and aliasProjName=? and brandName=?";


    public static final String GET_SUPPLIER_DETAIL = "select * from supplierQuoteDetails where AliasProjName = ?  and itemName=? and supplierAliasName =? and brandName=?";

    public static final String UPDATE_INDENT_DESC_STATUS_FOR_PURCHASE = "update indentdescitem set IndentItemStatus= ? where ItemName = ? and IndentItemStatus in ('APPROVED','PARTIALLY PURCHASED')" +
            "and IndentDescId in (select IndentDescId from indentdesc where IndentId in ( select IndentId from indent where projid = ?)) ";

    public static final String GET_SUPPLIER_DETAIL_BY_STATUS = "select * from supplierQuoteDetails where AliasProjName = ? and itemName= ? and supplierAliasName = ? and supplierQuoteStatus = ? and brandName =?";

    public static final String UPDATE_SUPPLIER_QUOTE_STATUS = "update supplierQuoteDetails set supplierQuoteStatus = ? where ItemName = ? and itemType = ? and  SupplierAliasName = ? and aliasProjName=? and brandName =?";

    public static final String UPDATE_INDENT_DESC_STATUS_FOR_STORE = "update indentdescitem set IndentItemStatus= ? where ItemName = ? and itemType =? and IndentItemStatus in ('APPROVED','PARTIALLY PURCHASED')" +
            "and IndentDescId in (select IndentDescId from indentdesc where IndentId in ( select IndentId from indent where projid = ?)) ";
    
    public static final String GET_FULL_STORE_DETAILS = "select * from storeDetail where itemname = ? and ItemType = ? and supplierName = ? and brandName = ? and ProjId = ? group by supplierName";

}
