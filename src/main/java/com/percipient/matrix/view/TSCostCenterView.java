package com.percipient.matrix.view;

import java.util.ArrayList;
import java.util.List;

import javax.validation.Valid;

import org.codehaus.jackson.annotate.JsonIgnore;
import org.hibernate.validator.constraints.NotBlank;

public class TSCostCenterView {

    @NotBlank
    private String costCode = "";// blank to keep the default on form
    @Valid
    private TimesheetItemView monday;
    @Valid
    private TimesheetItemView tuesday;
    @Valid
    private TimesheetItemView wednesday;
    @Valid
    private TimesheetItemView thursday;
    @Valid
    private TimesheetItemView friday;
    @Valid
    private TimesheetItemView saturday;
    @Valid
    private TimesheetItemView sunday;

    public String getCostCode() {
        return costCode;
    }

    public void setCostCode(String costCode) {
        this.costCode = costCode;
    }

    public TimesheetItemView getMonday() {
        return monday;
    }

    public void setMonday(TimesheetItemView monday) {
        this.monday = monday;
    }

    public TimesheetItemView getTuesday() {
        return tuesday;
    }

    public void setTuesday(TimesheetItemView tuesday) {
        this.tuesday = tuesday;
    }

    public TimesheetItemView getWednesday() {
        return wednesday;
    }

    public void setWednesday(TimesheetItemView wednesday) {
        this.wednesday = wednesday;
    }

    public TimesheetItemView getThursday() {
        return thursday;
    }

    public void setThursday(TimesheetItemView thursday) {
        this.thursday = thursday;
    }

    public TimesheetItemView getFriday() {
        return friday;
    }

    public void setFriday(TimesheetItemView friday) {
        this.friday = friday;
    }

    public TimesheetItemView getSaturday() {
        return saturday;
    }

    public void setSaturday(TimesheetItemView saturday) {
        this.saturday = saturday;
    }

    public TimesheetItemView getSunday() {
        return sunday;
    }

    public void setSunday(TimesheetItemView sunday) {
        this.sunday = sunday;
    }

    @JsonIgnore
    public List<TimesheetItemView> getTimesheetItems() {
        List<TimesheetItemView> tsItems = new ArrayList<TimesheetItemView>();
        tsItems.add(getMonday());
        tsItems.add(getTuesday());
        tsItems.add(getWednesday());
        tsItems.add(getThursday());
        tsItems.add(getFriday());
        tsItems.add(getSaturday());
        tsItems.add(getSunday());
        return tsItems;
    }

}
