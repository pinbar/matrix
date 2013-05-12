package com.percipient.matrix.controller;

import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.percipient.matrix.service.CostCenterService;
import com.percipient.matrix.view.AdminEmpPasswordView;
import com.percipient.matrix.view.CostCenterView;
import com.percipient.matrix.view.EmployeeView;
import com.percipient.matrix.view.GroupView;

@Controller
@RequestMapping(value = "/admin/costCenter")
public class CostCenterController {

    public static final String MODEL_ATTRIBUTE_COST_CENTER = "costCenter";
    public static final String MODEL_ATTRIBUTE_COST_CENTERS = "costCenters";

    @Autowired
    CostCenterService costCenterService;

    @RequestMapping(value = "/listAsJson", produces = "application/json")
    @ResponseBody
    public List<CostCenterView> getGroups(Model model) {
        List<CostCenterView> costCenters = costCenterService.getCostCenters();
        return costCenters;

    }

    @RequestMapping(value = "/save", method = RequestMethod.POST)
    public String saveGroup(
            @Valid @ModelAttribute(MODEL_ATTRIBUTE_COST_CENTER) CostCenterView costCenterView,
            BindingResult result, Model model) {

        if (result.hasErrors()) {
            return gotoCostCenterEdit(model);
        }
        costCenterService.saveCostCenter(costCenterView);
        return gotoCostCenterList(model);
    }

    @RequestMapping(value = "/update", method = RequestMethod.GET, produces = "application/json")
    @ResponseBody
    public CostCenterView updateCostCenter(
            @RequestParam("id") Integer costCenterId, Model model) {
        CostCenterView costCenterView = costCenterService
                .getCostCenter(costCenterId);
        return costCenterView;
    }

    @RequestMapping(value = "/delete")
    public @ResponseBody
    Object deleteGroup(@RequestParam("id") Integer costCenterId, Model model) {
        CostCenterView costCenterView = costCenterService
                .getCostCenter(costCenterId);
        costCenterService.deleteGroup(costCenterView);
        return new CostCenterView();
    }

    private String gotoCostCenterEdit(Model model) {
        model.addAttribute(GroupController.MODEL_ATTRIBUTE_GROUP, new GroupView());
        model.addAttribute(EmployeeController.MODEL_ATTRIBUTE_EMPLOYEE,
                new EmployeeView());
        model.addAttribute(EmployeeController.MODEL_ATTRIBUTE_CHANGE_PASS,
				new AdminEmpPasswordView());
	    model.addAttribute(
                AdministrationController.MODEL_ATTRIBUTE_DEFAULT_FORM,
                "costCenterEdit");
        return "administrationPage";
    }

    private String gotoCostCenterList(Model model) {
        model.addAttribute(GroupController.MODEL_ATTRIBUTE_GROUP, new GroupView());
        model.addAttribute(EmployeeController.MODEL_ATTRIBUTE_EMPLOYEE,
                new EmployeeView());
        model.addAttribute(CostCenterController.MODEL_ATTRIBUTE_COST_CENTER,
                new CostCenterView());
        model.addAttribute(EmployeeController.MODEL_ATTRIBUTE_CHANGE_PASS,
				new AdminEmpPasswordView());
	    model.addAttribute(
                AdministrationController.MODEL_ATTRIBUTE_DEFAULT_FORM,
                "costCenterList");
        return "administrationPage";
    }
}
