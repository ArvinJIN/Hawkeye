package com.king.hawkeye.server.dao;

import com.king.hawkeye.server.entity.AlertConfig;
import org.apache.commons.lang.ArrayUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by King on 16/4/26.
 */
public class AlertConfigDao implements IAlertConfigDao {

    private static final Logger LOG = LogManager.getLogger(AlertConfigDao.class);

    private Connection connection = BaseDao.getConnection();

    @Override
    public List<AlertConfig> getByProjectId(int projectId) {
        List<AlertConfig> result = null;
        try {
            PreparedStatement prepareStatement = connection.prepareStatement("SELECT C.*,P.name as project_name FROM t_alert_config C left join t_project P on C.project_id = P.id where project_id = ?");
            prepareStatement.setInt(1, projectId);
            ResultSet rs = prepareStatement.executeQuery();
            int cols = rs.getMetaData().getColumnCount();
            result = new ArrayList<AlertConfig>(cols);
            while (rs.next()) {
                AlertConfig config = new AlertConfig();
                config.setId(rs.getInt("id"));
                config.setProjectId(rs.getInt("project_id"));
                config.setProjectName(rs.getString("project_name"));
                config.setConfig(rs.getString("config"));
                result.add(config);
            }
        } catch (SQLException e) {
            LOG.error("get all alert config error.", e);
            return null;
        }
        return result;
    }

    @Override
    public List<AlertConfig> getAll() {
        List<AlertConfig> result = null;
        try {
            PreparedStatement prepareStatement = connection.prepareStatement("SELECT C.*,P.name as project_name FROM t_alert_config C left join t_project P on C.project_id = P.id");
            ResultSet rs = prepareStatement.executeQuery();
            int cols = rs.getMetaData().getColumnCount();
            result = new ArrayList<AlertConfig>(cols);
            while (rs.next()) {
                AlertConfig config = new AlertConfig();
                config.setId(rs.getInt("id"));
                config.setProjectId(rs.getInt("project_id"));
                config.setProjectName(rs.getString("project_name"));
                config.setConfig(rs.getString("config"));
                result.add(config);
            }
        } catch (SQLException e) {
            LOG.error("get all alert config error.", e);
            return null;
        }
        return result;
    }

    @Override
    public Integer[] getIdsByProjectName(String projectName) {
        Integer[] result = new Integer[0];
        try {
            PreparedStatement preparedStatement = connection.prepareStatement("select id from t_project where name = ?");
            preparedStatement.setString(1, projectName);
            ResultSet rs = preparedStatement.executeQuery();
            if (rs.getMetaData().getColumnCount() <= 0) {
                return new Integer[0];
            }
            while (rs.next()) {
                int projectId = rs.getInt("id");

                List<AlertConfig> list = this.getByProjectId(projectId);
                for (AlertConfig config : list) {
                    result = (Integer[]) ArrayUtils.add(result, config.getId());
                }
            }
        } catch (SQLException e) {
            LOG.error("get config ids by project name error, project name is " + projectName, e);
        }
        return result;
    }
}
