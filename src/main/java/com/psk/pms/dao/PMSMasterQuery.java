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

    public static final String GETEMDDETAILSBYPROJECTID = "select e.EmdId ,e.ProjId , p.AliasProjName, e.EmdType , e.EmdAmount , e.EmdStartDate , e.EmdEndDate " +
            "                from emddetail e  left join project as p on e.ProjId = p.ProjId " +
            "                where e.ProjId = ? and e.SubProjId is null ";

    public static final String GETEMDDETAILSBYSUBPROJECTID =
            "select e.EmdId ,e.ProjId , p.AliasProjName , s.AliasSubProjName ," +
            "     s.SubProjName , e.EmdType , e.EmdAmount , e.EmdStartDate , e.EmdEndDate " +
            "    from emddetail e left join project as p on e.ProjId = p.ProjId " +
            "    left join subproject as s on e.SubProjId=s.SubProjId where e.SubProjId=?";

    public static String projDescDetailQuery = "SELECT ProjId, SubProjId, SerialNumber , WorkType, QuantityInFig, QuantityInWords, "
            + "Description, AliasDescription, RateInFig, RateInWords, Amount, ProjDescId FROM projectdesc";

    public static String projDescDetail = "SELECT d.ProjId, d.SubProjId , d.SerialNumber , d.WorkType, d.QuantityInFig, d.QuantityInWords, "
            + "d.Description, d.AliasDescription, d.RateInFig, d.RateInWords, d.Amount, d.ProjDescId";

    public static String deleteProjDescDetailQuery = "DELETE FROM projectdesc where ProjDescId = ?";

    public static String emdDatesQuery = "select EmdAmount, EmdStartDate, EmdEndDate, EmdType, EmdExtensionDate from emddetail";
}
