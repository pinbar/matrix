package com.percipient.matrix.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.percipient.matrix.dao.EmployeeRepository;
import com.percipient.matrix.dao.UserRepository;
import com.percipient.matrix.domain.Employee;
import com.percipient.matrix.security.User;
import com.percipient.matrix.view.ChangePasswordView;
import com.percipient.matrix.view.EmployeeContactInfoView;

public interface UserCPService {

    public ChangePasswordView getChangePasswordView(String userName);

    public void saveUser(ChangePasswordView changePassView);

    public EmployeeContactInfoView employeeContactInfoView(String userName);

    public void saveEmployee(EmployeeContactInfoView employeeContactInfoView);

}

@Service
class UserCPServiceImpl implements UserCPService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private EmployeeRepository employeeResporitory;

    @Override
    @Transactional
    public ChangePasswordView getChangePasswordView(String userName) {

        User user = userRepository.getUserByUserName(userName);
        ChangePasswordView changePassView = new ChangePasswordView();
        changePassView.setUserId(user.getId());
        changePassView.setUserName(user.getUserName());
        changePassView.setPassword(user.getPassword());
        return changePassView;
    }

    @Override
    @Transactional
    public void saveUser(ChangePasswordView changePassView) {

        User user = userRepository.getUserByUserName(changePassView
                .getUserName());
        user.setPassword(changePassView.getNewPassword1());
        userRepository.save(user);
    }

    @Override
    @Transactional
    public EmployeeContactInfoView employeeContactInfoView(String userName) {

        Employee employee = employeeResporitory.getEmployeeByUserName(userName);
        EmployeeContactInfoView empContactInfoView = new EmployeeContactInfoView();
        empContactInfoView.setEmployeeId(employee.getId());
        empContactInfoView.setPhone(employee.getPhone());
        empContactInfoView.setEmail(employee.getEmail());
        empContactInfoView.setAddress(employee.getAddress());

        return empContactInfoView;
    }

    @Override
    @Transactional
    public void saveEmployee(EmployeeContactInfoView employeeContactInfoView) {

        Employee employee = employeeResporitory
                .getEmployee(employeeContactInfoView.getEmployeeId());
        employee.setPhone(employeeContactInfoView.getPhone());
        employee.setEmail(employeeContactInfoView.getEmail());
        employee.setAddress(employeeContactInfoView.getAddress());
    }

}
