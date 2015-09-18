package com.psk.pms.service;

import com.psk.pms.model.Permission;

import java.util.List;

/**
 * Created by Sony on 16-09-2015.
 */
public interface AuthorisationService {
    List<Permission> getPermissionList(String teamName, String projectId);

    List<Permission> saveProjectUserPrivilege(String projectId, List<String> users, String teamName, List<String> availableUsers);
}
