package com.psk.pms.factory;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * Created by Sony on 04-09-2015.
 */
@Component
public class EmployeeTeamFactory {

    public static final String ADMIN = "admin";
    public static final String TECHNICAL = "technical";
    public static final String ACCOUNT = "Account";
    public static final String MANAGEMENT = "Management";
    public static final String PURCHASE = "Purchase";
    public static final String STORE = "Store";
    @Autowired
    AdminEmployeeTeam adminEmployeeTeam;

    @Autowired
    TechnicalEmployeeTeam technicalEmployeeTeam;

    @Autowired
    ManagementEmployeeTeam managementEmployeeTeam;

    @Autowired
    StoreEmployeeTeam storeEmployeeTeam;
    
    @Autowired
    PurchaseEmployeeTeam purchaseEmployeeTeam;


    public EmployeeTeam getEmployeeTeam(String employeeTeam) {
        if (ADMIN.equalsIgnoreCase(employeeTeam)) {
            return adminEmployeeTeam;
        }
        if (TECHNICAL.equalsIgnoreCase(employeeTeam)) {
            return technicalEmployeeTeam;
        }
        if(STORE.equalsIgnoreCase(employeeTeam)){
            return storeEmployeeTeam;
        }
        if(PURCHASE.equalsIgnoreCase(employeeTeam)){
            return purchaseEmployeeTeam;
        }
        return managementEmployeeTeam;
    }
}
