package org.crazybun.codegen.ssm.assist;

/**
 * 适合处理MSM项目中[in_AaBbCc、dt_AaBbCc、tx_AaBbCC、tf_AaBbCC等]等形式的字段名
 * 处理后格式为[aaBbCc];去掉首个下划线及其前面部分[in_整数、dt_日期、tx_字符串、tf_判断]截取下划线后面部分(会将最后一位转为小写)
 */
public class MsmColumnConverter implements IConverter {

    @Override
    public String convert(String column) {
        if (column == null) {
            return column;
        }


        String lower = column.toLowerCase();
        if (lower.indexOf("_") == -1) {
            return lower;
        }
        String[] wordArr = lower.split("_");
        StringBuilder result = new StringBuilder(wordArr[0]);
        for (int i = 1, m = wordArr.length; i < m; i++) {
            result.append(wordArr[i].substring(0, 1).toUpperCase()).append(wordArr[i].substring(1));
        }
        return result.toString();
    }
}
