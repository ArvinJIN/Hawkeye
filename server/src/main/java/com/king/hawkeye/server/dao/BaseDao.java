package com.king.hawkeye.server.dao;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;

/**
 * Created by King on 16/4/26.
 */
public class BaseDao {
    private static final Logger LOG = LogManager.getLogger(BaseDao.class);

    private static String URL;
    private static String USERNAME;
    private static String PASSWORD;
    private static Connection connection;
    static {
        try {
            //加载MySql的驱动类
            Class.forName("com.mysql.jdbc.Driver");
        } catch (ClassNotFoundException e) {
            System.out.println("找不到驱动程序类 ，加载驱动失败！");
            e.printStackTrace();
        }

        try {
            Properties properties = new Properties();
            InputStream in = new FileInputStream(new File(System.getProperty("user.dir") + "/config/database.properties"));
            properties.load(in);
            
            URL = properties.getProperty("url");
            USERNAME = properties.getProperty("username");
            PASSWORD = properties.getProperty("password");

            LOG.info("database properties: " + URL + "," + USERNAME + "," + PASSWORD);
        } catch (IOException e) {
            e.printStackTrace();
        }

        try{
            connection = DriverManager.getConnection(URL, USERNAME, PASSWORD);
        }catch(SQLException se){
            System.out.println("数据库连接失败！");
            se.printStackTrace() ;
        }
    }

    public static Connection getConnection() {
        return connection;
    }
}
