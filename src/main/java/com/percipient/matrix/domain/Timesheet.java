package com.percipient.matrix.domain;

import java.util.Date;
import java.util.HashSet;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.OneToMany;
import javax.persistence.Table;

@Entity
@Table(name = "timesheets")
public class Timesheet {

    @Id
    @GeneratedValue
    @Column(name = "id")
    private Integer id;

    @Column(name = "week_ending")
    private Date weekEnding;

    @Column(name = "status")
    private String status;

    @Column(name = "employee_id")
    private Integer employeeId;

    @OneToMany(cascade = { CascadeType.ALL }, orphanRemoval = true)
    @JoinTable(name = "timesheets_timesheet_items", joinColumns = { @JoinColumn(name = "timesheet_id") }, inverseJoinColumns = { @JoinColumn(name = "timesheet_item_id") })
    private Set<TimesheetItem> timesheetItems = new HashSet<TimesheetItem>();

    public Set<TimesheetItem> getTimesheetItems() {
        return timesheetItems;
    }

    public void setTimesheetItems(Set<TimesheetItem> timesheetItems) {
        this.timesheetItems = timesheetItems;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Date getWeekEnding() {
        return weekEnding;
    }

    public void setWeekEnding(Date weekEnding) {
        this.weekEnding = weekEnding;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public Integer getEmployeeId() {
        return employeeId;
    }

    public void setEmployeeId(Integer employeeId) {
        this.employeeId = employeeId;
    }

}
