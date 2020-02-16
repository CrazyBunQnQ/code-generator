package org.crazybun.codegen.ssm.db;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import java.sql.*;
import java.util.Properties;

public class JDBCUtils {
    private static final Log logger = LogFactory.getLog(JDBCUtils.class);

    public static void loadDriver(DataBase dataBase) throws ClassNotFoundException {
        try {
            Class.forName(dataBase.getDBType().getDriver());
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
            logger.error("Load database driver failed");
            throw e;
        }
    }

    public static Connection getConnection(DataBase dataBase) throws SQLException {
        Properties props = new Properties();
        props.put("user", dataBase.getUser());
        props.put("password", dataBase.getPassword());
        props.put("useInformationSchema", "true");
        props.put("remarksReporting", "true");
        String url = dataBase.getUrl();
        logger.info("Connection URL is [" + url + "]");
        try {
            return DriverManager.getConnection(url, props);
        } catch (SQLException e) {
            e.printStackTrace();
            logger.error("Get database connection failed");
            throw e;
        }
    }

    public static void release(Connection conn, ResultSet rs) {
        release(rs);
        release(conn);
    }

    public static void release(Statement statement, ResultSet rs) {
        release(rs);
        release(statement);
    }

    public static void release(Connection conn) {
        if (conn != null) {
            try {
                conn.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }

    public static void release(Statement statement) {
        if (statement != null) {
            try {
                statement.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }

    public static void release(ResultSet rs) {
        if (rs != null) {
            try {
                rs.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }
}
