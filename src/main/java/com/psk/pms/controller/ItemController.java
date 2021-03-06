package com.psk.pms.controller;

import static com.psk.pms.Constants.ITEM_TYPE;
import static com.psk.pms.constants.JSPFileNames.BUILD_ITEM;

import java.io.IOException;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.codehaus.jackson.JsonParseException;
import org.codehaus.jackson.map.JsonMappingException;
import org.codehaus.jackson.map.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.support.SessionStatus;
import org.springframework.web.multipart.MultipartFile;

import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.reflect.TypeToken;
import com.mysql.jdbc.StringUtils;
import com.psk.pms.Constants;
import com.psk.pms.model.DescItemDetail;
import com.psk.pms.model.DescItemDetail.ItemDetail;
import com.psk.pms.model.Employee;
import com.psk.pms.model.ExcelDetail;
import com.psk.pms.model.Item;
import com.psk.pms.model.LeadDetailConfiguration;
import com.psk.pms.model.LeadDetailConfiguration.LeadDetail;
import com.psk.pms.model.ProjDescDetail;
import com.psk.pms.model.ProjectConfiguration;
import com.psk.pms.model.ProjectDetail;
import com.psk.pms.service.FileService;
import com.psk.pms.service.ItemService;
import com.psk.pms.service.ProjectDescriptionService;
import com.psk.pms.service.ProjectService;
import com.psk.pms.validator.FileUploadValidator;
import com.psk.pms.validator.ItemValidator;

@Controller
public class ItemController extends BaseController {

    @Autowired
    ProjectService projectService;

    @Autowired
    ProjectDescriptionService projectDescService;

    @Autowired
    ItemValidator itemValidator;

    @Autowired
    ItemService itemService;


    @Autowired
    FileUploadValidator fileUploadValidator;

    @Autowired
    FileService fileService;

    private static final Logger LOGGER = Logger.getLogger(ItemController.class);

    @RequestMapping(value = "/emp/myview/buildItem/{employeeId}", method = RequestMethod.GET)
    public String buildItem(@PathVariable String employeeId, Model model) {
        LOGGER.info("method = buildItem()");
        Item item = new Item();
        item.setEmployeeId(employeeId);
        model.addAttribute("itemForm", item);
        return BUILD_ITEM;
    }

    @RequestMapping(value = "/emp/myview/updateItem/{employeeId}", method = RequestMethod.GET)
    public String updateProject(@PathVariable String employeeId,
                                @RequestParam(value = "itemName", required = true) String itemName,
                                @RequestParam(value = "itemUnit", required = true) String itemUnit,
                                @RequestParam(value = "itemType", required = true) String itemType,
                                Model model) {
        Item item = new Item();
        item.setEmployeeId(employeeId);
        item.setItemName(itemName);
        item.setItemUnit(itemUnit);
        item.setItemType(itemType);
        item.setIsUpdate("Y");
        model.addAttribute("itemForm", item);
        return BUILD_ITEM;
    }


    @RequestMapping(value = "/emp/myview/updateItem/createItem.do", method = RequestMethod.POST)
    public String updateItem(
            @ModelAttribute("itemForm") Item item,
            BindingResult result, Model model, SessionStatus status) throws IOException {
        try {
            if (StringUtils.isNullOrEmpty(item.getItemUnit())) {
                model.addAttribute("uploadItemProjectDescriptionFailed", "Item Unit can't be empty.");
            } else {
                itemService.updateItem(item);
                model.addAttribute("itemCreationMessage", "Update of Item is successful.");
            }
        } catch (Exception e) {
            model.addAttribute("uploadItemProjectDescriptionFailed", "Updating the Item failed due to " + e.getMessage());
        }
        return BUILD_ITEM;
    }

    @RequestMapping(value = "/emp/myview/searchItem/deleteItem.do", method = RequestMethod.POST)
    public void deleteProjectDescriptionDetail(HttpServletRequest request,
                                               HttpServletResponse response) {
        String itemName = request.getParameter("itemName");
        String itemType = request.getParameter("itemType");
        itemService.deleteItem(itemName, itemType);
    }


