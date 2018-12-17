package com.oneshoppoint.yates.controller.api;

import com.oneshoppoint.yates.model.Image;
import com.oneshoppoint.yates.model.Inn;
import com.oneshoppoint.yates.model.Product;
import com.oneshoppoint.yates.service.ProductService;
import com.oneshoppoint.yates.util.*;
import com.oneshoppoint.yates.wrapper.ProductForm;
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
import java.util.List;
import java.util.Set;


/**
 * The product api contains endpoints for adding,updating,deleting and accessing product data
 * @author robinson odhiambo
 * @version 1.0
 * @since 4/9/16.
 */
@RestController
@RequestMapping("/api/product")
@Secured("ROLE_APP")
public class ProductController {
    @Autowired
    private ProductService productService;
    @Autowired
    private CurrentUser currentUser;

    /**
     * Create a new product
     * @param productForm This is a wrapper for the new product
     * @param result This is a BindingResult object that holds the validation result of the productForm wrapper
     * @return RestMessage Json object that gives a status and a message concerning the operation
     */
    @Secured("ROLE_MANAGE_PRODUCTS")
    @RequestMapping(method = RequestMethod.POST)
    public RestMessage<Product> add (@Valid @RequestBody ProductForm productForm,BindingResult result) {
        if(result.hasErrors()) {
            return new RestMessage<Product> (Status.FAILED,"Failed to add new product ",result.getFieldErrors());
        }

        productService.save(productForm);
        return new RestMessage<Product>(Status.CREATED,"Successfully added a new product");
    }

    /**
     * List products with user specified pagination
     * @param page This is an integer used to specify the page
     * @param parts This is an integer used to specify the number of products to be returned in a particular page
     * @return RestMessage Json object that gives a status and a message concerning the operation
     */
    @Secured({"ROLE_MANAGE_PRODUCTS","ROLE_APP"})
    @RequestMapping(method = RequestMethod.GET,params = {"page", "parts"})
    public RestMessage<List<Product>> get (@RequestParam int page,@RequestParam int parts) {
        Pagination<Product> pagination = new Paginator<Product>().paginate(page,productService.getAll(),parts);
        String prev="",next = "";
        if(pagination.getPrev() != null) {
            prev = "/product?page="+pagination.getPrev()+"&parts="+parts;
        }
        if(pagination.getNext() != null) {
            next = "/product?page="+pagination.getNext()+"&parts="+parts;
        }

        return new RestMessage<List<Product>>(Status.OK,pagination.getList(),prev,next);
    }

    /**
     * List all products
     * @return RestMessage Json object that gives a status and a message concerning the operation
     */
    @Secured({"ROLE_MANAGE_PRODUCTS","ROLE_APP"})
    @RequestMapping(method = RequestMethod.GET)
    public RestMessage<List<Product>> get () {
        return new RestMessage<List<Product>>(Status.OK,productService.getAll());
    }

    /**
     * List products by category
     * @param categoryId This is an integer used to specify the category of interest
     * @param page This is an integer used to specify the page
     * @param parts This is an integer used to specify the number of products to be returned in a particular page
     * @return RestMessage Json object that gives a status and a message concerning the operation
     */
    @Secured({"ROLE_MANAGE_PRODUCTS","ROLE_APP"})
    @RequestMapping(method = RequestMethod.GET,params = {"categoryId","page", "parts"})
    public RestMessage<List<Product>> get (@RequestParam int categoryId,@RequestParam int page,@RequestParam int parts) {
        Pagination<Product> pagination = new Paginator<Product>().paginate(page,productService.getByCategoryId(categoryId),parts);
        String prev="",next = "";
        if(pagination.getPrev() != null) {
            prev = "/product?page="+pagination.getPrev()+"&parts="+parts;
        }
        if(pagination.getNext() != null) {
            next = "/product?page="+pagination.getNext()+"&parts="+parts;
        }

        return new RestMessage<List<Product>>(Status.OK,pagination.getList(),prev,next);
    }

    /**
     * List all product of a given category
     *  @param categoryId This is an integer used to specify the category of interest
     * @return RestMessage Json object that gives a status and a message concerning the operation
     */
    @Secured({"ROLE_MANAGE_PRODUCTS","ROLE_APP"})
    @RequestMapping(method = RequestMethod.GET,params = {"categoryId"})
    public RestMessage<List<Product>> get (@RequestParam int categoryId) {
        return new RestMessage<List<Product>>(Status.OK,productService.getByCategoryId(categoryId));
    }

    /**
     * Get information of a particular product
     * @param id Integer specifying the id of the product
     * @return RestMessage Json object that gives a status and a message concerning the operation
     */
    @Secured({"ROLE_MANAGE_PRODUCTS","ROLE_APP"})
    @RequestMapping(params={"id"},method = RequestMethod.GET)
    public RestMessage<Product> get (@RequestParam Integer id) {
        return new RestMessage<Product>(Status.OK,productService.getById(id));
    }

    /**
     * List all allowed inns by a particular doctor
     * @return RestMessage Json object that gives a status and a message concerning the operation
     */
    @Secured("ROLE_MEDIC")
    @RequestMapping(method = RequestMethod.GET, params = "medic")
    public RestMessage<List<Product>> getByMedic () {
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
        return new RestMessage<List<Product>>(Status.OK,products);
    }

