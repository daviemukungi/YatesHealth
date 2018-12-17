package com.oneshoppoint.yates.controller.api;

import com.oneshoppoint.yates.model.Carrier;
import com.oneshoppoint.yates.model.CarrierPlan;
import com.oneshoppoint.yates.service.CarrierPlanService;
import com.oneshoppoint.yates.service.CarrierService;
import com.oneshoppoint.yates.util.Pagination;
import com.oneshoppoint.yates.util.Paginator;
import com.oneshoppoint.yates.util.RestMessage;
import com.oneshoppoint.yates.util.Status;
import com.oneshoppoint.yates.wrapper.CarrierForm;
import com.oneshoppoint.yates.wrapper.CarrierPlanForm;
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
 * The carrier api contains endpoints for adding,updating,deleting and accessing carrier data
 * @author robinson odhiambo
 * @version 1.0
 * @since 4/9/16.
 */

@RestController
@RequestMapping("/api/carrier")
public class CarrierController {
    @Autowired
    private CarrierService carrierService;
    @Autowired
    private CarrierPlanService carrierPlanService;

    /**
     * Create a new carrier
     * @param carrierForm This is a wrapper for the new carrier
     * @param result This is a BindingResult object that holds the validation result of the carrierForm wrapper
     * @return RestMessage Json object that gives a status and a message concerning the operation
     */
    @Secured("ROLE_MANAGE_CARRIERS")
    @RequestMapping(method = RequestMethod.POST)
    public RestMessage<Carrier> add (@Valid @RequestBody CarrierForm carrierForm,BindingResult result) {
        if(result.hasErrors()) {
            return new RestMessage<Carrier> (Status.FAILED,"Failed to add new carrier ",result.getFieldErrors());
        }

        carrierService.save(carrierForm);
        return new RestMessage<Carrier>(Status.CREATED,"Successfully added a new carrier");
    }

    /**
     * List carriers with user specified pagination
     * @param page This is an integer used to specify the page
     * @param parts This is an integer used to specify the number of carriers to be returned in a particular page
     * @return RestMessage Json object that gives a status and a message concerning the operation
     */
    @Secured("ROLE_MANAGE_CARRIERS")
    @RequestMapping(method = RequestMethod.GET,params = {"page", "parts"})
    public RestMessage<List<Carrier>> get (@RequestParam int page,@RequestParam int parts) {
        Pagination<Carrier> pagination = new Paginator<Carrier>().paginate(page,carrierService.getAll(),parts);
        String prev="",next = "";
        if(pagination.getPrev() != null) {
            prev = "/carrier?page="+pagination.getPrev()+"&parts="+parts;
        }
        if(pagination.getNext() != null) {
            next = "/carrier?page="+pagination.getNext()+"&parts="+parts;
        }

        return new RestMessage<List<Carrier>>(Status.OK,pagination.getList(),prev,next);
    }

    /**
     * List all carriers
     * @return RestMessage Json object that gives a status and a message concerning the operation
     */
    @Secured("ROLE_MANAGE_CARRIERS")
    @RequestMapping(method = RequestMethod.GET)
    public RestMessage<List<Carrier>> get () {
        return new RestMessage<List<Carrier>>(Status.OK,carrierService.getAll());
    }

    /**
     * Get information of a particular carrier
     * @param id Integer specifying the id of the carrier
     * @return RestMessage Json object that gives a status and a message concerning the operation
     */
    @Secured("ROLE_MANAGE_CARRIERS")
    @RequestMapping(params="{id}",method = RequestMethod.GET)
    public RestMessage<Carrier> get (@RequestParam Integer id) {
        return new RestMessage<Carrier>(Status.OK,carrierService.getById(id));
    }

    /**
     * Search for carrier(s). This method uses primitive string matching hence regular expressions are not
     * supported
     * @param search String pattern specifying the name of the carrier
     * @return RestMessage Json object that gives a status and a message concerning the operation
     */
    @Secured("ROLE_MANAGE_CARRIERS")
    @RequestMapping(method = RequestMethod.GET,params = {"search"})
    public RestMessage<List<Carrier>> search (@RequestParam String search) {
        return new RestMessage<List<Carrier>>(Status.OK,carrierService.search(search));
    }

    /**
     * Update a carrier
     * @param carrierForm This is a wrapper for the carrier
     * @param result This is a BindingResult object that holds the validation result of the carrierForm wrapper
     * @return RestMessage Json object that gives a status and a message concerning the operation
     */
    @Secured("ROLE_MANAGE_CARRIERS")
    @RequestMapping(method = RequestMethod.PUT)
    public RestMessage<Carrier> update (@Valid @RequestBody CarrierForm carrierForm,BindingResult result) {
        if(result.hasErrors()) {
            return new RestMessage<Carrier> (Status.FAILED,"Failed to updated the carrier ",result.getFieldErrors());
        }

        carrierService.update(carrierForm);
        return new RestMessage<Carrier>(Status.MODIFIED,"Successfully updated the carrier");
    }

