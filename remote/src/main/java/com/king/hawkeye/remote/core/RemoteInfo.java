package com.king.hawkeye.remote.core;

import java.util.Date;

/**
 * Created by King on 16/4/13.
 */
public class RemoteInfo {
    private String projectName;
    private String clientName;
    private String source;
    private Date date;
    private Object info;

    public RemoteInfo(String projectName, String clientName, String source, Object info) {
        this.projectName = projectName;
        this.clientName = clientName;
        this.source = source;
        this.info = info;
        this.date = new Date();
    }

    public String getProjectName() {
        return projectName;
    }

    public void setProjectName(String projectName) {
        this.projectName = projectName;
    }

    public String getClientName() {
        return clientName;
    }

    public void setClientName(String clientName) {
        this.clientName = clientName;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public Object getInfo() {
        return info;
    }

    public void setInfo(Object info) {
        this.info = info;
    }

    public String getSource() {
        return source;
    }

    public void setSource(String source) {
        this.source = source;
    }

    @Override
    public String toString() {
        return "RemoteInfo{" +
                "projectName='" + projectName + '\'' +
                ", clientName='" + clientName + '\'' +
                ", source='" + source + '\'' +
                ", date=" + date +
                ", info=" + info +
                '}';
    }
}
