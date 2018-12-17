package com.oneshoppoint.yates.util;

import com.oneshoppoint.yates.model.User;
import com.oneshoppoint.yates.repository.UserDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;

/**
 * Created by robinson on 4/8/16.
 */
public class CurrentUser {
    @Autowired
    private UserDao userDao;
    private String email;


    public User getUser() {
        //to allow unit and integration testing
        if (email != null) {
            return userDao.getByEmail(email);
        }

        SecurityContext context = SecurityContextHolder.getContext();
        Authentication auth = context.getAuthentication();
        Object obj  =  auth.getPrincipal();
        UserDetails userDetails = null;

        if(obj instanceof UserDetails) {
            userDetails = (UserDetails) obj;
        } else {
            return null;
        }

        return userDao.getByEmail(userDetails.getUsername());

    }
}
