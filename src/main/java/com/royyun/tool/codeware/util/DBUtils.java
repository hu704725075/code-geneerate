package com.royyun.tool.codeware.util;

import org.apache.log4j.Logger;

import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * 数据库连接获取
 *
 * @author Administrator
 */
public class DBUtils {

    public static final Logger log = Logger.getLogger(DBUtils.class);
    private static AtomicInteger tableSeq = new AtomicInteger();
    private static String schemaStr;

    public static Connection getConn(String driver, String url,
                                     String username, String password, String schema) {
        schemaStr = schema;
        Connection conn = null;

        log.info(String.format("  加载配置项driver  ：%s", driver));
        log.info(String.format("  加载配置项url     ：%s", url));
        log.info(String.format("  加载配置项username：%s", username));
        log.info(String.format("  加载配置项password：%s", password));
        try {
            Class.forName(driver);

            conn = DriverManager.getConnection(url, username, password);
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(
                    String.format("[JDBC驱动加载错误][%s]", driver), e);
        } catch (SQLException e) {
            throw new RuntimeException(String.format("[数据库连接失败]"), e);
        }
        log.info("*************初始化数据库【完成】！");
        return conn;
    }

    public static String getOracleSchema() {
        return schemaStr;
    }

    public static DatabaseMetaData getDatabaseMetaData(Connection conn) {
        DatabaseMetaData dbmd = null;
        // ResultSet rs = null;
        try {
            dbmd = conn.getMetaData();
            // rs = dbmd.getTypeInfo();
            // while (rs.next()) {
            //
            // log.info(String.format("类型名称【%s】 JavaType【%s】最大精度【%s】",
            // rs.getString(1),
            // SqlTypeUtils.decodeToJavaType(rs.getInt(2)),
            // rs.getString(3)));
            // }
            String dbType = dbmd.getDatabaseProductName();
            String dbVersion = dbmd.getDatabaseProductVersion();
            String driverName = dbmd.getDriverName();
            String driverVersion = dbmd.getDriverVersion();
            log.info(String.format(
                    "数据库类型【%s】数据库版本【%s】数据库驱动名称【%s】数据库驱动程序版本【%s】", dbType,
                    dbVersion, driverName, driverVersion));
        } catch (SQLException e) {
            throw new RuntimeException("获取元数据出错", e);
        }
        return dbmd;
    }

    public static String createTableAlias() {
        return "t" + tableSeq.incrementAndGet();
    }

}
