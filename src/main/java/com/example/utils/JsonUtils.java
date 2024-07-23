package com.example.utils;
import com.example.exception.AsyncTestingFrameworkException;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.jayway.jsonpath.JsonPath;

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

    public static String getValueFromJson(String json, String jsonPath) throws AsyncTestingFrameworkException {
        try {
            ObjectMapper objectMapper = new ObjectMapper();
            Object value = JsonPath.read(json, jsonPath);
            return objectMapper.writeValueAsString(value);
        } catch (JsonProcessingException e) {
            throw new AsyncTestingFrameworkException("Can't get value from json: " + json + ", using path: " + jsonPath);
        }

    }
}
