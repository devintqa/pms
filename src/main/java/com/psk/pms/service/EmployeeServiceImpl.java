package com.psk.pms.service;

import com.psk.pms.dao.EmployeeDAO;
import com.psk.pms.model.Employee;
import com.psk.pms.utils.Encryption;
import com.psk.pms.utils.MailClient;

import org.apache.log4j.Logger;

import java.util.List;
import java.util.Map;

public class EmployeeServiceImpl implements EmployeeService {
		
	private EmployeeDAO employeeDAO;
	private MailClient mailClient;

	@SuppressWarnings("unused")
	private static final Logger LOGGER = Logger.getLogger(EmployeeServiceImpl.class);

	public boolean isValidLogin(String userName, String password){	
		int total = employeeDAO.getEmployeeLoginDetails(userName, password);
		if(total == 0){
			return false;
		}
		return true;
	}
	
	public Map<String, String> fetchTeamNames(){
		Map<String, String> teamList = employeeDAO.fetchTeamNames();
		return teamList;
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
		employee.setEmployeePwd(Encryption.doPasswordEncode(employee.getEmployeePwd()));
		employee.setEmployeeId(getUserName(employee.getEmployeeFName(),employee.getEmployeeLName()));
		isInsertSuccessful = employeeDAO.signupEmployee(employee);
		if(isInsertSuccessful){
			mailClient.sendMail(employee.getEmployeeMail(), employee.getEmployeeId());
		}
		return isInsertSuccessful;
	}
	
	public boolean isEmployeeMotherMaidenExisting(String userName,String motherMaiden){
		boolean isAvailable = false;
		isAvailable = employeeDAO.isEmployeeMotherMaidenExisting(userName, motherMaiden);
		return isAvailable;
	}
	

	public boolean isEmployeeMailExisting(String mail) {
		boolean isAvailable = false;
		isAvailable = employeeDAO.isEmployeeMailExisting(mail);
		return isAvailable;
	}

	public boolean isEmployeeMobNumExisting(String mobile) {
		boolean isAvailable = false;
		isAvailable = employeeDAO.isEmployeeMobNumExisting(mobile);
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
	
	private String getUserName(String employeeFName, String employeeLName) {
		String firstChar = employeeFName.substring(0, 1).toLowerCase();
		String userLastName = employeeLName.toLowerCase();
		String userName = firstChar.concat(userLastName);
		int count = employeeDAO.getUserNamePatternCount(userName);
		if(count == 0){
			return userName;
		}else{
			userName = userName.concat(String.valueOf(count));
		}
		return userName;
	}
	
	public void setEmployeeDAO(EmployeeDAO employeeDAO) {
		this.employeeDAO = employeeDAO;
	}
	
	public void setMailClient(MailClient mailClient) {
		this.mailClient = mailClient;
	}

	@Override
	public int manageUserAccess(Employee employee) {
		int status = employeeDAO.manageUserAccess(employee);
		//mailClient.sendAccessMail(employee.getEmployeeMail(), employee.getEmployeeId(), employee.getEnabled());
		return status;
	}

}
