package com.psk.pms.dao;

public class PmsMasterQuery {

    public static String NOOFFIELDDESCASSOCIATEDTOPROJECT = "SELECT count(*) FROM fieldprojectdesc WHERE ProjId = ?";

    public static String NOOFFIELDDESCASSOCIATEDTOSUBPROJECT = "SELECT count(*) FROM fieldprojectdesc WHERE SubProjId = ?";

    public static String CREATEPROJECTFIELDDESCRIPTION = "insert into fieldprojectdesc select * from projectdesc where ProjId = ? and subProjId is null";

    public static String CREATESUBPROJECTFIELDDESCRIPTION = "insert into fieldprojectdesc select * from projectdesc where SubProjId = ?;";

    public static String DELETEPROJECTFIELDDESCRIPTION = "delete from fieldprojectdesc where ProjId = ? and SubProjId is null";

    public static String DELETESUBPROJECTFIELDDESCRIPTION = "delete from fieldprojectdesc where SubProjId = ?";

    public static String DELETEPROJECTFIELDDESCRIPTIONITEM = "delete from fieldprojdescitem where ProjId = ? and SubProjId = 0";

    public static String DELETESUBPROJECTFIELDDESCRIPTIONITEM = "delete from fieldprojdescitem where SubProjId = ?";

    public static String CREATEPROJECTFIELDDESCRIPTIONITEM = "insert into fieldprojdescitem select * from projdescitem where ProjId = ? and subProjId =0";

    public static String CREATESUBPROJECTFIELDDESCRIPTIONITEM = "insert into fieldprojdescitem select * from projdescitem where SubProjId = ?";

    public static final String CREATE_DEPOSIT_DETAIL = "INSERT INTO depositdetail (ProjId , SubProjId , DepositAmount , DepositStartDate ,DepositEndDate ,DepositType, BGNumber ,"
			+ "DepositPeriod,DepositExtensionDate,DepositLedgerNumber, LastUpdatedBy,LastUpdatedAt,DepositSubmitter,DepositDetail,DepositStatus,DepositRecievedDate,DepositRecievedNote)"
			+ "VALUES ( ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? ,?,?,?,?,?)";

	public static final String UPDATE_DEPOSIT_DETAIL = "UPDATE depositdetail set ProjId = ? , SubProjId = ? ,DepositAmount = ? , DepositStartDate = ? ,DepositEndDate = ?,DepositType =? , BGNumber =?,"
			+ "DepositPeriod = ?,DepositExtensionDate = ? ,DepositLedgerNumber =? , "
			+ "LastUpdatedBy = ?,LastUpdatedAt =? ,DepositSubmitter =?, DepositDetail =?,DepositStatus=?,DepositRecievedDate=?,DepositRecievedNote=? WHERE DepositId = ? ";

	public static final String FETCH_DEPOSIT_DETAILS = "select e.DepositId ,e.ProjId , p.AliasProjName , s.AliasSubProjName ,"
			+ " s.SubProjName , e.DepositType , e.DepositAmount , e.DepositStartDate , e.DepositEndDate "
			+ "from depositdetail e left join project as p on e.ProjId = p.ProjId "
			+ "left join subproject as s on e.SubProjId=s.SubProjId ";

	public static final String FETCH_DEPOSIT_DETAIL_BY_DEPOSIT_ID = "select  ProjId , SubProjId , DepositAmount , DepositStartDate ,DepositEndDate ,DepositType, BGNumber ,"
			+ "DepositPeriod,DepositExtensionDate,DepositLedgerNumber, DepositSubmitter,DepositStatus,DepositRecievedDate,DepositRecievedNote from depositdetail where DepositId = ?";

	public static final String DELETEPROJECTBYPROJECTID = "DELETE FROM project WHERE ProjId = ?";

	public static final String DELETESUBPROJECTBYPROJECTID = "DELETE FROM subproject WHERE ProjId = ?";

	public static final String DELETESUBPROJECTBYSUBPROJECTID = "DELETE FROM subproject WHERE SubProjId = ?";

	public static final String DELETE_DEPOSIT_DETAIL_BY_PROJECTID = "DELETE FROM depositdetail WHERE ProjId = ?";

