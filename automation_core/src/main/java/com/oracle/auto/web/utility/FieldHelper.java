package com.oracle.auto.web.utility;

import com.oracle.auto.web.pages.object.JsonHelper;

import java.lang.annotation.Annotation;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class FieldHelper {
	private static List<Field> getInheritedPrivateFields(Class<?> type) {
	    List<Field> result = new ArrayList<Field>();

	    Class<?> i = type;
	    while (i != null && i != Object.class) {
	    	for (Field field : i.getDeclaredFields())
	    		result.add(field);
	        i = i.getSuperclass();
	    }

	    return result;
	}
	
	// by default only for expose
	public static void setFields(String jsonValue, Object target, Class<? extends Annotation> annotation) {
		Object copy = JsonHelper.fromJson(jsonValue, target.getClass());
		
		List<Field> fields = getInheritedPrivateFields(target.getClass());
		for (Field field : fields) {
			if (annotation == null || (annotation != null && field.getAnnotation(annotation) != null)) {
				// load this field from config
				try {
					field.setAccessible(true);
					if (field.get(copy) != null)
						field.set(target, field.get(copy));
				} catch (IllegalArgumentException | IllegalAccessException e) {
					e.printStackTrace();
				}
				
				continue;
			}
		}
	}

	public static void setFields(String jsonValue, Object target) {
		setFields(jsonValue, target, null);
	}

	
	public static String getConfig(Map<String, String> config,String name) {
		Object obj = config.get(name);
		if (obj == null || obj.toString().isEmpty()) throw new RuntimeException("config doesn't include name: " + name + " detail: " + config.toString());
		
		return obj.toString();
	}

	public static boolean getConfig(Map<String, String> config, String name, boolean def) {
		Object obj = config.get(name);
		if (obj == null || obj.toString().isEmpty()) return def;
		
		return Boolean.parseBoolean(obj.toString());
	}
	
	public static String getConfig(Map<String, String> config, String name, String def) {
		Object obj = config.get(name);
		if (obj == null || obj.toString().isEmpty()) return def;
		
		return obj.toString();
	}
	
	public static String dumpObjects(Object obj) {
		return JsonHelper.getGsonExportOnly().toJsonTree(obj).toString();
	}

}
