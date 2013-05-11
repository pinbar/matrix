package com.percipient.matrix.view;

import javax.validation.constraints.NotNull;

import org.hibernate.validator.constraints.NotBlank;

public class ChangePasswordView {

    @NotNull
    private Integer userId;

    @NotBlank
    private String userName;

    @NotBlank
    private String password;

    @NotBlank
    private String newPassword1;

    @NotBlank
    private String newPassword2;

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getNewPassword1() {
        return newPassword1;
    }

    public void setNewPassword1(String newPassword1) {
        this.newPassword1 = newPassword1;
    }

    public String getNewPassword2() {
        return newPassword2;
    }

    public void setNewPassword2(String newPassword2) {
        this.newPassword2 = newPassword2;
    }

}