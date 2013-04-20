package com.percipient.matrix.domain;

import java.util.HashSet;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import com.percipient.matrix.security.GroupMember;
import com.percipient.matrix.security.User;

@Entity
@Table(name = "employees")
public class Employee {

    @Id
    @Column(name = "id")
    private Integer id;

    @Column(name = "first_name")
    private String firstName;

    @Column(name = "last_name")
    private String lastName;

    @Column(name = "username")
    private String userName;

    @OneToOne(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    @JoinColumn(name = "username", insertable = false, updatable = false)
    private User user;

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    @OneToOne(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    @JoinColumn(name = "username", referencedColumnName = "username", insertable = false, updatable = false)
    private GroupMember groupMember;

    public GroupMember getGroupMember() {
        return groupMember;
    }

    public void setGroupMember(GroupMember groupMember) {
        this.groupMember = groupMember;
    }

    @ManyToMany(cascade = { CascadeType.ALL })
    @JoinTable(name = "employees_clients", joinColumns = { @JoinColumn(name = "employee_id") }, inverseJoinColumns = { @JoinColumn(name = "client_id") })
    private Set<Client> clients = new HashSet<Client>();

    public Set<Client> getClients() {
        return clients;
    }

    public void setClients(Set<Client> clients) {
        this.clients = clients;
    }

    /*
     * @OneToMany(cascade = {CascadeType.ALL})
     * 
     * @JoinTable(name="employees_timesheets",
     * joinColumns={@JoinColumn(name="employee_id")},
     * inverseJoinColumns={@JoinColumn(name="timesheet_id")}) private
     * Set<Timesheet> timesheets = new HashSet<Timesheet>();
     * 
     * public Set<Timesheet> getTimesheets() { return timesheets; }
     * 
     * public void setTimesheets(Set<Timesheet> timesheets) { this.timesheets =
     * timesheets; }
     */
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

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

}
