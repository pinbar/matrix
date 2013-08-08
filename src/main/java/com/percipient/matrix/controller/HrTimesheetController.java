package com.percipient.matrix.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.percipient.matrix.service.TimesheetService;
import com.percipient.matrix.util.DateUtil;
import com.percipient.matrix.view.HrTimesheetView;

@Controller
@RequestMapping(value = "/hr/timesheets")
public class HrTimesheetController {

    public static final String MODEL_ATTRIBUTE_HR_TIMESHEET_LIST = "hrTimesheetList";
    public static final String MODEL_ATTRIBUTE_HR_TIMESHEET = "hrTimesheet";

    @Autowired
    TimesheetService timesheetService;

    @Autowired
    DateUtil dateUtil;

    @RequestMapping(method = RequestMethod.GET)
    public String getTimesheets(Model model) {

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
}
