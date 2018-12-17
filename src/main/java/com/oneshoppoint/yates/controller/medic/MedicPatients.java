package com.oneshoppoint.yates.controller.medic;


import com.oneshoppoint.yates.service.MedicTypeService;
import com.oneshoppoint.yates.service.PatientService;
import com.oneshoppoint.yates.service.SettingsService;
import com.oneshoppoint.yates.util.CurrentUser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.annotation.Secured;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

/**
 * Created by robinson on 4/18/16.
 */
@Controller
@RequestMapping("/medic")
@Secured("ROLE_MEDIC")
public class MedicPatients {
    @Autowired
    MedicTypeService medicTypeService;
    @Autowired
    PatientService patientService;
    @Autowired
    CurrentUser currentUser;
    @Autowired
    SettingsService settingsService;


    @RequestMapping("/patients/add")
    public ModelAndView form () {
        ModelAndView mv = new ModelAndView();
        mv.addObject("medic",true);
        mv.addObject("settings",settingsService.get());
        mv.setViewName("medic/forms/patient");
        return mv;
    }

    @RequestMapping("/patients/{id}/edit")
    public ModelAndView form (@PathVariable Integer id) {
        ModelAndView mv = new ModelAndView();
        mv.addObject("medic",true);
        mv.addObject("settings",settingsService.get());
        mv.addObject("patient",patientService.getById(id));
        mv.setViewName("medic/forms/patient");
        return mv;
    }
}
