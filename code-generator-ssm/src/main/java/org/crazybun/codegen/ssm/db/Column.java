package org.crazybun.codegen.ssm.db;

import java.sql.ResultSet;
import java.sql.SQLException;

public class Column {
	private String columnName;
	private int dataType;
	private int decimalDigits;
	private int nullable;
	private String remarks;
	
	public Column() {}
	public Column(String columnName, int dataType, int decimalDigits, int nullable, String remarks) {
		this.columnName = columnName;
		this.dataType = dataType;
		this.decimalDigits = decimalDigits;
		this.nullable = nullable;
		this.remarks = remarks;
	}
	
	public Column(ResultSet rs) throws SQLException {
		this.columnName = rs.getString("COLUMN_NAME");
		this.dataType = rs.getInt("DATA_TYPE");
		this.decimalDigits = rs.getInt("DECIMAL_DIGITS");
		this.nullable = rs.getInt("NULLABLE");
		this.remarks = rs.getString("REMARKS");
	}
	public String getColumnName() {
		return columnName;
	}
	public void setColumnName(String columnName) {
		this.columnName = columnName;
	}
	public int getDataType() {
		return dataType;
	}
	public void setDataType(int dataType) {
		this.dataType = dataType;
	}
	public int getDecimalDigits() {
		return decimalDigits;
	}
	public void setDecimalDigits(int decimalDigits) {
		this.decimalDigits = decimalDigits;
	}
	public int getNullable() {
		return nullable;
	}
	public void setNullable(int nullable) {
		this.nullable = nullable;
	}
	public String getRemarks() {
		return remarks;
	}
	public void setRemarks(String remarks) {
		this.remarks = remarks;
	}
}
