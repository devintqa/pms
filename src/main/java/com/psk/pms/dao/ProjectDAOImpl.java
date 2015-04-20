package com.psk.pms.dao;

import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.Calendar;
import java.util.List;

import org.springframework.jdbc.core.BatchPreparedStatementSetter;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.datasource.DriverManagerDataSource;

import com.psk.pms.model.ProjectDetail;

public class ProjectDAOImpl implements ProjectDAO {
	
	private DriverManagerDataSource dataSource;
	private JdbcTemplate jdbcTemplate;
	
	public void setDataSource(DriverManagerDataSource dataSource) {
		this.dataSource = dataSource;
	}
	
	public boolean saveProject(final List<ProjectDetail> cabDetails){
		/*String sql = "INSERT INTO cabdetail" +
		"(empId, project, subProject, won, requestDate, dropRequestDate, pickupTime, dropTime, area) " +
		"VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?)";
	jdbcTemplate = new JdbcTemplate(dataSource);	
	jdbcTemplate.batchUpdate(sql, new BatchPreparedStatementSetter(){	 
		@Override
		public void setValues(PreparedStatement ps, int i) throws SQLException {
			ProjectDetail cabDetail = cabDetails.get(i);
			ps.setInt(1, Integer.valueOf(cabDetail.getEmpId()));
			ps.setString(2, cabDetail.getProject());
			ps.setString(3, cabDetail.getSubProject());		
			ps.setString(4, cabDetail.getWon());
			java.util.Date requestedDate = cabDetail.getRequestCabDate();			
			ps.setDate(5, new java.sql.Date(requestedDate.getTime()));
			Calendar c = Calendar.getInstance();
			if(cabDetail.getDropTime().contains("AM") && cabDetail.getPickupTime().contains("PM"))
			{
				c.setTime(requestedDate);	
				c.add(Calendar.DATE, 1);
				requestedDate = c.getTime();
			}
			ps.setDate(6, new java.sql.Date(requestedDate.getTime()));
			ps.setString(7, cabDetail.getPickupTime());
			ps.setString(8, cabDetail.getDropTime());
			ps.setString(9, cabDetail.getArea());
		} 
		@Override
		public int getBatchSize() {
			return cabDetails.size();
		}
	  });		*/
		return true;
	}
	
	public boolean editProject(final List<ProjectDetail> cabDetails){
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
