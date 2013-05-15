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

import com.percipient.matrix.service.ClientService;
import com.percipient.matrix.view.AdminEmpPasswordView;
import com.percipient.matrix.view.ClientView;
import com.percipient.matrix.view.CostCenterView;
import com.percipient.matrix.view.EmployeeView;
import com.percipient.matrix.view.GroupView;

@Controller
@RequestMapping(value = "/admin/client")
public class ClientController {

    public static final String MODEL_ATTRIBUTE_CLIENT = "client";
    public static final String MODEL_ATTRIBUTE_CLIENTS = "clients";

    @Autowired
    ClientService clientService;

    @RequestMapping(value = "/listAsJson", produces = "application/json")
    @ResponseBody
    public List<ClientView> getClients(Model model) {
        List<ClientView> clients = clientService.getClients();
        return clients;
    }

    @RequestMapping(value = "/save", method = RequestMethod.POST)
    public String saveClient(
            @Valid @ModelAttribute(MODEL_ATTRIBUTE_CLIENT) ClientView clientView,
            BindingResult result, Model model) {

        if (result.hasErrors()) {
            return gotoClientEdit(model);
        }
        clientService.saveClient(clientView);
        return gotoClientList(model);
    }

    @RequestMapping(value = "/update", method = RequestMethod.GET, produces = "application/json")
    @ResponseBody
    public ClientView updateClient(@RequestParam("id") Integer clientId,
            Model model) {
        ClientView clientView = clientService.getClient(clientId);
        return clientView;
    }

    @RequestMapping(value = "/delete")
    public @ResponseBody
    Object deleteGroup(@RequestParam("id") Integer clientId, Model model) {
        ClientView clientView = clientService.getClient(clientId);
        clientService.deleteClient(clientView);
        return new ClientView();
    }

    private String gotoClientEdit(Model model) {
        model.addAttribute(GroupController.MODEL_ATTRIBUTE_GROUP,
                new GroupView());
        model.addAttribute(EmployeeController.MODEL_ATTRIBUTE_EMPLOYEE,
                new EmployeeView());
        model.addAttribute(CostCenterController.MODEL_ATTRIBUTE_COST_CENTER,
                new CostCenterView());
        model.addAttribute(EmployeeController.MODEL_ATTRIBUTE_CHANGE_PASS,
                new AdminEmpPasswordView());
        model.addAttribute(
                AdministrationController.MODEL_ATTRIBUTE_DEFAULT_FORM,
                "clientUpdate");
        return "administrationPage";
    }

    private String gotoClientList(Model model) {
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
                "clientList");
        return "administrationPage";
    }
}
