package com.patandmat.otmz.global.utils;

import com.google.gson.Gson;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

import java.util.HashMap;
import java.util.Map;

public class VectorParser {

    public static final String STYLE_KEY = "style";
    public static final String STYLE_VALUE = "score";

    // for raw vector
    public static Map<String, Double> parseToMap(String jsonString, String name, String value) {
        if (jsonString == null) {
            return new HashMap<>();
        }

        JsonElement jsonElement = JsonParser.parseString(jsonString);
        JsonObject jsonObject = jsonElement.getAsJsonObject();

        Map<String, Double> map = new HashMap<>();
        for (String key : jsonObject.keySet()) {
            JsonObject element = jsonObject.get(key).getAsJsonObject();
            map.put(element.get(name).getAsString(), Double.parseDouble(element.get(value).getAsString()));
        }

        return map;
    }

    // for member style stat
    public static Map<String, Double> parseToMap(String jsonString) {
        if (jsonString == null) {
            return new HashMap<>();
        }

        JsonObject jsonObject = JsonParser.parseString(jsonString).getAsJsonObject();

        Map<String, Double> map = new HashMap<>();
        for (String key : jsonObject.keySet()) {
            map.put(key, jsonObject.get(key).getAsDouble());
        }

        return map;
    }

    public static String parseToString(Map<String, Double> map) {
        Gson gson = new Gson();
        return gson.toJson(map);
    }
}
