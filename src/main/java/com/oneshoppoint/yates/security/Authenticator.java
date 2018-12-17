package com.oneshoppoint.yates.security;

import com.oneshoppoint.yates.model.MedicType;
import com.oneshoppoint.yates.repository.MedicTypeDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Calendar;

/**
 * Created by robinson on 6/4/16.
 */
@Service("authenticator")
public class Authenticator {
    @Autowired
    MedicTypeDao medicTypeDao;

    public boolean checkRegulatorTokenAndAuthenticate(Object token) {
        if (token instanceof String) {
            String tk = (String) token;
            MedicType medicType = medicTypeDao.getByToken(tk);
            if(medicType != null) {
                long tokenTime = medicType.getTokenTime().getTime();
                long now = Calendar.getInstance().getTimeInMillis();
                if (((now - tokenTime)/(1000*60*60*24)) <= 7) {
                    return true;
                }
            }
        }

        return false;
    }
}
