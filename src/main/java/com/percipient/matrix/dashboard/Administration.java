package com.percipient.matrix.dashboard;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import com.percipient.matrix.display.EmployeeView;
import com.percipient.matrix.display.GroupView;

@Controller
@RequestMapping(value = "/admin")
public class Administration {
	
	public static final String MODEL_ATTRIBUTE_DEFAULT_FORM = "form";

	@RequestMapping(value = "/")
	public String home(Model model) {
		model.addAttribute(ManageGroups.MODEL_ATTRIBUTE_GROUP, new GroupView());
		model.addAttribute(ManageEmployees.MODEL_ATTRIBUTE_EMPLOYEE,new EmployeeView());
		model.addAttribute(MODEL_ATTRIBUTE_DEFAULT_FORM,"admin");
		return "administrationPage";
	}
}
