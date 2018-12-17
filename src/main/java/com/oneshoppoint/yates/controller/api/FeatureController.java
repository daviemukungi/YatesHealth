package com.oneshoppoint.yates.controller.api;

import com.oneshoppoint.yates.model.Feature;
import com.oneshoppoint.yates.service.FeatureService;
import com.oneshoppoint.yates.util.Pagination;
import com.oneshoppoint.yates.util.Paginator;
import com.oneshoppoint.yates.util.RestMessage;
import com.oneshoppoint.yates.util.Status;
import com.oneshoppoint.yates.wrapper.FeatureList;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.annotation.Secured;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.Arrays;
import java.util.List;


/**
 * The feature api contains endpoints for adding,updating,deleting and accessing product features data
 * @author robinson odhiambo
 * @version 1.0
 * @since 4/9/16.
 */

@RestController
@RequestMapping("/api/feature")
@Secured("ROLE_MANAGE_FEATURES")
public class FeatureController {
    @Autowired
    private FeatureService featureService;

    /**
     * Create a new feature
     * @param featureList This is a wrapper for the new carrier
     * @param result This is a BindingResult object that holds the validation result of the featureList wrapper
     * @return RestMessage Json object that gives a status and a message concerning the operation
     */
    @RequestMapping(method = RequestMethod.POST)
    public RestMessage<Feature> add (@Valid @RequestBody FeatureList featureList,BindingResult result) {
        if(result.hasErrors()) {
            return new RestMessage<Feature> (Status.FAILED,"Failed to add new feature ",result.getFieldErrors());
        }

        featureService.save(featureList);
        return new RestMessage<Feature>(Status.CREATED,"Successfully added a new feature");
    }

    /**
     * List features with user specified pagination
     * @param page This is an integer used to specify the page
     * @param parts This is an integer used to specify the number of features to be returned in a particular page
     * @return RestMessage Json object that gives a status and a message concerning the operation
     */
    @RequestMapping(method = RequestMethod.GET,params = {"page", "parts"})
    public RestMessage<List<Feature>> get (@RequestParam int page,@RequestParam int parts) {
        Pagination<Feature> pagination = new Paginator<Feature>().paginate(page,featureService.getAll(),parts);
        String prev="",next = "";
        if(pagination.getPrev() != null) {
            prev = "/feature?page="+pagination.getPrev()+"&parts="+parts;
        }
        if(pagination.getNext() != null) {
            next = "/feature?page="+pagination.getNext()+"&parts="+parts;
        }

        return new RestMessage<List<Feature>>(Status.OK,pagination.getList(),prev,next);
    }

    /**
     * List all features
     * @return RestMessage Json object that gives a status and a message concerning the operation
     */
    @RequestMapping(method = RequestMethod.GET)
    public RestMessage<List<Feature>> get () {
        return new RestMessage<List<Feature>>(Status.OK,featureService.getAll());
    }

    /**
     * Get information of a particular feature
     * @param id Integer specifying the id of the feature
     * @return RestMessage Json object that gives a status and a message concerning the operation
     */
    @RequestMapping(params="{id}",method = RequestMethod.GET)
    public RestMessage<Feature> get (@RequestParam Integer id) {
        return new RestMessage<Feature>(Status.OK,featureService.getById(id));
    }

    /**
     * Update a feature
     * @param featureList This is a wrapper for the feature
     * @param result This is a BindingResult object that holds the validation result of the featureList wrapper
     * @return RestMessage Json object that gives a status and a message concerning the operation
     */
    @RequestMapping(method = RequestMethod.PUT)
    public RestMessage<Feature> update (@Valid @RequestBody FeatureList featureList,BindingResult result) {
        if(result.hasErrors()) {
            return new RestMessage<Feature> (Status.FAILED,"Failed to updated the feature ",result.getFieldErrors());
        }

        featureService.update(featureList);
        return new RestMessage<Feature>(Status.MODIFIED,"Successfully updated the feature");
    }

    /**
     * Delete feature(s)
     * @param ids Integer specifying the id(s) of the features to be deleted
     * @return RestMessage Json object that gives a status and a message concerning the operation
     */
    @RequestMapping(method = RequestMethod.DELETE,params = {"ids"})
    public RestMessage<List<Feature>> delete (@RequestParam Integer[] ids) {
        featureService.delete(Arrays.asList(ids));
        return new RestMessage<List<Feature>>(Status.DELETED,"successfully deleted the feature(s)");
    }
}
