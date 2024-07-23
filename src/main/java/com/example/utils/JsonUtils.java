package com.example.utils;
import com.example.exception.AsyncTestingFrameworkException;

import java.io.File;
import java.nio.file.Files;
import java.util.Map;

public class JsonUtils {

    public static String getJsonFromTemplate(String templatePath, Map<String, Object> variables) throws AsyncTestingFrameworkException {
        try {
            String template = new String(Files.readAllBytes(new File(templatePath).toPath()));
            for (Map.Entry<String, Object> variable : variables.entrySet()) {
                template = template.replace("${" + variable.getKey() + "}", String.valueOf(variable.getValue()));
            }
            return template;
        } catch (Exception e) {
            throw new AsyncTestingFrameworkException("Can't get JSON string from template file '" + templatePath + "'", e);
        }
    }

    public static String getJsonFromTemplate(String templatePath, String variableName, String variableValue) throws AsyncTestingFrameworkException {
        try {
            String template = new String(Files.readAllBytes(new File(templatePath).toPath()));
            template = template.replace("${" + variableName + "}", variableValue);
            return template;
        } catch (Exception e) {
            throw new AsyncTestingFrameworkException("Can't get JSON string from template file '" + templatePath + "'", e);
        }
    }


    public static String getJsonFromFile(String filePath) throws AsyncTestingFrameworkException {
        try {
            return new String(Files.readAllBytes(new File(filePath).toPath()));
        } catch (Exception e) {
            throw new AsyncTestingFrameworkException("Can't get JSON string from file '" + filePath + "'", e);
        }
    }
}
