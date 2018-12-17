package com.oneshoppoint.yates.controller.api;

import com.oneshoppoint.yates.model.Category;
import com.oneshoppoint.yates.service.CategoryService;
import com.oneshoppoint.yates.util.Pagination;
import com.oneshoppoint.yates.util.Paginator;
import com.oneshoppoint.yates.util.RestMessage;
import com.oneshoppoint.yates.util.Status;
import com.oneshoppoint.yates.wrapper.CategoryForm;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.annotation.Secured;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.Valid;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;


/**
 * The category api contains endpoints for adding,updating,deleting and accessing category data
 * @author robinson odhiambo
 * @version 1.0
 * @since 4/9/16.
 */

@RestController
@RequestMapping("/api/category")
public class CategoryController{
    @Autowired
    private CategoryService categoryService;

    /**
     * Create a new category
     * @param categoryForm This is a wrapper for the new category
     * @param result This is a BindingResult object that holds the validation result of the categoryForm wrapper
     * @return RestMessage Json object that gives a status and a message concerning the operation
     */
    @Secured("ROLE_MANAGE_CATEGORIES")
    @RequestMapping(method = RequestMethod.POST)
    public RestMessage<Category> add (@Valid @RequestBody CategoryForm categoryForm,BindingResult result) {
        if(result.hasErrors()) {
            return new RestMessage<Category> (Status.FAILED,"Failed to add new category ",result.getFieldErrors());
        }

        categoryService.save(categoryForm);
        return new RestMessage<Category>(Status.CREATED,"Successfully added a new category");
    }

    /**
     * List categories with user specified pagination
     * @param page This is an integer used to specify the page
     * @param parts This is an integer used to specify the number of categories to be returned in a particular page
     * @return RestMessage Json object that gives a status and a message concerning the operation
     */
    @Secured({"ROLE_APP","ROLE_MANAGE_CATEGORIES"})
    @RequestMapping(method = RequestMethod.GET,params = {"page", "parts"})
    public RestMessage<List<Category>> get (@RequestParam int page,@RequestParam int parts) {
        List<Category> categories = categoryService.getAll();
        List<Category> categoryList = new ArrayList<Category>();
        for(Category category : categories) {
            categoryList.addAll(flatten(category));
        }
        Pagination<Category> pagination = new Paginator<Category>().paginate(page,categoryList,parts);
        String prev="",next = "";
        if(pagination.getPrev() != null) {
            prev = "/category?page="+pagination.getPrev()+"&parts="+parts;
        }
        if(pagination.getNext() != null) {
            next = "/category?page="+pagination.getNext()+"&parts="+parts;
        }

        return new RestMessage<List<Category>>(Status.OK,pagination.getList(),prev,next);
    }

    /**
     * List all categories
     * @return RestMessage Json object that gives a status and a message concerning the operation
     */
    @Secured({"ROLE_APP","ROLE_MANAGE_CATEGORIES"})
    @RequestMapping(method = RequestMethod.GET)
    public RestMessage<List<Category>> get () {
        List<Category> categories = categoryService.getAll();
        List<Category> categoryList = new ArrayList<Category>();
        if(categories != null) {
            for(Category category : categories) {
                categoryList.addAll(flatten(category));
            }
        }

        return new RestMessage<List<Category>>(Status.OK,categoryList);
    }

    /**
     * Get the category hierarchy
     * @return RestMessage Json object that gives a status and a message concerning the operation
     */
    @Secured({"ROLE_APP","ROLE_MANAGE_CATEGORIES"})
    @RequestMapping(value="/tree",method = RequestMethod.GET)
    public RestMessage<List<Category>> get (@PathVariable int tree) {
        return new RestMessage<List<Category>>(Status.OK,categoryService.getAll());
    }

    /**
     * Get information of a particular category
     * @param id Integer specifying the id of the category
     * @return RestMessage Json object that gives a status and a message concerning the operation
     */
    @Secured({"ROLE_APP","ROLE_MANAGE_CATEGORIES"})
    @RequestMapping(value="/{id}",method = RequestMethod.GET)
    public RestMessage<Category> get (@PathVariable Integer id) {
        return new RestMessage<Category>(Status.OK,categoryService.getById(id));
    }

