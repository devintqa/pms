package com.psk.pms.factory;

import com.psk.pms.model.ProjectDetail;
import com.psk.pms.service.ProjectService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.ui.Model;

import java.util.List;

/**
 * Created by Sony on 04-09-2015.
 */
@Component
public class TechnicalEmployeeTeam implements EmployeeTeam {

    @Autowired
    ProjectService projectService;


    @Override
    public void performTeamActivity(Model model) {
        List<ProjectDetail> projectDocumentList = projectService
                .getProjectDocumentList();
        if (!projectDocumentList.isEmpty()) {
            model.addAttribute("projectDocumentList", projectDocumentList);
        }
    }
}
