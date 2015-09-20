package com.psk.pms.service;

import com.psk.pms.dao.EmployeeDAO;
import com.psk.pms.model.Permission;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Sony on 16-09-2015.
 */
@Service
public class AuthorisationServiceImpl implements AuthorisationService {
    @Autowired
    EmployeeDAO employeeDAO;

    @Override
    public List<Permission> getPermissionList(String teamName, String projectId) {
        List<Permission> selectedEmployees = getSelectedEmployees(teamName, projectId);
        List<Permission> availableEmployees = getAvailableEmployees(teamName, projectId);
        selectedEmployees.addAll(availableEmployees);
        return selectedEmployees;
    }

    private List<Permission> getAvailableEmployees(String teamName, String projectId) {
        List<Permission> permissions = new ArrayList<Permission>();
        List<String> employeesOfTeam = employeeDAO.getAvailableEmployees(teamName, projectId);
        for (String empId : employeesOfTeam) {
            Permission permission = new Permission();
            permission.setLabel(empId);
            permission.setValue(empId);
            permissions.add(permission);
        }
        return permissions;

    }

    private List<Permission> getSelectedEmployees(String teamName, String projectId) {
        List<Permission> permissions = new ArrayList<Permission>();
        List<String> employeesOfTeam = employeeDAO.getSelectedEmployees(teamName, projectId);
        for (String empId : employeesOfTeam) {
            Permission permission = new Permission();
            permission.setLabel(empId);
            permission.setValue(empId);
            permission.setSelected(true);
            permissions.add(permission);
        }
        return permissions;
    }

    @Override
    @Transactional
    public List<Permission> saveProjectUserPrivilege(String projectId, List<String> selectedUsers, String teamName, List<String> availableUsers) {
        employeeDAO.deleteProjectUserPrivilege(projectId, availableUsers, teamName);
        employeeDAO.deleteProjectUserPrivilege(projectId, selectedUsers, teamName);
        employeeDAO.saveProjectUserPrivilege(projectId, selectedUsers, teamName);
        List<Permission> permissions = new ArrayList<Permission>();
        for (String user : selectedUsers) {
            Permission permission = new Permission();
            permission.setLabel(user);
            permission.setValue(user);
            permission.setSelected(true);
            permissions.add(permission);
        }
        return permissions;
    }
}
