package com.psk.pms.service;

import com.psk.pms.model.Employee;

public interface EmployeeService {
	
	public boolean isValidLogin(String userName, String password);
	
	public boolean isEmployeeExisting(String userName);
	
	public boolean signupEmployee(Employee employee);
	
	public Employee getEmployeeDetails(String empId);
	
	public boolean updateEmployee(Employee employee);

	public String getUserRole(String empId);
	
	public boolean isEmployeeMotherMaidenExisting(String userName,String motherMaiden);

	public boolean resetPassword(String userName, String password);

}
