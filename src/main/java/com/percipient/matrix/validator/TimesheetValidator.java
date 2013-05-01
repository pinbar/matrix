package com.percipient.matrix.validator;

import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

import com.percipient.matrix.display.TimesheetView;

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
            if (StringUtils.isBlank(tsView.getTsCostCenters().get(i)
                    .getCostCode())) {
                errors.rejectValue(null, "tsCostCenters[" + i + "].costCode",
                        "Cost code may not be blank");
                errors.rejectValue(null, "weekEnding",
                        "Something with weekending");
            }
        }
        /*
         * ValidationUtils.rejectIfEmpty(e, "name", "name.empty");
         * ValidationUtils.rejectIfEmpty(e, "authority", "authority.empty");
         */
    }
}