	public static final String DELETE_DEPOSIT_DETAIL_BY_SUB_PROJECTID = "DELETE FROM depositdetail WHERE SubProjId = ?";

	public static final String DELETEPROJECTDESCRIPTIONBYPROJECTID = "DELETE FROM projectdesc WHERE ProjId = ?";

	public static final String DELETEPROJECTDESCRIPTIONBYSUBPROJECTID = "DELETE FROM projectdesc WHERE SubProjId = ?";

	public static final String DELETE_DEPOSIT_DETAIL_BY_DEPOSIT_ID = "DELETE FROM depositdetail WHERE DepositId = ?";

	public static final String GET_DEPOSIT_DETAILS_BY_PROJECTID = "select e.DepositId ,e.ProjId , p.AliasProjName, e.DepositType , e.DepositAmount , e.DepositStartDate , e.DepositEndDate "
			+ "                from depositdetail e  left join project as p on e.ProjId = p.ProjId "
			+ "                where e.ProjId = ? and e.SubProjId is null ";

	public static final String GET_DEPOSIT_DETAILS_BY_SUBPROJECTID = "select e.DepositId ,e.ProjId , p.AliasProjName , s.AliasSubProjName ,"
			+ "     s.SubProjName , e.DepositType , e.DepositAmount , e.DepositStartDate , e.DepositEndDate "
			+ "    from depositdetail e left join project as p on e.ProjId = p.ProjId "
			+ "    left join subproject as s on e.SubProjId=s.SubProjId where e.SubProjId=?";


	public static String projDescDetailQuery = "SELECT ProjId, SubProjId, SerialNumber, WorkType, Quantity, Metric,"
			+ "Description, AliasDescription, PricePerQuantity, TotalCost, ProjDescId FROM projectdesc";

	public static String compareDataQuery = "SELECT distinct p.SerialNumber, p.Quantity, p.Metric, p.AliasDescription, p.PricePerQuantity, p.TotalCost, pp.PricePerQuantity as DeptPricePerQuantity, pp.TotalCost as DeptTotalCost "
			+ "FROM projectdesc p INNER JOIN quotedprojectdesc pp on p.ProjId = pp.ProjId "
			+ "and p.AliasDescription = pp.AliasDescription";

	public static String projDescDetail = "SELECT d.ProjId, d.SubProjId, d.SerialNumber, d.WorkType, d.Quantity, d.Metric,"
			+ "d.Description, d.AliasDescription, d.PricePerQuantity, d.TotalCost, d.ProjDescId";

	public static String baseDescDetail = "SELECT BaseDescId, WorkType, Metric, Quantity, PricePerQuantity, LastUpdatedBy, LastUpdatedAt, Description, BaseDescription FROM basedesc";
	
	public static String deleteProjDescDetailQuery = "DELETE FROM projectdesc where ProjDescId = ?";

	public static String DEPOSIT_DATE_QUERY = "select DepositAmount, DepositStartDate, DepositEndDate, DepositType, DepositExtensionDate from depositdetail";

	public static String DELETEPROJDESCITEMBYPROJECTID = "DELETE FROM projdescitem WHERE ProjId = ?";

	public static String DELETEPROJDESCITEMBYSUBPROJECTID = "DELETE FROM  projdescitem WHERE SubProjId = ?";

	public static String DELETEPROJDESCAITEMBYPROJECTDESCID = "DELETE FROM projdescitem WHERE ProjDescId = ?";

	public static String DELETEPROJDESCAITEMBYPROJECTDESCITEMID = "DELETE FROM projdescitem WHERE DescItemId = ?";

	public static String FETCHITEMTYPES = "SELECT itemTypeName FROM itemtype";

	public static String FETCH_DEPOSIT_TYPES = "SELECT depositTypeName FROM deposittype";

	public static String FETCHUNIQUEITEMUNIT = "SELECT DISTINCT itemUnit FROM itemcodes";

