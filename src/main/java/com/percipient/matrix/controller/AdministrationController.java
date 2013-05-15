package com.percipient.matrix.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import com.percipient.matrix.service.GroupService;
import com.percipient.matrix.view.AdminEmpPasswordView;
import com.percipient.matrix.view.ClientView;
import com.percipient.matrix.view.CostCenterView;
import com.percipient.matrix.view.EmployeeView;
import com.percipient.matrix.view.GroupView;

@Controller
@RequestMapping(value = "/admin")
public class AdministrationController {

    public static final String MODEL_ATTRIBUTE_DEFAULT_FORM = "form";
    public static final String MODEL_ATTRIBUTE_GROUPS = "groups";

    @Autowired
    GroupService groupService;

    @RequestMapping(value = "/")
    public String home(Model model) {
        model.addAttribute(GroupController.MODEL_ATTRIBUTE_GROUP,
                new GroupView());
        model.addAttribute(EmployeeController.MODEL_ATTRIBUTE_EMPLOYEE,
                new EmployeeView());
        model.addAttribute(CostCenterController.MODEL_ATTRIBUTE_COST_CENTER,
                new CostCenterView());
        model.addAttribute(ClientController.MODEL_ATTRIBUTE_CLIENT,
                new ClientView());
        model.addAttribute(MODEL_ATTRIBUTE_DEFAULT_FORM, "admin");
        model.addAttribute(EmployeeController.MODEL_ATTRIBUTE_CHANGE_PASS,
                new AdminEmpPasswordView());
        return "administrationPage";
    }
}
