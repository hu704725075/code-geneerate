package com.royyun.tool.codeware.module.basics;

import com.royyun.tool.codeware.util.DataColumnConvert;

import java.util.List;
import java.util.UUID;


public class TableModule {

    private List<ColumnModule> keyList;
    private List<ColumnModule> columns;
    private String alias;
    private String tableName;
    private String tableCat;// TABLE_CAT
    private String tableSchema;// TABLE_SCHEM
    private String tableType;// TABLE_TYPE
    private String remarks;// REMARKS
    private String describe;
    private String moduleName;

    private boolean isFactoryVC;


    public boolean isFactoryVC() {
        return isFactoryVC;
    }

    public void setFactoryVC(boolean isFactoryVC) {
        this.isFactoryVC = isFactoryVC;
    }

    public String getModuleName() {
        return DataColumnConvert.covert(moduleName);
    }

    public void setModuleName(String moduleName) {
        this.moduleName = moduleName;
    }

    public String getUpperName() {
        String camelCase = DataColumnConvert.covert(tableName);
        return tableName.indexOf("_") != -1 ? camelCase.substring(0, 1).toUpperCase() + camelCase.substring(1) : camelCase;

    }

    public String getLowerName() {
        return DataColumnConvert.covert(tableName);
    }

    public String getUpperName1() {
        String camelCase = DataColumnConvert.covert(tableName.substring(1));
        return tableName.indexOf("_") != -1 ? camelCase.substring(0, 1).toUpperCase() + camelCase.substring(1) : camelCase;

    }

    public String getLowerName1() {
        return DataColumnConvert.covert(tableName.substring(1));
    }

    public String getName() {
        return tableName;
    }

    public List<ColumnModule> getKeys() {
        return keyList;
    }

    public List<ColumnModule> getColumns() {
        return columns;
    }

    public void setColumns(List<ColumnModule> columns) {
        this.columns = columns;
    }

    public String getAlias() {
        return this.alias;
    }

    public void setAlias(String alias) {
        this.alias = alias;
    }

    public void setKeyList(List<ColumnModule> keyList) {
        this.keyList = keyList;
    }

    public String getTableCat() {
        return tableCat;
    }

    public void setTableCat(String tableCat) {
        this.tableCat = tableCat;
    }

    public String getTableSchema() {
        return tableSchema;
    }

    public void setTableSchema(String tableSchema) {
        this.tableSchema = tableSchema;
    }

    public String getTableType() {
        return tableType;
    }

    public void setTableType(String tableType) {
        this.tableType = tableType;
    }

    public String getRemarks() {
        return remarks;
    }

    public void setRemarks(String remarks) {
        this.remarks = remarks;
    }

    public String getTableName() {
        return tableName;
    }

    public void setTableName(String tableName) {
        this.tableName = tableName;
    }

    public String getDescribe() {
        return describe;
    }

    public void setDescribe(String describe) {
        this.describe = describe;
    }

    @Override
    public int hashCode() {
        // TODO Auto-generated method stub
        return this.tableName.hashCode();
    }

    public String getMethodNo() {
        return "FCN-" + UUID.randomUUID().toString();
    }
}
