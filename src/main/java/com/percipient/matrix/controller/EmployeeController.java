package com.percipient.matrix.controller;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.percipient.matrix.service.EmployeeCostCenterService;
import com.percipient.matrix.service.EmployeePtoConfigService;
import com.percipient.matrix.service.EmployeeService;
import com.percipient.matrix.service.GroupService;
import com.percipient.matrix.service.UserCPService;
import com.percipient.matrix.view.AdminEmpPasswordView;
import com.percipient.matrix.view.ClientView;
import com.percipient.matrix.view.CostCenterView;
import com.percipient.matrix.view.EmployeeCostCenterView;
import com.percipient.matrix.view.EmployeePtoConfigView;
import com.percipient.matrix.view.EmployeeView;
import com.percipient.matrix.view.GroupView;

@Controller
@RequestMapping(value = "/admin/employee")
public class EmployeeController {

    public static final String MODEL_ATTRIBUTE_EMPLOYEE = "employee";
    public static final String MODEL_ATTRIBUTE_EMPLOYEES = "employees";
    public static final String MODEL_ATTRIBUTE_GROUPS = "groups";
    public static final String MODEL_ATTRIBUTE_CHANGE_PASS = "changePass";
    public static final String MODEL_ATTRIBUTE_COST_CENTER_GROUPS = "costCenters";

    @Autowired
    EmployeeService employeeService;

    @Autowired
    UserCPService userCPService;

    @Autowired
    GroupService groupService;

    @Autowired
    EmployeeCostCenterService employeeCostCenterService;

    @Autowired
    EmployeePtoConfigService employeePtoConfigService;

    @RequestMapping(value = "/listAsJson")
    public @ResponseBody
    List<EmployeeView> getEmployeeListAsJSON(Model model) {
        List<EmployeeView> employees = employeeService.getEmployees();
        return employees;
    }

    @RequestMapping(value = "{group}/listAsJson")
    public @ResponseBody
    List<EmployeeView> getEmployeeWithRolesListAsJSON(Model model,
            @PathVariable("group") String group) {
        if (StringUtils.isBlank(group)) {
            return new ArrayList<EmployeeView>();
        }
        List<EmployeeView> employees = employeeService
                .getEmployeesByGroup(group);
        return employees;
    }

    @RequestMapping(value = "/new")
    public String newEmployee(Model model) {
        model.addAttribute(MODEL_ATTRIBUTE_EMPLOYEE, new EmployeeView());
        return AdministrationController.ADMIN_PAGE;
    }

