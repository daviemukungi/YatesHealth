package com.oneshoppoint.yates.service.impl;

import com.oneshoppoint.yates.service.SettingsService;
import com.oneshoppoint.yates.wrapper.Settings;
import org.springframework.stereotype.Service;

import java.io.*;

/**
 * Created by robinson on 5/20/16.
 */
@Service
public class SettingsServiceImpl implements SettingsService {
    public void save (Settings settings) {
        String path = System.getProperty("catalina.home")+File.separator+"yates";
        File dir = new File(path);
        if (!dir.exists()) {
            dir.mkdirs();
        }

        File settingsFile = new File(dir.getAbsolutePath()+ File.separator + "settings.properties");
        try {
            BufferedOutputStream stream = new BufferedOutputStream(new FileOutputStream(settingsFile));
            if(settings != null) {
                stream.write(settings.toString().getBytes());
                stream.close();

                if(settings.getAboutUs() != null) {
                    stream = new BufferedOutputStream(new FileOutputStream(new File(dir.getAbsolutePath()+ File.separator + "about.us")));
                    stream.write(settings.getAboutUs().getBytes());
                    stream.close();
                }

                if(settings.getLegalPublic() != null) {
                    stream = new BufferedOutputStream(new FileOutputStream(new File(dir.getAbsolutePath()+ File.separator + "public.legal")));
                    stream.write(settings.getLegalPublic().getBytes());
                    stream.close();
                }

                if(settings.getLegalRetailer() != null) {
                    stream = new BufferedOutputStream(new FileOutputStream(new File(dir.getAbsolutePath()+ File.separator + "retailer.legal")));
                    stream.write(settings.getLegalRetailer().getBytes());
                    stream.close();
                }

                if(settings.getLegalMedic() != null) {
                    stream = new BufferedOutputStream(new FileOutputStream(new File(dir.getAbsolutePath()+ File.separator + "medic.legal")));
                    stream.write(settings.getLegalMedic().getBytes());
                    stream.close();
                }

                if(settings.getLegalCarrier() != null) {
                    stream = new BufferedOutputStream(new FileOutputStream(new File(dir.getAbsolutePath()+ File.separator + "carrier.legal")));
                    stream.write(settings.getLegalCarrier().getBytes());
                    stream.close();
                }
            }

        } catch (IOException ioE) {
            ioE.printStackTrace();
        }

    }

    public Settings get () {
        Settings settings = new Settings();
        String rootPath = System.getProperty("catalina.home")+File.separator+"yates"+ File.separator;
        File settingsFile = new File(rootPath + "settings.properties");
        if (settingsFile.exists()) {
            try {
                BufferedReader br = new BufferedReader(new FileReader(settingsFile));
                String line;
                while ((line = br.readLine()) != null) {
                    String setting[] = line.split("=");
                    if(setting[0].trim().equalsIgnoreCase("logo")) {
                        settings.setLogo(setting[1].trim());
                    } else if(setting[0].trim().equalsIgnoreCase("branding")) {
                        settings.setBranding(setting[1].trim());
                    } else if(setting[0].trim().equalsIgnoreCase("company")) {
                        settings.setCompany(setting[1].trim());
                    } else if(setting[0].trim().equalsIgnoreCase("phone")) {
                        settings.setPhone(setting[1].trim());
                    } else if(setting[0].trim().equalsIgnoreCase("street")) {
                        settings.setStreet(setting[1].trim());
                    } else if(setting[0].trim().equalsIgnoreCase("residence")) {
                        settings.setResidence(setting[1].trim());
                    } else if(setting[0].trim().equalsIgnoreCase("location")) {
                        settings.setLocation(setting[1].trim());
                    } else if(setting[0].trim().equalsIgnoreCase("country")) {
                        settings.setCountry(setting[1].trim());
                    } else if(setting[0].trim().equalsIgnoreCase("facebook")) {
                        settings.setFacebook(setting[1].trim());
                    } else if(setting[0].trim().equalsIgnoreCase("twitter")) {
                        settings.setTwitter(setting[1].trim());
                    } else if(setting[0].trim().equalsIgnoreCase("google_plus")) {
                        settings.setGooglePlus(setting[1].trim());
                    } else if(setting[0].trim().equalsIgnoreCase("email")) {
                        settings.setEmail(setting[1].trim());
                    }

                }
                br.close();

                //read about us
                br = new BufferedReader(new FileReader(new File(rootPath + "about.us")));
                StringBuilder builder = new StringBuilder();
                line ="";
                while ((line = br.readLine()) != null) {
                    builder.append(line);
                }
                settings.setAboutUs(builder.toString());
                br.close();

                //read legalPublic
                br = new BufferedReader(new FileReader(new File(rootPath + "public.legal")));
                builder = new StringBuilder();
                line ="";
                while ((line = br.readLine()) != null) {
                    builder.append(line);
                }
                settings.setLegalPublic(builder.toString());
                br.close();

                //read legalRetailer
                br = new BufferedReader(new FileReader(new File(rootPath + "retailer.legal")));
                builder = new StringBuilder();
                line ="";
                while ((line = br.readLine()) != null) {
                    builder.append(line);
                }
                settings.setLegalRetailer(builder.toString());
                br.close();

                //read legalMedic
                br = new BufferedReader(new FileReader(new File(rootPath + "medic.legal")));
                builder = new StringBuilder();
                line ="";
                while ((line = br.readLine()) != null) {
                    builder.append(line);
                }
                settings.setLegalMedic(builder.toString());
                br.close();

                //read legalCarrier
                br = new BufferedReader(new FileReader(new File(rootPath + "carrier.legal")));
                builder = new StringBuilder();
                line ="";
                while ((line = br.readLine()) != null) {
                    builder.append(line);
                }
                settings.setLegalCarrier(builder.toString());
                br.close();
            } catch (IOException ioE) {
                ioE.printStackTrace();
            }
        }


        return settings;
    }

}
