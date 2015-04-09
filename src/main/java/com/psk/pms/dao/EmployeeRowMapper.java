package com.psk.pms.dao;

import java.sql.ResultSet;
import java.sql.SQLException;
 


import org.springframework.jdbc.core.RowMapper;

import com.psk.pms.model.Employee;

@SuppressWarnings("rawtypes")
public class EmployeeRowMapper implements RowMapper {

	public Object mapRow(ResultSet rs, int rowNum) throws SQLException {
		Employee employee = new Employee();
		employee.setEmpId(String.valueOf(rs.getInt("empId")));
		employee.setEmpPassword(rs.getString("empPassword"));
		employee.setEmployeeFName(rs.getString("empFirstName"));
		employee.setEmployeeLName(rs.getString("empLastName"));
		employee.setEmployeeAddress(rs.getString("empAddress"));
		employee.setEmployeeDOJ(rs.getString("empDOJ"));
		employee.setEmployeeMail(rs.getString("empMail"));
		employee.setEmployeeMobile(rs.getString("empMobNum"));
		employee.setEmployeeTeam(rs.getString("empTeam"));
		return employee;
	}
}
