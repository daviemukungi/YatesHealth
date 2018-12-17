package com.oneshoppoint.yates.controller.tokenTask;

import com.oneshoppoint.yates.service.SettingsService;
import com.oneshoppoint.yates.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;


/**
 * Created by Davie on 6/4/16.
 */
@Controller
@RequestMapping("/regulator")
public class MedicConfirmController {
    @Autowired
    UserService userService;
    @Autowired
    SettingsService settingsService;

    @PreAuthorize("@authenticator.checkRegulatorTokenAndAuthenticate(#token)")
    @RequestMapping(value = "/medics",params = {"token"},method = RequestMethod.GET)
    public ModelAndView medics (@RequestParam String token) {
        ModelAndView mv = new ModelAndView();
        mv.addObject("settings",settingsService.get());
        mv.addObject("users", userService.getByRegulatorToken(token));
        mv.addObject("token",token);
        mv.setViewName("/token/confirmation");
        return mv;
    }

    @PreAuthorize("@authenticator.checkRegulatorTokenAndAuthenticate(#token)")
    @RequestMapping(value = "/medics",params = {"userId","status","token"},method = RequestMethod.GET)
    public String confirm (@RequestParam Integer userId,@RequestParam String status,@RequestParam String token) {
        userService.confirm(userId,status);
        return "redirect:/regulator/medics?token="+token;
    }
}
