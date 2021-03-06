package com.percipient.matrix.controller;

import java.util.ArrayList;
import java.util.Date;
import java.util.EnumSet;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.validation.Valid;

import org.apache.commons.lang3.StringUtils;
import org.codehaus.jackson.JsonNode;
import org.codehaus.jackson.map.ObjectMapper;
import org.codehaus.jackson.node.ArrayNode;
import org.codehaus.jackson.node.ObjectNode;
import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.percipient.matrix.common.Status;
import com.percipient.matrix.service.EmployeeCostCenterService;
import com.percipient.matrix.service.EmployeeService;
import com.percipient.matrix.service.TimesheetService;
import com.percipient.matrix.session.UserInfo;
import com.percipient.matrix.util.DateUtil;
import com.percipient.matrix.util.Logging.Loggable;
import com.percipient.matrix.validator.TimesheetValidator;
import com.percipient.matrix.view.CostCenterView;
import com.percipient.matrix.view.EmployeeView;
import com.percipient.matrix.view.HrTimesheetView;
import com.percipient.matrix.view.TimesheetView;

@Controller
@RequestMapping(value = "/hr/timesheets")
public class HrTimesheetController {

    public static final String MODEL_ATTRIBUTE_HR_TIMESHEET_LIST = "hrTimesheetList";
    public static final String MODEL_ATTRIBUTE_HR_TIMESHEET = "hrTimesheet";
    private static final String TOTAL_RECORDS_VIEW_PROP = "iTotalRecords";
    private static final String TOTAL_DISPLAY_RECORDS_VIEW_PROP = "iTotalDisplayRecords";

    private static enum Action {
        APPROVED("approve"), REJECTED("reject");

        private String actionVal;
        private static final Map<String, Action> lookup = new HashMap<String, Action>();

        static {
            for (Action s : EnumSet.allOf(Action.class))
                lookup.put(s.getVal(), s);
        }

        private Action(String val) {
            actionVal = val;
        }

        public String getVal() {
            return actionVal;
        }

        public static Action get(String val) {
            return lookup.get(val);
        }
    };

    @Loggable
    private Logger logger;

    @Autowired
    TimesheetService timesheetService;

    @Autowired
    EmployeeService employeeService;

    @Autowired
    EmployeeCostCenterService employeeCostCenterService;

    @Autowired
    DateUtil dateUtil;

    @Autowired
    private javax.inject.Provider<UserInfo> userInfo;

    @Autowired
    TimesheetValidator timesheetValidator;

    @InitBinder
    protected void initBinder(WebDataBinder binder) {
        binder.setValidator(timesheetValidator);
    }

    @RequestMapping(method = RequestMethod.GET)
    public String getTimesheets(Model model) {
        List<EmployeeView> reportees = userInfo.get().getReporteeViews();
        model.addAttribute("employees", getEmployListAsJSONString(reportees));
        return "hr/hrTimesheetPage";
    }

    @RequestMapping(value = "/listAsJson/{status}")
    public @ResponseBody
    ObjectNode getTimesheetListAsJSON(@PathVariable String status, Model model) {

        List<HrTimesheetView> hrTimesheetViewList = null;
        if (userInfo.get().getReporteeIds().isEmpty()) {
            hrTimesheetViewList = timesheetService.getTimesheetsByStatus(status
                    .toLowerCase());
        } else {
            Set<Integer> reporteeIds = userInfo.get().getReporteeIds();
            hrTimesheetViewList = timesheetService
                    .getReporteeTimesheetsByStatus(status.toLowerCase(),
                            reporteeIds);
        }
        return getHrTimesheetListAsJSON(hrTimesheetViewList);
    }

    @RequestMapping(value = "/reportees/listAsJson/{status}")
    public @ResponseBody
    ObjectNode getReporteesTimesheetListAsJSON(@PathVariable String status,
            Model model) {

        Set<Integer> reporteeIds = userInfo.get().getReporteeIds();
        List<HrTimesheetView> hrTimesheetViewList = timesheetService
                .getReporteeTimesheetsByStatus(status.toLowerCase(),
                        reporteeIds);
        return getHrTimesheetListAsJSON(hrTimesheetViewList);
    }

