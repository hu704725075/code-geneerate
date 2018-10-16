package com.royyun.tool.codeware.core.run;

import com.royyun.tool.codeware.core.factory.CodeBuilder;
import com.royyun.tool.codeware.core.factory.CodeBuilderFactory;
import java.util.HashMap;
import java.util.Map;

public class CodeWareRun {

    public static void main(String[] args) {

        try {
            CodeBuilderFactory dbf = CodeBuilderFactory.newInstance();
            CodeBuilder newDefaultBuilder = dbf.newCodeBuilder("D:\\code-generate\\src\\main\\java\\com\\royyun\\tool\\codeware\\default.properties");
            Map<String, Object> contextParams = new HashMap<String, Object>();
            //newDefaultBuilder.setContextParams(contextParams);
            newDefaultBuilder.builderForTableToSave("t_ad");
        } catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

    }

}
