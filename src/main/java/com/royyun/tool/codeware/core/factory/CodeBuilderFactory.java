package com.royyun.tool.codeware.core.factory;

import com.royyun.tool.codeware.core.configure.ContextConfigure;

/**
 * 模板使用工厂
 *
 * @author Administrator
 */
public class CodeBuilderFactory {

    private static CodeBuilderFactory self;

    private static String defaultFilePath = "../default.properties";

    private CodeBuilderFactory() {

    }

    public static synchronized CodeBuilderFactory newInstance() {
        if (self == null) {
            self = new CodeBuilderFactory();
        }
        return self;
    }

    public static CodeBuilder newDefaultBuilder() {
        return newCodeBuilder(defaultFilePath);
    }

    public static CodeBuilder newCodeBuilder(String filePath) {
        ContextConfigure configureLoad = new ContextConfigure(filePath);
        CodeBuilder dbBuilder = new CodeBuilder(
                configureLoad.getCodeConfigure());
        return dbBuilder;
    }

    public static CodeBuilder newCodeBuilder(ContextConfigure configureLoad) {
        CodeBuilder dbBuilder = new CodeBuilder(
                configureLoad.getCodeConfigure());
        return dbBuilder;
    }


}
