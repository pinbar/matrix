package com.percipient.matrix.session;

import java.security.Principal;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.percipient.matrix.service.EmployeeService;
import com.percipient.matrix.session.UserInfo;

@Controller
@Scope("session")
public class Login {

	@Autowired
	UserInfo userInfo;
	@Autowired
	EmployeeService employeeService;

	@RequestMapping(value = "/start")
	public String setUser(Principal principal) {

		userInfo.setUserName(principal.getName());
		employeeService.setUserInfo(userInfo);

		return "home";
	}

	@RequestMapping(value = "/")
	public String home() {

		return "landingPage";
	}
}
