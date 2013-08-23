package com.percipient.matrix.view;

import javax.validation.constraints.Max;
import javax.validation.constraints.NotNull;

public class TimesheetItemView {

    private Integer id;

    @Max(24)
    @NotNull
    private Double hours;
    private String date;
    private String costCode;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Double getHours() {
        return hours;
    }

    public void setHours(Double hours) {
        this.hours = hours;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getCostCode() {
        return costCode;
    }

    public void setCostCode(String costCode) {
        this.costCode = costCode;
    }

}
