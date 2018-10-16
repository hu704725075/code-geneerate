package com.royyun.tool.codeware.core.factory;

import com.royyun.tool.codeware.core.configure.CodeConfigure;
import com.royyun.tool.codeware.module.basics.TableModule;
import com.royyun.tool.codeware.util.FileUtil;
import com.royyun.tool.codeware.util.TableModuleBulider;
import org.beetl.core.GroupTemplate;
import org.beetl.core.Template;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 模板使用工厂
 *
 * @author Administrator
 */
public class CodeBuilder {

    private GroupTemplate group;

    private CodeConfigure config;

    private Map<String, Map<String, Object>> params = new HashMap<String, Map<String, Object>>();

    private Map<String, Object> contextParams = new HashMap<String, Object>();

    private CodeInvokeListener listener;

    private String filePath;

    public CodeBuilder(CodeConfigure config) {
        this.config = config;

        group = new GroupTemplate(this.config.getBeetlResourceLoader(),
                this.config.getBeetlConfig());
    }

    public void setParams(Map<String, Map<String, Object>> params) {
        this.params.putAll(params);
    }

    public void setContextParams(Map<String, Object> contextParams) {
        this.contextParams.putAll(contextParams);
    }

    public void setListener(CodeInvokeListener listener) {
        this.listener = listener;
    }

    public List<String> builder() {
        List<String> filePaths = config.getFilePaths();
        List<String> result = new ArrayList<String>();
        if (filePaths != null && filePaths.size() > 0) {
            for (String fileName : filePaths) {
                Template item = group.getTemplate(fileName
                        + config.getTemplateFileType());
                Map<String, Object> hasMap = invoke(fileName);
                item.fastBinding(hasMap);
                String render = item.render();
                result.add(render);
            }
        }
        return result;
    }

    public void builderToSave() {
        if (filePath == null) {
            filePath = config.getDefaultSavePath();
        }
        List<String> filePaths = config.getFilePaths();

        if (filePaths != null && filePaths.size() > 0) {
            for (int i = 0;i<filePaths.size();i++){//String fileName : filePaths) {
                Template item = group.getTemplate(filePaths.get(i)
                        + config.getTemplateFileType());
                Map<String, Object> hasMap = invoke(filePaths.get(i));
                item.fastBinding(hasMap);
                String render = item.render();
                String saveContollerPath = filePath
                        + config.getSavefilePaths().get(filePaths.get(i))
                        .replace("##", (String) hasMap.get("fileName"));
                System.out.println(saveContollerPath+"__________"+i);
                FileUtil.update(saveContollerPath, render);
            }
        }

    }

    public void builderToSave(String filePath, String tableName)
            throws Exception {
        TableModuleBulider tb = new TableModuleBulider();
        Map<String, Object> map = new HashMap<String, Object>();
        TableModule bulider = tb.bulider(config.getDefaultConnection(),
                "t_user");
        map.put("table", bulider);
        map.put("fileName", bulider.getUpperName());
        setContextParams(map);
        setFilePath(filePath);
        builderToSave();
    }

    public void builderForTableToSave(String tableName) throws Exception {
        TableModuleBulider tb = new TableModuleBulider();
        Map<String, Object> map = new HashMap<String, Object>();
        TableModule bulider = tb.bulider(config.getDefaultConnection(),
                tableName);
        map.put("table", bulider);
        map.put("fileName", bulider.getUpperName());
        setContextParams(map);
        builderToSave();
    }

    public void setFilePath(String filePath) {
        this.filePath = filePath;
    }

    private Map<String, Object> invoke(String fileName) {
        Map<String, Object> hasMap = new HashMap<String, Object>();

        Map<String, Object> contextParameter = this.config
                .getContextParameter();
        if (contextParameter != null) {
            hasMap.putAll(contextParameter);
        }


        Map<String, Object> fileNameMap = config.getFileParameters().get(
                fileName);
        if (fileNameMap != null) {
            hasMap.putAll(fileNameMap);
        }

        if (this.contextParams != null) {
            hasMap.putAll(this.contextParams);
        }

        if (params != null) {
            Map<String, Object> NameMap = this.params.get(fileName);
            if (NameMap != null) {
                hasMap.putAll(NameMap);
            }
        }

        if (listener != null) {
            listener.inovke(fileName, hasMap);
        }
        return hasMap;
    }

    public CodeConfigure getConfig() {
        return config;
    }
}
