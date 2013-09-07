package com.percipient.matrix.controller;

import java.util.Date;
import java.util.List;

import javax.validation.Valid;

import org.apache.commons.lang3.StringUtils;
import org.codehaus.jackson.map.ObjectMapper;
import org.codehaus.jackson.node.ObjectNode;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.percipient.matrix.service.EmployeeCostCenterService;
import com.percipient.matrix.service.TimesheetService;
import com.percipient.matrix.session.UserInfo;
import com.percipient.matrix.util.DateUtil;
import com.percipient.matrix.validator.TimesheetValidator;
import com.percipient.matrix.view.CostCenterView;
import com.percipient.matrix.view.TimesheetView;

@Controller
@RequestMapping(value = "/timesheet")
public class TimesheetController {

    public static final String MODEL_ATTRIBUTE_TIMESHEET_LIST = "timesheetList";
    public static final String MODEL_ATTRIBUTE_TIMESHEET = "timesheet";
    public static final String MODEL_ATTRIBUTE_COST_CENTER_LIST = "costCenters";

    @Autowired
    TimesheetService timesheetService;
    @Autowired
    EmployeeCostCenterService employeeCostCenterService;

    @Autowired
    DateUtil dateUtil;

    @Autowired
    UserInfo userInfo;

    @Autowired
    TimesheetValidator timesheetValidator;

    @InitBinder
    protected void initBinder(WebDataBinder binder) {
        binder.setValidator(timesheetValidator);
    }

    @RequestMapping(method = RequestMethod.GET)
    public String getTimesheetPreview(Model model) {

        List<TimesheetView> tsPreviews = timesheetService.getTimesheetPreview();
        model.addAttribute(MODEL_ATTRIBUTE_TIMESHEET, tsPreviews.get(0));
        model.addAttribute(MODEL_ATTRIBUTE_TIMESHEET_LIST, tsPreviews);
        List<CostCenterView> costCenters = employeeCostCenterService
                .getCostCenterViewListForEmployees(userInfo.getEmployeeId());
        model.addAttribute(MODEL_ATTRIBUTE_COST_CENTER_LIST, costCenters);

        return "timesheet/timesheetPage";
    }

    @RequestMapping(method = RequestMethod.GET, value = "/home")
    public String getTimesheetLanding(Model model) {
        return "timesheet/timesheetLandingPage";
    }

    @RequestMapping(value = "/listAsJson", method = RequestMethod.GET)
    @ResponseBody
    public ObjectNode getTimesheetListAsJson() {
        List<TimesheetView> tsPreviews = timesheetService.getTimesheets();
        ObjectMapper mapper = new ObjectMapper();
        ObjectNode retObject = mapper.createObjectNode();
        retObject.putPOJO("timesheets", tsPreviews);
        retObject.put("iTotalRecords", tsPreviews.size());
        retObject.put("iTotalDisplayRecords", tsPreviews.size());
        return retObject;

    }

    @RequestMapping(value = "/{id}", method = RequestMethod.GET)
    public String getTimesheetByIdAsJson(@PathVariable Integer id, Model model) {

        TimesheetView timesheet = timesheetService.getTimesheet(id);
        model.addAttribute(MODEL_ATTRIBUTE_TIMESHEET, timesheet);
        return gotoTimesheetContentWrapper(model);
    }

    @RequestMapping(value = "weekendings/{weekEnding}", method = RequestMethod.GET)
    public String createNewTimesheet(@PathVariable String weekEnding,
            Model model) {

        Date weekEndingDate = StringUtils.isBlank(weekEnding) ? dateUtil
                .getCurrentWeekEndingDate() : dateUtil
                .getWeekEndingDate(dateUtil.getAsDate(weekEnding));
        TimesheetView timesheetView = timesheetService
                .getTimesheet(weekEndingDate);
        model.addAttribute(MODEL_ATTRIBUTE_TIMESHEET, timesheetView);
        return gotoTimesheetContentWrapper(model);
    }

    @RequestMapping(value = "/addCostCodeRow", method = RequestMethod.GET)
    public String addCostCodeRow(
            @RequestParam(value = "timesheetId", required = false) Integer timesheetId,
            @RequestParam(value = "weekEnding", required = false) String weekEnding,
            Model model) {

        if (timesheetId == null) {
            model.addAttribute("error",
                    "You must save the timesheet before adding rows.");
            TimesheetView timesheetView = timesheetService
                    .getTimesheet(dateUtil.getAsDate(weekEnding));
            model.addAttribute(MODEL_ATTRIBUTE_TIMESHEET, timesheetView);
            return gotoTimesheetContentWrapper(model);
        }
        TimesheetView timesheetView = timesheetService
                .addCostCodeRow(timesheetId);
        model.addAttribute(MODEL_ATTRIBUTE_TIMESHEET, timesheetView);
        return gotoTimesheetContentWrapper(model);
    }

