package com.psk.pms.controller;

import java.util.ArrayList;
import java.util.Hashtable;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.psk.pms.model.*;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.support.SessionStatus;

import com.psk.pms.model.DescItemDetail.ItemDetail;
import com.psk.pms.service.DepositDetailService;
import com.psk.pms.service.ItemService;
import com.psk.pms.service.ProjectDescriptionService;
import com.psk.pms.service.SubProjectService;
import com.psk.pms.validator.SearchValidator;

@Controller
public class SearchController extends BaseController {

    private static final Logger LOGGER = Logger
            .getLogger(SearchController.class);

    @Autowired
    private SearchValidator searchValidator;

    @Autowired
    DepositDetailService depositDetailService;

    @Autowired
    SubProjectService subProjectService;

    @Autowired
    ItemService itemService;

    @Autowired
    ProjectDescriptionService projectDescriptionService;

    @RequestMapping(value = "/emp/myview/searchProject/{employeeId}", method = RequestMethod.GET)
    public String searchProject(@PathVariable String employeeId, Model model) {
        LOGGER.info("Search Controller : searchProject()");
        List<ProjectDetail> projectDocumentList = projectService
                .getProjectDocumentList();
        if (!projectDocumentList.isEmpty()) {
            model.addAttribute("projectDocumentList", projectDocumentList);
        } else {
            model.addAttribute("noDetailsFound",
                    "No Projects Found For The Selection.");
        }
        return "SearchProject";
    }

    @RequestMapping(value = "/emp/myview/searchSubProject/{employeeId}", method = RequestMethod.GET)
    public String searchSubProject(@PathVariable String employeeId, Model model) {
        LOGGER.info("Search Controller : searchSubProject()");
        SearchDetail searchDetail = new SearchDetail();
        searchDetail.setEditSubProject(true);
        model.addAttribute("searchSubForm", searchDetail);
        return "SearchSubProject";
    }

    @RequestMapping(value = "/emp/myview/searchProjectDescription/{employeeId}", method = RequestMethod.GET)
    public String searchProjectDescription(@PathVariable String employeeId,@RequestParam("team") String team,
                                           Model model) {
        LOGGER.info("Search Controller : searchProjectDescription()");
        Employee employee = new Employee();
        employee.setEmployeeId(employeeId);
        employee.setEmployeeTeam(team);
        SearchDetail searchDetail = new SearchDetail();
        model.addAttribute("searchProjDescForm", searchDetail);
        model.addAttribute("employeeObj", employee);
        return "SearchProjectDescription";
    }

    @RequestMapping(value = "/emp/myview/searchSubProject/searchProject.do", method = RequestMethod.GET)
    public
    @ResponseBody
    List<String> getProjectList(
            @RequestParam("term") String name) {
        LOGGER.info("method = getProjectList()");
        return fetchProjectsInfo(name);
    }

    @RequestMapping(value = "/emp/myview/searchProjectDescription/searchProject.do", method = RequestMethod.GET)
    public
    @ResponseBody
    List<String> getProjects(
            @RequestParam("term") String name) {
        LOGGER.info("method = getProjectList()");
        return fetchProjectsInfo(name);
    }

    @RequestMapping(value = "/emp/myview/searchProjectDescription/searchSubProject.do", method = RequestMethod.GET)
    public
    @ResponseBody
    List<String> getSubProjectList(
            @RequestParam("term") String name) {
        LOGGER.info("method = getSubProjectList()");
        return fetchSubProjectsInfo(name);
    }

