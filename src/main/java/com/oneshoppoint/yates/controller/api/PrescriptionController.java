package com.oneshoppoint.yates.controller.api;

import com.oneshoppoint.yates.model.Prescription;
import com.oneshoppoint.yates.service.PrescriptionService;
import com.oneshoppoint.yates.util.Pagination;
import com.oneshoppoint.yates.util.Paginator;
import com.oneshoppoint.yates.util.RestMessage;
import com.oneshoppoint.yates.util.Status;
import com.oneshoppoint.yates.wrapper.PrescriptionForm;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.annotation.Secured;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;


/**
 * The prescription api contains endpoints for adding,updating,deleting and accessing prescription data
 * @author robinson odhiambo
 * @version 1.0
 * @since 4/9/16.
 */

@RestController
@RequestMapping("/api/prescription")
@Secured("ROLE_MEDIC")
public class PrescriptionController {
    @Autowired
    private PrescriptionService prescriptionService;

    /**
     * Create a new prescription
     * @param prescriptionForm This is a wrapper for the new prescription
     * @param result This is a BindingResult object that holds the validation result of the prescriptionForm wrapper
     * @return RestMessage Json object that gives a status and a message concerning the operation
     */
    @RequestMapping(method = RequestMethod.POST)
    public RestMessage<Prescription> add (@Valid @RequestBody PrescriptionForm prescriptionForm,BindingResult result) {
        if(result.hasErrors()) {
            return new RestMessage<Prescription> (Status.FAILED,"Failed to add new prescription ",result.getFieldErrors());
        }

        prescriptionService.save(prescriptionForm);
        return new RestMessage<Prescription>(Status.CREATED,"Successfully added a new prescription");
    }

    /**
     * Update a prescription
     * @param prescriptionForm This is a wrapper for the prescription
     * @param result This is a BindingResult object that holds the validation result of the prescriptionForm wrapper
     * @return RestMessage Json object that gives a status and a message concerning the operation
     */
    @RequestMapping(method = RequestMethod.PUT)
    public RestMessage<Prescription> update (@Valid @RequestBody PrescriptionForm prescriptionForm,BindingResult result) {
        if(result.hasErrors()) {
            return new RestMessage<Prescription> (Status.FAILED,"Failed to update the prescription ",result.getFieldErrors());
        }

        prescriptionService.update(prescriptionForm);
        return new RestMessage<Prescription>(Status.MODIFIED,"Successfully updated the prescription");
    }

    /**
     * send a prescription
     * @param id Integer specifying the id of the prescription to be sent
     * @return RestMessage Json object that gives a status and a message concerning the operation
     */
    @RequestMapping(params = {"id"},method = RequestMethod.PATCH)
    public RestMessage<Prescription> send (@RequestParam Integer id) {
        prescriptionService.send(id);
        return new RestMessage<Prescription>(Status.MODIFIED,"Successfully sent the prescription");
    }

    /**
     * send a prescription
     * @param id Integer specifying the id of the prescription to be sent
     * @return RestMessage Json object that gives a status and a message concerning the operation
     */
    @RequestMapping(value="/mobile/send",params = {"id"},method = RequestMethod.PUT)
    public RestMessage<Prescription> sendMobile (@RequestParam Integer id) {
        prescriptionService.send(id);
        return new RestMessage<Prescription>(Status.MODIFIED,"Successfully sent the prescription");
    }

    /**
     * send a prescription
     * @param id Integer specifying the id of the prescription to be sent
     * @return RestMessage Json object that gives a status and a message concerning the operation
     */
    @RequestMapping(value="/dispense",params = {"id"},method = RequestMethod.PATCH)
    public RestMessage<Prescription> dispense (@RequestParam Integer id) {
        prescriptionService.dispense(id);
        return new RestMessage<Prescription>(Status.MODIFIED,"Successfully dispensed the prescription");
    }

    /**
     * List prescriptions with user specified pagination
     * @param page This is an integer used to specify the page
     * @param parts This is an integer used to specify the number of prescriptions to be returned in a particular page
     * @return RestMessage Json object that gives a status and a message concerning the operation
     */
    @RequestMapping(method = RequestMethod.GET,params = {"page", "parts"})
    public RestMessage<List<Prescription>> get (@RequestParam int page,@RequestParam int parts) {
        Pagination<Prescription> pagination = new Paginator<Prescription>().paginate(page,prescriptionService.getAll(),parts);
        String prev="",next = "";
        if(pagination.getPrev() != null) {
            prev = "/prescription?page="+pagination.getPrev()+"&parts="+parts;
        }
        if(pagination.getNext() != null) {
            next = "/prescription?page="+pagination.getNext()+"&parts="+parts;
        }

        return new RestMessage<List<Prescription>>(Status.OK,pagination.getList(),prev,next);
    }

    /**
     * List all prescriptions
     * @return RestMessage Json object that gives a status and a message concerning the operation
     */
    @RequestMapping(method = RequestMethod.GET)
    public RestMessage<List<Prescription>> get () {
        return new RestMessage<List<Prescription>>(Status.OK,prescriptionService.getAll());
    }

    /**
     * List prescriptions by patientId
     * @param patientId Integer specifying the id of the patient
     * @return RestMessage Json object that gives a status and a message concerning the operation
     */
    @RequestMapping(method = RequestMethod.GET,params = {"patientId"})
    public RestMessage<List<Prescription>> getByPatient (@RequestParam Integer patientId) {
        return new RestMessage<List<Prescription>>(Status.OK,prescriptionService.getByPatient(patientId));
    }

    /**
     * Get information of a particular prescription
     * @param id Integer specifying the id of the prescription
     * @return RestMessage Json object that gives a status and a message concerning the operation
     */
    @RequestMapping(method = RequestMethod.GET,params = {"id"})
    public RestMessage<Prescription> get (@RequestParam Integer id) {
        return new RestMessage<Prescription>(Status.OK,prescriptionService.getById(id));
    }

    /**
     * Get information of a particular prescription
     * @param code String specifying the id of the prescription
     * @return RestMessage Json object that gives a status and a message concerning the operation
     */
    @RequestMapping(method = RequestMethod.GET,params = {"code"})
    public RestMessage<Prescription> get (@RequestParam String code) {
        return new RestMessage<Prescription>(Status.OK,prescriptionService.getByCode(code));
    }

    /**
     * Search for prescription(s). This method uses primitive string matching hence regular expressions are not
     * supported
     * @param search String pattern specifying the name of the prescription
     * @return RestMessage Json object that gives a status and a message concerning the operation
     */
    @RequestMapping(method = RequestMethod.GET,params = {"search"})
    public RestMessage<List<Prescription>> search (@PathVariable String search) {
        return new RestMessage<List<Prescription>>(Status.OK,prescriptionService.search(search));
    }

    /**
     * Delete prescription(s)
     * @param ids Integer specifying the id(s) of the prescriptions to be deleted
     * @return RestMessage Json object that gives a status and a message concerning the operation
     */
    @RequestMapping(method = RequestMethod.DELETE,params = {"ids"})
    public RestMessage<List<Prescription>> delete (@RequestParam Integer[] ids) {
        for(Integer id :ids) {
            prescriptionService.delete(id);
        }

        return new RestMessage<List<Prescription>>(Status.DELETED,"successfully deleted the prescription(s)");
    }
}
