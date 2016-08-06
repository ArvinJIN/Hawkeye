package com.king.hawkeye.router.sink;


import com.king.hawkeye.router.channel.AbstractRouterChannel;
import org.apache.commons.mail.DefaultAuthenticator;
import org.apache.commons.mail.Email;
import org.apache.commons.mail.EmailException;
import org.apache.commons.mail.SimpleEmail;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

/**
 * 邮件 报警器
 * Created by King on 16/3/11.
 */
public class EmailSink extends AbstractSink<String> {

    private static final String SENGER_EMAIL = "";
    private static final String SENDER_PWD = "";
    private static final String ALERT_SUBJECT = "日志监控报警";
    private static final String SENDER_HOST = "smtp.qq.com";
    private Logger logger = LogManager.getLogger(EmailSink.class);

    private String emailAddress;

    public EmailSink(String emailAddress) {
        this.emailAddress = emailAddress;
    }

    @Override
    public void sink(String message) {
        try {
            sendEmail(message, emailAddress);
            logger.error("email alert ! message: " + message);
        } catch (EmailException e) {
            logger.error("send email error. email address is {} and message is {}", emailAddress, message, e);
        }
    }

    private void sendEmail(String message, String emailAddress) throws EmailException {
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
