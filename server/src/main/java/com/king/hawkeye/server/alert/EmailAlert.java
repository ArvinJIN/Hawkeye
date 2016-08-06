package com.king.hawkeye.server.alert;

import org.apache.commons.mail.DefaultAuthenticator;
import org.apache.commons.mail.Email;
import org.apache.commons.mail.EmailException;
import org.apache.commons.mail.SimpleEmail;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

/**
 * Created by King on 16/4/23.
 */
public class EmailAlert implements IAlert{

    private static final String SENGER_EMAIL = "";
    private static final String SENDER_PWD = "";
    private static final String ALERT_SUBJECT = "日志监控报警";
    private static final String SENDER_HOST = "smtp.qq.com";
    private Logger logger = LogManager.getLogger(EmailAlert.class);

    private String emailAddress;

    public EmailAlert(String target) {
        this.emailAddress = target;
    }

    public void alert(String message) throws EmailException {
        Email email = new SimpleEmail();
        email.setHostName(SENDER_HOST);
        email.setSmtpPort(465);
        email.setAuthenticator(new DefaultAuthenticator(SENGER_EMAIL, SENDER_PWD));
        email.setSSLOnConnect(true);
        email.setCharset("UTF-8");
        email.setFrom(SENGER_EMAIL);
        email.setSubject(ALERT_SUBJECT);
        email.setMsg(message);
        email.addTo(emailAddress);
        email.send();
    }
}
