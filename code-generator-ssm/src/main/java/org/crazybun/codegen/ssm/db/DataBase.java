package org.crazybun.codegen.ssm.db;

public class DataBase {
    /**
     * 数据库类型
     */
    private DBType dbType;
    /**
     * 主机IP
     */
    private String ip;
    /**
     * 端口
     */
    private String port;
    /**
     * 数据库SID
     */
    private String sid;
    /**
     * 数据库用户
     */
    private String user;
    /**
     * 密码
     */
    private String password;

    public DataBase() {}

    public DataBase(String dbType, String ip, String port, String sid, String user, String password) {
        this.dbType = Enum.valueOf(DBType.class, dbType);
        this.ip = ip;
        this.port = port;
        this.sid = sid;
        this.user = user;
        this.password = password;
    }

    public String getUrl() {
        return new StringBuilder(dbType.getProtocol()).append(ip).append(":").append(port).append(dbType.getSeparator()).append(sid).append(dbType.getSuffixParam()).toString();
    }

    public String getTableSchema() {
        return DBType.SQLSERVER.equals(dbType) ? "dbo" : "%";
    }

    public DBType getDBType() {
        return dbType;
    }

    public void setDBType(DBType dbType) {
        this.dbType = dbType;
    }

    public String getIp() {
        return ip;
    }

    public void setIp(String ip) {
        this.ip = ip;
    }

    public String getPort() {
        return port;
    }

    public void setPort(String port) {
        this.port = port;
    }

    public String getSid() {
        return sid;
    }

    public void setSid(String sid) {
        this.sid = sid;
    }

    public String getUser() {
        return user;
    }

    public void setUser(String user) {
        this.user = user;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
