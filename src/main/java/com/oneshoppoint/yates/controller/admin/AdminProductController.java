package com.oneshoppoint.yates.controller.admin;

import com.oneshoppoint.yates.model.Category;
import com.oneshoppoint.yates.service.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.annotation.Secured;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;

/**
 * Created by Davie on 4/9/16.
 */

@Controller
@RequestMapping("/product/view")
@Secured("ROLE_MANAGE_PRODUCTS")
public class AdminProductController {
    @Autowired
    private ProductService productService;
    @Autowired
    private CategoryService categoryService;
    @Autowired
    private ManufacturerService manufacturerService;
    @Autowired
    private InnService innService;
    @Autowired
    private FeatureService featureService;

    @RequestMapping(value="/admin/table",method = RequestMethod.GET)
    public ModelAndView table () {
        ModelAndView mv = new ModelAndView();
        mv.setViewName("admin/tables/product");
        return mv;
    }

    @RequestMapping(value="/admin/form",method = RequestMethod.GET)
    public ModelAndView form () {
        ModelAndView mv = new ModelAndView();
        mv.addObject("products",productService.getAll());
        List<Category> categories = categoryService.getAll();
        if(categories != null) {
            List<Category> categoryList = new ArrayList<Category>();
            for(Category category : categories) {
                categoryList.addAll(flatten(category));
            }

            mv.addObject("categories",categoryList);
        }

        mv.addObject("inns",innService.getAll());
        mv.addObject("features",featureService.getAll());
        mv.addObject("manufacturers",manufacturerService.getAll());
        mv.setViewName("admin/forms/product");
        return mv;
    }

    @RequestMapping(value="/admin/{id}/form",method = RequestMethod.GET)
    public ModelAndView form (@PathVariable Integer id) {
        ModelAndView mv = new ModelAndView();
        List<Category> categories = categoryService.getAll();
        if(categories != null) {
            List<Category> categoryList = new ArrayList<Category>();
            for(Category category : categories) {
                categoryList.addAll(flatten(category));
            }

            mv.addObject("categories",categoryList);
        }
        mv.addObject("product",productService.getById(id));
        mv.addObject("inns",innService.getAll());
        mv.addObject("features",featureService.getAll());
        mv.addObject("manufacturers",manufacturerService.getAll());
        mv.setViewName("admin/forms/product");
        return mv;
    }

    @RequestMapping(value="/admin/{id}/gallery",method = RequestMethod.GET)
    public ModelAndView gallery (@PathVariable Integer id) {
        ModelAndView mv = new ModelAndView();
        mv.addObject("product",productService.getById(id));
        mv.setViewName("admin/forms/productImages");
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
