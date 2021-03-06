package com.percipient.matrix.view;

import org.hibernate.validator.constraints.NotBlank;

public class CostCenterView {

    private Integer id;
    @NotBlank
    private String costCode;
    @NotBlank
    private String name;

    private Boolean pto = false;

    @NotBlank
    private String clientName;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getCostCode() {
        return costCode;
    }

    public void setCostCode(String costCode) {
        this.costCode = costCode;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Boolean getPto() {
        return pto;
    }

    public void setPto(Boolean pto) {
        this.pto = pto;
    }

    public String getClientName() {
        return clientName;
    }

    public void setClientName(String clientName) {
        this.clientName = clientName;
    }

}
