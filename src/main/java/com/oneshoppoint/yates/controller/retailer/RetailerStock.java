package com.oneshoppoint.yates.controller.retailer;


import com.oneshoppoint.yates.model.*;
import com.oneshoppoint.yates.service.*;
import com.oneshoppoint.yates.util.CurrentUser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.annotation.Secured;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import java.util.*;

/**
 * Created by robinson on 4/18/16.
 */
@Controller
@RequestMapping("/retailer")
@Secured("ROLE_RETAILER")
public class RetailerStock {
    @Autowired
    CurrentUser currentUser;
    @Autowired
    RetailerService retailerService;
    @Autowired
    StockService stockService;
    @Autowired
    CategoryService categoryService;
    @Autowired
    ProductService productService;
    @Autowired
    SettingsService settingsService;

    @RequestMapping(value="/stock",method = RequestMethod.GET)
    public ModelAndView stock () {
        ModelAndView mv = new ModelAndView();
        List<Manufacturer> manufacturers = currentUser.getUser().getAffiliate().getRetailer().getManufacturers();
        if(currentUser.getUser().getAffiliate().getPharmacist() != null && currentUser.getUser().getAffiliate().getPharmacist()) {
            Set<Inn> inns = new HashSet<Inn>();

            for(Manufacturer manufacturer : manufacturers) {
                List<Product> products = productService.getByManufacturerId(manufacturer.getId());
                if(products != null) {
                    for (Product product : products) {
                        inns.add(product.getInn());
                    }
                }
            }
            mv.addObject("inns",inns);
        } else {
            Set<Category> categories = new HashSet<Category>();

            for(Manufacturer manufacturer : manufacturers) {
                List<Product> products = productService.getByManufacturerId(manufacturer.getId());
                if(products != null) {
                    for(Product product : products) {
                        categories.add(product.getCategory());
                    }
                }
            }

            mv.addObject("categories", categories);
        }
        mv.addObject("stocks",stockService.getByRetailer(currentUser.getUser().getAffiliate().getRetailer().getId()));
        mv.addObject("pharmacy",currentUser.getUser().getAffiliate().getPharmacist());
        mv.addObject("retailer",true);
        mv.addObject("settings",settingsService.get());
        mv.setViewName("retailer/stock");
        return mv;
    }

    @RequestMapping(value="/stock/add",method = RequestMethod.GET)
    public ModelAndView stockForm () {
        ModelAndView mv = new ModelAndView();
        mv.addObject("pharmacy",currentUser.getUser().getAffiliate().getPharmacist());
        mv.addObject("retailer",true);
        mv.addObject("settings",settingsService.get());
        mv.setViewName("retailer/forms/stock");
        return mv;
    }

    @RequestMapping(value="/stock/{id}/edit",method = RequestMethod.GET)
    public ModelAndView stockForm (@PathVariable Integer id) {
        ModelAndView mv = new ModelAndView();
        mv.addObject("pharmacy",currentUser.getUser().getAffiliate().getPharmacist());
        mv.addObject("retailer",true);
        mv.addObject("settings",settingsService.get());
        mv.addObject("stock",retailerService.getById(id));
        mv.setViewName("retailer/forms/stock");
        return mv;
    }

    private List<Category> flatten (Category category) {
        List<Category> categoryList  = new ArrayList<Category>();
        if(category != null) {
            Queue<Category> categoryQueue = new LinkedList<Category>();
            categoryQueue.add(category);
            while(!categoryQueue.isEmpty()) {
                Category cat = categoryQueue.remove();

                if(!cat.getChildren().isEmpty()) {
                    categoryQueue.addAll(cat.getChildren());
                }
                categoryList.add(cat);
            }
        }

        return categoryList;
    }
}
