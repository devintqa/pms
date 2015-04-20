package com.psk.pms.service;

import java.util.List;

import com.psk.pms.dao.EmployeeDAO;
import com.psk.pms.model.Employee;
import com.psk.pms.utils.Encryption;
import com.psk.pms.utils.PMSMail;

public class EmployeeServiceImpl implements EmployeeService {
		
	private EmployeeDAO employeeDAO;
	private PMSMail pmsMail;

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
		if(isInsertSuccessful){
			//pmsMail.sendMail(employee.getEmployeeMail(), employee.getEmployeeId());
		}
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
	
	private String getUserName(String employeeFName, String employeeLName) {
		String firstChar = employeeFName.substring(0, 1).toLowerCase();
		String userLastName = employeeLName.toLowerCase();
		String userName = firstChar.concat(userLastName);
		String loopUserName = userName;
		List<String> existingEmpList = employeeDAO.getUserNames(userName);
		if(existingEmpList.size()>0){
			System.out.println("Employee User Name Pattern Exists!");
			int i = 1;
			for(String empName : existingEmpList){
				if(loopUserName.equalsIgnoreCase(empName)){
					System.out.println("Existing Employee Name :" + empName);
					loopUserName = new StringBuilder(userName).append(i).toString();
					System.out.println("Appended Employee Name :" + empName);
					i++;
				}
			}
			i = i-1;
			userName = new StringBuilder(userName).append(i).toString();
		}
		return userName;
	}
	
	public void setEmployeeDAO(EmployeeDAO employeeDAO) {
		this.employeeDAO = employeeDAO;
	}
	
	public void setPmsMail(PMSMail pmsMail) {
		this.pmsMail = pmsMail;
	}

}
