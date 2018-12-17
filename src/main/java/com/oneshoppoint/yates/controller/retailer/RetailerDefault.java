package com.oneshoppoint.yates.controller.retailer;


import com.oneshoppoint.yates.model.Retailer;
import com.oneshoppoint.yates.service.RetailerService;
import com.oneshoppoint.yates.service.SettingsService;
import com.oneshoppoint.yates.util.CurrentUser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.annotation.Secured;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

/**
 * Created by robinson on 4/18/16.
 */
@Controller
@RequestMapping("/retailer")
@Secured("ROLE_RETAILER")
public class RetailerDefault {
    @Autowired
    CurrentUser currentUser;
    @Autowired
    RetailerService retailerService;
    @Autowired
    SettingsService settingsService;

    @RequestMapping(method = RequestMethod.GET)
    public ModelAndView index () {
        ModelAndView mv = new ModelAndView();
        mv.addObject("retailer",true);
        mv.addObject("outlets",filter(currentUser.getUser().getAffiliate().getRetailer().getOutlets()));
        mv.addObject("settings",settingsService.get());
        mv.addObject("pharmacy",currentUser.getUser().getAffiliate().getPharmacist());
        mv.setViewName("retailer/index");
        return mv;
    }

    @RequestMapping(value="/outlet/add",method = RequestMethod.GET)
    public ModelAndView outletForm () {
        ModelAndView mv = new ModelAndView();
        mv.addObject("retailer",true);
        mv.addObject("settings",settingsService.get());
        mv.setViewName("retailer/forms/outlet");
        mv.addObject("pharmacy",currentUser.getUser().getAffiliate().getPharmacist());
        return mv;
    }

    @RequestMapping(value="/outlet/{id}/edit",method = RequestMethod.GET)
    public ModelAndView outletForm (@PathVariable Integer id) {
        ModelAndView mv = new ModelAndView();
        mv.addObject("retailer",true);
        mv.addObject("settings",settingsService.get());
        mv.addObject("outlet",retailerService.getById(id));
        mv.addObject("pharmacy",currentUser.getUser().getAffiliate().getPharmacist());
        mv.setViewName("retailer/forms/outlet");
        return mv;
    }

    @RequestMapping(value="/account",method = RequestMethod.GET)
    public ModelAndView account () {
        ModelAndView mv = new ModelAndView();
        mv.addObject("retailer",true);
        mv.addObject("settings",settingsService.get());
        mv.addObject("user",currentUser.getUser());
        mv.setViewName("common/account");
        return mv;
    }

    private List<Retailer> filter (Collection<Retailer> retailers) {
        List<Retailer> filtered = new ArrayList<Retailer>();
        for (Retailer retailer : retailers) {
            if(retailer.getDeletedBy() == null) {
                filtered.add(retailer);
            }
        }

        return filtered;
    }
}
