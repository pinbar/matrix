package com.percipient.matrix.dashboard;

import java.util.Date;
import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import com.percipient.matrix.display.CostCenterView;
import com.percipient.matrix.display.TimesheetView;
import com.percipient.matrix.service.CostCenterService;
import com.percipient.matrix.service.TimesheetService;
import com.percipient.matrix.util.DateUtil;

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
    
    return "timesheetPage";
}

/*
 * @RequestMapping(value = "/previewList", method = RequestMethod.GET) public
 * 
 * @ResponseBody List<TimesheetView> getPreviewAsJSON(Model model) {
 * List<TimesheetView> weekEndings = timesheetService
 * .getTimesheetWeekEndingPreview(); return weekEndings; }
 */

@RequestMapping(value = "/{weekEnding}", method = RequestMethod.GET)
public String getTimesheetByWeekEnding(@PathVariable String weekEnding,
        Model model) {
    TimesheetView timesheet = timesheetService.getTimesheet(dateUtil
            .getAsDate(weekEnding));
    model.addAttribute(MODEL_ATTRIBUTE_TIMESHEET, timesheet);
    List<TimesheetView> tsPreviews = timesheetService.getTimesheetPreview();
    model.addAttribute(MODEL_ATTRIBUTE_TIMESHEET_LIST, tsPreviews);
    List<CostCenterView> costCenters = costCenterService.getCostCenters();
    model.addAttribute(MODEL_ATTRIBUTE_COST_CENTER_LIST, costCenters);
    
    return "timesheetPage";
}

@RequestMapping(value = "/new/{weekEnding}", method = RequestMethod.GET)
public String createNewTimesheet(@PathVariable String weekEnding, Model model) {
    Date weekEndingDate = StringUtils.isBlank(weekEnding) ? dateUtil
            .getCurrentWeekEndingDate() : dateUtil.getWeekEndingDate(dateUtil
            .getAsDate(weekEnding));
    ;
    TimesheetView timesheetView = timesheetService
            .createTimesheet(weekEndingDate);
    model.addAttribute(MODEL_ATTRIBUTE_TIMESHEET, timesheetView);
    List<TimesheetView> tsPreviews = timesheetService.getTimesheetPreview();
    model.addAttribute(MODEL_ATTRIBUTE_TIMESHEET_LIST, tsPreviews);
    List<CostCenterView> costCenters = costCenterService.getCostCenters();
    model.addAttribute(MODEL_ATTRIBUTE_COST_CENTER_LIST, costCenters);
    
    return "timesheetPage";
}

@RequestMapping(value = "/addCostCodeRow", method = RequestMethod.GET)
public String addCostCodeRow(
        @RequestParam(value = "timesheetId", required = false) Integer timesheetId,
        @RequestParam(value = "costCode", required = false) String costCode,
        Model model) {
    TimesheetView timesheetView = timesheetService.addCostCodeRow(timesheetId,
            costCode);
    model.addAttribute(MODEL_ATTRIBUTE_TIMESHEET, timesheetView);
    List<TimesheetView> tsPreviews = timesheetService.getTimesheetPreview();
    model.addAttribute(MODEL_ATTRIBUTE_TIMESHEET_LIST, tsPreviews);
    List<CostCenterView> costCenters = costCenterService.getCostCenters();
    model.addAttribute(MODEL_ATTRIBUTE_COST_CENTER_LIST, costCenters);
    
    return "timesheetPage";
}

@RequestMapping(value = "/save", method = RequestMethod.POST)
public String saveTimesheet(TimesheetView timesheetView, Model model) {
    timesheetView = timesheetService.saveTimesheet(timesheetView);
    model.addAttribute(MODEL_ATTRIBUTE_TIMESHEET, timesheetView);
    List<TimesheetView> tsPreviews = timesheetService.getTimesheetPreview();
    model.addAttribute(MODEL_ATTRIBUTE_TIMESHEET_LIST, tsPreviews);
    List<CostCenterView> costCenters = costCenterService.getCostCenters();
    model.addAttribute(MODEL_ATTRIBUTE_COST_CENTER_LIST, costCenters);
    
    return "timesheetPage";
}

@RequestMapping(value = "/deleteCostCodeRow", method = RequestMethod.GET)
public String deleteTimesheetCostCodeRow(
        Model model,
        @RequestParam(value = "timesheetId", required = false) Integer timesheetId,
        @RequestParam(value = "costCode", required = false) String costCode) {
    timesheetService.deleteCostCodeRow(timesheetId, costCode);
    return getTimesheetPreview(model);
    
}

@RequestMapping(value = "/delete/{timesheetId}", method = RequestMethod.GET)
public String deleteTimesheet(Model model, @PathVariable Integer timesheetId) {
    timesheetService.deleteTimesheet(timesheetId);
    return getTimesheetPreview(model);
}

}
