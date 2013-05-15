package com.percipient.matrix.view;

import org.hibernate.validator.constraints.NotBlank;

public class AdminEmpPasswordView {
    @NotBlank
    private String userName;
    @NotBlank
    private String password;
    private Boolean active;

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getUserName() {
        return userName;
    }

    public Boolean getActive() {
        return active;
    }

    public void setActive(Boolean enabled) {
        this.active = enabled;
    }

}
