package com.oneshoppoint.yates.controller.api;

import com.oneshoppoint.yates.model.Location;
import com.oneshoppoint.yates.service.LocationService;
import com.oneshoppoint.yates.util.Pagination;
import com.oneshoppoint.yates.util.Paginator;
import com.oneshoppoint.yates.util.RestMessage;
import com.oneshoppoint.yates.util.Status;
import com.oneshoppoint.yates.wrapper.LocationForm;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.annotation.Secured;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.Valid;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;


/**
 * The location api contains endpoints for adding,updating,deleting and accessing location data
 * @author robinson odhiambo
 * @version 1.0
 * @since 4/9/16.
 */

@RestController
@RequestMapping("/api/location")
public class LocationController {
    @Autowired
    private LocationService locationService;

    /**
     * Create a new location
     * @param locationForm This is a wrapper for the new location
     * @param result This is a BindingResult object that holds the validation result of the locationForm wrapper
     * @return RestMessage Json object that gives a status and a message concerning the operation
     */
    @RequestMapping(method = RequestMethod.POST)
    @Secured("ROLE_MANAGE_LOCATIONS")
    public RestMessage<Location> add (@Valid @RequestBody LocationForm locationForm,BindingResult result) {
        if(result.hasErrors()) {
            return new RestMessage<Location> (Status.FAILED,"Failed to add new location ",result.getFieldErrors());
        }

        locationService.save(locationForm);
        return new RestMessage<Location>(Status.CREATED,"Successfully added a new location");
    }

    /**
     * List locations with user specified pagination
     * @param page This is an integer used to specify the page
     * @param parts This is an integer used to specify the number of locations to be returned in a particular page
     * @return RestMessage Json object that gives a status and a message concerning the operation
     */
    @Secured({"ROLE_APP","ROLE_MANAGE_LOCATIONS"})
    @RequestMapping(method = RequestMethod.GET,params = {"page", "parts"})
    public RestMessage<List<Location>> get (@RequestParam int page,@RequestParam int parts) {
        Pagination<Location> pagination = new Paginator<Location>().paginate(page,flatten(locationService.getTree()),parts);
        String prev="",next = "";
        if(pagination.getPrev() != null) {
            prev = "/location?page="+pagination.getPrev()+"&parts="+parts;
        }
        if(pagination.getNext() != null) {
            next = "/location?page="+pagination.getNext()+"&parts="+parts;
        }

        return new RestMessage<List<Location>>(Status.OK,pagination.getList(),prev,next);
    }

    /**
     * List all locations
     * @return RestMessage Json object that gives a status and a message concerning the operation
     */
    @RequestMapping(method = RequestMethod.GET)
    @Secured({"ROLE_APP","ROLE_MANAGE_LOCATIONS"})
    public RestMessage<List<Location>> get () {
        return new RestMessage<List<Location>>(Status.OK,flatten(locationService.getTree()));
    }

    /**
     * Get the location hierarchy
     * @return RestMessage Json object that gives a status and a message concerning the operation
     */
    @RequestMapping(value="/tree",method = RequestMethod.GET)
    @Secured({"ROLE_APP","ROLE_MANAGE_LOCATIONS"})
    public synchronized RestMessage<Location>  tree () {
        return new RestMessage<Location>(Status.OK,locationService.getTree());
    }

    /**
     * Get information of a particular location
     * @param id Integer specifying the id of the location
     * @return RestMessage Json object that gives a status and a message concerning the operation
     */
    @RequestMapping(value="/{id}",method = RequestMethod.GET)
    @Secured({"ROLE_APP","ROLE_MANAGE_LOCATIONS"})
    public RestMessage<Location> get (@PathVariable Integer id) {
        return new RestMessage<Location>(Status.OK,locationService.getById(id));
    }

    /**
     * Search for location(s). This method uses primitive string matching hence regular expressions are not
     * supported
     * @param search String pattern specifying the name of the location
     * @return RestMessage Json object that gives a status and a message concerning the operation
     */
    @RequestMapping(method = RequestMethod.GET,params = {"search"})
    @Secured({"ROLE_APP","ROLE_MANAGE_LOCATIONS"})
    public RestMessage<List<Location>> search (@PathVariable String search) {
        return new RestMessage<List<Location>>(Status.OK,locationService.search(search));
    }

    /**
     * Update a location
     * @param locationForm This is a wrapper for the location
     * @param result This is a BindingResult object that holds the validation result of the locationForm wrapper
     * @return RestMessage Json object that gives a status and a message concerning the operation
     */
    @RequestMapping(method = RequestMethod.PUT)
    @Secured("ROLE_MANAGE_LOCATIONS")
    public RestMessage<Location> update (@Valid @RequestBody LocationForm locationForm,BindingResult result) {
        if(result.hasErrors()) {
            return new RestMessage<Location> (Status.FAILED,"Failed to updated the location ",result.getFieldErrors());
        }

        locationService.update(locationForm);
        return new RestMessage<Location>(Status.MODIFIED,"Successfully updated the location");
    }

    /**
     * Delete location(s)
     * @param ids Integer specifying the id(s) of the locations to be deleted
     * @return RestMessage Json object that gives a status and a message concerning the operation
     */
    @RequestMapping(method = RequestMethod.DELETE,params = {"ids"})
    @Secured("ROLE_MANAGE_LOCATIONS")
    public RestMessage<List<Location>> delete (@RequestParam Integer[] ids) {
        for(Integer id :ids) {
            locationService.delete(id);
        }

        return new RestMessage<List<Location>>(Status.DELETED,"successfully deleted the location(s)");
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
