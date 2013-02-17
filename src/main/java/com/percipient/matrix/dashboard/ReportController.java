package com.percipient.matrix.dashboard;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.percipient.matrix.service.ClientService;
import com.percipient.matrix.service.EmployeeService;

@Controller
@RequestMapping(value = "/report")
public class ReportController {

	public static final String MODEL_ATTRIBUTE_CLIENTS = "clients";
	public static final String MODEL_ATTRIBUTE_EMPLOYEES = "employees";

	@Autowired
	EmployeeService employeeService;

	@Autowired
	ClientService clientService;

	@RequestMapping(value = "/", method = RequestMethod.GET)
	public String go() {
		return "reportPage";
	}

	@RequestMapping(value = "/client/list", method = RequestMethod.GET)
	public String getGroupList(Model model) {
		List<?> clients = clientService.getClients();
		model.addAttribute(MODEL_ATTRIBUTE_CLIENTS, clients);
		return "reportPage";
	}

	@RequestMapping(value = "/employee/list", method = RequestMethod.GET)
	public String getEmployeeList(Model model) {
		List<?> employees = employeeService.getEmployees();
		model.addAttribute(MODEL_ATTRIBUTE_EMPLOYEES, employees);
		return "reportPage";
	}
}
