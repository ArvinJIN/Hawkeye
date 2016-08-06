package com.king.hawkeye.server.alert;

import org.apache.commons.mail.EmailException;

/**
 * Created by King on 16/4/23.
 */
public interface IAlert {
    public void alert(String message) throws EmailException;
}
