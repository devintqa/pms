package com.psk.pms.service;

import com.psk.pms.model.Permission;

import java.util.List;

/**
 * Created by Sony on 16-09-2015.
 */
public interface AuthorisationService {
    List<Permission> getPermissionList(String teamName);

    List<Permission> saveProjectUserPrivilage(String projectId, List<String> users);
}