    @RequestMapping(value = "/save", method = RequestMethod.POST)
    public String saveEmployee(
            @Valid @ModelAttribute(MODEL_ATTRIBUTE_EMPLOYEE) EmployeeView employeeView,
            BindingResult result, Model model, HttpServletRequest request) {
        String costCodeStr = request.getParameter("costCodes");
        String ptos = request.getParameter("ptos") != null ? request
                .getParameter("ptos") : null;

        String[] costCodes = StringUtils.isNotBlank(costCodeStr) ? costCodeStr.split(",")
                : null;
        if (null != costCodes && costCodes.length > 0) {
            employeeView.setCostCodes(Arrays.asList(costCodes));
        }

        // TODO validation on PTOs
        if (StringUtils.isNotBlank(ptos)) {
            employeeView.setPtosJSONStr(ptos);
        }
        if (result.hasErrors()) {
            return gotoEmployeeEdit(model);
        }
        employeeService.saveEmployee(employeeView);
        return gotoEmployeeList(model);
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

    @RequestMapping(value = "/resetpassword")
    public String resetEmployeePassword(
            @Valid @ModelAttribute(MODEL_ATTRIBUTE_CHANGE_PASS) AdminEmpPasswordView adminEmpPasswordView,
            BindingResult result, Model model) {

        if (result.hasErrors()) {
            return gotoAdminResetPassword(model);
        }
        userCPService.resetUserCredentials(adminEmpPasswordView);
        return gotoEmployeeList(model);
    }

    @RequestMapping(value = "/costCenters/listAsJson")
    public @ResponseBody
    List<String> getAllCostCodesForEmp(
            @RequestParam("employeeId") Integer employeeId, Model model) {
        List<String> empCostCodeList = new ArrayList<String>();
        if (null != employeeId) {
            empCostCodeList = employeeCostCenterService
                    .getCostCodesForEmployee(employeeId);
        }
        return empCostCodeList;
    }

    @RequestMapping(value = "/costCenters/save", method = RequestMethod.POST)
    public @ResponseBody
    void saveCostCodessForEmp(@RequestParam("employeeId") int employeeId,
            @RequestBody List<String> costCodeList) {
        employeeCostCenterService.saveCostCodesForEmployee(employeeId,
                costCodeList);
    }

    @RequestMapping(value = "/costCenters/delete", method = RequestMethod.POST)
    public @ResponseBody
    List<EmployeeCostCenterView> deleteCostCentersForEmp(
            @RequestParam("employeeId") int employeeId,
            @RequestBody List<String> empCostCenterViewList) {
        employeeCostCenterService.deleteCostCodesForEmployee(employeeId,
                empCostCenterViewList);
        return null;
    }

    @RequestMapping(value = "/ptoConfig/listAsJson")
    public @ResponseBody
    List<EmployeePtoConfigView> getPtoConfigForEmp(
            @RequestParam("employeeId") Integer employeeId, Model model) {
        List<EmployeePtoConfigView> ptoConfigViewList = new ArrayList<EmployeePtoConfigView>();
        if (null != employeeId) {
            ptoConfigViewList = employeePtoConfigService
                    .getPtoConfigForEmployee(employeeId);
        }
        return ptoConfigViewList;
    }

    @RequestMapping(value = "/ptoConfig/save", method = RequestMethod.POST)
    public @ResponseBody
    void savePtoConfigForEmp(@RequestParam("employeeId") int employeeId,
            @RequestBody List<EmployeePtoConfigView> ptoConfigViewList) {
        employeePtoConfigService.savePtoConfigForEmployee(employeeId,
                ptoConfigViewList);
    }

    public String gotoEmployeeEdit(Model model) {
        model.addAttribute(GroupController.MODEL_ATTRIBUTE_GROUP,
                new GroupView());
        model.addAttribute(CostCenterController.MODEL_ATTRIBUTE_COST_CENTER,
                new CostCenterView());
        model.addAttribute(ClientController.MODEL_ATTRIBUTE_CLIENT,
                new ClientView());
        model.addAttribute(EmployeeController.MODEL_ATTRIBUTE_CHANGE_PASS,
                new AdminEmpPasswordView());
        model.addAttribute(
                AdministrationController.MODEL_ATTRIBUTE_DEFAULT_FORM,
                "employeeEdit");

        return AdministrationController.ADMIN_PAGE;
    }

    public String gotoEmployeeList(Model model) {
        model.addAttribute(GroupController.MODEL_ATTRIBUTE_GROUP,
                new GroupView());
        model.addAttribute(EmployeeController.MODEL_ATTRIBUTE_EMPLOYEE,
                new EmployeeView());
        model.addAttribute(CostCenterController.MODEL_ATTRIBUTE_COST_CENTER,
                new CostCenterView());
        model.addAttribute(ClientController.MODEL_ATTRIBUTE_CLIENT,
                new ClientView());
        model.addAttribute(EmployeeController.MODEL_ATTRIBUTE_CHANGE_PASS,
                new AdminEmpPasswordView());
        model.addAttribute(
                AdministrationController.MODEL_ATTRIBUTE_DEFAULT_FORM,
                "employeeList");
        return AdministrationController.ADMIN_PAGE;
    }

    public String gotoAdminResetPassword(Model model) {
        model.addAttribute(GroupController.MODEL_ATTRIBUTE_GROUP,
                new GroupView());
        model.addAttribute(CostCenterController.MODEL_ATTRIBUTE_COST_CENTER,
                new CostCenterView());
        model.addAttribute(ClientController.MODEL_ATTRIBUTE_CLIENT,
                new ClientView());
        model.addAttribute(EmployeeController.MODEL_ATTRIBUTE_EMPLOYEE,
                new EmployeeView());
        model.addAttribute(
                AdministrationController.MODEL_ATTRIBUTE_DEFAULT_FORM,
                "empPassReset");
        return AdministrationController.ADMIN_PAGE;
    }
}