package com.psk.pms.service;

import java.util.List;
import java.util.Map;

import com.psk.pms.model.Employee;

public interface EmployeeService {
	
	public boolean isValidLogin(String userName, String password);
	
	public boolean isEmployeeExisting(String userName);
	
	public boolean signupEmployee(Employee employee);
	
	public Map<String, String> fetchTeamNames();
	
	public Employee getEmployeeDetails(String empId);
	
	public boolean updateEmployee(Employee employee);

	public String getUserRole(String empId);
	
	public boolean isEmployeeMotherMaidenExisting(String userName,String motherMaiden);
	
	public boolean isEmployeeMailExisting(String mail);
	
	public boolean isEmployeeMobNumExisting(String mobile);

	public boolean resetPassword(String userName, String password);
	
	public List<Employee> getNewRegistrationRequest(String fromDate);
	
	public int manageUserAccess(Employee employee);


}
