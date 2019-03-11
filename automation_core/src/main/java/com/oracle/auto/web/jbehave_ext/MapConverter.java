package com.oracle.auto.web.jbehave_ext;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import org.apache.log4j.Logger;
import org.jbehave.core.steps.ParameterConverters.ParameterConverter;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

public class MapConverter implements ParameterConverter {
    private static Logger log = Logger.getLogger(MapConverter.class);

    private List<Class<?>> acceptableClassList = new ArrayList<Class<?>>();

    public MapConverter() {
    }

    public MapConverter(Class<?>... classList) {
        acceptableClassList.addAll(Arrays.asList(classList));
    }

    public boolean accept(Type type) {
        return type.equals(new TypeToken<Map<String, String>>() {
        }.getType());
    }

    public Object convertValue(String value, Type type) {
        log.debug("Converting to type [" + type + "] from json string: " + value);

        try {
            if (accept(type)) {
                return new Gson().fromJson(value, new TypeToken<Map<String, String>>() {
                }.getType());
            }
            throw new RuntimeException("Error converting. Type is not in accepetable list. type [" + type + "] from json string: " + value);
        } catch (Exception e) {
            log.error("Error converting to type [" + type + "] from json string: " + value, e);
            throw new RuntimeException("Error converting to type [" + type + "] from json string: " + value, e);
        }
    }
}
