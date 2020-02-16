package org.crazybun.codegen.ssm.assist;

public interface IConverter {
	/**
	 * 将数据库中表、列名转换为Java中类名、属性
	 * @param column
	 * @return
	 */
	public String convert(String column);
}
