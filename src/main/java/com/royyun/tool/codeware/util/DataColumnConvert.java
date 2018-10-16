package com.royyun.tool.codeware.util;


import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 数据库列改驼峰写法
 *
 * @author Administrator
 */
public class DataColumnConvert {
    public static List<Map<String, Object>> covert(
            List<Map<String, Object>> list) {
        if (list != null) {
            List<Map<String, Object>> result = new ArrayList<Map<String, Object>>();
            for (Map<String, Object> map : list) {
                result.add(covert(map));
            }
            return result;
        }
        return list;
    }

    public static Map<String, Object> covert(Map<String, Object> map) {
        if (map != null) {
            Map<String, Object> result = new HashMap<String, Object>();
            for (String key : map.keySet()) {
                result.put(covert(key), covert(map.get(key)));
            }
            return result;
        }
        return map;
    }

    @SuppressWarnings("unchecked")
    public static Object covert(Object obj) {
        if (obj != null) {
            if (obj instanceof List<?>) {
                return covert((List<Map<String, Object>>) obj);
            } else if (obj instanceof Map) {
                return covert((Map<String, Object>) obj);
            } else if (obj instanceof BigDecimal) {
                //hessian BigDecimal 类型处理
                return new Integer(((BigDecimal) obj).intValue());
            } else {
                return obj;
            }
        }
        return obj;
    }

    public static String covert(String columnName) {
        if (columnName != null) {
            try {
                String columnNameCopy = columnName;
                columnNameCopy = columnNameCopy.replace("O_", "");
                columnNameCopy = columnNameCopy.toLowerCase();
                StringBuffer resultString = new StringBuffer();
                boolean isUp = false;
                for (int i = 0; i < columnNameCopy.length(); i++) {
                    String item = columnNameCopy.substring(i, i + 1);
                    if (item.equals("_")) {
                        isUp = true;
                    } else {
                        if (isUp) {
                            resultString.append(item.toUpperCase());
                            isUp = false;
                        } else {
                            resultString.append(item);
                        }
                    }

                }
                return resultString.toString();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return columnName;
    }
}
