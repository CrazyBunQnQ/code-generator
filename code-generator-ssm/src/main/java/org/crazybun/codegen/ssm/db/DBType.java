package org.crazybun.codegen.ssm.db;

public enum DBType {
	MYSQL("com.mysql.jdbc.Driver", "jdbc:mysql://", 3306, "/", "?useUnicode=true&characterEncoding=UTF-8"), 
	ORACLE("oracle.jdbc.driver.OracleDriver", "jdbc:oracle:thin:@", 1521, ":", ""),
	SQLSERVER("com.microsoft.sqlserver.jdbc.SQLServerDriver", "jdbc:sqlserver://", 1433, ";DatabaseName=", "");
	
	private String driver;
	private String protocol;
	private int port;
	private String separator;
	private String suffixParam;
	private DBType(String driver, String protocol,
                   int port, String separator, String suffixParam) {
		this.driver = driver;
		this.protocol = protocol;
		this.port = port;
		this.separator = separator;
		this.suffixParam = suffixParam;
	}
	public String getDriver() {
		return driver;
	}
	public int getPort() {
		return port;
	}
	public String getProtocol() {
		return protocol;
	}
	public String getSeparator() {
		return separator;
	}
	public String getSuffixParam() {
		return suffixParam;
	}
}
