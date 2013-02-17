package com.percipient.matrix.domain;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "timesheet_items")
public class TimesheetItem {

	@Id
	@GeneratedValue
	@Column(name = "id")
	private Integer id;

	@Column(name = "item_date")
	private Date date;
	
	@Column(name = "cost_code")
	private String costCode;

	@Column(name = "hours")
	private Double hours;

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

}
