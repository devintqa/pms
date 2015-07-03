package com.psk.pms.dao;

public class PMSMasterQuery {
	
	public static final String CREATEEMDDETAIL = "INSERT INTO emddetail (ProjId , SubProjId , EmdAmount , EmdStartDate ,EmdEndDate ,EmdType, BGNumber ," +
    "EmdPeriod,EmdExtensionDate,EmdLedgerNumber, LastUpdatedBy,LastUpdatedAt,EmdSubmitter)" +
    "VALUES ( ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? ,?)";

	public static final String UPDATEEMDDETAIL = "UPDATE emddetail set ProjId = ? , SubProjId = ? ,EmdAmount = ? , EmdStartDate = ? ,EmdEndDate = ?,EmdType =? , BGNumber =?," +
    "EmdPeriod = ?,EmdExtensionDate = ? ,EmdLedgerNumber =? , " +
    "LastUpdatedBy = ?,LastUpdatedAt =? ,EmdSubmitter =? WHERE EmdId = ? ";
	
	public static final String FETCHEMDDETAILS = "select e.EmdId ,e.ProjId , p.AliasProjName , s.AliasSubProjName ," +
    " s.SubProjName , e.EmdType , e.EmdAmount , e.EmdStartDate , e.EmdEndDate " +
    "from emddetail e left join project as p on e.ProjId = p.ProjId " +
    "left join subproject as s on e.SubProjId=s.SubProjId ";
	
	public static final String FETCHEMDDETAILBYEMDID = "select  ProjId , SubProjId , EmdAmount , EmdStartDate ,EmdEndDate ,EmdType, BGNumber ," +
    "EmdPeriod,EmdExtensionDate,EmdLedgerNumber, EmdSubmitter from emddetail where EmdId = ?";

    public static final String DELETEPROJECTBYPROJECTID = "DELETE FROM project WHERE ProjId = ?";

    public static final String DELETESUBPROJECTBYPROJECTID = "DELETE FROM subproject WHERE ProjId = ?";

    public static final String DELETESUBPROJECTBYSUBPROJECTID = "DELETE FROM subproject WHERE SubProjId = ?";

    public static final String DELETEEMDDETAILBYPROJECTID = "DELETE FROM emddetail WHERE ProjId = ?";

    public static final String DELETEEMDDETAILBYSUBPROJECTID = "DELETE FROM emddetail WHERE SubProjId = ?";

    public static final String DELETEPROJECTDESCRIPTIONBYPROJECTID = "DELETE FROM projectdesc WHERE ProjId = ?";

    public static final String DELETEPROJECTDESCRIPTIONBYSUBPROJECTID = "DELETE FROM projectdesc WHERE SubProjId = ?";

    public static final String DELTEPEMDDETAILBYEMDID = "DELETE FROM emddetail WHERE EmdId = ?";

}
