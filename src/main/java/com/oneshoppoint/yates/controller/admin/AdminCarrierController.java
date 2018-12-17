package com.oneshoppoint.yates.controller.admin;

import com.oneshoppoint.yates.service.CarrierService;
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
@RequestMapping("/carrier/view")
@Secured("ROLE_MANAGE_CARRIERS")
public class AdminCarrierController {
    @Autowired
    private CarrierService carrierService;

    @RequestMapping(value="/admin/table",method = RequestMethod.GET)
    public ModelAndView table () {
        ModelAndView mv = new ModelAndView();
        mv.setViewName("admin/tables/carrier");
        return mv;
    }

    @RequestMapping(value="/admin/form",method = RequestMethod.GET)
    public ModelAndView form () {
        ModelAndView mv = new ModelAndView();
        mv.addObject("carriers", carrierService.getAll());
        mv.setViewName("admin/forms/carrier");
        return mv;
    }

    @RequestMapping(value="/admin/{id}/form",method = RequestMethod.GET)
    public ModelAndView form (@PathVariable Integer id) {
        ModelAndView mv = new ModelAndView();
        mv.addObject("carrier", carrierService.getById(id));
        mv.addObject("carriers", carrierService.getAll());
        mv.setViewName("admin/forms/carrier");
        return mv;
    }
}
