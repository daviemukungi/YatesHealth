package com.oneshoppoint.yates.controller.publicAccess;

import com.oneshoppoint.yates.model.*;
import com.oneshoppoint.yates.service.CategoryService;
import com.oneshoppoint.yates.service.ProductService;
import com.oneshoppoint.yates.service.SettingsService;
import com.oneshoppoint.yates.util.PublicStorage;
import com.oneshoppoint.yates.wrapper.FeatureMap;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import java.util.*;

/**
 * Created by robinson on 4/27/16.
 */

@Controller
@RequestMapping("/product")
public class PublicProduct {
    @Autowired
    ProductService productService;
    @Autowired
    CategoryService categoryService;
    @Autowired
    PublicStorage publicStorage;
    @Autowired
    SettingsService settingsService;

    @RequestMapping(method = RequestMethod.GET)
    public ModelAndView index () {
        ModelAndView mv = new ModelAndView();
        List<Category> categories = categoryService.getAll();
        mv.addObject("categories",categories);
        mv.addObject("cat",categories.get(0));
        mv.addObject("settings",settingsService.get());
        Set<Product> products = new HashSet<Product>();
        products.addAll(productService.getByCategoryId(categories.get(0).getId()));
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

    @RequestMapping(params = {"manIds"},method = RequestMethod.GET)
    public ModelAndView productsFilteredByManufacturers (@RequestParam Integer[] manIds) {
        ModelAndView mv = new ModelAndView();
        List<Category> categories = categoryService.getAll();
        mv.addObject("categories",categories);
        mv.addObject("cat",categories.get(0));
        mv.addObject("settings",settingsService.get());
        Set<Product> products = new HashSet<Product>();
        List<Product> unfilteredProducts = productService.getByCategoryId(categories.get(0).getId());
        Set<Integer> mans = new TreeSet<Integer>();
        mans.addAll(Arrays.asList(manIds));
        for(Integer id :mans) {
            List<Product> manufacturerProducts = productService.getByManufacturerId(id);
            if(manufacturerProducts != null) {
                products.addAll(manufacturerProducts);
            }
        }
        Set<Manufacturer> manufacturers = new HashSet<Manufacturer>();
        List<FeatureMap> featureMaps =  new ArrayList<FeatureMap>();
        Set<Feature> features = new TreeSet<Feature>();
        for(Product product : unfilteredProducts) {
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
            for(Product product : unfilteredProducts) {
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

        mv.addObject("mans",mans);
        mv.addObject("products",products);
        mv.addObject("manufacturers",manufacturers);
        mv.addObject("featureMaps",featureMaps);
        mv.setViewName("public/index");
        return mv;
    }

    @RequestMapping(params = {"featIds"},method = RequestMethod.GET)
    public ModelAndView productsFilteredByFeatures (@RequestParam Integer[] featIds) {
        ModelAndView mv = new ModelAndView();
        List<Category> categories = categoryService.getAll();
        mv.addObject("categories",categories);
        mv.addObject("cat",categories.get(0));
        mv.addObject("settings",settingsService.get());
        Set<Product> products = new HashSet<Product>();
        Set<Integer> feats = new TreeSet<Integer>();
        feats.addAll(Arrays.asList(featIds));
        for(Integer id :feats) {
            List<Product> featureProducts = productService.getByFeatureId(id);
            if(featureProducts != null) {
                products.addAll(featureProducts);
            }
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

        mv.addObject("feats",feats);
        mv.addObject("products",products);
        mv.addObject("manufacturers",manufacturers);
        mv.addObject("featureMaps",featureMaps);
        mv.setViewName("public/index");
        return mv;
    }

    @RequestMapping(params = {"manIds","featIds"},method = RequestMethod.GET)
    public ModelAndView productsFilteredByBoth (@RequestParam Integer[] manIds,@RequestParam Integer[] featIds) {
        ModelAndView mv = new ModelAndView();
        List<Category> categories = categoryService.getAll();
        mv.addObject("categories",categories);
        mv.addObject("cat",categories.get(0));
        mv.addObject("settings",settingsService.get());
        Set<Product> products = new HashSet<Product>();
        Set<Integer> mans = new TreeSet<Integer>();
        mans.addAll(Arrays.asList(manIds));
        Set<Integer> feats = new TreeSet<Integer>();
        feats.addAll(Arrays.asList(featIds));

        for(Integer id :mans) {
            List<Product> manufacturerProducts = productService.getByManufacturerId(id);
            if(manufacturerProducts != null) {
                products.addAll(manufacturerProducts);
            }
        }
        for(Integer id :feats) {
            List<Product> featureProducts = productService.getByFeatureId(id);
            if(featureProducts != null) {
                products.addAll(featureProducts);
            }
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

        mv.addObject("mans",mans);
        mv.addObject("feats",feats);
        mv.addObject("products",products);
        mv.addObject("manufacturers",manufacturers);
        mv.addObject("featureMaps",featureMaps);
        mv.setViewName("public/index");
        return mv;
    }

    @RequestMapping(params={"cat"},method= RequestMethod.GET)
    public ModelAndView products (@RequestParam Integer cat) {
        ModelAndView mv = new ModelAndView();
        List<Category> categories = categoryService.getAll();
        mv.addObject("categories",categories);
        mv.addObject("cat",categoryService.getById(cat));
        mv.addObject("settings",settingsService.get());
        List<Product> products = productService.getByCategoryId(cat);
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

    @RequestMapping(params={"cat","child"},method= RequestMethod.GET)
    public ModelAndView products (@RequestParam Integer cat,@RequestParam Integer child) {
        ModelAndView mv = new ModelAndView();
        List<Category> categories = categoryService.getAll();
        mv.addObject("categories",categories);
        mv.addObject("cat",categoryService.getById(cat));
        mv.addObject("settings",settingsService.get());
        mv.addObject("child",categoryService.getById(child));
        List<Product> products = productService.getByCategoryId(child);
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

    @RequestMapping(value = "/search",params ={"query"},method = RequestMethod.GET)
    public ModelAndView search (@RequestParam String query) {
        ModelAndView mv = new ModelAndView();
        List<Product> products = productService.search(query);
        mv.addObject("settings",settingsService.get());
        Set<Manufacturer> manufacturers = new HashSet<Manufacturer>();
        List<FeatureMap> featureMaps =  new ArrayList<FeatureMap>();
        Set<Feature> features = new TreeSet<Feature>();
        if(products != null){
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

        }
        mv.addObject("products",products);
        mv.addObject("manufacturers",manufacturers);
        mv.addObject("featureMaps",featureMaps);
        mv.setViewName("public/search");
        return mv;
    }

    @RequestMapping(params ={"uuid"},method = RequestMethod.GET)
    public ModelAndView product (@RequestParam String uuid) {
        ModelAndView mv = new ModelAndView();
        mv.addObject("settings",settingsService.get());
        Product product = productService.getByUUID(uuid);
        mv.addObject("product",product);
        mv.setViewName("public/product");
        return mv;
    }

    @RequestMapping(params ={"id"},method = RequestMethod.GET)
    public ModelAndView product (@RequestParam Integer id) {
        ModelAndView mv = new ModelAndView();
        mv.addObject("settings",settingsService.get());
        Product product = productService.getById(id);
        mv.addObject("product",product);
        mv.setViewName("public/product");
        return mv;
    }

    @RequestMapping(value="/compare",method = RequestMethod.GET)
    public ModelAndView compare () {
        ModelAndView mv = new ModelAndView();
        List<Product> products = new ArrayList<Product>();
        mv.addObject("settings",settingsService.get());
        List<Item> comparisonList = publicStorage.getComparison();
        for(Item item : comparisonList){
            products.add(productService.getByUUID(item.getUUID()));
        }
        List<FeatureMap> featureMaps =  new ArrayList<FeatureMap>();
        Set<Feature> features = new TreeSet<Feature>();
        for(Product product : products) {
            List<FeatureValue> featureValues  = product.getFeatureValues();
            for(FeatureValue featureValue : featureValues) {
                features.add(featureValue.getFeature());
            }
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
        mv.addObject("featureMaps",featureMaps);
        mv.addObject("page","compare");
        mv.addObject("products",products);
        mv.setViewName("public/compare");
        return mv;
    }
}
