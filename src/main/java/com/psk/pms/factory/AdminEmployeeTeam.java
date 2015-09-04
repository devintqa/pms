package com.psk.pms.factory;

import com.psk.pms.model.DepositDetail;
import com.psk.pms.model.Employee;
import com.psk.pms.service.DepositDetailService;
import com.psk.pms.service.EmployeeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.ui.Model;

import java.util.List;

/**
 * Created by Sony on 04-09-2015.
 */
@Component
public class AdminEmployeeTeam implements EmployeeTeam {

    @Autowired
    EmployeeService employeeService;

    @Autowired
    DepositDetailService depositDetailService;

    @Override
    public void performTeamActivity(Model model) {
        List<Employee> newSignupRequestList = employeeService
                .getNewRegistrationRequest(null);
        if (!newSignupRequestList.isEmpty()) {
            model.addAttribute("newSignupRequestList", newSignupRequestList);
        }
        List<DepositDetail> depositEndAlertList = depositDetailService.getDepositEndAlertList();
        if (depositEndAlertList.size() > 0) {
            model.addAttribute("depositDocumentList", depositEndAlertList);
        }
    }
}
