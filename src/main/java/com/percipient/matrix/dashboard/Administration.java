package com.percipient.matrix.dashboard;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import com.percipient.matrix.display.CostCenterView;
import com.percipient.matrix.display.EmployeeView;
import com.percipient.matrix.display.GroupView;

@Controller
@RequestMapping(value = "/admin")
public class Administration {

    public static final String MODEL_ATTRIBUTE_DEFAULT_FORM = "form";

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

    @RequestMapping(value = "/groupEdit")
    public String groupEdit(Model model) {
        model.addAttribute(CostCenterController.MODEL_ATTRIBUTE_COST_CENTER,
                new CostCenterView());
        model.addAttribute(ManageEmployees.MODEL_ATTRIBUTE_EMPLOYEE,
                new CostCenterView());
        model.addAttribute(MODEL_ATTRIBUTE_DEFAULT_FORM, "groupEdit");
        return "administrationPage";
    }

    @RequestMapping(value = "/groupList")
    public String groupList(Model model) {
        model.addAttribute(ManageGroups.MODEL_ATTRIBUTE_GROUP, new GroupView());
        model.addAttribute(ManageEmployees.MODEL_ATTRIBUTE_EMPLOYEE,
                new EmployeeView());
        model.addAttribute(CostCenterController.MODEL_ATTRIBUTE_COST_CENTER,
                new CostCenterView());
        model.addAttribute(MODEL_ATTRIBUTE_DEFAULT_FORM, "groupList");
        return "administrationPage";
    }

    @RequestMapping(value = "/employeeEdit")
    public String employeeEdit(Model model) {
        model.addAttribute(ManageGroups.MODEL_ATTRIBUTE_GROUP, new GroupView());
        model.addAttribute(CostCenterController.MODEL_ATTRIBUTE_COST_CENTER,
                new CostCenterView());
        model.addAttribute(MODEL_ATTRIBUTE_DEFAULT_FORM, "employeeEdit");
        return "administrationPage";
    }

    @RequestMapping(value = "/employeeList")
    public String employeeList(Model model) {
        model.addAttribute(ManageGroups.MODEL_ATTRIBUTE_GROUP, new GroupView());
        model.addAttribute(ManageEmployees.MODEL_ATTRIBUTE_EMPLOYEE,
                new EmployeeView());
        model.addAttribute(CostCenterController.MODEL_ATTRIBUTE_COST_CENTER,
                new CostCenterView());
        model.addAttribute(MODEL_ATTRIBUTE_DEFAULT_FORM, "employeeList");
        return "administrationPage";
    }

    @RequestMapping(value = "/costCenterEdit")
    public String costCenterEdit(Model model) {
        model.addAttribute(ManageGroups.MODEL_ATTRIBUTE_GROUP, new GroupView());
        model.addAttribute(ManageEmployees.MODEL_ATTRIBUTE_EMPLOYEE,
                new EmployeeView());
        model.addAttribute(MODEL_ATTRIBUTE_DEFAULT_FORM, "costCenterEdit");
        return "administrationPage";
    }

    @RequestMapping(value = "/costCenterList")
    public String costCenterList(Model model) {
        model.addAttribute(ManageGroups.MODEL_ATTRIBUTE_GROUP, new GroupView());
        model.addAttribute(ManageEmployees.MODEL_ATTRIBUTE_EMPLOYEE,
                new EmployeeView());
        model.addAttribute(CostCenterController.MODEL_ATTRIBUTE_COST_CENTER,
                new CostCenterView());
        model.addAttribute(MODEL_ATTRIBUTE_DEFAULT_FORM, "costCenterList");
        return "administrationPage";
    }

}
