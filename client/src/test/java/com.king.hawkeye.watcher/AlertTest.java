package com.king.hawkeye.watcher;

import com.king.hawkeye.alert.EmailAlert;
import com.king.hawkeye.alert.ILogAlert;
import org.junit.Test;

/**
 * Created by King on 16/3/11.
 */
public class AlertTest {

    @Test
    public void emailAlertTest() {
        ILogAlert alert = new EmailAlert("jinleihao@58ganji.com");
        alert.alert("test alert...");
    }
}
