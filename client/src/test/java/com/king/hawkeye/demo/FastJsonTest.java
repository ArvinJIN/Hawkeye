package com.king.hawkeye.demo;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import org.junit.Before;
import org.junit.Test;

import java.io.*;

/**
 * Created by King on 16/3/10.
 */
public class FastJsonTest {

    private String config = "";

    @Before
    public void readConfig() throws IOException {

        BufferedReader reader = new BufferedReader(new FileReader(new File(System.getProperty("user.dir") + "/config/config.json")));
        String str;
        while((str = reader.readLine()) != null){
            config += str;
        }
        System.out.println("config: " + config);
    }

    @Test
    public void parseConfigToJson() {
        JSONObject object = JSON.parseObject(config);
        System.out.println(object.toString());
    }

}
