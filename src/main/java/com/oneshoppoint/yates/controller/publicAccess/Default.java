package com.oneshoppoint.yates.controller.publicAccess;


import com.oneshoppoint.yates.model.*;
import com.oneshoppoint.yates.service.CategoryService;
import com.oneshoppoint.yates.service.CheckoutService;
import com.oneshoppoint.yates.service.ProductService;
import com.oneshoppoint.yates.service.SettingsService;
import com.oneshoppoint.yates.util.CurrentUser;
import com.oneshoppoint.yates.util.PublicStorage;
import com.oneshoppoint.yates.wrapper.FeatureMap;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import java.util.*;


/**
 * Created by robinson on 4/8/16.
 */
@Controller
public class Default {
    @Autowired
    CategoryService categoryService;
    @Autowired
    ProductService productService;
    @Autowired
    PublicStorage publicStorage;
    @Autowired
    CheckoutService checkoutService;
    @Autowired
    CurrentUser currentUser;
    @Autowired
    SettingsService settingsService;

    @RequestMapping(value = "/",method = RequestMethod.GET)
    public ModelAndView index () {
        ModelAndView mv = new ModelAndView();
        List<Category> categories = categoryService.getAll();
        mv.addObject("categories",categories);
        mv.addObject("settings",settingsService.get());
        Set<Product> products = new HashSet<Product>();
        if(categories !=null) {
            mv.addObject("cat",categories.get(0));
            products.addAll(productService.getByCategoryId(categories.get(0).getId()));
        }

        Set<Manufacturer> manufacturers = new HashSet<Manufacturer>();
        List<FeatureMap> featureMaps =  new ArrayList<FeatureMap>();
        Set<Feature> features = new TreeSet<Feature>();
        for(Product product : products) {
            List<FeatureValue> featureValues  = product.getFeatureValues();
            for(FeatureValue featureValue : featureValues) {
                features.add(featureValue.getFeature());
            }
            manufacturers.add(product.getManufacturer());
        }
        for (Feature feature : features) {
            Set<FeatureValue> featureValueSet = new TreeSet<FeatureValue>();
            FeatureMap featureMap = new FeatureMap();
            featureMap.setFeature(feature);
            for(Product product : products) {
                List<FeatureValue> featureValues  = product.getFeatureValues();
                for(FeatureValue featureValue : featureValues) {
                    if(feature.getId() == featureValue.getFeature().getId()) {
                        featureValueSet.add(featureValue);
                    }
                }
            }
            featureMap.setFeatureValues(featureValueSet);
            featureMaps.add(featureMap);
        }
        mv.addObject("products",products);
        mv.addObject("manufacturers",manufacturers);
        mv.addObject("featureMaps",featureMaps);
        mv.setViewName("public/index");
        return mv;
    }

    @RequestMapping(value="/customer/register",method = RequestMethod.GET)
    public ModelAndView register () {
        ModelAndView mv = new ModelAndView();
        mv.addObject("settings",settingsService.get());
        mv.setViewName("public/register");
        return mv;
    }

    @RequestMapping(value="/checkout",params={"retailerId"},method= RequestMethod.GET)
    public ModelAndView checkout (@RequestParam Integer retailerId) {
        ModelAndView mv = new ModelAndView();
        mv.addObject("retailerId",retailerId);
        mv.addObject("settings",settingsService.get());
        User user = currentUser.getUser();
        if(user != null) {
            Set<Permission> permissions = user.getPermissions();
            for (Permission permission : permissions) {
                if(permission.getName().equals("ROLE_CUSTOMER")) {
                    mv.addObject("user",user);
                    break;
                }
            }
        }
        mv.addObject("retailerBasket",checkoutService.getBasketByRetailerId(retailerId,publicStorage));
        mv.setViewName("public/checkout");
        return mv;
    }

    @RequestMapping(value="/legal/{type}/")
    public ModelAndView legal (@PathVariable String type) {
        ModelAndView mv = new ModelAndView();
        mv.addObject("settings",settingsService.get());
        mv.addObject("type",type);
        mv.setViewName("public/legal");
        return mv;
    }

    @RequestMapping(value="/login")
    public ModelAndView login () {
        ModelAndView mv = new ModelAndView();
        mv.addObject("settings",settingsService.get());
        mv.setViewName("public/login");
        return mv;
    }

    @RequestMapping(value="/login",params = {"login_error"})
    public ModelAndView login (@RequestParam String login_error) {
        ModelAndView mv = new ModelAndView();
        mv.addObject("settings",settingsService.get());
        mv.addObject("loginFailed",true);
        mv.setViewName("public/login");
        return mv;
    }

    @RequestMapping(value="/login/recover")
    public ModelAndView recoverPassword () {
        ModelAndView mv = new ModelAndView();
        mv.addObject("settings",settingsService.get());
        mv.setViewName("public/recover");
        return mv;
    }

    @RequestMapping(value="/error")
    public ModelAndView generalError () {
        ModelAndView mv = new ModelAndView();
        mv.addObject("settings",settingsService.get());
        mv.addObject("message","Oops! Something went wrong. Rest assured our engineers are working to solve the problem");
        mv.addObject("errorCode","Server Error");
        mv.setViewName("public/error");
        return mv;
    }

    @RequestMapping(value="/error/not_found")
    public ModelAndView notFoundError () {
        ModelAndView mv = new ModelAndView();
        mv.addObject("settings",settingsService.get());
        mv.addObject("message","We are sorry - this page is not here anymore");
        mv.addObject("errorCode","Error 404 - Page not found");
        mv.setViewName("public/error");
        return mv;
    }

    @RequestMapping(value="/error/forbidden")
    public ModelAndView forbiddenError () {
        ModelAndView mv = new ModelAndView();
        mv.addObject("settings",settingsService.get());
        mv.addObject("message","You are not allowed access to this part of one shop point");
        mv.addObject("errorCode","Error 403 - Forbidden");
        mv.setViewName("public/error");
        return mv;
    }

}
