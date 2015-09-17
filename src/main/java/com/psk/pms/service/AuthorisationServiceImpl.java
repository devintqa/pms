package com.psk.pms.service;

import com.psk.pms.dao.EmployeeDAO;
import com.psk.pms.model.Employee;
import com.psk.pms.model.Permission;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

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
    public List<Permission> getPermissionList(String teamName) {
        List<Employee> employeesOfTeam = employeeDAO.getEmployeesOfTeam(teamName);
        List<Permission> permissions = new ArrayList<>();
        for (Employee employee : employeesOfTeam) {
            Permission permission = new Permission();
            permission.setLabel(employee.getEmployeeId());
            permission.setValue(employee.getEmployeeId());
            permissions.add(permission);
        }
        return permissions;
    }

    @Override
    public List<Permission> saveProjectUserPrivilage(String projectId, List<String> users) {
        employeeDAO.saveProjectUserPrivelage(projectId,users);
        List<Permission>permissions = new ArrayList<>();
        for (String user : users) {
            Permission permission = new Permission();
            permission.setLabel(user);
            permission.setValue(user);
            permission.setSelected(true);
            permissions.add(permission);
        }
        return permissions;
    }
}
