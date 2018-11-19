package com.royyun.tool.codeware.util;

import com.royyun.tool.codeware.module.basics.ColumnModule;
import com.royyun.tool.codeware.module.basics.TableModule;

import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 * 从数据库里加载表
 *
 * @author Administrator
 */
public class TableModuleBulider {
    private static String getPrimaryKeyName(ResultSet rs) throws SQLException {
        String columnName = rs.getString("COLUMN_NAME");
        return columnName;
    }

    /**
     * 日志输出
     */
    //public Logger log = Logger.getLogger(TableModuleBulider.class);
    public TableModule bulider(Connection con, String tables) throws Exception {
        TableModule tableAgency = null;

        DatabaseMetaData dbmd = DBUtils.getDatabaseMetaData(con);
        String dbType = dbmd.getDatabaseProductName();
        //log.info(String.format("  数据库类型dbType  ：%s", dbType));
        String[] types = {"TABLE"};
        ResultSet rs = null;
        String schema = null;
        try {
            if (true) {
                schema = con.getSchema();
            }
            if (tables != null) {
                // 循环加载table
                //orcl 大写
                //String tableName = tables.toUpperCase();
                //mysql 小写
                String tableName = tables.toLowerCase();
                //将t去掉
                 //tableName = tableName.substring(1);
                //log.info(String.format(" 加载表  ：%s", tableName));
                //orcl
               // rs = dbmd.getTables(null, schema, tableName, types);
                //mysql
                rs = dbmd.getTables(con.getCatalog(), "%", tableName, types);
                if (rs.next()) {
                    tableAgency = solveTable(rs);
                    List<ColumnModule> keyList = new ArrayList<ColumnModule>();
                    String keyName = "";
                    List<ColumnModule> ColList = new ArrayList<ColumnModule>();
                    //orcl
                    // rs = dbmd.getPrimaryKeys(null, null, tableName);
                    //mysql
                    rs = dbmd.getPrimaryKeys(con.getCatalog(), "%", tableName);
                    // 加载这个table的列
                    //log.info(String.format("加载表  ：%s的列", tableName));
                    if (rs.next()) {
                        keyName = getPrimaryKeyName(rs);
                    }
                    // 加载这个table的列
                    //orcl
                    //rs = dbmd.getColumns(null, schema, tableName, null);
                    //mysql
                    rs = dbmd.getColumns(con.getCatalog(), "%", tableName, "%");
                    while (rs.next()) {
                        ColumnModule solveColumn = solveColumn(rs, dbType);
                        if (keyName.equals(solveColumn.getColumnName())) {
                            keyList.add(solveColumn);
                        } else {
                            ColList.add(solveColumn);
                        }
                    }
                    tableAgency.setColumns(ColList);
                    tableAgency.setKeyList(keyList);

                }

                setTableDescribe(con, tables, tableAgency);
            }
        } catch (SQLException e) {
            throw new RuntimeException("获取表信息出错", e);
        }
        return tableAgency;
    }

    private TableModule solveTable(ResultSet rs) throws SQLException {
        TableModule ta = new TableModule();
        ta.setTableCat(rs.getString("TABLE_CAT  ".trim()));
        ta.setTableSchema(rs.getString("TABLE_SCHEM".trim()));
        ta.setTableType(rs.getString("TABLE_TYPE ".trim()));
        ta.setRemarks(rs.getString("REMARKS    ".trim()));
        ta.setTableName(rs.getString("TABLE_NAME        ".trim()));
        //log.info(String.format("  发现表【%s】", ta.getTableName()));
        ta.setAlias(DBUtils.createTableAlias());
        return ta;
    }

