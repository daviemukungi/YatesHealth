package com.oneshoppoint.yates.controller.api;

import com.oneshoppoint.yates.model.User;
import com.oneshoppoint.yates.service.UserService;
import com.oneshoppoint.yates.util.Pagination;
import com.oneshoppoint.yates.util.Paginator;
import com.oneshoppoint.yates.util.RestMessage;
import com.oneshoppoint.yates.util.Status;
import com.oneshoppoint.yates.wrapper.UserForm;
import com.oneshoppoint.yates.wrapper.UserPassword;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.annotation.Secured;
import org.springframework.security.crypto.codec.Base64;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.Valid;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;


/**
 * The user api contains endpoints for adding,updating,deleting and accessing user data
 * @author robinson odhiambo
 * @version 1.0
 * @since 4/9/16.
 */
@RestController
@RequestMapping("/api/user")
public class UserController {
    @Autowired
    private UserService userService;

    /**
     * Create a new user
     * @param userForm This is a wrapper for the new user
     * @param result This is a BindingResult object that holds the validation result of the userForm wrapper
     * @return RestMessage Json object that gives a status and a message concerning the operation
     */
    @Secured({"ROLE_MANAGE_USERS","ROLE_APP"})
    @RequestMapping(method = RequestMethod.POST)
    public RestMessage<User> add (@Valid @RequestBody UserForm userForm,BindingResult result) {
        if(result.hasErrors()) {
            return new RestMessage<User> (Status.FAILED,"Failed to add new user ",result.getFieldErrors());
        }

        userService.save(userForm);
        return new RestMessage<User>(Status.CREATED,"Successfully added a new user");
    }

    /**
     * Update an existing user password
     * @param userPassword This is a wrapper for the new user
     * @param result This is a BindingResult object that holds the validation result of the userPassword wrapper
     * @return RestMessage Json object that gives a status and a message concerning the operation
     */
    @Secured({"ROLE_ADMIN","ROLE_MEDIC","ROLE_CARRIER","ROLE_CUSTOMER","ROLE_RETAILER"})
    @RequestMapping(value="/password",method = RequestMethod.PATCH)
    public RestMessage<User> changePassword (@Valid @RequestBody UserPassword userPassword,BindingResult result) {
        if(result.hasErrors()) {
            return new RestMessage<User> (Status.FAILED,"Failed to update your password ",result.getFieldErrors());
        }

        userService.updatePassword(userPassword);
        return new RestMessage<User>(Status.MODIFIED,"Successfully updated your password");
    }

    /**
     * List users with specific pagination
     * @param type This is a string specifying the type of user . The accepted types are <strong>staff</strong>,
     *             <strong>customers</strong>,<strong>medics</strong>,<strong>affiliates</strong>
     * @param page This is an integer used to specify the page
     * @param parts This is an integer used to specify the number of users to be returned in a particular page
     * @return RestMessage Json object that gives a status and a message concerning the operation
     */
    @Secured("ROLE_MANAGE_USERS")
    @RequestMapping(method = RequestMethod.GET,params = {"type","page", "parts"})
    public RestMessage<List<User>> get (@RequestParam String type,@RequestParam int page,@RequestParam int parts) {
        Pagination<User> pagination = new Paginator<User>().paginate(page,userService.getAll(),parts);
        String prev="",next = "";
        if(pagination.getPrev() != null) {
            prev = "/user?page="+pagination.getPrev()+"&parts="+parts;
        }
        if(pagination.getNext() != null) {
            next = "/user?page="+pagination.getNext()+"&parts="+parts;
        }
        if(pagination.getList() != null && (type.equalsIgnoreCase("staff") || type.equalsIgnoreCase("customers"))) {
            return new RestMessage<List<User>>(Status.OK,filter(pagination.getList(), type),prev,next);
        }

        return new RestMessage<List<User>>(Status.OK,pagination.getList(),prev,next);
    }

    /**
     * List all users of a specific type.
     * @param type This is a string specifying the type of user . The accepted types are <strong>staff</strong>,
     *             <strong>customers</strong>,<strong>medics</strong>,<strong>affiliates</strong>
     * @return RestMessage Json object that gives a status and a message concerning the operation
     */
    @Secured("ROLE_MANAGE_USERS")
    @RequestMapping(method = RequestMethod.GET,params = {"type"})
    public RestMessage<List<User>> get (@RequestParam String type) {
        List<User> users = userService.getAll();
        if(users != null) {
            return new RestMessage<List<User>>(Status.OK, filter(users, type));
        }

        return new RestMessage<List<User>>(Status.OK,userService.getAll());
    }

