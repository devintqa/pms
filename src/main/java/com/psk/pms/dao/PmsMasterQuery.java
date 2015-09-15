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
			+ "FROM projectdesc p INNER JOIN quotedprojectdesc pp on p.ProjId = pp.ProjId "
			+ "and p.AliasDescription = pp.AliasDescription";

	public static final String projDescDetail = "SELECT d.ProjId, d.SubProjId, d.SerialNumber, d.WorkType, d.Quantity, d.Metric,"
			+ "d.Description, d.AliasDescription, d.PricePerQuantity, d.TotalCost, d.ProjDescId";

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
			+ "Description, AliasDescription, LastUpdatedBy, LastUpdatedAt, PricePerQuantity, TotalCost) "
			+ "VALUES (?, ?, ? , ?, ?, ?, ?, ?, ?, ?, ?)";

	public static final String INSERTGOVPROJECTDESCRIPTION = "INSERT INTO quotedprojectdesc (ProjId, SerialNumber, WorkType, Quantity, Metric, "
			+ "Description, AliasDescription, LastUpdatedBy, LastUpdatedAt, PricePerQuantity, TotalCost) "
			+ "VALUES (?, ?, ? , ?, ?, ?, ?, ?, ?, ?, ?)";

	public static final String NOOFPROJECTDESCASSOCIATEDTOPROJECT = "SELECT count(*) FROM projectdesc WHERE ProjId = ?";
	
	public static final String NOOFQUOTEDPROJECTDESCASSOCIATEDTOPROJECT = "SELECT count(*) FROM quotedprojectdesc WHERE ProjId = ?";

	public static final String NOOFPROJECTDESCASSOCIATEDTOSUBPROJECT = "SELECT count(*) FROM projectdesc WHERE SubProjId = ?";
	
	public static final String NOOFQUOTEDPROJECTDESCASSOCIATEDTOSUBPROJECT = "SELECT count(*) FROM quotedprojectdesc WHERE SubProjId = ?";

	public static final String SAVEITEMS = "INSERT INTO itemcodes (itemName, itemUnit ,itemType) VALUES (?, ?, ?)";

    public static final String UPDATEBASEDESCRIPTION ="UPDATE basedesc set WorkType = ?, Metric = ?, LastUpdatedBy = ?, LastUpdatedAt = ?, Description= ? where BaseDescription = ? ";

	public static final String DEACTIVATEEXISTINGPRICES = "UPDATE pskpricedetail SET active ='0' WHERE projectId = ? AND subProjectId = ?";

	public static final String INSERTPRICEFORITEMS = "INSERT INTO pskpricedetail (projectId, subProjectId, itemName, itemUnit, itemPrice, itemType, priceFeed) "
			+ "VALUES (?, ?, ?, ?, ?, ?, ?)";

	public static final String INSERTBASEDESCRIPTION = "INSERT INTO basedesc(Category, WorkType, Metric, Quantity, PricePerQuantity , LastUpdatedBy, LastUpdatedAt, Description, BaseDescription) values (?, ?, ?, ?, ?, ?, ?, ?, ?)";

	public static final String FETCHBASEDESCRIPTIONS = "SELECT * FROM basedesc";

	public static final String DELETEBASEDESCRIPTION = "DELETE FROM basedesc WHERE BaseDescId = ?";

    public static final String ISBASEDESCEXISTS = "SELECT * FROM basedesc WHERE BaseDescription = ?";

    public static final String GETBASEDESCRIPTION = "SELECT * FROM basedesc WHERE BaseDescId = ?";

    public static final String INSERT_ITEM_RATE_DESCRIPTION = "INSERT INTO govpricedetail (itemName, itemUnit, itemType, itemPrice, priceFeed,scheduledItemNumber,itemDescription) "
            + "VALUES (?, ?, ?, ?, ?, ?, ?)";


    public static final String SAVE_ROLES = "INSERT INTO teamRole (teamName, roleName) VALUES (?, ?)";

    public static final String GET_ALL_EMPLOYEE = "SELECT * FROM employee where empTeam != 'Admin'";

    public static final String DELETE_EMPLOYEE = "DELETE FROM employee WHERE empId = ? ";


    public static final String GET_ROLES = "select roleName from teamRole where teamName = ?";


    public static final String GET_DROP_DOWN_VALUES = "select Value from pmsmastertable where Type = ?";


}