    @RequestMapping(value = "/emp/myview/buildItem/searchItem.do", method = RequestMethod.GET)
    public
    @ResponseBody
    List<String> getProjectList(@RequestParam("term") String name) {
        LOGGER.info("method = getItemList()");
        LOGGER.info("method = fetchItemInfo()");
        List<String> result = new ArrayList<String>();
        Set<String> itemNames = itemService.fetchItemNames();
        LOGGER.info("The Item Name Size:" + itemNames.size());
        for (String item : itemNames) {
            if (item.toUpperCase().indexOf(name.toUpperCase()) != -1) {
                result.add(item);
            }
        }
        return result;
    }


    @RequestMapping(value = "/emp/myview/buildItem/ItemPresent.do", method = RequestMethod.GET)
    public
    @ResponseBody
    boolean isItemPresent(HttpServletRequest request) {
        return itemService.isItemDescriptionPresent();
    }

    @RequestMapping(value = "/emp/myview/buildItem/createItem.do", method = RequestMethod.POST)
    public String saveItem(
            @ModelAttribute("itemForm") Item item,
            BindingResult result, Model model, SessionStatus status) throws IOException {
        boolean isItemSaveSuccessful = false;
        List<MultipartFile> files = item.getFiles();
        if (files.size() != 0 && item.isBaseItem()) {
            fileUploadValidator.validateFileExistance(result, files);
        } else {
            itemValidator.validate(item, result);
        }
        if (!result.hasErrors()) {
            if (files.size() != 0 && item.isBaseItem()) {
                if (fileUploadServiceUnsuccessful(item, model)) {
                    item.setBaseItem(true);
                    return BUILD_ITEM;
                } else {
                    item.setBaseItem(false);
                    model.addAttribute("itemCreationMessage", "File Upload Successful.");

                    return BUILD_ITEM;
                }
            } else {
                isItemSaveSuccessful = itemService.createEditItem(item);
                if (result.hasErrors() || !isItemSaveSuccessful) {
                    return BUILD_ITEM;
                } else {
                    status.setComplete();
                    Employee employee = new Employee();
                    employee.setEmployeeId(item.getEmployeeId());
                    model.addAttribute("employee", employee);
                    model.addAttribute("itemCreationMessage", "Item Creation Successful.");
                    return BUILD_ITEM;
                }
            }
        }
        return BUILD_ITEM;
    }

    private boolean fileUploadServiceUnsuccessful(Item item, Model model) throws IOException {
        ExcelDetail excelDetail;
        try {
            excelDetail = fileService.saveProjectItemDescription(item);
        } catch (Exception e) {
            model.addAttribute(
                    "uploadItemProjectDescriptionFailed",
                    String.format("%s%s", Constants.UPLOADFAILED, e.getMessage()));
            return true;
        }
        if (!excelDetail.isExcel()) {
            model.addAttribute("itemUpdationMessage",
                    "Please Select Valid File Format");
            return true;
        }
        return false;
    }

    @RequestMapping(value = "/emp/myview/configureItems/{employeeId}", method = RequestMethod.GET)
    public String configureItems(@PathVariable String employeeId, @RequestParam(value = "project") int projectId, @RequestParam(value = "subProject") int subProjectId, Model model) {
        ProjectConfiguration projectConfiguration = new ProjectConfiguration();
        projectConfiguration.setEmployeeId(employeeId);
        projectConfiguration.setProjId(projectId);
        projectConfiguration.setSubProjId(subProjectId);

        projectConfiguration = itemService.getProjectItemConfiguration(projectConfiguration, false);
        model.addAttribute("projectItemForm", projectConfiguration);
        Gson gson = new Gson();
        JsonElement element = gson.toJsonTree(projectConfiguration.getItemDetail(), new TypeToken<List<ItemDetail>>() {
        }.getType());
        if (!element.isJsonArray()) {

        }
        JsonArray jsonArray = element.getAsJsonArray();
        projectConfiguration.setItemPriceConfiguration(jsonArray.toString());
        projectConfiguration.setEmployeeId(employeeId);
        model.addAttribute("projectItemForm", projectConfiguration);

        return "ConfigureItems";
    }

