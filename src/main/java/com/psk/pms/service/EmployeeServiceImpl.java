package com.psk.pms.service;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import com.psk.pms.dao.EmployeeDAO;
import com.psk.pms.model.Employee;

public class EmployeeServiceImpl implements EmployeeService {
		
	private EmployeeDAO employeeDAO;

	public boolean isValidLogin(String userName, String password){	
		System.out.println("in dao userName: "+userName);
		int total = employeeDAO.getEmployeeLoginDetails(userName, password);
		if(total == 0){
			return false;
		}
		return true;
	}
	
	public String getUserRole(String empId){
		return employeeDAO.getUserRole(empId);
	}
	
	public Employee getEmployeeDetails(String empId){
		return employeeDAO.getEmployeeDetails(empId);
	}
	
	public boolean isEmployeeExisting(String userName){	
		int total = employeeDAO.isEmployeeExisting(userName);
		if(total == 0){
			return false;
		}
		return true;
	}
	
	public boolean signupEmployee(Employee employee){
		boolean isInsertSuccessful = false;
		if("M".equalsIgnoreCase(employee.getEmployeeGender())){
			employee.setEmployeeGender("Male");
		} else {
			employee.setEmployeeGender("Female");
		}
		BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
		String hashedPassword = passwordEncoder.encode(employee.getEmployeePwd());
		employee.setEmployeePwd(hashedPassword);
		employee.setEmpId("112233");
		isInsertSuccessful = employeeDAO.signupEmployee(employee);
		return isInsertSuccessful;
	}
	
	public boolean isEmployeeMotherMaidenExisting(String userName,String motherMaiden){
		boolean isAvailable = false;
		isAvailable = employeeDAO.isEmployeeMotherMaidenExisting(userName, motherMaiden);
		return isAvailable;
	}
	
	public boolean resetPassword(String userName, String password)
	{
		boolean isAvailable = false;
		isAvailable = employeeDAO.resetpassword(userName, password);
		return isAvailable;
	}
	public boolean updateEmployee(Employee employee){
		boolean isInsertSuccessful = false;
		isInsertSuccessful = employeeDAO.updateEmployee(employee);
		return isInsertSuccessful;
	}
	
	
	public void setEmployeeDAO(EmployeeDAO employeeDAO) {
		this.employeeDAO = employeeDAO;
	}

}
