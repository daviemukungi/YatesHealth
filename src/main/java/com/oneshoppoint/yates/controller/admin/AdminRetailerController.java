package com.oneshoppoint.yates.controller.admin;

import com.oneshoppoint.yates.service.ManufacturerService;
import com.oneshoppoint.yates.service.RetailerService;
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
@RequestMapping("/retailer/view")
@Secured("ROLE_MANAGE_RETAILERS")
public class AdminRetailerController {
    @Autowired
    private RetailerService retailerService;
    @Autowired
    private ManufacturerService manufacturerService;

    @RequestMapping(value="/admin/table",method = RequestMethod.GET)
    public ModelAndView table () {
        ModelAndView mv = new ModelAndView();
        mv.setViewName("admin/tables/retailer");
        return mv;
    }

    @RequestMapping(value="/admin/form",method = RequestMethod.GET)
    public ModelAndView form () {
        ModelAndView mv = new ModelAndView();
        mv.addObject("retailers",retailerService.getAll());
        mv.addObject("manufacturers",manufacturerService.getAll());
        mv.setViewName("admin/forms/retailer");
        return mv;
    }

    @RequestMapping(value="/admin/{id}/form",method = RequestMethod.GET)
    public ModelAndView form (@PathVariable Integer id) {
        ModelAndView mv = new ModelAndView();
        mv.addObject("retailer",retailerService.getById(id));
        mv.addObject("manufacturers",manufacturerService.getAll());
        mv.addObject("retailers",retailerService.getAll());
        mv.setViewName("admin/forms/retailer");
        return mv;
    }
}
