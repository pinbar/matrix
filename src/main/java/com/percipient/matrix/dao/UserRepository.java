package com.percipient.matrix.dao;

import java.util.List;

import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.percipient.matrix.security.User;

public interface UserRepository {

    public User getUserByUserName(String userName);

    public User getUser(Integer userId);

    public List<User> getUsers();

    public void save(User user);

    public void delete(User user);

}

@Repository
class UserRepositoryImpl implements UserRepository {

    @Autowired
    private SessionFactory sessionFactory;

    @Override
    public User getUserByUserName(String userName) {
        String query = "from User as user where user.userName = :userName";
        return (User) sessionFactory.getCurrentSession().createQuery(query)
                .setParameter("userName", userName).uniqueResult();
    }

    @Override
    public User getUser(Integer userId) {
        return (User) sessionFactory.getCurrentSession()
                .get(User.class, userId);
    }

    @SuppressWarnings("unchecked")
    @Override
    public List<User> getUsers() {
        String query = "from User";
        return (List<User>) sessionFactory.getCurrentSession()
                .createQuery(query).list();
    }

    @Override
    public void save(User user) {
        sessionFactory.getCurrentSession().saveOrUpdate(user);
    }

    @Override
    public void delete(User user) {
        sessionFactory.getCurrentSession().delete(user);
    }
}
