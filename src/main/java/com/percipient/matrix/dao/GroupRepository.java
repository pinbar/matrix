package com.percipient.matrix.dao;

import java.util.List;

import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.percipient.matrix.security.Group;
import com.percipient.matrix.security.GroupAuthority;

public interface GroupRepository {

    public Group getGroup(Integer groupId);

    public List<Group> getGroups();

    public void saveGroup(Group group);

    public void deleteGroup(Group group);

    public GroupAuthority getGroupAuthority(Integer groupId);
}

@Repository
class GroupRepositoryImpl implements GroupRepository {

    @Autowired
    private SessionFactory sessionFactory;

    public Group getGroup(Integer groupId) {
        return (Group) sessionFactory.getCurrentSession().get(Group.class,
                groupId);

    }

    @SuppressWarnings("unchecked")
    public List<Group> getGroups() {
        String query = "from Group";
        return (List<Group>) sessionFactory.getCurrentSession()
                .createQuery(query).list();
    }

    @Override
    public void saveGroup(Group group) {
        sessionFactory.getCurrentSession().saveOrUpdate(group);
    }

    @Override
    public void deleteGroup(Group group) {
        sessionFactory.getCurrentSession().delete(group);
    }

    @Override
    public GroupAuthority getGroupAuthority(Integer groupId) {
        String query = "from GroupAuthority as groupAuthority "
                + "where groupAuthority.groupId = :groupId";
        Object ob = sessionFactory.getCurrentSession().createQuery(query)
                .setParameter("groupId", groupId).uniqueResult();

        return ob != null ? (GroupAuthority) ob : new GroupAuthority();
    }
}
