package com.oneshoppoint.yates.cron;

import com.oneshoppoint.yates.model.MedicType;
import com.oneshoppoint.yates.model.User;
import com.oneshoppoint.yates.repository.MedicTypeDao;
import com.oneshoppoint.yates.service.UserService;
import com.oneshoppoint.yates.util.Email;
import com.oneshoppoint.yates.util.RestException;
import com.oneshoppoint.yates.util.Status;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.security.crypto.codec.Base64;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.nio.charset.Charset;
import java.sql.Timestamp;
import java.util.*;

/**
 * Created by robinson on 6/5/16.
 */
@Component
@Transactional
public class RegulatorNotifier {
    @Autowired
    private UserService userService;
    @Autowired
    private MedicTypeDao medicTypeDao;

    private static final Logger logger = LoggerFactory.getLogger(Email.class);

    @Scheduled(cron = "0 0 6 * * MON-FRI")
    public void sendEmail () {
        List<User> users = userService.getPendingMedic();
        if (users != null) {
            Set<MedicType> medicTypes = new HashSet<MedicType>();
            for (User user : users) {
                medicTypes.add(user.getMedic().getMedicType());
            }

            for (MedicType medicType : medicTypes) {
                byte[] encodedBytes = Base64.encode(UUID.randomUUID().toString().getBytes());
                medicType.setToken(new String(encodedBytes, Charset.forName("UTF-8")));
                medicType.setTokenTime(new Timestamp(Calendar.getInstance().getTimeInMillis()));

                //send email
                String message = "Good Morning\n";
                message+=users.size()+" new applications for the medic program at one shop point have been submitted";
                message+="\n==================================================================================\n";
                message+="Use the following link to approve or disapprove them.\n";
                message+="https://www.oneshoppoint.com/regulator/medics?token="+medicType.getToken();
                try {
                    Email.send("MEDICAL PRACTITIONER CONFIRMATION : ONE SHOP POINT",medicType.getAddress().getEmail(), message);
                } catch (Exception e) {
                    logger.error("Email error :- ",e);
                    throw new RestException(Status.ERROR,e.getMessage());
                }

                medicTypeDao.update(medicType);

            }

        }
    }
}
