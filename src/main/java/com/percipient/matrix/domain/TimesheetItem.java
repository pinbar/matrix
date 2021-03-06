package com.percipient.matrix.domain;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@Entity
@Table(name = "timesheet_items")
public class TimesheetItem {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Integer id;

    @Column(name = "item_date")
    private Date date;

    @Column(name = "cost_code")
    private String costCode;

    @Column(name = "hours")
    private Double hours;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "timesheet_id", nullable = false)
    private Timesheet timesheet;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public String getCostCode() {
        return costCode;
    }

    public void setCostCode(String costCode) {
        this.costCode = costCode;
    }

    public Double getHours() {
        return hours;
    }

    public void setHours(Double hours) {
        this.hours = hours;
    }

    public Timesheet getTimesheet() {
        return timesheet;
    }

    public void setTimesheet(Timesheet timesheet) {
        this.timesheet = timesheet;
    }

}