    @RequestMapping(value = "/emp/myview/configureItems/syncItems.do", method = RequestMethod.POST, consumes = "application/json")
    public
    @ResponseBody
    boolean syncItems(@RequestBody ProjectConfiguration projectItemConfiguration) throws JsonParseException, JsonMappingException, IOException {
        ObjectMapper mapper = new ObjectMapper();
        List<com.psk.pms.model.ProjectConfiguration.ItemDetail> itemList = mapper.readValue(projectItemConfiguration.getItemPriceConfiguration(), mapper.getTypeFactory().constructCollectionType(List.class, com.psk.pms.model.ProjectConfiguration.ItemDetail.class));
        projectItemConfiguration.setItemDetail(itemList);
        List<com.psk.pms.model.ProjectConfiguration.ItemDetail> missingItemDetails = itemService.getMissingProjectDescriptionItems(projectItemConfiguration.getProjId());

        System.out.println("missingItemDetails " + missingItemDetails.size());
        itemList.addAll(missingItemDetails);
        projectItemConfiguration.setItemDetail(itemList);
        boolean status = itemService.configureItemPrice(projectItemConfiguration);
        return status;
    }

    @RequestMapping(value = "/emp/myview/buildProjectDesc/loadProjDescItems.do")
    public String loadProjDescItems(Model model, @RequestParam String projDescSerial,
                                    @RequestParam String projId, @RequestParam String subProjId,
                                    @RequestParam String projDescId, @RequestParam String employeeId,
                                    @RequestParam String descType) {
        DescItemDetail descItemDetail = new DescItemDetail();
        descItemDetail.setProjId(new Integer(projId));
        descItemDetail.setSubProjId(new Integer(subProjId));
        descItemDetail.setProjDescId(new Integer(projDescId));
        descItemDetail.setProjDescSerial(projDescSerial);
        descItemDetail.setDescType(descType);
        ProjDescDetail projDescDetail = null;
        projDescDetail = projectDescService.getProjectDescDetail(projDescId, null, descType);
        descItemDetail = itemService.getProjectDescriptionItems(descItemDetail);
        if (Constants.GOVERNMENT.equalsIgnoreCase(descType)) {
            ProjectDetail projectDetail = getProjectDocument(projId, employeeId);
            itemService.updateMaterialPriceWithLeadDetailsPrice(descItemDetail.getItemDetail(), projId, subProjId);
            itemService.applyWorkoutPercentage(descItemDetail.getItemDetail(), projectDetail.getWorkoutPercentage());
        }
        if(projDescDetail.isConversionFalg()){
            showItemCostWithItemRate(descItemDetail.getItemDetail(),projDescDetail.getConversionValue());
        }
        Gson gson = new Gson();
        JsonElement element = gson.toJsonTree(descItemDetail.getItemDetail(), new TypeToken<List<ItemDetail>>() {
        }.getType());
        if (!element.isJsonArray()) {

        }
        JsonArray jsonArray = element.getAsJsonArray();
        descItemDetail.setDescItemDetail(jsonArray.toString());
        descItemDetail.setEmployeeId(employeeId);
        descItemDetail.setConversionFlag(projDescDetail.isConversionFalg());
        descItemDetail.setConversionValue(projDescDetail.getConversionValue());
        Employee employee = employeeService.getEmployeeDetails(employeeId);
        model.addAttribute("descItemForm", descItemDetail);
        model.addAttribute("projDescForm", projDescDetail);
        model.addAttribute("employee", employee);
        return "DescItem";
    }

    private void showItemCostWithItemRate(List<ItemDetail> itemDetails,String conversionRate) {
        LOGGER.info("Applying conversion rate before showing");
        BigDecimal conversionValue = new BigDecimal(conversionRate);
        for(ItemDetail itemDetail : itemDetails){
            BigDecimal itemCost = new BigDecimal(itemDetail.getItemCost());
            BigDecimal convertedValue = itemCost.multiply(conversionValue);
            itemDetail.setItemCost(convertedValue.toString());
        }
    }

    @RequestMapping(value = "/emp/myview/buildProjectDesc/saveProjDescItems.do", method = RequestMethod.POST, consumes = "application/json")
    public
    @ResponseBody
    boolean saveProjDescItems(@RequestBody DescItemDetail descItemDetail)
            throws JsonParseException, JsonMappingException, IOException {
        ObjectMapper mapper = new ObjectMapper();
        List<com.psk.pms.model.DescItemDetail.ItemDetail> itemList = mapper.readValue(descItemDetail.getDescItemDetail(), mapper.getTypeFactory().constructCollectionType(List.class, com.psk.pms.model.DescItemDetail.ItemDetail.class));
        descItemDetail.setItemDetail(itemList);
        if(descItemDetail.isConversionFlag()){
            applyConversionForItems(itemList,descItemDetail.getConversionValue());
        }
        boolean status = itemService.insertProjectDescriptionItems(descItemDetail);
        return status;
    }

