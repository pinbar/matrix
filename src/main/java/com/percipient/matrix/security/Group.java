package com.percipient.matrix.security;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.Table;

@Entity
@Table(name = "groups")
public class Group {

	@Id
	@Column(name = "id")
	private Integer id;

	@Column(name = "group_name")
	private String name;

	@OneToOne(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
	@JoinColumn(name = "id", referencedColumnName = "group_id", insertable = false, updatable = false)
	private GroupAuthority groupAuthority;

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

	public GroupAuthority getGroupAuthority() {
		return groupAuthority;
	}

	public void setGroupAuthority(GroupAuthority groupAuthority) {
		this.groupAuthority = groupAuthority;
	}

}
