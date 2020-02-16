package org.crazybun.codegen.ssm.assist;

/**
 * 适合处理[AA_BB_CC]等用下划线分割形式的表名
 * 处理后格式为[AaBbCc]
 */
public class DefaultTableConverter implements IConverter {

    @Override
    public String convert(String column) {
        if (column == null) {
            return column;
        }
        String lower = column.toLowerCase();
        String[] wordArr = lower.split("_");
        StringBuilder result = new StringBuilder();
        for (int i = 0, m = wordArr.length; i < m; i++) {
            result.append(wordArr[i].substring(0, 1).toUpperCase()).append(wordArr[i].substring(1));
        }
        return result.toString();
    }
}
