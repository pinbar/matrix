package com.percipient.matrix.dashboard;

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

import com.percipient.matrix.display.CostCenterView;
import com.percipient.matrix.display.EmployeeView;
import com.percipient.matrix.display.GroupView;
import com.percipient.matrix.service.GroupService;

@Controller
@RequestMapping(value = "/admin/group")
public class ManageGroups {

    public static final String MODEL_ATTRIBUTE_GROUP = "group";
    public static final String MODEL_ATTRIBUTE_GROUPS = "groups";

    @Autowired
    GroupService groupService;

    @RequestMapping(value = "/listAsJson", produces = "application/json")
    @ResponseBody
    public List<GroupView> getGroups(Model model) {
        List<GroupView> groups = groupService.getGroups();
        model.addAttribute(MODEL_ATTRIBUTE_GROUPS, groups);
        return groups;

    }

    @RequestMapping(value = "/new")
    public String newGroup(Model model) {
        model.addAttribute(MODEL_ATTRIBUTE_GROUP, new GroupView());
        return "administrationPage";
    }

    @RequestMapping(value = "/save", method = RequestMethod.POST)
    public String saveGroup(
            @Valid @ModelAttribute(MODEL_ATTRIBUTE_GROUP) GroupView groupView,
            BindingResult result, Model model) {

        if (result.hasErrors()) {            
            return gotoGroupEdit(model);
        }
        groupService.saveGroup(groupView);
        return gotoGroupList(model);
    }

    @RequestMapping(value = "/update", method = RequestMethod.GET, produces = "application/json")
    @ResponseBody
    public GroupView updateGroup(@RequestParam("id") int groupId, Model model) {
        GroupView groupView = groupService.getGroup(groupId);
        return groupView;
    }

    @RequestMapping(value = "/delete")
    public @ResponseBody
    Object deleteGroup(@RequestParam("id") int groupId, Model model) {
        GroupView groupView = groupService.getGroup(groupId);
        groupService.deleteGroup(groupView);
        return new GroupView();
    }

    public String gotoGroupEdit(Model model) {
        model.addAttribute(CostCenterController.MODEL_ATTRIBUTE_COST_CENTER,
                new CostCenterView());
        model.addAttribute(ManageEmployees.MODEL_ATTRIBUTE_EMPLOYEE,
                new EmployeeView());
        model.addAttribute(Administration.MODEL_ATTRIBUTE_DEFAULT_FORM, "groupEdit");
        return "administrationPage";
    }

    public String gotoGroupList(Model model) {
        model.addAttribute(ManageGroups.MODEL_ATTRIBUTE_GROUP, new GroupView());
        model.addAttribute(CostCenterController.MODEL_ATTRIBUTE_COST_CENTER,
                new CostCenterView());
        model.addAttribute(ManageEmployees.MODEL_ATTRIBUTE_EMPLOYEE,
                new EmployeeView());
        model.addAttribute(Administration.MODEL_ATTRIBUTE_DEFAULT_FORM, "groupList");
        return "administrationPage";
    }
}
