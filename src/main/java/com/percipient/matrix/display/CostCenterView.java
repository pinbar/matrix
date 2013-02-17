package com.percipient.matrix.display;

import org.hibernate.validator.constraints.NotBlank;

public class CostCenterView {

	@NotBlank
	private String costCode;

	@NotBlank
	private String name;

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

}