    private void applyConversionForItems(List<ItemDetail> itemList, String conversionRate) {
        LOGGER.info("Applying conversion rate for items ....");
        BigDecimal conversionValue = new BigDecimal(conversionRate);
        for (ItemDetail itemDetail : itemList) {
            BigDecimal itemCost = new BigDecimal(itemDetail.getItemCost());
            BigDecimal convertedValue = itemCost.divide(conversionValue);
            itemDetail.setItemCost(convertedValue.toString());
        }
    }

    @RequestMapping(value = "/emp/myview/buildBaseDesc/saveBaseDescItems.do", method = RequestMethod.POST, consumes = "application/json")
    public
    @ResponseBody
    boolean saveBaseDescItems(@RequestBody DescItemDetail descItemDetail) throws JsonParseException, JsonMappingException, IOException {
        ObjectMapper mapper = new ObjectMapper();
        List<com.psk.pms.model.DescItemDetail.ItemDetail> itemList = mapper.readValue(descItemDetail.getDescItemDetail(), mapper.getTypeFactory().constructCollectionType(List.class, com.psk.pms.model.DescItemDetail.ItemDetail.class));
        descItemDetail.setItemDetail(itemList);
        boolean status = itemService.saveBaseDescriptionItems(descItemDetail);
        return status;
    }

    @RequestMapping(value = "/emp/myview/configureItems/saveItemPrice.do", method = RequestMethod.POST, consumes = "application/json")
    public
    @ResponseBody
    String saveConfiguredItems(@RequestBody ProjectConfiguration projectItemConfiguration) throws JsonParseException, JsonMappingException, IOException {
        Integer projectId = projectItemConfiguration.getProjId();
        LOGGER.info("Saving prices into pskpricedetail and updating prices of descriptionItems and recalculating all totals for Project Id:" + projectId);
        ObjectMapper mapper = new ObjectMapper();
        String result = "";
        Map<Integer, BigDecimal> descIdItemCostMap = new HashMap<Integer, BigDecimal>();
        List<com.psk.pms.model.ProjectConfiguration.ItemDetail> itemList = mapper.readValue(projectItemConfiguration.getItemPriceConfiguration(), mapper.getTypeFactory().constructCollectionType(List.class, com.psk.pms.model.ProjectConfiguration.ItemDetail.class));
        projectItemConfiguration.setItemDetail(itemList);
        result = itemValidator.validateItem(result, projectItemConfiguration);
        if (result != "") {
            LOGGER.warn("Duplicate items found, save unsuccessful");
            return result;
        }
        try {
            Map<String, BigDecimal> itemNamePriceMap = getConvertItemListToMap(itemList);
            itemService.updatePriceAndCostForConfiguredItems(projectId, itemNamePriceMap, descIdItemCostMap, Constants.PSK);
            if (!descIdItemCostMap.isEmpty()) {
                itemService.updateProjectDescriptionWithRecalculatedCost(projectId, descIdItemCostMap);
            }
            boolean status = itemService.configureItemPrice(projectItemConfiguration);
            if (status) {
                result = Constants.ITEM_SAVE_SUCCESSFUL;
            }
        } catch (Exception e) {
            LOGGER.error("Failed while saving projectDescriptions :" + e);
        }
        return result;
    }

    private Map<String, BigDecimal> getConvertItemListToMap(List<ProjectConfiguration.ItemDetail> itemList) {
        Map<String, BigDecimal> itemNamePriceMap = new HashMap<String, BigDecimal>();
        for (ProjectConfiguration.ItemDetail itemDetail : itemList) {
            if (!itemDetail.getItemName().isEmpty()) {
                itemNamePriceMap.put(itemDetail.getItemName(), new BigDecimal(itemDetail.getItemPrice()));
            }
        }
        return itemNamePriceMap;
    }