    private ColumnModule solveColumn(ResultSet rs, String dbTpye)
            throws SQLException {
        ColumnModule column = new ColumnModule();

        column.setTableCat(rs.getString("TABLE_CAT         ".trim()));
        column.setTableSchema(rs.getString("TABLE_SCHEM       ".trim()));
        column.setTableName(rs.getString("TABLE_NAME        ".trim()));
        column.setColumnName(rs.getString("COLUMN_NAME       ".trim()));
        column.setDataType(rs.getInt("DATA_TYPE         ".trim()));
        column.setTypeName(SqlTypeUtils.decodeToName(column.getDataType()));
        column.setColumnSize(rs.getInt("COLUMN_SIZE       ".trim()));
        column.setDecimalDigits(rs.getInt("DECIMAL_DIGITS    ".trim()));
        column.setNumPrecRadix(rs.getInt("NUM_PREC_RADIX    ".trim()));
        column.setNullable(rs.getInt("NULLABLE          ".trim()));
        column.setRemarks(rs.getString("REMARKS           ".trim()));
        column.setColumnDef(rs.getString("COLUMN_DEF        ".trim()));
        column.setCharOctetLength(rs.getInt("CHAR_OCTET_LENGTH ".trim()));
        column.setOrdinalPosition(rs.getInt("ORDINAL_POSITION  ".trim()));
        column.setIsNullable(rs.getString("IS_NULLABLE       ".trim()));
        if (dbTpye != "Oracle") {
            column.setScopeCatalog(rs.getString("SCOPE_CATALOG     ".trim()));
            column.setScopeSchema(rs.getString("SCOPE_SCHEMA      ".trim()));
            column.setScopeTable(rs.getString("SCOPE_TABLE       ".trim()));
            column.setSourceDataType(rs.getShort("SOURCE_DATA_TYPE  ".trim()));
            column.setIsAutoincrement(rs.getString("IS_AUTOINCREMENT  ".trim()));
        }
//		log.info(String.format("  表【%s】发现列【%s】，列类型为【%s】",
//				column.getTableName(), column.getColumnName(),
//				column.getTypeName()));
        return column;
    }

    public void setTableDescribe(Connection conn, String table,
                                 TableModule tableAgency) {
        if (tableAgency == null) {
            tableAgency = new TableModule();
        }
        String sql = "select comments from user_tab_comments u where u.TABLE_NAME ='"
                + table.toUpperCase() + "'";
        String describe = (String) OracleHelper.queryForObject(conn, sql);
        tableAgency.setDescribe(describe);
        if (tableAgency.getColumns() != null) {
            for (int i = 0; i < tableAgency.getColumns().size(); i++) {
                String columnName = tableAgency.getColumns().get(i).getName();
                sql = "select comments from user_col_comments c where c.TABLE_NAME = '"
                        + table.toUpperCase()
                        + "' and c.COLUMN_NAME = '"
                        + columnName.toUpperCase() + "'";
                describe = (String) OracleHelper.queryForObject(conn, sql);
                tableAgency.getColumns().get(i).setDescribe(describe);
            }
        }
        if (tableAgency.getKeys() != null) {
            for (int i = 0; i < tableAgency.getKeys().size(); i++) {
                String columnName = tableAgency.getKeys().get(i).getName();
                sql = "select comments from user_col_comments c where c.TABLE_NAME = '"
                        + table.toUpperCase()
                        + "' and c.COLUMN_NAME = '"
                        + columnName.toUpperCase() + "'";
                describe = (String) OracleHelper.queryForObject(conn, sql);
                tableAgency.getKeys().get(i).setDescribe(describe);
            }
        }
    }

    public void setMySqlTableDescribe(Connection conn, String table,
                                 TableModule tableAgency) {
        if (tableAgency == null) {
            tableAgency = new TableModule();
        }
        String sql = "select comments from user_tab_comments u where u.TABLE_NAME ='"
                + table.toUpperCase() + "'";
        String describe = (String) OracleHelper.queryForObject(conn, sql);
        tableAgency.setDescribe(describe);
        if (tableAgency.getColumns() != null) {
            for (int i = 0; i < tableAgency.getColumns().size(); i++) {
                String columnName = tableAgency.getColumns().get(i).getName();
                sql = "select comments from user_col_comments c where c.TABLE_NAME = '"
                        + table.toUpperCase()
                        + "' and c.COLUMN_NAME = '"
                        + columnName.toUpperCase() + "'";
                describe = (String) OracleHelper.queryForObject(conn, sql);
                tableAgency.getColumns().get(i).setDescribe(describe);
            }
        }
        if (tableAgency.getKeys() != null) {
            for (int i = 0; i < tableAgency.getKeys().size(); i++) {
                String columnName = tableAgency.getKeys().get(i).getName();
                sql = "select comments from user_col_comments c where c.TABLE_NAME = '"
                        + table.toUpperCase()
                        + "' and c.COLUMN_NAME = '"
                        + columnName.toUpperCase() + "'";
                describe = (String) OracleHelper.queryForObject(conn, sql);
                tableAgency.getKeys().get(i).setDescribe(describe);
            }
        }
    }

}
