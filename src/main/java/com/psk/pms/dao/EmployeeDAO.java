package com.psk.pms.dao;

import java.util.List;
import java.util.Map;

import com.psk.pms.model.Employee;
import com.psk.pms.model.Team;

public interface EmployeeDAO {

	public int getEmployeeLoginDetails(String userName, String password);

	public int isEmployeeExisting(String userName);

	public boolean signupEmployee(Employee employee);

	public Employee getEmployeeDetails(String empId);

	public boolean updateEmployee(Employee employee);

	public String getUserRole(String empId);

	public boolean isEmployeeMotherMaidenExisting(String userName,
			String motherMaiden);

	public boolean resetpassword(String userName, String password);

	public int getUserNamePatternCount(String userName);

	public List<Employee> getNewRegistrationRequest(String fromDate);

	public int manageUserAccess(Employee employee);

	public boolean isEmployeeMailExisting(String mail);

	public boolean isEmployeeMobNumExisting(String mobile);

    boolean isRoleExists(String roleName, String teamName);

    boolean saveTeamRoles(Team team);

    List<String> getRolesForTeam(String teamName);

    List<Employee> getAllEmployeeDetails();

    void deleteEmployee(String employeeId);
}
