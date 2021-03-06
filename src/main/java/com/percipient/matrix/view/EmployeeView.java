package com.percipient.matrix.view;

import java.util.List;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;

import org.apache.commons.lang3.StringUtils;
import org.hibernate.validator.constraints.Email;
import org.hibernate.validator.constraints.NotBlank;

import com.percipient.matrix.util.DateUtil;

public class EmployeeView {

    @NotBlank
    private String userName;
    private Boolean active;
    private Integer id;
    @NotBlank
    private String firstName;
    @NotBlank
    private String lastName;
    @NotBlank
    private String startDate = DateUtil.getCurrentDate();
    private String endDate;

    @Pattern(message = "must be a 10 digit number", regexp = "(^$|[0-9]{10})")
    private String phone;
    @Email
    private String email;
    private String address;
    @NotNull
    private String groupName;
    private Integer managerId;

    private List<String> costCodes;

    private String ptosJSONStr;

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public Boolean getActive() {
        return active;
    }

    public void setActive(Boolean active) {
        this.active = active;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getStartDate() {
        return startDate;
    }

    public void setStartDate(String startDate) {
        this.startDate = startDate;
    }

    public String getEndDate() {
        return endDate;
    }

    public void setEndDate(String endDate) {
        this.endDate = endDate;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getGroupName() {
        return groupName;
    }

    public void setGroupName(String groupId) {
        this.groupName = groupId;
    }

    public List<String> getCostCodes() {
        return costCodes;
    }

    public void setCostCodes(List<String> costCodes) {
        this.costCodes = costCodes;
    }

    public String getCostCodesStr() {
        return StringUtils.join(costCodes, ",");
    }

    public void setPtosJSONStr(String ptos) {
        this.ptosJSONStr = ptos;
    }

    public String getPtosJSONStr() {
        return ptosJSONStr;
    }

    public Integer getManagerId() {
        return managerId;
    }

    public void setManagerId(Integer managerId) {
        this.managerId = managerId;
    }

    public String getName() {
        return this.firstName != null ? this.firstName : "" + " "
                + this.lastName != null ? this.lastName : "";
    }
}
