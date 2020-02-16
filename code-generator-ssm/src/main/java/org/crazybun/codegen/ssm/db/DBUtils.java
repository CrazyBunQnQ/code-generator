package org.crazybun.codegen.ssm.db;

import java.sql.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class DBUtils {

    private static final String ALL_TABLE_REMARK = "SELECT OBJNAME,CAST(VALUE AS NVARCHAR(128)) AS REMARK from fn_listextendedproperty('MS_Description','SCHEMA',N'dbo','TABLE',NULL,NULL,NULL)";

    public static List<Table> listAllTable(DataBase dataBase) throws ClassNotFoundException, SQLException {
        Connection connection = null;
        DatabaseMetaData dbMetaData = null;
        ResultSet rs = null;
        try {
            JDBCUtils.loadDriver(dataBase);
            connection = JDBCUtils.getConnection(dataBase);
            Map<String, String> msRemarkMap = DBType.SQLSERVER.equals(dataBase.getDBType()) ? getMSTableRemarks(connection, null) : null;
            dbMetaData = connection.getMetaData();
            rs = dbMetaData.getTables(null, dataBase.getTableSchema(), "%", new String[]{"TABLE", "VIEW"});
            List<Table> tableList = new ArrayList<Table>();
            while (rs.next()) {
                Table table = new Table(rs);
                if (DBType.ORACLE.equals(dataBase.getDBType()) && (!dataBase.getUser().equalsIgnoreCase(table.getTableSchem()) || table.getTableName().indexOf("=") != -1)) {
                    continue;
                }
                tableList.add(table);
                if (msRemarkMap != null && table.getRemarks() == null) {
                    table.setRemarks(msRemarkMap.get(table.getTableName()));
                }
            }
            return tableList;
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
            throw e;
        } catch (SQLException e) {
            e.printStackTrace();
            throw e;
        } finally {
            JDBCUtils.release(connection, rs);
        }
    }

    public static List<Table> listTableWithColumns(DataBase dataBase, String[] tableNames) {
        Connection connection = null;
        DatabaseMetaData dbMetaData = null;
        try {
            JDBCUtils.loadDriver(dataBase);
            connection = JDBCUtils.getConnection(dataBase);
            Map<String, String> msRemarkMap = null;
            Map<String, Map<String, String>> msTabRemarkMap = null;
            if (DBType.SQLSERVER.equals(dataBase.getDBType())) {
                msRemarkMap = getMSTableRemarks(connection, tableNames);
                msTabRemarkMap = getMSColumnRemarks(connection, tableNames);
            }
            dbMetaData = connection.getMetaData();
            List<Table> tableList = new ArrayList<Table>();
            for (String tableName : tableNames) {
                ResultSet tabRs = dbMetaData.getTables(null, dataBase.getTableSchema(), tableName, new String[]{"TABLE", "VIEW"});
                if (tabRs.next()) {
                    Table table = new Table(tabRs);
                    ResultSet colRs = dbMetaData.getColumns(null, table.getTableSchem(), table.getTableName(), null);
                    List<Column> columnList = new ArrayList<Column>();
                    Map<String, String> msColRemarkMap = msTabRemarkMap != null ? msTabRemarkMap.get(tableName) : null;
                    while (colRs.next()) {
                        Column column = new Column(colRs);
                        columnList.add(column);
                        if (msColRemarkMap != null && column.getRemarks() == null) {
                            column.setRemarks(msColRemarkMap.get(column.getColumnName()));
                        }
                    }
                    JDBCUtils.release(colRs);
                    table.setColumns(columnList);
                    tableList.add(table);
                    if (msRemarkMap != null && table.getRemarks() == null) {
                        table.setRemarks(msRemarkMap.get(table.getTableName()));
                    }
                } else {
                    tableList.add(null);
                }
                JDBCUtils.release(tabRs);
            }
            return tableList;
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            JDBCUtils.release(connection);
        }
        return null;
    }

    /**
     * 获取SQL SERVER表注释
     *
     * @param connection
     * @param tableNames
     *
     * @return Map<表名, 表注释>
     */
    private static Map<String, String> getMSTableRemarks(Connection connection, String[] tableNames) {
        Statement statement = null;
        ResultSet rs = null;
        try {
            statement = connection.createStatement();
            rs = statement.executeQuery(getMsRemarkQuerySql(tableNames));
            Map<String, String> map = new HashMap<String, String>();
            while (rs.next()) {
                map.put(rs.getString(1), rs.getString(2));
            }
            return map;
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            JDBCUtils.release(statement, rs);
        }
        return null;
    }

    /**
     * 获取SQL SERVER表中字段的注释
     *
     * @param connection
     * @param tableNames
     *
     * @return Map<表名, Map < 字段名, 字段注释>>
     */
    private static Map<String, Map<String, String>> getMSColumnRemarks(Connection connection, String[] tableNames) {
        Statement statement = null;
        ResultSet rs = null;
        try {
            statement = connection.createStatement();
            StringBuilder sb = new StringBuilder("SELECT OBJNAME,CAST(VALUE AS NVARCHAR(128)) AS REMARK from fn_listextendedproperty('MS_Description','SCHEMA',N'dbo','TABLE',N'");
            int initLen = sb.length();
            Map<String, Map<String, String>> tabMap = new HashMap<String, Map<String, String>>();
            for (String tableName : tableNames) {
                sb.setLength(initLen);
                sb.append(tableName).append("','COLUMN',NULL)");
                rs = statement.executeQuery(sb.toString());
                Map<String, String> colMap = new HashMap<String, String>();
                while (rs.next()) {
                    colMap.put(rs.getString(1), rs.getString(2));
                }
                tabMap.put(tableName, colMap);
                JDBCUtils.release(rs);
            }
            return tabMap;
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            JDBCUtils.release(statement, rs);
        }
        return null;
    }

    /**
     * 获取SQL SERVER查询注释的SQL语句
     *
     * @param tableNames
     *
     * @return
     */
    private static String getMsRemarkQuerySql(String[] tableNames) {
        if (tableNames == null || tableNames.length == 0) {
            return ALL_TABLE_REMARK;
        }
        StringBuilder sb = new StringBuilder("SELECT t.* FROM (").append(ALL_TABLE_REMARK).append(") t WHERE t.OBJNAME IN(");
        int index = 0;
        for (String tableName : tableNames) {
            if (index++ > 0) {
                sb.append(",");
            }
            sb.append("'").append(tableName).append("'");
        }
        return sb.append(")").toString();
    }
}