    /**
     * Search for category(s). This method uses primitive string matching hence regular expressions are not
     * supported
     * @param search String pattern specifying the name of the category
     * @return RestMessage Json object that gives a status and a message concerning the operation
     */
    @Secured({"ROLE_APP","ROLE_MANAGE_CATEGORIES"})
    @RequestMapping(method = RequestMethod.GET,params = {"search"})
    public RestMessage<List<Category>> search (@RequestParam String search) {
        return new RestMessage<List<Category>>(Status.OK,categoryService.search(search));
    }

    /**
     * Update a category
     * @param categoryForm This is a wrapper for the category
     * @param result This is a BindingResult object that holds the validation result of the categoryForm wrapper
     * @return RestMessage Json object that gives a status and a message concerning the operation
     */
    @RequestMapping(method = RequestMethod.PUT)
    @Secured("ROLE_MANAGE_CATEGORIES")
    public RestMessage<Category> update (@Valid @RequestBody CategoryForm categoryForm,BindingResult result) {
        if(result.hasErrors()) {
            return new RestMessage<Category> (Status.FAILED,"Failed to updated the category ",result.getFieldErrors());
        }

        categoryService.update(categoryForm);
        return new RestMessage<Category>(Status.MODIFIED,"Successfully updated the category");
    }

    /**
     * Delete category(s)
     * @param ids Integer specifying the id(s) of the categories to be deleted
     * @return RestMessage Json object that gives a status and a message concerning the operation
     */
    @RequestMapping(method = RequestMethod.DELETE,params = {"ids"})
    @Secured("ROLE_MANAGE_CATEGORIES")
    public RestMessage<List<Category>> delete (@RequestParam Integer[] ids) {
        for(Integer id :ids) {
            categoryService.delete(id);
        }

        return new RestMessage<List<Category>>(Status.DELETED,"successfully deleted the category(s)");
    }

    /**
     * This endpoint is used in conjunction with either the category create or update endpoint
     * The sequence is to first upload the image then get the resulting message  which is a string specifying the location of the
     * image(on success), then you submit the categoryForm data to either the category create or update endpoint
     * @param file This is a MultipartFile object that accepts files that are submitted as if they were from a normal html form
     * @return RestMessage Json object that gives a status and a message which is a path to the image (on success) otherwise it is
     * just a string specifying the problem that occurred while uploading the image.
     */
    @RequestMapping(value="/upload",method = {RequestMethod.POST,RequestMethod.PUT})
    @Secured("ROLE_MANAGE_CATEGORIES")
    public RestMessage  upload(@RequestParam("file") MultipartFile file) {
        if (!file.isEmpty()) {
            try {
                byte[] bytes = file.getBytes();

                String rootPath = System.getProperty("catalina.home");
                String path = rootPath + File.separator +"yates"+ File.separator +"images" +File.separator + "categories";
                File dir = new File(path);
                if (!dir.exists()) {
                    dir.mkdirs();
                }

                File serverFile = new File(dir.getAbsolutePath()+ File.separator + file.getOriginalFilename());
                BufferedOutputStream stream = new BufferedOutputStream(new FileOutputStream(serverFile));
                stream.write(bytes);
                stream.close();

                return new RestMessage(Status.OK,File.separator + "categories"+File.separator+file.getOriginalFilename());
            } catch (Exception e) {
                return new RestMessage(Status.ERROR,"Failed to upload " + file.getOriginalFilename() + " => " + e.getMessage());
            }
        } else {
            return new RestMessage(Status.FAILED,"Failed to upload " + file.getOriginalFilename()+ " because the file was empty.");
        }
    }

    private List<Category> flatten (Category category) {
        List<Category> categoryList  = new ArrayList<Category>();
        Queue<Category> categoryQueue = new LinkedList<Category>();
        if(category != null) {
            categoryQueue.add(category);
            while(!categoryQueue.isEmpty()) {
                Category cat = categoryQueue.remove();

                if(cat.getChildren()!= null &&!cat.getChildren().isEmpty()) {
                    categoryQueue.addAll(cat.getChildren());
                }
                categoryList.add(cat);
            }
        }

        return categoryList;
    }
}
