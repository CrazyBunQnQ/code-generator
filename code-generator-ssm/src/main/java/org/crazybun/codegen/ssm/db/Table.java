package org.crazybun.codegen.ssm.db;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

public class Table {
	private String tableSchem;
	private String tableName;
	private String remarks;
	private List<Column> columns;

	public Table() {}
	public Table(String tableSchem, String tableName, String remarks) {
		this.tableSchem = tableSchem;
		this.tableName = tableName;
		this.remarks = remarks;
	}
	public Table(ResultSet rs) throws SQLException {
		this.tableSchem = rs.getString(2);
		this.tableName = rs.getString(3);
		this.remarks = rs.getString(5);
	}
	public String getTableSchem() {
		return tableSchem;
	}
	public void setTableSchem(String tableSchem) {
		this.tableSchem = tableSchem;
	}
	public String getTableName() {
		return tableName;
	}
	public void setTableName(String tableName) {
		this.tableName = tableName;
	}
	public String getRemarks() {
		return remarks;
	}
	public void setRemarks(String remarks) {
		this.remarks = remarks;
	}
	public List<Column> getColumns() {
		return columns;
	}
	public void setColumns(List<Column> columns) {
		this.columns = columns;
	}
}
