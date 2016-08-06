package com.king.hawkeye.server.dao;


import com.king.hawkeye.server.entity.AlertConfig;

import java.util.List;

/**
 * Created by King on 16/3/21.
 */
public interface IAlertConfigDao {

    List<AlertConfig> getByProjectId(int projectId);

    List<AlertConfig> getAll();

    Integer[] getIdsByProjectName(String projectName);
}
