package com.percipient.matrix.view;

public class HrTimesheetView {

    private Integer timesheetId;
    private String weekEnding;
    private String status;
    private String employeeName;
    private Double hours;

    public Integer getTimesheetId() {
        return timesheetId;
    }

    public void setTimesheetId(Integer timesheetId) {
        this.timesheetId = timesheetId;
    }

    public String getWeekEnding() {
        return weekEnding;
    }

    public void setWeekEnding(String weekEnding) {
        this.weekEnding = weekEnding;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getEmployeeName() {
        return employeeName;
    }

    public void setEmployeeName(String employeeName) {
        this.employeeName = employeeName;
    }

    public Double getHours() {
        return hours;
    }

    public void setHours(Double hours) {
        this.hours = hours;
    }

}
