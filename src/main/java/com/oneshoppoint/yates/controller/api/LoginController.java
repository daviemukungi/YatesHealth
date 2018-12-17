package com.oneshoppoint.yates.controller.api;

import com.oneshoppoint.yates.model.User;
import com.oneshoppoint.yates.util.RestMessage;
import com.oneshoppoint.yates.util.Status;
import org.springframework.security.access.annotation.Secured;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * Created by robinson on 6/7/16.
 */
@RestController
@RequestMapping("/api/login")
public class LoginController {
    @Secured("ROLE_MEDIC")
    @RequestMapping("/medic")
    public RestMessage<User> medic () {
        return new RestMessage<User>(Status.OK,"successfully logged in");
    }

    @Secured("ROLE_CUSTOMER")
    @RequestMapping("/customer")
    public RestMessage<User> customer () {
        return new RestMessage<User>(Status.OK,"successfully logged in");
    }
}
