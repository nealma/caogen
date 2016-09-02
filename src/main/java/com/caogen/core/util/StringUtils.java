package com.caogen.core.util;

import java.io.UnsupportedEncodingException;


/**
 * 字符串处理 静态工具类，继承于{@link org.apache.commons.lang.StringUtils}
 *
 */
public class StringUtils extends org.apache.commons.lang.StringUtils {


    /**
     * 将method的大写字母转换为下划线+小写字母 例如: getMsg -> get_msg
     *
     * @param name
     */
    public static String addUnderscores(String name) {
        if (name == null)
            return null;
        StringBuffer buf = new StringBuffer(name.replace('.', '_'));
        for (int i = 1; i < buf.length() - 1; i++) {
            if ('_' != buf.charAt(i - 1) && Character.isUpperCase(buf.charAt(i)) && !Character.isUpperCase(buf.charAt(i + 1))) {
                buf.insert(i++, '_');
            }
        }
        return buf.toString().toLowerCase();
    }

    /**
     * 获取某字符的SET方法
     *
     * @param str
     */
    public static String getSeter(String str) {
        return "set" + str.substring(0, 1).toUpperCase() + str.substring(1);
    }

    /**
     * 获取某字符的GET方法
     *
     * @param str
     */
    public static String getGeter(String str) {
        return "get" + str.substring(0, 1).toUpperCase() + str.substring(1);
    }

    /**
     * 将method的下划线去掉，并将其后的第一个字母转换为大写字母 例如: dp_gr_query -> DpGrQuery
     *
     * @param name
     */
    public static String removeUnderscores(String name) {
        if (name == null)
            return null;
        StringBuffer buf = new StringBuffer();
        String[] words = split(name.toLowerCase(), "_");
        for (int i = 0; i < words.length; i++) {
            buf.append(capitalise(words[i]));
        }
        return buf.toString();
    }

    /**
     * dp_gr_query -> dpGrQuery
     *
     * @param name
     */
    public static String getJavaName(String name) {
        if (name == null)
            return null;
        String converted = removeUnderscores(name);
        return new StringBuffer(converted.length()).append(Character.toLowerCase(converted.charAt(0))).append(converted.substring(1))
                .toString();
    }
    public static String encodeUrlData(String value){
        try {
            if(value==null){
                return null;
            }
            return java.net.URLEncoder.encode(value,"UTF-8");
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        return value;
    }
    public static String getfileNameEncode(String fileName){
        String encodeFileName;
        try {
            encodeFileName = new String(fileName.getBytes(), "UTF-8");
            return encodeFileName;
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        return fileName;
    }

}