    @RequestMapping(value = "/deleteCostCodeRow", method = RequestMethod.GET)
    public String deleteTimesheetCostCodeRow(
            Model model,
            @RequestParam(value = "timesheetId", required = false) Integer timesheetId,
            @RequestParam(value = "weekEnding", required = false) String weekEnding,
            @RequestParam(value = "costCode", required = false) String costCode) {

        if (timesheetId == null) {
            model.addAttribute("error",
                    "You must save the timesheet before deleting rows.");
            gotoTimesheetContentWrapper(model);
        }

        timesheetService.deleteCostCodeRow(timesheetId, costCode);
        TimesheetView timesheetView = timesheetService.getTimesheet(dateUtil
                .getAsDate(weekEnding));
        model.addAttribute(MODEL_ATTRIBUTE_TIMESHEET, timesheetView);
        return gotoTimesheetContentWrapper(model);

    }

    @RequestMapping(value = "/save", method = RequestMethod.POST)
    public String saveTimesheet(
            @Valid @ModelAttribute(MODEL_ATTRIBUTE_TIMESHEET) TimesheetView timesheetView,
            BindingResult result, Model model) {

        if (result.hasErrors()) {
            return gotoTimesheetContent(model);
        }
        timesheetService.saveTimesheet(timesheetView);
        timesheetView = timesheetService.getTimesheet(dateUtil
                .getAsDate(timesheetView.getWeekEnding()));
        model.addAttribute(MODEL_ATTRIBUTE_TIMESHEET, timesheetView);
        return gotoTimesheetContent(model);
    }

    @RequestMapping(value = "/submit", method = RequestMethod.POST)
    public String submitTimesheet(
            @Valid @ModelAttribute(MODEL_ATTRIBUTE_TIMESHEET) TimesheetView timesheetView,
            BindingResult result, Model model) {

        if (result.hasErrors()) {
            return gotoTimesheetContent(model);
        }
        timesheetView.setStatus("Submitted");
        timesheetService.saveTimesheet(timesheetView);
        timesheetView = timesheetService.getTimesheet(dateUtil
                .getAsDate(timesheetView.getWeekEnding()));
        model.addAttribute(MODEL_ATTRIBUTE_TIMESHEET, timesheetView);
        return gotoTimesheetContent(model);
    }

    @RequestMapping(value = "/activate/{timesheetId}", method = RequestMethod.GET)
    public String activateTimesheet(Model model,
            @PathVariable Integer timesheetId) {

        TimesheetView timesheetView = timesheetService
                .getTimesheet(timesheetId);
        if ("Approved".equalsIgnoreCase(timesheetView.getStatus())) {
            model.addAttribute("error",
                    "Approved Timesheet cannot be activated..");
        } else {
            timesheetView.setStatus("pending");
            timesheetService.saveTimesheet(timesheetView);
        }
        model.addAttribute(MODEL_ATTRIBUTE_TIMESHEET, timesheetView);
        return gotoTimesheetContent(model);
    }

    @RequestMapping(value = "/delete/{timesheetId}", method = RequestMethod.GET)
    public String deleteTimesheet(Model model, @PathVariable Integer timesheetId) {

        timesheetService.deleteTimesheet(timesheetId);
        return getTimesheetPreview(model);
    }

    private String gotoTimesheetPage(Model model) {

        List<TimesheetView> tsPreviews = timesheetService.getTimesheetPreview();
        model.addAttribute(MODEL_ATTRIBUTE_TIMESHEET_LIST, tsPreviews);
        List<CostCenterView> costCenters = employeeCostCenterService
                .getCostCenterViewListForEmployees(userInfo.getEmployeeId());
        model.addAttribute(MODEL_ATTRIBUTE_COST_CENTER_LIST, costCenters);

        return "timesheet/timesheetPage";
    }

    private String gotoTimesheetContent(Model model) {

        List<CostCenterView> costCenters = employeeCostCenterService
                .getCostCenterViewListForEmployees(userInfo.getEmployeeId());
        model.addAttribute(MODEL_ATTRIBUTE_COST_CENTER_LIST, costCenters);

        return "timesheet/timesheetContent";
    }

    private String gotoTimesheetContentWrapper(Model model) {
        List<CostCenterView> costCenters = employeeCostCenterService
                .getCostCenterViewListForEmployees(userInfo.getEmployeeId());
        model.addAttribute(MODEL_ATTRIBUTE_COST_CENTER_LIST, costCenters);

        return "timesheet/timesheetContentWrapper";
    }
}
