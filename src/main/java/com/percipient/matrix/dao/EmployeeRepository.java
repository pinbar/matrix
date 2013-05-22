package com.percipient.matrix.dao;

import java.util.List;

import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.percipient.matrix.domain.Employee;
import com.percipient.matrix.security.GroupMember;

public interface EmployeeRepository {

    public Employee getEmployeeByUserName(String userName);

    public Employee getEmployee(Integer employeeId);

    public List<Employee> getEmployees();

    public Integer saveEmployee(Employee employee);

    public void deleteEmployee(Employee employee);

    public GroupMember getGroupMemberByUserName(String userName);

    public void saveGroupMember(GroupMember groupMember);

    public void deleteGroupMember(GroupMember groupMember);
}

@Repository
class EmployeeRepositoryImpl implements EmployeeRepository {

    @Autowired
    private SessionFactory sessionFactory;

    @Override
    public Employee getEmployeeByUserName(String userName) {
        String query = "from Employee as employee where employee.userName = :userName";
        return (Employee) sessionFactory.getCurrentSession().createQuery(query)
                .setParameter("userName", userName).uniqueResult();
    }

    @Override
    public Employee getEmployee(Integer employeeId) {
        return (Employee) sessionFactory.getCurrentSession().get(
                Employee.class, employeeId);
    }

    @SuppressWarnings("unchecked")
    @Override
    public List<Employee> getEmployees() {
        String query = "from Employee as employee";
        return (List<Employee>) sessionFactory.getCurrentSession()
                .createQuery(query).list();
    }

    @Override
    public Integer saveEmployee(Employee employee) {
       Integer id =null; 
        if (employee.getId() != null) {
            sessionFactory.getCurrentSession().saveOrUpdate(employee);
        }else {
           id =(Integer)sessionFactory.getCurrentSession().save(employee);
        }
        return id; 
    }

    @Override
    public void deleteEmployee(Employee employee) {
        sessionFactory.getCurrentSession().delete(employee);
    }

    @Override
    public GroupMember getGroupMemberByUserName(String userName) {
        String query = "from GroupMember as group_member where group_member.userName = :userName";
        Object ob = sessionFactory.getCurrentSession().createQuery(query)
                .setParameter("userName", userName).uniqueResult();
        return ob != null ? (GroupMember) ob : new GroupMember();
    }

    @Override
    public void saveGroupMember(GroupMember groupMember) {
        sessionFactory.getCurrentSession().saveOrUpdate(groupMember);
    }

    @Override
    public void deleteGroupMember(GroupMember groupMember) {
        sessionFactory.getCurrentSession().delete(groupMember);
    }

}
