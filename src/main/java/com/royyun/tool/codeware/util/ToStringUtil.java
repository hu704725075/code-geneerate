package com.royyun.tool.codeware.util;

import java.util.List;
import java.util.Map;

/**
 * toString工具类
 *
 * @author Administrator
 */
public class ToStringUtil {

    @SuppressWarnings("rawtypes")
    public static String objectToString(Object obj) {
        String line = "\t";
        if (obj != null) {
            if (obj instanceof List<?>) {
                return objectToString((List<?>) obj, line);
            } else if (obj instanceof Map) {
                return objectToString((Map) obj, line);
            } else if (obj instanceof String[]) {
                return objectToString((String[]) obj, line);
            } else {
                return obj.toString();
            }
        }
        return null;
    }

    @SuppressWarnings("rawtypes")
    public static String objectToString(Object obj, String line) {
        if (obj != null) {
            if (obj instanceof List) {
                return objectToString((List) obj, line);
            } else if (obj instanceof Map) {
                return objectToString((Map) obj, line);
            } else if (obj instanceof String[]) {
                return objectToString((String[]) obj, line);
            } else {
                return obj.toString();
            }
        }
        return null;
    }

    @SuppressWarnings("rawtypes")
    private static String objectToString(Map obj, String line) {
        if (obj != null) {
            StringBuffer result = new StringBuffer("{");
            for (Object str : obj.keySet()) {

                Object object = obj.get(str);
                result.append(line + objectToString(str, line) + "="
                        + objectToString(object, line));
                result.append("\n");
            }

            result.append("}");
            return result.toString();
        }
        return null;
    }

    private static String objectToString(List<?> obj, String line) {
        if (obj != null) {
            StringBuffer result = new StringBuffer(line + "[");
            for (int i = 0; i < obj.size(); i++) {
                result.append(line + objectToString(obj.get(i), line + line));
                result.append("\n");
            }

            result.append("]" + line);
        }
        return null;
    }

    private static String objectToString(String[] obj, String line) {
        if (obj != null) {
            StringBuffer result = new StringBuffer("[");
            for (int i = 0; i < obj.length; i++) {
                result.append(obj[i] + ",");
            }
            result.append("]");
            return result.toString();
        }
        return null;
    }

}
