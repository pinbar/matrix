package com.percipient.matrix.dashboard;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import com.percipient.matrix.display.CostCenterView;
import com.percipient.matrix.display.EmployeeView;
import com.percipient.matrix.display.GroupView;
import com.percipient.matrix.service.GroupService;

@Controller
@RequestMapping(value = "/admin")
public class Administration {

    public static final String MODEL_ATTRIBUTE_DEFAULT_FORM = "form";
    public static final String MODEL_ATTRIBUTE_GROUPS = "groups";

    @Autowired
    GroupService groupService;

    @RequestMapping(value = "/")
    public String home(Model model) {
        model.addAttribute(ManageGroups.MODEL_ATTRIBUTE_GROUP, new GroupView());
        model.addAttribute(ManageEmployees.MODEL_ATTRIBUTE_EMPLOYEE,
                new EmployeeView());
        model.addAttribute(CostCenterController.MODEL_ATTRIBUTE_COST_CENTER,
                new CostCenterView());
        model.addAttribute(MODEL_ATTRIBUTE_DEFAULT_FORM, "admin");
        return "administrationPage";
    }
}
