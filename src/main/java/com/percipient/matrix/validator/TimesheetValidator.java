package com.percipient.matrix.validator;

import java.util.ArrayList;
import java.util.List;

import org.springframework.context.annotation.Scope;
import org.springframework.context.annotation.ScopedProxyMode;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.ValidationUtils;
import org.springframework.validation.Validator;

import com.percipient.matrix.view.TSCostCenterView;
import com.percipient.matrix.view.TimesheetView;

@Component
@Scope(proxyMode = ScopedProxyMode.TARGET_CLASS, value = "prototype")
public class TimesheetValidator implements Validator {

    private static final String TIMESHEET_TOTALHOURS_ERROR_CODE = "timesheet.totalhours.day";
    private static final String TOTAL_HOURS_IN_A_DAY_EXCEEDED = "Total hours in a day cannot be more than 24";

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
            if (mondayHours > 24 ) {
                populateTotalHoursErrorMesage(errors, "monday");
                return;
            }
            tuesdayHours = tuesdayHours + tsCCView.getTuesday().getHours();
            if (tuesdayHours > 24 ) {
                populateTotalHoursErrorMesage(errors, "tuesday");
                return;
            }
            wednesdayHours = wednesdayHours
                    + tsCCView.getWednesday().getHours();
            if (wednesdayHours > 24 ) {
                populateTotalHoursErrorMesage(errors, "wednesday");
                return;
            }
            thursdayHours = thursdayHours + tsCCView.getThursday().getHours();
            if (thursdayHours > 24 ) {
                populateTotalHoursErrorMesage(errors, "thursday");
                return;
            }
            fridayHours = fridayHours + tsCCView.getFriday().getHours();
            if (fridayHours > 24) {
                populateTotalHoursErrorMesage(errors, "friday");
                return;
            }
            saturdayHours = saturdayHours + tsCCView.getSaturday().getHours();
            if (saturdayHours > 24 ) {
                populateTotalHoursErrorMesage(errors, "saturday");
                return;
            }
            sundayHours = sundayHours + tsCCView.getSunday().getHours();
            if (sundayHours > 24 ) {
                populateTotalHoursErrorMesage(errors, "sunday");
                return;
            }
        }
    }

    private void populateTotalHoursErrorMesage(Errors errors, String day) {
        errors.reject(TIMESHEET_TOTALHOURS_ERROR_CODE, new Object[] { day },
                TOTAL_HOURS_IN_A_DAY_EXCEEDED);
    }
}
