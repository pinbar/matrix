package com.percipient.matrix.view;

import java.io.InputStream;

public class TimesheetAttachmentView {

    private Integer id;
    private String fileName;
    private String contentType;
    private InputStream content;
    private Long size;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getFileName() {
        return fileName;
    }

    public void setFileName(String fileName) {
        this.fileName = fileName;
    }

    public String getContentType() {
        return contentType;
    }

    public Long getSize() {
        return size;
    }

    public void setSize(Long size) {
        this.size = size;
    }

    public void setContentType(String contentType) {
        this.contentType = contentType;
    }

    public InputStream getContent() {
        return content;
    }

    public void setContent(InputStream content) {
        this.content = content;
    }

}
