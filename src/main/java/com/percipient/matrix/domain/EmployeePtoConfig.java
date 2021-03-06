package com.percipient.matrix.domain;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "employees_pto_config")
public class EmployeePtoConfig implements Serializable {

    private static final long serialVersionUID = 745407141548950900L;

    @Id
    @Column(name = "employee_id")
    private Integer employeeId;

    @Id
    @Column(name = "cost_code")
    private String costCode;

    @Column(name = "yearly_allocated_hours")
    private Double yearlyAllocatedHours;

    @Column(name = "carry_over_allowed_hours")
    private Double carryOverAllowedHours;

    public Integer getEmployeeId() {
        return employeeId;
    }

    public void setEmployeeId(Integer employeeId) {
        this.employeeId = employeeId;
    }

    public String getCostCode() {
        return costCode;
    }

    public void setCostCode(String costCode) {
        this.costCode = costCode;
    }

    public Double getYearlyAllocatedHours() {
        return yearlyAllocatedHours;
    }

    public void setYearlyAllocatedHours(Double yearlyAllocatedHours) {
        this.yearlyAllocatedHours = yearlyAllocatedHours;
    }

    public Double getCarryOverAllowedHours() {
        return carryOverAllowedHours;
    }

    public void setCarryOverAllowedHours(Double carryOverAllowedHours) {
        this.carryOverAllowedHours = carryOverAllowedHours;
    }

}
