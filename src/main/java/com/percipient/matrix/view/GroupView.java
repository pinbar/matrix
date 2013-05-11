package com.percipient.matrix.view;

import org.hibernate.validator.constraints.NotBlank;

public class GroupView {

    private Integer id;
    @NotBlank
    private String name;
    @NotBlank
    private String authority;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAuthority() {
        return authority;
    }

    public void setAuthority(String authority) {
        this.authority = authority;
    }
}
