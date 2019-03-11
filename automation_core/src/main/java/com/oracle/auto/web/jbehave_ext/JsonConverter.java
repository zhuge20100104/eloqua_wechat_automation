package com.oracle.auto.web.jbehave_ext;

import com.google.gson.Gson;
import com.oracle.auto.web.annotations.JsonConvertable;
import org.apache.log4j.Logger;
import org.jbehave.core.steps.ParameterConverters.ParameterConverter;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class JsonConverter implements ParameterConverter {
    private static Logger log = Logger.getLogger(JsonConverter.class);

    private List<Class<?>> acceptableClassList = new ArrayList<Class<?>>();

    public JsonConverter() {
    }

    public JsonConverter(Class<?>... classList) {
        acceptableClassList.addAll(Arrays.asList(classList));
    }

    public boolean accept(Type type) {
        if (type instanceof Class<?>) {
            Class<?> classType = (Class<?>) type;

            if (acceptableClassList.contains(classType)) {
                return true;
            }

            if (classType.getAnnotation(JsonConvertable.class) != null) {
                return true;
            }
        }

        return false;
    }

    public Object convertValue(String value, Type type) {
        log.debug("Converting to type [" + type + "] from json string: " + value);

        try {
            if (type instanceof Class<?>) {
                return new Gson().fromJson(value, (Class<?>) type);
            }
            throw new RuntimeException("Error converting. Type is not in accepetable list. type [" + type + "] from json string: " + value);
        } catch (Exception e) {
            log.error("Error converting to type [" + type + "] from json string: " + value, e);
            throw new RuntimeException("Error converting to type [" + type + "] from json string: " + value, e);
        }
    }
}
