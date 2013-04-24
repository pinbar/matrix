package com.percipient.matrix.security;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToOne;
import javax.persistence.Table;

@Entity
@Table(name = "groups")
public class Group {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Integer id;

    @Column(name = "group_name")
    private String name;

    @OneToOne(mappedBy = "group", cascade = CascadeType.ALL)
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
