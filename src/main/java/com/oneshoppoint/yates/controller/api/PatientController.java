package com.oneshoppoint.yates.controller.api;

import com.oneshoppoint.yates.model.Patient;
import com.oneshoppoint.yates.service.PatientService;
import com.oneshoppoint.yates.util.Pagination;
import com.oneshoppoint.yates.util.Paginator;
import com.oneshoppoint.yates.util.RestMessage;
import com.oneshoppoint.yates.util.Status;
import com.oneshoppoint.yates.wrapper.PatientForm;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.annotation.Secured;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;


/**
 * The patient api contains endpoints for adding,updating,deleting and accessing patient data
 * @author robinson odhiambo
 * @version 1.0
 * @since 4/9/16.
 */

@RestController
@RequestMapping("/api/patient")
@Secured("ROLE_MEDIC")
public class PatientController {
    @Autowired
    private PatientService patientService;

    /**
     * Create a new patient
     * @param patientForm This is a wrapper for the new patient
     * @param result This is a BindingResult object that holds the validation result of the patientForm wrapper
     * @return RestMessage Json object that gives a status and a message concerning the operation
     */
    @RequestMapping(method = RequestMethod.POST)
    public RestMessage<Patient> add (@Valid @RequestBody PatientForm patientForm,BindingResult result) {
        if(result.hasErrors()) {
            return new RestMessage<Patient> (Status.FAILED,"Failed to add new patient ",result.getFieldErrors());
        }

        patientService.save(patientForm);
        return new RestMessage<Patient>(Status.CREATED,"Successfully added a new patient");
    }

    /**
     * Update a patient
     * @param patientForm This is a wrapper for the patient
     * @param result This is a BindingResult object that holds the validation result of the patientForm wrapper
     * @return RestMessage Json object that gives a status and a message concerning the operation
     */
    @RequestMapping(method = RequestMethod.PUT)
    public RestMessage<Patient> update (@Valid @RequestBody PatientForm patientForm,BindingResult result) {
        if(result.hasErrors()) {
            return new RestMessage<Patient> (Status.FAILED,"Failed to update the patient ",result.getFieldErrors());
        }

        patientService.update(patientForm);
        return new RestMessage<Patient>(Status.MODIFIED,"Successfully updated the patient");
    }

    /**
     * List patients with user specified pagination
     * @param page This is an integer used to specify the page
     * @param parts This is an integer used to specify the number of patients to be returned in a particular page
     * @return RestMessage Json object that gives a status and a message concerning the operation
     */
    @Secured("ROLE_MEDIC")
    @RequestMapping(method = RequestMethod.GET,params = {"page", "parts"})
    public RestMessage<List<Patient>> get (@RequestParam int page,@RequestParam int parts) {
        Pagination<Patient> pagination = new Paginator<Patient>().paginate(page,patientService.getAll(),parts);
        String prev="",next = "";
        if(pagination.getPrev() != null) {
            prev = "/patient?page="+pagination.getPrev()+"&parts="+parts;
        }
        if(pagination.getNext() != null) {
            next = "/patient?page="+pagination.getNext()+"&parts="+parts;
        }

        return new RestMessage<List<Patient>>(Status.OK,pagination.getList(),prev,next);
    }

    /**
     * List all patients
     * @return RestMessage Json object that gives a status and a message concerning the operation
     */
    @RequestMapping(method = RequestMethod.GET)
    public RestMessage<List<Patient>> get () {
        return new RestMessage<List<Patient>>(Status.OK,patientService.getAll());
    }

    /**
     * Get information of a particular patient
     * @param id Integer specifying the id of the patient
     * @return RestMessage Json object that gives a status and a message concerning the operation
     */
    @RequestMapping(method = RequestMethod.GET,params = {"id"})
    public RestMessage<Patient> get (@RequestParam Integer id) {
        return new RestMessage<Patient>(Status.OK,patientService.getById(id));
    }

    /**
     * Search for patient(s). This method uses primitive string matching hence regular expressions are not
     * supported
     * @param search String pattern specifying the name of the patient
     * @return RestMessage Json object that gives a status and a message concerning the operation
     */
    @RequestMapping(method = RequestMethod.GET,params = {"search"})
    public RestMessage<List<Patient>> search (@PathVariable String search) {
        return new RestMessage<List<Patient>>(Status.OK,patientService.search(search));
    }

    /**
     * Delete patient(s)
     * @param ids Integer specifying the id(s) of the categories to be deleted
     * @return RestMessage Json object that gives a status and a message concerning the operation
     */
    @RequestMapping(method = RequestMethod.DELETE,params = {"ids"})
    public RestMessage<List<Patient>> delete (@RequestParam Integer[] ids) {
        for(Integer id :ids) {
            patientService.delete(id);
        }

        return new RestMessage<List<Patient>>(Status.DELETED,"successfully deleted the patient(s)");
    }
}
