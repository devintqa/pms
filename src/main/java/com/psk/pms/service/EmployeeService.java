package com.psk.pms.service;

import com.psk.pms.model.Employee;
import com.psk.pms.model.Team;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public interface EmployeeService {

    boolean isValidLogin(String userName, String password);

    boolean isEmployeeExisting(String userName);

    boolean signupEmployee(Employee employee);

    Map<String, String> fetchTeamNames();

    Employee getEmployeeDetails(String empId);

    boolean updateEmployee(Employee employee);

    String getUserRole(String empId);

    boolean isEmployeeMotherMaidenExisting(String userName,
                                           String motherMaiden);

    boolean isEmployeeMailExisting(String mail);

    boolean isEmployeeMobNumExisting(String mobile);

    boolean resetPassword(String userName, String password);

    List<Employee> getNewRegistrationRequest(String fromDate);

    int manageUserAccess(HashMap<String, String> accessDetails);

    boolean isRoleExists(String roleName, String teamName);

    boolean saveTeamRoles(Team team);

    List<Employee> getAllEmployeeDetails();

    void deleteEmployee(String empId);

    List<String> getRolesForTeam(String teamName);
}
