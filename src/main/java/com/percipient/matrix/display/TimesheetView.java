package com.percipient.matrix.display;

import java.util.List;

import javax.validation.Valid;

import org.springframework.web.multipart.MultipartFile;

public class TimesheetView {

    private Integer id;
    private String weekEnding;
    private String status;
    private MultipartFile attachment;
    private TimesheetAttachmentView attachmentInfo;

    @Valid
    private List<TSCostCenterView> tsCostCenters;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
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

    public MultipartFile getAttachment() {
        return attachment;
    }

    public void setAttachment(MultipartFile attachment) {
        this.attachment = attachment;
    }

    public TimesheetAttachmentView getAttachmentInfo() {
        return attachmentInfo;
    }

    public void setAttachmentInfo(TimesheetAttachmentView attachmentInfo) {
        this.attachmentInfo = attachmentInfo;
    }

    public List<TSCostCenterView> getTsCostCenters() {
        return tsCostCenters;
    }

    public void setTsCostCenters(List<TSCostCenterView> tsCostCenters) {
        this.tsCostCenters = tsCostCenters;
    }

}
