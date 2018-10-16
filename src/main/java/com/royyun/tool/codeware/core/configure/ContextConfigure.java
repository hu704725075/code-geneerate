package com.royyun.tool.codeware.core.configure;

import com.royyun.tool.codeware.util.FileUtil;

import java.util.Map;

public class ContextConfigure {

    private final String TEMPLATE_FILE_TYPE_NAME = "template.type";
    private final String TEMPLATE_PATH_NAME = "template.path";
    private final String CONFIG_KEY = "template.config.type";
    private final String DEFAULT_KEY = "template.basics";
    private final String DEFAULT_SAVE_KEY = "template.basics.save";

    CodeConfigure factoryConfigure;

    private Map<String, Object> data;

    public ContextConfigure(String filePath) {
        data = FileUtil.loadProperties(filePath);
        factoryConfigure = new CodeConfigure(this, DEFAULT_KEY);
    }

    public ContextConfigure(String filePath, String key) {
        data = FileUtil.loadProperties(filePath);
        factoryConfigure = new CodeConfigure(this, key);
    }

    public CodeConfigure getCodeConfigure() {
        return factoryConfigure;
    }

    String getValue(String key) {
        return (String) data.get(key);
    }

    String getTemplateFileType() {
        String fileType = (String) data.get(TEMPLATE_FILE_TYPE_NAME);
        if (fileType == null || "".equals(fileType.trim())) {
            return ".txt";
        } else {
            return fileType;
        }
    }

    String getConfigType() {
        String configType = (String) data.get(CONFIG_KEY);
        if (configType == null || "".equals(configType.trim())) {
            return ".properties";
        } else {
            return configType;
        }
    }

    String getTemplatePath() {
        return (String) data.get(this.TEMPLATE_PATH_NAME);
    }

    String getSavePath() {
        return (String) data.get(this.DEFAULT_SAVE_KEY);
    }
}
