package com.oracle.auto.web.jbehave_ext;

import com.google.gson.Gson;
import com.oracle.auto.web.annotations.JsonListConvertable;
import org.apache.log4j.Logger;
import org.jbehave.core.steps.ParameterConverters.ParameterConverter;

import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class JsonListConverter implements ParameterConverter {

	private static Logger log = Logger.getLogger(JsonListConverter.class);

	private List<Class<?>> acceptableClassList = new ArrayList<Class<?>>();

	public JsonListConverter() {
	}

	public JsonListConverter(Class<?>... classList) {
		acceptableClassList.addAll(Arrays.asList(classList));
	}

	public boolean accept(Type type) {
		if (type instanceof ParameterizedType) {

			ParameterizedType parameterizedType = (ParameterizedType) type;
			Type argumentType = parameterizedType.getActualTypeArguments()[0];

			Class<?> classType = (Class<?>) argumentType;

			if (acceptableClassList.contains(argumentType)) {
				return true;
			}

			if (classType.getAnnotation(JsonListConvertable.class) != null) {
				return true;
			}
		}

		return false;
	}

	public Object convertValue(String value, Type type) {
		log.debug("Converting to type [" + type + "] from json string: "
				+ value);
		final String DEFAULT_LIST_SEPARATOR = ";;";
		List<Object> jsonObjects = new ArrayList<Object>();
		List<String> values = Arrays
				.asList(value.split(DEFAULT_LIST_SEPARATOR));
		try {
			if (type instanceof ParameterizedType) {
				ParameterizedType parameterizedType = (ParameterizedType) type;
				Type[] actualTypeArguments = parameterizedType
						.getActualTypeArguments();

				for (String val : values) {
					for (Type argumentType : actualTypeArguments) {
						Class<?> classType = (Class<?>) argumentType;
						jsonObjects.add(new Gson().fromJson(val.trim(),
								(Class<?>) classType));
					}
				}
				return jsonObjects;
			}
			throw new RuntimeException(
					"Error converting. Type is not in accepetable list. type ["
							+ type + "] from json string: " + value);
		} catch (Exception e) {
			log.error("Error converting to type [" + type
					+ "] from json string: " + value, e);
			throw new RuntimeException("Error converting to type [" + type
					+ "] from json string: " + value, e);
		}
	}

}