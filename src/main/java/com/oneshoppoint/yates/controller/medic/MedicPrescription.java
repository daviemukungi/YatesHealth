package com.oneshoppoint.yates.controller.medic;


import com.oneshoppoint.yates.model.Inn;
import com.oneshoppoint.yates.model.Prescription;
import com.oneshoppoint.yates.model.Product;
import com.oneshoppoint.yates.service.*;
import com.oneshoppoint.yates.util.CurrentUser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.annotation.Secured;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

/**
 * Created by robinson on 4/20/16.
 */

@Controller
@RequestMapping("/medic/prescription")
@Secured("ROLE_MEDIC")
public class MedicPrescription {
    @Autowired
    CurrentUser currentUser;
    @Autowired
    PrescriptionService prescriptionService;
    @Autowired
    PatientService patientService;
    @Autowired
    ProductService productService;
    @Autowired
    SettingsService settingsService;
    @Autowired
    DosageFormService dosageFormService;

    @RequestMapping(params = {"patientId"},method = RequestMethod.GET)
    public ModelAndView index (@RequestParam Integer patientId) {
        ModelAndView mv = new ModelAndView();
        mv.addObject("medic",true);
        mv.addObject("patient",patientService.getById(patientId));
        mv.addObject("prescriptions",prescriptionService.getByPatient(patientId));
        mv.addObject("settings",settingsService.get());
        mv.setViewName("medic/prescription");
        return mv;
    }

    @RequestMapping(value = "/add",params = {"patientId"},method = RequestMethod.GET)
    public ModelAndView add (@RequestParam Integer patientId) {
        ModelAndView mv = new ModelAndView();
        mv.addObject("medic",true);
        mv.addObject("inns",currentUser.getUser().getMedic().getMedicType().getAllowedINNs());
        mv.addObject("settings",settingsService.get());
        mv.addObject("patient",patientService.getById(patientId));
        mv.setViewName("medic/forms/prescription");
        return mv;
    }

    @RequestMapping(value="/{id}/edit",params = {"patientId"},method = RequestMethod.GET)
    public ModelAndView edit (@PathVariable Integer id,@RequestParam Integer patientId) {
        ModelAndView mv = new ModelAndView();
        mv.addObject("medic",true);
        Prescription prescription = prescriptionService.getById(id);
        if(prescription != null) {
            mv.addObject("prescription",prescription);
            mv.addObject("settings",settingsService.get());
            mv.addObject("dosageForms",dosageFormService.getByInn(prescription.getMedic().getId()));
            mv.addObject("inns",currentUser.getUser().getMedic().getMedicType().getAllowedINNs());
            Set<Inn> inns = currentUser.getUser().getMedic().getMedicType().getAllowedINNs();
            List<Product> products  = new ArrayList<Product>();
            for(Inn inn : inns) {
                List<Product> productList = productService.getByInnId(inn.getId());
                if(productList != null) {
                    for (Product product : productList) {
                        products.add(product);
                    }
                }
            }
            mv.addObject("products",products);
            mv.addObject("patient",patientService.getById(patientId));
        }

        mv.setViewName("medic/forms/prescription");
        return mv;
    }

    @RequestMapping(value="/{id}/info",method = RequestMethod.GET)
    public ModelAndView info (@PathVariable Integer id) {
        ModelAndView mv = new ModelAndView();
        mv.addObject("medic",true);
        mv.addObject("settings",settingsService.get());
        mv.addObject("prescription",prescriptionService.getById(id));
        mv.setViewName("common/prescription");
        return mv;
    }
}