    @RequestMapping(value = "/emp/myview/searchSubProject/searchSubProjectDetails.do", method = RequestMethod.POST)
    public String searchSubProjectDetail(
            @ModelAttribute("searchSubForm") SearchDetail searchDetail,
            BindingResult result, Model model, SessionStatus status) {
        LOGGER.info("method = searchSubProjectDetail()");
        searchValidator.validate(searchDetail, result);
        LOGGER.info("method = searchSubProjectDetail()" + "after validate");
        if (!result.hasErrors()) {
            LOGGER.info("method = searchSubProjectDetail()" + "into fetch");
            List<SubProjectDetail> subProjectDocumentList = getSubProjectDocumentList(searchDetail
                    .getProjId());
            if (subProjectDocumentList.size() > 0) {
                model.addAttribute("subProjectDocumentList",
                        subProjectDocumentList);
                model.addAttribute("subProjectDocumentSize",
                        subProjectDocumentList.size());
                model.addAttribute("projectAliasName",
                        searchDetail.getAliasProjectName());
            } else {
                model.addAttribute("noDetailsFound",
                        "No Sub Projects Found For The Selection.");
            }
        }
        return "SearchSubProject";
    }

    @RequestMapping(value = "/emp/myview/searchProjectDescription/searchProjectDescDetails.do", method = RequestMethod.POST)
    public String searchProjectDescDetail(
            @ModelAttribute("searchProjDescForm") SearchDetail searchDetail,
            BindingResult result,
            Model model,
            SessionStatus status) {
        LOGGER.info("method = searchProjectDetail()");
        searchValidator.validate(searchDetail, result);
        if (!result.hasErrors()) {
            List<ProjDescDetail> projDescDocList = projectDescriptionService.getProjectDescDetailList(searchDetail);
            if (projDescDocList.size() > 0) {
                model.addAttribute("projDescDocList", projDescDocList);
                model.addAttribute("projDescDocListSize", projDescDocList.size());
                model.addAttribute("projectAliasName", searchDetail.getAliasProjectName());
            } else {
                model.addAttribute("noDetailsFound", "No Project Descriptions Found For The Selection.");
            }
        }
        return "SearchProjectDescription";
    }

    @RequestMapping(value = "/emp/myview/searchDepositDetail/searchDepositDetails.do", method = RequestMethod.POST)
    public String searchProjectDepositDetails(
            @ModelAttribute("searchDepositForm") SearchDetail searchDetail,
            BindingResult result, Model model, SessionStatus status) {
        List<DepositDetail> depositDetails = new ArrayList<>();
        searchValidator.validate(searchDetail, result);
        if (!result.hasErrors()) {
            LOGGER.info("method = searchProjectDepositDetails()"
                    + searchDetail.getProjId());
            String searchUnder = searchDetail.getSearchUnder();
            if ("Global".equalsIgnoreCase(searchUnder)) {
                depositDetails = depositDetailService.getDepositDetails();
            } else {
                boolean searchUnderProject = "project"
                        .equalsIgnoreCase(searchUnder);
                if (searchUnderProject) {
                    depositDetails = depositDetailService
                            .getDepositDetailsByProjectId(null != searchDetail
                                    .getProjId() ? searchDetail
                                    .getProjId() : Integer.valueOf(0));
                } else {
                    depositDetails = depositDetailService
                            .getDepositDetailsBySubProjectId(null != searchDetail
                                    .getProjId() ? searchDetail
                                    .getProjId() : Integer.valueOf(0));
                }
            }
            if (depositDetails.size() > 0) {
                model.addAttribute("depositDetails", depositDetails);
                model.addAttribute("depositDetailsSize", depositDetails.size());
                model.addAttribute("projectAliasName",
                        searchDetail.getAliasProjectName());
            } else {
                model.addAttribute("noDetailsFound",
                        "No Project Deposit Details Found For The Selection.");
            }
        }
        return "SearchDepositDetail";
    }

    @RequestMapping(value = "/emp/myview/searchProjectDescription/searchDescItems.do", method = RequestMethod.GET)
    public
    @ResponseBody
    List<ItemDetail> searchDescItem(
            @RequestParam("itemName") String itemName,
            @RequestParam("itemType") String itemType,
            @RequestParam("projectId") String projectId,
            @RequestParam("subProjectId") String subProjectId) {
        LOGGER.info("method = getDescItem()");
        Map<String, Object> request = new Hashtable<String, Object>();
        request.put("itemName", itemName);
        request.put("itemType", itemType);
        request.put("projectId", projectId);
        request.put("subProjectId", subProjectId);
        List<ItemDetail> itemsDetail = itemService.getDescItemNames(request);
        return itemsDetail;
    }

