package com.psk.pms.dao;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.datasource.DriverManagerDataSource;

import com.psk.pms.model.ProjectDetail;

public class ProjectDAOImpl implements ProjectDAO {
	
	private DriverManagerDataSource dataSource;
	private JdbcTemplate jdbcTemplate;
	
	public void setDataSource(DriverManagerDataSource dataSource) {
		this.dataSource = dataSource;
	}
	
	public boolean saveProject(final ProjectDetail projectDetail){
		String sql = "INSERT INTO project" +
		"(ProjName, AliasProjName, AgreementNum, CERNum, Amount, ContractorName, ContractorAdd, ContractorValue, AgreementValue, TenderValue, ExcessInAmount, ExcessInPercentage, TenderDate, AgreementDate, CommencementDate, CompletedDate, AgreementPeriod) " +
		"VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
		jdbcTemplate = new JdbcTemplate(dataSource);
		java.util.Date TenderDate = projectDetail.getTenderDate();
		java.util.Date AgreementDate = projectDetail.getAgreementDate();
		java.util.Date CommencementDate = projectDetail.getCommencementDate();
		java.util.Date CompletionDate = projectDetail.getCompletionDate();	
		jdbcTemplate.update(sql, new Object[] { projectDetail.getProjectName(),
				projectDetail.getAliasName(),projectDetail.getAgreementNo(), projectDetail.getCerNo(), projectDetail.getAmount(),
				projectDetail.getContractorName(), projectDetail.getContractorAddress(),
				projectDetail.getContractValue(), projectDetail.getAgreementValue(), projectDetail.getTenderValue(), projectDetail.getExAmount(),
				projectDetail.getExPercentage(), new java.sql.Date(TenderDate.getTime()), new java.sql.Date(AgreementDate.getTime()),
				new java.sql.Date(CommencementDate.getTime()), new java.sql.Date(CompletionDate.getTime()), Integer.valueOf(projectDetail.getAgreementPeriod())
		});
		return true;
	}
	
	public boolean editProject(final ProjectDetail projectDetail){
		/*String sql = "update cabdetail set subProject = ?, won = ?, pickupTime = ?, dropTime = ?, area = ?, dropRequestDate =? where empId = ? and requestDate = ?";
	jdbcTemplate = new JdbcTemplate(dataSource);	
	jdbcTemplate.batchUpdate(sql, new BatchPreparedStatementSetter(){	 
		@Override
		public void setValues(PreparedStatement ps, int i) throws SQLException {
			ProjectDetail cabDetail = cabDetails.get(i);
			ps.setString(1, cabDetail.getSubProject());		
			ps.setString(2, cabDetail.getWon());
			ps.setString(3, cabDetail.getPickupTime());
			ps.setString(4, cabDetail.getDropTime());
			ps.setString(5, cabDetail.getArea());
			Calendar c = Calendar.getInstance();
			java.util.Date requestedDate = cabDetail.getRequestCabDate();
			if(cabDetail.getDropTime().contains("AM") && cabDetail.getPickupTime().contains("PM"))
			{
				c.setTime(requestedDate);
				c.add(Calendar.DATE, 1);
				requestedDate = c.getTime();
			}
			ps.setDate(6, new java.sql.Date(requestedDate.getTime()));	
			ps.setInt(7, Integer.valueOf(cabDetail.getEmpId()));			
			ps.setDate(8, new java.sql.Date(cabDetail.getRequestCabDate().getTime()));
		} 
		@Override
		public int getBatchSize() {
			return cabDetails.size();
		}
	  });		*/
		return true;
	}
}
