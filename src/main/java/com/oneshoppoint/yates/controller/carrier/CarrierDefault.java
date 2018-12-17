package com.oneshoppoint.yates.controller.carrier;

import com.oneshoppoint.yates.model.CarrierPlan;
import com.oneshoppoint.yates.model.Location;
import com.oneshoppoint.yates.service.CarrierPlanService;
import com.oneshoppoint.yates.service.LocationService;
import com.oneshoppoint.yates.service.OrderService;
import com.oneshoppoint.yates.service.SettingsService;
import com.oneshoppoint.yates.util.CurrentUser;
import com.oneshoppoint.yates.wrapper.OrderSummary;
import com.oneshoppoint.yates.wrapper.Settings;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.annotation.Secured;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import java.util.*;

/**
 * Created by Davie on 5/1/16.
 */
@RequestMapping("/carrier")
@Controller
@Secured("ROLE_CARRIER")
public class CarrierDefault {
    @Autowired
    private OrderService orderService;
    @Autowired
    private CurrentUser currentUser;
    @Autowired
    private LocationService locationService;
    @Autowired
    private SettingsService settingsService;

    @RequestMapping(method = RequestMethod.GET)
    public ModelAndView index () {
        ModelAndView mv = new ModelAndView();
        List<OrderSummary> orders = orderService.getOrderSummariesByCarrier(currentUser.getUser().getAffiliate().getCarrier().getId());
        if(orders != null) {
            Collections.reverse(orders);
            mv.addObject("orders",orders);
        }
        mv.addObject("carrier",true);
        mv.addObject("settings",settingsService.get());
        mv.setViewName("common/orders");
        return mv;
    }

    @RequestMapping(value="/order/{invoice}/view",method = RequestMethod.GET)
    public ModelAndView invoice (@PathVariable String invoice) {
        ModelAndView mv = new ModelAndView();
        mv.addObject("carrier",true);
        mv.addObject("invoice", orderService.getInvoice(invoice));
        mv.addObject("settings",settingsService.get());
        mv.setViewName("common/invoice");
        return mv;
    }

    @RequestMapping(value="/plan",method = RequestMethod.GET)
    public ModelAndView plan () {
        ModelAndView mv = new ModelAndView();
        mv.addObject("carrier",true);
        mv.addObject("settings",settingsService.get());
        mv.addObject("carrierPlans",currentUser.getUser().getAffiliate().getCarrier().getCarrierPlans());
        mv.setViewName("carrier/plan");
        return mv;
    }

    @RequestMapping(value="/plan/add",method = RequestMethod.GET)
    public ModelAndView form () {
        ModelAndView mv = new ModelAndView();
        mv.addObject("carrier",true);
        mv.addObject("settings",settingsService.get());
        mv.addObject("locations",flatten(locationService.getTree()));
        mv.setViewName("carrier/planForm");
        return mv;
    }

    @RequestMapping(value="/plan/{id}/edit",method = RequestMethod.GET)
    public ModelAndView form (@PathVariable Integer id) {
        ModelAndView mv = new ModelAndView();
        mv.addObject("locations",flatten(locationService.getTree()));
        mv.addObject("settings",settingsService.get());
        mv.addObject("carrier",true);
        List<CarrierPlan> carrierPlans = currentUser.getUser().getAffiliate().getCarrier().getCarrierPlans();
        for(CarrierPlan plan : carrierPlans) {
            if(plan.getId() == id) {
                mv.addObject("plan",plan);
            }
        }
        mv.setViewName("carrier/planForm");
        return mv;
    }

    @RequestMapping(value="/account",method = RequestMethod.GET)
    public ModelAndView account () {
        ModelAndView mv = new ModelAndView();
        mv.addObject("carrier",true);
        mv.addObject("settings",settingsService.get());
        mv.addObject("user",currentUser.getUser());
        mv.setViewName("common/account");
        return mv;
    }

    private List<Location> flatten (Location location) {
        List<Location> locationList  = new ArrayList<Location>();
        Queue<Location> locationQueue = new LinkedList<Location>();
        if(location != null) {
            locationQueue.add(location);
            while(!locationQueue.isEmpty()) {
                Location cat = locationQueue.remove();

                if(cat.getChildren()!= null &&!cat.getChildren().isEmpty()) {
                    locationQueue.addAll(cat.getChildren());
                }
                locationList.add(cat);
            }
        }

        return locationList;
    }
}