    @RequestMapping(value = "/emp/myview/searchBaseDescription/searchBaseItems.do", method = RequestMethod.GET)
    public
    @ResponseBody
    List<ItemDetail> searchBaseItem(
            @RequestParam("itemName") String itemName,
            @RequestParam("itemType") String itemType) {
        LOGGER.info("method = getDescItem()");
        Map<String, Object> request = new Hashtable<String, Object>();
        request.put("itemName", itemName.toUpperCase());
        request.put("itemType", itemType.toUpperCase());
        List<ItemDetail> itemsDetail = itemService.getBaseItemNames(request);
        return itemsDetail;
    }

    @RequestMapping(value = "/emp/myview/configureItems/searchItems.do", method = RequestMethod.GET)
    public
    @ResponseBody
    List<ItemDetail> getItemNames(
            @RequestParam("itemName") String itemName,
            @RequestParam("itemType") String itemType) {
        LOGGER.info("method = getItemNames()");
        List<ItemDetail> itemsDetail = itemService.searchItemName(itemName,
                itemType);
        return itemsDetail;
    }

    @RequestMapping(value = "/emp/myview/searchProject/deleteProject.do", method = RequestMethod.POST)
    public void deleteProject(HttpServletRequest request,
                              HttpServletResponse response) {
        String projectId = request.getParameter("projectId");
        LOGGER.info("method = deleteProject() , projectId :" + projectId);
        Integer projectIdNumeric = Integer.parseInt(projectId);
        projectService.deleteProject(projectIdNumeric);
    }

    @RequestMapping(value = "/emp/myview/searchSubProject/deleteSubProject.do", method = RequestMethod.POST)
    public void deleteSubProject(HttpServletRequest request,
                                 HttpServletResponse response) {
        String subProjectId = request.getParameter("subProjectId");
        LOGGER.info("method = deleteSubProject() , sub projectid : "
                + subProjectId);
        Integer subProjectIdNumeric = Integer.parseInt(subProjectId);
        subProjectService.deleteSubProject(subProjectIdNumeric);

    }

    @RequestMapping(value = "/emp/myview/searchDepositDetail/{employeeId}", method = RequestMethod.GET)
    public String searchDepositDetail(@PathVariable String employeeId, Model model) {
        LOGGER.info("Search Controller : searchDepositDetail()");
        SearchDetail searchDetail = new SearchDetail();
        model.addAttribute("searchDepositForm", searchDetail);
        return "SearchDepositDetail";
    }

    public List<SubProjectDetail> getSubProjectDocumentList(Integer projectId) {
        List<SubProjectDetail> subProjectDocumentList = subProjectService.getSubProjectDocumentList(projectId);
        return subProjectDocumentList;
    }

    @RequestMapping(value = "/emp/myview/searchBaseDescription/{employeeId}", method = RequestMethod.GET)
    public String loadSearchBaseDescription(@PathVariable String employeeId,
                                            Model model) {
        LOGGER.info("Search Controller : searchBaseDescription()");
        SearchDetail searchDetail = new SearchDetail();
        model.addAttribute("baseDesc", searchDetail);
        return "SearchBaseDescription";
    }

    @RequestMapping(value = "/emp/myview/searchBaseDescription/searchBaseDescDetails.do", method = RequestMethod.POST)
    public String searchBaseDescription(@ModelAttribute("baseDesc") SearchDetail searchDetail, Model model) {
        LOGGER.info("Search Controller : searchBaseDescription()");

        model.addAttribute("baseDesc", searchDetail);
        List<ProjDescDetail> projDescDetails = projectDescriptionService.getBaseDescriptions(searchDetail.getSearchOn());
        model.addAttribute("baseDescriptionList", projDescDetails);

        return "SearchBaseDescription";
    }
}
