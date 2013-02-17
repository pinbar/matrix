package com.percipient.matrix.dashboard;

import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.percipient.matrix.display.EmployeeView;
import com.percipient.matrix.display.GroupView;
import com.percipient.matrix.service.GroupService;

@Controller
@Scope("session")
@RequestMapping(value = "/admin/group")
public class ManageGroups {

	public static final String MODEL_ATTRIBUTE_GROUP = "group";
	public static final String MODEL_ATTRIBUTE_GROUPS = "groups";

	@Autowired
	GroupService groupService;

	@RequestMapping(value = "/list", method = RequestMethod.GET)
	public String getGroupList(Model model) {
		List<?> groups = groupService.getGroups();
		model.addAttribute(MODEL_ATTRIBUTE_GROUPS, groups);
		return "administrationPage";
	}

	@RequestMapping(value = "/listAsJson", produces = "application/json")
	@ResponseBody
	public List<GroupView> getGroups(Model model) {
		List<GroupView> groups = groupService.getGroups();
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
			model.addAttribute(ManageEmployees.MODEL_ATTRIBUTE_EMPLOYEE,
					new EmployeeView());
			model.addAttribute(Administration.MODEL_ATTRIBUTE_DEFAULT_FORM,
					"group");
			model.addAttribute(Administration.MODEL_ATTRIBUTE_DEFAULT_FORM,
					"groupEdit");
			return "administrationPage";
		}
		groupService.saveGroup(groupView);
		// return getGroupList(model); dont do it send an empty admin page
		model.addAttribute(MODEL_ATTRIBUTE_GROUP, new GroupView());
		model.addAttribute(ManageEmployees.MODEL_ATTRIBUTE_EMPLOYEE,
				new EmployeeView());
		model.addAttribute(Administration.MODEL_ATTRIBUTE_DEFAULT_FORM, "group");
		return "administrationPage";
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
}
