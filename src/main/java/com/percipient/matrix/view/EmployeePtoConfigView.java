package com.percipient.matrix.view;

import org.hibernate.validator.constraints.NotBlank;

public class EmployeePtoConfigView {

    @NotBlank
    private String costCode;
    private Double yearlyAllocatedHours = 0.00;
    private Double carryOverAllowedHours = 0.00;

    public String getCostCode() {
        return costCode;
    }

    public void setCostCode(String costCode) {
        this.costCode = costCode;
    }

    public Double getYearlyAllocatedHours() {
        return yearlyAllocatedHours;
    }

    public void setYearlyAllocatedHours(Double yearlyAllocatedHours) {
        this.yearlyAllocatedHours = yearlyAllocatedHours;
    }

    public Double getCarryOverAllowedHours() {
        return carryOverAllowedHours;
    }

    public void setCarryOverAllowedHours(Double carryOverAllowedHours) {
        this.carryOverAllowedHours = carryOverAllowedHours;
    }

}