    /**
     * Delete carrier(s)
     * @param ids Integer specifying the id(s) of the carriers to be deleted
     * @return RestMessage Json object that gives a status and a message concerning the operation
     */
    @Secured("ROLE_MANAGE_CARRIERS")
    @RequestMapping(method = RequestMethod.DELETE,params = {"ids"})
    public RestMessage<List<Carrier>> delete (@RequestParam Integer[] ids) {
        for(Integer id :ids) {
            carrierService.delete(id);
        }

        return new RestMessage<List<Carrier>>(Status.DELETED,"successfully deleted the carrier(s)");
    }

    /**
     * Create carrier plan for a particular carrier
     * @param plan this is a String that is used for differentiation of other carrier endpoints and therefore the value is
     *                of no importance
     * @param carrierPlanForm This is a wrapper for the carrier's plan
     * @param result This is a BindingResult object that holds the validation result of the carrierPlanForm wrapper
     * @return RestMessage Json object that gives a status and a message concerning the operation
     */
    @Secured({"ROLE_CARRIER","ROLE_MANAGE_CARRIERS"})
    @RequestMapping(method = RequestMethod.POST,params = {"plan"})
    public RestMessage<CarrierPlan> addPlan (@RequestParam String plan ,@Valid @RequestBody CarrierPlanForm carrierPlanForm,BindingResult result) {
        if(result.hasErrors()) {
            return new RestMessage<CarrierPlan> (Status.FAILED,"Failed to updated carrier plan (s) ",result.getFieldErrors());
        }

        carrierPlanService.save(carrierPlanForm);
        return new RestMessage<CarrierPlan>(Status.MODIFIED,"Successfully updated carrier plan (s)");
    }

    /**
     * Update carrier plan for a particular carrier
     * @param plan this is a String that is used for differentiation of other carrier endpoints and therefore the value is
     *                of no importance
     * @param carrierPlanForm This is a wrapper for the carrier's plan
     * @param result This is a BindingResult object that holds the validation result of the carrierPlanForm wrapper
     * @return RestMessage Json object that gives a status and a message concerning the operation
     */
    @Secured({"ROLE_CARRIER","ROLE_MANAGE_CARRIERS"})
    @RequestMapping(method = RequestMethod.PUT,params = {"plan"})
    public RestMessage<CarrierPlan> updatePlan (@RequestParam String plan ,@Valid @RequestBody CarrierPlanForm carrierPlanForm,BindingResult result) {
        if(result.hasErrors()) {
            return new RestMessage<CarrierPlan> (Status.FAILED,"Failed to updated carrier plan (s) ",result.getFieldErrors());
        }

        carrierPlanService.update(carrierPlanForm);
        return new RestMessage<CarrierPlan>(Status.MODIFIED,"Successfully updated carrier plan (s)");
    }

    /**
     * Update carrier plan for a particular carrier
     * @param plan this is a String that is used for differentiation of other carrier endpoints and therefore the value is
     *                of no importance
     * @param ids Integer specifying the id(s) of the carrier plans to be deleted
     * @return RestMessage Json object that gives a status and a message concerning the operation
     */
    @Secured({"ROLE_CARRIER","ROLE_MANAGE_CARRIERS"})
    @RequestMapping(method = RequestMethod.DELETE,params = {"ids","plan"})
    public RestMessage<CarrierPlan> deletePlan (@RequestParam String plan ,@RequestParam Integer[] ids) {
        for (Integer id : ids) {
            carrierPlanService.delete(id);
        }

        return new RestMessage<CarrierPlan>(Status.DELETED,"Successfully deleted carrier plan (s)");
    }

    /**
     * This endpoint is usually used in conjunction with either the carrier create or update endpoint
     * The sequence is to first upload the image then get the resulting message  which is a string specifying the location of the
     * image(on success), then you submit the carrierForm data to either the carrier create or update endpoint
     * @param file This is a MultipartFile object that accepts files that are submitted as if they were from a normal html form
     * @return RestMessage Json object that gives a status and a message which is a path to the image (on success) otherwise it is
     * just a string specifying the problem that occurred while uploading the image.
     */
    @Secured("ROLE_MANAGE_CARRIERS")
    @RequestMapping(value="/upload",method = RequestMethod.POST)
    public RestMessage  upload(@RequestParam("file") MultipartFile file) {
        if (!file.isEmpty()) {
            try {
                byte[] bytes = file.getBytes();

                String rootPath = System.getProperty("catalina.home");
                UUID uuid = UUID.randomUUID();
                String path = rootPath + File.separator +"yates"+ File.separator +"images" +File.separator + "carriers"+File.separator+ uuid;
                File dir = new File(path);
                if (!dir.exists()) {
                    dir.mkdirs();
                }

                File serverFile = new File(dir.getAbsolutePath()+ File.separator + file.getOriginalFilename());
                BufferedOutputStream stream = new BufferedOutputStream(new FileOutputStream(serverFile));
                stream.write(bytes);
                stream.close();

                return new RestMessage(Status.OK,File.separator + "carriers"+File.separator+uuid+File.separator+file.getOriginalFilename());
            } catch (Exception e) {
                return new RestMessage(Status.ERROR,"Failed to upload " + file.getOriginalFilename() + " => " + e.getMessage());
            }
        } else {
            return new RestMessage(Status.FAILED,"Failed to upload " + file.getOriginalFilename()+ " because the file was empty.");
        }
    }
}
