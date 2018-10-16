package com.royyun.tool.codeware.core.configure;

import com.royyun.tool.codeware.util.DBUtils;
import com.royyun.tool.codeware.util.FileUtil;
import org.apache.log4j.Logger;
import org.beetl.core.Configuration;
import org.beetl.core.ResourceLoader;
import org.beetl.core.resource.FileResourceLoader;

import java.sql.Connection;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class CodeConfigure {

    String splitStr = ",";

    Logger log = Logger.getLogger(getClass());

    List<String> filePaths;

    Map<String, String> savefilePaths;

    Map<String, Object> contextParameter;

    Map<String, Map<String, Object>> fileParameters;

    ContextConfigure context;

    Configuration beetlConfig;

    ResourceLoader beetlResourceLoader;

    CodeConfigure(ContextConfigure context, String key) {
        this.context = context;
        init(key);
    }

    private void init(String defaultKey) {
        log.info("加载模板配置开始");
        if (context == null) {
            throw new RuntimeException("context is null");
        }
        if (defaultKey == null) {
            throw new RuntimeException("defaultKey is null");
        }

        try {

            this.beetlConfig = Configuration.defaultConfiguration();
            this.beetlResourceLoader = new FileResourceLoader(
                    context.getTemplatePath());
            String filePath = context.getTemplatePath() + "/contextParameter"
                    + context.getConfigType();
            try {

                this.contextParameter = FileUtil.loadProperties(filePath);
            } catch (Exception e) {

            }
            String templates = context.getValue(defaultKey);
            log.info("加载[" + defaultKey + "]的值为" + templates);
            if (templates != null) {
                String[] split = templates.split(splitStr);
                filePaths = new ArrayList<String>();
                savefilePaths = new HashMap<String, String>();
                for (String string : split) {
                    String value = context.getValue(string);
                    filePaths.add(value);
                    savefilePaths
                            .put(value, context.getValue(string + ".save"));
                }
                fileParameters = new HashMap<String, Map<String, Object>>();
                for (String string : filePaths) {
                    try {

                        filePath = context.getTemplatePath() + "/" + string
                                + context.getConfigType();
                        fileParameters.put(string,
                                FileUtil.loadProperties(filePath));
                    } catch (Exception e) {

                    }
                }

            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        log.info("加载模板配置结束");

    }

    public Configuration getBeetlConfig() {
        return beetlConfig;
    }

    public ResourceLoader getBeetlResourceLoader() {
        return beetlResourceLoader;
    }

    public Map<String, Object> getContextParameter() {
        return contextParameter;
    }

    public Map<String, String> getSavefilePaths() {
        return savefilePaths;
    }

    public List<String> getFilePaths() {
        return filePaths;
    }

    public Map<String, Map<String, Object>> getFileParameters() {
        return fileParameters;
    }

    public String getTemplateFileType() {
        return context.getTemplateFileType();
    }

    public String getDefaultSavePath() {
        return context.getSavePath();
    }

    public Connection getDefaultConnection() {
        String driver = context.getValue("jdbc.driver");
        String url = context.getValue("jdbc.url");
        String username = context.getValue("jdbc.username");
        String password = context.getValue("jdbc.password");
        String schema = context.getValue("jdbc.schema");
        Connection con = DBUtils.getConn(driver, url, username, password, schema);
        return con;
    }
}
