package com.oneshoppoint.yates.controller.admin;

import com.oneshoppoint.yates.service.InnService;
import com.oneshoppoint.yates.service.MedicTypeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.annotation.Secured;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

/**
 * Created by Davie on 4/9/16.
 */

@Controller
@RequestMapping("/medictype/view")
@Secured("ROLE_MANAGE_MEDIC_TYPES")
public class AdminMedicTypeController {
    @Autowired
    private MedicTypeService medicTypeService;
    @Autowired
    private InnService innService;

    @RequestMapping(value="/admin/table",method = RequestMethod.GET)
    public ModelAndView table () {
        ModelAndView mv = new ModelAndView();
        mv.setViewName("admin/tables/medicType");
        return mv;
    }

    @RequestMapping(value="/admin/form",method = RequestMethod.GET)
    public ModelAndView form () {
        ModelAndView mv = new ModelAndView();
        mv.addObject("inns",innService.getAll());
        mv.setViewName("admin/forms/medicType");
        return mv;
    }

    @RequestMapping(value="/admin/{id}/form",method = RequestMethod.GET)
    public ModelAndView form (@PathVariable Integer id) {
        ModelAndView mv = new ModelAndView();
        mv.addObject("medicType",medicTypeService.getById(id));
        mv.addObject("inns",innService.getAll());
        mv.setViewName("admin/forms/medicType");
        return mv;
    }
}
