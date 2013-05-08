package com.percipient.matrix.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.percipient.matrix.dao.UserRepository;
import com.percipient.matrix.security.User;
import com.percipient.matrix.view.UserView;

public interface UserCPService {

    public UserView getUser(String userName);

    public List<UserView> getUsers();

    public void saveUser(UserView userView);

    public void deleteGroup(UserView userView);

}

@Service
class UserCPServiceImpl implements UserCPService {

    @Autowired
    private UserRepository userRepository;

    @Override
    @Transactional
    public List<UserView> getUsers() {

        List<User> users = userRepository.getUsers();
        List<UserView> userViews = new ArrayList<UserView>();
        for (User user : users) {
            UserView userView = getUserViewFromUser(user);
            userViews.add(userView);
        }
        return userViews;
    }

    @Override
    @Transactional
    public void saveUser(UserView userView) {
        User user = userRepository.getUserByUserName(userView.getUserName());
        user.setPassword(userView.getNewPassword1());
        userRepository.save(user);
    }

    @Override
    @Transactional
    public UserView getUser(String userName) {
        User user = userRepository.getUserByUserName(userName);
        return getUserViewFromUser(user);
    }

    @Override
    @Transactional
    public void deleteGroup(UserView userView) {
        User user = userRepository.getUserByUserName(userView.getUserName());
        userRepository.delete(user);
    }

    private UserView getUserViewFromUser(User user) {

        UserView userView = new UserView();
        userView.setId(user.getId());
        userView.setUserName(user.getUserName());
        userView.setPassword(user.getPassword());
        return userView;
    }
}
