package org.crazybun.codegen.ssm.util;

public class StringUtils {
    public static final String UTF8 = "UTF-8";

    public static String trim(String str) {
        return str == null ? null : str.trim();
    }

    /**
     * 建议先进行上面的trim操作
     *
     * @param str
     *
     * @return
     */
    public static boolean isEmpty(String str) {
        return str == null || str.length() == 0;
    }
}
