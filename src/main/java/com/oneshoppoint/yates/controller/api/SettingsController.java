package com.oneshoppoint.yates.controller.api;

import com.oneshoppoint.yates.service.SettingsService;
import com.oneshoppoint.yates.util.RestMessage;
import com.oneshoppoint.yates.util.Status;
import com.oneshoppoint.yates.wrapper.Settings;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.annotation.Secured;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.Valid;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.util.UUID;


/**
 * The setting api contains endpoints for saving and accessing settings data
 * @author robinson odhiambo
 * @version 1.0
 * @since 4/9/16.
 */

@RestController
@RequestMapping("/api/settings")
@Secured("ROLE_MANAGE_SETTINGS")
public class SettingsController {
    @Autowired
    private SettingsService settingsService;

    /**
     * Add or Update setting
     * @param settings This is a wrapper for the new setting
     * @param result This is a BindingResult object that holds the validation result of the settings wrapper
     * @return RestMessage Json object that gives a status and a message concerning the operation
     */
    @RequestMapping(method = RequestMethod.POST)
    public RestMessage<Settings> save (@Valid @RequestBody Settings settings,BindingResult result) {
        if(result.hasErrors()) {
            return new RestMessage<Settings> (Status.FAILED,"Failed to save settings ",result.getFieldErrors());
        }

        settingsService.save(settings);
        return new RestMessage<Settings>(Status.CREATED,"Successfully saved settings");
    }

    /**
     * Get settings with user specified pagination
     * @return RestMessage Json object that gives a status and a message concerning the operation
     */
    @RequestMapping(method = RequestMethod.GET)
    public RestMessage<Settings> get () {
        return new RestMessage<Settings>(Status.OK,settingsService.get());
    }

    /**
     * This endpoint is usually used in conjunction with the settings save
     * The sequence is to first upload the image then get the resulting message  which is a string specifying the location of the
     * image(on success), then you submit the settings data to  the settings save endpoint
     * @param file This is a MultipartFile object that accepts files that are submitted as if they were from a normal html form
     * @return RestMessage Json object that gives a status and a message which is a path to the image (on success) otherwise it is
     * just a string specifying the problem that occurred while uploading the image.
     */
    @RequestMapping(value="/upload",method = RequestMethod.POST)
    public RestMessage  upload(@RequestParam("file") MultipartFile file) {
        if (!file.isEmpty()) {
            try {
                byte[] bytes = file.getBytes();

                String rootPath = System.getProperty("catalina.home");
                UUID uuid = UUID.randomUUID();
                String path = rootPath + File.separator +"yates"+ File.separator +"images" +File.separator + "logo"+File.separator+ uuid;
                File dir = new File(path);
                if (!dir.exists()) {
                    dir.mkdirs();
                }

                File serverFile = new File(dir.getAbsolutePath()+ File.separator + file.getOriginalFilename());
                BufferedOutputStream stream = new BufferedOutputStream(new FileOutputStream(serverFile));
                stream.write(bytes);
                stream.close();

                return new RestMessage(Status.OK,File.separator + "logo"+File.separator+uuid+File.separator+file.getOriginalFilename());
            } catch (Exception e) {
                return new RestMessage(Status.ERROR,"Failed to upload " + file.getOriginalFilename() + " => " + e.getMessage());
            }
        } else {
            return new RestMessage(Status.FAILED,"Failed to upload " + file.getOriginalFilename()+ " because the file was empty.");
        }
    }
}
