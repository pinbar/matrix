package com.percipient.matrix.controller;

import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import com.percipient.matrix.service.EmployeeCostCenterService;
import com.percipient.matrix.service.TimesheetService;
import com.percipient.matrix.util.DateUtil;
import com.percipient.matrix.view.CostCenterView;
import com.percipient.matrix.view.HrTimesheetView;
import com.percipient.matrix.view.TimesheetView;

@Controller
@RequestMapping(value = "/hr/timesheets")
public class HrTimesheetController {

    public static final String MODEL_ATTRIBUTE_HR_TIMESHEET_LIST = "hrTimesheetList";
    public static final String MODEL_ATTRIBUTE_HR_TIMESHEET = "hrTimesheet";

    @Autowired
    TimesheetService timesheetService;

    @Autowired
    EmployeeCostCenterService employeeCostCenterService;

    @Autowired
    DateUtil dateUtil;

    @RequestMapping(method = RequestMethod.GET)
    public String getTimesheets(Model model) {
        List<HrTimesheetView> hrTimesheetViewList = timesheetService
                .getTimesheetsByStatus("pending");
        model.addAttribute(MODEL_ATTRIBUTE_HR_TIMESHEET_LIST,
                hrTimesheetViewList);
        return "hr/hrTimesheetPage";
    }

    @RequestMapping(value = "/{status}", method = RequestMethod.GET)
    public String getTimesheetByStatus(@PathVariable String status, Model model) {

        List<HrTimesheetView> hrTimesheetViewList = timesheetService
                .getTimesheetsByStatus(status.toLowerCase());
        model.addAttribute(MODEL_ATTRIBUTE_HR_TIMESHEET_LIST,
                hrTimesheetViewList);
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

    @RequestMapping(value = "/save", method = RequestMethod.POST)
    public String saveTimesheet(
            @Valid @ModelAttribute(TimesheetController.MODEL_ATTRIBUTE_TIMESHEET) TimesheetView timesheetView,
            BindingResult result, Model model,
            @RequestParam(value = "employee") Integer employeeId) {

        if (result.hasErrors()) {
            model.addAttribute("error",
                    "Please input correct values where indicated .....");
            model.addAttribute(TimesheetController.MODEL_ATTRIBUTE_TIMESHEET,
                    timesheetView);

            List<CostCenterView> costCenters = employeeCostCenterService
                    .getCostCenterViewListForEmployees(employeeId);
            model.addAttribute(
                    TimesheetController.MODEL_ATTRIBUTE_COST_CENTER_LIST,
                    costCenters);
            return "timesheet/timesheetContent";
        }
        timesheetService.saveTimesheet(timesheetView);
        timesheetView = timesheetService.getTimesheet(timesheetView.getId());
        model.addAttribute(TimesheetController.MODEL_ATTRIBUTE_TIMESHEET,
                timesheetView);
        return getTimesheetByStatus(timesheetView.getStatus(), model);
    }

    @RequestMapping(value = "/addCostCodeRow", method = RequestMethod.POST)
    public String addCostCodeRow(
            @RequestParam(value = "timesheetId") Integer timesheetId,
            @RequestParam(value = "employee") Integer employeeId, Model model) {

        if (timesheetId == null) {
            model.addAttribute("error",
                    "You must save the timesheet before adding rows.");
            List<CostCenterView> costCenters = employeeCostCenterService
                    .getCostCenterViewListForEmployees(employeeId);
            model.addAttribute(
                    TimesheetController.MODEL_ATTRIBUTE_COST_CENTER_LIST,
                    costCenters);
            return "timesheet/timesheetContent";
        }
        TimesheetView timesheetView = timesheetService
                .addCostCodeRow(timesheetId);
        model.addAttribute(TimesheetController.MODEL_ATTRIBUTE_TIMESHEET,
                timesheetView);
        List<CostCenterView> costCenters = employeeCostCenterService
                .getCostCenterViewListForEmployees(employeeId);
        model.addAttribute(
                TimesheetController.MODEL_ATTRIBUTE_COST_CENTER_LIST,
                costCenters);
        return "timesheet/timesheetContent";
    }

    @RequestMapping(value = "/deleteCostCodeRow", method = RequestMethod.GET)
    public String deleteTimesheetCostCodeRow(Model model,
            @RequestParam(value = "timesheetId") Integer timesheetId,
            @RequestParam(value = "employee") Integer employeeId,
            @RequestParam(value = "costCode", required = false) String costCode) {

        if (timesheetId == null) {
            model.addAttribute("error",
                    "You must save the timesheet before deleting rows.");
            List<CostCenterView> costCenters = employeeCostCenterService
                    .getCostCenterViewListForEmployees(employeeId);
            model.addAttribute(
                    TimesheetController.MODEL_ATTRIBUTE_COST_CENTER_LIST,
                    costCenters);
            return "timesheet/timesheetContent";
        }

        timesheetService.deleteCostCodeRow(timesheetId, costCode);
        TimesheetView timesheetView = timesheetService
                .getTimesheet(timesheetId);
        model.addAttribute(TimesheetController.MODEL_ATTRIBUTE_TIMESHEET,
                timesheetView);
        return "timesheet/timesheetContent";

    }

}