    @RequestMapping(value = "/{status}", method = RequestMethod.GET)
    public String getTimesheetByStatus(@PathVariable String status, Model model) {

        model.addAttribute(MODEL_ATTRIBUTE_HR_TIMESHEET_LIST,
                new ArrayList<HrTimesheetView>());
        List<EmployeeView> reportees = userInfo.get().getReporteeViews();
        model.addAttribute("employees", getEmployListAsJSONString(reportees));
        return "hr/hrTimesheetPage";
    }

    @RequestMapping(value = "/{status}/{id}", method = RequestMethod.GET)
    public String getTimesheetById(@PathVariable String status,
            @PathVariable Integer id,
            @RequestParam(value = "employee") Integer employeeId, Model model) {

        TimesheetView timesheet = timesheetService.getTimesheet(id);

        model.addAttribute(TimesheetController.MODEL_ATTRIBUTE_TIMESHEET,
                timesheet);
        List<CostCenterView> costCenters = employeeCostCenterService
                .getCostCenterViewListForEmployees(employeeId);
        model.addAttribute(
                TimesheetController.MODEL_ATTRIBUTE_COST_CENTER_LIST,
                costCenters);
        return "timesheet/timesheetContent";
    }

    @RequestMapping(value = "/{status}/{timesheetId}/{action}", method = RequestMethod.GET)
    public String approveRejectTimesheet(Model model,
            @PathVariable String status, @PathVariable Integer timesheetId,
            @PathVariable String action) {

        if (timesheetId == null) {
            model.addAttribute("error", "Timesheet id is required");
            return "timesheet/hrTimesheetPage";
        }

        TimesheetView timesheetView = timesheetService
                .getTimesheet(timesheetId);
        if (!StringUtils.isEmpty(action)) {
            timesheetView.setStatus(Action.get(action).name().toLowerCase());
        }

        timesheetService.saveTimesheet(timesheetView);
        List<HrTimesheetView> hrTimesheetViewList = timesheetService
                .getTimesheetsByStatus(status.toLowerCase());
        model.addAttribute(MODEL_ATTRIBUTE_HR_TIMESHEET_LIST,
                hrTimesheetViewList);
        return "hr/hrTimesheetPage";
    }

    @RequestMapping(value = "/{status}/{action}", method = RequestMethod.POST)
    public String approveRejectTimesheet(Model model,
            @PathVariable String status, @PathVariable String action,
            @RequestBody ObjectNode selections) {
        if (StringUtils.isEmpty(action)) {
            model.addAttribute("error", "Changing status is not allowed ");
            return "hr/hrTimesheetPage";
        }
        List<HrTimesheetView> hrTimesheetViews = timesheetService
                .getTimesheetsByStatus(status.toLowerCase());

        if (selections.get("all").asBoolean()) {
            for (HrTimesheetView view : hrTimesheetViews) {
                view.setStatus(Action.get(action).name().toLowerCase());
            }
        } else if (null != selections.get("selectionArray")
                && selections.get("selectionArray").isArray()) {
            JsonNode selectionArray = selections.get("selectionArray");
            List<Integer> selectedIds = getSelectionFromSelectionJSONArray(selectionArray);
            List<HrTimesheetView> selectedHrTimesheetViews = processSelectedViews(
                    action, hrTimesheetViews, selectedIds);
            hrTimesheetViews = selectedHrTimesheetViews;
        }
        timesheetService.saveTimesheets(hrTimesheetViews);
        model.addAttribute(MODEL_ATTRIBUTE_HR_TIMESHEET_LIST, hrTimesheetViews);
        return "hr/hrTimesheetPage";
    }

    private List<HrTimesheetView> processSelectedViews(String action,
            List<HrTimesheetView> hrTimesheetViews, List<Integer> selectedIds) {
        List<HrTimesheetView> filteredHrTimesheetViews = new ArrayList<HrTimesheetView>();
        for (HrTimesheetView view : hrTimesheetViews) {
            if (selectedIds.contains(Integer.valueOf(view.getTimesheetId())))
                view.setStatus(Action.get(action).name().toLowerCase());
            filteredHrTimesheetViews.add(view);
        }
        return filteredHrTimesheetViews;
    }

    private List<Integer> getSelectionFromSelectionJSONArray(
            JsonNode selectionArray) {
        List<Integer> selectedIds = new ArrayList<Integer>();
        Iterator<JsonNode> ite = selectionArray.getElements();
        while (ite.hasNext()) {
            JsonNode temp = ite.next();
            selectedIds.add(temp.asInt());
        }
        return selectedIds;
    }

