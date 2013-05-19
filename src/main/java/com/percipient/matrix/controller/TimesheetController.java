package com.percipient.matrix.controller;

import java.util.Date;
import java.util.List;

import javax.validation.Valid;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import com.percipient.matrix.service.CostCenterService;
import com.percipient.matrix.service.TimesheetService;
import com.percipient.matrix.util.DateUtil;
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
    CostCenterService costCenterService;

    @Autowired
    DateUtil dateUtil;

    @RequestMapping(method = RequestMethod.GET)
    public String getTimesheetPreview(Model model) {

        List<TimesheetView> tsPreviews = timesheetService.getTimesheetPreview();
        model.addAttribute(MODEL_ATTRIBUTE_TIMESHEET, tsPreviews.get(0));
        model.addAttribute(MODEL_ATTRIBUTE_TIMESHEET_LIST, tsPreviews);
        List<CostCenterView> costCenters = costCenterService.getCostCenters();
        model.addAttribute(MODEL_ATTRIBUTE_COST_CENTER_LIST, costCenters);

        return "timesheet/timesheetPage";
    }

    @RequestMapping(value = "/{weekEnding}", method = RequestMethod.GET)
    public String getTimesheetByWeekEnding(@PathVariable String weekEnding,
            Model model) {

        TimesheetView timesheet = timesheetService.getTimesheet(dateUtil
                .getAsDate(weekEnding));
        model.addAttribute(MODEL_ATTRIBUTE_TIMESHEET, timesheet);
        return gotoTimesheetPage(model);
    }

    @RequestMapping(value = "/new/{weekEnding}", method = RequestMethod.GET)
    public String createNewTimesheet(@PathVariable String weekEnding,
            Model model) {

        Date weekEndingDate = StringUtils.isBlank(weekEnding) ? dateUtil
                .getCurrentWeekEndingDate() : dateUtil
                .getWeekEndingDate(dateUtil.getAsDate(weekEnding));
        ;
        TimesheetView timesheetView = timesheetService
                .createTimesheet(weekEndingDate);
        model.addAttribute(MODEL_ATTRIBUTE_TIMESHEET, timesheetView);
        return gotoTimesheetPage(model);
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
            return gotoTimesheetPage(model);
        }
        TimesheetView timesheetView = timesheetService
                .addCostCodeRow(timesheetId);
        model.addAttribute(MODEL_ATTRIBUTE_TIMESHEET, timesheetView);
        return gotoTimesheetPage(model);
    }

    @RequestMapping(value = "/save", method = RequestMethod.POST)
    public String saveTimesheet(
            @Valid @ModelAttribute(MODEL_ATTRIBUTE_TIMESHEET) TimesheetView timesheetView,
            BindingResult result, Model model) {

        if (result.hasErrors()) {
            return gotoTimesheetPage(model);
        }
        timesheetService.saveTimesheet(timesheetView);
        timesheetView = timesheetService.getTimesheet(dateUtil
                .getAsDate(timesheetView.getWeekEnding()));
        model.addAttribute(MODEL_ATTRIBUTE_TIMESHEET, timesheetView);
        return gotoTimesheetPage(model);
    }

    private String gotoTimesheetPage(Model model) {

        List<TimesheetView> tsPreviews = timesheetService.getTimesheetPreview();
        model.addAttribute(MODEL_ATTRIBUTE_TIMESHEET_LIST, tsPreviews);
        List<CostCenterView> costCenters = costCenterService.getCostCenters();
        model.addAttribute(MODEL_ATTRIBUTE_COST_CENTER_LIST, costCenters);

        return "timesheet/timesheetPage";
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
            gotoTimesheetPage(model);
        }

        timesheetService.deleteCostCodeRow(timesheetId, costCode);
        TimesheetView timesheetView = timesheetService.getTimesheet(dateUtil
                .getAsDate(weekEnding));
        model.addAttribute(MODEL_ATTRIBUTE_TIMESHEET, timesheetView);
        return gotoTimesheetPage(model);

    }

    @RequestMapping(value = "/delete/{timesheetId}", method = RequestMethod.GET)
    public String deleteTimesheet(Model model, @PathVariable Integer timesheetId) {

        timesheetService.deleteTimesheet(timesheetId);
        return getTimesheetPreview(model);
    }

}