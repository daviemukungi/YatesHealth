package com.oneshoppoint.yates.controller.medic;


import com.oneshoppoint.yates.service.MedicTypeService;
import com.oneshoppoint.yates.service.PatientService;
import com.oneshoppoint.yates.service.PrescriptionService;
import com.oneshoppoint.yates.service.SettingsService;
import com.oneshoppoint.yates.util.CurrentUser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.annotation.Secured;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

/**
 * Created by robinson on 4/18/16.
 */
@Controller
@RequestMapping("/medic")

public class MedicDefault {
    @Autowired
    MedicTypeService medicTypeService;
    @Autowired
    PatientService patientService;
    @Autowired
    CurrentUser currentUser;
    @Autowired
    SettingsService settingsService;

    @RequestMapping("/register")
    public ModelAndView register () {
        ModelAndView mv = new ModelAndView();
        mv.addObject("medicTypes",medicTypeService.getAll());
        mv.addObject("settings",settingsService.get());
        mv.setViewName("medic/register");
        return mv;
    }

    @Secured("ROLE_MEDIC")
    @RequestMapping(method = RequestMethod.GET)
    public ModelAndView index () {
        ModelAndView mv = new ModelAndView();
        mv.addObject("medic",true);
        mv.addObject("settings",settingsService.get());
        mv.addObject("patients",patientService.getByCreator(currentUser.getUser().getId()));
        mv.setViewName("medic/index");
        return mv;
    }

    @RequestMapping(value="/account",method = RequestMethod.GET)
    public ModelAndView account () {
        ModelAndView mv = new ModelAndView();
        mv.addObject("medic",true);
        mv.addObject("settings",settingsService.get());
        mv.addObject("user",currentUser.getUser());
        mv.setViewName("common/account");
        return mv;
    }
}
