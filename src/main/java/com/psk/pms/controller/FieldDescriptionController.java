package com.psk.pms.controller;

import com.google.gson.Gson;
import com.psk.pms.model.ProjDescDetail;
import com.psk.pms.service.FieldDescriptionService;
import com.psk.pms.service.ProjectDescriptionService;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Map;

/**
 * Created by prakashbhanu57 on 8/18/2015.
 */
@Controller
public class FieldDescriptionController extends BaseController {

    @Autowired
    private ProjectDescriptionService projectDescriptionService;

    @Autowired
    private FieldDescriptionService fieldDescriptionService;

    private static final Logger LOGGER = Logger.getLogger(FieldDescriptionController.class);

    @RequestMapping(value = "/emp/myview/buildFieldDescription/{employeeId}", method = RequestMethod.GET)
    public String buildFieldDescription(@PathVariable String employeeId,
                                        @RequestParam(value = "team", required = true) String team,
                                        @RequestParam(value = "action", required = false) String action,
                                        Model model) {
        LOGGER.info("Opening buildFieldDescription ");
        ProjDescDetail projDescDetail = new ProjDescDetail();
        model.addAttribute("aliasProjectList", populateAliasProjectList());
        model.addAttribute("projDescForm", projDescDetail);
        return "BuildFieldDescription";
    }

    @RequestMapping(value = "/emp/myview/buildFieldDescription/FieldDescriptionPresent.do", method = RequestMethod.GET)
    public @ResponseBody String isFieldDescriptionPresent(HttpServletRequest request) {
        String NULL_STRING = "null";
        int projectId = Integer.parseInt(request.getParameter("projectId"));
        int subProjectId;
        if (NULL_STRING.equalsIgnoreCase(request.getParameter("subProjectId"))) {
            subProjectId = 0;
        } else {
            subProjectId = Integer.parseInt(request.getParameter("subProjectId"));
        }
        LOGGER.info("Checking is filedDescription already present for projectId :"+projectId +"subProjectId :"+subProjectId);
        ProjDescDetail projDescDetail = new ProjDescDetail();
        if (0 != subProjectId) {
            if (!projectDescriptionService.isProjectDescriptionDetailsExistsForSubProject(subProjectId,"N")) {
                return "NotExist";
            } else if (fieldDescriptionService.isFieldDescriptionDetailsExistsForSubProject(subProjectId)) {
                return "AlreadyExist";
            }
            fieldDescriptionService.createFieldDescription(projectId,subProjectId);
        } else {
            if (!projectDescriptionService.isProjectDescriptionDetailsExistsForProject(projectId,"N")) {
                return "NotExist";
            } else if (fieldDescriptionService.isFieldDescriptionDetailsExistsForProject(projectId)) {
                return "AlreadyExist";
            }
            fieldDescriptionService.createFieldDescription(Integer.valueOf(projectId),0);
        }
        return "Success";
    }

    @RequestMapping(value = "/emp/myview/buildFieldDescription/getSubAliasProject.do", method = RequestMethod.GET)
    @ResponseBody
    public String getSubAliasProject(HttpServletRequest request,
                                     HttpServletResponse response) {
        LOGGER.info("method = getSubAliasProject() , Sub Project Id : " + request.getParameter("subProjId"));
        Map<String, String> subAliasProjectList = populateSubAliasProjectList(request.getParameter("aliasProjectName"));
        subAliasProjectList.put("0", "--Please Select--");
        Gson gson = new Gson();
        String subAliasProjectJson = gson.toJson(subAliasProjectList);
        return subAliasProjectJson;
    }

    @RequestMapping(value = "/emp/myview/buildFieldDescription/createFieldDescription.do", method = RequestMethod.POST)
    @ResponseBody
    public void createFieldDescription(HttpServletRequest request,
                                       HttpServletResponse response) {
        String NULL_STRING = "null";
        String projectId = request.getParameter("projectId");
        String subProjectId = request.getParameter("subProjectId");
        int subProjectIdIntValue;
        LOGGER.info("Checking is filedDescription already present for :");
        ProjDescDetail projDescDetail = new ProjDescDetail();
        if (NULL_STRING.equalsIgnoreCase(subProjectId)) {
            subProjectIdIntValue = 0;
        } else {
            subProjectIdIntValue = Integer.parseInt(subProjectId);
        }
        fieldDescriptionService.createFieldDescription(Integer.valueOf(projectId), subProjectIdIntValue);
    }

}
