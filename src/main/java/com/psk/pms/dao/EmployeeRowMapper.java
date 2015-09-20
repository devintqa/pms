package com.psk.pms.dao;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;

import com.psk.pms.model.Employee;

public class EmployeeRowMapper implements RowMapper {

	public Object mapRow(ResultSet rs, int rowNum) throws SQLException {
		Employee employee = new Employee();
		employee.setEmployeeId(String.valueOf(rs.getString("empId")));
		employee.setEmployeePwd(rs.getString("empPassword"));
		employee.setEmployeeFName(rs.getString("empFirstName"));
		employee.setEmployeeLName(rs.getString("empLastName"));
		employee.setEmployeeTeam(rs.getString("empTeam"));
		employee.setEmployeeAddress(rs.getString("empAddress"));
		employee.setEmployeeMail(rs.getString("empMail"));
		employee.setEmployeeMobile(rs.getString("empMobNum"));
        employee.setEmployeeRole(rs.getString("empRole"));
		return employee;
	}
}
