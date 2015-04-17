package com.psk.pms.service;

import java.util.List;

import com.psk.pms.dao.EmployeeDAO;
import com.psk.pms.model.Employee;
import com.psk.pms.utils.Encryption;

public class EmployeeServiceImpl implements EmployeeService {
		
	private EmployeeDAO employeeDAO;

	public boolean isValidLogin(String userName, String password){	
		int total = employeeDAO.getEmployeeLoginDetails(userName, password);
		if(total == 0){
			return false;
		}
		return true;
	}
	
	public List<Employee> getNewRegistrationRequest(String fromDate){
		
		List<Employee> newRequests= employeeDAO.getNewRegistrationRequest(fromDate);
		return newRequests;
		
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
		employee.setEmployeePwd(Encryption.doPasswordEncode(employee.getEmployeePwd()));
		employee.setEmployeeId(getUserName(employee.getEmployeeFName(),employee.getEmployeeLName()));
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
		String hashedPassword = Encryption.doPasswordEncode(password);
		isAvailable = employeeDAO.resetpassword(userName, hashedPassword);
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
	
	public String getUserName(String employeeFName, String employeeLName) {
		String fname ="";
		String userName ="";
		fname = employeeFName.substring(0, 1);
		userName = fname.concat(employeeLName);
		int total = employeeDAO.isUserNameExists(userName);
		for(int x = 1; total != 0; x++) {
			String tempUserName = new StringBuilder(userName).append(x).toString();
			total = employeeDAO.isUserNameExists(tempUserName);
			if (total == 0){
				userName = tempUserName;	
			}
		}
		return userName;
	}

}
