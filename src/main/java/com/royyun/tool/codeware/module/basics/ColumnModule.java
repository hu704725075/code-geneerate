package com.royyun.tool.codeware.module.basics;

import com.royyun.tool.codeware.util.DataColumnConvert;
import com.royyun.tool.codeware.util.SqlTypeUtils;


public class ColumnModule {

    private String alias;
    private String columnName;
    private String typeName;
    private String remarks;
    private int dataType;
    private String tableName;
    private String tableCat;// TABLE_CAT
    private String tableSchema;// TABLE_SCHEM
    private Integer columnSize;// COLUMN_SIZE
    private Integer decimalDigits;// DECIMAL_DIGITS
    private Integer numPrecRadix;// NUM_PREC_RADIX
    private Integer nullable;// NULLABLE
    private String columnDef;// COLUMN_DEF
    private Integer charOctetLength;// CHAR_OCTET_LENGTH
    private Integer ordinalPosition;// ORDINAL_POSITION
    private String isNullable;// IS_NULLABLE
    private String scopeCatalog;// SCOPE_CATALOG
    private String scopeSchema;// SCOPE_SCHEMA
    private String scopeTable;// SCOPE_TABLE
    private Short sourceDataType;// SOURCE_DATA_TYPE
    private String isAutoincrement;// IS_AUTOINCREMENT
    private String describe;

    public String getColumnName() {
        return columnName;
    }

    public void setColumnName(String columnName) {
        this.columnName = columnName;
    }

    public String getTypeName() {
        return typeName;
    }

    public void setTypeName(String typeName) {
        this.typeName = typeName;
    }

    public int getDataType() {
        return dataType;
    }

    public void setDataType(int dataType) {
        this.dataType = dataType;
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

    public Integer getColumnSize() {
        return columnSize;
    }

    public void setColumnSize(Integer columnSize) {
        this.columnSize = columnSize;
    }

    public Integer getDecimalDigits() {
        return decimalDigits;
    }

    public void setDecimalDigits(Integer decimalDigits) {
        this.decimalDigits = decimalDigits;
    }

    public Integer getNumPrecRadix() {
        return numPrecRadix;
    }

    public void setNumPrecRadix(Integer numPrecRadix) {
        this.numPrecRadix = numPrecRadix;
    }

    public Integer getNullable() {
        return nullable;
    }

    public void setNullable(Integer nullable) {
        this.nullable = nullable;
    }

    public String getColumnDef() {
        return columnDef;
    }

    public void setColumnDef(String columnDef) {
        this.columnDef = columnDef;
    }

    public Integer getCharOctetLength() {
        return charOctetLength;
    }

    public void setCharOctetLength(Integer charOctetLength) {
        this.charOctetLength = charOctetLength;
    }

    public Integer getOrdinalPosition() {
        return ordinalPosition;
    }

    public void setOrdinalPosition(Integer ordinalPosition) {
        this.ordinalPosition = ordinalPosition;
    }

    public String getIsNullable() {
        return isNullable;
    }

    public void setIsNullable(String isNullable) {
        this.isNullable = isNullable;
    }

    public String getScopeCatalog() {
        return scopeCatalog;
    }

    public void setScopeCatalog(String scopeCatalog) {
        this.scopeCatalog = scopeCatalog;
    }

    public String getScopeSchema() {
        return scopeSchema;
    }

    public void setScopeSchema(String scopeSchema) {
        this.scopeSchema = scopeSchema;
    }

    public String getScopeTable() {
        return scopeTable;
    }

    /**
     * @param scopeTable
     */
    public void setScopeTable(String scopeTable) {
        this.scopeTable = scopeTable;
    }

    public Short getSourceDataType() {
        return sourceDataType;
    }

    public void setSourceDataType(Short sourceDataType) {
        this.sourceDataType = sourceDataType;
    }

    public String getIsAutoincrement() {
        return isAutoincrement;
    }

    public void setIsAutoincrement(String isAutoincrement) {
        this.isAutoincrement = isAutoincrement;
    }

    public String getUpperName() {
        String camelCase = DataColumnConvert.covert(columnName);
        return camelCase.substring(0, 1).toUpperCase() + camelCase.substring(1);
    }

    public String getLowerName() {
        return DataColumnConvert.covert(columnName);

    }

    public String getJdbcType() {
        return typeName;
    }

    public String getRemarks() {
        return remarks;
    }

    public void setRemarks(String remarks) {
        this.remarks = remarks;
    }

    public String getJavaType() {
        return SqlTypeUtils.decodeToJavaType(dataType);
    }

    public String getVoJavaType() {
        return SqlTypeUtils.decodeToJavaTypeVo(dataType);
    }

    public String getName() {
        return columnName;
    }

    public String getTableName() {
        return tableName;
    }

    public void setTableName(String tableName) {
        this.tableName = tableName;
    }

    public String getAlias() {
        return alias;
    }

    public void setAlias(String alias) {
        this.alias = alias;
    }

    public String getDescribe() {
        return describe;
    }

    public void setDescribe(String describe) {
        this.describe = describe;
    }

}
