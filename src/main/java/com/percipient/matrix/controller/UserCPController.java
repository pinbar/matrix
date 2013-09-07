package com.percipient.matrix.controller;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.percipient.matrix.service.UserCPService;
import com.percipient.matrix.session.UserInfo;
import com.percipient.matrix.view.ChangePasswordView;
import com.percipient.matrix.view.EmployeeContactInfoView;

@Controller
@RequestMapping(value = "/usercp")
public class UserCPController {

    private static final String PAGE_USER_CP = "user/userCPPage";
    private static final String MODEL_ATTRIBUTE_CHANGE_PASS = "changePass";
    private static final String MODEL_ATTRIBUTE_EMP_CONTACT_INFO = "empContactInfo";

    @Autowired
    UserCPService userCPService;

    @Autowired
    private javax.inject.Provider<UserInfo> userInfo;

    @RequestMapping(value = "/empcontactinfo", method = RequestMethod.GET)
    public String gotoEmpContactInfo(Model model) {

        EmployeeContactInfoView empContactInfoView = userCPService
                .employeeContactInfoView(userInfo.get().getUserName());
        model.addAttribute(MODEL_ATTRIBUTE_EMP_CONTACT_INFO, empContactInfoView);
        return PAGE_USER_CP;
    }

    @RequestMapping(value = "/empcontactinfo", method = RequestMethod.POST)
    public String saveEmployeeContactInfo(
            @Valid @ModelAttribute(MODEL_ATTRIBUTE_EMP_CONTACT_INFO) EmployeeContactInfoView empContactInfoView,
            BindingResult result, Model model) {

        if (result.hasErrors()) {
            return PAGE_USER_CP;
        }
        userCPService.saveEmployee(empContactInfoView);
        model.addAttribute("info", "Information changed successfully!");
        model.addAttribute(MODEL_ATTRIBUTE_EMP_CONTACT_INFO, empContactInfoView);
        return PAGE_USER_CP;
    }

    @RequestMapping(value = "/changepassword", method = RequestMethod.GET)
    public String gotoChangePassword(Model model) {

        ChangePasswordView changePassView = userCPService
                .getChangePasswordView(userInfo.get().getUserName());
        setupChangePassword(changePassView, model);
        return PAGE_USER_CP;
    }

    @RequestMapping(value = "/changepassword", method = RequestMethod.POST)
    public String changePassword(
            @Valid @ModelAttribute(MODEL_ATTRIBUTE_CHANGE_PASS) ChangePasswordView changePassView,
            BindingResult result, Model model) {

        if (result.hasErrors()) {
            return PAGE_USER_CP;
        }

        if (isChangePasswordInvalid(changePassView, model)) {
            return PAGE_USER_CP;
        }

        userCPService.saveUser(changePassView);

        setupChangePassword(changePassView, model);
        model.addAttribute("info", "Password changed successfully!");
        return PAGE_USER_CP;
    }

    private void setupChangePassword(ChangePasswordView changePass, Model model) {
        ChangePasswordView changePassView = new ChangePasswordView();
        changePassView.setUserId(changePass.getUserId());
        changePassView.setUserName(changePass.getUserName());
        model.addAttribute(MODEL_ATTRIBUTE_CHANGE_PASS, changePassView);
    }

    private boolean isChangePasswordInvalid(ChangePasswordView changePassView,
            Model model) {
        if (!changePassView.getNewPassword().equalsIgnoreCase(
                changePassView.getNewPassword2())) {
            model.addAttribute("error",
                    "New password must match in both fields.");
            return true;
        }

        ChangePasswordView existingUserView = userCPService
                .getChangePasswordView(userInfo.get().getUserName());

        if (!existingUserView.getPassword().equalsIgnoreCase(
                changePassView.getPassword())) {
            model.addAttribute("error",
                    "Old password must match the existing password.");
            return true;
        }

        if (existingUserView.getPassword().equalsIgnoreCase(
                changePassView.getNewPassword())) {
            model.addAttribute("error",
                    "New password cannot be the same as the existing password.");
            return true;
        }

        return false;
    }

}
