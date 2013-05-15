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
    private String newPassword;

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

    public String getNewPassword() {
        return newPassword;
    }

    public void setNewPassword(String newPassword) {
        this.newPassword = newPassword;
    }

    public String getNewPassword2() {
        return newPassword2;
    }

    public void setNewPassword2(String newPassword2) {
        this.newPassword2 = newPassword2;
    }

}