    @RequestMapping(value = "/save", method = RequestMethod.POST)
    public String saveTimesheet(
            @Valid @ModelAttribute(TimesheetController.MODEL_ATTRIBUTE_TIMESHEET) TimesheetView timesheetView,
            BindingResult result, Model model,
            @RequestParam(value = "employee") Integer employeeId) {

        List<CostCenterView> costCenters = employeeCostCenterService
                .getCostCenterViewListForEmployees(employeeId);
        model.addAttribute(
                TimesheetController.MODEL_ATTRIBUTE_COST_CENTER_LIST,
                costCenters);
        if (result.hasErrors()) {
            model.addAttribute("error",
                    "Please input correct values where indicated .....");
            model.addAttribute(TimesheetController.MODEL_ATTRIBUTE_TIMESHEET,
                    timesheetView);
            return "timesheet/timesheetContent";
        }
        timesheetService.saveTimesheet(timesheetView);
        timesheetView = timesheetService.getTimesheet(
                dateUtil.getAsDate(timesheetView.getWeekEnding()), employeeId);
        model.addAttribute(TimesheetController.MODEL_ATTRIBUTE_TIMESHEET,
                timesheetView);
        return "timesheet/timesheetContent";
    }

    @RequestMapping(value = "/activate", method = RequestMethod.POST)
    public String activateTimesheet(
            @Valid @ModelAttribute(TimesheetController.MODEL_ATTRIBUTE_TIMESHEET) TimesheetView timesheetView,
            BindingResult result, Model model,
            @RequestParam(value = "employee") Integer employeeId,
            @RequestParam(value = "status") String status) {

        List<CostCenterView> costCenters = employeeCostCenterService
                .getCostCenterViewListForEmployees(employeeId);
        model.addAttribute(
                TimesheetController.MODEL_ATTRIBUTE_COST_CENTER_LIST,
                costCenters);
        if (Status.APPROVED.getVal().equalsIgnoreCase(status)) {
            ObjectError error = new ObjectError("error",
                    "Approved Timesheet cannot be activated..");
            result.addError(error);
        }// TODO supervisors may be able to activate ? && employee not
         // supervisor)
        if (result.hasErrors()) {
            if (null == result.getGlobalError()) {
                model.addAttribute("error",
                        "Please input correct values where indicated .....");
            } else {
                model.addAttribute("error", result.getGlobalError()
                        .getDefaultMessage());
            }
            model.addAttribute(TimesheetController.MODEL_ATTRIBUTE_TIMESHEET,
                    timesheetView);
            return "timesheet/timesheetContent";
        }
        timesheetView
                .setStatus(StringUtils.capitalize(Status.PENDING.getVal()));
        timesheetService.saveTimesheet(timesheetView);
        timesheetView = timesheetService.getTimesheet(timesheetView.getId());
        model.addAttribute(TimesheetController.MODEL_ATTRIBUTE_TIMESHEET,
                timesheetView);
        return "timesheet/timesheetContent";
    }

    @RequestMapping(value = "/submit", method = RequestMethod.POST)
    public String submitTimesheet(
            @Valid @ModelAttribute(TimesheetController.MODEL_ATTRIBUTE_TIMESHEET) TimesheetView timesheetView,
            BindingResult result, Model model,
            @RequestParam(value = "employee") Integer employeeId) {

        List<CostCenterView> costCenters = employeeCostCenterService
                .getCostCenterViewListForEmployees(employeeId);
        model.addAttribute(
                TimesheetController.MODEL_ATTRIBUTE_COST_CENTER_LIST,
                costCenters);
        if (result.hasErrors()) {
            model.addAttribute("error",
                    "Please input correct values where indicated .....");
            model.addAttribute(TimesheetController.MODEL_ATTRIBUTE_TIMESHEET,
                    timesheetView);
            return "timesheet/timesheetContent";
        }
        timesheetView.setStatus(StringUtils.capitalize(Status.SUBMITTED
                .getVal()));
        timesheetService.saveTimesheet(timesheetView);
        timesheetView = timesheetService.getTimesheet(timesheetView.getId());
        model.addAttribute(TimesheetController.MODEL_ATTRIBUTE_TIMESHEET,
                timesheetView);
        return "timesheet/timesheetContent";
    }