    @RequestMapping(value = "/emp/myview/buildBaseDesc/loadBaseDescItems.do")
    public String loadBaseDescItems(Model model, @RequestParam String baseDescId, @RequestParam String employeeId, @RequestParam String descType) {

        DescItemDetail descItemDetail = new DescItemDetail();
        descItemDetail.setBaseDescId(new Integer(baseDescId));
        descItemDetail = itemService.getBaseDescription(descItemDetail);

        Gson gson = new Gson();
        JsonElement element = gson.toJsonTree(descItemDetail.getItemDetail(), new TypeToken<List<ItemDetail>>() {
        }.getType());
        if (!element.isJsonArray()) {

        }
        JsonArray jsonArray = element.getAsJsonArray();
        descItemDetail.setDescItemDetail(jsonArray.toString());
        descItemDetail.setBaseDescId(new Integer(baseDescId));
        descItemDetail.setEmployeeId(employeeId);
        descItemDetail.setDescType(descType);
        model.addAttribute("descItemForm", descItemDetail);
        ProjDescDetail projDescDetail = new ProjDescDetail();
        model.addAttribute("baseDescForm", projDescDetail);
        ProjDescDetail baseDescDetail = projectDescService.getBaseDescDetail(baseDescId);
        model.addAttribute("baseDescForm", baseDescDetail);
        return "BaseItem";
    }

    @ModelAttribute("itemTypes")
    public List<String> fetchItemTypes() {
        LOGGER.info("method = fetchTypes()");
        return projectService.getDropDownValuesFor(ITEM_TYPE);
    }


    @RequestMapping("emp/myview/configureLead/{employeeId}")
    public String configureLead(@PathVariable String employeeId, @RequestParam String project, @RequestParam String subProject, Model model) {
        Gson gson = new Gson();
        LeadDetailConfiguration leadDetailConfiguration = itemService.getLeadDetails(project, subProject);
        leadDetailConfiguration.setEmployeeId(employeeId);
        JsonElement element = gson.toJsonTree(leadDetailConfiguration.getLeadDetails(), new TypeToken<List<LeadDetailConfiguration.LeadDetail>>() {
        }.getType());
        JsonArray jsonArray = element.getAsJsonArray();
        leadDetailConfiguration.setLeadConfiguration(jsonArray.toString());
        model.addAttribute("leadDetailForm", leadDetailConfiguration);
        return "ConfigureLeadDetails";
    }

    @RequestMapping(value = "emp/myview/configureLead/createLead.do", method = RequestMethod.POST, consumes = "application/json")
    public
    @ResponseBody
    void createOrUpdateLeadDetail(@RequestBody LeadDetailConfiguration leadDetailConfiguration) throws IOException {
        LOGGER.info("method = createOrUpdateLeadDetail()");
        ObjectMapper mapper = new ObjectMapper();
        Map<String, BigDecimal> materialNameCostMap;
        Integer projectId = leadDetailConfiguration.getProjectId();
        List<LeadDetail> leadDetailList = mapper.readValue(leadDetailConfiguration.getLeadConfiguration(), mapper.getTypeFactory().constructCollectionType(List.class, LeadDetail.class));
        leadDetailConfiguration.setLeadDetails(leadDetailList);
        materialNameCostMap = getConvertLeadDetailListToMap(leadDetailList);
        getLeadConfiguredItemNames(leadDetailList);
        itemService.updatePricesForAlreadyConfiguredItems(projectId, materialNameCostMap, getLeadConfiguredItemNames(leadDetailList), Constants.GOVERNMENT);
        projectDescService.recalculateProjectPrices(projectId, Constants.GOVERNMENT);
        itemService.saveLeadDetails(leadDetailConfiguration);
        LOGGER.info("Save Lead detail success");
    }

    private List<String> getLeadConfiguredItemNames(List<LeadDetail> leadDetailList) {
        List<String> itemNames = new ArrayList<String>();
        for (LeadDetail leadDetail : leadDetailList) {
            itemNames.add(leadDetail.getMaterial());
        }
        return itemNames;
    }

    private Map<String, BigDecimal> getConvertLeadDetailListToMap(List<LeadDetail> leadDetails) {
        Map<String, BigDecimal> itemNamePriceMap = new HashMap<String, BigDecimal>();
        for (LeadDetail leadDetail : leadDetails) {
            if (!leadDetail.getMaterial().isEmpty()) {
                itemNamePriceMap.put(leadDetail.getMaterial(), new BigDecimal(leadDetail.getTotal()));
            }
        }
        return itemNamePriceMap;
    }
}
