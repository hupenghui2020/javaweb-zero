package com.hph.helper;

import com.hph.util.CollectionUtil;
import com.hph.util.PropsUtil;
import com.sun.org.slf4j.internal.Logger;
import com.sun.org.slf4j.internal.LoggerFactory;
import org.apache.commons.dbcp2.BasicDataSource;
import org.apache.commons.dbutils.QueryRunner;
import org.apache.commons.dbutils.handlers.BeanHandler;
import org.apache.commons.dbutils.handlers.BeanListHandler;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Properties;

/**
 * 数据库助手类
 * @author hph
 */
public final class DatabaseHelper {

    private static final Logger LOGGER = LoggerFactory.getLogger(DatabaseHelper.class);

    private static final QueryRunner QUERY_RUNNER = new QueryRunner();

    private static final ThreadLocal<Connection> CONNECTION_HOLDER = new ThreadLocal<>();

    private static final BasicDataSource DATA_SOURCE;

    static {
        Properties conf = PropsUtil.loadProps("config.properties");
        String driver = conf.getProperty("jdbc.driver");
        try {
            // 驱动类是否存在
            Class.forName(driver);
        }catch (ClassNotFoundException e) {
            LOGGER.error("can not load jdbc driver", e);
        }
        DATA_SOURCE = new BasicDataSource();
        DATA_SOURCE.setDriverClassName(driver);
        DATA_SOURCE.setUrl(conf.getProperty("jdbc.url"));
        DATA_SOURCE.setUsername(conf.getProperty("jdbc.username"));
        DATA_SOURCE.setPassword(conf.getProperty("jdbc.password"));
    }

    /**
     * 获取连接
     * @return
     */
    private static Connection getConnection() {

        Connection conn = CONNECTION_HOLDER.get();
        if(conn == null) {
            try{
                conn = DATA_SOURCE.getConnection();
            }catch (SQLException e) {
                LOGGER.error("get connection failure", e);
                throw new RuntimeException(e);
            }finally {
                CONNECTION_HOLDER.set(conn);
            }
        }
        return conn;
    }

    /**
     * 查询实体列表
     * @param entityClass
     * @param sql
     * @param params
     * @param <T>
     * @return
     */
    public static <T> List<T> queryEntityList(Class<T> entityClass, String sql, Object... params) {
        List<T> entityList;
        try{
            entityList = QUERY_RUNNER.query(getConnection(), sql, new BeanListHandler<T>(entityClass), params);
        }catch (SQLException e) {
            LOGGER.error("query entity list failure", e);
            throw new RuntimeException(e);
        }finally {
            closeConnection();
        }
        return entityList;
    }

    /**
     * 查询实体
     * @param entityClass
     * @param sql
     * @param params
     * @param <T>
     * @return
     */
    public static <T>T queryEntity(Class<T> entityClass, String sql, Object... params) {
        T entity;
        try {
            entity = QUERY_RUNNER.query(getConnection(), sql, new BeanHandler<>(entityClass), params);
        }catch (SQLException e) {
            LOGGER.error("query entity failure", e);
            throw new RuntimeException(e);
        }finally {
            closeConnection();
        }
        return entity;
    }

    /**
     * 插入实体
     * @param entityClass
     * @param fieldMap
     * @param <T>
     * @return
     */
    public static <T> boolean insertEntity(Class<T> entityClass, Map<String,Object> fieldMap) {
        if(CollectionUtil.isEmpty(fieldMap)) {
            LOGGER.error("can not insert entity: fieldMap is empty");
            return false;
        }
        String sql = "INSERT INTO " + getTableName(entityClass);
        StringBuilder columns = new StringBuilder("(");
        StringBuilder values = new StringBuilder("(");
        for(String fieldName : fieldMap.keySet()) {
            columns.append(fieldName).append(", ");
            values.append("? ");
        }
        columns.replace(columns.lastIndexOf(", "), columns.length(), ")");
        values.replace(values.lastIndexOf(", "), values.length(), ")");
        sql += columns + "VALUES" + values;
        return executeUpdate(sql, fieldMap.values().toArray()) == 1;
    }

    /**
     * 更新实体
     * @param entityClass
     * @param id
     * @param fieldMap
     * @param <T>
     * @return
     */
    public static <T> boolean updateEntity(Class<T> entityClass, long id, Map<String,Object> fieldMap) {
        if(CollectionUtil.isEmpty(fieldMap)) {
            LOGGER.error("can not update entity: fieldMap is empty");
            return false;
        }
        String sql = "UPDATE " + getTableName(entityClass) + " SET";
        StringBuilder columns = new StringBuilder("(");
        for(String fieldName : fieldMap.keySet()) {
            columns.append(fieldName).append("=?, ");
        }
        sql += columns.substring(0, columns.lastIndexOf(", ")) + " WHERE id=?";
        List<Object> paramList = new ArrayList<>();
        paramList.addAll(fieldMap.values());
        paramList.add(id);
        return executeUpdate(sql, paramList.toArray()) == 1;
    }

    public static <T> boolean deleteEntity(Class<T> entityClass, long id) {
        String sql = "DELETE FROM " + getTableName(entityClass) + " WHERE id=?";
        return executeUpdate(sql, id) == 1;
    }

    public static int executeUpdate(String sql, Object... params) {
        int rows = 0;
        try {
            rows = QUERY_RUNNER.update(getConnection(), sql, params);
        }catch (SQLException e) {
            LOGGER.error("execute update failure", e);
            throw new RuntimeException(e);
        }finally {
            closeConnection();
        }
        return rows;
    }

    /**
     * 执行 SQL 文件
     * @param file
     * @throws IOException
     */
    public static void executeSqlFile(String file) throws IOException {
        InputStream is = Thread.currentThread().getContextClassLoader().getResourceAsStream(file);
        BufferedReader br = new BufferedReader(new InputStreamReader(is));
        try {
            String sql;
            while ((sql = br.readLine()) != null) {
                DatabaseHelper.executeUpdate(sql);
            }
        }catch (Exception e) {
            LOGGER.error("execute sql file failure", e);
            throw new RuntimeException(e);
        }
    }

    private static <T> String getTableName(Class<T> entityClass) {
        return entityClass.getSimpleName();
    }

    /**
     * 关闭数据库连接
     */
    private static void closeConnection() {
        Connection conn = CONNECTION_HOLDER.get();
        if(conn != null) {
            try {
                conn.close();
            }catch (SQLException e) {
                LOGGER.error("close connection failure", e);
                throw new RuntimeException(e);
            }finally {
                CONNECTION_HOLDER.remove();
            }
        }
    }
}
