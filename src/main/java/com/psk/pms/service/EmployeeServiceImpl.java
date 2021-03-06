package com.psk.pms.service;

import java.util.HashMap;
import java.util.List;

import org.apache.log4j.Logger;
import org.springframework.scheduling.annotation.Async;

import com.psk.pms.Constants;
import com.psk.pms.dao.EmployeeDAO;
import com.psk.pms.model.Employee;
import com.psk.pms.model.Team;
import com.psk.pms.utils.Encryption;
import com.psk.pms.utils.MailClient;

public class EmployeeServiceImpl implements EmployeeService {

	private EmployeeDAO employeeDAO;
	private MailClient mailClient;

	private static final Logger LOGGER = Logger.getLogger(EmployeeServiceImpl.class);

	public boolean isValidLogin(String userName, String password) {
		int total = employeeDAO.getEmployeeLoginDetails(userName, password);
		if (total == 0) {
			return false;
		}
		return true;
	}

	public List<Employee> getNewRegistrationRequest(String fromDate) {

		List<Employee> newRequests = employeeDAO
				.getNewRegistrationRequest(fromDate);
		return newRequests;

	}

	public String getUserRole(String empId) {
		return employeeDAO.getUserRole(empId);
	}

	public Employee getEmployeeDetails(String empId) {
		Employee employee = employeeDAO.getEmployeeDetails(empId);
		if(employee.getEmployeeRole() == null){
			employee.setEmployeeRole(Constants.NO_ROLE_TAGGED);
		}
		return employee;
	}

	public boolean isEmployeeExisting(String userName) {
		int total = employeeDAO.isEmployeeExisting(userName);
		if (total == 0) {
			return false;
		}
		return true;
	}

	public boolean signupEmployee(Employee employee) {
		boolean isInsertSuccessful = false;
		employee.setEmployeePwd(Encryption.doPasswordEncode(employee
				.getEmployeePwd()));
		employee.setEmployeeId(getUserName(employee.getEmployeeFName(),
				employee.getEmployeeLName()));
		isInsertSuccessful = employeeDAO.signupEmployee(employee);
		if (isInsertSuccessful) {
			mailClient.sendMail(employee.getEmployeeMail(),
					employee.getEmployeeId());
		}
		return isInsertSuccessful;
	}

	public boolean isEmployeeMotherMaidenExisting(String userName,
			String motherMaiden) {
		boolean isAvailable = false;
		isAvailable = employeeDAO.isEmployeeMotherMaidenExisting(userName,
				motherMaiden);
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

	public boolean resetPassword(String userName, String password) {
		boolean isAvailable = false;
		String hashedPassword = Encryption.doPasswordEncode(password);
		isAvailable = employeeDAO.resetpassword(userName, hashedPassword);
		return isAvailable;
	}

	public boolean updateEmployee(Employee employee) {
		boolean isInsertSuccessful = false;
		isInsertSuccessful = employeeDAO.updateEmployee(employee);
		return isInsertSuccessful;
	}

	private String getUserName(String employeeFName, String employeeLName) {
		String firstChar = employeeFName.substring(0, 1).toLowerCase();
		String userLastName = employeeLName.toLowerCase();
		String userName = firstChar.concat(userLastName);
		int count = employeeDAO.getUserNamePatternCount(userName);
		if (count == 0) {
			return userName;
		} else {
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
	public int manageUserAccess(HashMap<String, String> accessDetails) {
        Employee employee = new Employee();
        String action = "";
        if ("enable".equalsIgnoreCase(accessDetails.get("action"))) {
            action = Constants.ENABLE_EMPLOYEE_ACCESS;
        } else if ("deny".equalsIgnoreCase(accessDetails.get("action"))) {
            action = Constants.DENY_EMPLOYEE_ACCESS;
        } else {
            action = Constants.DISABLE_EMPLOYEE_ACCESS;
        }
        employee.setEnabled(action);
        employee.setEmployeeId(accessDetails.get("user"));
		int status = employeeDAO.manageUserAccess(employee);
		sendAsyncEmployeeStatusMail(action, employee.getEmployeeId());
		return status;
	}
	
	@Async
	private void sendAsyncEmployeeStatusMail(String action, String employeeId){
		Employee employee = getEmployeeDetails(employeeId);
        LOGGER.info("Employee Id :" + employee.getEmployeeId()
        + " Employee Enabled : " + action
        + " Employee mail Id: " + employee.getEmployeeMail());
		mailClient.sendAccessMail(employee.getEmployeeMail(),
		employee.getEmployeeId(), action);
	}


    @Override
    public boolean isRoleExists(String roleName, String teamName) {
        return employeeDAO.isRoleExists(roleName,teamName);
    }


    @Override
    public boolean saveTeamRoles(Team team) {
        return employeeDAO.saveTeamRoles(team);
    }

    @Override
    public List<Employee> getAllEmployeeDetails(){
        return employeeDAO.getAllEmployeeDetails();
    }


    @Override
    public void deleteEmployee(String empId){
        employeeDAO.deleteEmployee(empId);
    }


    @Override
    public List<String> getRolesForTeam(String teamName) {
        return employeeDAO.getRolesForTeam(teamName);
    }
}
