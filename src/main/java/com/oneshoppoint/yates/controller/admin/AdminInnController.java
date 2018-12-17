package com.oneshoppoint.yates.controller.admin;


import com.oneshoppoint.yates.service.DosageFormService;
import com.oneshoppoint.yates.service.InnService;
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
@RequestMapping("/inn/view")
@Secured("ROLE_MANAGE_INNS")
public class AdminInnController {
    @Autowired
    InnService innService;
    @Autowired
    DosageFormService dosageFormService;

    @RequestMapping(value="/admin/table",method = RequestMethod.GET)
    public ModelAndView table () {
        ModelAndView mv = new ModelAndView();
        mv.setViewName("admin/tables/inn");
        return mv;
    }


    @RequestMapping(value="/admin/form",method = RequestMethod.GET)
    public ModelAndView form () {
        ModelAndView mv = new ModelAndView();
        mv.addObject("dosageForms", dosageFormService.getAll());
        mv.setViewName("admin/forms/inn");
        return mv;
    }

    @RequestMapping(value="/admin/{id}/form",method = RequestMethod.GET)
    public ModelAndView form (@PathVariable Integer id) {
        ModelAndView mv = new ModelAndView();
        mv.addObject("inn",innService.getById(id));
        mv.addObject("dosageForms",dosageFormService.getAll());
        mv.setViewName("admin/forms/inn");
        return mv;
    }
}
