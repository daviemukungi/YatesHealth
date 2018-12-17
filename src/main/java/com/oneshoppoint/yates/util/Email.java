package com.oneshoppoint.yates.util;


import org.apache.commons.mail.DefaultAuthenticator;
import org.apache.commons.mail.SimpleEmail;

/**
 * Created by robinson on 2/25/16.
 */
public class Email {
    private static final Boolean DEBUG = false;
    private static final String HOST_NAME = "smtp.gmail.com";
    private static final String FROM = "excelpipelinetool@gmail.com";

    public static void send(String subject,String recipientEmail,String message) throws Exception {
        SimpleEmail email = new SimpleEmail();
        email.setSmtpPort(587);
        email.setAuthenticator(new DefaultAuthenticator("excelpipelinetool@gmail.com", "bootcamp2016"));
        email.setDebug(DEBUG);
        email.setSSLOnConnect(true);
        email.setHostName(HOST_NAME);
        email.setFrom(FROM);
        email.setSubject(subject);
        email.setMsg(message);
        email.addTo(recipientEmail);
        email.send();

    }
}