    /**
     * Get information of a particular user
     * @param id Integer specifying the id of the user
     * @return RestMessage Json object that gives a status and a message concerning the operation
     */
    @Secured({"ROLE_MANAGE_USERS","ROLE_APP"})
    @RequestMapping(params="{id}",method = RequestMethod.GET)
    public RestMessage<User> get (@RequestParam Integer id) {
        return new RestMessage<User>(Status.OK,userService.getById(id));
    }

    /**
     * Search for user(s). This method uses primitive string matching hence regular expressions are not
     * supported
     * @param search String pattern specifying the name of the user
     * @return RestMessage Json object that gives a status and a message concerning the operation
     */
    @Secured("ROLE_MANAGE_USERS")
    @RequestMapping(method = RequestMethod.GET,params = {"search"})
    public RestMessage<List<User>> search (@RequestParam String search) {
        return new RestMessage<List<User>>(Status.OK,userService.search(search));
    }

    /**
     * Update a user
     * @param userForm This is a wrapper for the user
     * @param result This is a BindingResult object that holds the validation result of the userForm wrapper
     * @return RestMessage Json object that gives a status and a message concerning the operation
     */
    @Secured({"ROLE_MANAGE_USERS","ROLE_CARRIER","ROLE_CUSTOMER","ROLE_RETAILER"})
    @RequestMapping(method = RequestMethod.PUT)
    public RestMessage<User> update (@Valid @RequestBody UserForm userForm,BindingResult result) {
        if(result.hasErrors()) {
            return new RestMessage<User> (Status.FAILED,"Failed to updated the user ",result.getFieldErrors());
        }

        userService.update(userForm);
        return new RestMessage<User>(Status.MODIFIED,"Successfully updated the user");
    }

    /**
     * Delete user(s)
     * @param ids Integer specifying the id(s) of the user(s) to be deleted
     * @return RestMessage Json object that gives a status and a message concerning the operation
     */
    @Secured("ROLE_MANAGE_USERS")
    @RequestMapping(method = RequestMethod.DELETE,params = {"ids"})
    public RestMessage<List<User>> delete (@RequestParam Integer[] ids) {
        for(Integer id :ids) {
            userService.delete(id);
        }

        return new RestMessage<List<User>>(Status.DELETED,"successfully deleted the user(s)");
    }

    /**
     * This endpoint is used in conjunction with either the user create or update endpoint
     * The sequence is to first upload the image then get the resulting message  which is a string specifying the location of the
     * image(on success), then you submit the userForm data to either the user create or update endpoint
     * @param file This is a MultipartFile object that accepts files that are submitted as if they were from a normal html form
     * @return RestMessage Json object that gives a status and a message which is a path to the image (on success) otherwise it is
     * just a string specifying the problem that occurred while uploading the image.
     */
    @Secured("ROLE_MANAGE_USERS")
    @RequestMapping(value="/{userType}/upload",method = RequestMethod.POST)
    public RestMessage  upload(@PathVariable String userType,@RequestParam("file") MultipartFile file) {
        if (!file.isEmpty()) {
            try {
                byte[] encodedBytes = Base64.encode(UUID.randomUUID().toString().getBytes());
                String randomStr= new String(encodedBytes, Charset.forName("UTF-8"));

                byte[] bytes = file.getBytes();

                String rootPath = System.getProperty("catalina.home");
                String path = rootPath + File.separator +"yates"+ File.separator +"images" +File.separator + userType +File.separator+randomStr;
                File dir = new File(path);
                if (!dir.exists()) {
                    dir.mkdirs();
                }

                File serverFile = new File(dir.getAbsolutePath()+ File.separator + file.getOriginalFilename());
                BufferedOutputStream stream = new BufferedOutputStream(new FileOutputStream(serverFile));
                stream.write(bytes);
                stream.close();

                return new RestMessage(Status.OK,File.separator +userType+File.separator+randomStr+File.separator+file.getOriginalFilename());
            } catch (Exception e) {
                return new RestMessage(Status.ERROR,"Failed to upload " + file.getOriginalFilename() + " => " + e.getMessage());
            }
        } else {
            return new RestMessage(Status.FAILED,"Failed to upload " + file.getOriginalFilename()+ " because the file was empty.");
        }
    }

    private List<User> filter (List<User> users,String type) {
        List<User> userList = new ArrayList<User>();
        for(User user : users) {
            if(type.equalsIgnoreCase("staff") && user.getCustomer() == null && user.getAffiliate() == null && user.getMedic() == null) {
                userList.add(user);
            } else if (type.equalsIgnoreCase("customers") && user.getCustomer() != null) {
                userList.add(user);
            } else if (type.equalsIgnoreCase("medics") && user.getMedic() != null) {
                userList.add(user);
            } else if (type.equalsIgnoreCase("affiliates") && user.getAffiliate() != null) {
                userList.add(user);
            }
        }

        return userList;
    }
}
