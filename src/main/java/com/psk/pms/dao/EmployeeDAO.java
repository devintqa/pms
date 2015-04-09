package com.psk.pms.dao;

import com.psk.pms.model.Employee;

public interface EmployeeDAO {

	public int getEmployeeLoginDetails(String userName, String password);

	public int isEmployeeExisting(String userName);

	public boolean signupEmployee(Employee employee);

	public Employee getEmployeeDetails(String empId);

	public boolean updateEmployee(Employee employee);

	public String getUserRole(String empId);

	public boolean isEmployeeDOJExisting(String userName, String DOJ);

	public boolean isEmployeeMotherMaidenExisting(String userName,	String motherMaiden);

	public boolean resetpassword(String userName, String password);

}
