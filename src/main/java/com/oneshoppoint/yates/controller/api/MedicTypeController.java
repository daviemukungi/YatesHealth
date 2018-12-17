package com.oneshoppoint.yates.controller.api;

import com.oneshoppoint.yates.model.MedicType;
import com.oneshoppoint.yates.service.MedicTypeService;
import com.oneshoppoint.yates.util.Pagination;
import com.oneshoppoint.yates.util.Paginator;
import com.oneshoppoint.yates.util.RestMessage;
import com.oneshoppoint.yates.util.Status;
import com.oneshoppoint.yates.wrapper.MedicTypeForm;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.annotation.Secured;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;


/**
 * The medictype api contains endpoints for adding,updating,deleting and accessing medic type data.
 * A medic type is a category that groups together medical practitioners with the same level of qualifications
 * This is used to restrict the kind of INNs they can prescribe.
 * @author robinson odhiambo
 * @version 1.0
 * @since 4/9/16.
 */


@RestController
@RequestMapping("/api/medictype")
@Secured("ROLE_MANAGE_MEDIC_TYPES")
public class MedicTypeController {
    @Autowired
    private MedicTypeService medicTypeService;

    /**
     * Create a new medicType
     * @param medicTypeForm This is a wrapper for the new medicType
     * @param result This is a BindingResult object that holds the validation result of the medicTypeForm wrapper
     * @return RestMessage Json object that gives a status and a message concerning the operation
     */
    @RequestMapping(method = RequestMethod.POST)
    public RestMessage<MedicType> add (@Valid @RequestBody MedicTypeForm medicTypeForm,BindingResult result) {
        if(result.hasErrors()) {
            return new RestMessage<MedicType> (Status.FAILED,"Failed to updated the medic type ",result.getFieldErrors());
        }

        medicTypeService.save(medicTypeForm);
        return new RestMessage<MedicType>(Status.MODIFIED,"Successfully updated the medic type");
    }

    /**
     * List medicTypes with user specified pagination
     * @param page This is an integer used to specify the page
     * @param parts This is an integer used to specify the number of medicTypes to be returned in a particular page
     * @return RestMessage Json object that gives a status and a message concerning the operation
     */
    @RequestMapping(method = RequestMethod.GET,params = {"page", "parts"})
    public RestMessage<List<MedicType>> get (@RequestParam int page,@RequestParam int parts) {
        Pagination<MedicType> pagination = new Paginator<MedicType>().paginate(page,medicTypeService.getAll(),parts);
        String prev="",next = "";
        if(pagination.getPrev() != null) {
            prev = "/medicType?page="+pagination.getPrev()+"&parts="+parts;
        }
        if(pagination.getNext() != null) {
            next = "/medicType?page="+pagination.getNext()+"&parts="+parts;
        }

        return new RestMessage<List<MedicType>>(Status.OK,pagination.getList(),prev,next);
    }

    /**
     * List all medicTypes
     * @return RestMessage Json object that gives a status and a message concerning the operation
     */
    @RequestMapping(method = RequestMethod.GET)
    public RestMessage<List<MedicType>> get () {
        return new RestMessage<List<MedicType>>(Status.OK,medicTypeService.getAll());
    }

    /**
     * Get information of a particular medicType
     * @param id Integer specifying the id of the medicType
     * @return RestMessage Json object that gives a status and a message concerning the operation
     */
    @RequestMapping(value="/{id}",method = RequestMethod.GET)
    public RestMessage<MedicType> get (@PathVariable Integer id) {
        return new RestMessage<MedicType>(Status.OK,medicTypeService.getById(id));
    }

    /**
     * Search for medicType(s). This method uses primitive string matching hence regular expressions are not
     * supported
     * @param search String pattern specifying the name of the medicType
     * @return RestMessage Json object that gives a status and a message concerning the operation
     */
    @RequestMapping(method = RequestMethod.GET,params = {"search"})
    public RestMessage<List<MedicType>> search (@PathVariable String search) {
        return new RestMessage<List<MedicType>>(Status.OK,medicTypeService.search(search));
    }

    /**
     * Update a medicType
     * @param medicTypeForm This is a wrapper for the medicType
     * @param result This is a BindingResult object that holds the validation result of the medicTypeForm wrapper
     * @return RestMessage Json object that gives a status and a message concerning the operation
     */
    @RequestMapping(method = RequestMethod.PUT)
    public RestMessage<MedicType> update (@Valid @RequestBody MedicTypeForm medicTypeForm,BindingResult result) {
        if(result.hasErrors()) {
            return new RestMessage<MedicType> (Status.FAILED,"Failed to updated the medic type ",result.getFieldErrors());
        }

        medicTypeService.update(medicTypeForm);
        return new RestMessage<MedicType>(Status.MODIFIED,"Successfully updated the medic type");
    }

    /**
     * Delete medicType(s)
     * @param ids Integer specifying the id(s) of the categories to be deleted
     * @return RestMessage Json object that gives a status and a message concerning the operation
     */
    @RequestMapping(method = RequestMethod.DELETE,params = {"ids"})
    public RestMessage<List<MedicType>> delete (@RequestParam Integer[] ids) {
        for(Integer id :ids) {
            medicTypeService.delete(id);
        }

        return new RestMessage<List<MedicType>>(Status.DELETED,"successfully deleted the medic type(s)");
    }
}
