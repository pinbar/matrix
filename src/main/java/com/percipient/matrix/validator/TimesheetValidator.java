package com.percipient.matrix.validator;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.context.annotation.ScopedProxyMode;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.ValidationUtils;
import org.springframework.validation.Validator;

import com.percipient.matrix.util.DateUtil;
import com.percipient.matrix.view.TSCostCenterView;
import com.percipient.matrix.view.TimesheetView;

@Component
@Scope(proxyMode = ScopedProxyMode.TARGET_CLASS, value = "prototype")
public class TimesheetValidator implements Validator {

    @Autowired
    private DateUtil dateUtil;

    private static final String TIMESHEET_TOTALHOURS_ERROR_CODE = "timesheet.totalhours.day";
    private static final String TOTAL_HOURS_IN_A_DAY_EXCEEDED = "Total hours in a day cannot be more than 24";
    private static final String TIMESHEET_HOURS_NEGETIVE = "timesheet.hours.day";

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
            validateNotNegetiveHours(tsView, errors);
        }
        if (!errors.hasErrors()) {
            validateDailyHoursMax(tsView, errors);
        }
    }

    private void validateNotNegetiveHours(TimesheetView tsView, Errors errors) {
        List<TSCostCenterView> ccViews = tsView.getTsCostCenters();
        int size = ccViews.size();
        for (int i = 0; i < size; i++) {
            if (ccViews.get(i).getMonday().getHours() < 0
                    || ccViews.get(i).getTuesday().getHours() < 0
                    || ccViews.get(i).getWednesday().getHours() < 0
                    || ccViews.get(i).getThursday().getHours() < 0
                    || ccViews.get(i).getFriday().getHours() < 0
                    || ccViews.get(i).getSaturday().getHours() < 0
                    || ccViews.get(i).getSunday().getHours() < 0) {
                errors.reject(TIMESHEET_HOURS_NEGETIVE);
            }
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
                populateTotalHoursErrorMesage(errors,
                        DateUtil.DAYS_OF_WEEK.MONDAY.getDay());
                return;
            }
            tuesdayHours = tuesdayHours + tsCCView.getTuesday().getHours();
            if (tuesdayHours > 24) {
                populateTotalHoursErrorMesage(errors,
                        DateUtil.DAYS_OF_WEEK.TUESDAY.getDay());
                return;
            }
            wednesdayHours = wednesdayHours
                    + tsCCView.getWednesday().getHours();
            if (wednesdayHours > 24) {
                populateTotalHoursErrorMesage(errors,
                        DateUtil.DAYS_OF_WEEK.WEDNESDAY.getDay());
                return;
            }
            thursdayHours = thursdayHours + tsCCView.getThursday().getHours();
            if (thursdayHours > 24) {
                populateTotalHoursErrorMesage(errors,
                        DateUtil.DAYS_OF_WEEK.THURSDAY.getDay());
                return;
            }
            fridayHours = fridayHours + tsCCView.getFriday().getHours();
            if (fridayHours > 24) {
                populateTotalHoursErrorMesage(errors,
                        DateUtil.DAYS_OF_WEEK.FRIDAY.getDay());
                return;
            }
            saturdayHours = saturdayHours + tsCCView.getSaturday().getHours();

            if (saturdayHours > 24) {
                populateTotalHoursErrorMesage(errors,
                        DateUtil.DAYS_OF_WEEK.SATURDAY.getDay());
                return;
            }
            sundayHours = sundayHours + tsCCView.getSunday().getHours();

            if (sundayHours > 24) {
                populateTotalHoursErrorMesage(errors,
                        DateUtil.DAYS_OF_WEEK.SUNDAY.getDay());
                return;
            }
        }
    }

    private void populateTotalHoursErrorMesage(Errors errors, String day) {
        errors.reject(TIMESHEET_TOTALHOURS_ERROR_CODE, new Object[] { day },
                TOTAL_HOURS_IN_A_DAY_EXCEEDED);
    }

}
