package com.oneshoppoint.yates.controller.api;

import com.oneshoppoint.yates.model.Manufacturer;
import com.oneshoppoint.yates.service.ManufacturerService;
import com.oneshoppoint.yates.util.Pagination;
import com.oneshoppoint.yates.util.Paginator;
import com.oneshoppoint.yates.util.RestMessage;
import com.oneshoppoint.yates.util.Status;
import com.oneshoppoint.yates.wrapper.ManufacturerForm;
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


/**
 * The manufacturer api contains endpoints for adding,updating,deleting and accessing manufacturer data
 * @author robinson odhiambo
 * @version 1.0
 * @since 4/9/16.
 */

@RestController
@RequestMapping("/api/manufacturer")
public class ManufacturerController {
    @Autowired
    private ManufacturerService manufacturerService;

    /**
     * Create a new manufacturer
     * @param manufacturerForm This is a wrapper for the new manufacturer
     * @param result This is a BindingResult object that holds the validation result of the manufacturerForm wrapper
     * @return RestMessage Json object that gives a status and a message concerning the operation
     */
    @Secured("ROLE_MANAGE_MANUFACTURERS")
    @RequestMapping(method = RequestMethod.POST)
    public RestMessage<Manufacturer> add (@Valid @RequestBody ManufacturerForm manufacturerForm,BindingResult result) {
        if(result.hasErrors()) {
            return new RestMessage<Manufacturer> (Status.FAILED,"Failed to add new manufacturer ",result.getFieldErrors());
        }

        manufacturerService.save(manufacturerForm);
        return new RestMessage<Manufacturer>(Status.CREATED,"Successfully added a new manufacturer");
    }

    /**
     * List manufacturers with user specified pagination
     * @param page This is an integer used to specify the page
     * @param parts This is an integer used to specify the number of manufacturers to be returned in a particular page
     * @return RestMessage Json object that gives a status and a message concerning the operation
     */
    @Secured({"ROLE_MANAGE_MANUFACTURERS","ROLE_APP"})
    @RequestMapping(method = RequestMethod.GET,params = {"page", "parts"})
    public RestMessage<List<Manufacturer>> get (@RequestParam int page,@RequestParam int parts) {
        Pagination<Manufacturer> pagination = new Paginator<Manufacturer>().paginate(page,manufacturerService.getAll(),parts);
        String prev="",next = "";
        if(pagination.getPrev() != null) {
            prev = "/manufacturer?page="+pagination.getPrev()+"&parts="+parts;
        }
        if(pagination.getNext() != null) {
            next = "/manufacturer?page="+pagination.getNext()+"&parts="+parts;
        }

        return new RestMessage<List<Manufacturer>>(Status.OK,pagination.getList(),prev,next);
    }

    /**
     * List all manufacturers
     * @return RestMessage Json object that gives a status and a message concerning the operation
     */
    @Secured({"ROLE_MANAGE_MANUFACTURERS","ROLE_APP"})
    @RequestMapping(method = RequestMethod.GET)
    public RestMessage<List<Manufacturer>> get () {
        return new RestMessage<List<Manufacturer>>(Status.OK,manufacturerService.getAll());
    }

    /**
     * Get information of a particular manufacturer
     * @param id Integer specifying the id of the manufacturer
     * @return RestMessage Json object that gives a status and a message concerning the operation
     */
    @Secured({"ROLE_MANAGE_MANUFACTURERS","ROLE_APP"})
    @RequestMapping(value="/{id}",method = RequestMethod.GET)
    public RestMessage<Manufacturer> get (@PathVariable Integer id) {
        return new RestMessage<Manufacturer>(Status.OK,manufacturerService.getById(id));
    }

    /**
     * Search for manufacturer(s). This method uses primitive string matching hence regular expressions are not
     * supported
     * @param search String pattern specifying the name of the manufacturer
     * @return RestMessage Json object that gives a status and a message concerning the operation
     */
    @Secured({"ROLE_MANAGE_MANUFACTURERS","ROLE_APP"})
    @RequestMapping(method = RequestMethod.GET,params = {"search"})
    public RestMessage<List<Manufacturer>> search (@PathVariable String search) {
        return new RestMessage<List<Manufacturer>>(Status.OK,manufacturerService.search(search));
    }

    /**
     * Update a manufacturer
     * @param manufacturerForm This is a wrapper for the manufacturer
     * @param result This is a BindingResult object that holds the validation result of the manufacturerForm wrapper
     * @return RestMessage Json object that gives a status and a message concerning the operation
     */
    @RequestMapping(method = RequestMethod.PUT)
    @Secured("ROLE_MANAGE_MANUFACTURERS")
    public RestMessage<Manufacturer> update (@Valid @RequestBody ManufacturerForm manufacturerForm,BindingResult result) {
        if(result.hasErrors()) {
            return new RestMessage<Manufacturer> (Status.FAILED,"Failed to updated the manufacturer ",result.getFieldErrors());
        }

        manufacturerService.update(manufacturerForm);
        return new RestMessage<Manufacturer>(Status.MODIFIED,"Successfully updated the manufacturer");
    }

    /**
     * Delete location(s)
     * @param ids Integer specifying the id(s) of the locations to be deleted
     * @return RestMessage Json object that gives a status and a message concerning the operation
     */
    @RequestMapping(method = RequestMethod.DELETE,params = {"ids"})
    @Secured("ROLE_MANAGE_MANUFACTURERS")
    public RestMessage<List<Manufacturer>> delete (@RequestParam Integer[] ids) {
        for(Integer id :ids) {
            manufacturerService.delete(id);
        }

        return new RestMessage<List<Manufacturer>>(Status.DELETED,"successfully deleted the manufacturer(s)");
    }

    /**
     * This endpoint is usually used in conjunction with either the manufacturer create or update endpoint
     * The sequence is to first upload the image then get the resulting message  which is a string specifying the location of the
     * image(on success), then you submit the manufacturerForm data to either the manufacturer create or update endpoint
     * @param file This is a MultipartFile object that accepts files that are submitted as if they were from a normal html form
     * @return RestMessage Json object that gives a status and a message which is a path to the image (on success) otherwise it is
     * just a string specifying the problem that occurred while uploading the image.
     */
    @RequestMapping(value="/upload",method = RequestMethod.POST)
    @Secured("ROLE_MANAGE_MANUFACTURERS")
    public RestMessage  upload(@RequestParam("file") MultipartFile file) {
        if (!file.isEmpty()) {
            try {
                byte[] bytes = file.getBytes();

                String rootPath = System.getProperty("catalina.home");
                String path = rootPath + File.separator +"yates"+ File.separator +"images" +File.separator + "manufacturers";
                File dir = new File(path);
                if (!dir.exists()) {
                    dir.mkdirs();
                }

                File serverFile = new File(dir.getAbsolutePath()+ File.separator + file.getOriginalFilename());
                BufferedOutputStream stream = new BufferedOutputStream(new FileOutputStream(serverFile));
                stream.write(bytes);
                stream.close();

                return new RestMessage(Status.OK,File.separator + "manufacturers"+File.separator+file.getOriginalFilename());
            } catch (Exception e) {
                return new RestMessage(Status.ERROR,"Failed to upload " + file.getOriginalFilename() + " => " + e.getMessage());
            }
        } else {
            return new RestMessage(Status.FAILED,"Failed to upload " + file.getOriginalFilename()+ " because the file was empty.");
        }
    }
}
