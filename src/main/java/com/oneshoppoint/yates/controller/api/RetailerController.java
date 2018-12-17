package com.oneshoppoint.yates.controller.api;

import com.oneshoppoint.yates.model.Retailer;
import com.oneshoppoint.yates.service.RetailerService;
import com.oneshoppoint.yates.util.*;
import com.oneshoppoint.yates.wrapper.RetailerForm;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.annotation.Secured;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.Valid;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.util.List;
import java.util.UUID;


/**
 * The retailer api contains endpoints for adding,updating,deleting and accessing retailer data
 * @author robinson odhiambo
 * @version 1.0
 * @since 4/9/16.
 */

@RestController
@RequestMapping("/api/retailer")
@Secured("ROLE_MANAGE_RETAILERS")
public class RetailerController {
    @Autowired
    private RetailerService retailerService;

    /**
     * Create a new retailer
     * @param retailerForm This is a wrapper for the new retailer
     * @param result This is a BindingResult object that holds the validation result of the retailerForm wrapper
     * @return RestMessage Json object that gives a status and a message concerning the operation
     */
    @RequestMapping(method = RequestMethod.POST)
    public RestMessage<Retailer> add (@Valid @RequestBody RetailerForm retailerForm,BindingResult result) {
        if(result.hasErrors()) {
            return new RestMessage<Retailer> (Status.FAILED,"Failed to add new retailer ",result.getFieldErrors());
        }

        retailerService.save(retailerForm);
        return new RestMessage<Retailer>(Status.CREATED,"Successfully added a new retailer");
    }

    /**
     * List retailers with user specified pagination
     * @param page This is an integer used to specify the page
     * @param parts This is an integer used to specify the number of retailers to be returned in a particular page
     * @return RestMessage Json object that gives a status and a message concerning the operation
     */
    @RequestMapping(method = RequestMethod.GET,params = {"page", "parts"})
    @Secured({"ROLE_MANAGE_RETAILERS","ROLE_APP"})
    public RestMessage<List<Retailer>> get (@RequestParam int page,@RequestParam int parts) {
        Pagination<Retailer> pagination = new Paginator<Retailer>().paginate(page,retailerService.getAll(),parts);
        String prev="",next = "";
        if(pagination.getPrev() != null) {
            prev = "/retailer?page="+pagination.getPrev()+"&parts="+parts;
        }
        if(pagination.getNext() != null) {
            next = "/retailer?page="+pagination.getNext()+"&parts="+parts;
        }

        return new RestMessage<List<Retailer>>(Status.OK,pagination.getList(),prev,next);
    }

    /**
     * List all retailers
     * @return RestMessage Json object that gives a status and a message concerning the operation
     */
    @RequestMapping(method = RequestMethod.GET)
    @Secured({"ROLE_MANAGE_RETAILERS","ROLE_APP"})
    public RestMessage<List<Retailer>> get () {
        return new RestMessage<List<Retailer>>(Status.OK,retailerService.getAll());
    }

    /**
     * Get information of a particular retailer
     * @param id Integer specifying the id of the retailer
     * @return RestMessage Json object that gives a status and a message concerning the operation
     */
    @RequestMapping(method = RequestMethod.GET,params = {"id"})
    @Secured({"ROLE_MANAGE_RETAILERS","ROLE_APP"})
    public RestMessage<Retailer> get (@RequestParam Integer id) {
        return new RestMessage<Retailer>(Status.OK,retailerService.getById(id));
    }

    /**
     * Get information of retailers for a particular location
     * @param locationId Integer specifying the id of the location of interest
     * @return RestMessage Json object that gives a status and a message concerning the operation
     */

    @RequestMapping(method = RequestMethod.GET,params = {"locationId"})
    @Secured({"ROLE_MANAGE_RETAILERS","ROLE_APP"})
    public RestMessage<Retailer> getRetailers (@RequestParam Integer locationId) {
        return new RestMessage<Retailer>(Status.OK,retailerService.getById(locationId));
    }

    /**
     * Search for retailer(s). This method uses primitive string matching hence regular expressions are not
     * supported
     * @param search String pattern specifying the name of the retailer
     * @return RestMessage Json object that gives a status and a message concerning the operation
     */
    @RequestMapping(method = RequestMethod.GET,params = {"search"})
    public RestMessage<List<Retailer>> search (@PathVariable String search) {
        return new RestMessage<List<Retailer>>(Status.OK,retailerService.search(search));
    }

    /**
     * Update a retailer
     * @param retailerForm This is a wrapper for the retailer
     * @param result This is a BindingResult object that holds the validation result of the retailerForm wrapper
     * @return RestMessage Json object that gives a status and a message concerning the operation
     */
    @RequestMapping(method = RequestMethod.PUT)
    public RestMessage<Retailer> update (@Valid @RequestBody RetailerForm retailerForm,BindingResult result) {
        if(result.hasErrors()) {
            return new RestMessage<Retailer> (Status.FAILED,"Failed to updated the retailer ",result.getFieldErrors());
        }

        retailerService.update(retailerForm);
        return new RestMessage<Retailer>(Status.MODIFIED,"Successfully updated the retailer");
    }

    /**
     * Delete retailer(s)
     * @param ids Integer specifying the id(s) of the retailers to be deleted
     * @return RestMessage Json object that gives a status and a message concerning the operation
     */
    @RequestMapping(method = RequestMethod.DELETE,params = {"ids"})
    public RestMessage<List<Retailer>> delete (@RequestParam Integer[] ids) {
        for(Integer id :ids) {
            retailerService.delete(id);
        }

        return new RestMessage<List<Retailer>>(Status.DELETED,"successfully deleted the retailer(s)");
    }

    /**
     * This endpoint is usually used in conjunction with either the retailer create or update endpoint
     * The sequence is to first upload the image then get the resulting message  which is a string specifying the location of the
     * image(on success), then you submit the retailerForm data to either the retailer create or update endpoint
     * @param file This is a MultipartFile object that accepts files that are submitted as if they were from a normal html form
     * @return RestMessage Json object that gives a status and a message which is a path to the image (on success) otherwise it is
     * just a string specifying the problem that occurred while uploading the image.
     */
    @RequestMapping(value="/upload",method = RequestMethod.POST)
    public RestMessage  upload(@RequestParam("file") MultipartFile file) {
        if (!file.isEmpty()) {
            try {
                byte[] bytes = file.getBytes();

                String rootPath = System.getProperty("catalina.home");
                UUID uuid = UUID.randomUUID();
                String path = rootPath + File.separator +"yates"+ File.separator +"images" +File.separator + "retailers"+File.separator+ uuid;
                File dir = new File(path);
                if (!dir.exists()) {
                    dir.mkdirs();
                }

                File serverFile = new File(dir.getAbsolutePath()+ File.separator + file.getOriginalFilename());
                BufferedOutputStream stream = new BufferedOutputStream(new FileOutputStream(serverFile));
                stream.write(bytes);
                stream.close();

                return new RestMessage(Status.OK,File.separator + "retailers"+File.separator+uuid+File.separator+file.getOriginalFilename());
            } catch (Exception e) {
                return new RestMessage(Status.ERROR,"Failed to upload " + file.getOriginalFilename() + " => " + e.getMessage());
            }
        } else {
            return new RestMessage(Status.FAILED,"Failed to upload " + file.getOriginalFilename()+ " because the file was empty.");
        }
    }
}
