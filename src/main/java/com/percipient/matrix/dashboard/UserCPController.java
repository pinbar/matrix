package com.percipient.matrix.dashboard;

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
import com.percipient.matrix.view.UserView;

@Controller
@RequestMapping(value = "/usercp")
public class UserCPController {

    public static final String MODEL_ATTRIBUTE_USER = "user";

    @Autowired
    UserCPService userCPService;

    @Autowired
    UserInfo userInfo;

    @RequestMapping(value = "/password", method = RequestMethod.GET)
    public String gotoChangePassword(Model model) {

        UserView user = userCPService.getUser(userInfo.getUserName());
        gotoChangePassword(user, model);
        return "changePassword";
    }

    @RequestMapping(value = "/password", method = RequestMethod.POST)
    public String setPassword(
            @Valid @ModelAttribute(MODEL_ATTRIBUTE_USER) UserView user,
            BindingResult result, Model model) {

        if (result.hasErrors()) {
            return "changePassword";
        }

        if (!user.getNewPassword1().equalsIgnoreCase(user.getNewPassword2())) {
            model.addAttribute("error",
                    "New password must match in both fields.");
            return "changePassword";
        }

        UserView existingUserView = userCPService.getUser(userInfo
                .getUserName());

        if (!existingUserView.getPassword()
                .equalsIgnoreCase(user.getPassword())) {
            model.addAttribute("error",
                    "Old password must match the existing password.");
            return "changePassword";
        }

        if (existingUserView.getPassword().equalsIgnoreCase(
                user.getNewPassword1())) {
            model.addAttribute("error",
                    "New password cannot be the same as the existing password.");
            return "changePassword";
        }

        userCPService.saveUser(user);

        gotoChangePassword(user, model);
        model.addAttribute("info", "Password changed successfully!");
        return "changePassword";
    }

    private void gotoChangePassword(UserView user, Model model) {
        UserView userView = new UserView();
        userView.setId(user.getId());
        userView.setUserName(user.getUserName());
        model.addAttribute(MODEL_ATTRIBUTE_USER, userView);
    }

}
