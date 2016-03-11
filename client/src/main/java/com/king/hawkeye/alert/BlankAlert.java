package com.king.hawkeye.alert;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

/**
 * Created by King on 16/3/11.
 */
public class BlankAlert implements ILogAlert {
    private static final Logger logger = LogManager.getLogger(BlankAlert.class);

    @Override
    public void alert(String message) {
        System.out.println("alert: " + message);
    }
}
