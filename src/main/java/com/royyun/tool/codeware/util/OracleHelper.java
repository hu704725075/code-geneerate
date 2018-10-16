package com.royyun.tool.codeware.util;

import java.math.BigDecimal;
import java.sql.*;
import java.util.List;

/**
 * oracle的一些通用的数据库操作
 *
 * @author Administrator
 */
public class OracleHelper {

    public static Object queryForObject(Connection con, String querySql) {
        Object result = null;
        try {
            Statement createStatement = con.createStatement();
            ResultSet resultSet = createStatement.executeQuery(querySql);
            if (resultSet.next()) {
                result = resultSet.getObject(1);
            }
            resultSet.close();
            createStatement.close();
        } catch (SQLException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } finally {

        }
        return result;
    }

    public static BigDecimal querySequenceNextValue(Connection con, String sequenceName) {
        String sql = "select " + sequenceName + ".nextval from dual";
        return (BigDecimal) queryForObject(con, sql);
    }

    public static void batchUpdate(Connection con, String sql, List<Object[]> datas) throws SQLException {

        PreparedStatement ps = con.prepareStatement(sql);
        if (datas != null) {
            for (int i = 0; i < datas.size(); i++) {
                Object[] item = datas.get(i);
                for (int j = 0; j < item.length; j++) {
                    ps.setObject(j + 1, item[j]);
                }
                ps.addBatch();
            }
        }
        int[] arr = ps.executeBatch();
        for (int i = 0; i < arr.length; i++) {
            System.out.println(arr[i]);
            if (arr[i] < 1) {
                //throw new SQLException("批处理失败");
            }
        }
    }

    public static void batchUpdate(Connection con, String sql) throws SQLException {
        Statement statement = con.createStatement();
        int executeUpdate = statement.executeUpdate(sql);
        if (executeUpdate < 1) {
            throw new SQLException("修改失败");
        }
    }
}
