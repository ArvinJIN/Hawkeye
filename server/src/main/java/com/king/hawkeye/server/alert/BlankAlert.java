package com.king.hawkeye.server.alert;

import org.apache.commons.mail.EmailException;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

/**
 * Created by King on 16/4/23.
 */
public class BlankAlert implements IAlert {
    private static final Logger LOG = LogManager.getLogger(BlankAlert.class);

    @Override
    public void alert(String message) throws EmailException {
        LOG.info("blnak alert ----- " + message);
    }
}
