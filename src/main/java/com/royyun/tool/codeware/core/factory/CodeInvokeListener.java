package com.royyun.tool.codeware.core.factory;

import java.util.Map;

public interface CodeInvokeListener {

    Map<String, Object> inovke(String fileName, Map<String, Object> args);
}
