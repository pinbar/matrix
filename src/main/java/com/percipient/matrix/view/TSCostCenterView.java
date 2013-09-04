package com.percipient.matrix.view;

import java.util.ArrayList;
import java.util.List;

import org.codehaus.jackson.annotate.JsonIgnore;

public class TSCostCenterView {

    private String costCode = "";// blank to keep the default on form
    private TimesheetItemView monday;
    private TimesheetItemView tuesday;
    private TimesheetItemView wednesday;
    private TimesheetItemView thursday;
    private TimesheetItemView friday;
    private TimesheetItemView saturday;
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

    @JsonIgnore
    public Double getTotalHours() {
        double totalHours = getMonday().getHours() + getTuesday().getHours()
                + getWednesday().getHours() + getThursday().getHours()
                + getFriday().getHours() + getSaturday().getHours()
                + getSunday().getHours();

        return totalHours;
    }
}
