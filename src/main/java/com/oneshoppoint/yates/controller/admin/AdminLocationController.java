package com.oneshoppoint.yates.controller.admin;

import com.oneshoppoint.yates.model.Location;
import com.oneshoppoint.yates.service.LocationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.annotation.Secured;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;

/**
 * Created by Davie on 4/9/16.
 */

@Controller
@RequestMapping("/location/view")
@Secured("ROLE_MANAGE_LOCATIONS")
public class AdminLocationController {
    @Autowired
    private LocationService locationService;

    @RequestMapping(value="/admin/table",method = RequestMethod.GET)
    public ModelAndView table () {
        ModelAndView mv = new ModelAndView();
        mv.setViewName("admin/tables/location");
        return mv;
    }

    @RequestMapping(value="/admin/form",method = RequestMethod.GET)
    public ModelAndView form () {
        ModelAndView mv = new ModelAndView();
        mv.addObject("locations",flatten(locationService.getTree()));
        mv.setViewName("admin/forms/location");
        return mv;
    }

    @RequestMapping(value="/admin/{id}/form",method = RequestMethod.GET)
    public ModelAndView form (@PathVariable Integer id) {
        ModelAndView mv = new ModelAndView();
        mv.addObject("location",locationService.getById(id));
        mv.addObject("locations",flatten(locationService.getTree()));
        mv.setViewName("admin/forms/location");
        return mv;
    }

    private List<Location> flatten (Location location) {
        List<Location> locationList  = new ArrayList<Location>();
        if(location != null) {
            Queue<Location> locationQueue = new LinkedList<Location>();
            locationQueue.add(location);
            while(!locationQueue.isEmpty()) {
                Location cat = locationQueue.remove();

                if(!cat.getChildren().isEmpty()) {
                    locationQueue.addAll(cat.getChildren());
                }
                locationList.add(cat);
            }
        }

        return locationList;
    }
}
