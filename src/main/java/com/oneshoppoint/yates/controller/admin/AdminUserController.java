package com.oneshoppoint.yates.controller.admin;

import com.oneshoppoint.yates.model.Permission;
import com.oneshoppoint.yates.model.User;
import com.oneshoppoint.yates.repository.RetailerDao;
import com.oneshoppoint.yates.service.CarrierService;
import com.oneshoppoint.yates.service.PermissionService;
import com.oneshoppoint.yates.service.RetailerService;
import com.oneshoppoint.yates.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.annotation.Secured;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Davie on 4/9/16.
 */

@Controller
@RequestMapping("/user/view")
@Secured("ROLE_MANAGE_USERS")
public class AdminUserController {
    @Autowired
    private UserService userService;
    @Autowired
    private PermissionService permissionService;
    @Autowired
    private RetailerService retailerService;
    @Autowired
    private CarrierService carrierService;

    @RequestMapping(value="/admin/{type}/table",method = RequestMethod.GET)
    public ModelAndView table (@PathVariable String type) {
        ModelAndView mv = new ModelAndView();
        mv.addObject("type",type);
        mv.setViewName("admin/tables/user");
        return mv;
    }

    @RequestMapping(value="/admin/{type}/form",method = RequestMethod.GET)
    public ModelAndView form (@PathVariable String type) {
        ModelAndView mv = new ModelAndView();
        mv.addObject("users",userService.getAll());
        mv.addObject("permissions",filter(permissionService.getAll()));
        mv.addObject("type",type);
        if(type.equals("affiliates")) {
            mv.addObject("employers",retailerService.getAll());
        }
        mv.setViewName("admin/forms/user");
        return mv;
    }

    @RequestMapping(value="/admin/{type}/{id}/form",method = RequestMethod.GET)
    public ModelAndView form (@PathVariable String type,@PathVariable Integer id) {
        ModelAndView mv = new ModelAndView();
        User user = userService.getById(id);
        mv.addObject("user",user);
        mv.addObject("type",type);
        if(user!= null && user.getAffiliate() != null) {
            if(user.getAffiliate().getRetailer() != null) {
                mv.addObject("employers",retailerService.getAll());
            }
            if(user.getAffiliate().getCarrier() != null) {
                mv.addObject("employers",carrierService.getAll());
            }
        }

        mv.addObject("permissions",filter(permissionService.getAll()));
        mv.addObject("users",userService.getAll());
        mv.setViewName("admin/forms/user");
        return mv;
    }

    private List<Permission> filter(List<Permission> permissions) {
        List<Permission> permissionList = new ArrayList<Permission>();
        for(Permission permission : permissions) {
            if(!permission.getName().equalsIgnoreCase("ROLE_ADMIN") &&
               !permission.getName().equalsIgnoreCase("ROLE_CUSTOMER") &&
               !permission.getName().equalsIgnoreCase("ROLE_AFFILIATE") &&
               !permission.getName().equalsIgnoreCase("ROLE_MEDIC")) {
                permissionList.add(permission);
            }
        }
        return permissionList;
    }
}
