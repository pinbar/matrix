package com.percipient.matrix.view;

import java.util.List;

import javax.validation.Valid;

import org.apache.commons.lang3.StringUtils;
import org.springframework.web.multipart.MultipartFile;

public class TimesheetView {

    private Integer id;
    private String weekEnding;
    private String status;
    private MultipartFile attachment;
    private Double totalHours = 0.00;
    private Double regularHours = 0.00;
    private Double overTimeHours = 0.00;
    private Double ptoHours = 0.00;
    private Integer employeeId;

    @Valid
    private List<TSCostCenterView> tsCostCenters;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getEmployeeId() {
        return employeeId;
    }

    public void setEmployeeId(Integer employeeId) {
        this.employeeId = employeeId;
    }

    public String getWeekEnding() {
        return weekEnding;
    }

    public void setWeekEnding(String weekEnding) {
        this.weekEnding = weekEnding;
    }

    public String getStatus() {
        return StringUtils.capitalize(status);
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public Double getTotalHours() {
        return totalHours;
    }

    public void setTotalHours(Double hours) {
        this.totalHours = hours;
    }

    public Double getRegularHours() {
        return regularHours;
    }

    public void setRegularHours(Double regularHours) {
        this.regularHours = regularHours;
    }

    public Double getOverTimeHours() {
        return overTimeHours;
    }

    public void setOverTimeHours(Double overTimeHours) {
        this.overTimeHours = overTimeHours;
    }

    public Double getPtoHours() {
        return ptoHours;
    }

    public void setPtoHours(Double ptoHours) {
        this.ptoHours = ptoHours;
    }

    public MultipartFile getAttachment() {
        return attachment;
    }

    public void setAttachment(MultipartFile attachment) {
        this.attachment = attachment;
    }

    public List<TSCostCenterView> getTsCostCenters() {
        return tsCostCenters;
    }

    public void setTsCostCenters(List<TSCostCenterView> tsCostCenters) {
        this.tsCostCenters = tsCostCenters;
    }

}
