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
import com.percipient.matrix.service.CostCenterService;

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
            return "forward:/admin/costCenterEdit";
        }
        costCenterService.saveCostCenter(costCenterView);
        return "redirect:/admin/costCenterList";
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
}
