package com.oneshoppoint.yates.controller.admin;


import org.springframework.security.access.annotation.Secured;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

/**
 * Created by Davie on 4/9/16.
 *
 * This is a controller class that serves logic
 * to the product features
 */

@Controller
@RequestMapping("/feature/view")
@Secured("ROLE_MANAGE_FEATURES")
public class AdminFeatureController {
    /**
     * This is a controller method that loads the default
     * admin page
     * @return
     */
    @RequestMapping(value="/admin/table",method = RequestMethod.GET)
    public ModelAndView table () {
        ModelAndView mv = new ModelAndView();
        mv.setViewName("admin/tables/feature");
        return mv;
    }
}
