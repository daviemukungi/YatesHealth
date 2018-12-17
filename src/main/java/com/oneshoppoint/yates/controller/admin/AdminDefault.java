package com.oneshoppoint.yates.controller.admin;

import com.oneshoppoint.yates.model.Location;
import com.oneshoppoint.yates.model.Medic;
import com.oneshoppoint.yates.model.User;
import com.oneshoppoint.yates.service.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.annotation.Secured;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;

/**
 * Created by Davie on 4/15/16.
 * This is a controller class that serves logic
 * to the admin views
 */
@Controller
public class AdminDefault {
    @Autowired
    LocationService locationService;
    @Autowired
    RetailerService retailerService;
    @Autowired
    OrderService orderService;
    @Autowired
    CarrierService carrierService;
    @Autowired
    UserService userService;
    @Autowired
    SettingsService settingsService;


    /**
     * This is a controller method that loads the default
     * admin page
     * @return
     */
    @RequestMapping("/admin")
    @Secured("ROLE_ADMIN")
    public ModelAndView admin () {
        ModelAndView mv = new ModelAndView();
        Location root = locationService.getTree();
        mv.addObject("root",root);
        mv.addObject("locations",flatten(root));
        mv.addObject("years",orderService.getYears());
        mv.addObject("carriers",carrierService.getAll());
        mv.addObject("retailers",retailerService.getAll());
        mv.addObject("settings",settingsService.get());
        List<User> users = userService.getAll();
        List<User> medicUsers = new ArrayList<User>();
        for (User user : users) {
            if(user.getMedic() != null) {
                medicUsers.add(user);
            }
        }
        mv.addObject("medicUsers",medicUsers);
        mv.setViewName("admin/index");
        return mv;
    }

    /**
     * This is a controller method that loads the settings page
     * @return
     */
    @RequestMapping("/admin/settings")
    @Secured("ROLE_MANAGE_SETTINGS")
    public ModelAndView settings () {
        ModelAndView mv = new ModelAndView();
        mv.addObject("settings",settingsService.get());
        mv.setViewName("admin/forms/settings");
        return mv;
    }

    /**
     * This is a controller method that loads the password page
     * @return
     */
    @RequestMapping("/admin/password")
    @Secured("ROLE_ADMIN")
    public ModelAndView changePassword () {
        ModelAndView mv = new ModelAndView();
        mv.addObject("settings",settingsService.get());
        mv.setViewName("admin/forms/changePassword");
        return mv;
    }

    /**
     * This is a utility method that transforms the location object
     * hierarchy to a list
     * @return
     */
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