	public static String INSERTSUBPROJECTDESCRIPTION = "INSERT INTO projectDesc (ProjId, SubProjId,SerialNumber ,WorkType, Quantity, Metric, "
			+ "Description, AliasDescription,LastUpdatedBy ,LastUpdatedAt) "
			+ "VALUES (?, ? , ?, ?, ?, ?, ?, ?, ?, ?)";

	public static String INSERTPROJECTDESCRIPTION = "INSERT INTO projectDesc (ProjId, SerialNumber, WorkType, Quantity, Metric, "
			+ "Description, AliasDescription, LastUpdatedBy, LastUpdatedAt, PricePerQuantity, TotalCost) "
			+ "VALUES (?, ?, ? , ?, ?, ?, ?, ?, ?, ?, ?)";

	public static String INSERTGOVPROJECTDESCRIPTION = "INSERT INTO quotedprojectdesc (ProjId, SerialNumber, WorkType, Quantity, Metric, "
			+ "Description, AliasDescription, LastUpdatedBy, LastUpdatedAt, PricePerQuantity, TotalCost) "
			+ "VALUES (?, ?, ? , ?, ?, ?, ?, ?, ?, ?, ?)";

	public static String NOOFPROJECTDESCASSOCIATEDTOPROJECT = "SELECT count(*) FROM projectdesc WHERE ProjId = ?";
	
	public static String NOOFQUOTEDPROJECTDESCASSOCIATEDTOPROJECT = "SELECT count(*) FROM quotedprojectdesc WHERE ProjId = ?";

	public static String NOOFPROJECTDESCASSOCIATEDTOSUBPROJECT = "SELECT count(*) FROM projectdesc WHERE SubProjId = ?";
	
	public static String NOOFQUOTEDPROJECTDESCASSOCIATEDTOSUBPROJECT = "SELECT count(*) FROM quotedprojectdesc WHERE SubProjId = ?";

	public static String SAVEITEMS = "INSERT INTO itemcodes (itemName, itemUnit ,itemType) VALUES (?, ?, ?)";

    public static String UPDATEBASEDESCRIPTION ="UPDATE basedesc set Category = ?, WorkType = ?, Metric = ?, LastUpdatedBy = ?, LastUpdatedAt = ?, Description= ? where BaseDescription = ? ";

	public static String FETCHPROJECTTYPES = "SELECT projectTypeName FROM projecttype";

	public static String DEACTIVATEEXISTINGPRICES = "UPDATE pricedetail SET active ='0' WHERE projectId = ? AND subProjectId = ?";

	public static String INSERTPRICEFORITEMS = "INSERT INTO pricedetail (projectId, subProjectId, itemName, itemUnit, itemPrice, itemType, priceFeed) "
			+ "VALUES (?, ?, ?, ?, ?, ?, ?)";

	public static String INSERTBASEDESCRIPTION = "INSERT INTO basedesc(Category, WorkType, Metric, Quantity, PricePerQuantity , LastUpdatedBy, LastUpdatedAt, Description, BaseDescription) values (?, ?, ?, ?, ?, ?, ?, ?, ?)";

	public static String FETCHBASEDESCRIPTIONS = "SELECT * FROM basedesc";

	public static String DELETEBASEDESCRIPTION = "DELETE FROM basedesc WHERE BaseDescId = ?";

    public static String ISBASEDESCEXISTS = "SELECT count(*) FROM basedesc WHERE category = ? and BaseDescription = ?";

    public static String GETBASEDESCRIPTION = "SELECT * FROM basedesc WHERE BaseDescId = ?";

    public static String INSERT_ITEM_RATE_DESCRIPTION = "INSERT INTO govestpricedetail (itemName, itemUnit, itemType, itemPrice, priceFeed,scheduledItemNumber,itemDescription) "
            + "VALUES (?, ?, ?, ?, ?, ?, ?)";


    public static String SAVE_ROLES = "INSERT INTO teamRole (teamName, roleName) VALUES (?, ?)";

    public static String GET_ALL_EMPLOYEE = "SELECT * FROM employee where empTeam != 'Admin'";

    public static String DELETE_EMPLOYEE = "DELETE FROM employee WHERE empId = ? ";


    public static String GET_ROLES = "select roleName from teamRole where teamName = ?";


}
