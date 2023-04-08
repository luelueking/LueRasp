package com.lue.rasp.tool;

public class StringUtils {
    /**
     * 检查字符串是否非空（包括null和空格字符）
     * @param str 待检查字符串
     * @return true-非空，false-空
     */
    public static boolean isNotBlank(String str) {
        return str != null && !str.trim().isEmpty();
    }

    /**
     * 检查字符串是否为空（包括null和空格字符）
     * @param str 待检查字符串
     * @return true-为空，false-非空
     */
    public static boolean isBlank(String str) {
        return str == null || str.trim().isEmpty();
    }

}
