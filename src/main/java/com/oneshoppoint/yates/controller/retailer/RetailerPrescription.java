package com.oneshoppoint.yates.controller.retailer;


import com.oneshoppoint.yates.model.Prescription;
import com.oneshoppoint.yates.service.PrescriptionService;
import com.oneshoppoint.yates.service.RetailerService;
import com.oneshoppoint.yates.service.SettingsService;
import com.oneshoppoint.yates.util.CurrentUser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.annotation.Secured;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

/**
 * Created by robinson on 4/18/16.
 */
@Controller
@RequestMapping("/retailer")
@Secured("ROLE_RETAILER")
public class RetailerPrescription {
    @Autowired
    CurrentUser currentUser;
    @Autowired
    RetailerService retailerService;
    @Autowired
    PrescriptionService prescriptionService;
    @Autowired
    SettingsService settingsService;

    @RequestMapping(value="/prescriptions",method = RequestMethod.GET)
    public ModelAndView outletForm () {
        ModelAndView mv = new ModelAndView();
        mv.addObject("retailer",true);
        mv.addObject("settings",settingsService.get());
        mv.setViewName("retailer/prescription");
        return mv;
    }

    @RequestMapping(value="/prescriptions",params = {"code","submitCode"},method = RequestMethod.POST)
    public ModelAndView outletForm (@RequestParam String code) {
        ModelAndView mv = new ModelAndView();
        Prescription prescription = prescriptionService.getByCode(code);
        if(prescription == null){
            mv.addObject("expired",true);
        } else {
            mv.addObject("prescription",prescription);
        }
        mv.addObject("retailer",true);
        mv.addObject("settings",settingsService.get());
        mv.setViewName("retailer/prescription");
        return mv;
    }


}
