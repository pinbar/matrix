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

import com.percipient.matrix.display.EmployeeView;
import com.percipient.matrix.display.GroupView;
import com.percipient.matrix.service.EmployeeService;
import com.percipient.matrix.session.UserInfo;

@Controller
@RequestMapping(value = "/admin/employee")
public class ManageEmployees {

    public static final String MODEL_ATTRIBUTE_EMPLOYEE = "employee";
    public static final String MODEL_ATTRIBUTE_EMPLOYEES = "employees";

    @Autowired
    UserInfo userInfo;

    @Autowired
    EmployeeService employeeService;

    @RequestMapping(value = "/list")
    public String getEmployeeList(Model model) {
        List<EmployeeView> employees = employeeService.getEmployees();
        model.addAttribute(MODEL_ATTRIBUTE_EMPLOYEES, employees);
        return "administrationPage";
    }

    @RequestMapping(value = "/listAsJson")
    public @ResponseBody
    List<EmployeeView> getEmployeeListAsJSON(Model model) {
        List<EmployeeView> employees = employeeService.getEmployees();
        return employees;
    }

    @RequestMapping(value = "/new")
    public String newEmployee(Model model) {
        model.addAttribute(MODEL_ATTRIBUTE_EMPLOYEE, new EmployeeView());
        return "administrationPage";
    }

    @RequestMapping(value = "/save", method = RequestMethod.POST)
    public String saveEmployee(
            @Valid @ModelAttribute(MODEL_ATTRIBUTE_EMPLOYEE) EmployeeView employeeView,
            BindingResult result, Model model) {

        if (result.hasErrors()) {
            model.addAttribute(ManageGroups.MODEL_ATTRIBUTE_GROUP,
                    new GroupView());
            model.addAttribute(Administration.MODEL_ATTRIBUTE_DEFAULT_FORM,
                    "employeeEdit");
            return "administrationPage";
        }
        employeeService.saveEmployee(employeeView);
        return "redirect:/admin/";
    }

    @RequestMapping(value = "/update")
    public @ResponseBody
    EmployeeView updateEmployee(@RequestParam("id") int employeeId, Model model) {
        EmployeeView employeeView = employeeService.getEmployee(employeeId);
        return employeeView;
    }

    @RequestMapping(value = "/delete")
    public @ResponseBody
    Object deleteEmployee(@RequestParam("id") int employeeId, Model model) {
        EmployeeView employeeView = employeeService.getEmployee(employeeId);
        employeeService.deleteEmployee(employeeView);
        return new EmployeeView();
    }
}