    /**
     * Search for product(s). This method uses primitive string matching hence regular expressions are not
     * supported
     * @param search String pattern specifying the name of the product
     * @return RestMessage Json object that gives a status and a message concerning the operation
     */
    @Secured({"ROLE_MANAGE_PRODUCTS","ROLE_APP"})
    @RequestMapping(method = RequestMethod.GET,params = {"search"})
    public RestMessage<List<Product>> search (@RequestParam String search) {
        return new RestMessage<List<Product>>(Status.OK,productService.search(search));
    }

    /**
     * Update a product
     * @param productForm This is a wrapper for the product
     * @param result This is a BindingResult object that holds the validation result of the productForm wrapper
     * @return RestMessage Json object that gives a status and a message concerning the operation
     */
    @RequestMapping(method = RequestMethod.PUT)
    @Secured("ROLE_MANAGE_PRODUCTS")
    public RestMessage<Product> update (@Valid @RequestBody ProductForm productForm,BindingResult result) {
        if(result.hasErrors()) {
            return new RestMessage<Product> (Status.FAILED,"Failed to updated the product ",result.getFieldErrors());
        }

        productService.update(productForm);
        return new RestMessage<Product>(Status.MODIFIED,"Successfully updated the product");
    }

    /**
     * Delete product(s)
     * @param ids Integer specifying the id(s) of the categories to be deleted
     * @return RestMessage Json object that gives a status and a message concerning the operation
     */
    @RequestMapping(method = RequestMethod.DELETE,params = {"ids"})
    @Secured("ROLE_MANAGE_PRODUCTS")
    public RestMessage<List<Product>> delete (@RequestParam Integer[] ids) {
        for(Integer id :ids) {
            productService.delete(id);
        }

        return new RestMessage<List<Product>>(Status.DELETED,"successfully deleted the product(s)");
    }

    /**
     * This endpoint is usually used in conjunction with either the product create or update endpoint
     * The sequence is to first upload the image then get the resulting message  which is a string specifying the location of the
     * image(on success), then you submit the productForm data to either the product create or update endpoint
     * @param file This is a MultipartFile object that accepts files that are submitted as if they were from a normal html form
     * @return RestMessage Json object that gives a status and a message which is a path to the image (on success) otherwise it is
     * just a string specifying the problem that occurred while uploading the image.
     */
    @RequestMapping(value="/upload",method = RequestMethod.POST)
    @Secured("ROLE_MANAGE_PRODUCTS")
    public RestMessage  upload(@RequestParam("file") MultipartFile file) {
        if (!file.isEmpty()) {
            try {
                byte[] bytes = file.getBytes();

                String rootPath = System.getProperty("catalina.home");
                String path = rootPath + File.separator +"yates"+ File.separator +"images" +File.separator + "products";
                File dir = new File(path);
                if (!dir.exists()) {
                    dir.mkdirs();
                }

                File serverFile = new File(dir.getAbsolutePath()+ File.separator + file.getOriginalFilename());
                BufferedOutputStream stream = new BufferedOutputStream(new FileOutputStream(serverFile));
                stream.write(bytes);
                stream.close();

                return new RestMessage(Status.OK,File.separator + "products"+File.separator+file.getOriginalFilename());
            } catch (Exception e) {
                return new RestMessage(Status.ERROR,"Failed to upload " + file.getOriginalFilename() + " => " + e.getMessage());
            }
        } else {
            return new RestMessage(Status.FAILED,"Failed to upload " + file.getOriginalFilename()+ " because the file was empty.");
        }
    }

    /**
     * This endpoint is used to upload secondary images of a product which are at most four.
     * @param file This is a MultipartFile object that accepts files that are submitted as if they were from a normal html form
     * @return RestMessage Json object that gives a status and a message which is just a string specifying the status of the upload.
     */
    @RequestMapping(value="/{id}/gallery",method = RequestMethod.POST)
    @Secured("ROLE_MANAGE_PRODUCTS")
    public RestMessage  upload(@PathVariable Integer id,@RequestParam("file") MultipartFile file) {
        if (!file.isEmpty()) {
            try {
                byte[] bytes = file.getBytes();

                String rootPath = System.getProperty("catalina.home");
                Product product = productService.getById(id);
                String path = rootPath + File.separator +"yates"+ File.separator +"images" +File.separator + "products"+File.separator + "gallery"+File.separator +product.getUUID();
                File dir = new File(path);
                if (!dir.exists()) {
                    dir.mkdirs();
                }

                File serverFile = new File(dir.getAbsolutePath()+ File.separator + file.getOriginalFilename());
                BufferedOutputStream stream = new BufferedOutputStream(new FileOutputStream(serverFile));
                stream.write(bytes);
                stream.close();
                Image img = new Image();
                img.setPath(File.separator + "products"+File.separator + "gallery"+File.separator +product.getUUID()+File.separator+file.getOriginalFilename());
                productService.addImage(product.getId(),img);
                return new RestMessage(Status.OK,"Successfully updated gallery with : "+file.getOriginalFilename());
            } catch (Exception e) {
                return new RestMessage(Status.ERROR,"Failed to upload " + file.getOriginalFilename() + " => " + e.getMessage());
            }
        } else {
            return new RestMessage(Status.FAILED,"Failed to upload " + file.getOriginalFilename()+ " because the file was empty.");
        }
    }
}
