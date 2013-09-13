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
    private Double hours;
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

    public Double getHours() {
        return hours;
    }

    public void setHours(Double hours) {
        this.hours = hours;
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
