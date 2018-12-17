package com.oneshoppoint.yates.controller.retailer;

import com.oneshoppoint.yates.service.OrderService;
import com.oneshoppoint.yates.service.SettingsService;
import com.oneshoppoint.yates.util.CurrentUser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.annotation.Secured;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

/**
 * Created by robinson on 5/3/16.
 */
@Controller
@RequestMapping("/retailer/order")
@Secured("ROLE_RETAILER")
public class RetailerOrder {
    @Autowired
    private OrderService orderService;
    @Autowired
    private CurrentUser currentUser;
    @Autowired
    private SettingsService settingsService;

    @RequestMapping(method = RequestMethod.GET)
    public ModelAndView orders () {
        ModelAndView mv = new ModelAndView();
        mv.addObject("retailer",true);
        mv.addObject("settings",settingsService.get());
        mv.addObject("orders",orderService.getOrderSummariesByRetailer(currentUser.getUser().getAffiliate().getRetailer().getId()));
        mv.addObject("pharmacy",currentUser.getUser().getAffiliate().getPharmacist());
        mv.setViewName("common/orders");
        return mv;
    }

    @RequestMapping(value="/{invoice}/view",method = RequestMethod.GET)
    public ModelAndView invoice (@PathVariable String invoice) {
        ModelAndView mv = new ModelAndView();
        mv.addObject("retailer",true);
        mv.addObject("settings",settingsService.get());
        mv.addObject("invoice", orderService.getInvoice(invoice));
        mv.addObject("pharmacy",currentUser.getUser().getAffiliate().getPharmacist());
        mv.setViewName("common/invoice");
        return mv;
    }
}
