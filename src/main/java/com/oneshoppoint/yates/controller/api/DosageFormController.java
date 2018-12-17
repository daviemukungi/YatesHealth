package com.oneshoppoint.yates.controller.api;

import com.oneshoppoint.yates.model.DosageForm;
import com.oneshoppoint.yates.model.Product;
import com.oneshoppoint.yates.service.DosageFormService;
import com.oneshoppoint.yates.service.ProductService;
import com.oneshoppoint.yates.util.Pagination;
import com.oneshoppoint.yates.util.Paginator;
import com.oneshoppoint.yates.util.RestMessage;
import com.oneshoppoint.yates.util.Status;
import com.oneshoppoint.yates.wrapper.DosageList;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.annotation.Secured;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.Arrays;
import java.util.List;


/**
 * The dosageForm api contains endpoints for adding,updating,deleting and accessing dosageForm data
 * @author robinson odhiambo
 * @version 1.0
 * @since 4/9/16.
 */

@RestController
@RequestMapping("/api/dosage_form")
@Secured("ROLE_MANAGE_INNS")
public class DosageFormController {
    @Autowired
    private DosageFormService dosageFormService;
    @Autowired
    private ProductService productService;

    /**
     * Create a new dosageForm
     * @param dosageList This is a wrapper for the new carrier
     * @param result This is a BindingResult object that holds the validation result of the dosageList wrapper
     * @return RestMessage Json object that gives a status and a message concerning the operation
     */
    @RequestMapping(method = RequestMethod.POST)
    public RestMessage<DosageForm> add (@Valid @RequestBody DosageList dosageList,BindingResult result) {
        if(result.hasErrors()) {
            return new RestMessage<DosageForm> (Status.FAILED,"Failed to add new dosage form ",result.getFieldErrors());
        }

        dosageFormService.save(dosageList);
        return new RestMessage<DosageForm>(Status.CREATED,"Successfully added a new dosage form(s)");
    }

    /**
     * List dosageForms with user specified pagination
     * @param page This is an integer used to specify the page
     * @param parts This is an integer used to specify the number of dosageForms to be returned in a particular page
     * @return RestMessage Json object that gives a status and a message concerning the operation
     */
    @RequestMapping(method = RequestMethod.GET,params = {"page", "parts"})
    public RestMessage<List<DosageForm>> get (@RequestParam int page,@RequestParam int parts) {
        Pagination<DosageForm> pagination = new Paginator<DosageForm>().paginate(page,dosageFormService.getAll(),parts);
        String prev="",next = "";
        if(pagination.getPrev() != null) {
            prev = "/dosage_form?page="+pagination.getPrev()+"&parts="+parts;
        }
        if(pagination.getNext() != null) {
            next = "/dosage_form?page="+pagination.getNext()+"&parts="+parts;
        }

        return new RestMessage<List<DosageForm>>(Status.OK,pagination.getList(),prev,next);
    }

    /**
     * List all dosageForms
     * @return RestMessage Json object that gives a status and a message concerning the operation
     */
    @RequestMapping(method = RequestMethod.GET)
    public RestMessage<List<DosageForm>> get () {
        return new RestMessage<List<DosageForm>>(Status.OK,dosageFormService.getAll());
    }

    /**
     * Get information of a particular dosageForm
     * @param id Integer specifying the id of the dosageForm
     * @return RestMessage Json object that gives a status and a message concerning the operation
     */
    @RequestMapping(params={"id"},method = RequestMethod.GET)
    public RestMessage<DosageForm> get (@RequestParam Integer id) {
        return new RestMessage<DosageForm>(Status.OK,dosageFormService.getById(id));
    }

    /**
     * Get dosage forms by Inn
     * @param innId Integer specifying the id of the Inn
     * @return RestMessage Json object that gives a status and a message concerning the operation
     */
    @Secured("ROLE_MEDIC")
    @RequestMapping(params={"innId"},method = RequestMethod.GET)
    public RestMessage<List<DosageForm>> getByInn (@RequestParam Integer innId) {
        return new RestMessage<List<DosageForm>>(Status.OK,dosageFormService.getByInn(innId));
    }

    /**
     * Get dosage forms by product
     * @param productId Integer specifying the id of the Inn
     * @return RestMessage Json object that gives a status and a message concerning the operation
     */
    @Secured("ROLE_MEDIC")
    @RequestMapping(params={"productId"},method = RequestMethod.GET)
    public RestMessage<List<DosageForm>> getByProduct (@RequestParam Integer productId) {
        Product product = productService.getById(productId);
        return new RestMessage<List<DosageForm>>(Status.OK,dosageFormService.getByInn(product.getInn().getId()));
    }

    /**
     * Update a dosageForm
     * @param dosageList This is a wrapper for the dosageForm
     * @param result This is a BindingResult object that holds the validation result of the dosageList wrapper
     * @return RestMessage Json object that gives a status and a message concerning the operation
     */
    @RequestMapping(method = RequestMethod.PUT)
    public RestMessage<DosageForm> update (@Valid @RequestBody DosageList dosageList,BindingResult result) {
        if(result.hasErrors()) {
            return new RestMessage<DosageForm> (Status.FAILED,"Failed to updated the dosage form ",result.getFieldErrors());
        }

        dosageFormService.update(dosageList);
        return new RestMessage<DosageForm>(Status.MODIFIED,"Successfully updated the dosage forms");
    }

    /**
     * Delete dosageForm(s)
     * @param ids Integer specifying the id(s) of the dosageForms to be deleted
     * @return RestMessage Json object that gives a status and a message concerning the operation
     */
    @RequestMapping(method = RequestMethod.DELETE,params = {"ids"})
    public RestMessage<List<DosageForm>> delete (@RequestParam Integer[] ids) {
        dosageFormService.delete(Arrays.asList(ids));
        return new RestMessage<List<DosageForm>>(Status.DELETED,"successfully deleted the dosageForm(s)");
    }
}