    @RequestMapping(value = "/create/{weekEnding}/{employee}", method = RequestMethod.POST)
    public// @ResponseBody TimesheetView
    String createNewTimesheet(@PathVariable String weekEnding,
            @PathVariable Integer employee, Model model) {

        Date weekEndingDate = StringUtils.isBlank(weekEnding) ? dateUtil
                .getCurrentWeekEndingDate() : dateUtil
                .getWeekEndingDate(dateUtil.getAsDate(weekEnding));
        TimesheetView timesheetView = timesheetService.createTimesheet(
                weekEndingDate, employee);
        model.addAttribute(TimesheetController.MODEL_ATTRIBUTE_TIMESHEET,
                timesheetView);
        List<CostCenterView> costCenters = employeeCostCenterService
                .getCostCenterViewListForEmployees(employee);
        model.addAttribute(
                TimesheetController.MODEL_ATTRIBUTE_COST_CENTER_LIST,
                costCenters);
        return "timesheet/timesheetContent";
    }

    @RequestMapping(value = "/addCostCodeRow", method = RequestMethod.POST)
    public String addCostCodeRow(
            @RequestParam(value = "timesheetId") Integer timesheetId,
            @RequestParam(value = "employee") Integer employeeId,
            @RequestParam(value = "weekEnding") String weekEnding,
            Model model) {

        List<CostCenterView> costCenters = employeeCostCenterService
                .getCostCenterViewListForEmployees(employeeId);
        model.addAttribute(
                TimesheetController.MODEL_ATTRIBUTE_COST_CENTER_LIST,
                costCenters);

        if (timesheetId == null) {
            model.addAttribute("error",
                    "You must save the timesheet before adding rows.");
            TimesheetView timesheetView = timesheetService
                    .getTimesheet(dateUtil.getAsDate(weekEnding));
            model.addAttribute(TimesheetController.MODEL_ATTRIBUTE_TIMESHEET, timesheetView);
            return "timesheet/timesheetContent";
        }
        TimesheetView timesheetView = timesheetService
                .addCostCodeRow(timesheetId);
        model.addAttribute(TimesheetController.MODEL_ATTRIBUTE_TIMESHEET,
                timesheetView);

        return "timesheet/timesheetContent";
    }

    @RequestMapping(value = "/deleteCostCodeRow", method = RequestMethod.GET)
    public String deleteTimesheetCostCodeRow(
            Model model,
            @RequestParam(value = "timesheetId") Integer timesheetId,
            @RequestParam(value = "employee") Integer employeeId,
            @RequestParam(value = "costCode", required = false) String costCode,
            @RequestParam(value = "tsItemId", required = false) Integer tsItemId) {

        List<CostCenterView> costCenters = employeeCostCenterService
                .getCostCenterViewListForEmployees(employeeId);
        model.addAttribute(
                TimesheetController.MODEL_ATTRIBUTE_COST_CENTER_LIST,
                costCenters);

        if (timesheetId == null) {
            model.addAttribute("error",
                    "You must save the timesheet before deleting rows.");
            return "timesheet/timesheetContent";
        }

        if (null != tsItemId) {
            timesheetService.deleteCostCodeRow(timesheetId, costCode);
        }
        TimesheetView timesheetView = timesheetService
                .getTimesheet(timesheetId);
        model.addAttribute(TimesheetController.MODEL_ATTRIBUTE_TIMESHEET,
                timesheetView);
        return "timesheet/timesheetContent";

    }

    private ObjectNode getHrTimesheetListAsJSON(
            List<HrTimesheetView> hrTimesheetViewList) {
        ObjectMapper mapper = new ObjectMapper();
        ObjectNode retObject = mapper.createObjectNode();
        retObject.putPOJO("timesheets", hrTimesheetViewList);
        retObject.put(TOTAL_RECORDS_VIEW_PROP, hrTimesheetViewList.size());
        retObject.put(TOTAL_DISPLAY_RECORDS_VIEW_PROP,
                hrTimesheetViewList.size());
        return retObject;
    }

    private String getEmployListAsJSONString(List<EmployeeView> reportees) {
        ObjectMapper mapper = new ObjectMapper();
        ObjectNode retObject = mapper.createObjectNode();
        ObjectNode employees = mapper.createObjectNode();
        ArrayNode employeeNames = retObject.putArray("employees");
        for (EmployeeView e : reportees) {
            employees.put(e.getName(), e.getId());
            employeeNames.add(e.getName());
        }
        retObject.put("employeesWithId", employees);
        return retObject.toString();
    }
}
