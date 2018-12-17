package com.oneshoppoint.yates.controller.api;

import com.oneshoppoint.yates.model.Inn;
import com.oneshoppoint.yates.service.InnService;
import com.oneshoppoint.yates.util.*;
import com.oneshoppoint.yates.wrapper.InnForm;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.annotation.Secured;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.Arrays;
import java.util.List;
import java.util.Set;


/**
 * The inn api contains endpoints for adding,updating,deleting and accessing inn data
 * @author robinson odhiambo
 * @version 1.0
 * @since 4/9/16.
 */

@RestController
@RequestMapping("/api/inn")
@Secured("ROLE_MANAGE_INNS")
public class InnController {
    @Autowired
    private InnService innService;
    @Autowired
    private CurrentUser currentUser;

    /**
     * Create a new inn
     * @param innForm This is a wrapper for the new inn
     * @param result This is a BindingResult object that holds the validation result of the innForm wrapper
     * @return RestMessage Json object that gives a status and a message concerning the operation
     */
    @RequestMapping(method = RequestMethod.POST)
    public RestMessage<Inn> add (@Valid @RequestBody InnForm innForm,BindingResult result) {
        if(result.hasErrors()) {
            return new RestMessage<Inn> (Status.FAILED,"Failed to add new inn ",result.getFieldErrors());
        }

        innService.save(innForm);
        return new RestMessage<Inn>(Status.CREATED,"Successfully added a new inn");
    }

    /**
     * List inns with user specified pagination
     * @param page This is an integer used to specify the page
     * @param parts This is an integer used to specify the number of inns to be returned in a particular page
     * @return RestMessage Json object that gives a status and a message concerning the operation
     */
    @RequestMapping(method = RequestMethod.GET,params = {"page", "parts"})
    public RestMessage<List<Inn>> get (@RequestParam int page,@RequestParam int parts) {
        Pagination<Inn> pagination = new Paginator<Inn>().paginate(page,innService.getAll(),parts);
        String prev="",next = "";
        if(pagination.getPrev() != null) {
            prev = "/inn?page="+pagination.getPrev()+"&parts="+parts;
        }
        if(pagination.getNext() != null) {
            next = "/inn?page="+pagination.getNext()+"&parts="+parts;
        }

        return new RestMessage<List<Inn>>(Status.OK,pagination.getList(),prev,next);
    }

    /**
     * List all inns
     * @return RestMessage Json object that gives a status and a message concerning the operation
     */
    @RequestMapping(method = RequestMethod.GET)
    public RestMessage<List<Inn>> get () {
        return new RestMessage<List<Inn>>(Status.OK,innService.getAll());
    }

    /**
     * List all allowed inns by a particular doctor
     * @return RestMessage Json object that gives a status and a message concerning the operation
     */
    @Secured("ROLE_MEDIC")
    @RequestMapping(method = RequestMethod.GET, params = "medic")
    public RestMessage<Set<Inn>> getByMedic () {
        return new RestMessage<Set<Inn>>(Status.OK,currentUser.getUser().getMedic().getMedicType().getAllowedINNs());
    }

    /**
     * Get information of a particular inn
     * @param id Integer specifying the id of the inn
     * @return RestMessage Json object that gives a status and a message concerning the operation
     */
    @RequestMapping(params="{id}",method = RequestMethod.GET)
    public RestMessage<Inn> get (@RequestParam Integer id) {
        return new RestMessage<Inn>(Status.OK,innService.getById(id));
    }

    /**
     * Update a inn
     * @param innForm This is a wrapper for the inn
     * @param result This is a BindingResult object that holds the validation result of the innForm wrapper
     * @return RestMessage Json object that gives a status and a message concerning the operation
     */
    @RequestMapping(method = RequestMethod.PUT)
    public RestMessage<Inn> update (@Valid @RequestBody InnForm innForm,BindingResult result) {
        if(result.hasErrors()) {
            return new RestMessage<Inn> (Status.FAILED,"Failed to updated the inn ",result.getFieldErrors());
        }

        innService.update(innForm);
        return new RestMessage<Inn>(Status.MODIFIED,"Successfully updated the inn");
    }

    /**
     * Delete inn(s)
     * @param ids Integer specifying the id(s) of the inns to be deleted
     * @return RestMessage Json object that gives a status and a message concerning the operation
     */
    @RequestMapping(method = RequestMethod.DELETE,params = {"ids"})
    public RestMessage<List<Inn>> delete (@RequestParam Integer[] ids) {
        innService.delete(Arrays.asList(ids));
        return new RestMessage<List<Inn>>(Status.DELETED,"successfully deleted the inn(s)");
    }
}
