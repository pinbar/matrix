package com.percipient.matrix.validator;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.ValidationUtils;
import org.springframework.validation.Validator;

import com.percipient.matrix.view.TSCostCenterView;
import com.percipient.matrix.view.TimesheetView;

@Component
public class TimesheetValidator implements Validator {

    public boolean supports(Class<?> clazz) {
        return TimesheetView.class.equals(clazz);
    }

    public void validate(Object obj, Errors errors) {
        TimesheetView tsView = (TimesheetView) obj;
        if (tsView.getTsCostCenters() == null) {
            return;
        }
        int size = tsView.getTsCostCenters().size();
        for (int i = 0; i < size; i++) {
            ValidationUtils.rejectIfEmpty(errors, "tsCostCenters[" + i
                    + "].costCode", "timesheet.costcode.empty");
            ValidationUtils.rejectIfEmpty(errors, "weekEnding",
                    "timesheet.weekending.empty");
        }
        if (!errors.hasErrors()) {
            validateCostCenterUnique(tsView, errors);
        }
        if (!errors.hasErrors()) {
            validateDailyHoursMax(tsView, errors);
        }
    }

    private void validateCostCenterUnique(TimesheetView tsView, Errors errors) {
        List<String> ccList = new ArrayList<String>();
        int size = tsView.getTsCostCenters().size();
        String costCode = "";
        for (int i = 0; i < size; i++) {
            costCode = tsView.getTsCostCenters().get(i).getCostCode();
            if (ccList.contains(costCode)) {
                errors.reject("timesheet.costcode.unique");
                return;
            }
            ccList.add(costCode);
        }
        return;
    }

    private void validateDailyHoursMax(TimesheetView tsView, Errors errors) {
        double mondayHours = 0.00;
        double tuesdayHours = 0.00;
        double wednesdayHours = 0.00;
        double thursdayHours = 0.00;
        double fridayHours = 0.00;
        double saturdayHours = 0.00;
        double sundayHours = 0.00;
        int size = tsView.getTsCostCenters().size();
        for (int i = 0; i < size; i++) {
            TSCostCenterView tsCCView = tsView.getTsCostCenters().get(i);
            mondayHours = mondayHours + tsCCView.getMonday().getHours();
            if (mondayHours > 24) {
                errors.reject("timesheet.totalhours.day",
                        new Object[] { "monday" },
                        "Total hours in a day cannot be more than 24");
                return;
            }
            tuesdayHours = tuesdayHours + tsCCView.getTuesday().getHours();
            if (tuesdayHours > 24) {
                errors.reject("timesheet.totalhours.day",
                        new Object[] { "tuesday" },
                        "Total hours in a day cannot be more than 24");
                return;
            }
            wednesdayHours = wednesdayHours
                    + tsCCView.getWednesday().getHours();
            if (wednesdayHours > 24) {
                errors.reject("timesheet.totalhours.day",
                        new Object[] { "wednesday" },
                        "Total hours in a day cannot be more than 24");
                return;
            }
            thursdayHours = thursdayHours + tsCCView.getThursday().getHours();
            if (thursdayHours > 24) {
                errors.reject("timesheet.totalhours.day",
                        new Object[] { "thursday" },
                        "Total hours in a day cannot be more than 24");
                return;
            }
            fridayHours = fridayHours + tsCCView.getFriday().getHours();
            if (fridayHours > 24) {
                errors.reject("timesheet.totalhours.day",
                        new Object[] { "friday" },
                        "Total hours in a day cannot be more than 24");
                return;
            }
            saturdayHours = saturdayHours + tsCCView.getSaturday().getHours();
            if (saturdayHours > 24) {
                errors.reject("timesheet.totalhours.day",
                        new Object[] { "saturday" },
                        "Total hours in a day cannot be more than 24");
                return;
            }
            sundayHours = sundayHours + tsCCView.getSunday().getHours();
            if (sundayHours > 24) {
                errors.reject("timesheet.totalhours.day",
                        new Object[] { "sunday" },
                        "Total hours in a day cannot be more than 24");
                return;
            }
        }
    }
}
